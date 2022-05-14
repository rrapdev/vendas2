package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.repository.VendaRepository;
import com.mycompany.myapp.service.criteria.VendaCriteria;
import com.mycompany.myapp.service.dto.VendaDTO;
import com.mycompany.myapp.service.mapper.VendaMapper;
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
 * Service for executing complex queries for {@link Venda} entities in the database.
 * The main input is a {@link VendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendaDTO} or a {@link Page} of {@link VendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendaQueryService extends QueryService<Venda> {

    private final Logger log = LoggerFactory.getLogger(VendaQueryService.class);

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    public VendaQueryService(VendaRepository vendaRepository, VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    /**
     * Return a {@link List} of {@link VendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendaDTO> findByCriteria(VendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaMapper.toDto(vendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendaDTO> findByCriteria(VendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaRepository.findAll(specification, page).map(vendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaRepository.count(specification);
    }

    /**
     * Function to convert {@link VendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Venda> createSpecification(VendaCriteria criteria) {
        Specification<Venda> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Venda_.id));
            }
            if (criteria.getDataHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHora(), Venda_.dataHora));
            }
            if (criteria.getValorTotalBruto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotalBruto(), Venda_.valorTotalBruto));
            }
            if (criteria.getValorTotalDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotalDesconto(), Venda_.valorTotalDesconto));
            }
            if (criteria.getValorTotalLiquido() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotalLiquido(), Venda_.valorTotalLiquido));
            }
            if (criteria.getValorTotalPago() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotalPago(), Venda_.valorTotalPago));
            }
            if (criteria.getValorSaldoRestante() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSaldoRestante(), Venda_.valorSaldoRestante));
            }
            if (criteria.getIndicadorPossuiPagamentoPendente() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIndicadorPossuiPagamentoPendente(), Venda_.indicadorPossuiPagamentoPendente)
                    );
            }
            if (criteria.getIndicadorPossuiItemPresente() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIndicadorPossuiItemPresente(), Venda_.indicadorPossuiItemPresente));
            }
            if (criteria.getIndicadorBloqueio() != null) {
                specification = specification.and(buildSpecification(criteria.getIndicadorBloqueio(), Venda_.indicadorBloqueio));
            }
            if (criteria.getDataHoraCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraCadastro(), Venda_.dataHoraCadastro));
            }
            if (criteria.getColaboradorCadastro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColaboradorCadastro(), Venda_.colaboradorCadastro));
            }
            if (criteria.getDataHoraAtualizacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraAtualizacao(), Venda_.dataHoraAtualizacao));
            }
            if (criteria.getColaboradorAtualizacao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorAtualizacao(), Venda_.colaboradorAtualizacao));
            }
            if (criteria.getClienteQueComprouId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteQueComprouId(),
                            root -> root.join(Venda_.clienteQueComprou, JoinType.LEFT).get(Cliente_.id)
                        )
                    );
            }
            if (criteria.getColaboradoresQueIndicaramId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getColaboradoresQueIndicaramId(),
                            root -> root.join(Venda_.colaboradoresQueIndicarams, JoinType.LEFT).get(Colaborador_.id)
                        )
                    );
            }
            if (criteria.getItensVendaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getItensVendaId(),
                            root -> root.join(Venda_.itensVendas, JoinType.LEFT).get(ItemVenda_.id)
                        )
                    );
            }
            if (criteria.getPagamentosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPagamentosId(),
                            root -> root.join(Venda_.pagamentos, JoinType.LEFT).get(Pagamento_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
