package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.LancamentoCarteiraCliente} entity.
 */
public class LancamentoCarteiraClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataHora;

    @NotNull
    private String descricaoLancamento;

    private BigDecimal valorCredito;

    private BigDecimal valorDebito;

    private String observacoes;

    private Boolean indicadorBloqueio;

    private Instant dataHoraCadastro;

    private String colaboradorCadastro;

    private Instant dataHoraAtualizacao;

    private String colaboradorAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricaoLancamento() {
        return descricaoLancamento;
    }

    public void setDescricaoLancamento(String descricaoLancamento) {
        this.descricaoLancamento = descricaoLancamento;
    }

    public BigDecimal getValorCredito() {
        return valorCredito;
    }

    public void setValorCredito(BigDecimal valorCredito) {
        this.valorCredito = valorCredito;
    }

    public BigDecimal getValorDebito() {
        return valorDebito;
    }

    public void setValorDebito(BigDecimal valorDebito) {
        this.valorDebito = valorDebito;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LancamentoCarteiraClienteDTO)) {
            return false;
        }

        LancamentoCarteiraClienteDTO lancamentoCarteiraClienteDTO = (LancamentoCarteiraClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lancamentoCarteiraClienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LancamentoCarteiraClienteDTO{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", descricaoLancamento='" + getDescricaoLancamento() + "'" +
            ", valorCredito=" + getValorCredito() +
            ", valorDebito=" + getValorDebito() +
            ", observacoes='" + getObservacoes() + "'" +
            ", indicadorBloqueio='" + getIndicadorBloqueio() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            "}";
    }
}
