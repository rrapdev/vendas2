package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.repository.ItemVendaRepository;
import com.mycompany.myapp.service.ItemVendaService;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
import com.mycompany.myapp.service.mapper.ItemVendaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemVenda}.
 */
@Service
@Transactional
public class ItemVendaServiceImpl implements ItemVendaService {

    private final Logger log = LoggerFactory.getLogger(ItemVendaServiceImpl.class);

    private final ItemVendaRepository itemVendaRepository;

    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaServiceImpl(ItemVendaRepository itemVendaRepository, ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    @Override
    public ItemVendaDTO save(ItemVendaDTO itemVendaDTO) {
        log.debug("Request to save ItemVenda : {}", itemVendaDTO);
        ItemVenda itemVenda = itemVendaMapper.toEntity(itemVendaDTO);
        itemVenda = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toDto(itemVenda);
    }

    @Override
    public ItemVendaDTO update(ItemVendaDTO itemVendaDTO) {
        log.debug("Request to save ItemVenda : {}", itemVendaDTO);
        ItemVenda itemVenda = itemVendaMapper.toEntity(itemVendaDTO);
        itemVenda = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toDto(itemVenda);
    }

    @Override
    public Optional<ItemVendaDTO> partialUpdate(ItemVendaDTO itemVendaDTO) {
        log.debug("Request to partially update ItemVenda : {}", itemVendaDTO);

        return itemVendaRepository
            .findById(itemVendaDTO.getId())
            .map(existingItemVenda -> {
                itemVendaMapper.partialUpdate(existingItemVenda, itemVendaDTO);

                return existingItemVenda;
            })
            .map(itemVendaRepository::save)
            .map(itemVendaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemVendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemVendas");
        return itemVendaRepository.findAll(pageable).map(itemVendaMapper::toDto);
    }

    public Page<ItemVendaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return itemVendaRepository.findAllWithEagerRelationships(pageable).map(itemVendaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemVendaDTO> findOne(Long id) {
        log.debug("Request to get ItemVenda : {}", id);
        return itemVendaRepository.findOneWithEagerRelationships(id).map(itemVendaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemVenda : {}", id);
        itemVendaRepository.deleteById(id);
    }
}
