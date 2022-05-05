package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.repository.PlataformaPagamentoRepository;
import com.mycompany.myapp.service.PlataformaPagamentoService;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import com.mycompany.myapp.service.mapper.PlataformaPagamentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlataformaPagamento}.
 */
@Service
@Transactional
public class PlataformaPagamentoServiceImpl implements PlataformaPagamentoService {

    private final Logger log = LoggerFactory.getLogger(PlataformaPagamentoServiceImpl.class);

    private final PlataformaPagamentoRepository plataformaPagamentoRepository;

    private final PlataformaPagamentoMapper plataformaPagamentoMapper;

    public PlataformaPagamentoServiceImpl(
        PlataformaPagamentoRepository plataformaPagamentoRepository,
        PlataformaPagamentoMapper plataformaPagamentoMapper
    ) {
        this.plataformaPagamentoRepository = plataformaPagamentoRepository;
        this.plataformaPagamentoMapper = plataformaPagamentoMapper;
    }

    @Override
    public PlataformaPagamentoDTO save(PlataformaPagamentoDTO plataformaPagamentoDTO) {
        log.debug("Request to save PlataformaPagamento : {}", plataformaPagamentoDTO);
        PlataformaPagamento plataformaPagamento = plataformaPagamentoMapper.toEntity(plataformaPagamentoDTO);
        plataformaPagamento = plataformaPagamentoRepository.save(plataformaPagamento);
        return plataformaPagamentoMapper.toDto(plataformaPagamento);
    }

    @Override
    public PlataformaPagamentoDTO update(PlataformaPagamentoDTO plataformaPagamentoDTO) {
        log.debug("Request to save PlataformaPagamento : {}", plataformaPagamentoDTO);
        PlataformaPagamento plataformaPagamento = plataformaPagamentoMapper.toEntity(plataformaPagamentoDTO);
        plataformaPagamento = plataformaPagamentoRepository.save(plataformaPagamento);
        return plataformaPagamentoMapper.toDto(plataformaPagamento);
    }

    @Override
    public Optional<PlataformaPagamentoDTO> partialUpdate(PlataformaPagamentoDTO plataformaPagamentoDTO) {
        log.debug("Request to partially update PlataformaPagamento : {}", plataformaPagamentoDTO);

        return plataformaPagamentoRepository
            .findById(plataformaPagamentoDTO.getId())
            .map(existingPlataformaPagamento -> {
                plataformaPagamentoMapper.partialUpdate(existingPlataformaPagamento, plataformaPagamentoDTO);

                return existingPlataformaPagamento;
            })
            .map(plataformaPagamentoRepository::save)
            .map(plataformaPagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlataformaPagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlataformaPagamentos");
        return plataformaPagamentoRepository.findAll(pageable).map(plataformaPagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlataformaPagamentoDTO> findOne(Long id) {
        log.debug("Request to get PlataformaPagamento : {}", id);
        return plataformaPagamentoRepository.findById(id).map(plataformaPagamentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlataformaPagamento : {}", id);
        plataformaPagamentoRepository.deleteById(id);
    }
}
