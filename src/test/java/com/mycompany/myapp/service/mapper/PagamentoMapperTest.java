package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagamentoMapperTest {

    private PagamentoMapper pagamentoMapper;

    @BeforeEach
    public void setUp() {
        pagamentoMapper = new PagamentoMapperImpl();
    }
}
