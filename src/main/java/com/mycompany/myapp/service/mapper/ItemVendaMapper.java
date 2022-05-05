package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.domain.ItemVenda;
import com.mycompany.myapp.domain.Servico;
import com.mycompany.myapp.domain.Venda;
import com.mycompany.myapp.service.dto.ClienteDTO;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import com.mycompany.myapp.service.dto.ItemVendaDTO;
import com.mycompany.myapp.service.dto.ServicoDTO;
import com.mycompany.myapp.service.dto.VendaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemVenda} and its DTO {@link ItemVendaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemVendaMapper extends EntityMapper<ItemVendaDTO, ItemVenda> {
    @Mapping(target = "servico", source = "servico", qualifiedByName = "servicoNomeServico")
    @Mapping(target = "colaboradorQueIndicou", source = "colaboradorQueIndicou", qualifiedByName = "colaboradorNomeApresentacao")
    @Mapping(target = "clienteQueVaiRealizar", source = "clienteQueVaiRealizar", qualifiedByName = "clienteNomeCompleto")
    @Mapping(target = "venda", source = "venda", qualifiedByName = "vendaId")
    ItemVendaDTO toDto(ItemVenda s);

    @Named("servicoNomeServico")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeServico", source = "nomeServico")
    ServicoDTO toDtoServicoNomeServico(Servico servico);

    @Named("colaboradorNomeApresentacao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeApresentacao", source = "nomeApresentacao")
    ColaboradorDTO toDtoColaboradorNomeApresentacao(Colaborador colaborador);

    @Named("clienteNomeCompleto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeCompleto", source = "nomeCompleto")
    ClienteDTO toDtoClienteNomeCompleto(Cliente cliente);

    @Named("vendaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendaDTO toDtoVendaId(Venda venda);
}
