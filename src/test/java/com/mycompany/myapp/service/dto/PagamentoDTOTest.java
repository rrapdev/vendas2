package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagamentoDTO.class);
        PagamentoDTO pagamentoDTO1 = new PagamentoDTO();
        pagamentoDTO1.setId(1L);
        PagamentoDTO pagamentoDTO2 = new PagamentoDTO();
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
        pagamentoDTO2.setId(pagamentoDTO1.getId());
        assertThat(pagamentoDTO1).isEqualTo(pagamentoDTO2);
        pagamentoDTO2.setId(2L);
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
        pagamentoDTO1.setId(null);
        assertThat(pagamentoDTO1).isNotEqualTo(pagamentoDTO2);
    }
}
