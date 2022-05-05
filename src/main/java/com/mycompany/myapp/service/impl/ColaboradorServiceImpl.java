package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.repository.ColaboradorRepository;
import com.mycompany.myapp.service.ColaboradorService;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import com.mycompany.myapp.service.mapper.ColaboradorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Colaborador}.
 */
@Service
@Transactional
public class ColaboradorServiceImpl implements ColaboradorService {

    private final Logger log = LoggerFactory.getLogger(ColaboradorServiceImpl.class);

    private final ColaboradorRepository colaboradorRepository;

    private final ColaboradorMapper colaboradorMapper;

    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository, ColaboradorMapper colaboradorMapper) {
        this.colaboradorRepository = colaboradorRepository;
        this.colaboradorMapper = colaboradorMapper;
    }

    @Override
    public ColaboradorDTO save(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to save Colaborador : {}", colaboradorDTO);
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorDTO);
        colaborador = colaboradorRepository.save(colaborador);
        return colaboradorMapper.toDto(colaborador);
    }

    @Override
    public ColaboradorDTO update(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to save Colaborador : {}", colaboradorDTO);
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorDTO);
        colaborador = colaboradorRepository.save(colaborador);
        return colaboradorMapper.toDto(colaborador);
    }

    @Override
    public Optional<ColaboradorDTO> partialUpdate(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to partially update Colaborador : {}", colaboradorDTO);

        return colaboradorRepository
            .findById(colaboradorDTO.getId())
            .map(existingColaborador -> {
                colaboradorMapper.partialUpdate(existingColaborador, colaboradorDTO);

                return existingColaborador;
            })
            .map(colaboradorRepository::save)
            .map(colaboradorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColaboradorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colaboradors");
        return colaboradorRepository.findAll(pageable).map(colaboradorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ColaboradorDTO> findOne(Long id) {
        log.debug("Request to get Colaborador : {}", id);
        return colaboradorRepository.findById(id).map(colaboradorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colaborador : {}", id);
        colaboradorRepository.deleteById(id);
    }
}
