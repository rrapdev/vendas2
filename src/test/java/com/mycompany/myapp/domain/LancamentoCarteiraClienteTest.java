package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LancamentoCarteiraClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoCarteiraCliente.class);
        LancamentoCarteiraCliente lancamentoCarteiraCliente1 = new LancamentoCarteiraCliente();
        lancamentoCarteiraCliente1.setId(1L);
        LancamentoCarteiraCliente lancamentoCarteiraCliente2 = new LancamentoCarteiraCliente();
        lancamentoCarteiraCliente2.setId(lancamentoCarteiraCliente1.getId());
        assertThat(lancamentoCarteiraCliente1).isEqualTo(lancamentoCarteiraCliente2);
        lancamentoCarteiraCliente2.setId(2L);
        assertThat(lancamentoCarteiraCliente1).isNotEqualTo(lancamentoCarteiraCliente2);
        lancamentoCarteiraCliente1.setId(null);
        assertThat(lancamentoCarteiraCliente1).isNotEqualTo(lancamentoCarteiraCliente2);
    }
}
