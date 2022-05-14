package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.repository.CarteiraClienteRepository;
import com.mycompany.myapp.service.criteria.CarteiraClienteCriteria;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.CarteiraClienteMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CarteiraCliente} entities in the database.
 * The main input is a {@link CarteiraClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CarteiraClienteDTO} or a {@link Page} of {@link CarteiraClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CarteiraClienteQueryService extends QueryService<CarteiraCliente> {

    private final Logger log = LoggerFactory.getLogger(CarteiraClienteQueryService.class);

    private final CarteiraClienteRepository carteiraClienteRepository;

    private final CarteiraClienteMapper carteiraClienteMapper;

    public CarteiraClienteQueryService(CarteiraClienteRepository carteiraClienteRepository, CarteiraClienteMapper carteiraClienteMapper) {
        this.carteiraClienteRepository = carteiraClienteRepository;
        this.carteiraClienteMapper = carteiraClienteMapper;
    }

    /**
     * Return a {@link List} of {@link CarteiraClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CarteiraClienteDTO> findByCriteria(CarteiraClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CarteiraCliente> specification = createSpecification(criteria);
        return carteiraClienteMapper.toDto(carteiraClienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CarteiraClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CarteiraClienteDTO> findByCriteria(CarteiraClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CarteiraCliente> specification = createSpecification(criteria);
        return carteiraClienteRepository.findAll(specification, page).map(carteiraClienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CarteiraClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CarteiraCliente> specification = createSpecification(criteria);
        return carteiraClienteRepository.count(specification);
    }

    /**
     * Function to convert {@link CarteiraClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CarteiraCliente> createSpecification(CarteiraClienteCriteria criteria) {
        Specification<CarteiraCliente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CarteiraCliente_.id));
            }
            if (criteria.getNomeCarteiraCliente() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNomeCarteiraCliente(), CarteiraCliente_.nomeCarteiraCliente));
            }
            if (criteria.getSaldoConsolidado() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSaldoConsolidado(), CarteiraCliente_.saldoConsolidado));
            }
            if (criteria.getTipoIndicadorSaldo() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTipoIndicadorSaldo(), CarteiraCliente_.tipoIndicadorSaldo));
            }
            if (criteria.getIndicadorBloqueio() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorBloqueio(), CarteiraCliente_.indicadorBloqueio));
            }
            if (criteria.getDataHoraCadastro() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDataHoraCadastro(), CarteiraCliente_.dataHoraCadastro));
            }
            if (criteria.getColaboradorCadastro() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorCadastro(), CarteiraCliente_.colaboradorCadastro));
            }
            if (criteria.getDataHoraAtualizacao() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDataHoraAtualizacao(), CarteiraCliente_.dataHoraAtualizacao));
            }
            if (criteria.getColaboradorAtualizacao() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getColaboradorAtualizacao(), CarteiraCliente_.colaboradorAtualizacao)
                    );
            }
            if (criteria.getLancamentoCarteiraClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLancamentoCarteiraClienteId(),
                            root -> root.join(CarteiraCliente_.lancamentoCarteiraClientes, JoinType.LEFT).get(LancamentoCarteiraCliente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
