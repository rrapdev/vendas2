package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlataformaPagamentoMapperTest {

    private PlataformaPagamentoMapper plataformaPagamentoMapper;

    @BeforeEach
    public void setUp() {
        plataformaPagamentoMapper = new PlataformaPagamentoMapperImpl();
    }
}
