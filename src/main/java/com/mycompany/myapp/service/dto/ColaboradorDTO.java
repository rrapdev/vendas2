package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Colaborador} entity.
 */
public class ColaboradorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeColaborador;

    private String nomeApresentacao;

    private Boolean indicadorAtivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public String getNomeApresentacao() {
        return nomeApresentacao;
    }

    public void setNomeApresentacao(String nomeApresentacao) {
        this.nomeApresentacao = nomeApresentacao;
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
        if (!(o instanceof ColaboradorDTO)) {
            return false;
        }

        ColaboradorDTO colaboradorDTO = (ColaboradorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, colaboradorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColaboradorDTO{" +
            "id=" + getId() +
            ", nomeColaborador='" + getNomeColaborador() + "'" +
            ", nomeApresentacao='" + getNomeApresentacao() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
