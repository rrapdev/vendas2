package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.enumeration.TipoIndicadorSaldo;
import com.mycompany.myapp.repository.CarteiraClienteRepository;
import com.mycompany.myapp.service.CarteiraClienteService;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.CarteiraClienteMapper;
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
 * Integration tests for the {@link CarteiraClienteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CarteiraClienteResourceIT {

    private static final BigDecimal DEFAULT_SALDO_CONSOLIDADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_CONSOLIDADO = new BigDecimal(2);

    private static final TipoIndicadorSaldo DEFAULT_TIPO_INDICADOR_SALDO = TipoIndicadorSaldo.POSITIVO;
    private static final TipoIndicadorSaldo UPDATED_TIPO_INDICADOR_SALDO = TipoIndicadorSaldo.NEGATIVO;

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

    private static final String ENTITY_API_URL = "/api/carteira-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarteiraClienteRepository carteiraClienteRepository;

    @Mock
    private CarteiraClienteRepository carteiraClienteRepositoryMock;

    @Autowired
    private CarteiraClienteMapper carteiraClienteMapper;

    @Mock
    private CarteiraClienteService carteiraClienteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteiraClienteMockMvc;

    private CarteiraCliente carteiraCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteiraCliente createEntity(EntityManager em) {
        CarteiraCliente carteiraCliente = new CarteiraCliente()
            .saldoConsolidado(DEFAULT_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(DEFAULT_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(DEFAULT_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(DEFAULT_DATA_HORA_CADASTRO)
            .colaboradorCadastro(DEFAULT_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(DEFAULT_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(DEFAULT_COLABORADOR_ATUALIZACAO);
        return carteiraCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteiraCliente createUpdatedEntity(EntityManager em) {
        CarteiraCliente carteiraCliente = new CarteiraCliente()
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        return carteiraCliente;
    }

    @BeforeEach
    public void initTest() {
        carteiraCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCarteiraCliente() throws Exception {
        int databaseSizeBeforeCreate = carteiraClienteRepository.findAll().size();
        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);
        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeCreate + 1);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(DEFAULT_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(DEFAULT_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(DEFAULT_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(DEFAULT_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createCarteiraClienteWithExistingId() throws Exception {
        // Create the CarteiraCliente with an existing ID
        carteiraCliente.setId(1L);
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        int databaseSizeBeforeCreate = carteiraClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSaldoConsolidadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteiraClienteRepository.findAll().size();
        // set the field null
        carteiraCliente.setSaldoConsolidado(null);

        // Create the CarteiraCliente, which fails.
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        restCarteiraClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarteiraClientes() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get all the carteiraClienteList
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteiraCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].saldoConsolidado").value(hasItem(sameNumber(DEFAULT_SALDO_CONSOLIDADO))))
            .andExpect(jsonPath("$.[*].tipoIndicadorSaldo").value(hasItem(DEFAULT_TIPO_INDICADOR_SALDO.toString())))
            .andExpect(jsonPath("$.[*].indicadorBloqueio").value(hasItem(DEFAULT_INDICADOR_BLOQUEIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataHoraCadastro").value(hasItem(DEFAULT_DATA_HORA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorCadastro").value(hasItem(DEFAULT_COLABORADOR_CADASTRO)))
            .andExpect(jsonPath("$.[*].dataHoraAtualizacao").value(hasItem(DEFAULT_DATA_HORA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].colaboradorAtualizacao").value(hasItem(DEFAULT_COLABORADOR_ATUALIZACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarteiraClientesWithEagerRelationshipsIsEnabled() throws Exception {
        when(carteiraClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carteiraClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarteiraClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(carteiraClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carteiraClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        // Get the carteiraCliente
        restCarteiraClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, carteiraCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteiraCliente.getId().intValue()))
            .andExpect(jsonPath("$.saldoConsolidado").value(sameNumber(DEFAULT_SALDO_CONSOLIDADO)))
            .andExpect(jsonPath("$.tipoIndicadorSaldo").value(DEFAULT_TIPO_INDICADOR_SALDO.toString()))
            .andExpect(jsonPath("$.indicadorBloqueio").value(DEFAULT_INDICADOR_BLOQUEIO.booleanValue()))
            .andExpect(jsonPath("$.dataHoraCadastro").value(DEFAULT_DATA_HORA_CADASTRO.toString()))
            .andExpect(jsonPath("$.colaboradorCadastro").value(DEFAULT_COLABORADOR_CADASTRO))
            .andExpect(jsonPath("$.dataHoraAtualizacao").value(DEFAULT_DATA_HORA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.colaboradorAtualizacao").value(DEFAULT_COLABORADOR_ATUALIZACAO));
    }

    @Test
    @Transactional
    void getNonExistingCarteiraCliente() throws Exception {
        // Get the carteiraCliente
        restCarteiraClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente
        CarteiraCliente updatedCarteiraCliente = carteiraClienteRepository.findById(carteiraCliente.getId()).get();
        // Disconnect from session so that the updates on updatedCarteiraCliente are not directly saved in db
        em.detach(updatedCarteiraCliente);
        updatedCarteiraCliente
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(updatedCarteiraCliente);

        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(UPDATED_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(UPDATED_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente using partial update
        CarteiraCliente partialUpdatedCarteiraCliente = new CarteiraCliente();
        partialUpdatedCarteiraCliente.setId(carteiraCliente.getId());

        partialUpdatedCarteiraCliente
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(DEFAULT_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(DEFAULT_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(DEFAULT_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(DEFAULT_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(DEFAULT_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdateCarteiraClienteWithPatch() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();

        // Update the carteiraCliente using partial update
        CarteiraCliente partialUpdatedCarteiraCliente = new CarteiraCliente();
        partialUpdatedCarteiraCliente.setId(carteiraCliente.getId());

        partialUpdatedCarteiraCliente
            .saldoConsolidado(UPDATED_SALDO_CONSOLIDADO)
            .tipoIndicadorSaldo(UPDATED_TIPO_INDICADOR_SALDO)
            .indicadorBloqueio(UPDATED_INDICADOR_BLOQUEIO)
            .dataHoraCadastro(UPDATED_DATA_HORA_CADASTRO)
            .colaboradorCadastro(UPDATED_COLABORADOR_CADASTRO)
            .dataHoraAtualizacao(UPDATED_DATA_HORA_ATUALIZACAO)
            .colaboradorAtualizacao(UPDATED_COLABORADOR_ATUALIZACAO);

        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteiraCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteiraCliente))
            )
            .andExpect(status().isOk());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
        CarteiraCliente testCarteiraCliente = carteiraClienteList.get(carteiraClienteList.size() - 1);
        assertThat(testCarteiraCliente.getSaldoConsolidado()).isEqualByComparingTo(UPDATED_SALDO_CONSOLIDADO);
        assertThat(testCarteiraCliente.getTipoIndicadorSaldo()).isEqualTo(UPDATED_TIPO_INDICADOR_SALDO);
        assertThat(testCarteiraCliente.getIndicadorBloqueio()).isEqualTo(UPDATED_INDICADOR_BLOQUEIO);
        assertThat(testCarteiraCliente.getDataHoraCadastro()).isEqualTo(UPDATED_DATA_HORA_CADASTRO);
        assertThat(testCarteiraCliente.getColaboradorCadastro()).isEqualTo(UPDATED_COLABORADOR_CADASTRO);
        assertThat(testCarteiraCliente.getDataHoraAtualizacao()).isEqualTo(UPDATED_DATA_HORA_ATUALIZACAO);
        assertThat(testCarteiraCliente.getColaboradorAtualizacao()).isEqualTo(UPDATED_COLABORADOR_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carteiraClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarteiraCliente() throws Exception {
        int databaseSizeBeforeUpdate = carteiraClienteRepository.findAll().size();
        carteiraCliente.setId(count.incrementAndGet());

        // Create the CarteiraCliente
        CarteiraClienteDTO carteiraClienteDTO = carteiraClienteMapper.toDto(carteiraCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteiraClienteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteiraClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteiraCliente in the database
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarteiraCliente() throws Exception {
        // Initialize the database
        carteiraClienteRepository.saveAndFlush(carteiraCliente);

        int databaseSizeBeforeDelete = carteiraClienteRepository.findAll().size();

        // Delete the carteiraCliente
        restCarteiraClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, carteiraCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteiraCliente> carteiraClienteList = carteiraClienteRepository.findAll();
        assertThat(carteiraClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
