package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.repository.PlataformaPagamentoRepository;
import com.mycompany.myapp.service.criteria.PlataformaPagamentoCriteria;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import com.mycompany.myapp.service.mapper.PlataformaPagamentoMapper;
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
 * Service for executing complex queries for {@link PlataformaPagamento} entities in the database.
 * The main input is a {@link PlataformaPagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlataformaPagamentoDTO} or a {@link Page} of {@link PlataformaPagamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlataformaPagamentoQueryService extends QueryService<PlataformaPagamento> {

    private final Logger log = LoggerFactory.getLogger(PlataformaPagamentoQueryService.class);

    private final PlataformaPagamentoRepository plataformaPagamentoRepository;

    private final PlataformaPagamentoMapper plataformaPagamentoMapper;

    public PlataformaPagamentoQueryService(
        PlataformaPagamentoRepository plataformaPagamentoRepository,
        PlataformaPagamentoMapper plataformaPagamentoMapper
    ) {
        this.plataformaPagamentoRepository = plataformaPagamentoRepository;
        this.plataformaPagamentoMapper = plataformaPagamentoMapper;
    }

    /**
     * Return a {@link List} of {@link PlataformaPagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlataformaPagamentoDTO> findByCriteria(PlataformaPagamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlataformaPagamento> specification = createSpecification(criteria);
        return plataformaPagamentoMapper.toDto(plataformaPagamentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlataformaPagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlataformaPagamentoDTO> findByCriteria(PlataformaPagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlataformaPagamento> specification = createSpecification(criteria);
        return plataformaPagamentoRepository.findAll(specification, page).map(plataformaPagamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlataformaPagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlataformaPagamento> specification = createSpecification(criteria);
        return plataformaPagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PlataformaPagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlataformaPagamento> createSpecification(PlataformaPagamentoCriteria criteria) {
        Specification<PlataformaPagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlataformaPagamento_.id));
            }
            if (criteria.getNomePlataformaPagamento() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getNomePlataformaPagamento(), PlataformaPagamento_.nomePlataformaPagamento)
                    );
            }
            if (criteria.getIndicadorAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorAtivo(), PlataformaPagamento_.indicadorAtivo));
            }
        }
        return specification;
    }
}
