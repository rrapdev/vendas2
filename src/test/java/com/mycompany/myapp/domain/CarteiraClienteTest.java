package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarteiraClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteiraCliente.class);
        CarteiraCliente carteiraCliente1 = new CarteiraCliente();
        carteiraCliente1.setId(1L);
        CarteiraCliente carteiraCliente2 = new CarteiraCliente();
        carteiraCliente2.setId(carteiraCliente1.getId());
        assertThat(carteiraCliente1).isEqualTo(carteiraCliente2);
        carteiraCliente2.setId(2L);
        assertThat(carteiraCliente1).isNotEqualTo(carteiraCliente2);
        carteiraCliente1.setId(null);
        assertThat(carteiraCliente1).isNotEqualTo(carteiraCliente2);
    }
}
