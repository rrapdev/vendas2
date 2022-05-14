package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.VendaRepository;
import com.mycompany.myapp.service.VendaService;
import com.mycompany.myapp.service.criteria.VendaCriteria;
import com.mycompany.myapp.service.dto.VendaDTO;
import com.mycompany.myapp.service.mapper.VendaMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link VendaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VendaResourceIT {

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_BRUTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_BRUTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_BRUTO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_DESCONTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_DESCONTO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_LIQUIDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_LIQUIDO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_LIQUIDO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_PAGO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_PAGO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_PAGO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_SALDO_RESTANTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SALDO_RESTANTE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_SALDO_RESTANTE = new BigDecimal(1 - 1);

    private static final String DEFAULT_OBSERVARCOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVARCOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE = false;
    private static final Boolean UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE = true;

    private static final Boolean DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE = false;
    private static final Boolean UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE = true;

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

    private static final String ENTITY_API_URL = "/api/vendas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VendaRepository vendaRepository;

    @Mock
    private VendaRepository vendaRepositoryMock;

    @Autowired
    private VendaMapper vendaMapper;

    @Mock
    private VendaService vendaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendaMockMvc;

    private Venda venda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createEntity(EntityManager em) {
        Venda venda = new Venda()
            .dataHora(DEFAULT_DATA_HORA)
            .valorTotalBruto(DEFAULT_VALOR_TOTAL_BRUTO)
            .valorTotalDesconto(DEFAULT_VALOR_TOTAL_DESCONTO)
            .valorTotalLiquido(DEFAULT_VALOR_TOTAL_LIQUIDO)
            .valorTotalPago(DEFAULT_VALOR_TOTAL_PAGO)
            .valorSaldoRestante(DEFAULT_VALOR_SALDO_RESTANTE)
            .observarcoes(DEFAULT_OBSERVARCOES)
            .indicadorPossuiPagamentoPendente(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE)
            .indicadorPossuiItemPresente(DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE)
            .indicadorBloqueio(DEFAULT_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return venda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createUpdatedEntity(EntityManager em) {
        Venda venda = new Venda()
            .dataHora(UPDATED_DATA_HORA)
            .valorTotalBruto(UPDATED_VALOR_TOTAL_BRUTO)
            .valorTotalDesconto(UPDATED_VALOR_TOTAL_DESCONTO)
            .valorTotalLiquido(UPDATED_VALOR_TOTAL_LIQUIDO)
            .valorTotalPago(UPDATED_VALOR_TOTAL_PAGO)
            .valorSaldoRestante(UPDATED_VALOR_SALDO_RESTANTE)
            .observarcoes(UPDATED_OBSERVARCOES)
            .indicadorPossuiPagamentoPendente(UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE)
            .indicadorPossuiItemPresente(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return venda;
    }

    @BeforeEach
    public void initTest() {
        venda = createEntity(em);
    }

    @Test
    @Transactional
    void createVenda() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();
        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);
        restVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate + 1);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testVenda.getValorTotalBruto()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_BRUTO);
        assertThat(testVenda.getValorTotalDesconto()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_DESCONTO);
        assertThat(testVenda.getValorTotalLiquido()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_LIQUIDO);
        assertThat(testVenda.getValorTotalPago()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_PAGO);
        assertThat(testVenda.getValorSaldoRestante()).isEqualByComparingTo(DEFAULT_VALOR_SALDO_RESTANTE);
        assertThat(testVenda.getObservarcoes()).isEqualTo(DEFAULT_OBSERVARCOES);
        assertThat(testVenda.getIndicadorPossuiPagamentoPendente()).isEqualTo(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
        assertThat(testVenda.getIndicadorPossuiItemPresente()).isEqualTo(DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE);
        assertThat(testVenda.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testVenda.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testVenda.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testVenda.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testVenda.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createVendaWithExistingId() throws Exception {
        // Create the Venda with an existing ID
        venda.setId(1L);
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendaRepository.findAll().size();
        // set the field null
        venda.setDataHora(null);

        // Create the Venda, which fails.
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        restVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVendas() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList
        restVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].valorTotalBruto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_BRUTO))))
            .andExpect(jsonPath("$.[*].valorTotalDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_DESCONTO))))
            .andExpect(jsonPath("$.[*].valorTotalLiquido").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_LIQUIDO))))
            .andExpect(jsonPath("$.[*].valorTotalPago").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].valorSaldoRestante").value(hasItem(sameNumber(DEFAULT_VALOR_SALDO_RESTANTE))))
            .andExpect(jsonPath("$.[*].observarcoes").value(hasItem(DEFAULT_OBSERVARCOES.toString())))
            .andExpect(
                jsonPath("$.[*].indicadorPossuiPagamentoPendente")
                    .value(hasItem(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].indicadorPossuiItemPresente").value(hasItem(DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendasWithEagerRelationshipsIsEnabled() throws Exception {
        when(vendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get the venda
        restVendaMockMvc
            .perform(get(ENTITY_API_URL_ID, venda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venda.getId().intValue()))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()))
            .andExpect(jsonPath("$.valorTotalBruto").value(sameNumber(DEFAULT_VALOR_TOTAL_BRUTO)))
            .andExpect(jsonPath("$.valorTotalDesconto").value(sameNumber(DEFAULT_VALOR_TOTAL_DESCONTO)))
            .andExpect(jsonPath("$.valorTotalLiquido").value(sameNumber(DEFAULT_VALOR_TOTAL_LIQUIDO)))
            .andExpect(jsonPath("$.valorTotalPago").value(sameNumber(DEFAULT_VALOR_TOTAL_PAGO)))
            .andExpect(jsonPath("$.valorSaldoRestante").value(sameNumber(DEFAULT_VALOR_SALDO_RESTANTE)))
            .andExpect(jsonPath("$.observarcoes").value(DEFAULT_OBSERVARCOES.toString()))
            .andExpect(jsonPath("$.indicadorPossuiPagamentoPendente").value(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE.booleanValue()))
            .andExpect(jsonPath("$.indicadorPossuiItemPresente").value(DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE.booleanValue()))
            .andExpect(jsonPath("$.indicadorBloqueio").value(DEFAULT_INDICADOR_BLOQUEIO.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getVendasByIdFiltering() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        Long id = venda.getId();

        defaultVendaShouldBeFound("id.equals=" + id);
        defaultVendaShouldNotBeFound("id.notEquals=" + id);

        defaultVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHora equals to DEFAULT_DATA_HORA
        defaultVendaShouldBeFound("dataHora.equals=" + DEFAULT_DATA_HORA);

        // Get all the vendaList where dataHora equals to UPDATED_DATA_HORA
        defaultVendaShouldNotBeFound("dataHora.equals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHora not equals to DEFAULT_DATA_HORA
        defaultVendaShouldNotBeFound("dataHora.notEquals=" + DEFAULT_DATA_HORA);

        // Get all the vendaList where dataHora not equals to UPDATED_DATA_HORA
        defaultVendaShouldBeFound("dataHora.notEquals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHora in DEFAULT_DATA_HORA or UPDATED_DATA_HORA
        defaultVendaShouldBeFound("dataHora.in=" + DEFAULT_DATA_HORA + "," + UPDATED_DATA_HORA);

        // Get all the vendaList where dataHora equals to UPDATED_DATA_HORA
        defaultVendaShouldNotBeFound("dataHora.in=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHora is not null
        defaultVendaShouldBeFound("dataHora.specified=true");

        // Get all the vendaList where dataHora is null
        defaultVendaShouldNotBeFound("dataHora.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto equals to DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.equals=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto equals to UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.equals=" + UPDATED_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto not equals to DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.notEquals=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto not equals to UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.notEquals=" + UPDATED_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto in DEFAULT_VALOR_TOTAL_BRUTO or UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.in=" + DEFAULT_VALOR_TOTAL_BRUTO + "," + UPDATED_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto equals to UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.in=" + UPDATED_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto is not null
        defaultVendaShouldBeFound("valorTotalBruto.specified=true");

        // Get all the vendaList where valorTotalBruto is null
        defaultVendaShouldNotBeFound("valorTotalBruto.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto is greater than or equal to DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto is greater than or equal to UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto is less than or equal to DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto is less than or equal to SMALLER_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto is less than DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.lessThan=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto is less than UPDATED_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.lessThan=" + UPDATED_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalBrutoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalBruto is greater than DEFAULT_VALOR_TOTAL_BRUTO
        defaultVendaShouldNotBeFound("valorTotalBruto.greaterThan=" + DEFAULT_VALOR_TOTAL_BRUTO);

        // Get all the vendaList where valorTotalBruto is greater than SMALLER_VALOR_TOTAL_BRUTO
        defaultVendaShouldBeFound("valorTotalBruto.greaterThan=" + SMALLER_VALOR_TOTAL_BRUTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto equals to DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.equals=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto equals to UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.equals=" + UPDATED_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto not equals to DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.notEquals=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto not equals to UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.notEquals=" + UPDATED_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto in DEFAULT_VALOR_TOTAL_DESCONTO or UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.in=" + DEFAULT_VALOR_TOTAL_DESCONTO + "," + UPDATED_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto equals to UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.in=" + UPDATED_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto is not null
        defaultVendaShouldBeFound("valorTotalDesconto.specified=true");

        // Get all the vendaList where valorTotalDesconto is null
        defaultVendaShouldNotBeFound("valorTotalDesconto.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto is greater than or equal to DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto is greater than or equal to UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto is less than or equal to DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto is less than or equal to SMALLER_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto is less than DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.lessThan=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto is less than UPDATED_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.lessThan=" + UPDATED_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalDesconto is greater than DEFAULT_VALOR_TOTAL_DESCONTO
        defaultVendaShouldNotBeFound("valorTotalDesconto.greaterThan=" + DEFAULT_VALOR_TOTAL_DESCONTO);

        // Get all the vendaList where valorTotalDesconto is greater than SMALLER_VALOR_TOTAL_DESCONTO
        defaultVendaShouldBeFound("valorTotalDesconto.greaterThan=" + SMALLER_VALOR_TOTAL_DESCONTO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido equals to DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.equals=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido equals to UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.equals=" + UPDATED_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido not equals to DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.notEquals=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido not equals to UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.notEquals=" + UPDATED_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido in DEFAULT_VALOR_TOTAL_LIQUIDO or UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.in=" + DEFAULT_VALOR_TOTAL_LIQUIDO + "," + UPDATED_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido equals to UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.in=" + UPDATED_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido is not null
        defaultVendaShouldBeFound("valorTotalLiquido.specified=true");

        // Get all the vendaList where valorTotalLiquido is null
        defaultVendaShouldNotBeFound("valorTotalLiquido.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido is greater than or equal to DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido is greater than or equal to UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido is less than or equal to DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido is less than or equal to SMALLER_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido is less than DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.lessThan=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido is less than UPDATED_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.lessThan=" + UPDATED_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalLiquidoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalLiquido is greater than DEFAULT_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldNotBeFound("valorTotalLiquido.greaterThan=" + DEFAULT_VALOR_TOTAL_LIQUIDO);

        // Get all the vendaList where valorTotalLiquido is greater than SMALLER_VALOR_TOTAL_LIQUIDO
        defaultVendaShouldBeFound("valorTotalLiquido.greaterThan=" + SMALLER_VALOR_TOTAL_LIQUIDO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago equals to DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.equals=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago equals to UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.equals=" + UPDATED_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago not equals to DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.notEquals=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago not equals to UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.notEquals=" + UPDATED_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago in DEFAULT_VALOR_TOTAL_PAGO or UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.in=" + DEFAULT_VALOR_TOTAL_PAGO + "," + UPDATED_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago equals to UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.in=" + UPDATED_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago is not null
        defaultVendaShouldBeFound("valorTotalPago.specified=true");

        // Get all the vendaList where valorTotalPago is null
        defaultVendaShouldNotBeFound("valorTotalPago.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago is greater than or equal to DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago is greater than or equal to UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago is less than or equal to DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago is less than or equal to SMALLER_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago is less than DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.lessThan=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago is less than UPDATED_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.lessThan=" + UPDATED_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorTotalPagoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorTotalPago is greater than DEFAULT_VALOR_TOTAL_PAGO
        defaultVendaShouldNotBeFound("valorTotalPago.greaterThan=" + DEFAULT_VALOR_TOTAL_PAGO);

        // Get all the vendaList where valorTotalPago is greater than SMALLER_VALOR_TOTAL_PAGO
        defaultVendaShouldBeFound("valorTotalPago.greaterThan=" + SMALLER_VALOR_TOTAL_PAGO);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante equals to DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.equals=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante equals to UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.equals=" + UPDATED_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante not equals to DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.notEquals=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante not equals to UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.notEquals=" + UPDATED_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante in DEFAULT_VALOR_SALDO_RESTANTE or UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.in=" + DEFAULT_VALOR_SALDO_RESTANTE + "," + UPDATED_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante equals to UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.in=" + UPDATED_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante is not null
        defaultVendaShouldBeFound("valorSaldoRestante.specified=true");

        // Get all the vendaList where valorSaldoRestante is null
        defaultVendaShouldNotBeFound("valorSaldoRestante.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante is greater than or equal to DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.greaterThanOrEqual=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante is greater than or equal to UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.greaterThanOrEqual=" + UPDATED_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante is less than or equal to DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.lessThanOrEqual=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante is less than or equal to SMALLER_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.lessThanOrEqual=" + SMALLER_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante is less than DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.lessThan=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante is less than UPDATED_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.lessThan=" + UPDATED_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByValorSaldoRestanteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valorSaldoRestante is greater than DEFAULT_VALOR_SALDO_RESTANTE
        defaultVendaShouldNotBeFound("valorSaldoRestante.greaterThan=" + DEFAULT_VALOR_SALDO_RESTANTE);

        // Get all the vendaList where valorSaldoRestante is greater than SMALLER_VALOR_SALDO_RESTANTE
        defaultVendaShouldBeFound("valorSaldoRestante.greaterThan=" + SMALLER_VALOR_SALDO_RESTANTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiPagamentoPendenteIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiPagamentoPendente equals to DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldBeFound("indicadorPossuiPagamentoPendente.equals=" + DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);

        // Get all the vendaList where indicadorPossuiPagamentoPendente equals to UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldNotBeFound("indicadorPossuiPagamentoPendente.equals=" + UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiPagamentoPendenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiPagamentoPendente not equals to DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldNotBeFound("indicadorPossuiPagamentoPendente.notEquals=" + DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);

        // Get all the vendaList where indicadorPossuiPagamentoPendente not equals to UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldBeFound("indicadorPossuiPagamentoPendente.notEquals=" + UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiPagamentoPendenteIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiPagamentoPendente in DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE or UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldBeFound(
            "indicadorPossuiPagamentoPendente.in=" +
            DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE +
            "," +
            UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        );

        // Get all the vendaList where indicadorPossuiPagamentoPendente equals to UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE
        defaultVendaShouldNotBeFound("indicadorPossuiPagamentoPendente.in=" + UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiPagamentoPendenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiPagamentoPendente is not null
        defaultVendaShouldBeFound("indicadorPossuiPagamentoPendente.specified=true");

        // Get all the vendaList where indicadorPossuiPagamentoPendente is null
        defaultVendaShouldNotBeFound("indicadorPossuiPagamentoPendente.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiItemPresenteIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiItemPresente equals to DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldBeFound("indicadorPossuiItemPresente.equals=" + DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE);

        // Get all the vendaList where indicadorPossuiItemPresente equals to UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldNotBeFound("indicadorPossuiItemPresente.equals=" + UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiItemPresenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiItemPresente not equals to DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldNotBeFound("indicadorPossuiItemPresente.notEquals=" + DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE);

        // Get all the vendaList where indicadorPossuiItemPresente not equals to UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldBeFound("indicadorPossuiItemPresente.notEquals=" + UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiItemPresenteIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiItemPresente in DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE or UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldBeFound(
            "indicadorPossuiItemPresente.in=" + DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE + "," + UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE
        );

        // Get all the vendaList where indicadorPossuiItemPresente equals to UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE
        defaultVendaShouldNotBeFound("indicadorPossuiItemPresente.in=" + UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorPossuiItemPresenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorPossuiItemPresente is not null
        defaultVendaShouldBeFound("indicadorPossuiItemPresente.specified=true");

        // Get all the vendaList where indicadorPossuiItemPresente is null
        defaultVendaShouldNotBeFound("indicadorPossuiItemPresente.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorBloqueioIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorBloqueio equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultVendaShouldBeFound("indicadorBloqueio.equals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the vendaList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultVendaShouldNotBeFound("indicadorBloqueio.equals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorBloqueioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorBloqueio not equals to DEFAULT_INDICADOR_BLOQUEIO
        defaultVendaShouldNotBeFound("indicadorBloqueio.notEquals=" + DEFAULT_INDICADOR_BLOQUEIO);

        // Get all the vendaList where indicadorBloqueio not equals to UPDATED_INDICADOR_BLOQUEIO
        defaultVendaShouldBeFound("indicadorBloqueio.notEquals=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorBloqueioIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorBloqueio in DEFAULT_INDICADOR_BLOQUEIO or UPDATED_INDICADOR_BLOQUEIO
        defaultVendaShouldBeFound("indicadorBloqueio.in=" + DEFAULT_INDICADOR_BLOQUEIO + "," + UPDATED_INDICADOR_BLOQUEIO);

        // Get all the vendaList where indicadorBloqueio equals to UPDATED_INDICADOR_BLOQUEIO
        defaultVendaShouldNotBeFound("indicadorBloqueio.in=" + UPDATED_INDICADOR_BLOQUEIO);
    }

    @Test
    @Transactional
    void getAllVendasByIndicadorBloqueioIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where indicadorBloqueio is not null
        defaultVendaShouldBeFound("indicadorBloqueio.specified=true");

        // Get all the vendaList where indicadorBloqueio is null
        defaultVendaShouldNotBeFound("indicadorBloqueio.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraCadastro equals to DEFAULT_DATA_HORA_CADASTRO
        defaultVendaShouldBeFound("dataHoraCadastro.equals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the vendaList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultVendaShouldNotBeFound("dataHoraCadastro.equals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraCadastro not equals to DEFAULT_DATA_HORA_CADASTRO
        defaultVendaShouldNotBeFound("dataHoraCadastro.notEquals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the vendaList where dataHoraCadastro not equals to UPDATED_DATA_HORA_CADASTRO
        defaultVendaShouldBeFound("dataHoraCadastro.notEquals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraCadastro in DEFAULT_DATA_HORA_CADASTRO or UPDATED_DATA_HORA_CADASTRO
        defaultVendaShouldBeFound("dataHoraCadastro.in=" + DEFAULT_DATA_HORA_CADASTRO + "," + UPDATED_DATA_HORA_CADASTRO);

        // Get all the vendaList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultVendaShouldNotBeFound("dataHoraCadastro.in=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraCadastro is not null
        defaultVendaShouldBeFound("dataHoraCadastro.specified=true");

        // Get all the vendaList where dataHoraCadastro is null
        defaultVendaShouldNotBeFound("dataHoraCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro equals to DEFAULT_COLABORADOR_CADASTRO
        defaultVendaShouldBeFound("colaboradorCadastro.equals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the vendaList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldNotBeFound("colaboradorCadastro.equals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro not equals to DEFAULT_COLABORADOR_CADASTRO
        defaultVendaShouldNotBeFound("colaboradorCadastro.notEquals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the vendaList where colaboradorCadastro not equals to UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldBeFound("colaboradorCadastro.notEquals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro in DEFAULT_COLABORADOR_CADASTRO or UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldBeFound("colaboradorCadastro.in=" + DEFAULT_COLABORADOR_CADASTRO + "," + UPDATED_COLABORADOR_CADASTRO);

        // Get all the vendaList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldNotBeFound("colaboradorCadastro.in=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro is not null
        defaultVendaShouldBeFound("colaboradorCadastro.specified=true");

        // Get all the vendaList where colaboradorCadastro is null
        defaultVendaShouldNotBeFound("colaboradorCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro contains DEFAULT_COLABORADOR_CADASTRO
        defaultVendaShouldBeFound("colaboradorCadastro.contains=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the vendaList where colaboradorCadastro contains UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldNotBeFound("colaboradorCadastro.contains=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorCadastro does not contain DEFAULT_COLABORADOR_CADASTRO
        defaultVendaShouldNotBeFound("colaboradorCadastro.doesNotContain=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the vendaList where colaboradorCadastro does not contain UPDATED_COLABORADOR_CADASTRO
        defaultVendaShouldBeFound("colaboradorCadastro.doesNotContain=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraAtualizacao equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultVendaShouldBeFound("dataHoraAtualizacao.equals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the vendaList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultVendaShouldNotBeFound("dataHoraAtualizacao.equals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraAtualizacao not equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultVendaShouldNotBeFound("dataHoraAtualizacao.notEquals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the vendaList where dataHoraAtualizacao not equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultVendaShouldBeFound("dataHoraAtualizacao.notEquals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraAtualizacao in DEFAULT_DATA_HORA_ATUALIZACAO or UPDATED_DATA_HORA_ATUALIZACAO
        defaultVendaShouldBeFound("dataHoraAtualizacao.in=" + DEFAULT_DATA_HORA_ATUALIZACAO + "," + UPDATED_DATA_HORA_ATUALIZACAO);

        // Get all the vendaList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultVendaShouldNotBeFound("dataHoraAtualizacao.in=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByDataHoraAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataHoraAtualizacao is not null
        defaultVendaShouldBeFound("dataHoraAtualizacao.specified=true");

        // Get all the vendaList where dataHoraAtualizacao is null
        defaultVendaShouldNotBeFound("dataHoraAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultVendaShouldBeFound("colaboradorAtualizacao.equals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the vendaList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.equals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao not equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.notEquals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the vendaList where colaboradorAtualizacao not equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldBeFound("colaboradorAtualizacao.notEquals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao in DEFAULT_COLABORADOR_ATUALIZACAO or UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldBeFound("colaboradorAtualizacao.in=" + DEFAULT_COLABORADOR_ATUALIZACAO + "," + UPDATED_COLABORADOR_ATUALIZACAO);

        // Get all the vendaList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.in=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao is not null
        defaultVendaShouldBeFound("colaboradorAtualizacao.specified=true");

        // Get all the vendaList where colaboradorAtualizacao is null
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao contains DEFAULT_COLABORADOR_ATUALIZACAO
        defaultVendaShouldBeFound("colaboradorAtualizacao.contains=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the vendaList where colaboradorAtualizacao contains UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.contains=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByColaboradorAtualizacaoNotContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where colaboradorAtualizacao does not contain DEFAULT_COLABORADOR_ATUALIZACAO
        defaultVendaShouldNotBeFound("colaboradorAtualizacao.doesNotContain=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the vendaList where colaboradorAtualizacao does not contain UPDATED_COLABORADOR_ATUALIZACAO
        defaultVendaShouldBeFound("colaboradorAtualizacao.doesNotContain=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllVendasByClienteQueComprouIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Cliente clienteQueComprou;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            clienteQueComprou = ClienteResourceIT.createEntity(em);
            em.persist(clienteQueComprou);
            em.flush();
        } else {
            clienteQueComprou = TestUtil.findAll(em, Cliente.class).get(0);
        }
        em.persist(clienteQueComprou);
        em.flush();
        venda.setClienteQueComprou(clienteQueComprou);
        vendaRepository.saveAndFlush(venda);
        Long clienteQueComprouId = clienteQueComprou.getId();

        // Get all the vendaList where clienteQueComprou equals to clienteQueComprouId
        defaultVendaShouldBeFound("clienteQueComprouId.equals=" + clienteQueComprouId);

        // Get all the vendaList where clienteQueComprou equals to (clienteQueComprouId + 1)
        defaultVendaShouldNotBeFound("clienteQueComprouId.equals=" + (clienteQueComprouId + 1));
    }

    @Test
    @Transactional
    void getAllVendasByColaboradoresQueIndicaramIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Colaborador colaboradoresQueIndicaram;
        if (TestUtil.findAll(em, Colaborador.class).isEmpty()) {
            colaboradoresQueIndicaram = ColaboradorResourceIT.createEntity(em);
            em.persist(colaboradoresQueIndicaram);
            em.flush();
        } else {
            colaboradoresQueIndicaram = TestUtil.findAll(em, Colaborador.class).get(0);
        }
        em.persist(colaboradoresQueIndicaram);
        em.flush();
        venda.addColaboradoresQueIndicaram(colaboradoresQueIndicaram);
        vendaRepository.saveAndFlush(venda);
        Long colaboradoresQueIndicaramId = colaboradoresQueIndicaram.getId();

        // Get all the vendaList where colaboradoresQueIndicaram equals to colaboradoresQueIndicaramId
        defaultVendaShouldBeFound("colaboradoresQueIndicaramId.equals=" + colaboradoresQueIndicaramId);

        // Get all the vendaList where colaboradoresQueIndicaram equals to (colaboradoresQueIndicaramId + 1)
        defaultVendaShouldNotBeFound("colaboradoresQueIndicaramId.equals=" + (colaboradoresQueIndicaramId + 1));
    }

    @Test
    @Transactional
    void getAllVendasByItensVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        ItemVenda itensVenda;
        if (TestUtil.findAll(em, ItemVenda.class).isEmpty()) {
            itensVenda = ItemVendaResourceIT.createEntity(em);
            em.persist(itensVenda);
            em.flush();
        } else {
            itensVenda = TestUtil.findAll(em, ItemVenda.class).get(0);
        }
        em.persist(itensVenda);
        em.flush();
        venda.addItensVenda(itensVenda);
        vendaRepository.saveAndFlush(venda);
        Long itensVendaId = itensVenda.getId();

        // Get all the vendaList where itensVenda equals to itensVendaId
        defaultVendaShouldBeFound("itensVendaId.equals=" + itensVendaId);

        // Get all the vendaList where itensVenda equals to (itensVendaId + 1)
        defaultVendaShouldNotBeFound("itensVendaId.equals=" + (itensVendaId + 1));
    }

    @Test
    @Transactional
    void getAllVendasByPagamentosIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Pagamento pagamentos;
        if (TestUtil.findAll(em, Pagamento.class).isEmpty()) {
            pagamentos = PagamentoResourceIT.createEntity(em);
            em.persist(pagamentos);
            em.flush();
        } else {
            pagamentos = TestUtil.findAll(em, Pagamento.class).get(0);
        }
        em.persist(pagamentos);
        em.flush();
        venda.addPagamentos(pagamentos);
        vendaRepository.saveAndFlush(venda);
        Long pagamentosId = pagamentos.getId();

        // Get all the vendaList where pagamentos equals to pagamentosId
        defaultVendaShouldBeFound("pagamentosId.equals=" + pagamentosId);

        // Get all the vendaList where pagamentos equals to (pagamentosId + 1)
        defaultVendaShouldNotBeFound("pagamentosId.equals=" + (pagamentosId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendaShouldBeFound(String filter) throws Exception {
        restVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].valorTotalBruto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_BRUTO))))
            .andExpect(jsonPath("$.[*].valorTotalDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_DESCONTO))))
            .andExpect(jsonPath("$.[*].valorTotalLiquido").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_LIQUIDO))))
            .andExpect(jsonPath("$.[*].valorTotalPago").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_PAGO))))
            .andExpect(jsonPath("$.[*].valorSaldoRestante").value(hasItem(sameNumber(DEFAULT_VALOR_SALDO_RESTANTE))))
            .andExpect(jsonPath("$.[*].observarcoes").value(hasItem(DEFAULT_OBSERVARCOES.toString())))
            .andExpect(
                jsonPath("$.[*].indicadorPossuiPagamentoPendente")
                    .value(hasItem(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].indicadorPossuiItemPresente").value(hasItem(DEFAULT_INDICADOR_POSSUI_ITEM_PRESENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));

        // Check, that the count call also returns 1
        restVendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendaShouldNotBeFound(String filter) throws Exception {
        restVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVenda() throws Exception {
        // Get the venda
        restVendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda
        Venda updatedVenda = vendaRepository.findById(venda.getId()).get();
        // Disconnect from session so that the updates on updatedVenda are not directly saved in db
        em.detach(updatedVenda);
        updatedVenda
            .dataHora(UPDATED_DATA_HORA)
            .valorTotalBruto(UPDATED_VALOR_TOTAL_BRUTO)
            .valorTotalDesconto(UPDATED_VALOR_TOTAL_DESCONTO)
            .valorTotalLiquido(UPDATED_VALOR_TOTAL_LIQUIDO)
            .valorTotalPago(UPDATED_VALOR_TOTAL_PAGO)
            .valorSaldoRestante(UPDATED_VALOR_SALDO_RESTANTE)
            .observarcoes(UPDATED_OBSERVARCOES)
            .indicadorPossuiPagamentoPendente(UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE)
            .indicadorPossuiItemPresente(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        VendaDTO vendaDTO = vendaMapper.toDto(updatedVenda);

        restVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testVenda.getValorTotalBruto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_BRUTO);
        assertThat(testVenda.getValorTotalDesconto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_DESCONTO);
        assertThat(testVenda.getValorTotalLiquido()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_LIQUIDO);
        assertThat(testVenda.getValorTotalPago()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_PAGO);
        assertThat(testVenda.getValorSaldoRestante()).isEqualByComparingTo(UPDATED_VALOR_SALDO_RESTANTE);
        assertThat(testVenda.getObservarcoes()).isEqualTo(UPDATED_OBSERVARCOES);
        assertThat(testVenda.getIndicadorPossuiPagamentoPendente()).isEqualTo(UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
        assertThat(testVenda.getIndicadorPossuiItemPresente()).isEqualTo(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
        assertThat(testVenda.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testVenda.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testVenda.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testVenda.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVendaWithPatch() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda using partial update
        Venda partialUpdatedVenda = new Venda();
        partialUpdatedVenda.setId(venda.getId());

        partialUpdatedVenda
            .valorTotalLiquido(UPDATED_VALOR_TOTAL_LIQUIDO)
            .observarcoes(UPDATED_OBSERVARCOES)
            .indicadorPossuiItemPresente(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO);

        restVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenda))
            )
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testVenda.getValorTotalBruto()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_BRUTO);
        assertThat(testVenda.getValorTotalDesconto()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_DESCONTO);
        assertThat(testVenda.getValorTotalLiquido()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_LIQUIDO);
        assertThat(testVenda.getValorTotalPago()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_PAGO);
        assertThat(testVenda.getValorSaldoRestante()).isEqualByComparingTo(DEFAULT_VALOR_SALDO_RESTANTE);
        assertThat(testVenda.getObservarcoes()).isEqualTo(UPDATED_OBSERVARCOES);
        assertThat(testVenda.getIndicadorPossuiPagamentoPendente()).isEqualTo(DEFAULT_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
        assertThat(testVenda.getIndicadorPossuiItemPresente()).isEqualTo(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
        assertThat(testVenda.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testVenda.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testVenda.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testVenda.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdateVendaWithPatch() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda using partial update
        Venda partialUpdatedVenda = new Venda();
        partialUpdatedVenda.setId(venda.getId());

        partialUpdatedVenda
            .dataHora(UPDATED_DATA_HORA)
            .valorTotalBruto(UPDATED_VALOR_TOTAL_BRUTO)
            .valorTotalDesconto(UPDATED_VALOR_TOTAL_DESCONTO)
            .valorTotalLiquido(UPDATED_VALOR_TOTAL_LIQUIDO)
            .valorTotalPago(UPDATED_VALOR_TOTAL_PAGO)
            .valorSaldoRestante(UPDATED_VALOR_SALDO_RESTANTE)
            .observarcoes(UPDATED_OBSERVARCOES)
            .indicadorPossuiPagamentoPendente(UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE)
            .indicadorPossuiItemPresente(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenda))
            )
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testVenda.getValorTotalBruto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_BRUTO);
        assertThat(testVenda.getValorTotalDesconto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_DESCONTO);
        assertThat(testVenda.getValorTotalLiquido()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_LIQUIDO);
        assertThat(testVenda.getValorTotalPago()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_PAGO);
        assertThat(testVenda.getValorSaldoRestante()).isEqualByComparingTo(UPDATED_VALOR_SALDO_RESTANTE);
        assertThat(testVenda.getObservarcoes()).isEqualTo(UPDATED_OBSERVARCOES);
        assertThat(testVenda.getIndicadorPossuiPagamentoPendente()).isEqualTo(UPDATED_INDICADOR_POSSUI_PAGAMENTO_PENDENTE);
        assertThat(testVenda.getIndicadorPossuiItemPresente()).isEqualTo(UPDATED_INDICADOR_POSSUI_ITEM_PRESENTE);
        assertThat(testVenda.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testVenda.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testVenda.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testVenda.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vendaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();
        venda.setId(count.incrementAndGet());

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeDelete = vendaRepository.findAll().size();

        // Delete the venda
        restVendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, venda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
