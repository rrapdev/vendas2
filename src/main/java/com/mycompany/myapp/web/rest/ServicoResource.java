package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ServicoRepository;
import com.mycompany.myapp.service.ServicoQueryService;
import com.mycompany.myapp.service.ServicoService;
import com.mycompany.myapp.service.criteria.ServicoCriteria;
import com.mycompany.myapp.service.dto.ServicoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Servico}.
 */
@RestController
@RequestMapping("/api")
public class ServicoResource {

    private final Logger log = LoggerFactory.getLogger(ServicoResource.class);

    private static final String ENTITY_NAME = "servico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoService servicoService;

    private final ServicoRepository servicoRepository;

    private final ServicoQueryService servicoQueryService;

    public ServicoResource(ServicoService servicoService, ServicoRepository servicoRepository, ServicoQueryService servicoQueryService) {
        this.servicoService = servicoService;
        this.servicoRepository = servicoRepository;
        this.servicoQueryService = servicoQueryService;
    }

    /**
     * {@code POST  /servicos} : Create a new servico.
     *
     * @param servicoDTO the servicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoDTO, or with status {@code 400 (Bad Request)} if the servico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servicos")
    public ResponseEntity<ServicoDTO> createServico(@Valid @RequestBody ServicoDTO servicoDTO) throws URISyntaxException {
        log.debug("REST request to save Servico : {}", servicoDTO);
        if (servicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new servico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicoDTO result = servicoService.save(servicoDTO);
        return ResponseEntity
            .created(new URI("/api/servicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servicos/:id} : Updates an existing servico.
     *
     * @param id the id of the servicoDTO to save.
     * @param servicoDTO the servicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoDTO,
     * or with status {@code 400 (Bad Request)} if the servicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servicos/{id}")
    public ResponseEntity<ServicoDTO> updateServico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoDTO servicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Servico : {}, {}", id, servicoDTO);
        if (servicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServicoDTO result = servicoService.update(servicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servicos/:id} : Partial updates given fields of an existing servico, field will ignore if it is null
     *
     * @param id the id of the servicoDTO to save.
     * @param servicoDTO the servicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoDTO,
     * or with status {@code 400 (Bad Request)} if the servicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoDTO> partialUpdateServico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoDTO servicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Servico partially : {}, {}", id, servicoDTO);
        if (servicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoDTO> result = servicoService.partialUpdate(servicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servicos} : get all the servicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicos in body.
     */
    @GetMapping("/servicos")
    public ResponseEntity<List<ServicoDTO>> getAllServicos(
        ServicoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Servicos by criteria: {}", criteria);
        Page<ServicoDTO> page = servicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servicos/count} : count all the servicos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/servicos/count")
    public ResponseEntity<Long> countServicos(ServicoCriteria criteria) {
        log.debug("REST request to count Servicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(servicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /servicos/:id} : get the "id" servico.
     *
     * @param id the id of the servicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servicos/{id}")
    public ResponseEntity<ServicoDTO> getServico(@PathVariable Long id) {
        log.debug("REST request to get Servico : {}", id);
        Optional<ServicoDTO> servicoDTO = servicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoDTO);
    }

    /**
     * {@code DELETE  /servicos/:id} : delete the "id" servico.
     *
     * @param id the id of the servicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servicos/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        log.debug("REST request to delete Servico : {}", id);
        servicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
