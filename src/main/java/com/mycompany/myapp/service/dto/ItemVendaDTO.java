package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ItemVenda} entity.
 */
public class ItemVendaDTO implements Serializable {

    private Long id;

    private Instant dataHora;

    @NotNull
    private Integer quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private BigDecimal valorDescontoPercentual;

    private BigDecimal valorDescontoReal;

    private BigDecimal valorTotalComDesconto;

    private Boolean indicadorItemPresente;

    private Instant dataHoraCadastro;

    private String colaboradorCadastro;

    private Instant dataHoraAtualizacao;

    private String colaboradorAtualizacao;

    private ServicoDTO servico;

    private ColaboradorDTO colaboradorQueIndicou;

    private ClienteDTO clienteQueVaiRealizar;

    private VendaDTO venda;

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDescontoPercentual() {
        return valorDescontoPercentual;
    }

    public void setValorDescontoPercentual(BigDecimal valorDescontoPercentual) {
        this.valorDescontoPercentual = valorDescontoPercentual;
    }

    public BigDecimal getValorDescontoReal() {
        return valorDescontoReal;
    }

    public void setValorDescontoReal(BigDecimal valorDescontoReal) {
        this.valorDescontoReal = valorDescontoReal;
    }

    public BigDecimal getValorTotalComDesconto() {
        return valorTotalComDesconto;
    }

    public void setValorTotalComDesconto(BigDecimal valorTotalComDesconto) {
        this.valorTotalComDesconto = valorTotalComDesconto;
    }

    public Boolean getIndicadorItemPresente() {
        return indicadorItemPresente;
    }

    public void setIndicadorItemPresente(Boolean indicadorItemPresente) {
        this.indicadorItemPresente = indicadorItemPresente;
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

    public ServicoDTO getServico() {
        return servico;
    }

    public void setServico(ServicoDTO servico) {
        this.servico = servico;
    }

    public ColaboradorDTO getColaboradorQueIndicou() {
        return colaboradorQueIndicou;
    }

    public void setColaboradorQueIndicou(ColaboradorDTO colaboradorQueIndicou) {
        this.colaboradorQueIndicou = colaboradorQueIndicou;
    }

    public ClienteDTO getClienteQueVaiRealizar() {
        return clienteQueVaiRealizar;
    }

    public void setClienteQueVaiRealizar(ClienteDTO clienteQueVaiRealizar) {
        this.clienteQueVaiRealizar = clienteQueVaiRealizar;
    }

    public VendaDTO getVenda() {
        return venda;
    }

    public void setVenda(VendaDTO venda) {
        this.venda = venda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemVendaDTO)) {
            return false;
        }

        ItemVendaDTO itemVendaDTO = (ItemVendaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemVendaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemVendaDTO{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", quantidade=" + getQuantidade() +
            ", valorUnitario=" + getValorUnitario() +
            ", valorTotal=" + getValorTotal() +
            ", valorDescontoPercentual=" + getValorDescontoPercentual() +
            ", valorDescontoReal=" + getValorDescontoReal() +
            ", valorTotalComDesconto=" + getValorTotalComDesconto() +
            ", indicadorItemPresente='" + getIndicadorItemPresente() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            ", servico=" + getServico() +
            ", colaboradorQueIndicou=" + getColaboradorQueIndicou() +
            ", clienteQueVaiRealizar=" + getClienteQueVaiRealizar() +
            ", venda=" + getVenda() +
            "}";
    }
}
