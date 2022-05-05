package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ServicoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Servico}.
 */
public interface ServicoService {
    /**
     * Save a servico.
     *
     * @param servicoDTO the entity to save.
     * @return the persisted entity.
     */
    ServicoDTO save(ServicoDTO servicoDTO);

    /**
     * Updates a servico.
     *
     * @param servicoDTO the entity to update.
     * @return the persisted entity.
     */
    ServicoDTO update(ServicoDTO servicoDTO);

    /**
     * Partially updates a servico.
     *
     * @param servicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServicoDTO> partialUpdate(ServicoDTO servicoDTO);

    /**
     * Get all the servicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServicoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicoDTO> findOne(Long id);

    /**
     * Delete the "id" servico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
