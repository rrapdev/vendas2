package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CarteiraCliente}.
 */
public interface CarteiraClienteService {
    /**
     * Save a carteiraCliente.
     *
     * @param carteiraClienteDTO the entity to save.
     * @return the persisted entity.
     */
    CarteiraClienteDTO save(CarteiraClienteDTO carteiraClienteDTO);

    /**
     * Updates a carteiraCliente.
     *
     * @param carteiraClienteDTO the entity to update.
     * @return the persisted entity.
     */
    CarteiraClienteDTO update(CarteiraClienteDTO carteiraClienteDTO);

    /**
     * Partially updates a carteiraCliente.
     *
     * @param carteiraClienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarteiraClienteDTO> partialUpdate(CarteiraClienteDTO carteiraClienteDTO);

    /**
     * Get all the carteiraClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarteiraClienteDTO> findAll(Pageable pageable);

    /**
     * Get all the carteiraClientes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarteiraClienteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" carteiraCliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarteiraClienteDTO> findOne(Long id);

    /**
     * Delete the "id" carteiraCliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
