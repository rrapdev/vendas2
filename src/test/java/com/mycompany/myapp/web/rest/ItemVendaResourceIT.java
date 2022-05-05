package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.repository.ItemVendaRepository;
import com.mycompany.myapp.service.ItemVendaService;
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

    private static final BigDecimal DEFAULT_VALOR_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UNITARIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_DESCONTO_PERCENTUAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR_DESCONTO_PERCENTUAL = new BigDecimal(1);

    private static final BigDecimal DEFAULT_VALOR_DESCONTO_REAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_DESCONTO_REAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_COM_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_COM_DESCONTO = new BigDecimal(2);

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
