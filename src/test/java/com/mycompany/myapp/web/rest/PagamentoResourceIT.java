package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.enumeration.BandeiraCartao;
import com.mycompany.myapp.domain.enumeration.FormaPagamento;
import com.mycompany.myapp.repository.PagamentoRepository;
import com.mycompany.myapp.service.PagamentoService;
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

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PARCELAS = 1;
    private static final Integer UPDATED_NUMERO_PARCELAS = 2;

    private static final BandeiraCartao DEFAULT_BANDEIRA_CARTAO = BandeiraCartao.MASTER;
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
