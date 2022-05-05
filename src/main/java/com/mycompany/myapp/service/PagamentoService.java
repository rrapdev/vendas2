package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PagamentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Pagamento}.
 */
public interface PagamentoService {
    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    PagamentoDTO save(PagamentoDTO pagamentoDTO);

    /**
     * Updates a pagamento.
     *
     * @param pagamentoDTO the entity to update.
     * @return the persisted entity.
     */
    PagamentoDTO update(PagamentoDTO pagamentoDTO);

    /**
     * Partially updates a pagamento.
     *
     * @param pagamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PagamentoDTO> partialUpdate(PagamentoDTO pagamentoDTO);

    /**
     * Get all the pagamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PagamentoDTO> findAll(Pageable pageable);

    /**
     * Get all the pagamentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PagamentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" pagamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PagamentoDTO> findOne(Long id);

    /**
     * Delete the "id" pagamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
