package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ColaboradorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Colaborador}.
 */
public interface ColaboradorService {
    /**
     * Save a colaborador.
     *
     * @param colaboradorDTO the entity to save.
     * @return the persisted entity.
     */
    ColaboradorDTO save(ColaboradorDTO colaboradorDTO);

    /**
     * Updates a colaborador.
     *
     * @param colaboradorDTO the entity to update.
     * @return the persisted entity.
     */
    ColaboradorDTO update(ColaboradorDTO colaboradorDTO);

    /**
     * Partially updates a colaborador.
     *
     * @param colaboradorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ColaboradorDTO> partialUpdate(ColaboradorDTO colaboradorDTO);

    /**
     * Get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ColaboradorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" colaborador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColaboradorDTO> findOne(Long id);

    /**
     * Delete the "id" colaborador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
