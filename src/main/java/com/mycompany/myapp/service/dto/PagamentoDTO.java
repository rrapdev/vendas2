package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.BandeiraCartao;
import com.mycompany.myapp.domain.enumeration.FormaPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Pagamento} entity.
 */
public class PagamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    private Instant dataHora;

    @NotNull
    private BigDecimal valor;

    private String observacoes;

    @Min(value = 1)
    private Integer numeroParcelas;

    private BandeiraCartao bandeiraCartao;

    private String clienteOrigemPagamento;

    private Boolean indicadorConferido;

    private Instant dataHoraCadastro;

    private String colaboradorCadastro;

    private Instant dataHoraAtualizacao;

    private String colaboradorAtualizacao;

    private PlataformaPagamentoDTO plataformaPagamento;

    private LancamentoCarteiraClienteDTO lancamentoCarteiraCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public BandeiraCartao getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getClienteOrigemPagamento() {
        return clienteOrigemPagamento;
    }

    public void setClienteOrigemPagamento(String clienteOrigemPagamento) {
        this.clienteOrigemPagamento = clienteOrigemPagamento;
    }

    public Boolean getIndicadorConferido() {
        return indicadorConferido;
    }

    public void setIndicadorConferido(Boolean indicadorConferido) {
        this.indicadorConferido = indicadorConferido;
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

    public PlataformaPagamentoDTO getPlataformaPagamento() {
        return plataformaPagamento;
    }

    public void setPlataformaPagamento(PlataformaPagamentoDTO plataformaPagamento) {
        this.plataformaPagamento = plataformaPagamento;
    }

    public LancamentoCarteiraClienteDTO getLancamentoCarteiraCliente() {
        return lancamentoCarteiraCliente;
    }

    public void setLancamentoCarteiraCliente(LancamentoCarteiraClienteDTO lancamentoCarteiraCliente) {
        this.lancamentoCarteiraCliente = lancamentoCarteiraCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagamentoDTO)) {
            return false;
        }

        PagamentoDTO pagamentoDTO = (PagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagamentoDTO{" +
            "id=" + getId() +
            ", formaPagamento='" + getFormaPagamento() + "'" +
            ", dataHora='" + getDataHora() + "'" +
            ", valor=" + getValor() +
            ", observacoes='" + getObservacoes() + "'" +
            ", numeroParcelas=" + getNumeroParcelas() +
            ", bandeiraCartao='" + getBandeiraCartao() + "'" +
            ", clienteOrigemPagamento='" + getClienteOrigemPagamento() + "'" +
            ", indicadorConferido='" + getIndicadorConferido() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            ", plataformaPagamento=" + getPlataformaPagamento() +
            ", lancamentoCarteiraCliente=" + getLancamentoCarteiraCliente() +
            "}";
    }
}
