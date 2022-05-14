package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.domain.enumeration.BandeiraCartao;
import com.mycompany.myapp.domain.enumeration.FormaPagamento;
import com.mycompany.myapp.repository.PagamentoRepository;
import com.mycompany.myapp.service.PagamentoService;
import com.mycompany.myapp.service.criteria.PagamentoCriteria;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.mapper.PagamentoMapper;
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
 * Integration tests for the {@link PagamentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PagamentoResourceIT {

    private static final FormaPagamento DEFAULT_FORMA_PAGAMENTO = FormaPagamento.DINHEIRO;
    private static final FormaPagamento UPDATED_FORMA_PAGAMENTO = FormaPagamento.PIX;

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PARCELAS = 1;
    private static final Integer UPDATED_NUMERO_PARCELAS = 2;
    private static final Integer SMALLER_NUMERO_PARCELAS = 1 - 1;

    private static final BandeiraCartao DEFAULT_BANDEIRA_CARTAO = BandeiraCartao.MASTERCARD;
    private static final BandeiraCartao UPDATED_BANDEIRA_CARTAO = BandeiraCartao.VISA;

    private static final String DEFAULT_CLIENTE_ORIGEM_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE_ORIGEM_PAGAMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICADOR_CONFERIDO = false;
    private static final Boolean UPDATED_INDICADOR_CONFERIDO = true;

    private static final Instant DEFAULT_DATA_HORA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_CADASTRO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_ATUALIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_ATUALIZACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoRepository pagamentoRepositoryMock;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Mock
    private PagamentoService pagamentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .formaPagamento(DEFAULT_FORMA_PAGAMENTO)
            .dataHora(DEFAULT_DATA_HORA)
            .valor(DEFAULT_VALOR)
            .observacoes(DEFAULT_OBSERVACOES)
            .numeroParcelas(DEFAULT_NUMERO_PARCELAS)
            .bandeiraCartao(DEFAULT_BANDEIRA_CARTAO)
            .clienteOrigemPagamento(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO)
            .indicadorConferido(DEFAULT_INDICADOR_CONFERIDO)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return pagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createUpdatedEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .dataHora(UPDATED_DATA_HORA)
            .valor(UPDATED_VALOR)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .bandeiraCartao(UPDATED_BANDEIRA_CARTAO)
            .clienteOrigemPagamento(UPDATED_CLIENTE_ORIGEM_PAGAMENTO)
            .indicadorConferido(UPDATED_INDICADOR_CONFERIDO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return pagamento;
    }

    @BeforeEach
    public void initTest() {
        pagamento = createEntity(em);
    }

    @Test
    @Transactional
    void createPagamento() throws Exception {
        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();
        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);
        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getFormaPagamento()).isEqualTo(DEFAULT_FORMA_PAGAMENTO);
        assertThat(testPagamento.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testPagamento.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
        assertThat(testPagamento.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPagamento.getNumeroParcelas()).isEqualTo(DEFAULT_NUMERO_PARCELAS);
        assertThat(testPagamento.getBandeiraCartao()).isEqualTo(DEFAULT_BANDEIRA_CARTAO);
        assertThat(testPagamento.getClienteOrigemPagamento()).isEqualTo(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);
        assertThat(testPagamento.getIndicadorConferido()).isEqualTo(DEFAULT_INDICADOR_CONFERIDO);
        assertThat(testPagamento.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testPagamento.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testPagamento.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testPagamento.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createPagamentoWithExistingId() throws Exception {
        // Create the Pagamento with an existing ID
        pagamento.setId(1L);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        int databaseSizeBeforeCreate = pagamentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFormaPagamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setFormaPagamento(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setDataHora(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagamentoRepository.findAll().size();
        // set the field null
        pagamento.setValor(null);

        // Create the Pagamento, which fails.
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPagamentos() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].numeroParcelas").value(hasItem(DEFAULT_NUMERO_PARCELAS)))
            .andExpect(jsonPath("$.[*].bandeiraCartao").value(hasItem(DEFAULT_BANDEIRA_CARTAO.toString())))
            .andExpect(jsonPath("$.[*].clienteOrigemPagamento").value(hasItem(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].indicadorConferido").value(hasItem(DEFAULT_INDICADOR_CONFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagamentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(pagamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pagamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagamentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pagamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pagamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.formaPagamento").value(DEFAULT_FORMA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.numeroParcelas").value(DEFAULT_NUMERO_PARCELAS))
            .andExpect(jsonPath("$.bandeiraCartao").value(DEFAULT_BANDEIRA_CARTAO.toString()))
            .andExpect(jsonPath("$.clienteOrigemPagamento").value(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO))
            .andExpect(jsonPath("$.indicadorConferido").value(DEFAULT_INDICADOR_CONFERIDO.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getPagamentosByIdFiltering() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        Long id = pagamento.getId();

        defaultPagamentoShouldBeFound("id.equals=" + id);
        defaultPagamentoShouldNotBeFound("id.notEquals=" + id);

        defaultPagamentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPagamentoShouldNotBeFound("id.greaterThan=" + id);

        defaultPagamentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPagamentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPagamentosByFormaPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where formaPagamento equals to DEFAULT_FORMA_PAGAMENTO
        defaultPagamentoShouldBeFound("formaPagamento.equals=" + DEFAULT_FORMA_PAGAMENTO);

        // Get all the pagamentoList where formaPagamento equals to UPDATED_FORMA_PAGAMENTO
        defaultPagamentoShouldNotBeFound("formaPagamento.equals=" + UPDATED_FORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByFormaPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where formaPagamento not equals to DEFAULT_FORMA_PAGAMENTO
        defaultPagamentoShouldNotBeFound("formaPagamento.notEquals=" + DEFAULT_FORMA_PAGAMENTO);

        // Get all the pagamentoList where formaPagamento not equals to UPDATED_FORMA_PAGAMENTO
        defaultPagamentoShouldBeFound("formaPagamento.notEquals=" + UPDATED_FORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByFormaPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where formaPagamento in DEFAULT_FORMA_PAGAMENTO or UPDATED_FORMA_PAGAMENTO
        defaultPagamentoShouldBeFound("formaPagamento.in=" + DEFAULT_FORMA_PAGAMENTO + "," + UPDATED_FORMA_PAGAMENTO);

        // Get all the pagamentoList where formaPagamento equals to UPDATED_FORMA_PAGAMENTO
        defaultPagamentoShouldNotBeFound("formaPagamento.in=" + UPDATED_FORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByFormaPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where formaPagamento is not null
        defaultPagamentoShouldBeFound("formaPagamento.specified=true");

        // Get all the pagamentoList where formaPagamento is null
        defaultPagamentoShouldNotBeFound("formaPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHora equals to DEFAULT_DATA_HORA
        defaultPagamentoShouldBeFound("dataHora.equals=" + DEFAULT_DATA_HORA);

        // Get all the pagamentoList where dataHora equals to UPDATED_DATA_HORA
        defaultPagamentoShouldNotBeFound("dataHora.equals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHora not equals to DEFAULT_DATA_HORA
        defaultPagamentoShouldNotBeFound("dataHora.notEquals=" + DEFAULT_DATA_HORA);

        // Get all the pagamentoList where dataHora not equals to UPDATED_DATA_HORA
        defaultPagamentoShouldBeFound("dataHora.notEquals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHora in DEFAULT_DATA_HORA or UPDATED_DATA_HORA
        defaultPagamentoShouldBeFound("dataHora.in=" + DEFAULT_DATA_HORA + "," + UPDATED_DATA_HORA);

        // Get all the pagamentoList where dataHora equals to UPDATED_DATA_HORA
        defaultPagamentoShouldNotBeFound("dataHora.in=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHora is not null
        defaultPagamentoShouldBeFound("dataHora.specified=true");

        // Get all the pagamentoList where dataHora is null
        defaultPagamentoShouldNotBeFound("dataHora.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor equals to DEFAULT_VALOR
        defaultPagamentoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor equals to UPDATED_VALOR
        defaultPagamentoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor not equals to DEFAULT_VALOR
        defaultPagamentoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor not equals to UPDATED_VALOR
        defaultPagamentoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultPagamentoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the pagamentoList where valor equals to UPDATED_VALOR
        defaultPagamentoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor is not null
        defaultPagamentoShouldBeFound("valor.specified=true");

        // Get all the pagamentoList where valor is null
        defaultPagamentoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor is greater than or equal to DEFAULT_VALOR
        defaultPagamentoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor is greater than or equal to UPDATED_VALOR
        defaultPagamentoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor is less than or equal to DEFAULT_VALOR
        defaultPagamentoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor is less than or equal to SMALLER_VALOR
        defaultPagamentoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor is less than DEFAULT_VALOR
        defaultPagamentoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor is less than UPDATED_VALOR
        defaultPagamentoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valor is greater than DEFAULT_VALOR
        defaultPagamentoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the pagamentoList where valor is greater than SMALLER_VALOR
        defaultPagamentoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes equals to DEFAULT_OBSERVACOES
        defaultPagamentoShouldBeFound("observacoes.equals=" + DEFAULT_OBSERVACOES);

        // Get all the pagamentoList where observacoes equals to UPDATED_OBSERVACOES
        defaultPagamentoShouldNotBeFound("observacoes.equals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes not equals to DEFAULT_OBSERVACOES
        defaultPagamentoShouldNotBeFound("observacoes.notEquals=" + DEFAULT_OBSERVACOES);

        // Get all the pagamentoList where observacoes not equals to UPDATED_OBSERVACOES
        defaultPagamentoShouldBeFound("observacoes.notEquals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes in DEFAULT_OBSERVACOES or UPDATED_OBSERVACOES
        defaultPagamentoShouldBeFound("observacoes.in=" + DEFAULT_OBSERVACOES + "," + UPDATED_OBSERVACOES);

        // Get all the pagamentoList where observacoes equals to UPDATED_OBSERVACOES
        defaultPagamentoShouldNotBeFound("observacoes.in=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes is not null
        defaultPagamentoShouldBeFound("observacoes.specified=true");

        // Get all the pagamentoList where observacoes is null
        defaultPagamentoShouldNotBeFound("observacoes.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes contains DEFAULT_OBSERVACOES
        defaultPagamentoShouldBeFound("observacoes.contains=" + DEFAULT_OBSERVACOES);

        // Get all the pagamentoList where observacoes contains UPDATED_OBSERVACOES
        defaultPagamentoShouldNotBeFound("observacoes.contains=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllPagamentosByObservacoesNotContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where observacoes does not contain DEFAULT_OBSERVACOES
        defaultPagamentoShouldNotBeFound("observacoes.doesNotContain=" + DEFAULT_OBSERVACOES);

        // Get all the pagamentoList where observacoes does not contain UPDATED_OBSERVACOES
        defaultPagamentoShouldBeFound("observacoes.doesNotContain=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas equals to DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.equals=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas equals to UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.equals=" + UPDATED_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas not equals to DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.notEquals=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas not equals to UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.notEquals=" + UPDATED_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas in DEFAULT_NUMERO_PARCELAS or UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.in=" + DEFAULT_NUMERO_PARCELAS + "," + UPDATED_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas equals to UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.in=" + UPDATED_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas is not null
        defaultPagamentoShouldBeFound("numeroParcelas.specified=true");

        // Get all the pagamentoList where numeroParcelas is null
        defaultPagamentoShouldNotBeFound("numeroParcelas.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas is greater than or equal to DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.greaterThanOrEqual=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas is greater than or equal to UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.greaterThanOrEqual=" + UPDATED_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas is less than or equal to DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.lessThanOrEqual=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas is less than or equal to SMALLER_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.lessThanOrEqual=" + SMALLER_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsLessThanSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas is less than DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.lessThan=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas is less than UPDATED_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.lessThan=" + UPDATED_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByNumeroParcelasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where numeroParcelas is greater than DEFAULT_NUMERO_PARCELAS
        defaultPagamentoShouldNotBeFound("numeroParcelas.greaterThan=" + DEFAULT_NUMERO_PARCELAS);

        // Get all the pagamentoList where numeroParcelas is greater than SMALLER_NUMERO_PARCELAS
        defaultPagamentoShouldBeFound("numeroParcelas.greaterThan=" + SMALLER_NUMERO_PARCELAS);
    }

    @Test
    @Transactional
    void getAllPagamentosByBandeiraCartaoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where bandeiraCartao equals to DEFAULT_BANDEIRA_CARTAO
        defaultPagamentoShouldBeFound("bandeiraCartao.equals=" + DEFAULT_BANDEIRA_CARTAO);

        // Get all the pagamentoList where bandeiraCartao equals to UPDATED_BANDEIRA_CARTAO
        defaultPagamentoShouldNotBeFound("bandeiraCartao.equals=" + UPDATED_BANDEIRA_CARTAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByBandeiraCartaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where bandeiraCartao not equals to DEFAULT_BANDEIRA_CARTAO
        defaultPagamentoShouldNotBeFound("bandeiraCartao.notEquals=" + DEFAULT_BANDEIRA_CARTAO);

        // Get all the pagamentoList where bandeiraCartao not equals to UPDATED_BANDEIRA_CARTAO
        defaultPagamentoShouldBeFound("bandeiraCartao.notEquals=" + UPDATED_BANDEIRA_CARTAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByBandeiraCartaoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where bandeiraCartao in DEFAULT_BANDEIRA_CARTAO or UPDATED_BANDEIRA_CARTAO
        defaultPagamentoShouldBeFound("bandeiraCartao.in=" + DEFAULT_BANDEIRA_CARTAO + "," + UPDATED_BANDEIRA_CARTAO);

        // Get all the pagamentoList where bandeiraCartao equals to UPDATED_BANDEIRA_CARTAO
        defaultPagamentoShouldNotBeFound("bandeiraCartao.in=" + UPDATED_BANDEIRA_CARTAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByBandeiraCartaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where bandeiraCartao is not null
        defaultPagamentoShouldBeFound("bandeiraCartao.specified=true");

        // Get all the pagamentoList where bandeiraCartao is null
        defaultPagamentoShouldNotBeFound("bandeiraCartao.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento equals to DEFAULT_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldBeFound("clienteOrigemPagamento.equals=" + DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);

        // Get all the pagamentoList where clienteOrigemPagamento equals to UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.equals=" + UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento not equals to DEFAULT_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.notEquals=" + DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);

        // Get all the pagamentoList where clienteOrigemPagamento not equals to UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldBeFound("clienteOrigemPagamento.notEquals=" + UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento in DEFAULT_CLIENTE_ORIGEM_PAGAMENTO or UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldBeFound(
            "clienteOrigemPagamento.in=" + DEFAULT_CLIENTE_ORIGEM_PAGAMENTO + "," + UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        );

        // Get all the pagamentoList where clienteOrigemPagamento equals to UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.in=" + UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento is not null
        defaultPagamentoShouldBeFound("clienteOrigemPagamento.specified=true");

        // Get all the pagamentoList where clienteOrigemPagamento is null
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento contains DEFAULT_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldBeFound("clienteOrigemPagamento.contains=" + DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);

        // Get all the pagamentoList where clienteOrigemPagamento contains UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.contains=" + UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByClienteOrigemPagamentoNotContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where clienteOrigemPagamento does not contain DEFAULT_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldNotBeFound("clienteOrigemPagamento.doesNotContain=" + DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);

        // Get all the pagamentoList where clienteOrigemPagamento does not contain UPDATED_CLIENTE_ORIGEM_PAGAMENTO
        defaultPagamentoShouldBeFound("clienteOrigemPagamento.doesNotContain=" + UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByIndicadorConferidoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where indicadorConferido equals to DEFAULT_INDICADOR_CONFERIDO
        defaultPagamentoShouldBeFound("indicadorConferido.equals=" + DEFAULT_INDICADOR_CONFERIDO);

        // Get all the pagamentoList where indicadorConferido equals to UPDATED_INDICADOR_CONFERIDO
        defaultPagamentoShouldNotBeFound("indicadorConferido.equals=" + UPDATED_INDICADOR_CONFERIDO);
    }

    @Test
    @Transactional
    void getAllPagamentosByIndicadorConferidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where indicadorConferido not equals to DEFAULT_INDICADOR_CONFERIDO
        defaultPagamentoShouldNotBeFound("indicadorConferido.notEquals=" + DEFAULT_INDICADOR_CONFERIDO);

        // Get all the pagamentoList where indicadorConferido not equals to UPDATED_INDICADOR_CONFERIDO
        defaultPagamentoShouldBeFound("indicadorConferido.notEquals=" + UPDATED_INDICADOR_CONFERIDO);
    }

    @Test
    @Transactional
    void getAllPagamentosByIndicadorConferidoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where indicadorConferido in DEFAULT_INDICADOR_CONFERIDO or UPDATED_INDICADOR_CONFERIDO
        defaultPagamentoShouldBeFound("indicadorConferido.in=" + DEFAULT_INDICADOR_CONFERIDO + "," + UPDATED_INDICADOR_CONFERIDO);

        // Get all the pagamentoList where indicadorConferido equals to UPDATED_INDICADOR_CONFERIDO
        defaultPagamentoShouldNotBeFound("indicadorConferido.in=" + UPDATED_INDICADOR_CONFERIDO);
    }

    @Test
    @Transactional
    void getAllPagamentosByIndicadorConferidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where indicadorConferido is not null
        defaultPagamentoShouldBeFound("indicadorConferido.specified=true");

        // Get all the pagamentoList where indicadorConferido is null
        defaultPagamentoShouldNotBeFound("indicadorConferido.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraCadastro equals to DEFAULT_DATA_HORA_CADASTRO
        defaultPagamentoShouldBeFound("dataHoraCadastro.equals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the pagamentoList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultPagamentoShouldNotBeFound("dataHoraCadastro.equals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraCadastro not equals to DEFAULT_DATA_HORA_CADASTRO
        defaultPagamentoShouldNotBeFound("dataHoraCadastro.notEquals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the pagamentoList where dataHoraCadastro not equals to UPDATED_DATA_HORA_CADASTRO
        defaultPagamentoShouldBeFound("dataHoraCadastro.notEquals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraCadastro in DEFAULT_DATA_HORA_CADASTRO or UPDATED_DATA_HORA_CADASTRO
        defaultPagamentoShouldBeFound("dataHoraCadastro.in=" + DEFAULT_DATA_HORA_CADASTRO + "," + UPDATED_DATA_HORA_CADASTRO);

        // Get all the pagamentoList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultPagamentoShouldNotBeFound("dataHoraCadastro.in=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraCadastro is not null
        defaultPagamentoShouldBeFound("dataHoraCadastro.specified=true");

        // Get all the pagamentoList where dataHoraCadastro is null
        defaultPagamentoShouldNotBeFound("dataHoraCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro equals to DEFAULT_COLABORADOR_CADASTRO
        defaultPagamentoShouldBeFound("colaboradorCadastro.equals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the pagamentoList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.equals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro not equals to DEFAULT_COLABORADOR_CADASTRO
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.notEquals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the pagamentoList where colaboradorCadastro not equals to UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldBeFound("colaboradorCadastro.notEquals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro in DEFAULT_COLABORADOR_CADASTRO or UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldBeFound("colaboradorCadastro.in=" + DEFAULT_COLABORADOR_CADASTRO + "," + UPDATED_COLABORADOR_CADASTRO);

        // Get all the pagamentoList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.in=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro is not null
        defaultPagamentoShouldBeFound("colaboradorCadastro.specified=true");

        // Get all the pagamentoList where colaboradorCadastro is null
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro contains DEFAULT_COLABORADOR_CADASTRO
        defaultPagamentoShouldBeFound("colaboradorCadastro.contains=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the pagamentoList where colaboradorCadastro contains UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.contains=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorCadastro does not contain DEFAULT_COLABORADOR_CADASTRO
        defaultPagamentoShouldNotBeFound("colaboradorCadastro.doesNotContain=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the pagamentoList where colaboradorCadastro does not contain UPDATED_COLABORADOR_CADASTRO
        defaultPagamentoShouldBeFound("colaboradorCadastro.doesNotContain=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraAtualizacao equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldBeFound("dataHoraAtualizacao.equals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the pagamentoList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("dataHoraAtualizacao.equals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraAtualizacao not equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("dataHoraAtualizacao.notEquals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the pagamentoList where dataHoraAtualizacao not equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldBeFound("dataHoraAtualizacao.notEquals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraAtualizacao in DEFAULT_DATA_HORA_ATUALIZACAO or UPDATED_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldBeFound("dataHoraAtualizacao.in=" + DEFAULT_DATA_HORA_ATUALIZACAO + "," + UPDATED_DATA_HORA_ATUALIZACAO);

        // Get all the pagamentoList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("dataHoraAtualizacao.in=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataHoraAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataHoraAtualizacao is not null
        defaultPagamentoShouldBeFound("dataHoraAtualizacao.specified=true");

        // Get all the pagamentoList where dataHoraAtualizacao is null
        defaultPagamentoShouldNotBeFound("dataHoraAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldBeFound("colaboradorAtualizacao.equals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the pagamentoList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.equals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao not equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.notEquals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the pagamentoList where colaboradorAtualizacao not equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldBeFound("colaboradorAtualizacao.notEquals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao in DEFAULT_COLABORADOR_ATUALIZACAO or UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldBeFound(
            "colaboradorAtualizacao.in=" + DEFAULT_COLABORADOR_ATUALIZACAO + "," + UPDATED_COLABORADOR_ATUALIZACAO
        );

        // Get all the pagamentoList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.in=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao is not null
        defaultPagamentoShouldBeFound("colaboradorAtualizacao.specified=true");

        // Get all the pagamentoList where colaboradorAtualizacao is null
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao contains DEFAULT_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldBeFound("colaboradorAtualizacao.contains=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the pagamentoList where colaboradorAtualizacao contains UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.contains=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByColaboradorAtualizacaoNotContainsSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where colaboradorAtualizacao does not contain DEFAULT_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldNotBeFound("colaboradorAtualizacao.doesNotContain=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the pagamentoList where colaboradorAtualizacao does not contain UPDATED_COLABORADOR_ATUALIZACAO
        defaultPagamentoShouldBeFound("colaboradorAtualizacao.doesNotContain=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAdquirentePagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
        PlataformaPagamento adquirentePagamento;
        if (TestUtil.findAll(em, PlataformaPagamento.class).isEmpty()) {
            adquirentePagamento = PlataformaPagamentoResourceIT.createEntity(em);
            em.persist(adquirentePagamento);
            em.flush();
        } else {
            adquirentePagamento = TestUtil.findAll(em, PlataformaPagamento.class).get(0);
        }
        em.persist(adquirentePagamento);
        em.flush();
        pagamento.setAdquirentePagamento(adquirentePagamento);
        pagamentoRepository.saveAndFlush(pagamento);
        Long adquirentePagamentoId = adquirentePagamento.getId();

        // Get all the pagamentoList where adquirentePagamento equals to adquirentePagamentoId
        defaultPagamentoShouldBeFound("adquirentePagamentoId.equals=" + adquirentePagamentoId);

        // Get all the pagamentoList where adquirentePagamento equals to (adquirentePagamentoId + 1)
        defaultPagamentoShouldNotBeFound("adquirentePagamentoId.equals=" + (adquirentePagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllPagamentosByVendasIsEqualToSomething() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);
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
        pagamento.addVendas(vendas);
        pagamentoRepository.saveAndFlush(pagamento);
        Long vendasId = vendas.getId();

        // Get all the pagamentoList where vendas equals to vendasId
        defaultPagamentoShouldBeFound("vendasId.equals=" + vendasId);

        // Get all the pagamentoList where vendas equals to (vendasId + 1)
        defaultPagamentoShouldNotBeFound("vendasId.equals=" + (vendasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPagamentoShouldBeFound(String filter) throws Exception {
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].numeroParcelas").value(hasItem(DEFAULT_NUMERO_PARCELAS)))
            .andExpect(jsonPath("$.[*].bandeiraCartao").value(hasItem(DEFAULT_BANDEIRA_CARTAO.toString())))
            .andExpect(jsonPath("$.[*].clienteOrigemPagamento").value(hasItem(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].indicadorConferido").value(hasItem(DEFAULT_INDICADOR_CONFERIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));

        // Check, that the count call also returns 1
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPagamentoShouldNotBeFound(String filter) throws Exception {
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .dataHora(UPDATED_DATA_HORA)
            .valor(UPDATED_VALOR)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .bandeiraCartao(UPDATED_BANDEIRA_CARTAO)
            .clienteOrigemPagamento(UPDATED_CLIENTE_ORIGEM_PAGAMENTO)
            .indicadorConferido(UPDATED_INDICADOR_CONFERIDO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(updatedPagamento);

        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getFormaPagamento()).isEqualTo(UPDATED_FORMA_PAGAMENTO);
        assertThat(testPagamento.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testPagamento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testPagamento.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPagamento.getNumeroParcelas()).isEqualTo(UPDATED_NUMERO_PARCELAS);
        assertThat(testPagamento.getBandeiraCartao()).isEqualTo(UPDATED_BANDEIRA_CARTAO);
        assertThat(testPagamento.getClienteOrigemPagamento()).isEqualTo(UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
        assertThat(testPagamento.getIndicadorConferido()).isEqualTo(UPDATED_INDICADOR_CONFERIDO);
        assertThat(testPagamento.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testPagamento.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testPagamento.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testPagamento.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagamentoWithPatch() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento using partial update
        Pagamento partialUpdatedPagamento = new Pagamento();
        partialUpdatedPagamento.setId(pagamento.getId());

        partialUpdatedPagamento
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .dataHora(UPDATED_DATA_HORA)
            .valor(UPDATED_VALOR)
            .bandeiraCartao(UPDATED_BANDEIRA_CARTAO)
            .indicadorConferido(UPDATED_INDICADOR_CONFERIDO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagamento))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getFormaPagamento()).isEqualTo(UPDATED_FORMA_PAGAMENTO);
        assertThat(testPagamento.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testPagamento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testPagamento.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPagamento.getNumeroParcelas()).isEqualTo(DEFAULT_NUMERO_PARCELAS);
        assertThat(testPagamento.getBandeiraCartao()).isEqualTo(UPDATED_BANDEIRA_CARTAO);
        assertThat(testPagamento.getClienteOrigemPagamento()).isEqualTo(DEFAULT_CLIENTE_ORIGEM_PAGAMENTO);
        assertThat(testPagamento.getIndicadorConferido()).isEqualTo(UPDATED_INDICADOR_CONFERIDO);
        assertThat(testPagamento.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testPagamento.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testPagamento.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testPagamento.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdatePagamentoWithPatch() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();

        // Update the pagamento using partial update
        Pagamento partialUpdatedPagamento = new Pagamento();
        partialUpdatedPagamento.setId(pagamento.getId());

        partialUpdatedPagamento
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .dataHora(UPDATED_DATA_HORA)
            .valor(UPDATED_VALOR)
            .observacoes(UPDATED_OBSERVACOES)
            .numeroParcelas(UPDATED_NUMERO_PARCELAS)
            .bandeiraCartao(UPDATED_BANDEIRA_CARTAO)
            .clienteOrigemPagamento(UPDATED_CLIENTE_ORIGEM_PAGAMENTO)
            .indicadorConferido(UPDATED_INDICADOR_CONFERIDO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPagamento))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
        Pagamento testPagamento = pagamentoList.get(pagamentoList.size() - 1);
        assertThat(testPagamento.getFormaPagamento()).isEqualTo(UPDATED_FORMA_PAGAMENTO);
        assertThat(testPagamento.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testPagamento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testPagamento.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPagamento.getNumeroParcelas()).isEqualTo(UPDATED_NUMERO_PARCELAS);
        assertThat(testPagamento.getBandeiraCartao()).isEqualTo(UPDATED_BANDEIRA_CARTAO);
        assertThat(testPagamento.getClienteOrigemPagamento()).isEqualTo(UPDATED_CLIENTE_ORIGEM_PAGAMENTO);
        assertThat(testPagamento.getIndicadorConferido()).isEqualTo(UPDATED_INDICADOR_CONFERIDO);
        assertThat(testPagamento.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testPagamento.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testPagamento.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testPagamento.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pagamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPagamento() throws Exception {
        int databaseSizeBeforeUpdate = pagamentoRepository.findAll().size();
        pagamento.setId(count.incrementAndGet());

        // Create the Pagamento
        PagamentoDTO pagamentoDTO = pagamentoMapper.toDto(pagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pagamentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagamento in the database
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePagamento() throws Exception {
        // Initialize the database
        pagamentoRepository.saveAndFlush(pagamento);

        int databaseSizeBeforeDelete = pagamentoRepository.findAll().size();

        // Delete the pagamento
        restPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagamento> pagamentoList = pagamentoRepository.findAll();
        assertThat(pagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
