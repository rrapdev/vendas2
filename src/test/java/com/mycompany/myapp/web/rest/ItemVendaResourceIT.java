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
import com.mycompany.myapp.domain.Servico;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.ItemVendaRepository;
import com.mycompany.myapp.service.ItemVendaService;
import com.mycompany.myapp.service.criteria.ItemVendaCriteria;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
import com.mycompany.myapp.service.mapper.ItemVendaMapper;
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
 * Integration tests for the {@link ItemVendaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ItemVendaResourceIT {

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;
    private static final Integer SMALLER_QUANTIDADE = 1 - 1;

    private static final BigDecimal DEFAULT_VALOR_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UNITARIO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_UNITARIO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_DESCONTO_PERCENTUAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR_DESCONTO_PERCENTUAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR_DESCONTO_PERCENTUAL = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_VALOR_DESCONTO_REAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DESCONTO_REAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_DESCONTO_REAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_COM_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_COM_DESCONTO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_TOTAL_COM_DESCONTO = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_INDICADOR_ITEM_PRESENTE = false;
    private static final Boolean UPDATED_INDICADOR_ITEM_PRESENTE = true;

    private static final Instant DEFAULT_DATA_HORA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_CADASTRO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COLABORADOR_ATUALIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_COLABORADOR_ATUALIZACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/item-vendas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private ItemVendaRepository itemVendaRepositoryMock;

    @Autowired
    private ItemVendaMapper itemVendaMapper;

    @Mock
    private ItemVendaService itemVendaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemVendaMockMvc;

    private ItemVenda itemVenda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVenda createEntity(EntityManager em) {
        ItemVenda itemVenda = new ItemVenda()
            .dataHora(DEFAULT_DATA_HORA)
            .quantidade(DEFAULT_QUANTIDADE)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .valorDescontoPercentual(DEFAULT_VALOR_DESCONTO_PERCENTUAL)
            .valorDescontoReal(DEFAULT_VALOR_DESCONTO_REAL)
            .valorTotalComDesconto(DEFAULT_VALOR_TOTAL_COM_DESCONTO)
            .indicadorItemPresente(DEFAULT_INDICADOR_ITEM_PRESENTE)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return itemVenda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVenda createUpdatedEntity(EntityManager em) {
        ItemVenda itemVenda = new ItemVenda()
            .dataHora(UPDATED_DATA_HORA)
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorDescontoPercentual(UPDATED_VALOR_DESCONTO_PERCENTUAL)
            .valorDescontoReal(UPDATED_VALOR_DESCONTO_REAL)
            .valorTotalComDesconto(UPDATED_VALOR_TOTAL_COM_DESCONTO)
            .indicadorItemPresente(UPDATED_INDICADOR_ITEM_PRESENTE)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return itemVenda;
    }

    @BeforeEach
    public void initTest() {
        itemVenda = createEntity(em);
    }

    @Test
    @Transactional
    void createItemVenda() throws Exception {
        int databaseSizeBeforeCreate = itemVendaRepository.findAll().size();
        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);
        restItemVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemVenda.getValorUnitario()).isEqualByComparingTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testItemVenda.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testItemVenda.getValorDescontoPercentual()).isEqualByComparingTo(DEFAULT_VALOR_DESCONTO_PERCENTUAL);
        assertThat(testItemVenda.getValorDescontoReal()).isEqualByComparingTo(DEFAULT_VALOR_DESCONTO_REAL);
        assertThat(testItemVenda.getValorTotalComDesconto()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL_COM_DESCONTO);
        assertThat(testItemVenda.getIndicadorItemPresente()).isEqualTo(DEFAULT_INDICADOR_ITEM_PRESENTE);
        assertThat(testItemVenda.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testItemVenda.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testItemVenda.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testItemVenda.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createItemVendaWithExistingId() throws Exception {
        // Create the ItemVenda with an existing ID
        itemVenda.setId(1L);
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        int databaseSizeBeforeCreate = itemVendaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemVendaRepository.findAll().size();
        // set the field null
        itemVenda.setQuantidade(null);

        // Create the ItemVenda, which fails.
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        restItemVendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isBadRequest());

        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemVendas() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(sameNumber(DEFAULT_VALOR_UNITARIO))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].valorDescontoPercentual").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO_PERCENTUAL))))
            .andExpect(jsonPath("$.[*].valorDescontoReal").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO_REAL))))
            .andExpect(jsonPath("$.[*].valorTotalComDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COM_DESCONTO))))
            .andExpect(jsonPath("$.[*].indicadorItemPresente").value(hasItem(DEFAULT_INDICADOR_ITEM_PRESENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemVendasWithEagerRelationshipsIsEnabled() throws Exception {
        when(itemVendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemVendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemVendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemVendasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(itemVendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemVendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemVendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get the itemVenda
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL_ID, itemVenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemVenda.getId().intValue()))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.valorUnitario").value(sameNumber(DEFAULT_VALOR_UNITARIO)))
            .andExpect(jsonPath("$.valorTotal").value(sameNumber(DEFAULT_VALOR_TOTAL)))
            .andExpect(jsonPath("$.valorDescontoPercentual").value(sameNumber(DEFAULT_VALOR_DESCONTO_PERCENTUAL)))
            .andExpect(jsonPath("$.valorDescontoReal").value(sameNumber(DEFAULT_VALOR_DESCONTO_REAL)))
            .andExpect(jsonPath("$.valorTotalComDesconto").value(sameNumber(DEFAULT_VALOR_TOTAL_COM_DESCONTO)))
            .andExpect(jsonPath("$.indicadorItemPresente").value(DEFAULT_INDICADOR_ITEM_PRESENTE.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getItemVendasByIdFiltering() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        Long id = itemVenda.getId();

        defaultItemVendaShouldBeFound("id.equals=" + id);
        defaultItemVendaShouldNotBeFound("id.notEquals=" + id);

        defaultItemVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultItemVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemVendaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHora equals to DEFAULT_DATA_HORA
        defaultItemVendaShouldBeFound("dataHora.equals=" + DEFAULT_DATA_HORA);

        // Get all the itemVendaList where dataHora equals to UPDATED_DATA_HORA
        defaultItemVendaShouldNotBeFound("dataHora.equals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHora not equals to DEFAULT_DATA_HORA
        defaultItemVendaShouldNotBeFound("dataHora.notEquals=" + DEFAULT_DATA_HORA);

        // Get all the itemVendaList where dataHora not equals to UPDATED_DATA_HORA
        defaultItemVendaShouldBeFound("dataHora.notEquals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHora in DEFAULT_DATA_HORA or UPDATED_DATA_HORA
        defaultItemVendaShouldBeFound("dataHora.in=" + DEFAULT_DATA_HORA + "," + UPDATED_DATA_HORA);

        // Get all the itemVendaList where dataHora equals to UPDATED_DATA_HORA
        defaultItemVendaShouldNotBeFound("dataHora.in=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHora is not null
        defaultItemVendaShouldBeFound("dataHora.specified=true");

        // Get all the itemVendaList where dataHora is null
        defaultItemVendaShouldNotBeFound("dataHora.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade equals to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade not equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the itemVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is not null
        defaultItemVendaShouldBeFound("quantidade.specified=true");

        // Get all the itemVendaList where quantidade is null
        defaultItemVendaShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is less than DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is less than UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is greater than SMALLER_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario equals to DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.equals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.equals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario not equals to DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.notEquals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario not equals to UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.notEquals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario in DEFAULT_VALOR_UNITARIO or UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.in=" + DEFAULT_VALOR_UNITARIO + "," + UPDATED_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.in=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario is not null
        defaultItemVendaShouldBeFound("valorUnitario.specified=true");

        // Get all the itemVendaList where valorUnitario is null
        defaultItemVendaShouldNotBeFound("valorUnitario.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario is greater than or equal to DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.greaterThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario is greater than or equal to UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.greaterThanOrEqual=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario is less than or equal to DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.lessThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario is less than or equal to SMALLER_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.lessThanOrEqual=" + SMALLER_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario is less than DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.lessThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario is less than UPDATED_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.lessThan=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorUnitario is greater than DEFAULT_VALOR_UNITARIO
        defaultItemVendaShouldNotBeFound("valorUnitario.greaterThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the itemVendaList where valorUnitario is greater than SMALLER_VALOR_UNITARIO
        defaultItemVendaShouldBeFound("valorUnitario.greaterThan=" + SMALLER_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal equals to DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.equals=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.equals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal not equals to DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.notEquals=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal not equals to UPDATED_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.notEquals=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal in DEFAULT_VALOR_TOTAL or UPDATED_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.in=" + DEFAULT_VALOR_TOTAL + "," + UPDATED_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal equals to UPDATED_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.in=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal is not null
        defaultItemVendaShouldBeFound("valorTotal.specified=true");

        // Get all the itemVendaList where valorTotal is null
        defaultItemVendaShouldNotBeFound("valorTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal is greater than or equal to DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal is greater than or equal to UPDATED_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal is less than or equal to DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal is less than or equal to SMALLER_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.lessThanOrEqual=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal is less than DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.lessThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal is less than UPDATED_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.lessThan=" + UPDATED_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotal is greater than DEFAULT_VALOR_TOTAL
        defaultItemVendaShouldNotBeFound("valorTotal.greaterThan=" + DEFAULT_VALOR_TOTAL);

        // Get all the itemVendaList where valorTotal is greater than SMALLER_VALOR_TOTAL
        defaultItemVendaShouldBeFound("valorTotal.greaterThan=" + SMALLER_VALOR_TOTAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual equals to DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound("valorDescontoPercentual.equals=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual equals to UPDATED_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.equals=" + UPDATED_VALOR_DESCONTO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual not equals to DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.notEquals=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual not equals to UPDATED_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound("valorDescontoPercentual.notEquals=" + UPDATED_VALOR_DESCONTO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual in DEFAULT_VALOR_DESCONTO_PERCENTUAL or UPDATED_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound(
            "valorDescontoPercentual.in=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL + "," + UPDATED_VALOR_DESCONTO_PERCENTUAL
        );

        // Get all the itemVendaList where valorDescontoPercentual equals to UPDATED_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.in=" + UPDATED_VALOR_DESCONTO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual is not null
        defaultItemVendaShouldBeFound("valorDescontoPercentual.specified=true");

        // Get all the itemVendaList where valorDescontoPercentual is null
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual is greater than or equal to DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound("valorDescontoPercentual.greaterThanOrEqual=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual is greater than or equal to (DEFAULT_VALOR_DESCONTO_PERCENTUAL.add(BigDecimal.ONE))
        defaultItemVendaShouldNotBeFound(
            "valorDescontoPercentual.greaterThanOrEqual=" + (DEFAULT_VALOR_DESCONTO_PERCENTUAL.add(BigDecimal.ONE))
        );
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual is less than or equal to DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound("valorDescontoPercentual.lessThanOrEqual=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual is less than or equal to SMALLER_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.lessThanOrEqual=" + SMALLER_VALOR_DESCONTO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual is less than DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.lessThan=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual is less than (DEFAULT_VALOR_DESCONTO_PERCENTUAL.add(BigDecimal.ONE))
        defaultItemVendaShouldBeFound("valorDescontoPercentual.lessThan=" + (DEFAULT_VALOR_DESCONTO_PERCENTUAL.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoPercentualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoPercentual is greater than DEFAULT_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldNotBeFound("valorDescontoPercentual.greaterThan=" + DEFAULT_VALOR_DESCONTO_PERCENTUAL);

        // Get all the itemVendaList where valorDescontoPercentual is greater than SMALLER_VALOR_DESCONTO_PERCENTUAL
        defaultItemVendaShouldBeFound("valorDescontoPercentual.greaterThan=" + SMALLER_VALOR_DESCONTO_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal equals to DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.equals=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal equals to UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.equals=" + UPDATED_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal not equals to DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.notEquals=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal not equals to UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.notEquals=" + UPDATED_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal in DEFAULT_VALOR_DESCONTO_REAL or UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.in=" + DEFAULT_VALOR_DESCONTO_REAL + "," + UPDATED_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal equals to UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.in=" + UPDATED_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal is not null
        defaultItemVendaShouldBeFound("valorDescontoReal.specified=true");

        // Get all the itemVendaList where valorDescontoReal is null
        defaultItemVendaShouldNotBeFound("valorDescontoReal.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal is greater than or equal to DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.greaterThanOrEqual=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal is greater than or equal to UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.greaterThanOrEqual=" + UPDATED_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal is less than or equal to DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.lessThanOrEqual=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal is less than or equal to SMALLER_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.lessThanOrEqual=" + SMALLER_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal is less than DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.lessThan=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal is less than UPDATED_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.lessThan=" + UPDATED_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorDescontoRealIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorDescontoReal is greater than DEFAULT_VALOR_DESCONTO_REAL
        defaultItemVendaShouldNotBeFound("valorDescontoReal.greaterThan=" + DEFAULT_VALOR_DESCONTO_REAL);

        // Get all the itemVendaList where valorDescontoReal is greater than SMALLER_VALOR_DESCONTO_REAL
        defaultItemVendaShouldBeFound("valorDescontoReal.greaterThan=" + SMALLER_VALOR_DESCONTO_REAL);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto equals to DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.equals=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto equals to UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.equals=" + UPDATED_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto not equals to DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.notEquals=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto not equals to UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.notEquals=" + UPDATED_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto in DEFAULT_VALOR_TOTAL_COM_DESCONTO or UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound(
            "valorTotalComDesconto.in=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO + "," + UPDATED_VALOR_TOTAL_COM_DESCONTO
        );

        // Get all the itemVendaList where valorTotalComDesconto equals to UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.in=" + UPDATED_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto is not null
        defaultItemVendaShouldBeFound("valorTotalComDesconto.specified=true");

        // Get all the itemVendaList where valorTotalComDesconto is null
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto is greater than or equal to DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.greaterThanOrEqual=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto is greater than or equal to UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.greaterThanOrEqual=" + UPDATED_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto is less than or equal to DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.lessThanOrEqual=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto is less than or equal to SMALLER_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.lessThanOrEqual=" + SMALLER_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto is less than DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.lessThan=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto is less than UPDATED_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.lessThan=" + UPDATED_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByValorTotalComDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valorTotalComDesconto is greater than DEFAULT_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldNotBeFound("valorTotalComDesconto.greaterThan=" + DEFAULT_VALOR_TOTAL_COM_DESCONTO);

        // Get all the itemVendaList where valorTotalComDesconto is greater than SMALLER_VALOR_TOTAL_COM_DESCONTO
        defaultItemVendaShouldBeFound("valorTotalComDesconto.greaterThan=" + SMALLER_VALOR_TOTAL_COM_DESCONTO);
    }

    @Test
    @Transactional
    void getAllItemVendasByIndicadorItemPresenteIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where indicadorItemPresente equals to DEFAULT_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldBeFound("indicadorItemPresente.equals=" + DEFAULT_INDICADOR_ITEM_PRESENTE);

        // Get all the itemVendaList where indicadorItemPresente equals to UPDATED_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldNotBeFound("indicadorItemPresente.equals=" + UPDATED_INDICADOR_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllItemVendasByIndicadorItemPresenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where indicadorItemPresente not equals to DEFAULT_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldNotBeFound("indicadorItemPresente.notEquals=" + DEFAULT_INDICADOR_ITEM_PRESENTE);

        // Get all the itemVendaList where indicadorItemPresente not equals to UPDATED_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldBeFound("indicadorItemPresente.notEquals=" + UPDATED_INDICADOR_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllItemVendasByIndicadorItemPresenteIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where indicadorItemPresente in DEFAULT_INDICADOR_ITEM_PRESENTE or UPDATED_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldBeFound(
            "indicadorItemPresente.in=" + DEFAULT_INDICADOR_ITEM_PRESENTE + "," + UPDATED_INDICADOR_ITEM_PRESENTE
        );

        // Get all the itemVendaList where indicadorItemPresente equals to UPDATED_INDICADOR_ITEM_PRESENTE
        defaultItemVendaShouldNotBeFound("indicadorItemPresente.in=" + UPDATED_INDICADOR_ITEM_PRESENTE);
    }

    @Test
    @Transactional
    void getAllItemVendasByIndicadorItemPresenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where indicadorItemPresente is not null
        defaultItemVendaShouldBeFound("indicadorItemPresente.specified=true");

        // Get all the itemVendaList where indicadorItemPresente is null
        defaultItemVendaShouldNotBeFound("indicadorItemPresente.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraCadastro equals to DEFAULT_DATA_HORA_CADASTRO
        defaultItemVendaShouldBeFound("dataHoraCadastro.equals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the itemVendaList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultItemVendaShouldNotBeFound("dataHoraCadastro.equals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraCadastro not equals to DEFAULT_DATA_HORA_CADASTRO
        defaultItemVendaShouldNotBeFound("dataHoraCadastro.notEquals=" + DEFAULT_DATA_HORA_CADASTRO);

        // Get all the itemVendaList where dataHoraCadastro not equals to UPDATED_DATA_HORA_CADASTRO
        defaultItemVendaShouldBeFound("dataHoraCadastro.notEquals=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraCadastro in DEFAULT_DATA_HORA_CADASTRO or UPDATED_DATA_HORA_CADASTRO
        defaultItemVendaShouldBeFound("dataHoraCadastro.in=" + DEFAULT_DATA_HORA_CADASTRO + "," + UPDATED_DATA_HORA_CADASTRO);

        // Get all the itemVendaList where dataHoraCadastro equals to UPDATED_DATA_HORA_CADASTRO
        defaultItemVendaShouldNotBeFound("dataHoraCadastro.in=" + UPDATED_DATA_HORA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraCadastro is not null
        defaultItemVendaShouldBeFound("dataHoraCadastro.specified=true");

        // Get all the itemVendaList where dataHoraCadastro is null
        defaultItemVendaShouldNotBeFound("dataHoraCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro equals to DEFAULT_COLABORADOR_CADASTRO
        defaultItemVendaShouldBeFound("colaboradorCadastro.equals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the itemVendaList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.equals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro not equals to DEFAULT_COLABORADOR_CADASTRO
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.notEquals=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the itemVendaList where colaboradorCadastro not equals to UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldBeFound("colaboradorCadastro.notEquals=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro in DEFAULT_COLABORADOR_CADASTRO or UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldBeFound("colaboradorCadastro.in=" + DEFAULT_COLABORADOR_CADASTRO + "," + UPDATED_COLABORADOR_CADASTRO);

        // Get all the itemVendaList where colaboradorCadastro equals to UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.in=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro is not null
        defaultItemVendaShouldBeFound("colaboradorCadastro.specified=true");

        // Get all the itemVendaList where colaboradorCadastro is null
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroContainsSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro contains DEFAULT_COLABORADOR_CADASTRO
        defaultItemVendaShouldBeFound("colaboradorCadastro.contains=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the itemVendaList where colaboradorCadastro contains UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.contains=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorCadastro does not contain DEFAULT_COLABORADOR_CADASTRO
        defaultItemVendaShouldNotBeFound("colaboradorCadastro.doesNotContain=" + DEFAULT_COLABORADOR_CADASTRO);

        // Get all the itemVendaList where colaboradorCadastro does not contain UPDATED_COLABORADOR_CADASTRO
        defaultItemVendaShouldBeFound("colaboradorCadastro.doesNotContain=" + UPDATED_COLABORADOR_CADASTRO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraAtualizacao equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldBeFound("dataHoraAtualizacao.equals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the itemVendaList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("dataHoraAtualizacao.equals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraAtualizacao not equals to DEFAULT_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("dataHoraAtualizacao.notEquals=" + DEFAULT_DATA_HORA_ATUALIZACAO);

        // Get all the itemVendaList where dataHoraAtualizacao not equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldBeFound("dataHoraAtualizacao.notEquals=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraAtualizacao in DEFAULT_DATA_HORA_ATUALIZACAO or UPDATED_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldBeFound("dataHoraAtualizacao.in=" + DEFAULT_DATA_HORA_ATUALIZACAO + "," + UPDATED_DATA_HORA_ATUALIZACAO);

        // Get all the itemVendaList where dataHoraAtualizacao equals to UPDATED_DATA_HORA_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("dataHoraAtualizacao.in=" + UPDATED_DATA_HORA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByDataHoraAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where dataHoraAtualizacao is not null
        defaultItemVendaShouldBeFound("dataHoraAtualizacao.specified=true");

        // Get all the itemVendaList where dataHoraAtualizacao is null
        defaultItemVendaShouldNotBeFound("dataHoraAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldBeFound("colaboradorAtualizacao.equals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the itemVendaList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.equals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao not equals to DEFAULT_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.notEquals=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the itemVendaList where colaboradorAtualizacao not equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldBeFound("colaboradorAtualizacao.notEquals=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao in DEFAULT_COLABORADOR_ATUALIZACAO or UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldBeFound(
            "colaboradorAtualizacao.in=" + DEFAULT_COLABORADOR_ATUALIZACAO + "," + UPDATED_COLABORADOR_ATUALIZACAO
        );

        // Get all the itemVendaList where colaboradorAtualizacao equals to UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.in=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao is not null
        defaultItemVendaShouldBeFound("colaboradorAtualizacao.specified=true");

        // Get all the itemVendaList where colaboradorAtualizacao is null
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoContainsSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao contains DEFAULT_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldBeFound("colaboradorAtualizacao.contains=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the itemVendaList where colaboradorAtualizacao contains UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.contains=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorAtualizacaoNotContainsSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where colaboradorAtualizacao does not contain DEFAULT_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldNotBeFound("colaboradorAtualizacao.doesNotContain=" + DEFAULT_COLABORADOR_ATUALIZACAO);

        // Get all the itemVendaList where colaboradorAtualizacao does not contain UPDATED_COLABORADOR_ATUALIZACAO
        defaultItemVendaShouldBeFound("colaboradorAtualizacao.doesNotContain=" + UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllItemVendasByServicoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
        Servico servico;
        if (TestUtil.findAll(em, Servico.class).isEmpty()) {
            servico = ServicoResourceIT.createEntity(em);
            em.persist(servico);
            em.flush();
        } else {
            servico = TestUtil.findAll(em, Servico.class).get(0);
        }
        em.persist(servico);
        em.flush();
        itemVenda.setServico(servico);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long servicoId = servico.getId();

        // Get all the itemVendaList where servico equals to servicoId
        defaultItemVendaShouldBeFound("servicoId.equals=" + servicoId);

        // Get all the itemVendaList where servico equals to (servicoId + 1)
        defaultItemVendaShouldNotBeFound("servicoId.equals=" + (servicoId + 1));
    }

    @Test
    @Transactional
    void getAllItemVendasByColaboradorQueIndicouIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
        Colaborador colaboradorQueIndicou;
        if (TestUtil.findAll(em, Colaborador.class).isEmpty()) {
            colaboradorQueIndicou = ColaboradorResourceIT.createEntity(em);
            em.persist(colaboradorQueIndicou);
            em.flush();
        } else {
            colaboradorQueIndicou = TestUtil.findAll(em, Colaborador.class).get(0);
        }
        em.persist(colaboradorQueIndicou);
        em.flush();
        itemVenda.setColaboradorQueIndicou(colaboradorQueIndicou);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long colaboradorQueIndicouId = colaboradorQueIndicou.getId();

        // Get all the itemVendaList where colaboradorQueIndicou equals to colaboradorQueIndicouId
        defaultItemVendaShouldBeFound("colaboradorQueIndicouId.equals=" + colaboradorQueIndicouId);

        // Get all the itemVendaList where colaboradorQueIndicou equals to (colaboradorQueIndicouId + 1)
        defaultItemVendaShouldNotBeFound("colaboradorQueIndicouId.equals=" + (colaboradorQueIndicouId + 1));
    }

    @Test
    @Transactional
    void getAllItemVendasByClienteQueVaiRealizarIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
        Cliente clienteQueVaiRealizar;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            clienteQueVaiRealizar = ClienteResourceIT.createEntity(em);
            em.persist(clienteQueVaiRealizar);
            em.flush();
        } else {
            clienteQueVaiRealizar = TestUtil.findAll(em, Cliente.class).get(0);
        }
        em.persist(clienteQueVaiRealizar);
        em.flush();
        itemVenda.setClienteQueVaiRealizar(clienteQueVaiRealizar);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long clienteQueVaiRealizarId = clienteQueVaiRealizar.getId();

        // Get all the itemVendaList where clienteQueVaiRealizar equals to clienteQueVaiRealizarId
        defaultItemVendaShouldBeFound("clienteQueVaiRealizarId.equals=" + clienteQueVaiRealizarId);

        // Get all the itemVendaList where clienteQueVaiRealizar equals to (clienteQueVaiRealizarId + 1)
        defaultItemVendaShouldNotBeFound("clienteQueVaiRealizarId.equals=" + (clienteQueVaiRealizarId + 1));
    }

    @Test
    @Transactional
    void getAllItemVendasByVendasIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
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
        itemVenda.addVendas(vendas);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long vendasId = vendas.getId();

        // Get all the itemVendaList where vendas equals to vendasId
        defaultItemVendaShouldBeFound("vendasId.equals=" + vendasId);

        // Get all the itemVendaList where vendas equals to (vendasId + 1)
        defaultItemVendaShouldNotBeFound("vendasId.equals=" + (vendasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemVendaShouldBeFound(String filter) throws Exception {
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(sameNumber(DEFAULT_VALOR_UNITARIO))))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL))))
            .andExpect(jsonPath("$.[*].valorDescontoPercentual").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO_PERCENTUAL))))
            .andExpect(jsonPath("$.[*].valorDescontoReal").value(hasItem(sameNumber(DEFAULT_VALOR_DESCONTO_REAL))))
            .andExpect(jsonPath("$.[*].valorTotalComDesconto").value(hasItem(sameNumber(DEFAULT_VALOR_TOTAL_COM_DESCONTO))))
            .andExpect(jsonPath("$.[*].indicadorItemPresente").value(hasItem(DEFAULT_INDICADOR_ITEM_PRESENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));

        // Check, that the count call also returns 1
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemVendaShouldNotBeFound(String filter) throws Exception {
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemVendaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingItemVenda() throws Exception {
        // Get the itemVenda
        restItemVendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();

        // Update the itemVenda
        ItemVenda updatedItemVenda = itemVendaRepository.findById(itemVenda.getId()).get();
        // Disconnect from session so that the updates on updatedItemVenda are not directly saved in db
        em.detach(updatedItemVenda);
        updatedItemVenda
            .dataHora(UPDATED_DATA_HORA)
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorDescontoPercentual(UPDATED_VALOR_DESCONTO_PERCENTUAL)
            .valorDescontoReal(UPDATED_VALOR_DESCONTO_REAL)
            .valorTotalComDesconto(UPDATED_VALOR_TOTAL_COM_DESCONTO)
            .indicadorItemPresente(UPDATED_INDICADOR_ITEM_PRESENTE)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(updatedItemVenda);

        restItemVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemVendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemVenda.getValorUnitario()).isEqualByComparingTo(UPDATED_VALOR_UNITARIO);
        assertThat(testItemVenda.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testItemVenda.getValorDescontoPercentual()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_PERCENTUAL);
        assertThat(testItemVenda.getValorDescontoReal()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_REAL);
        assertThat(testItemVenda.getValorTotalComDesconto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COM_DESCONTO);
        assertThat(testItemVenda.getIndicadorItemPresente()).isEqualTo(UPDATED_INDICADOR_ITEM_PRESENTE);
        assertThat(testItemVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testItemVenda.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testItemVenda.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testItemVenda.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemVendaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemVendaWithPatch() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();

        // Update the itemVenda using partial update
        ItemVenda partialUpdatedItemVenda = new ItemVenda();
        partialUpdatedItemVenda.setId(itemVenda.getId());

        partialUpdatedItemVenda
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorDescontoPercentual(UPDATED_VALOR_DESCONTO_PERCENTUAL)
            .valorDescontoReal(UPDATED_VALOR_DESCONTO_REAL)
            .valorTotalComDesconto(UPDATED_VALOR_TOTAL_COM_DESCONTO)
            .indicadorItemPresente(UPDATED_INDICADOR_ITEM_PRESENTE)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO);

        restItemVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemVenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVenda))
            )
            .andExpect(status().isOk());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getDataHora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemVenda.getValorUnitario()).isEqualByComparingTo(UPDATED_VALOR_UNITARIO);
        assertThat(testItemVenda.getValorTotal()).isEqualByComparingTo(DEFAULT_VALOR_TOTAL);
        assertThat(testItemVenda.getValorDescontoPercentual()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_PERCENTUAL);
        assertThat(testItemVenda.getValorDescontoReal()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_REAL);
        assertThat(testItemVenda.getValorTotalComDesconto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COM_DESCONTO);
        assertThat(testItemVenda.getIndicadorItemPresente()).isEqualTo(UPDATED_INDICADOR_ITEM_PRESENTE);
        assertThat(testItemVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testItemVenda.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testItemVenda.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testItemVenda.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdateItemVendaWithPatch() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();

        // Update the itemVenda using partial update
        ItemVenda partialUpdatedItemVenda = new ItemVenda();
        partialUpdatedItemVenda.setId(itemVenda.getId());

        partialUpdatedItemVenda
            .dataHora(UPDATED_DATA_HORA)
            .quantidade(UPDATED_QUANTIDADE)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorDescontoPercentual(UPDATED_VALOR_DESCONTO_PERCENTUAL)
            .valorDescontoReal(UPDATED_VALOR_DESCONTO_REAL)
            .valorTotalComDesconto(UPDATED_VALOR_TOTAL_COM_DESCONTO)
            .indicadorItemPresente(UPDATED_INDICADOR_ITEM_PRESENTE)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restItemVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemVenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVenda))
            )
            .andExpect(status().isOk());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getDataHora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemVenda.getValorUnitario()).isEqualByComparingTo(UPDATED_VALOR_UNITARIO);
        assertThat(testItemVenda.getValorTotal()).isEqualByComparingTo(UPDATED_VALOR_TOTAL);
        assertThat(testItemVenda.getValorDescontoPercentual()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_PERCENTUAL);
        assertThat(testItemVenda.getValorDescontoReal()).isEqualByComparingTo(UPDATED_VALOR_DESCONTO_REAL);
        assertThat(testItemVenda.getValorTotalComDesconto()).isEqualByComparingTo(UPDATED_VALOR_TOTAL_COM_DESCONTO);
        assertThat(testItemVenda.getIndicadorItemPresente()).isEqualTo(UPDATED_INDICADOR_ITEM_PRESENTE);
        assertThat(testItemVenda.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testItemVenda.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testItemVenda.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testItemVenda.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemVendaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();
        itemVenda.setId(count.incrementAndGet());

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVendaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemVendaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeDelete = itemVendaRepository.findAll().size();

        // Delete the itemVenda
        restItemVendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemVenda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
