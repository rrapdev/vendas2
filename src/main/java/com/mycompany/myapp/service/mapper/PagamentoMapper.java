package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagamento} and its DTO {@link PagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {
    @Mapping(target = "adquirentePagamento", source = "adquirentePagamento", qualifiedByName = "plataformaPagamentoNomePlataformaPagamento")
    PagamentoDTO toDto(Pagamento s);

    @Named("plataformaPagamentoNomePlataformaPagamento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomePlataformaPagamento", source = "nomePlataformaPagamento")
    PlataformaPagamentoDTO toDtoPlataformaPagamentoNomePlataformaPagamento(PlataformaPagamento plataformaPagamento);
}
