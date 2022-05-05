package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemVendaMapperTest {

    private ItemVendaMapper itemVendaMapper;

    @BeforeEach
    public void setUp() {
        itemVendaMapper = new ItemVendaMapperImpl();
    }
}
