package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarteiraClienteMapperTest {

    private CarteiraClienteMapper carteiraClienteMapper;

    @BeforeEach
    public void setUp() {
        carteiraClienteMapper = new CarteiraClienteMapperImpl();
    }
}
