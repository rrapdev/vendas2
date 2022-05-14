package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.ColaboradorRepository;
import com.mycompany.myapp.service.criteria.ColaboradorCriteria;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import com.mycompany.myapp.service.mapper.ColaboradorMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ColaboradorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColaboradorResourceIT {

    private static final String DEFAULT_NOME_COLABORADOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COLABORADOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_APRESENTACAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_APRESENTACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICADOR_ATIVO = false;
    private static final Boolean UPDATED_INDICADOR_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/colaboradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ColaboradorMapper colaboradorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColaboradorMockMvc;

    private Colaborador colaborador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador()
            .nomeColaborador(DEFAULT_NOME_COLABORADOR)
            .nomeApresentacao(DEFAULT_NOME_APRESENTACAO)
            .indicadorAtivo(DEFAULT_INDICADOR_ATIVO);
        return colaborador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createUpdatedEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador()
            .nomeColaborador(UPDATED_NOME_COLABORADOR)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        return colaborador;
    }

    @BeforeEach
    public void initTest() {
        colaborador = createEntity(em);
    }

    @Test
    @Transactional
    void createColaborador() throws Exception {
        int databaseSizeBeforeCreate = colaboradorRepository.findAll().size();
        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);
        restColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeCreate + 1);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getNomeColaborador()).isEqualTo(DEFAULT_NOME_COLABORADOR);
        assertThat(testColaborador.getNomeApresentacao()).isEqualTo(DEFAULT_NOME_APRESENTACAO);
        assertThat(testColaborador.getIndicadorAtivo()).isEqualTo(DEFAULT_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void createColaboradorWithExistingId() throws Exception {
        // Create the Colaborador with an existing ID
        colaborador.setId(1L);
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        int databaseSizeBeforeCreate = colaboradorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeColaboradorIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradorRepository.findAll().size();
        // set the field null
        colaborador.setNomeColaborador(null);

        // Create the Colaborador, which fails.
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        restColaboradorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllColaboradors() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeColaborador").value(hasItem(DEFAULT_NOME_COLABORADOR)))
            .andExpect(jsonPath("$.[*].nomeApresentacao").value(hasItem(DEFAULT_NOME_APRESENTACAO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get the colaborador
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL_ID, colaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colaborador.getId().intValue()))
            .andExpect(jsonPath("$.nomeColaborador").value(DEFAULT_NOME_COLABORADOR))
            .andExpect(jsonPath("$.nomeApresentacao").value(DEFAULT_NOME_APRESENTACAO))
            .andExpect(jsonPath("$.indicadorAtivo").value(DEFAULT_INDICADOR_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getColaboradorsByIdFiltering() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        Long id = colaborador.getId();

        defaultColaboradorShouldBeFound("id.equals=" + id);
        defaultColaboradorShouldNotBeFound("id.notEquals=" + id);

        defaultColaboradorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultColaboradorShouldNotBeFound("id.greaterThan=" + id);

        defaultColaboradorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultColaboradorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorIsEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador equals to DEFAULT_NOME_COLABORADOR
        defaultColaboradorShouldBeFound("nomeColaborador.equals=" + DEFAULT_NOME_COLABORADOR);

        // Get all the colaboradorList where nomeColaborador equals to UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldNotBeFound("nomeColaborador.equals=" + UPDATED_NOME_COLABORADOR);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador not equals to DEFAULT_NOME_COLABORADOR
        defaultColaboradorShouldNotBeFound("nomeColaborador.notEquals=" + DEFAULT_NOME_COLABORADOR);

        // Get all the colaboradorList where nomeColaborador not equals to UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldBeFound("nomeColaborador.notEquals=" + UPDATED_NOME_COLABORADOR);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorIsInShouldWork() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador in DEFAULT_NOME_COLABORADOR or UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldBeFound("nomeColaborador.in=" + DEFAULT_NOME_COLABORADOR + "," + UPDATED_NOME_COLABORADOR);

        // Get all the colaboradorList where nomeColaborador equals to UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldNotBeFound("nomeColaborador.in=" + UPDATED_NOME_COLABORADOR);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorIsNullOrNotNull() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador is not null
        defaultColaboradorShouldBeFound("nomeColaborador.specified=true");

        // Get all the colaboradorList where nomeColaborador is null
        defaultColaboradorShouldNotBeFound("nomeColaborador.specified=false");
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorContainsSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador contains DEFAULT_NOME_COLABORADOR
        defaultColaboradorShouldBeFound("nomeColaborador.contains=" + DEFAULT_NOME_COLABORADOR);

        // Get all the colaboradorList where nomeColaborador contains UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldNotBeFound("nomeColaborador.contains=" + UPDATED_NOME_COLABORADOR);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeColaboradorNotContainsSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeColaborador does not contain DEFAULT_NOME_COLABORADOR
        defaultColaboradorShouldNotBeFound("nomeColaborador.doesNotContain=" + DEFAULT_NOME_COLABORADOR);

        // Get all the colaboradorList where nomeColaborador does not contain UPDATED_NOME_COLABORADOR
        defaultColaboradorShouldBeFound("nomeColaborador.doesNotContain=" + UPDATED_NOME_COLABORADOR);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao equals to DEFAULT_NOME_APRESENTACAO
        defaultColaboradorShouldBeFound("nomeApresentacao.equals=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the colaboradorList where nomeApresentacao equals to UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldNotBeFound("nomeApresentacao.equals=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao not equals to DEFAULT_NOME_APRESENTACAO
        defaultColaboradorShouldNotBeFound("nomeApresentacao.notEquals=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the colaboradorList where nomeApresentacao not equals to UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldBeFound("nomeApresentacao.notEquals=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoIsInShouldWork() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao in DEFAULT_NOME_APRESENTACAO or UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldBeFound("nomeApresentacao.in=" + DEFAULT_NOME_APRESENTACAO + "," + UPDATED_NOME_APRESENTACAO);

        // Get all the colaboradorList where nomeApresentacao equals to UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldNotBeFound("nomeApresentacao.in=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao is not null
        defaultColaboradorShouldBeFound("nomeApresentacao.specified=true");

        // Get all the colaboradorList where nomeApresentacao is null
        defaultColaboradorShouldNotBeFound("nomeApresentacao.specified=false");
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoContainsSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao contains DEFAULT_NOME_APRESENTACAO
        defaultColaboradorShouldBeFound("nomeApresentacao.contains=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the colaboradorList where nomeApresentacao contains UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldNotBeFound("nomeApresentacao.contains=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByNomeApresentacaoNotContainsSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where nomeApresentacao does not contain DEFAULT_NOME_APRESENTACAO
        defaultColaboradorShouldNotBeFound("nomeApresentacao.doesNotContain=" + DEFAULT_NOME_APRESENTACAO);

        // Get all the colaboradorList where nomeApresentacao does not contain UPDATED_NOME_APRESENTACAO
        defaultColaboradorShouldBeFound("nomeApresentacao.doesNotContain=" + UPDATED_NOME_APRESENTACAO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByIndicadorAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where indicadorAtivo equals to DEFAULT_INDICADOR_ATIVO
        defaultColaboradorShouldBeFound("indicadorAtivo.equals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the colaboradorList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultColaboradorShouldNotBeFound("indicadorAtivo.equals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByIndicadorAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where indicadorAtivo not equals to DEFAULT_INDICADOR_ATIVO
        defaultColaboradorShouldNotBeFound("indicadorAtivo.notEquals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the colaboradorList where indicadorAtivo not equals to UPDATED_INDICADOR_ATIVO
        defaultColaboradorShouldBeFound("indicadorAtivo.notEquals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByIndicadorAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where indicadorAtivo in DEFAULT_INDICADOR_ATIVO or UPDATED_INDICADOR_ATIVO
        defaultColaboradorShouldBeFound("indicadorAtivo.in=" + DEFAULT_INDICADOR_ATIVO + "," + UPDATED_INDICADOR_ATIVO);

        // Get all the colaboradorList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultColaboradorShouldNotBeFound("indicadorAtivo.in=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllColaboradorsByIndicadorAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList where indicadorAtivo is not null
        defaultColaboradorShouldBeFound("indicadorAtivo.specified=true");

        // Get all the colaboradorList where indicadorAtivo is null
        defaultColaboradorShouldNotBeFound("indicadorAtivo.specified=false");
    }

    @Test
    @Transactional
    void getAllColaboradorsByVendasIsEqualToSomething() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);
        Venda vendas;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            vendas = VendaResourceIT.createEntity(em);
            em.persist(vendas);
            em.flush();
        } else {
            vendas = TestUtil.findAll(em, Venda.class).get(0);
        }
        em.persist(vendas);
        em.flush();
        colaborador.addVendas(vendas);
        colaboradorRepository.saveAndFlush(colaborador);
        Long vendasId = vendas.getId();

        // Get all the colaboradorList where vendas equals to vendasId
        defaultColaboradorShouldBeFound("vendasId.equals=" + vendasId);

        // Get all the colaboradorList where vendas equals to (vendasId + 1)
        defaultColaboradorShouldNotBeFound("vendasId.equals=" + (vendasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultColaboradorShouldBeFound(String filter) throws Exception {
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeColaborador").value(hasItem(DEFAULT_NOME_COLABORADOR)))
            .andExpect(jsonPath("$.[*].nomeApresentacao").value(hasItem(DEFAULT_NOME_APRESENTACAO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultColaboradorShouldNotBeFound(String filter) throws Exception {
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingColaborador() throws Exception {
        // Get the colaborador
        restColaboradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador
        Colaborador updatedColaborador = colaboradorRepository.findById(colaborador.getId()).get();
        // Disconnect from session so that the updates on updatedColaborador are not directly saved in db
        em.detach(updatedColaborador);
        updatedColaborador
            .nomeColaborador(UPDATED_NOME_COLABORADOR)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(updatedColaborador);

        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getNomeColaborador()).isEqualTo(UPDATED_NOME_COLABORADOR);
        assertThat(testColaborador.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testColaborador.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colaboradorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador
            .nomeColaborador(UPDATED_NOME_COLABORADOR)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getNomeColaborador()).isEqualTo(UPDATED_NOME_COLABORADOR);
        assertThat(testColaborador.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testColaborador.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador
            .nomeColaborador(UPDATED_NOME_COLABORADOR)
            .nomeApresentacao(UPDATED_NOME_APRESENTACAO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
        Colaborador testColaborador = colaboradorList.get(colaboradorList.size() - 1);
        assertThat(testColaborador.getNomeColaborador()).isEqualTo(UPDATED_NOME_COLABORADOR);
        assertThat(testColaborador.getNomeApresentacao()).isEqualTo(UPDATED_NOME_APRESENTACAO);
        assertThat(testColaborador.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamColaborador() throws Exception {
        int databaseSizeBeforeUpdate = colaboradorRepository.findAll().size();
        colaborador.setId(count.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(colaboradorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteColaborador() throws Exception {
        // Initialize the database
        colaboradorRepository.saveAndFlush(colaborador);

        int databaseSizeBeforeDelete = colaboradorRepository.findAll().size();

        // Delete the colaborador
        restColaboradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, colaborador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colaborador> colaboradorList = colaboradorRepository.findAll();
        assertThat(colaboradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
