package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servico.class);
        Servico servico1 = new Servico();
        servico1.setId(1L);
        Servico servico2 = new Servico();
        servico2.setId(servico1.getId());
        assertThat(servico1).isEqualTo(servico2);
        servico2.setId(2L);
        assertThat(servico1).isNotEqualTo(servico2);
        servico1.setId(null);
        assertThat(servico1).isNotEqualTo(servico2);
    }
}
