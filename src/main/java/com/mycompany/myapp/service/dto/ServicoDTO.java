package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Servico} entity.
 */
public class ServicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeServico;

    private BigDecimal valor;

    private Boolean indicadorAtivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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
        if (!(o instanceof ServicoDTO)) {
            return false;
        }

        ServicoDTO servicoDTO = (ServicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoDTO{" +
            "id=" + getId() +
            ", nomeServico='" + getNomeServico() + "'" +
            ", valor=" + getValor() +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
