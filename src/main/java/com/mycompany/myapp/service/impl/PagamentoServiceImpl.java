package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.repository.PagamentoRepository;
import com.mycompany.myapp.service.PagamentoService;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.mapper.PagamentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pagamento}.
 */
@Service
@Transactional
public class PagamentoServiceImpl implements PagamentoService {

    private final Logger log = LoggerFactory.getLogger(PagamentoServiceImpl.class);

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoMapper pagamentoMapper;

    public PagamentoServiceImpl(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    @Override
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamento);
    }

    @Override
    public PagamentoDTO update(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDto(pagamento);
    }

    @Override
    public Optional<PagamentoDTO> partialUpdate(PagamentoDTO pagamentoDTO) {
        log.debug("Request to partially update Pagamento : {}", pagamentoDTO);

        return pagamentoRepository
            .findById(pagamentoDTO.getId())
            .map(existingPagamento -> {
                pagamentoMapper.partialUpdate(existingPagamento, pagamentoDTO);

                return existingPagamento;
            })
            .map(pagamentoRepository::save)
            .map(pagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pagamentos");
        return pagamentoRepository.findAll(pageable).map(pagamentoMapper::toDto);
    }

    public Page<PagamentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pagamentoRepository.findAllWithEagerRelationships(pageable).map(pagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PagamentoDTO> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findOneWithEagerRelationships(id).map(pagamentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pagamento : {}", id);
        pagamentoRepository.deleteById(id);
    }
}
