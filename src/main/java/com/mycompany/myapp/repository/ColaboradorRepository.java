package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Colaborador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Colaborador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>, JpaSpecificationExecutor<Colaborador> {}
