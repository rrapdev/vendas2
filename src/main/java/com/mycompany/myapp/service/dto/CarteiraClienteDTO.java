package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.TipoIndicadorSaldo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CarteiraCliente} entity.
 */
public class CarteiraClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal saldoConsolidado;

    private TipoIndicadorSaldo tipoIndicadorSaldo;

    private Boolean indicadorBloqueio;

    private Instant dataHoraCadastro;

    private String colaboradorCadastro;

    private Instant dataHoraAtualizacao;

    private String colaboradorAtualizacao;

    private Set<LancamentoCarteiraClienteDTO> lancamentoCarteiraClientes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldoConsolidado() {
        return saldoConsolidado;
    }

    public void setSaldoConsolidado(BigDecimal saldoConsolidado) {
        this.saldoConsolidado = saldoConsolidado;
    }

    public TipoIndicadorSaldo getTipoIndicadorSaldo() {
        return tipoIndicadorSaldo;
    }

    public void setTipoIndicadorSaldo(TipoIndicadorSaldo tipoIndicadorSaldo) {
        this.tipoIndicadorSaldo = tipoIndicadorSaldo;
    }

    public Boolean getIndicadorBloqueio() {
        return indicadorBloqueio;
    }

    public void setIndicadorBloqueio(Boolean indicadorBloqueio) {
        this.indicadorBloqueio = indicadorBloqueio;
    }

    public Instant getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return colaboradorCadastro;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return colaboradorAtualizacao;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public Set<LancamentoCarteiraClienteDTO> getLancamentoCarteiraClientes() {
        return lancamentoCarteiraClientes;
    }

    public void setLancamentoCarteiraClientes(Set<LancamentoCarteiraClienteDTO> lancamentoCarteiraClientes) {
        this.lancamentoCarteiraClientes = lancamentoCarteiraClientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteiraClienteDTO)) {
            return false;
        }

        CarteiraClienteDTO carteiraClienteDTO = (CarteiraClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carteiraClienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteiraClienteDTO{" +
            "id=" + getId() +
            ", saldoConsolidado=" + getSaldoConsolidado() +
            ", tipoIndicadorSaldo='" + getTipoIndicadorSaldo() + "'" +
            ", indicadorBloqueio='" + getIndicadorBloqueio() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            ", lancamentoCarteiraClientes=" + getLancamentoCarteiraClientes() +
            "}";
    }
}
