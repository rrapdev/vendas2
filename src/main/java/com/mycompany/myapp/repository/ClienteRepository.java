package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    default Optional<Cliente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Cliente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Cliente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct cliente from Cliente cliente left join fetch cliente.carteiraCliente",
        countQuery = "select count(distinct cliente) from Cliente cliente"
    )
    Page<Cliente> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct cliente from Cliente cliente left join fetch cliente.carteiraCliente")
    List<Cliente> findAllWithToOneRelationships();

    @Query("select cliente from Cliente cliente left join fetch cliente.carteiraCliente where cliente.id =:id")
    Optional<Cliente> findOneWithToOneRelationships(@Param("id") Long id);
}
