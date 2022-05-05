package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarteiraCliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarteiraCliente entity.
 */
@Repository
public interface CarteiraClienteRepository extends CarteiraClienteRepositoryWithBagRelationships, JpaRepository<CarteiraCliente, Long> {
    default Optional<CarteiraCliente> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CarteiraCliente> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CarteiraCliente> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
