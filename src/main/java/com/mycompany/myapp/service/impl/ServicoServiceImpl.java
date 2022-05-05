package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Servico;
import com.mycompany.myapp.repository.ServicoRepository;
import com.mycompany.myapp.service.ServicoService;
import com.mycompany.myapp.service.dto.ServicoDTO;
import com.mycompany.myapp.service.mapper.ServicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Servico}.
 */
@Service
@Transactional
public class ServicoServiceImpl implements ServicoService {

    private final Logger log = LoggerFactory.getLogger(ServicoServiceImpl.class);

    private final ServicoRepository servicoRepository;

    private final ServicoMapper servicoMapper;

    public ServicoServiceImpl(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    @Override
    public ServicoDTO save(ServicoDTO servicoDTO) {
        log.debug("Request to save Servico : {}", servicoDTO);
        Servico servico = servicoMapper.toEntity(servicoDTO);
        servico = servicoRepository.save(servico);
        return servicoMapper.toDto(servico);
    }

    @Override
    public ServicoDTO update(ServicoDTO servicoDTO) {
        log.debug("Request to save Servico : {}", servicoDTO);
        Servico servico = servicoMapper.toEntity(servicoDTO);
        servico = servicoRepository.save(servico);
        return servicoMapper.toDto(servico);
    }

    @Override
    public Optional<ServicoDTO> partialUpdate(ServicoDTO servicoDTO) {
        log.debug("Request to partially update Servico : {}", servicoDTO);

        return servicoRepository
            .findById(servicoDTO.getId())
            .map(existingServico -> {
                servicoMapper.partialUpdate(existingServico, servicoDTO);

                return existingServico;
            })
            .map(servicoRepository::save)
            .map(servicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servicos");
        return servicoRepository.findAll(pageable).map(servicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicoDTO> findOne(Long id) {
        log.debug("Request to get Servico : {}", id);
        return servicoRepository.findById(id).map(servicoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servico : {}", id);
        servicoRepository.deleteById(id);
    }
}
