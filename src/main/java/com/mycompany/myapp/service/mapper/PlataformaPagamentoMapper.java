package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PlataformaPagamento;
import com.mycompany.myapp.service.dto.PlataformaPagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlataformaPagamento} and its DTO {@link PlataformaPagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlataformaPagamentoMapper extends EntityMapper<PlataformaPagamentoDTO, PlataformaPagamento> {}
