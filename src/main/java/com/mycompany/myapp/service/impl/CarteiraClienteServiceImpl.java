package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.repository.CarteiraClienteRepository;
import com.mycompany.myapp.service.CarteiraClienteService;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.CarteiraClienteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarteiraCliente}.
 */
@Service
@Transactional
public class CarteiraClienteServiceImpl implements CarteiraClienteService {

    private final Logger log = LoggerFactory.getLogger(CarteiraClienteServiceImpl.class);

    private final CarteiraClienteRepository carteiraClienteRepository;

    private final CarteiraClienteMapper carteiraClienteMapper;

    public CarteiraClienteServiceImpl(CarteiraClienteRepository carteiraClienteRepository, CarteiraClienteMapper carteiraClienteMapper) {
        this.carteiraClienteRepository = carteiraClienteRepository;
        this.carteiraClienteMapper = carteiraClienteMapper;
    }

    @Override
    public CarteiraClienteDTO save(CarteiraClienteDTO carteiraClienteDTO) {
        log.debug("Request to save CarteiraCliente : {}", carteiraClienteDTO);
        CarteiraCliente carteiraCliente = carteiraClienteMapper.toEntity(carteiraClienteDTO);
        carteiraCliente = carteiraClienteRepository.save(carteiraCliente);
        return carteiraClienteMapper.toDto(carteiraCliente);
    }

    @Override
    public CarteiraClienteDTO update(CarteiraClienteDTO carteiraClienteDTO) {
        log.debug("Request to save CarteiraCliente : {}", carteiraClienteDTO);
        CarteiraCliente carteiraCliente = carteiraClienteMapper.toEntity(carteiraClienteDTO);
        carteiraCliente = carteiraClienteRepository.save(carteiraCliente);
        return carteiraClienteMapper.toDto(carteiraCliente);
    }

    @Override
    public Optional<CarteiraClienteDTO> partialUpdate(CarteiraClienteDTO carteiraClienteDTO) {
        log.debug("Request to partially update CarteiraCliente : {}", carteiraClienteDTO);

        return carteiraClienteRepository
            .findById(carteiraClienteDTO.getId())
            .map(existingCarteiraCliente -> {
                carteiraClienteMapper.partialUpdate(existingCarteiraCliente, carteiraClienteDTO);

                return existingCarteiraCliente;
            })
            .map(carteiraClienteRepository::save)
            .map(carteiraClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarteiraClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarteiraClientes");
        return carteiraClienteRepository.findAll(pageable).map(carteiraClienteMapper::toDto);
    }

    public Page<CarteiraClienteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return carteiraClienteRepository.findAllWithEagerRelationships(pageable).map(carteiraClienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarteiraClienteDTO> findOne(Long id) {
        log.debug("Request to get CarteiraCliente : {}", id);
        return carteiraClienteRepository.findOneWithEagerRelationships(id).map(carteiraClienteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarteiraCliente : {}", id);
        carteiraClienteRepository.deleteById(id);
    }
}
