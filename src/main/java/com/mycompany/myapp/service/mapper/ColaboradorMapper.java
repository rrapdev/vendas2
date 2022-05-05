package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Colaborador;
import com.mycompany.myapp.service.dto.ColaboradorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Colaborador} and its DTO {@link ColaboradorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ColaboradorMapper extends EntityMapper<ColaboradorDTO, Colaborador> {}
