package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorMapperTest {

    private ColaboradorMapper colaboradorMapper;

    @BeforeEach
    public void setUp() {
        colaboradorMapper = new ColaboradorMapperImpl();
    }
}
