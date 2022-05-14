package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ItemVenda;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ItemVenda entity.
 */
@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>, JpaSpecificationExecutor<ItemVenda> {
    default Optional<ItemVenda> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ItemVenda> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ItemVenda> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct itemVenda from ItemVenda itemVenda left join fetch itemVenda.servico left join fetch itemVenda.colaboradorQueIndicou left join fetch itemVenda.clienteQueVaiRealizar",
        countQuery = "select count(distinct itemVenda) from ItemVenda itemVenda"
    )
    Page<ItemVenda> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct itemVenda from ItemVenda itemVenda left join fetch itemVenda.servico left join fetch itemVenda.colaboradorQueIndicou left join fetch itemVenda.clienteQueVaiRealizar"
    )
    List<ItemVenda> findAllWithToOneRelationships();

    @Query(
        "select itemVenda from ItemVenda itemVenda left join fetch itemVenda.servico left join fetch itemVenda.colaboradorQueIndicou left join fetch itemVenda.clienteQueVaiRealizar where itemVenda.id =:id"
    )
    Optional<ItemVenda> findOneWithToOneRelationships(@Param("id") Long id);
}
