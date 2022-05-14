package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.repository.ClienteRepository;
import com.mycompany.myapp.service.ClienteService;
import com.mycompany.myapp.service.criteria.ClienteCriteria;
import com.mycompany.myapp.service.dto.ClienteDTO;
import com.mycompany.myapp.service.mapper.ClienteMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClienteResourceIT {

    private static final String DEFAULT_NOME_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_APRESENTACAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_APRESENTACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICADOR_ATIVO = false;
    private static final Boolean UPDATED_INDICADOR_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Autowired
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteService clienteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nomeCompleto(DEFAULT_NOME_COMPLETO)
            .telefone(DEFAULT_TELEFONE)
            .nomeApresentacao(DEFAULT_NOME_APRESENTACAO)
            .indicadorAtivo(DEFAULT_INDICADOR_ATIVO);
        return cliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .telefone(UPDATED_TELEFONE)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testCliente.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testCliente.getNomeApresentacao()).isEqualTo(DEFAULT_NOME_APRESENTACAO);
        assertThat(testCliente.getIndicadorAtivo()).isEqualTo(DEFAULT_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void createClienteWithExistingId() throws Exception {
        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNomeCompleto(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].nomeApresentacao").value(hasItem(DEFAULT_NOME_APRESENTACAO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nomeCompleto").value(DEFAULT_NOME_COMPLETO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.nomeApresentacao").value(DEFAULT_NOME_APRESENTACAO))
            .andExpect(jsonPath("$.indicadorAtivo").value(DEFAULT_INDICADOR_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getClientesByIdFiltering() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        Long id = cliente.getId();

        defaultClienteShouldBeFound("id.equals=" + id);
        defaultClienteShouldNotBeFound("id.notEquals=" + id);

        defaultClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto equals to DEFAULT_NOME_COMPLETO
        defaultClienteShouldBeFound("nomeCompleto.equals=" + DEFAULT_NOME_COMPLETO);

        // Get all the clienteList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultClienteShouldNotBeFound("nomeCompleto.equals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto not equals to DEFAULT_NOME_COMPLETO
        defaultClienteShouldNotBeFound("nomeCompleto.notEquals=" + DEFAULT_NOME_COMPLETO);

        // Get all the clienteList where nomeCompleto not equals to UPDATED_NOME_COMPLETO
        defaultClienteShouldBeFound("nomeCompleto.notEquals=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto in DEFAULT_NOME_COMPLETO or UPDATED_NOME_COMPLETO
        defaultClienteShouldBeFound("nomeCompleto.in=" + DEFAULT_NOME_COMPLETO + "," + UPDATED_NOME_COMPLETO);

        // Get all the clienteList where nomeCompleto equals to UPDATED_NOME_COMPLETO
        defaultClienteShouldNotBeFound("nomeCompleto.in=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto is not null
        defaultClienteShouldBeFound("nomeCompleto.specified=true");

        // Get all the clienteList where nomeCompleto is null
        defaultClienteShouldNotBeFound("nomeCompleto.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto contains DEFAULT_NOME_COMPLETO
        defaultClienteShouldBeFound("nomeCompleto.contains=" + DEFAULT_NOME_COMPLETO);

        // Get all the clienteList where nomeCompleto contains UPDATED_NOME_COMPLETO
        defaultClienteShouldNotBeFound("nomeCompleto.contains=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeCompletoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeCompleto does not contain DEFAULT_NOME_COMPLETO
        defaultClienteShouldNotBeFound("nomeCompleto.doesNotContain=" + DEFAULT_NOME_COMPLETO);

        // Get all the clienteList where nomeCompleto does not contain UPDATED_NOME_COMPLETO
        defaultClienteShouldBeFound("nomeCompleto.doesNotContain=" + UPDATED_NOME_COMPLETO);
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone equals to DEFAULT_TELEFONE
        defaultClienteShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the clienteList where telefone equals to UPDATED_TELEFONE
        defaultClienteShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone not equals to DEFAULT_TELEFONE
        defaultClienteShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the clienteList where telefone not equals to UPDATED_TELEFONE
        defaultClienteShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultClienteShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the clienteList where telefone equals to UPDATED_TELEFONE
        defaultClienteShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone is not null
        defaultClienteShouldBeFound("telefone.specified=true");

        // Get all the clienteList where telefone is null
        defaultClienteShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone contains DEFAULT_TELEFONE
        defaultClienteShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the clienteList where telefone contains UPDATED_TELEFONE
        defaultClienteShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllClientesByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone does not contain DEFAULT_TELEFONE
        defaultClienteShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the clienteList where telefone does not contain UPDATED_TELEFONE
        defaultClienteShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao equals to DEFAULT_NOME_APRESENTACAO
        defaultClienteShouldBeFound("nomeApresentacao.equals=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the clienteList where nomeApresentacao equals to UPDATED_NOME_APRESENTACAO
        defaultClienteShouldNotBeFound("nomeApresentacao.equals=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao not equals to DEFAULT_NOME_APRESENTACAO
        defaultClienteShouldNotBeFound("nomeApresentacao.notEquals=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the clienteList where nomeApresentacao not equals to UPDATED_NOME_APRESENTACAO
        defaultClienteShouldBeFound("nomeApresentacao.notEquals=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao in DEFAULT_NOME_APRESENTACAO or UPDATED_NOME_APRESENTACAO
        defaultClienteShouldBeFound("nomeApresentacao.in=" + DEFAULT_NOME_APRESENTACAO + "," + UPDATED_NOME_APRESENTACAO);

        // Get all the clienteList where nomeApresentacao equals to UPDATED_NOME_APRESENTACAO
        defaultClienteShouldNotBeFound("nomeApresentacao.in=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao is not null
        defaultClienteShouldBeFound("nomeApresentacao.specified=true");

        // Get all the clienteList where nomeApresentacao is null
        defaultClienteShouldNotBeFound("nomeApresentacao.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao contains DEFAULT_NOME_APRESENTACAO
        defaultClienteShouldBeFound("nomeApresentacao.contains=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the clienteList where nomeApresentacao contains UPDATED_NOME_APRESENTACAO
        defaultClienteShouldNotBeFound("nomeApresentacao.contains=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllClientesByNomeApresentacaoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nomeApresentacao does not contain DEFAULT_NOME_APRESENTACAO
        defaultClienteShouldNotBeFound("nomeApresentacao.doesNotContain=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the clienteList where nomeApresentacao does not contain UPDATED_NOME_APRESENTACAO
        defaultClienteShouldBeFound("nomeApresentacao.doesNotContain=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllClientesByIndicadorAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where indicadorAtivo equals to DEFAULT_INDICADOR_ATIVO
        defaultClienteShouldBeFound("indicadorAtivo.equals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the clienteList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultClienteShouldNotBeFound("indicadorAtivo.equals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllClientesByIndicadorAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where indicadorAtivo not equals to DEFAULT_INDICADOR_ATIVO
        defaultClienteShouldNotBeFound("indicadorAtivo.notEquals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the clienteList where indicadorAtivo not equals to UPDATED_INDICADOR_ATIVO
        defaultClienteShouldBeFound("indicadorAtivo.notEquals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllClientesByIndicadorAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where indicadorAtivo in DEFAULT_INDICADOR_ATIVO or UPDATED_INDICADOR_ATIVO
        defaultClienteShouldBeFound("indicadorAtivo.in=" + DEFAULT_INDICADOR_ATIVO + "," + UPDATED_INDICADOR_ATIVO);

        // Get all the clienteList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultClienteShouldNotBeFound("indicadorAtivo.in=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllClientesByIndicadorAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where indicadorAtivo is not null
        defaultClienteShouldBeFound("indicadorAtivo.specified=true");

        // Get all the clienteList where indicadorAtivo is null
        defaultClienteShouldNotBeFound("indicadorAtivo.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByCarteiraClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        CarteiraCliente carteiraCliente;
        if (TestUtil.findAll(em, CarteiraCliente.class).isEmpty()) {
            carteiraCliente = CarteiraClienteResourceIT.createEntity(em);
            em.persist(carteiraCliente);
            em.flush();
        } else {
            carteiraCliente = TestUtil.findAll(em, CarteiraCliente.class).get(0);
        }
        em.persist(carteiraCliente);
        em.flush();
        cliente.setCarteiraCliente(carteiraCliente);
        clienteRepository.saveAndFlush(cliente);
        Long carteiraClienteId = carteiraCliente.getId();

        // Get all the clienteList where carteiraCliente equals to carteiraClienteId
        defaultClienteShouldBeFound("carteiraClienteId.equals=" + carteiraClienteId);

        // Get all the clienteList where carteiraCliente equals to (carteiraClienteId + 1)
        defaultClienteShouldNotBeFound("carteiraClienteId.equals=" + (carteiraClienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].nomeApresentacao").value(hasItem(DEFAULT_NOME_APRESENTACAO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .telefone(UPDATED_TELEFONE)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testCliente.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testCliente.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testCliente.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente.nomeApresentacao(UPDATED_NOME_APRESENTACAO);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testCliente.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testCliente.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testCliente.getIndicadorAtivo()).isEqualTo(DEFAULT_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .telefone(UPDATED_TELEFONE)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testCliente.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testCliente.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testCliente.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
