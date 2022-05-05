package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarteiraClienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteiraClienteDTO.class);
        CarteiraClienteDTO carteiraClienteDTO1 = new CarteiraClienteDTO();
        carteiraClienteDTO1.setId(1L);
        CarteiraClienteDTO carteiraClienteDTO2 = new CarteiraClienteDTO();
        assertThat(carteiraClienteDTO1).isNotEqualTo(carteiraClienteDTO2);
        carteiraClienteDTO2.setId(carteiraClienteDTO1.getId());
        assertThat(carteiraClienteDTO1).isEqualTo(carteiraClienteDTO2);
        carteiraClienteDTO2.setId(2L);
        assertThat(carteiraClienteDTO1).isNotEqualTo(carteiraClienteDTO2);
        carteiraClienteDTO1.setId(null);
        assertThat(carteiraClienteDTO1).isNotEqualTo(carteiraClienteDTO2);
    }
}
