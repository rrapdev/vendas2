package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlataformaPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlataformaPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlataformaPagamentoRepository
    extends JpaRepository<PlataformaPagamento, Long>, JpaSpecificationExecutor<PlataformaPagamento> {}
