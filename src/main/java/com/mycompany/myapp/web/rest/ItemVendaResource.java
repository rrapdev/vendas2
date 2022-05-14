package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ItemVendaRepository;
import com.mycompany.myapp.service.ItemVendaQueryService;
import com.mycompany.myapp.service.ItemVendaService;
import com.mycompany.myapp.service.criteria.ItemVendaCriteria;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ItemVenda}.
 */
@RestController
@RequestMapping("/api")
public class ItemVendaResource {

    private final Logger log = LoggerFactory.getLogger(ItemVendaResource.class);

    private static final String ENTITY_NAME = "itemVenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemVendaService itemVendaService;

    private final ItemVendaRepository itemVendaRepository;

    private final ItemVendaQueryService itemVendaQueryService;

    public ItemVendaResource(
        ItemVendaService itemVendaService,
        ItemVendaRepository itemVendaRepository,
        ItemVendaQueryService itemVendaQueryService
    ) {
        this.itemVendaService = itemVendaService;
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaQueryService = itemVendaQueryService;
    }

    /**
     * {@code POST  /item-vendas} : Create a new itemVenda.
     *
     * @param itemVendaDTO the itemVendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemVendaDTO, or with status {@code 400 (Bad Request)} if the itemVenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-vendas")
    public ResponseEntity<ItemVendaDTO> createItemVenda(@Valid @RequestBody ItemVendaDTO itemVendaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemVenda : {}", itemVendaDTO);
        if (itemVendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemVenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemVendaDTO result = itemVendaService.save(itemVendaDTO);
        return ResponseEntity
            .created(new URI("/api/item-vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-vendas/:id} : Updates an existing itemVenda.
     *
     * @param id the id of the itemVendaDTO to save.
     * @param itemVendaDTO the itemVendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemVendaDTO,
     * or with status {@code 400 (Bad Request)} if the itemVendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemVendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-vendas/{id}")
    public ResponseEntity<ItemVendaDTO> updateItemVenda(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemVendaDTO itemVendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ItemVenda : {}, {}", id, itemVendaDTO);
        if (itemVendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemVendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemVendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemVendaDTO result = itemVendaService.update(itemVendaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemVendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-vendas/:id} : Partial updates given fields of an existing itemVenda, field will ignore if it is null
     *
     * @param id the id of the itemVendaDTO to save.
     * @param itemVendaDTO the itemVendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemVendaDTO,
     * or with status {@code 400 (Bad Request)} if the itemVendaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemVendaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemVendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-vendas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemVendaDTO> partialUpdateItemVenda(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemVendaDTO itemVendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemVenda partially : {}, {}", id, itemVendaDTO);
        if (itemVendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemVendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemVendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemVendaDTO> result = itemVendaService.partialUpdate(itemVendaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemVendaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /item-vendas} : get all the itemVendas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemVendas in body.
     */
    @GetMapping("/item-vendas")
    public ResponseEntity<List<ItemVendaDTO>> getAllItemVendas(
        ItemVendaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ItemVendas by criteria: {}", criteria);
        Page<ItemVendaDTO> page = itemVendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-vendas/count} : count all the itemVendas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-vendas/count")
    public ResponseEntity<Long> countItemVendas(ItemVendaCriteria criteria) {
        log.debug("REST request to count ItemVendas by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemVendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-vendas/:id} : get the "id" itemVenda.
     *
     * @param id the id of the itemVendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemVendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-vendas/{id}")
    public ResponseEntity<ItemVendaDTO> getItemVenda(@PathVariable Long id) {
        log.debug("REST request to get ItemVenda : {}", id);
        Optional<ItemVendaDTO> itemVendaDTO = itemVendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemVendaDTO);
    }

    /**
     * {@code DELETE  /item-vendas/:id} : delete the "id" itemVenda.
     *
     * @param id the id of the itemVendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-vendas/{id}")
    public ResponseEntity<Void> deleteItemVenda(@PathVariable Long id) {
        log.debug("REST request to delete ItemVenda : {}", id);
        itemVendaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
