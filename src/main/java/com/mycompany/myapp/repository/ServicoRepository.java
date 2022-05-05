package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Servico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Servico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {}
