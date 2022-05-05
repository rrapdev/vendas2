package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PlataformaPagamentoRepository;
import com.mycompany.myapp.service.PlataformaPagamentoService;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PlataformaPagamento}.
 */
@RestController
@RequestMapping("/api")
public class PlataformaPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(PlataformaPagamentoResource.class);

    private static final String ENTITY_NAME = "plataformaPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlataformaPagamentoService plataformaPagamentoService;

    private final PlataformaPagamentoRepository plataformaPagamentoRepository;

    public PlataformaPagamentoResource(
        PlataformaPagamentoService plataformaPagamentoService,
        PlataformaPagamentoRepository plataformaPagamentoRepository
    ) {
        this.plataformaPagamentoService = plataformaPagamentoService;
        this.plataformaPagamentoRepository = plataformaPagamentoRepository;
    }

    /**
     * {@code POST  /plataforma-pagamentos} : Create a new plataformaPagamento.
     *
     * @param plataformaPagamentoDTO the plataformaPagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plataformaPagamentoDTO, or with status {@code 400 (Bad Request)} if the plataformaPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plataforma-pagamentos")
    public ResponseEntity<PlataformaPagamentoDTO> createPlataformaPagamento(
        @Valid @RequestBody PlataformaPagamentoDTO plataformaPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PlataformaPagamento : {}", plataformaPagamentoDTO);
        if (plataformaPagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new plataformaPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlataformaPagamentoDTO result = plataformaPagamentoService.save(plataformaPagamentoDTO);
        return ResponseEntity
            .created(new URI("/api/plataforma-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plataforma-pagamentos/:id} : Updates an existing plataformaPagamento.
     *
     * @param id the id of the plataformaPagamentoDTO to save.
     * @param plataformaPagamentoDTO the plataformaPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plataformaPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the plataformaPagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plataformaPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plataforma-pagamentos/{id}")
    public ResponseEntity<PlataformaPagamentoDTO> updatePlataformaPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlataformaPagamentoDTO plataformaPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlataformaPagamento : {}, {}", id, plataformaPagamentoDTO);
        if (plataformaPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plataformaPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plataformaPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlataformaPagamentoDTO result = plataformaPagamentoService.update(plataformaPagamentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plataformaPagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plataforma-pagamentos/:id} : Partial updates given fields of an existing plataformaPagamento, field will ignore if it is null
     *
     * @param id the id of the plataformaPagamentoDTO to save.
     * @param plataformaPagamentoDTO the plataformaPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plataformaPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the plataformaPagamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plataformaPagamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plataformaPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plataforma-pagamentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlataformaPagamentoDTO> partialUpdatePlataformaPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlataformaPagamentoDTO plataformaPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlataformaPagamento partially : {}, {}", id, plataformaPagamentoDTO);
        if (plataformaPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plataformaPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plataformaPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlataformaPagamentoDTO> result = plataformaPagamentoService.partialUpdate(plataformaPagamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plataformaPagamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plataforma-pagamentos} : get all the plataformaPagamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plataformaPagamentos in body.
     */
    @GetMapping("/plataforma-pagamentos")
    public ResponseEntity<List<PlataformaPagamentoDTO>> getAllPlataformaPagamentos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlataformaPagamentos");
        Page<PlataformaPagamentoDTO> page = plataformaPagamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plataforma-pagamentos/:id} : get the "id" plataformaPagamento.
     *
     * @param id the id of the plataformaPagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plataformaPagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plataforma-pagamentos/{id}")
    public ResponseEntity<PlataformaPagamentoDTO> getPlataformaPagamento(@PathVariable Long id) {
        log.debug("REST request to get PlataformaPagamento : {}", id);
        Optional<PlataformaPagamentoDTO> plataformaPagamentoDTO = plataformaPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plataformaPagamentoDTO);
    }

    /**
     * {@code DELETE  /plataforma-pagamentos/:id} : delete the "id" plataformaPagamento.
     *
     * @param id the id of the plataformaPagamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plataforma-pagamentos/{id}")
    public ResponseEntity<Void> deletePlataformaPagamento(@PathVariable Long id) {
        log.debug("REST request to delete PlataformaPagamento : {}", id);
        plataformaPagamentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
