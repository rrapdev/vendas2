package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlataformaPagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlataformaPagamento.class);
        PlataformaPagamento plataformaPagamento1 = new PlataformaPagamento();
        plataformaPagamento1.setId(1L);
        PlataformaPagamento plataformaPagamento2 = new PlataformaPagamento();
        plataformaPagamento2.setId(plataformaPagamento1.getId());
        assertThat(plataformaPagamento1).isEqualTo(plataformaPagamento2);
        plataformaPagamento2.setId(2L);
        assertThat(plataformaPagamento1).isNotEqualTo(plataformaPagamento2);
        plataformaPagamento1.setId(null);
        assertThat(plataformaPagamento1).isNotEqualTo(plataformaPagamento2);
    }
}
