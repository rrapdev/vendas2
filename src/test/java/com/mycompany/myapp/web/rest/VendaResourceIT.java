package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.VendaRepository;
import com.mycompany.myapp.service.VendaService;
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

    private static final BigDecimal DEFAULT_VALOR_TOTAL_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_DESCONTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_LIQUIDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_LIQUIDO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_TOTAL_PAGO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL_PAGO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_SALDO_RESTANTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SALDO_RESTANTE = new BigDecimal(2);

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
