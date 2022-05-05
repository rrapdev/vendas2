package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CarteiraCliente;
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.service.dto.CarteiraClienteDTO;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarteiraCliente} and its DTO {@link CarteiraClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarteiraClienteMapper extends EntityMapper<CarteiraClienteDTO, CarteiraCliente> {
    @Mapping(
        target = "lancamentoCarteiraClientes",
        source = "lancamentoCarteiraClientes",
        qualifiedByName = "lancamentoCarteiraClienteDescricaoLancamentoSet"
    )
    CarteiraClienteDTO toDto(CarteiraCliente s);

    @Mapping(target = "removeLancamentoCarteiraCliente", ignore = true)
    CarteiraCliente toEntity(CarteiraClienteDTO carteiraClienteDTO);

    @Named("lancamentoCarteiraClienteDescricaoLancamento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricaoLancamento", source = "descricaoLancamento")
    LancamentoCarteiraClienteDTO toDtoLancamentoCarteiraClienteDescricaoLancamento(LancamentoCarteiraCliente lancamentoCarteiraCliente);

    @Named("lancamentoCarteiraClienteDescricaoLancamentoSet")
    default Set<LancamentoCarteiraClienteDTO> toDtoLancamentoCarteiraClienteDescricaoLancamentoSet(
        Set<LancamentoCarteiraCliente> lancamentoCarteiraCliente
    ) {
        return lancamentoCarteiraCliente.stream().map(this::toDtoLancamentoCarteiraClienteDescricaoLancamento).collect(Collectors.toSet());
    }
}
