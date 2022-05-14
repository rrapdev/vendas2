package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.repository.PlataformaPagamentoRepository;
import com.mycompany.myapp.service.criteria.PlataformaPagamentoCriteria;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import com.mycompany.myapp.service.mapper.PlataformaPagamentoMapper;
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
 * Integration tests for the {@link PlataformaPagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlataformaPagamentoResourceIT {

    private static final String DEFAULT_NOME_PLATAFORMA_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PLATAFORMA_PAGAMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICADOR_ATIVO = false;
    private static final Boolean UPDATED_INDICADOR_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/plataforma-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlataformaPagamentoRepository plataformaPagamentoRepository;

    @Autowired
    private PlataformaPagamentoMapper plataformaPagamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlataformaPagamentoMockMvc;

    private PlataformaPagamento plataformaPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlataformaPagamento createEntity(EntityManager em) {
        PlataformaPagamento plataformaPagamento = new PlataformaPagamento()
            .nomePlataformaPagamento(DEFAULT_NOME_PLATAFORMA_PAGAMENTO)
            .indicadorAtivo(DEFAULT_INDICADOR_ATIVO);
        return plataformaPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlataformaPagamento createUpdatedEntity(EntityManager em) {
        PlataformaPagamento plataformaPagamento = new PlataformaPagamento()
            .nomePlataformaPagamento(UPDATED_NOME_PLATAFORMA_PAGAMENTO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        return plataformaPagamento;
    }

    @BeforeEach
    public void initTest() {
        plataformaPagamento = createEntity(em);
    }

    @Test
    @Transactional
    void createPlataformaPagamento() throws Exception {
        int databaseSizeBeforeCreate = plataformaPagamentoRepository.findAll().size();
        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);
        restPlataformaPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        PlataformaPagamento testPlataformaPagamento = plataformaPagamentoList.get(plataformaPagamentoList.size() - 1);
        assertThat(testPlataformaPagamento.getNomePlataformaPagamento()).isEqualTo(DEFAULT_NOME_PLATAFORMA_PAGAMENTO);
        assertThat(testPlataformaPagamento.getIndicadorAtivo()).isEqualTo(DEFAULT_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void createPlataformaPagamentoWithExistingId() throws Exception {
        // Create the PlataformaPagamento with an existing ID
        plataformaPagamento.setId(1L);
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        int databaseSizeBeforeCreate = plataformaPagamentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlataformaPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomePlataformaPagamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = plataformaPagamentoRepository.findAll().size();
        // set the field null
        plataformaPagamento.setNomePlataformaPagamento(null);

        // Create the PlataformaPagamento, which fails.
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        restPlataformaPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentos() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plataformaPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePlataformaPagamento").value(hasItem(DEFAULT_NOME_PLATAFORMA_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getPlataformaPagamento() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get the plataformaPagamento
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, plataformaPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plataformaPagamento.getId().intValue()))
            .andExpect(jsonPath("$.nomePlataformaPagamento").value(DEFAULT_NOME_PLATAFORMA_PAGAMENTO))
            .andExpect(jsonPath("$.indicadorAtivo").value(DEFAULT_INDICADOR_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getPlataformaPagamentosByIdFiltering() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        Long id = plataformaPagamento.getId();

        defaultPlataformaPagamentoShouldBeFound("id.equals=" + id);
        defaultPlataformaPagamentoShouldNotBeFound("id.notEquals=" + id);

        defaultPlataformaPagamentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlataformaPagamentoShouldNotBeFound("id.greaterThan=" + id);

        defaultPlataformaPagamentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlataformaPagamentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento equals to DEFAULT_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldBeFound("nomePlataformaPagamento.equals=" + DEFAULT_NOME_PLATAFORMA_PAGAMENTO);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento equals to UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.equals=" + UPDATED_NOME_PLATAFORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento not equals to DEFAULT_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.notEquals=" + DEFAULT_NOME_PLATAFORMA_PAGAMENTO);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento not equals to UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldBeFound("nomePlataformaPagamento.notEquals=" + UPDATED_NOME_PLATAFORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento in DEFAULT_NOME_PLATAFORMA_PAGAMENTO or UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldBeFound(
            "nomePlataformaPagamento.in=" + DEFAULT_NOME_PLATAFORMA_PAGAMENTO + "," + UPDATED_NOME_PLATAFORMA_PAGAMENTO
        );

        // Get all the plataformaPagamentoList where nomePlataformaPagamento equals to UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.in=" + UPDATED_NOME_PLATAFORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento is not null
        defaultPlataformaPagamentoShouldBeFound("nomePlataformaPagamento.specified=true");

        // Get all the plataformaPagamentoList where nomePlataformaPagamento is null
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoContainsSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento contains DEFAULT_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldBeFound("nomePlataformaPagamento.contains=" + DEFAULT_NOME_PLATAFORMA_PAGAMENTO);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento contains UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.contains=" + UPDATED_NOME_PLATAFORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByNomePlataformaPagamentoNotContainsSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento does not contain DEFAULT_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldNotBeFound("nomePlataformaPagamento.doesNotContain=" + DEFAULT_NOME_PLATAFORMA_PAGAMENTO);

        // Get all the plataformaPagamentoList where nomePlataformaPagamento does not contain UPDATED_NOME_PLATAFORMA_PAGAMENTO
        defaultPlataformaPagamentoShouldBeFound("nomePlataformaPagamento.doesNotContain=" + UPDATED_NOME_PLATAFORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByIndicadorAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where indicadorAtivo equals to DEFAULT_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldBeFound("indicadorAtivo.equals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the plataformaPagamentoList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldNotBeFound("indicadorAtivo.equals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByIndicadorAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where indicadorAtivo not equals to DEFAULT_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldNotBeFound("indicadorAtivo.notEquals=" + DEFAULT_INDICADOR_ATIVO);

        // Get all the plataformaPagamentoList where indicadorAtivo not equals to UPDATED_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldBeFound("indicadorAtivo.notEquals=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByIndicadorAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where indicadorAtivo in DEFAULT_INDICADOR_ATIVO or UPDATED_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldBeFound("indicadorAtivo.in=" + DEFAULT_INDICADOR_ATIVO + "," + UPDATED_INDICADOR_ATIVO);

        // Get all the plataformaPagamentoList where indicadorAtivo equals to UPDATED_INDICADOR_ATIVO
        defaultPlataformaPagamentoShouldNotBeFound("indicadorAtivo.in=" + UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void getAllPlataformaPagamentosByIndicadorAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        // Get all the plataformaPagamentoList where indicadorAtivo is not null
        defaultPlataformaPagamentoShouldBeFound("indicadorAtivo.specified=true");

        // Get all the plataformaPagamentoList where indicadorAtivo is null
        defaultPlataformaPagamentoShouldNotBeFound("indicadorAtivo.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlataformaPagamentoShouldBeFound(String filter) throws Exception {
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plataformaPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePlataformaPagamento").value(hasItem(DEFAULT_NOME_PLATAFORMA_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].indicadorAtivo").value(hasItem(DEFAULT_INDICADOR_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlataformaPagamentoShouldNotBeFound(String filter) throws Exception {
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlataformaPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlataformaPagamento() throws Exception {
        // Get the plataformaPagamento
        restPlataformaPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlataformaPagamento() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();

        // Update the plataformaPagamento
        PlataformaPagamento updatedPlataformaPagamento = plataformaPagamentoRepository.findById(plataformaPagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPlataformaPagamento are not directly saved in db
        em.detach(updatedPlataformaPagamento);
        updatedPlataformaPagamento.nomePlataformaPagamento(UPDATED_NOME_PLATAFORMA_PAGAMENTO).indicadorAtivo(UPDATED_INDICADOR_ATIVO);
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(updatedPlataformaPagamento);

        restPlataformaPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plataformaPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
        PlataformaPagamento testPlataformaPagamento = plataformaPagamentoList.get(plataformaPagamentoList.size() - 1);
        assertThat(testPlataformaPagamento.getNomePlataformaPagamento()).isEqualTo(UPDATED_NOME_PLATAFORMA_PAGAMENTO);
        assertThat(testPlataformaPagamento.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plataformaPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlataformaPagamentoWithPatch() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();

        // Update the plataformaPagamento using partial update
        PlataformaPagamento partialUpdatedPlataformaPagamento = new PlataformaPagamento();
        partialUpdatedPlataformaPagamento.setId(plataformaPagamento.getId());

        partialUpdatedPlataformaPagamento.nomePlataformaPagamento(UPDATED_NOME_PLATAFORMA_PAGAMENTO);

        restPlataformaPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlataformaPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlataformaPagamento))
            )
            .andExpect(status().isOk());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
        PlataformaPagamento testPlataformaPagamento = plataformaPagamentoList.get(plataformaPagamentoList.size() - 1);
        assertThat(testPlataformaPagamento.getNomePlataformaPagamento()).isEqualTo(UPDATED_NOME_PLATAFORMA_PAGAMENTO);
        assertThat(testPlataformaPagamento.getIndicadorAtivo()).isEqualTo(DEFAULT_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdatePlataformaPagamentoWithPatch() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();

        // Update the plataformaPagamento using partial update
        PlataformaPagamento partialUpdatedPlataformaPagamento = new PlataformaPagamento();
        partialUpdatedPlataformaPagamento.setId(plataformaPagamento.getId());

        partialUpdatedPlataformaPagamento
            .nomePlataformaPagamento(UPDATED_NOME_PLATAFORMA_PAGAMENTO)
            .indicadorAtivo(UPDATED_INDICADOR_ATIVO);

        restPlataformaPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlataformaPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlataformaPagamento))
            )
            .andExpect(status().isOk());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
        PlataformaPagamento testPlataformaPagamento = plataformaPagamentoList.get(plataformaPagamentoList.size() - 1);
        assertThat(testPlataformaPagamento.getNomePlataformaPagamento()).isEqualTo(UPDATED_NOME_PLATAFORMA_PAGAMENTO);
        assertThat(testPlataformaPagamento.getIndicadorAtivo()).isEqualTo(UPDATED_INDICADOR_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plataformaPagamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlataformaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = plataformaPagamentoRepository.findAll().size();
        plataformaPagamento.setId(count.incrementAndGet());

        // Create the PlataformaPagamento
        PlataformaPagamentoDTO plataformaPagamentoDTO = plataformaPagamentoMapper.toDto(plataformaPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlataformaPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plataformaPagamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlataformaPagamento in the database
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlataformaPagamento() throws Exception {
        // Initialize the database
        plataformaPagamentoRepository.saveAndFlush(plataformaPagamento);

        int databaseSizeBeforeDelete = plataformaPagamentoRepository.findAll().size();

        // Delete the plataformaPagamento
        restPlataformaPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, plataformaPagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlataformaPagamento> plataformaPagamentoList = plataformaPagamentoRepository.findAll();
        assertThat(plataformaPagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
