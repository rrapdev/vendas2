package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.repository.ColaboradorRepository;
import com.mycompany.myapp.service.criteria.ColaboradorCriteria;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import com.mycompany.myapp.service.mapper.ColaboradorMapper;
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
 * Service for executing complex queries for {@link Colaborador} entities in the database.
 * The main input is a {@link ColaboradorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ColaboradorDTO} or a {@link Page} of {@link ColaboradorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColaboradorQueryService extends QueryService<Colaborador> {

    private final Logger log = LoggerFactory.getLogger(ColaboradorQueryService.class);

    private final ColaboradorRepository colaboradorRepository;

    private final ColaboradorMapper colaboradorMapper;

    public ColaboradorQueryService(ColaboradorRepository colaboradorRepository, ColaboradorMapper colaboradorMapper) {
        this.colaboradorRepository = colaboradorRepository;
        this.colaboradorMapper = colaboradorMapper;
    }

    /**
     * Return a {@link List} of {@link ColaboradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ColaboradorDTO> findByCriteria(ColaboradorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Colaborador> specification = createSpecification(criteria);
        return colaboradorMapper.toDto(colaboradorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ColaboradorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ColaboradorDTO> findByCriteria(ColaboradorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Colaborador> specification = createSpecification(criteria);
        return colaboradorRepository.findAll(specification, page).map(colaboradorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColaboradorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Colaborador> specification = createSpecification(criteria);
        return colaboradorRepository.count(specification);
    }

    /**
     * Function to convert {@link ColaboradorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Colaborador> createSpecification(ColaboradorCriteria criteria) {
        Specification<Colaborador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Colaborador_.id));
            }
            if (criteria.getNomeColaborador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeColaborador(), Colaborador_.nomeColaborador));
            }
            if (criteria.getNomeApresentacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeApresentacao(), Colaborador_.nomeApresentacao));
            }
            if (criteria.getIndicadorAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorAtivo(), Colaborador_.indicadorAtivo));
            }
            if (criteria.getVendasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVendasId(), root -> root.join(Colaborador_.vendas, JoinType.LEFT).get(Venda_.id))
                    );
            }
        }
        return specification;
    }
}
