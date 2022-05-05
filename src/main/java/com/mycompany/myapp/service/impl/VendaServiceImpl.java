package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.VendaRepository;
import com.mycompany.myapp.service.VendaService;
import com.mycompany.myapp.service.dto.VendaDTO;
import com.mycompany.myapp.service.mapper.VendaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venda}.
 */
@Service
@Transactional
public class VendaServiceImpl implements VendaService {

    private final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    public VendaServiceImpl(VendaRepository vendaRepository, VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    @Override
    public VendaDTO save(VendaDTO vendaDTO) {
        log.debug("Request to save Venda : {}", vendaDTO);
        Venda venda = vendaMapper.toEntity(vendaDTO);
        venda = vendaRepository.save(venda);
        return vendaMapper.toDto(venda);
    }

    @Override
    public VendaDTO update(VendaDTO vendaDTO) {
        log.debug("Request to save Venda : {}", vendaDTO);
        Venda venda = vendaMapper.toEntity(vendaDTO);
        venda = vendaRepository.save(venda);
        return vendaMapper.toDto(venda);
    }

    @Override
    public Optional<VendaDTO> partialUpdate(VendaDTO vendaDTO) {
        log.debug("Request to partially update Venda : {}", vendaDTO);

        return vendaRepository
            .findById(vendaDTO.getId())
            .map(existingVenda -> {
                vendaMapper.partialUpdate(existingVenda, vendaDTO);

                return existingVenda;
            })
            .map(vendaRepository::save)
            .map(vendaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendas");
        return vendaRepository.findAll(pageable).map(vendaMapper::toDto);
    }

    public Page<VendaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vendaRepository.findAllWithEagerRelationships(pageable).map(vendaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendaDTO> findOne(Long id) {
        log.debug("Request to get Venda : {}", id);
        return vendaRepository.findOneWithEagerRelationships(id).map(vendaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venda : {}", id);
        vendaRepository.deleteById(id);
    }
}
