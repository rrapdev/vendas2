package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.domain.Pagamento;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.service.dto.ClienteDTO;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import com.mycompany.myapp.service.dto.PagamentoDTO;
import com.mycompany.myapp.service.dto.VendaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venda} and its DTO {@link VendaDTO}.
 */
@Mapper(componentModel = "spring")
public interface VendaMapper extends EntityMapper<VendaDTO, Venda> {
    @Mapping(target = "clienteQueComprou", source = "clienteQueComprou", qualifiedByName = "clienteNomeCompleto")
    @Mapping(
        target = "lancamentoCarteiraCliente",
        source = "lancamentoCarteiraCliente",
        qualifiedByName = "lancamentoCarteiraClienteDescricaoLancamento"
    )
    @Mapping(
        target = "colaboradoresQueIndicarams",
        source = "colaboradoresQueIndicarams",
        qualifiedByName = "colaboradorNomeApresentacaoSet"
    )
    @Mapping(target = "itensVendas", source = "itensVendas", qualifiedByName = "itemVendaQuantidadeSet")
    @Mapping(target = "pagamentos", source = "pagamentos", qualifiedByName = "pagamentoValorSet")
    VendaDTO toDto(Venda s);

    @Mapping(target = "removeColaboradoresQueIndicaram", ignore = true)
    @Mapping(target = "removeItensVenda", ignore = true)
    @Mapping(target = "removePagamentos", ignore = true)
    Venda toEntity(VendaDTO vendaDTO);

    @Named("itemVendaQuantidade")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "quantidade", source = "quantidade")
    ItemVendaDTO toDtoItemVendaQuantidade(ItemVenda itemVenda);

    @Named("itemVendaQuantidadeSet")
    default Set<ItemVendaDTO> toDtoItemVendaQuantidadeSet(Set<ItemVenda> itemVenda) {
        return itemVenda.stream().map(this::toDtoItemVendaQuantidade).collect(Collectors.toSet());
    }

    @Named("clienteNomeCompleto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeCompleto", source = "nomeCompleto")
    ClienteDTO toDtoClienteNomeCompleto(Cliente cliente);

    @Named("lancamentoCarteiraClienteDescricaoLancamento")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricaoLancamento", source = "descricaoLancamento")
    LancamentoCarteiraClienteDTO toDtoLancamentoCarteiraClienteDescricaoLancamento(LancamentoCarteiraCliente lancamentoCarteiraCliente);

    @Named("colaboradorNomeApresentacao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeApresentacao", source = "nomeApresentacao")
    ColaboradorDTO toDtoColaboradorNomeApresentacao(Colaborador colaborador);

    @Named("colaboradorNomeApresentacaoSet")
    default Set<ColaboradorDTO> toDtoColaboradorNomeApresentacaoSet(Set<Colaborador> colaborador) {
        return colaborador.stream().map(this::toDtoColaboradorNomeApresentacao).collect(Collectors.toSet());
    }

    @Named("pagamentoValor")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "valor", source = "valor")
    PagamentoDTO toDtoPagamentoValor(Pagamento pagamento);

    @Named("pagamentoValorSet")
    default Set<PagamentoDTO> toDtoPagamentoValorSet(Set<Pagamento> pagamento) {
        return pagamento.stream().map(this::toDtoPagamentoValor).collect(Collectors.toSet());
    }
}
