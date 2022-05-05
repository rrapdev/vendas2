package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Servico;
import com.mycompany.myapp.service.dto.ServicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servico} and its DTO {@link ServicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicoMapper extends EntityMapper<ServicoDTO, Servico> {}
