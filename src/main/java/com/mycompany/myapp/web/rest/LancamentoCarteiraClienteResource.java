package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.LancamentoCarteiraClienteRepository;
import com.mycompany.myapp.service.LancamentoCarteiraClienteQueryService;
import com.mycompany.myapp.service.LancamentoCarteiraClienteService;
import com.mycompany.myapp.service.criteria.LancamentoCarteiraClienteCriteria;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.LancamentoCarteiraCliente}.
 */
@RestController
@RequestMapping("/api")
public class LancamentoCarteiraClienteResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoCarteiraClienteResource.class);

    private static final String ENTITY_NAME = "lancamentoCarteiraCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LancamentoCarteiraClienteService lancamentoCarteiraClienteService;

    private final LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository;

    private final LancamentoCarteiraClienteQueryService lancamentoCarteiraClienteQueryService;

    public LancamentoCarteiraClienteResource(
        LancamentoCarteiraClienteService lancamentoCarteiraClienteService,
        LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository,
        LancamentoCarteiraClienteQueryService lancamentoCarteiraClienteQueryService
    ) {
        this.lancamentoCarteiraClienteService = lancamentoCarteiraClienteService;
        this.lancamentoCarteiraClienteRepository = lancamentoCarteiraClienteRepository;
        this.lancamentoCarteiraClienteQueryService = lancamentoCarteiraClienteQueryService;
    }

    /**
     * {@code POST  /lancamento-carteira-clientes} : Create a new lancamentoCarteiraCliente.
     *
     * @param lancamentoCarteiraClienteDTO the lancamentoCarteiraClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lancamentoCarteiraClienteDTO, or with status {@code 400 (Bad Request)} if the lancamentoCarteiraCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lancamento-carteira-clientes")
    public ResponseEntity<LancamentoCarteiraClienteDTO> createLancamentoCarteiraCliente(
        @Valid @RequestBody LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to save LancamentoCarteiraCliente : {}", lancamentoCarteiraClienteDTO);
        if (lancamentoCarteiraClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamentoCarteiraCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoCarteiraClienteDTO result = lancamentoCarteiraClienteService.save(lancamentoCarteiraClienteDTO);
        return ResponseEntity
            .created(new URI("/api/lancamento-carteira-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lancamento-carteira-clientes/:id} : Updates an existing lancamentoCarteiraCliente.
     *
     * @param id the id of the lancamentoCarteiraClienteDTO to save.
     * @param lancamentoCarteiraClienteDTO the lancamentoCarteiraClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lancamentoCarteiraClienteDTO,
     * or with status {@code 400 (Bad Request)} if the lancamentoCarteiraClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lancamentoCarteiraClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lancamento-carteira-clientes/{id}")
    public ResponseEntity<LancamentoCarteiraClienteDTO> updateLancamentoCarteiraCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LancamentoCarteiraCliente : {}, {}", id, lancamentoCarteiraClienteDTO);
        if (lancamentoCarteiraClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lancamentoCarteiraClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lancamentoCarteiraClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LancamentoCarteiraClienteDTO result = lancamentoCarteiraClienteService.update(lancamentoCarteiraClienteDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lancamentoCarteiraClienteDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /lancamento-carteira-clientes/:id} : Partial updates given fields of an existing lancamentoCarteiraCliente, field will ignore if it is null
     *
     * @param id the id of the lancamentoCarteiraClienteDTO to save.
     * @param lancamentoCarteiraClienteDTO the lancamentoCarteiraClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lancamentoCarteiraClienteDTO,
     * or with status {@code 400 (Bad Request)} if the lancamentoCarteiraClienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lancamentoCarteiraClienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lancamentoCarteiraClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lancamento-carteira-clientes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LancamentoCarteiraClienteDTO> partialUpdateLancamentoCarteiraCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LancamentoCarteiraCliente partially : {}, {}", id, lancamentoCarteiraClienteDTO);
        if (lancamentoCarteiraClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lancamentoCarteiraClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lancamentoCarteiraClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LancamentoCarteiraClienteDTO> result = lancamentoCarteiraClienteService.partialUpdate(lancamentoCarteiraClienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lancamentoCarteiraClienteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lancamento-carteira-clientes} : get all the lancamentoCarteiraClientes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lancamentoCarteiraClientes in body.
     */
    @GetMapping("/lancamento-carteira-clientes")
    public ResponseEntity<List<LancamentoCarteiraClienteDTO>> getAllLancamentoCarteiraClientes(
        LancamentoCarteiraClienteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LancamentoCarteiraClientes by criteria: {}", criteria);
        Page<LancamentoCarteiraClienteDTO> page = lancamentoCarteiraClienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lancamento-carteira-clientes/count} : count all the lancamentoCarteiraClientes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lancamento-carteira-clientes/count")
    public ResponseEntity<Long> countLancamentoCarteiraClientes(LancamentoCarteiraClienteCriteria criteria) {
        log.debug("REST request to count LancamentoCarteiraClientes by criteria: {}", criteria);
        return ResponseEntity.ok().body(lancamentoCarteiraClienteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lancamento-carteira-clientes/:id} : get the "id" lancamentoCarteiraCliente.
     *
     * @param id the id of the lancamentoCarteiraClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lancamentoCarteiraClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lancamento-carteira-clientes/{id}")
    public ResponseEntity<LancamentoCarteiraClienteDTO> getLancamentoCarteiraCliente(@PathVariable Long id) {
        log.debug("REST request to get LancamentoCarteiraCliente : {}", id);
        Optional<LancamentoCarteiraClienteDTO> lancamentoCarteiraClienteDTO = lancamentoCarteiraClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoCarteiraClienteDTO);
    }

    /**
     * {@code DELETE  /lancamento-carteira-clientes/:id} : delete the "id" lancamentoCarteiraCliente.
     *
     * @param id the id of the lancamentoCarteiraClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lancamento-carteira-clientes/{id}")
    public ResponseEntity<Void> deleteLancamentoCarteiraCliente(@PathVariable Long id) {
        log.debug("REST request to delete LancamentoCarteiraCliente : {}", id);
        lancamentoCarteiraClienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
