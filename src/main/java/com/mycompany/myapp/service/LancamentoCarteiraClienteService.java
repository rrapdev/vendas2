package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.LancamentoCarteiraCliente}.
 */
public interface LancamentoCarteiraClienteService {
    /**
     * Save a lancamentoCarteiraCliente.
     *
     * @param lancamentoCarteiraClienteDTO the entity to save.
     * @return the persisted entity.
     */
    LancamentoCarteiraClienteDTO save(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO);

    /**
     * Updates a lancamentoCarteiraCliente.
     *
     * @param lancamentoCarteiraClienteDTO the entity to update.
     * @return the persisted entity.
     */
    LancamentoCarteiraClienteDTO update(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO);

    /**
     * Partially updates a lancamentoCarteiraCliente.
     *
     * @param lancamentoCarteiraClienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LancamentoCarteiraClienteDTO> partialUpdate(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO);

    /**
     * Get all the lancamentoCarteiraClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LancamentoCarteiraClienteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" lancamentoCarteiraCliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LancamentoCarteiraClienteDTO> findOne(Long id);

    /**
     * Delete the "id" lancamentoCarteiraCliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
