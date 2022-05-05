package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Venda;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Venda entity.
 */
@Repository
public interface VendaRepository extends VendaRepositoryWithBagRelationships, JpaRepository<Venda, Long> {
    default Optional<Venda> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Venda> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Venda> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct venda from Venda venda left join fetch venda.clienteQueComprou left join fetch venda.lancamentoCarteiraCliente",
        countQuery = "select count(distinct venda) from Venda venda"
    )
    Page<Venda> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct venda from Venda venda left join fetch venda.clienteQueComprou left join fetch venda.lancamentoCarteiraCliente")
    List<Venda> findAllWithToOneRelationships();

    @Query(
        "select venda from Venda venda left join fetch venda.clienteQueComprou left join fetch venda.lancamentoCarteiraCliente where venda.id =:id"
    )
    Optional<Venda> findOneWithToOneRelationships(@Param("id") Long id);
}
