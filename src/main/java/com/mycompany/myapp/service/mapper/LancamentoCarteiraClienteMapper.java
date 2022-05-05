package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.LancamentoCarteiraCliente;
import com.mycompany.myapp.service.dto.LancamentoCarteiraClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LancamentoCarteiraCliente} and its DTO {@link LancamentoCarteiraClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface LancamentoCarteiraClienteMapper extends EntityMapper<LancamentoCarteiraClienteDTO, LancamentoCarteiraCliente> {}
