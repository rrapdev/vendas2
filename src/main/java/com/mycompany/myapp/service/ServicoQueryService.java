package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Servico;
import com.mycompany.myapp.repository.ServicoRepository;
import com.mycompany.myapp.service.criteria.ServicoCriteria;
import com.mycompany.myapp.service.dto.ServicoDTO;
import com.mycompany.myapp.service.mapper.ServicoMapper;
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
 * Service for executing complex queries for {@link Servico} entities in the database.
 * The main input is a {@link ServicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServicoDTO} or a {@link Page} of {@link ServicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicoQueryService extends QueryService<Servico> {

    private final Logger log = LoggerFactory.getLogger(ServicoQueryService.class);

    private final ServicoRepository servicoRepository;

    private final ServicoMapper servicoMapper;

    public ServicoQueryService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    /**
     * Return a {@link List} of {@link ServicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> findByCriteria(ServicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Servico> specification = createSpecification(criteria);
        return servicoMapper.toDto(servicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicoDTO> findByCriteria(ServicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Servico> specification = createSpecification(criteria);
        return servicoRepository.findAll(specification, page).map(servicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Servico> specification = createSpecification(criteria);
        return servicoRepository.count(specification);
    }

    /**
     * Function to convert {@link ServicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Servico> createSpecification(ServicoCriteria criteria) {
        Specification<Servico> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Servico_.id));
            }
            if (criteria.getNomeServico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeServico(), Servico_.nomeServico));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Servico_.valor));
            }
            if (criteria.getIndicadorAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorAtivo(), Servico_.indicadorAtivo));
            }
        }
        return specification;
    }
}
