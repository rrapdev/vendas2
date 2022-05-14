package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.dto.VendaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LancamentoCarteiraCliente} and its DTO {@link LancamentoCarteiraClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface LancamentoCarteiraClienteMapper extends EntityMapper<LancamentoCarteiraClienteDTO, LancamentoCarteiraCliente> {
    @Mapping(target = "venda", source = "venda", qualifiedByName = "vendaId")
    @Mapping(target = "pagamento", source = "pagamento", qualifiedByName = "pagamentoId")
    LancamentoCarteiraClienteDTO toDto(LancamentoCarteiraCliente s);

    @Named("vendaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendaDTO toDtoVendaId(Venda venda);

    @Named("pagamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PagamentoDTO toDtoPagamentoId(Pagamento pagamento);
}
