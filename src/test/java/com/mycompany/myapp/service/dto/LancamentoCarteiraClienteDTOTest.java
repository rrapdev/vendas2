package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LancamentoCarteiraClienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoCarteiraClienteDTO.class);
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO1 = new LancamentoCarteiraClienteDTO();
        lancamentoCarteiraClienteDTO1.setId(1L);
        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO2 = new LancamentoCarteiraClienteDTO();
        assertThat(lancamentoCarteiraClienteDTO1).isNotEqualTo(lancamentoCarteiraClienteDTO2);
        lancamentoCarteiraClienteDTO2.setId(lancamentoCarteiraClienteDTO1.getId());
        assertThat(lancamentoCarteiraClienteDTO1).isEqualTo(lancamentoCarteiraClienteDTO2);
        lancamentoCarteiraClienteDTO2.setId(2L);
        assertThat(lancamentoCarteiraClienteDTO1).isNotEqualTo(lancamentoCarteiraClienteDTO2);
        lancamentoCarteiraClienteDTO1.setId(null);
        assertThat(lancamentoCarteiraClienteDTO1).isNotEqualTo(lancamentoCarteiraClienteDTO2);
    }
}
