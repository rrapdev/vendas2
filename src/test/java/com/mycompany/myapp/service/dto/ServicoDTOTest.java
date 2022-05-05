package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoDTO.class);
        ServicoDTO servicoDTO1 = new ServicoDTO();
        servicoDTO1.setId(1L);
        ServicoDTO servicoDTO2 = new ServicoDTO();
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
        servicoDTO2.setId(servicoDTO1.getId());
        assertThat(servicoDTO1).isEqualTo(servicoDTO2);
        servicoDTO2.setId(2L);
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
        servicoDTO1.setId(null);
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
    }
}
