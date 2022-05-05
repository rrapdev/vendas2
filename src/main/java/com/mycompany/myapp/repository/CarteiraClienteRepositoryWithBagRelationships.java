package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CarteiraCliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CarteiraClienteRepositoryWithBagRelationships {
    Optional<CarteiraCliente> fetchBagRelationships(Optional<CarteiraCliente> carteiraCliente);

    List<CarteiraCliente> fetchBagRelationships(List<CarteiraCliente> carteiraClientes);

    Page<CarteiraCliente> fetchBagRelationships(Page<CarteiraCliente> carteiraClientes);
}
