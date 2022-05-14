package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeCompleto;

    private String telefone;

    private String nomeApresentacao;

    private Boolean indicadorAtivo;

    private CarteiraClienteDTO carteiraCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public CarteiraClienteDTO getCarteiraCliente() {
        return carteiraCliente;
    }

    public void setCarteiraCliente(CarteiraClienteDTO carteiraCliente) {
        this.carteiraCliente = carteiraCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", nomeApresentacao='" + getNomeApresentacao() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            ", carteiraCliente=" + getCarteiraCliente() +
            "}";
    }
}
