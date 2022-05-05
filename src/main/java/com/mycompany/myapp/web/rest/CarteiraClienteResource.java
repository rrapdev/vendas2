package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CarteiraClienteRepository;
import com.mycompany.myapp.service.CarteiraClienteService;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CarteiraCliente}.
 */
@RestController
@RequestMapping("/api")
public class CarteiraClienteResource {

    private final Logger log = LoggerFactory.getLogger(CarteiraClienteResource.class);

    private static final String ENTITY_NAME = "carteiraCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarteiraClienteService carteiraClienteService;

    private final CarteiraClienteRepository carteiraClienteRepository;

    public CarteiraClienteResource(CarteiraClienteService carteiraClienteService, CarteiraClienteRepository carteiraClienteRepository) {
        this.carteiraClienteService = carteiraClienteService;
        this.carteiraClienteRepository = carteiraClienteRepository;
    }

    /**
     * {@code POST  /carteira-clientes} : Create a new carteiraCliente.
     *
     * @param carteiraClienteDTO the carteiraClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carteiraClienteDTO, or with status {@code 400 (Bad Request)} if the carteiraCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carteira-clientes")
    public ResponseEntity<CarteiraClienteDTO> createCarteiraCliente(@Valid @RequestBody CarteiraClienteDTO carteiraClienteDTO)
        throws URISyntaxException {
        log.debug("REST request to save CarteiraCliente : {}", carteiraClienteDTO);
        if (carteiraClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new carteiraCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarteiraClienteDTO result = carteiraClienteService.save(carteiraClienteDTO);
        return ResponseEntity
            .created(new URI("/api/carteira-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carteira-clientes/:id} : Updates an existing carteiraCliente.
     *
     * @param id the id of the carteiraClienteDTO to save.
     * @param carteiraClienteDTO the carteiraClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteiraClienteDTO,
     * or with status {@code 400 (Bad Request)} if the carteiraClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carteiraClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carteira-clientes/{id}")
    public ResponseEntity<CarteiraClienteDTO> updateCarteiraCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarteiraClienteDTO carteiraClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarteiraCliente : {}, {}", id, carteiraClienteDTO);
        if (carteiraClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carteiraClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carteiraClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarteiraClienteDTO result = carteiraClienteService.update(carteiraClienteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carteiraClienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carteira-clientes/:id} : Partial updates given fields of an existing carteiraCliente, field will ignore if it is null
     *
     * @param id the id of the carteiraClienteDTO to save.
     * @param carteiraClienteDTO the carteiraClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteiraClienteDTO,
     * or with status {@code 400 (Bad Request)} if the carteiraClienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carteiraClienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carteiraClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carteira-clientes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarteiraClienteDTO> partialUpdateCarteiraCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarteiraClienteDTO carteiraClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarteiraCliente partially : {}, {}", id, carteiraClienteDTO);
        if (carteiraClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carteiraClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carteiraClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarteiraClienteDTO> result = carteiraClienteService.partialUpdate(carteiraClienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carteiraClienteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carteira-clientes} : get all the carteiraClientes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carteiraClientes in body.
     */
    @GetMapping("/carteira-clientes")
    public ResponseEntity<List<CarteiraClienteDTO>> getAllCarteiraClientes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of CarteiraClientes");
        Page<CarteiraClienteDTO> page;
        if (eagerload) {
            page = carteiraClienteService.findAllWithEagerRelationships(pageable);
        } else {
            page = carteiraClienteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carteira-clientes/:id} : get the "id" carteiraCliente.
     *
     * @param id the id of the carteiraClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carteiraClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carteira-clientes/{id}")
    public ResponseEntity<CarteiraClienteDTO> getCarteiraCliente(@PathVariable Long id) {
        log.debug("REST request to get CarteiraCliente : {}", id);
        Optional<CarteiraClienteDTO> carteiraClienteDTO = carteiraClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carteiraClienteDTO);
    }

    /**
     * {@code DELETE  /carteira-clientes/:id} : delete the "id" carteiraCliente.
     *
     * @param id the id of the carteiraClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carteira-clientes/{id}")
    public ResponseEntity<Void> deleteCarteiraCliente(@PathVariable Long id) {
        log.debug("REST request to delete CarteiraCliente : {}", id);
        carteiraClienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
