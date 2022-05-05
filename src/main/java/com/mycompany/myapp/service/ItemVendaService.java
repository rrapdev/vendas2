package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ItemVendaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ItemVenda}.
 */
public interface ItemVendaService {
    /**
     * Save a itemVenda.
     *
     * @param itemVendaDTO the entity to save.
     * @return the persisted entity.
     */
    ItemVendaDTO save(ItemVendaDTO itemVendaDTO);

    /**
     * Updates a itemVenda.
     *
     * @param itemVendaDTO the entity to update.
     * @return the persisted entity.
     */
    ItemVendaDTO update(ItemVendaDTO itemVendaDTO);

    /**
     * Partially updates a itemVenda.
     *
     * @param itemVendaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemVendaDTO> partialUpdate(ItemVendaDTO itemVendaDTO);

    /**
     * Get all the itemVendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemVendaDTO> findAll(Pageable pageable);

    /**
     * Get all the itemVendas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemVendaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" itemVenda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemVendaDTO> findOne(Long id);

    /**
     * Delete the "id" itemVenda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
