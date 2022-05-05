package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PlataformaPagamento} entity.
 */
public class PlataformaPagamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomePlataformaPagamento;

    private Boolean indicadorAtivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePlataformaPagamento() {
        return nomePlataformaPagamento;
    }

    public void setNomePlataformaPagamento(String nomePlataformaPagamento) {
        this.nomePlataformaPagamento = nomePlataformaPagamento;
    }

    public Boolean getIndicadorAtivo() {
        return indicadorAtivo;
    }

    public void setIndicadorAtivo(Boolean indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlataformaPagamentoDTO)) {
            return false;
        }

        PlataformaPagamentoDTO plataformaPagamentoDTO = (PlataformaPagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plataformaPagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlataformaPagamentoDTO{" +
            "id=" + getId() +
            ", nomePlataformaPagamento='" + getNomePlataformaPagamento() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
