package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.repository.ItemVendaRepository;
import com.mycompany.myapp.service.criteria.ItemVendaCriteria;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
import com.mycompany.myapp.service.mapper.ItemVendaMapper;
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
 * Service for executing complex queries for {@link ItemVenda} entities in the database.
 * The main input is a {@link ItemVendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemVendaDTO} or a {@link Page} of {@link ItemVendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemVendaQueryService extends QueryService<ItemVenda> {

    private final Logger log = LoggerFactory.getLogger(ItemVendaQueryService.class);

    private final ItemVendaRepository itemVendaRepository;

    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaQueryService(ItemVendaRepository itemVendaRepository, ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    /**
     * Return a {@link List} of {@link ItemVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemVendaDTO> findByCriteria(ItemVendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaMapper.toDto(itemVendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemVendaDTO> findByCriteria(ItemVendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaRepository.findAll(specification, page).map(itemVendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemVendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemVendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemVenda> createSpecification(ItemVendaCriteria criteria) {
        Specification<ItemVenda> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemVenda_.id));
            }
            if (criteria.getDataHora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHora(), ItemVenda_.dataHora));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), ItemVenda_.quantidade));
            }
            if (criteria.getValorUnitario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorUnitario(), ItemVenda_.valorUnitario));
            }
            if (criteria.getValorTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorTotal(), ItemVenda_.valorTotal));
            }
            if (criteria.getValorDescontoPercentual() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorDescontoPercentual(), ItemVenda_.valorDescontoPercentual));
            }
            if (criteria.getValorDescontoReal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorDescontoReal(), ItemVenda_.valorDescontoReal));
            }
            if (criteria.getValorTotalComDesconto() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorTotalComDesconto(), ItemVenda_.valorTotalComDesconto));
            }
            if (criteria.getIndicadorItemPresente() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIndicadorItemPresente(), ItemVenda_.indicadorItemPresente));
            }
            if (criteria.getDataHoraCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataHoraCadastro(), ItemVenda_.dataHoraCadastro));
            }
            if (criteria.getColaboradorCadastro() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorCadastro(), ItemVenda_.colaboradorCadastro));
            }
            if (criteria.getDataHoraAtualizacao() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDataHoraAtualizacao(), ItemVenda_.dataHoraAtualizacao));
            }
            if (criteria.getColaboradorAtualizacao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getColaboradorAtualizacao(), ItemVenda_.colaboradorAtualizacao));
            }
            if (criteria.getServicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getServicoId(), root -> root.join(ItemVenda_.servico, JoinType.LEFT).get(Servico_.id))
                    );
            }
            if (criteria.getColaboradorQueIndicouId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getColaboradorQueIndicouId(),
                            root -> root.join(ItemVenda_.colaboradorQueIndicou, JoinType.LEFT).get(Colaborador_.id)
                        )
                    );
            }
            if (criteria.getClienteQueVaiRealizarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteQueVaiRealizarId(),
                            root -> root.join(ItemVenda_.clienteQueVaiRealizar, JoinType.LEFT).get(Cliente_.id)
                        )
                    );
            }
            if (criteria.getVendasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVendasId(), root -> root.join(ItemVenda_.vendas, JoinType.LEFT).get(Venda_.id))
                    );
            }
        }
        return specification;
    }
}
