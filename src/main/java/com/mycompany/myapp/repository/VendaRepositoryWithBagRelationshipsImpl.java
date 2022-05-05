package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Venda;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VendaRepositoryWithBagRelationshipsImpl implements VendaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Venda> fetchBagRelationships(Optional<Venda> venda) {
        return venda.map(this::fetchColaboradoresQueIndicarams).map(this::fetchItensVendas).map(this::fetchPagamentos);
    }

    @Override
    public Page<Venda> fetchBagRelationships(Page<Venda> vendas) {
        return new PageImpl<>(fetchBagRelationships(vendas.getContent()), vendas.getPageable(), vendas.getTotalElements());
    }

    @Override
    public List<Venda> fetchBagRelationships(List<Venda> vendas) {
        return Optional
            .of(vendas)
            .map(this::fetchColaboradoresQueIndicarams)
            .map(this::fetchItensVendas)
            .map(this::fetchPagamentos)
            .orElse(Collections.emptyList());
    }

    Venda fetchColaboradoresQueIndicarams(Venda result) {
        return entityManager
            .createQuery(
                "select venda from Venda venda left join fetch venda.colaboradoresQueIndicarams where venda is :venda",
                Venda.class
            )
            .setParameter("venda", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Venda> fetchColaboradoresQueIndicarams(List<Venda> vendas) {
        return entityManager
            .createQuery(
                "select distinct venda from Venda venda left join fetch venda.colaboradoresQueIndicarams where venda in :vendas",
                Venda.class
            )
            .setParameter("vendas", vendas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    Venda fetchItensVendas(Venda result) {
        return entityManager
            .createQuery("select venda from Venda venda left join fetch venda.itensVendas where venda is :venda", Venda.class)
            .setParameter("venda", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Venda> fetchItensVendas(List<Venda> vendas) {
        return entityManager
            .createQuery("select distinct venda from Venda venda left join fetch venda.itensVendas where venda in :vendas", Venda.class)
            .setParameter("vendas", vendas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    Venda fetchPagamentos(Venda result) {
        return entityManager
            .createQuery("select venda from Venda venda left join fetch venda.pagamentos where venda is :venda", Venda.class)
            .setParameter("venda", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Venda> fetchPagamentos(List<Venda> vendas) {
        return entityManager
            .createQuery("select distinct venda from Venda venda left join fetch venda.pagamentos where venda in :vendas", Venda.class)
            .setParameter("vendas", vendas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
