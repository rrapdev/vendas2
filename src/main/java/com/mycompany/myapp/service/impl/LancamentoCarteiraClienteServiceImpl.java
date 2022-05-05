package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.repository.LancamentoCarteiraClienteRepository;
import com.mycompany.myapp.service.LancamentoCarteiraClienteService;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.LancamentoCarteiraClienteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LancamentoCarteiraCliente}.
 */
@Service
@Transactional
public class LancamentoCarteiraClienteServiceImpl implements LancamentoCarteiraClienteService {

    private final Logger log = LoggerFactory.getLogger(LancamentoCarteiraClienteServiceImpl.class);

    private final LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository;

    private final LancamentoCarteiraClienteMapper lancamentoCarteiraClienteMapper;

    public LancamentoCarteiraClienteServiceImpl(
        LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository,
        LancamentoCarteiraClienteMapper lancamentoCarteiraClienteMapper
    ) {
        this.lancamentoCarteiraClienteRepository = lancamentoCarteiraClienteRepository;
        this.lancamentoCarteiraClienteMapper = lancamentoCarteiraClienteMapper;
    }

    @Override
    public LancamentoCarteiraClienteDTO save(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO) {
        log.debug("Request to save LancamentoCarteiraCliente : {}", lancamentoCarteiraClienteDTO);
        LancamentoCarteiraCliente lancamentoCarteiraCliente = lancamentoCarteiraClienteMapper.toEntity(lancamentoCarteiraClienteDTO);
        lancamentoCarteiraCliente = lancamentoCarteiraClienteRepository.save(lancamentoCarteiraCliente);
        return lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);
    }

    @Override
    public LancamentoCarteiraClienteDTO update(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO) {
        log.debug("Request to save LancamentoCarteiraCliente : {}", lancamentoCarteiraClienteDTO);
        LancamentoCarteiraCliente lancamentoCarteiraCliente = lancamentoCarteiraClienteMapper.toEntity(lancamentoCarteiraClienteDTO);
        lancamentoCarteiraCliente = lancamentoCarteiraClienteRepository.save(lancamentoCarteiraCliente);
        return lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraCliente);
    }

    @Override
    public Optional<LancamentoCarteiraClienteDTO> partialUpdate(LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO) {
        log.debug("Request to partially update LancamentoCarteiraCliente : {}", lancamentoCarteiraClienteDTO);

        return lancamentoCarteiraClienteRepository
            .findById(lancamentoCarteiraClienteDTO.getId())
            .map(existingLancamentoCarteiraCliente -> {
                lancamentoCarteiraClienteMapper.partialUpdate(existingLancamentoCarteiraCliente, lancamentoCarteiraClienteDTO);

                return existingLancamentoCarteiraCliente;
            })
            .map(lancamentoCarteiraClienteRepository::save)
            .map(lancamentoCarteiraClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoCarteiraClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LancamentoCarteiraClientes");
        return lancamentoCarteiraClienteRepository.findAll(pageable).map(lancamentoCarteiraClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LancamentoCarteiraClienteDTO> findOne(Long id) {
        log.debug("Request to get LancamentoCarteiraCliente : {}", id);
        return lancamentoCarteiraClienteRepository.findById(id).map(lancamentoCarteiraClienteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LancamentoCarteiraCliente : {}", id);
        lancamentoCarteiraClienteRepository.deleteById(id);
    }
}
