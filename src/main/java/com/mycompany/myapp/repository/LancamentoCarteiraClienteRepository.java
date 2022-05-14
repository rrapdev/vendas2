package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LancamentoCarteiraCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoCarteiraClienteRepository
    extends JpaRepository<LancamentoCarteiraCliente, Long>, JpaSpecificationExecutor<LancamentoCarteiraCliente> {}
