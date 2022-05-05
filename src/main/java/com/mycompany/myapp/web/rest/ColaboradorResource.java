package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ColaboradorRepository;
import com.mycompany.myapp.service.ColaboradorService;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Colaborador}.
 */
@RestController
@RequestMapping("/api")
public class ColaboradorResource {

    private final Logger log = LoggerFactory.getLogger(ColaboradorResource.class);

    private static final String ENTITY_NAME = "colaborador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColaboradorService colaboradorService;

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorResource(ColaboradorService colaboradorService, ColaboradorRepository colaboradorRepository) {
        this.colaboradorService = colaboradorService;
        this.colaboradorRepository = colaboradorRepository;
    }

    /**
     * {@code POST  /colaboradors} : Create a new colaborador.
     *
     * @param colaboradorDTO the colaboradorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colaboradorDTO, or with status {@code 400 (Bad Request)} if the colaborador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colaboradors")
    public ResponseEntity<ColaboradorDTO> createColaborador(@Valid @RequestBody ColaboradorDTO colaboradorDTO) throws URISyntaxException {
        log.debug("REST request to save Colaborador : {}", colaboradorDTO);
        if (colaboradorDTO.getId() != null) {
            throw new BadRequestAlertException("A new colaborador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColaboradorDTO result = colaboradorService.save(colaboradorDTO);
        return ResponseEntity
            .created(new URI("/api/colaboradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colaboradors/:id} : Updates an existing colaborador.
     *
     * @param id the id of the colaboradorDTO to save.
     * @param colaboradorDTO the colaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the colaboradorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colaboradors/{id}")
    public ResponseEntity<ColaboradorDTO> updateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ColaboradorDTO colaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Colaborador : {}, {}", id, colaboradorDTO);
        if (colaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ColaboradorDTO result = colaboradorService.update(colaboradorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colaboradorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colaboradors/:id} : Partial updates given fields of an existing colaborador, field will ignore if it is null
     *
     * @param id the id of the colaboradorDTO to save.
     * @param colaboradorDTO the colaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the colaboradorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the colaboradorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the colaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colaboradors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ColaboradorDTO> partialUpdateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ColaboradorDTO colaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Colaborador partially : {}, {}", id, colaboradorDTO);
        if (colaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ColaboradorDTO> result = colaboradorService.partialUpdate(colaboradorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colaboradorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /colaboradors} : get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colaboradors in body.
     */
    @GetMapping("/colaboradors")
    public ResponseEntity<List<ColaboradorDTO>> getAllColaboradors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Colaboradors");
        Page<ColaboradorDTO> page = colaboradorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colaboradors/:id} : get the "id" colaborador.
     *
     * @param id the id of the colaboradorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colaboradorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colaboradors/{id}")
    public ResponseEntity<ColaboradorDTO> getColaborador(@PathVariable Long id) {
        log.debug("REST request to get Colaborador : {}", id);
        Optional<ColaboradorDTO> colaboradorDTO = colaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colaboradorDTO);
    }

    /**
     * {@code DELETE  /colaboradors/:id} : delete the "id" colaborador.
     *
     * @param id the id of the colaboradorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colaboradors/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        log.debug("REST request to delete Colaborador : {}", id);
        colaboradorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
