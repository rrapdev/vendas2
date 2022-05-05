package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemVendaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemVendaDTO.class);
        ItemVendaDTO itemVendaDTO1 = new ItemVendaDTO();
        itemVendaDTO1.setId(1L);
        ItemVendaDTO itemVendaDTO2 = new ItemVendaDTO();
        assertThat(itemVendaDTO1).isNotEqualTo(itemVendaDTO2);
        itemVendaDTO2.setId(itemVendaDTO1.getId());
        assertThat(itemVendaDTO1).isEqualTo(itemVendaDTO2);
        itemVendaDTO2.setId(2L);
        assertThat(itemVendaDTO1).isNotEqualTo(itemVendaDTO2);
        itemVendaDTO1.setId(null);
        assertThat(itemVendaDTO1).isNotEqualTo(itemVendaDTO2);
    }
}
