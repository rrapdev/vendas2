package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.repository.PagamentoRepository;
import com.mycompany.myapp.service.criteria.PagamentoCriteria;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.mapper.PagamentoMapper;
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
 * Service for executing complex queries for {@link Pagamento} entities in the database.
 * The main input is a {@link PagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PagamentoDTO} or a {@link Page} of {@link PagamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PagamentoQueryService extends QueryService<Pagamento> {

    private final Logger log = LoggerFactory.getLogger(PagamentoQueryService.class);

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoMapper pagamentoMapper;

    public PagamentoQueryService(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    /**
     * Return a {@link List} of {@link PagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PagamentoDTO> findByCriteria(PagamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pagamento> specification = createSpecification(criteria);
        return pagamentoMapper.toDto(pagamentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findByCriteria(PagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pagamento> specification = createSpecification(criteria);
        return pagamentoRepository.findAll(specification, page).map(pagamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pagamento> specification = createSpecification(criteria);
        return pagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pagamento> createSpecification(PagamentoCriteria criteria) {
        Specification<Pagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pagamento_.id));
            }
            if (criteria.getFormaPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getFormaPagamento(), Pagamento_.formaPagamento));
            }
            if (criteria.getDataHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHora(), Pagamento_.dataHora));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Pagamento_.valor));
            }
            if (criteria.getObservacoes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacoes(), Pagamento_.observacoes));
            }
            if (criteria.getNumeroParcelas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroParcelas(), Pagamento_.numeroParcelas));
            }
            if (criteria.getBandeiraCartao() != null) {
                specification = specification.and(buildSpecification(criteria.getBandeiraCartao(), Pagamento_.bandeiraCartao));
            }
            if (criteria.getClienteOrigemPagamento() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getClienteOrigemPagamento(), Pagamento_.clienteOrigemPagamento));
            }
            if (criteria.getIndicadorConferido() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorConferido(), Pagamento_.indicadorConferido));
            }
            if (criteria.getDataHoraCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraCadastro(), Pagamento_.dataHoraCadastro));
            }
            if (criteria.getColaboradorCadastro() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorCadastro(), Pagamento_.colaboradorCadastro));
            }
            if (criteria.getDataHoraAtualizacao() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDataHoraAtualizacao(), Pagamento_.dataHoraAtualizacao));
            }
            if (criteria.getColaboradorAtualizacao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorAtualizacao(), Pagamento_.colaboradorAtualizacao));
            }
            if (criteria.getAdquirentePagamentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdquirentePagamentoId(),
                            root -> root.join(Pagamento_.adquirentePagamento, JoinType.LEFT).get(PlataformaPagamento_.id)
                        )
                    );
            }
            if (criteria.getVendasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVendasId(), root -> root.join(Pagamento_.vendas, JoinType.LEFT).get(Venda_.id))
                    );
            }
        }
        return specification;
    }
}
