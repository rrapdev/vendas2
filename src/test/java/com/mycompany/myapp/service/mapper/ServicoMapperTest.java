package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServicoMapperTest {

    private ServicoMapper servicoMapper;

    @BeforeEach
    public void setUp() {
        servicoMapper = new ServicoMapperImpl();
    }
}
