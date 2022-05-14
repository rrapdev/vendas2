package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Venda} entity.
 */
public class VendaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataHora;

    private BigDecimal valorTotalBruto;

    private BigDecimal valorTotalDesconto;

    private BigDecimal valorTotalLiquido;

    private BigDecimal valorTotalPago;

    private BigDecimal valorSaldoRestante;

    @Lob
    private String observarcoes;

    private Boolean indicadorPossuiPagamentoPendente;

    private Boolean indicadorPossuiItemPresente;

    private Boolean indicadorBloqueio;

    private Instant dataHoraCadastro;

    private String colaboradorCadastro;

    private Instant dataHoraAtualizacao;

    private String colaboradorAtualizacao;

    private ClienteDTO clienteQueComprou;

    private Set<ColaboradorDTO> colaboradoresQueIndicarams = new HashSet<>();

    private Set<ItemVendaDTO> itensVendas = new HashSet<>();

    private Set<PagamentoDTO> pagamentos = new HashSet<>();

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

    public BigDecimal getValorTotalBruto() {
        return valorTotalBruto;
    }

    public void setValorTotalBruto(BigDecimal valorTotalBruto) {
        this.valorTotalBruto = valorTotalBruto;
    }

    public BigDecimal getValorTotalDesconto() {
        return valorTotalDesconto;
    }

    public void setValorTotalDesconto(BigDecimal valorTotalDesconto) {
        this.valorTotalDesconto = valorTotalDesconto;
    }

    public BigDecimal getValorTotalLiquido() {
        return valorTotalLiquido;
    }

    public void setValorTotalLiquido(BigDecimal valorTotalLiquido) {
        this.valorTotalLiquido = valorTotalLiquido;
    }

    public BigDecimal getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(BigDecimal valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public BigDecimal getValorSaldoRestante() {
        return valorSaldoRestante;
    }

    public void setValorSaldoRestante(BigDecimal valorSaldoRestante) {
        this.valorSaldoRestante = valorSaldoRestante;
    }

    public String getObservarcoes() {
        return observarcoes;
    }

    public void setObservarcoes(String observarcoes) {
        this.observarcoes = observarcoes;
    }

    public Boolean getIndicadorPossuiPagamentoPendente() {
        return indicadorPossuiPagamentoPendente;
    }

    public void setIndicadorPossuiPagamentoPendente(Boolean indicadorPossuiPagamentoPendente) {
        this.indicadorPossuiPagamentoPendente = indicadorPossuiPagamentoPendente;
    }

    public Boolean getIndicadorPossuiItemPresente() {
        return indicadorPossuiItemPresente;
    }

    public void setIndicadorPossuiItemPresente(Boolean indicadorPossuiItemPresente) {
        this.indicadorPossuiItemPresente = indicadorPossuiItemPresente;
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

    public ClienteDTO getClienteQueComprou() {
        return clienteQueComprou;
    }

    public void setClienteQueComprou(ClienteDTO clienteQueComprou) {
        this.clienteQueComprou = clienteQueComprou;
    }

    public Set<ColaboradorDTO> getColaboradoresQueIndicarams() {
        return colaboradoresQueIndicarams;
    }

    public void setColaboradoresQueIndicarams(Set<ColaboradorDTO> colaboradoresQueIndicarams) {
        this.colaboradoresQueIndicarams = colaboradoresQueIndicarams;
    }

    public Set<ItemVendaDTO> getItensVendas() {
        return itensVendas;
    }

    public void setItensVendas(Set<ItemVendaDTO> itensVendas) {
        this.itensVendas = itensVendas;
    }

    public Set<PagamentoDTO> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Set<PagamentoDTO> pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendaDTO)) {
            return false;
        }

        VendaDTO vendaDTO = (VendaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vendaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendaDTO{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", valorTotalBruto=" + getValorTotalBruto() +
            ", valorTotalDesconto=" + getValorTotalDesconto() +
            ", valorTotalLiquido=" + getValorTotalLiquido() +
            ", valorTotalPago=" + getValorTotalPago() +
            ", valorSaldoRestante=" + getValorSaldoRestante() +
            ", observarcoes='" + getObservarcoes() + "'" +
            ", indicadorPossuiPagamentoPendente='" + getIndicadorPossuiPagamentoPendente() + "'" +
            ", indicadorPossuiItemPresente='" + getIndicadorPossuiItemPresente() + "'" +
            ", indicadorBloqueio='" + getIndicadorBloqueio() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            ", clienteQueComprou=" + getClienteQueComprou() +
            ", colaboradoresQueIndicarams=" + getColaboradoresQueIndicarams() +
            ", itensVendas=" + getItensVendas() +
            ", pagamentos=" + getPagamentos() +
            "}";
    }
}
