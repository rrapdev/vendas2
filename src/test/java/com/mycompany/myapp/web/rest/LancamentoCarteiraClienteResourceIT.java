package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.LancamentoCarteiraClienteRepository;
import com.mycompany.myapp.service.criteria.LancamentoCarteiraClienteCriteria;
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

    private static final String DEFAULT_DESCRICAO_LANCAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_LANCAMENTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR_CREDITO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CREDITO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_CREDITO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_DEBITO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DEBITO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_DEBITO = new BigDecimal(1 - 1);

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
            .descricaoLancamento(DEFAULT_DESCRICAO_LANCAMENTO)
            .dataHora(DEFAULT_DATA_HORA)
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
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .dataHora(UPDATED_DATA_HORA)
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
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(DEFAULT_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
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
            .andExpect(jsonPath("$.[*].descricaoLancamento").value(hasItem(DEFAULT_DESCRICAO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
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
            .andExpect(jsonPath("$.descricaoLancamento").value(DEFAULT_DESCRICAO_LANCAMENTO))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()))
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
    void getLancamentoCarteiraClientesByIdFiltering() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        Long id = lancamentoCarteiraCliente.getId();

        defaultLancamentoCarteiraClienteShouldBeFound("id.equals=" + id);
        defaultLancamentoCarteiraClienteShouldNotBeFound("id.notEquals=" + id);

        defaultLancamentoCarteiraClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLancamentoCarteiraClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultLancamentoCarteiraClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLancamentoCarteiraClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento equals to DEFAULT_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldBeFound("descricaoLancamento.equals=" + DEFAULT_DESCRICAO_LANCAMENTO);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento equals to UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.equals=" + UPDATED_DESCRICAO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento not equals to DEFAULT_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.notEquals=" + DEFAULT_DESCRICAO_LANCAMENTO);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento not equals to UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldBeFound("descricaoLancamento.notEquals=" + UPDATED_DESCRICAO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento in DEFAULT_DESCRICAO_LANCAMENTO or UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "descricaoLancamento.in=" + DEFAULT_DESCRICAO_LANCAMENTO + "," + UPDATED_DESCRICAO_LANCAMENTO
        );

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento equals to UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.in=" + UPDATED_DESCRICAO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento is not null
        defaultLancamentoCarteiraClienteShouldBeFound("descricaoLancamento.specified=true");

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento contains DEFAULT_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldBeFound("descricaoLancamento.contains=" + DEFAULT_DESCRICAO_LANCAMENTO);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento contains UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.contains=" + UPDATED_DESCRICAO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDescricaoLancamentoNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento does not contain DEFAULT_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldNotBeFound("descricaoLancamento.doesNotContain=" + DEFAULT_DESCRICAO_LANCAMENTO);

        // Get all the lancamentoCarteiraClienteList where descricaoLancamento does not contain UPDATED_DESCRICAO_LANCAMENTO
        defaultLancamentoCarteiraClienteShouldBeFound("descricaoLancamento.doesNotContain=" + UPDATED_DESCRICAO_LANCAMENTO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHora equals to DEFAULT_DATA_HORA
        defaultLancamentoCarteiraClienteShouldBeFound("dataHora.equals=" + DEFAULT_DATA_HORA);

        // Get all the lancamentoCarteiraClienteList where dataHora equals to UPDATED_DATA_HORA
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHora.equals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHora not equals to DEFAULT_DATA_HORA
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHora.notEquals=" + DEFAULT_DATA_HORA);

        // Get all the lancamentoCarteiraClienteList where dataHora not equals to UPDATED_DATA_HORA
        defaultLancamentoCarteiraClienteShouldBeFound("dataHora.notEquals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHora in DEFAULT_DATA_HORA or UPDATED_DATA_HORA
        defaultLancamentoCarteiraClienteShouldBeFound("dataHora.in=" + DEFAULT_DATA_HORA + "," + UPDATED_DATA_HORA);

        // Get all the lancamentoCarteiraClienteList where dataHora equals to UPDATED_DATA_HORA
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHora.in=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHora is not null
        defaultLancamentoCarteiraClienteShouldBeFound("dataHora.specified=true");

        // Get all the lancamentoCarteiraClienteList where dataHora is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHora.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito equals to DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.equals=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito equals to UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.equals=" + UPDATED_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito not equals to DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.notEquals=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito not equals to UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.notEquals=" + UPDATED_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito in DEFAULT_VALOR_CREDITO or UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.in=" + DEFAULT_VALOR_CREDITO + "," + UPDATED_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito equals to UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.in=" + UPDATED_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito is not null
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.specified=true");

        // Get all the lancamentoCarteiraClienteList where valorCredito is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito is greater than or equal to DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.greaterThanOrEqual=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito is greater than or equal to UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.greaterThanOrEqual=" + UPDATED_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito is less than or equal to DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.lessThanOrEqual=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito is less than or equal to SMALLER_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.lessThanOrEqual=" + SMALLER_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito is less than DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.lessThan=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito is less than UPDATED_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.lessThan=" + UPDATED_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorCreditoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorCredito is greater than DEFAULT_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorCredito.greaterThan=" + DEFAULT_VALOR_CREDITO);

        // Get all the lancamentoCarteiraClienteList where valorCredito is greater than SMALLER_VALOR_CREDITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorCredito.greaterThan=" + SMALLER_VALOR_CREDITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito equals to DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.equals=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito equals to UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.equals=" + UPDATED_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito not equals to DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.notEquals=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito not equals to UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.notEquals=" + UPDATED_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito in DEFAULT_VALOR_DEBITO or UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.in=" + DEFAULT_VALOR_DEBITO + "," + UPDATED_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito equals to UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.in=" + UPDATED_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito is not null
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.specified=true");

        // Get all the lancamentoCarteiraClienteList where valorDebito is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito is greater than or equal to DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.greaterThanOrEqual=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito is greater than or equal to UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.greaterThanOrEqual=" + UPDATED_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito is less than or equal to DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.lessThanOrEqual=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito is less than or equal to SMALLER_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.lessThanOrEqual=" + SMALLER_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito is less than DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.lessThan=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito is less than UPDATED_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.lessThan=" + UPDATED_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByValorDebitoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where valorDebito is greater than DEFAULT_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldNotBeFound("valorDebito.greaterThan=" + DEFAULT_VALOR_DEBITO);

        // Get all the lancamentoCarteiraClienteList where valorDebito is greater than SMALLER_VALOR_DEBITO
        defaultLancamentoCarteiraClienteShouldBeFound("valorDebito.greaterThan=" + SMALLER_VALOR_DEBITO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes equals to DEFAULT_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.equals=" + DEFAULT_OBSERVACOES);

        // Get all the lancamentoCarteiraClienteList where observacoes equals to UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.equals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes not equals to DEFAULT_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.notEquals=" + DEFAULT_OBSERVACOES);

        // Get all the lancamentoCarteiraClienteList where observacoes not equals to UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.notEquals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes in DEFAULT_OBSERVACOES or UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.in=" + DEFAULT_OBSERVACOES + "," + UPDATED_OBSERVACOES);

        // Get all the lancamentoCarteiraClienteList where observacoes equals to UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.in=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes is not null
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.specified=true");

        // Get all the lancamentoCarteiraClienteList where observacoes is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes contains DEFAULT_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.contains=" + DEFAULT_OBSERVACOES);

        // Get all the lancamentoCarteiraClienteList where observacoes contains UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.contains=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByObservacoesNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where observacoes does not contain DEFAULT_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldNotBeFound("observacoes.doesNotContain=" + DEFAULT_OBSERVACOES);

        // Get all the lancamentoCarteiraClienteList where observacoes does not contain UPDATED_OBSERVACOES
        defaultLancamentoCarteiraClienteShouldBeFound("observacoes.doesNotContain=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByIndicadorBloqueioIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldBeFound("indicadorBloqueio.equals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldNotBeFound("indicadorBloqueio.equals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByIndicadorBloqueioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio not equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldNotBeFound("indicadorBloqueio.notEquals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio not equals to UPDATED_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldBeFound("indicadorBloqueio.notEquals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByIndicadorBloqueioIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio in DEFAULT_INDICADOR_BLOQUEIO or UPDATED_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "indicadorBloqueio.in=" + DEFAULT_INDICADOR_BLOQUEIO + "," + UPDATED_INDICADOR_BLOQUEIO
        );

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultLancamentoCarteiraClienteShouldNotBeFound("indicadorBloqueio.in=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByIndicadorBloqueioIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio is not null
        defaultLancamentoCarteiraClienteShouldBeFound("indicadorBloqueio.specified=true");

        // Get all the lancamentoCarteiraClienteList where indicadorBloqueio is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("indicadorBloqueio.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro equals to DEFAULT_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraCadastro.equals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraCadastro.equals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro not equals to DEFAULT_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraCadastro.notEquals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro not equals to UPDATED_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraCadastro.notEquals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro in DEFAULT_DATA_HORA_CADASTRO or UPDATED_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "dataHoraCadastro.in=" + DEFAULT_DATA_HORA_CADASTRO + "," + UPDATED_DATA_HORA_CADASTRO
        );

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraCadastro.in=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro is not null
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraCadastro.specified=true");

        // Get all the lancamentoCarteiraClienteList where dataHoraCadastro is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro equals to DEFAULT_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorCadastro.equals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.equals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro not equals to DEFAULT_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.notEquals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro not equals to UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorCadastro.notEquals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro in DEFAULT_COLABORADOR_CADASTRO or UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "colaboradorCadastro.in=" + DEFAULT_COLABORADOR_CADASTRO + "," + UPDATED_COLABORADOR_CADASTRO
        );

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.in=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro is not null
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorCadastro.specified=true");

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro contains DEFAULT_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorCadastro.contains=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro contains UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.contains=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro does not contain DEFAULT_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorCadastro.doesNotContain=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the lancamentoCarteiraClienteList where colaboradorCadastro does not contain UPDATED_COLABORADOR_CADASTRO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorCadastro.doesNotContain=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraAtualizacao.equals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.equals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao not equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.notEquals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao not equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraAtualizacao.notEquals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao in DEFAULT_DATA_HORA_ATUALIZACAO or UPDATED_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "dataHoraAtualizacao.in=" + DEFAULT_DATA_HORA_ATUALIZACAO + "," + UPDATED_DATA_HORA_ATUALIZACAO
        );

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.in=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByDataHoraAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao is not null
        defaultLancamentoCarteiraClienteShouldBeFound("dataHoraAtualizacao.specified=true");

        // Get all the lancamentoCarteiraClienteList where dataHoraAtualizacao is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("dataHoraAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorAtualizacao.equals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.equals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao not equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.notEquals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao not equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorAtualizacao.notEquals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao in DEFAULT_COLABORADOR_ATUALIZACAO or UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound(
            "colaboradorAtualizacao.in=" + DEFAULT_COLABORADOR_ATUALIZACAO + "," + UPDATED_COLABORADOR_ATUALIZACAO
        );

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.in=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao is not null
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorAtualizacao.specified=true");

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao is null
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao contains DEFAULT_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorAtualizacao.contains=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao contains UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.contains=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByColaboradorAtualizacaoNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao does not contain DEFAULT_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldNotBeFound("colaboradorAtualizacao.doesNotContain=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the lancamentoCarteiraClienteList where colaboradorAtualizacao does not contain UPDATED_COLABORADOR_ATUALIZACAO
        defaultLancamentoCarteiraClienteShouldBeFound("colaboradorAtualizacao.doesNotContain=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        Venda venda;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            venda = VendaResourceIT.createEntity(em);
            em.persist(venda);
            em.flush();
        } else {
            venda = TestUtil.findAll(em, Venda.class).get(0);
        }
        em.persist(venda);
        em.flush();
        lancamentoCarteiraCliente.setVenda(venda);
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        Long vendaId = venda.getId();

        // Get all the lancamentoCarteiraClienteList where venda equals to vendaId
        defaultLancamentoCarteiraClienteShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the lancamentoCarteiraClienteList where venda equals to (vendaId + 1)
        defaultLancamentoCarteiraClienteShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        Pagamento pagamento;
        if (TestUtil.findAll(em, Pagamento.class).isEmpty()) {
            pagamento = PagamentoResourceIT.createEntity(em);
            em.persist(pagamento);
            em.flush();
        } else {
            pagamento = TestUtil.findAll(em, Pagamento.class).get(0);
        }
        em.persist(pagamento);
        em.flush();
        lancamentoCarteiraCliente.setPagamento(pagamento);
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        Long pagamentoId = pagamento.getId();

        // Get all the lancamentoCarteiraClienteList where pagamento equals to pagamentoId
        defaultLancamentoCarteiraClienteShouldBeFound("pagamentoId.equals=" + pagamentoId);

        // Get all the lancamentoCarteiraClienteList where pagamento equals to (pagamentoId + 1)
        defaultLancamentoCarteiraClienteShouldNotBeFound("pagamentoId.equals=" + (pagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllLancamentoCarteiraClientesByCarteirasClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        CarteiraCliente carteirasCliente;
        if (TestUtil.findAll(em, CarteiraCliente.class).isEmpty()) {
            carteirasCliente = CarteiraClienteResourceIT.createEntity(em);
            em.persist(carteirasCliente);
            em.flush();
        } else {
            carteirasCliente = TestUtil.findAll(em, CarteiraCliente.class).get(0);
        }
        em.persist(carteirasCliente);
        em.flush();
        lancamentoCarteiraCliente.addCarteirasCliente(carteirasCliente);
        lancamentoCarteiraClienteRepository.saveAndFlush(lancamentoCarteiraCliente);
        Long carteirasClienteId = carteirasCliente.getId();

        // Get all the lancamentoCarteiraClienteList where carteirasCliente equals to carteirasClienteId
        defaultLancamentoCarteiraClienteShouldBeFound("carteirasClienteId.equals=" + carteirasClienteId);

        // Get all the lancamentoCarteiraClienteList where carteirasCliente equals to (carteirasClienteId + 1)
        defaultLancamentoCarteiraClienteShouldNotBeFound("carteirasClienteId.equals=" + (carteirasClienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLancamentoCarteiraClienteShouldBeFound(String filter) throws Exception {
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoCarteiraCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricaoLancamento").value(hasItem(DEFAULT_DESCRICAO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].valorCredito").value(hasItem(sameNumber(DEFAULT_VALOR_CREDITO))))
            .andExpect(jsonPath("$.[*].valorDebito").value(hasItem(sameNumber(DEFAULT_VALOR_DEBITO))))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));

        // Check, that the count call also returns 1
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLancamentoCarteiraClienteShouldNotBeFound(String filter) throws Exception {
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLancamentoCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .dataHora(UPDATED_DATA_HORA)
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
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(UPDATED_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
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
            .dataHora(UPDATED_DATA_HORA)
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
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(DEFAULT_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
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
            .descricaoLancamento(UPDATED_DESCRICAO_LANCAMENTO)
            .dataHora(UPDATED_DATA_HORA)
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
        assertThat(testLancamentoCarteiraCliente.getDescricaoLancamento()).isEqualTo(UPDATED_DESCRICAO_LANCAMENTO);
        assertThat(testLancamentoCarteiraCliente.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
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
