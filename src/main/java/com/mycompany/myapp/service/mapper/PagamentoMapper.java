package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagamento} and its DTO {@link PagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {
    @Mapping(target = "plataformaPagamento", source = "plataformaPagamento", qualifiedByName = "plataformaPagamentoNomePlataformaPagamento")
    @Mapping(
        target = "lancamentoCarteiraCliente",
        source = "lancamentoCarteiraCliente",
        qualifiedByName = "lancamentoCarteiraClienteDescricaoLancamento"
    )
    PagamentoDTO toDto(Pagamento s);

    @Named("plataformaPagamentoNomePlataformaPagamento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomePlataformaPagamento", source = "nomePlataformaPagamento")
    PlataformaPagamentoDTO toDtoPlataformaPagamentoNomePlataformaPagamento(PlataformaPagamento plataformaPagamento);

    @Named("lancamentoCarteiraClienteDescricaoLancamento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricaoLancamento", source = "descricaoLancamento")
    LancamentoCarteiraClienteDTO toDtoLancamentoCarteiraClienteDescricaoLancamento(LancamentoCarteiraCliente lancamentoCarteiraCliente);
}
