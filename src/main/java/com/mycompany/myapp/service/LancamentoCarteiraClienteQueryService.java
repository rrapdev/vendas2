package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.repository.LancamentoCarteiraClienteRepository;
import com.mycompany.myapp.service.criteria.LancamentoCarteiraClienteCriteria;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.mapper.LancamentoCarteiraClienteMapper;
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
 * Service for executing complex queries for {@link LancamentoCarteiraCliente} entities in the database.
 * The main input is a {@link LancamentoCarteiraClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LancamentoCarteiraClienteDTO} or a {@link Page} of {@link LancamentoCarteiraClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LancamentoCarteiraClienteQueryService extends QueryService<LancamentoCarteiraCliente> {

    private final Logger log = LoggerFactory.getLogger(LancamentoCarteiraClienteQueryService.class);

    private final LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository;

    private final LancamentoCarteiraClienteMapper lancamentoCarteiraClienteMapper;

    public LancamentoCarteiraClienteQueryService(
        LancamentoCarteiraClienteRepository lancamentoCarteiraClienteRepository,
        LancamentoCarteiraClienteMapper lancamentoCarteiraClienteMapper
    ) {
        this.lancamentoCarteiraClienteRepository = lancamentoCarteiraClienteRepository;
        this.lancamentoCarteiraClienteMapper = lancamentoCarteiraClienteMapper;
    }

    /**
     * Return a {@link List} of {@link LancamentoCarteiraClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LancamentoCarteiraClienteDTO> findByCriteria(LancamentoCarteiraClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LancamentoCarteiraCliente> specification = createSpecification(criteria);
        return lancamentoCarteiraClienteMapper.toDto(lancamentoCarteiraClienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LancamentoCarteiraClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoCarteiraClienteDTO> findByCriteria(LancamentoCarteiraClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LancamentoCarteiraCliente> specification = createSpecification(criteria);
        return lancamentoCarteiraClienteRepository.findAll(specification, page).map(lancamentoCarteiraClienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LancamentoCarteiraClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LancamentoCarteiraCliente> specification = createSpecification(criteria);
        return lancamentoCarteiraClienteRepository.count(specification);
    }

    /**
     * Function to convert {@link LancamentoCarteiraClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LancamentoCarteiraCliente> createSpecification(LancamentoCarteiraClienteCriteria criteria) {
        Specification<LancamentoCarteiraCliente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LancamentoCarteiraCliente_.id));
            }
            if (criteria.getDescricaoLancamento() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getDescricaoLancamento(), LancamentoCarteiraCliente_.descricaoLancamento)
                    );
            }
            if (criteria.getDataHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHora(), LancamentoCarteiraCliente_.dataHora));
            }
            if (criteria.getValorCredito() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorCredito(), LancamentoCarteiraCliente_.valorCredito));
            }
            if (criteria.getValorDebito() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorDebito(), LancamentoCarteiraCliente_.valorDebito));
            }
            if (criteria.getObservacoes() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getObservacoes(), LancamentoCarteiraCliente_.observacoes));
            }
            if (criteria.getIndicadorBloqueio() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIndicadorBloqueio(), LancamentoCarteiraCliente_.indicadorBloqueio));
            }
            if (criteria.getDataHoraCadastro() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDataHoraCadastro(), LancamentoCarteiraCliente_.dataHoraCadastro));
            }
            if (criteria.getColaboradorCadastro() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getColaboradorCadastro(), LancamentoCarteiraCliente_.colaboradorCadastro)
                    );
            }
            if (criteria.getDataHoraAtualizacao() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDataHoraAtualizacao(), LancamentoCarteiraCliente_.dataHoraAtualizacao)
                    );
            }
            if (criteria.getColaboradorAtualizacao() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getColaboradorAtualizacao(), LancamentoCarteiraCliente_.colaboradorAtualizacao)
                    );
            }
            if (criteria.getVendaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendaId(),
                            root -> root.join(LancamentoCarteiraCliente_.venda, JoinType.LEFT).get(Venda_.id)
                        )
                    );
            }
            if (criteria.getPagamentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPagamentoId(),
                            root -> root.join(LancamentoCarteiraCliente_.pagamento, JoinType.LEFT).get(Pagamento_.id)
                        )
                    );
            }
            if (criteria.getCarteirasClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCarteirasClienteId(),
                            root -> root.join(LancamentoCarteiraCliente_.carteirasClientes, JoinType.LEFT).get(CarteiraCliente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
