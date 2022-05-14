package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.domain.enumeration.TipoSaldo;
import com.mycompany.myapp.repository.CarteiraClienteRepository;
import com.mycompany.myapp.service.CarteiraClienteService;
import com.mycompany.myapp.service.criteria.CarteiraClienteCriteria;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.CarteiraClienteMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CarteiraClienteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CarteiraClienteResourceIT {

    private static final String DEFAULT_NOME_CARTEIRA_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CARTEIRA_CLIENTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALDO_CONSOLIDADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_CONSOLIDADO = new BigDecimal(2);
    private static final BigDecimal SMALLER_SALDO_CONSOLIDADO = new BigDecimal(1 - 1);

    private static final TipoSaldo DEFAULT_TIPO_INDICADOR_SALDO = TipoSaldo.POSITIVO;
    private static final TipoSaldo UPDATED_TIPO_INDICADOR_SALDO = TipoSaldo.NEGATIVO;

    private static final Boolean DEFAULT_INDICADOR_BLOQUEIO = false;
    private static final Boolean UPDATED_INDICADOR_BLOQUEIO = true;

    private static final Instant DEFAULT_DATA_HORA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_CADASTRO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_ATUALIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_ATUALIZACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carteira-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarteiraClienteRepository carteiraClienteRepository;

    @Mock
    private CarteiraClienteRepository carteiraClienteRepositoryMock;

    @Autowired
    private CarteiraClienteMapper carteiraClienteMapper;

    @Mock
    private CarteiraClienteService carteiraClienteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteiraClienteMockMvc;

    private CarteiraCliente carteiraCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteiraCliente createEntity(EntityManager em) {
        CarteiraCliente carteiraCliente = new CarteiraCliente()
            .nomeCarteiraCliente(DEFAULT_NOME_CARTEIRA_CLIENTE)
            .saldoConsolidado(DEFAULT_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(DEFAULT_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(DEFAULT_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return carteiraCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteiraCliente createUpdatedEntity(EntityManager em) {
        CarteiraCliente carteiraCliente = new CarteiraCliente()
            .nomeCarteiraCliente(UPDATED_NOME_CARTEIRA_CLIENTE)
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return carteiraCliente;
    }

    @BeforeEach
    public void initTest() {
        carteiraCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCarteiraCliente() throws Exception {
        int databaseSizeBeforeCreate = carteiraClienteRepository.findAll().size();
        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);
        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeCreate + 1);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getNomeCarteiraCliente()).isEqualTo(DEFAULT_NOME_CARTEIRA_CLIENTE);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(DEFAULT_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(DEFAULT_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createCarteiraClienteWithExistingId() throws Exception {
        // Create the CarteiraCliente with an existing ID
        carteiraCliente.setId(1L);
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        int databaseSizeBeforeCreate = carteiraClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeCarteiraClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteiraClienteRepository.findAll().size();
        // set the field null
        carteiraCliente.setNomeCarteiraCliente(null);

        // Create the CarteiraCliente, which fails.
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarteiraClientes() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteiraCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCarteiraCliente").value(hasItem(DEFAULT_NOME_CARTEIRA_CLIENTE)))
            .andExpect(jsonPath("$.[*].saldoConsolidado").value(hasItem(sameNumber(DEFAULT_SALDO_CONSOLIDADO))))
            .andExpect(jsonPath("$.[*].tipoIndicadorSaldo").value(hasItem(DEFAULT_TIPO_INDICADOR_SALDO.toString())))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarteiraClientesWithEagerRelationshipsIsEnabled() throws Exception {
        when(carteiraClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carteiraClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarteiraClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(carteiraClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carteiraClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get the carteiraCliente
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, carteiraCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteiraCliente.getId().intValue()))
            .andExpect(jsonPath("$.nomeCarteiraCliente").value(DEFAULT_NOME_CARTEIRA_CLIENTE))
            .andExpect(jsonPath("$.saldoConsolidado").value(sameNumber(DEFAULT_SALDO_CONSOLIDADO)))
            .andExpect(jsonPath("$.tipoIndicadorSaldo").value(DEFAULT_TIPO_INDICADOR_SALDO.toString()))
            .andExpect(jsonPath("$.indicadorBloqueio").value(DEFAULT_INDICADOR_BLOQUEIO.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getCarteiraClientesByIdFiltering() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        Long id = carteiraCliente.getId();

        defaultCarteiraClienteShouldBeFound("id.equals=" + id);
        defaultCarteiraClienteShouldNotBeFound("id.notEquals=" + id);

        defaultCarteiraClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCarteiraClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultCarteiraClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCarteiraClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente equals to DEFAULT_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldBeFound("nomeCarteiraCliente.equals=" + DEFAULT_NOME_CARTEIRA_CLIENTE);

        // Get all the carteiraClienteList where nomeCarteiraCliente equals to UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.equals=" + UPDATED_NOME_CARTEIRA_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente not equals to DEFAULT_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.notEquals=" + DEFAULT_NOME_CARTEIRA_CLIENTE);

        // Get all the carteiraClienteList where nomeCarteiraCliente not equals to UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldBeFound("nomeCarteiraCliente.notEquals=" + UPDATED_NOME_CARTEIRA_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente in DEFAULT_NOME_CARTEIRA_CLIENTE or UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldBeFound(
            "nomeCarteiraCliente.in=" + DEFAULT_NOME_CARTEIRA_CLIENTE + "," + UPDATED_NOME_CARTEIRA_CLIENTE
        );

        // Get all the carteiraClienteList where nomeCarteiraCliente equals to UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.in=" + UPDATED_NOME_CARTEIRA_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente is not null
        defaultCarteiraClienteShouldBeFound("nomeCarteiraCliente.specified=true");

        // Get all the carteiraClienteList where nomeCarteiraCliente is null
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente contains DEFAULT_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldBeFound("nomeCarteiraCliente.contains=" + DEFAULT_NOME_CARTEIRA_CLIENTE);

        // Get all the carteiraClienteList where nomeCarteiraCliente contains UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.contains=" + UPDATED_NOME_CARTEIRA_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByNomeCarteiraClienteNotContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where nomeCarteiraCliente does not contain DEFAULT_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldNotBeFound("nomeCarteiraCliente.doesNotContain=" + DEFAULT_NOME_CARTEIRA_CLIENTE);

        // Get all the carteiraClienteList where nomeCarteiraCliente does not contain UPDATED_NOME_CARTEIRA_CLIENTE
        defaultCarteiraClienteShouldBeFound("nomeCarteiraCliente.doesNotContain=" + UPDATED_NOME_CARTEIRA_CLIENTE);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado equals to DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.equals=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado equals to UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.equals=" + UPDATED_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado not equals to DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.notEquals=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado not equals to UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.notEquals=" + UPDATED_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado in DEFAULT_SALDO_CONSOLIDADO or UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.in=" + DEFAULT_SALDO_CONSOLIDADO + "," + UPDATED_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado equals to UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.in=" + UPDATED_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado is not null
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.specified=true");

        // Get all the carteiraClienteList where saldoConsolidado is null
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado is greater than or equal to DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.greaterThanOrEqual=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado is greater than or equal to UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.greaterThanOrEqual=" + UPDATED_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado is less than or equal to DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.lessThanOrEqual=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado is less than or equal to SMALLER_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.lessThanOrEqual=" + SMALLER_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsLessThanSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado is less than DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.lessThan=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado is less than UPDATED_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.lessThan=" + UPDATED_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesBySaldoConsolidadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where saldoConsolidado is greater than DEFAULT_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldNotBeFound("saldoConsolidado.greaterThan=" + DEFAULT_SALDO_CONSOLIDADO);

        // Get all the carteiraClienteList where saldoConsolidado is greater than SMALLER_SALDO_CONSOLIDADO
        defaultCarteiraClienteShouldBeFound("saldoConsolidado.greaterThan=" + SMALLER_SALDO_CONSOLIDADO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByTipoIndicadorSaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where tipoIndicadorSaldo equals to DEFAULT_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldBeFound("tipoIndicadorSaldo.equals=" + DEFAULT_TIPO_INDICADOR_SALDO);

        // Get all the carteiraClienteList where tipoIndicadorSaldo equals to UPDATED_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldNotBeFound("tipoIndicadorSaldo.equals=" + UPDATED_TIPO_INDICADOR_SALDO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByTipoIndicadorSaldoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where tipoIndicadorSaldo not equals to DEFAULT_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldNotBeFound("tipoIndicadorSaldo.notEquals=" + DEFAULT_TIPO_INDICADOR_SALDO);

        // Get all the carteiraClienteList where tipoIndicadorSaldo not equals to UPDATED_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldBeFound("tipoIndicadorSaldo.notEquals=" + UPDATED_TIPO_INDICADOR_SALDO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByTipoIndicadorSaldoIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where tipoIndicadorSaldo in DEFAULT_TIPO_INDICADOR_SALDO or UPDATED_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldBeFound("tipoIndicadorSaldo.in=" + DEFAULT_TIPO_INDICADOR_SALDO + "," + UPDATED_TIPO_INDICADOR_SALDO);

        // Get all the carteiraClienteList where tipoIndicadorSaldo equals to UPDATED_TIPO_INDICADOR_SALDO
        defaultCarteiraClienteShouldNotBeFound("tipoIndicadorSaldo.in=" + UPDATED_TIPO_INDICADOR_SALDO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByTipoIndicadorSaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where tipoIndicadorSaldo is not null
        defaultCarteiraClienteShouldBeFound("tipoIndicadorSaldo.specified=true");

        // Get all the carteiraClienteList where tipoIndicadorSaldo is null
        defaultCarteiraClienteShouldNotBeFound("tipoIndicadorSaldo.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByIndicadorBloqueioIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where indicadorBloqueio equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldBeFound("indicadorBloqueio.equals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the carteiraClienteList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldNotBeFound("indicadorBloqueio.equals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByIndicadorBloqueioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where indicadorBloqueio not equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldNotBeFound("indicadorBloqueio.notEquals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the carteiraClienteList where indicadorBloqueio not equals to UPDATED_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldBeFound("indicadorBloqueio.notEquals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByIndicadorBloqueioIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where indicadorBloqueio in DEFAULT_INDICADOR_BLOQUEIO or UPDATED_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldBeFound("indicadorBloqueio.in=" + DEFAULT_INDICADOR_BLOQUEIO + "," + UPDATED_INDICADOR_BLOQUEIO);

        // Get all the carteiraClienteList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultCarteiraClienteShouldNotBeFound("indicadorBloqueio.in=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByIndicadorBloqueioIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where indicadorBloqueio is not null
        defaultCarteiraClienteShouldBeFound("indicadorBloqueio.specified=true");

        // Get all the carteiraClienteList where indicadorBloqueio is null
        defaultCarteiraClienteShouldNotBeFound("indicadorBloqueio.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraCadastro equals to DEFAULT_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldBeFound("dataHoraCadastro.equals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the carteiraClienteList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("dataHoraCadastro.equals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraCadastro not equals to DEFAULT_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("dataHoraCadastro.notEquals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the carteiraClienteList where dataHoraCadastro not equals to UPDATED_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldBeFound("dataHoraCadastro.notEquals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraCadastro in DEFAULT_DATA_HORA_CADASTRO or UPDATED_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldBeFound("dataHoraCadastro.in=" + DEFAULT_DATA_HORA_CADASTRO + "," + UPDATED_DATA_HORA_CADASTRO);

        // Get all the carteiraClienteList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("dataHoraCadastro.in=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraCadastro is not null
        defaultCarteiraClienteShouldBeFound("dataHoraCadastro.specified=true");

        // Get all the carteiraClienteList where dataHoraCadastro is null
        defaultCarteiraClienteShouldNotBeFound("dataHoraCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro equals to DEFAULT_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.equals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the carteiraClienteList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.equals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro not equals to DEFAULT_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.notEquals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the carteiraClienteList where colaboradorCadastro not equals to UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.notEquals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro in DEFAULT_COLABORADOR_CADASTRO or UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.in=" + DEFAULT_COLABORADOR_CADASTRO + "," + UPDATED_COLABORADOR_CADASTRO);

        // Get all the carteiraClienteList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.in=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro is not null
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.specified=true");

        // Get all the carteiraClienteList where colaboradorCadastro is null
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro contains DEFAULT_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.contains=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the carteiraClienteList where colaboradorCadastro contains UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.contains=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorCadastro does not contain DEFAULT_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldNotBeFound("colaboradorCadastro.doesNotContain=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the carteiraClienteList where colaboradorCadastro does not contain UPDATED_COLABORADOR_CADASTRO
        defaultCarteiraClienteShouldBeFound("colaboradorCadastro.doesNotContain=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraAtualizacao equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("dataHoraAtualizacao.equals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the carteiraClienteList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.equals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraAtualizacao not equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.notEquals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the carteiraClienteList where dataHoraAtualizacao not equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("dataHoraAtualizacao.notEquals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraAtualizacao in DEFAULT_DATA_HORA_ATUALIZACAO or UPDATED_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound(
            "dataHoraAtualizacao.in=" + DEFAULT_DATA_HORA_ATUALIZACAO + "," + UPDATED_DATA_HORA_ATUALIZACAO
        );

        // Get all the carteiraClienteList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.in=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByDataHoraAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where dataHoraAtualizacao is not null
        defaultCarteiraClienteShouldBeFound("dataHoraAtualizacao.specified=true");

        // Get all the carteiraClienteList where dataHoraAtualizacao is null
        defaultCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("colaboradorAtualizacao.equals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the carteiraClienteList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.equals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao not equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.notEquals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the carteiraClienteList where colaboradorAtualizacao not equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("colaboradorAtualizacao.notEquals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao in DEFAULT_COLABORADOR_ATUALIZACAO or UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound(
            "colaboradorAtualizacao.in=" + DEFAULT_COLABORADOR_ATUALIZACAO + "," + UPDATED_COLABORADOR_ATUALIZACAO
        );

        // Get all the carteiraClienteList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.in=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao is not null
        defaultCarteiraClienteShouldBeFound("colaboradorAtualizacao.specified=true");

        // Get all the carteiraClienteList where colaboradorAtualizacao is null
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao contains DEFAULT_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("colaboradorAtualizacao.contains=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the carteiraClienteList where colaboradorAtualizacao contains UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.contains=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByColaboradorAtualizacaoNotContainsSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList where colaboradorAtualizacao does not contain DEFAULT_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.doesNotContain=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the carteiraClienteList where colaboradorAtualizacao does not contain UPDATED_COLABORADOR_ATUALIZACAO
        defaultCarteiraClienteShouldBeFound("colaboradorAtualizacao.doesNotContain=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllCarteiraClientesByLancamentoCarteiraClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);
        LancamentoCarteiraCliente lancamentoCarteiraCliente;
        if (TestUtil.findAll(em, LancamentoCarteiraCliente.class).isEmpty()) {
            lancamentoCarteiraCliente = LancamentoCarteiraClienteResourceIT.createEntity(em);
            em.persist(lancamentoCarteiraCliente);
            em.flush();
        } else {
            lancamentoCarteiraCliente = TestUtil.findAll(em, LancamentoCarteiraCliente.class).get(0);
        }
        em.persist(lancamentoCarteiraCliente);
        em.flush();
        carteiraCliente.addLancamentoCarteiraCliente(lancamentoCarteiraCliente);
        carteiraClienteRepository.saveAndFlush(carteiraCliente);
        Long lancamentoCarteiraClienteId = lancamentoCarteiraCliente.getId();

        // Get all the carteiraClienteList where lancamentoCarteiraCliente equals to lancamentoCarteiraClienteId
        defaultCarteiraClienteShouldBeFound("lancamentoCarteiraClienteId.equals=" + lancamentoCarteiraClienteId);

        // Get all the carteiraClienteList where lancamentoCarteiraCliente equals to (lancamentoCarteiraClienteId + 1)
        defaultCarteiraClienteShouldNotBeFound("lancamentoCarteiraClienteId.equals=" + (lancamentoCarteiraClienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCarteiraClienteShouldBeFound(String filter) throws Exception {
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteiraCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCarteiraCliente").value(hasItem(DEFAULT_NOME_CARTEIRA_CLIENTE)))
            .andExpect(jsonPath("$.[*].saldoConsolidado").value(hasItem(sameNumber(DEFAULT_SALDO_CONSOLIDADO))))
            .andExpect(jsonPath("$.[*].tipoIndicadorSaldo").value(hasItem(DEFAULT_TIPO_INDICADOR_SALDO.toString())))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));

        // Check, that the count call also returns 1
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCarteiraClienteShouldNotBeFound(String filter) throws Exception {
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCarteiraCliente() throws Exception {
        // Get the carteiraCliente
        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente
        CarteiraCliente updatedCarteiraCliente = carteiraClienteRepository.findById(carteiraCliente.getId()).get();
        // Disconnect from session so that the updates on updatedCarteiraCliente are not directly saved in db
        em.detach(updatedCarteiraCliente);
        updatedCarteiraCliente
            .nomeCarteiraCliente(UPDATED_NOME_CARTEIRA_CLIENTE)
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(updatedCarteiraCliente);

        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getNomeCarteiraCliente()).isEqualTo(UPDATED_NOME_CARTEIRA_CLIENTE);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(UPDATED_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(UPDATED_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente using partial update
        CarteiraCliente partialUpdatedCarteiraCliente = new CarteiraCliente();
        partialUpdatedCarteiraCliente.setId(carteiraCliente.getId());

        partialUpdatedCarteiraCliente
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getNomeCarteiraCliente()).isEqualTo(DEFAULT_NOME_CARTEIRA_CLIENTE);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(DEFAULT_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(DEFAULT_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdateCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente using partial update
        CarteiraCliente partialUpdatedCarteiraCliente = new CarteiraCliente();
        partialUpdatedCarteiraCliente.setId(carteiraCliente.getId());

        partialUpdatedCarteiraCliente
            .nomeCarteiraCliente(UPDATED_NOME_CARTEIRA_CLIENTE)
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getNomeCarteiraCliente()).isEqualTo(UPDATED_NOME_CARTEIRA_CLIENTE);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(UPDATED_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(UPDATED_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeDelete = carteiraClienteRepository.findAll().size();

        // Delete the carteiraCliente
        restCarteiraClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, carteiraCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
