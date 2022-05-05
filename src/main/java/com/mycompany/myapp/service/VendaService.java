package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VendaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Venda}.
 */
public interface VendaService {
    /**
     * Save a venda.
     *
     * @param vendaDTO the entity to save.
     * @return the persisted entity.
     */
    VendaDTO save(VendaDTO vendaDTO);

    /**
     * Updates a venda.
     *
     * @param vendaDTO the entity to update.
     * @return the persisted entity.
     */
    VendaDTO update(VendaDTO vendaDTO);

    /**
     * Partially updates a venda.
     *
     * @param vendaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VendaDTO> partialUpdate(VendaDTO vendaDTO);

    /**
     * Get all the vendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendaDTO> findAll(Pageable pageable);

    /**
     * Get all the vendas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" venda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendaDTO> findOne(Long id);

    /**
     * Delete the "id" venda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
