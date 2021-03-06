package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "carteiraCliente", source = "carteiraCliente", qualifiedByName = "carteiraClienteNomeCarteiraCliente")
    ClienteDTO toDto(Cliente s);

    @Named("carteiraClienteNomeCarteiraCliente")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeCarteiraCliente", source = "nomeCarteiraCliente")
    CarteiraClienteDTO toDtoCarteiraClienteNomeCarteiraCliente(CarteiraCliente carteiraCliente);
}
