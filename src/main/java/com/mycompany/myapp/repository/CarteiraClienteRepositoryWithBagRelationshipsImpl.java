package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarteiraCliente;
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
public class CarteiraClienteRepositoryWithBagRelationshipsImpl implements CarteiraClienteRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CarteiraCliente> fetchBagRelationships(Optional<CarteiraCliente> carteiraCliente) {
        return carteiraCliente.map(this::fetchLancamentoCarteiraClientes);
    }

    @Override
    public Page<CarteiraCliente> fetchBagRelationships(Page<CarteiraCliente> carteiraClientes) {
        return new PageImpl<>(
            fetchBagRelationships(carteiraClientes.getContent()),
            carteiraClientes.getPageable(),
            carteiraClientes.getTotalElements()
        );
    }

    @Override
    public List<CarteiraCliente> fetchBagRelationships(List<CarteiraCliente> carteiraClientes) {
        return Optional.of(carteiraClientes).map(this::fetchLancamentoCarteiraClientes).orElse(Collections.emptyList());
    }

    CarteiraCliente fetchLancamentoCarteiraClientes(CarteiraCliente result) {
        return entityManager
            .createQuery(
                "select carteiraCliente from CarteiraCliente carteiraCliente left join fetch carteiraCliente.lancamentoCarteiraClientes where carteiraCliente is :carteiraCliente",
                CarteiraCliente.class
            )
            .setParameter("carteiraCliente", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CarteiraCliente> fetchLancamentoCarteiraClientes(List<CarteiraCliente> carteiraClientes) {
        return entityManager
            .createQuery(
                "select distinct carteiraCliente from CarteiraCliente carteiraCliente left join fetch carteiraCliente.lancamentoCarteiraClientes where carteiraCliente in :carteiraClientes",
                CarteiraCliente.class
            )
            .setParameter("carteiraClientes", carteiraClientes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
