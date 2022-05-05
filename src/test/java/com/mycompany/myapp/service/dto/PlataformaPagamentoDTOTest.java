package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlataformaPagamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlataformaPagamentoDTO.class);
        PlataformaPagamentoDTO plataformaPagamentoDTO1 = new PlataformaPagamentoDTO();
        plataformaPagamentoDTO1.setId(1L);
        PlataformaPagamentoDTO plataformaPagamentoDTO2 = new PlataformaPagamentoDTO();
        assertThat(plataformaPagamentoDTO1).isNotEqualTo(plataformaPagamentoDTO2);
        plataformaPagamentoDTO2.setId(plataformaPagamentoDTO1.getId());
        assertThat(plataformaPagamentoDTO1).isEqualTo(plataformaPagamentoDTO2);
        plataformaPagamentoDTO2.setId(2L);
        assertThat(plataformaPagamentoDTO1).isNotEqualTo(plataformaPagamentoDTO2);
        plataformaPagamentoDTO1.setId(null);
        assertThat(plataformaPagamentoDTO1).isNotEqualTo(plataformaPagamentoDTO2);
    }
}
