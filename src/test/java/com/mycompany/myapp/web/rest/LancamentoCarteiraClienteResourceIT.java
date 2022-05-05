package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.repository.LancamentoCarteiraClienteRepository;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.LancamentoCarteiraClienteMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link LancamentoCarteiraClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LancamentoCarteiraClienteResourceIT {

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO_LANCAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_LANCAMENTO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_CREDITO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CREDITO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_DEBITO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DEBITO = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/lancamento-carteira-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository;

    @Autowired
    private LancamentoCarteiraClienteMapper lancamentoCarteiraClienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLancamentoCarteiraClienteMockMvc;

    private LancamentoCarteiraCliente lancamentoCarteiraCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LancamentoCarteiraCliente createEntity(EntityManager em) {
        LancamentoCarteiraCliente lancamentoCarteiraCliente = new LancamentoCarteiraCliente()
            .dataHora(DEFAULT_DATA_HORA)
            .descricaoLancamento(DEFAULT_DESCRICAO_LANCAMENTO)
            .valorCredito(DEFAULT_VALOR_CREDITO)
            .valorDebito(DEFAULT_VALOR_DEBITO)
            .observacoes(DEFAULT_OBSERVACOES)
            .indicadorBloqueio(DEFAULT_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return lancamentoCarteiraCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LancamentoCarteiraCliente createUpdatedEntity(EntityManager em) {
        LancamentoCarteiraCliente lancamentoCarteiraCliente = new LancamentoCarteiraCliente()
            .dataHora(UPDATED_DATA_HORA)
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorDebito(UPDATED_VALOR_DEBITO)
            .observacoes(UPDATED_OBSERVACOES)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return lancamentoCarteiraCliente;
    }

    @BeforeEach
    public void initTest() {
        lancamentoCarteiraCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeCreate = lancamentoCarteiraClienteRepository.findAll().size();
        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);
        restLancamentoCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeCreate + 1);
        LancamentoCarteiraCliente testLancamentoCarteiraCliente = lancamentoCarteiraClienteList.get(
            lancamentoCarteiraClienteList.size() - 1
        );
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(DEFAULT_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getValorCredito()).isEqualByComparingTo(DEFAULT_VALOR_CREDITO);
        assertThat(testLancamentoCarteiraCliente.getValorDebito()).isEqualByComparingTo(DEFAULT_VALOR_DEBITO);
        assertThat(testLancamentoCarteiraCliente.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testLancamentoCarteiraCliente.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createLancamentoCarteiraClienteWithExistingId() throws Exception {
        // Create the LancamentoCarteiraCliente with an existing ID
        lancamentoCarteiraCliente.setId(1L);
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        int databaseSizeBeforeCreate = lancamentoCarteiraClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCarteiraClienteRepository.findAll().size();
        // set the field null
        lancamentoCarteiraCliente.setDataHora(null);

        // Create the LancamentoCarteiraCliente, which fails.
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        restLancamentoCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoLancamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCarteiraClienteRepository.findAll().size();
        // set the field null
        lancamentoCarteiraCliente.setDescricaoLancamento(null);

        // Create the LancamentoCarteiraCliente, which fails.
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        restLancamentoCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientes() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoCarteiraCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].descricaoLancamento").value(hasItem(DEFAULT_DESCRICAO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].valorCredito").value(hasItem(sameNumber(DEFAULT_VALOR_CREDITO))))
            .andExpect(jsonPath("$.[*].valorDebito").value(hasItem(sameNumber(DEFAULT_VALOR_DEBITO))))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @Test
    @Transactional
    void getLancamentoCarteiraCliente() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get the lancamentoCarteiraCliente
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, lancamentoCarteiraCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lancamentoCarteiraCliente.getId().intValue()))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()))
            .andExpect(jsonPath("$.descricaoLancamento").value(DEFAULT_DESCRICAO_LANCAMENTO))
            .andExpect(jsonPath("$.valorCredito").value(sameNumber(DEFAULT_VALOR_CREDITO)))
            .andExpect(jsonPath("$.valorDebito").value(sameNumber(DEFAULT_VALOR_DEBITO)))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.indicadorBloqueio").value(DEFAULT_INDICADOR_BLOQUEIO.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getNonExistingLancamentoCarteiraCliente() throws Exception {
        // Get the lancamentoCarteiraCliente
        restLancamentoCarteiraClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLancamentoCarteiraCliente() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();

        // Update the lancamentoCarteiraCliente
        LancamentoCarteiraCliente updatedLancamentoCarteiraCliente = lancamentoCarteiraClienteRepository
            .findById(lancamentoCarteiraCliente.getId())
            .get();
        // Disconnect from session so that the updates on updatedLancamentoCarteiraCliente are not directly saved in db
        em.detach(updatedLancamentoCarteiraCliente);
        updatedLancamentoCarteiraCliente
            .dataHora(UPDATED_DATA_HORA)
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorDebito(UPDATED_VALOR_DEBITO)
            .observacoes(UPDATED_OBSERVACOES)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(updatedLancamentoCarteiraCliente);

        restLancamentoCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lancamentoCarteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        LancamentoCarteiraCliente testLancamentoCarteiraCliente = lancamentoCarteiraClienteList.get(
            lancamentoCarteiraClienteList.size() - 1
        );
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(UPDATED_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getValorCredito()).isEqualByComparingTo(UPDATED_VALOR_CREDITO);
        assertThat(testLancamentoCarteiraCliente.getValorDebito()).isEqualByComparingTo(UPDATED_VALOR_DEBITO);
        assertThat(testLancamentoCarteiraCliente.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testLancamentoCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lancamentoCarteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLancamentoCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();

        // Update the lancamentoCarteiraCliente using partial update
        LancamentoCarteiraCliente partialUpdatedLancamentoCarteiraCliente = new LancamentoCarteiraCliente();
        partialUpdatedLancamentoCarteiraCliente.setId(lancamentoCarteiraCliente.getId());

        partialUpdatedLancamentoCarteiraCliente
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorDebito(UPDATED_VALOR_DEBITO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO);

        restLancamentoCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLancamentoCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLancamentoCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        LancamentoCarteiraCliente testLancamentoCarteiraCliente = lancamentoCarteiraClienteList.get(
            lancamentoCarteiraClienteList.size() - 1
        );
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(UPDATED_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getValorCredito()).isEqualByComparingTo(UPDATED_VALOR_CREDITO);
        assertThat(testLancamentoCarteiraCliente.getValorDebito()).isEqualByComparingTo(UPDATED_VALOR_DEBITO);
        assertThat(testLancamentoCarteiraCliente.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testLancamentoCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdateLancamentoCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();

        // Update the lancamentoCarteiraCliente using partial update
        LancamentoCarteiraCliente partialUpdatedLancamentoCarteiraCliente = new LancamentoCarteiraCliente();
        partialUpdatedLancamentoCarteiraCliente.setId(lancamentoCarteiraCliente.getId());

        partialUpdatedLancamentoCarteiraCliente
            .dataHora(UPDATED_DATA_HORA)
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .valorCredito(UPDATED_VALOR_CREDITO)
            .valorDebito(UPDATED_VALOR_DEBITO)
            .observacoes(UPDATED_OBSERVACOES)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restLancamentoCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLancamentoCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLancamentoCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        LancamentoCarteiraCliente testLancamentoCarteiraCliente = lancamentoCarteiraClienteList.get(
            lancamentoCarteiraClienteList.size() - 1
        );
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(UPDATED_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getValorCredito()).isEqualByComparingTo(UPDATED_VALOR_CREDITO);
        assertThat(testLancamentoCarteiraCliente.getValorDebito()).isEqualByComparingTo(UPDATED_VALOR_DEBITO);
        assertThat(testLancamentoCarteiraCliente.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testLancamentoCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testLancamentoCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testLancamentoCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lancamentoCarteiraClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLancamentoCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCarteiraClienteRepository.findAll().size();
        lancamentoCarteiraCliente.setId(count.incrementAndGet());

        // Create the LancamentoCarteiraCliente
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLancamentoCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lancamentoCarteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LancamentoCarteiraCliente in the database
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLancamentoCarteiraCliente() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        int databaseSizeBeforeDelete = lancamentoCarteiraClienteRepository.findAll().size();

        // Delete the lancamentoCarteiraCliente
        restLancamentoCarteiraClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, lancamentoCarteiraCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LancamentoCarteiraCliente> lancamentoCarteiraClienteList = lancamentoCarteiraClienteRepository.findAll();
        assertThat(lancamentoCarteiraClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
