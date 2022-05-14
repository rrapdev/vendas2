package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Venda.
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private Instant dataHora;

    @Column(name = "valor_total_bruto", precision = 21, scale = 2)
    private BigDecimal valorTotalBruto;

    @Column(name = "valor_total_desconto", precision = 21, scale = 2)
    private BigDecimal valorTotalDesconto;

    @Column(name = "valor_total_liquido", precision = 21, scale = 2)
    private BigDecimal valorTotalLiquido;

    @Column(name = "valor_total_pago", precision = 21, scale = 2)
    private BigDecimal valorTotalPago;

    @Column(name = "valor_saldo_restante", precision = 21, scale = 2)
    private BigDecimal valorSaldoRestante;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observarcoes")
    private String observarcoes;

    @Column(name = "indicador_possui_pagamento_pendente")
    private Boolean indicadorPossuiPagamentoPendente;

    @Column(name = "indicador_possui_item_presente")
    private Boolean indicadorPossuiItemPresente;

    @Column(name = "indicador_bloqueio")
    private Boolean indicadorBloqueio;

    @Column(name = "data_hora_cadastro")
    private Instant dataHoraCadastro;

    @Column(name = "colaborador_cadastro")
    private String colaboradorCadastro;

    @Column(name = "data_hora_atualizacao")
    private Instant dataHoraAtualizacao;

    @Column(name = "colaborador_atualizacao")
    private String colaboradorAtualizacao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "carteiraCliente" }, allowSetters = true)
    private Cliente clienteQueComprou;

    @ManyToMany
    @JoinTable(
        name = "rel_venda__colaboradores_que_indicaram",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "colaboradores_que_indicaram_id")
    )
    @JsonIgnoreProperties(value = { "vendas" }, allowSetters = true)
    private Set<Colaborador> colaboradoresQueIndicarams = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_venda__itens_venda",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "itens_venda_id")
    )
    @JsonIgnoreProperties(value = { "servico", "colaboradorQueIndicou", "clienteQueVaiRealizar", "vendas" }, allowSetters = true)
    private Set<ItemVenda> itensVendas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_venda__pagamentos",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "pagamentos_id")
    )
    @JsonIgnoreProperties(value = { "adquirentePagamento", "vendas" }, allowSetters = true)
    private Set<Pagamento> pagamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return this.dataHora;
    }

    public Venda dataHora(Instant dataHora) {
        this.setDataHora(dataHora);
        return this;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValorTotalBruto() {
        return this.valorTotalBruto;
    }

    public Venda valorTotalBruto(BigDecimal valorTotalBruto) {
        this.setValorTotalBruto(valorTotalBruto);
        return this;
    }

    public void setValorTotalBruto(BigDecimal valorTotalBruto) {
        this.valorTotalBruto = valorTotalBruto;
    }

    public BigDecimal getValorTotalDesconto() {
        return this.valorTotalDesconto;
    }

    public Venda valorTotalDesconto(BigDecimal valorTotalDesconto) {
        this.setValorTotalDesconto(valorTotalDesconto);
        return this;
    }

    public void setValorTotalDesconto(BigDecimal valorTotalDesconto) {
        this.valorTotalDesconto = valorTotalDesconto;
    }

    public BigDecimal getValorTotalLiquido() {
        return this.valorTotalLiquido;
    }

    public Venda valorTotalLiquido(BigDecimal valorTotalLiquido) {
        this.setValorTotalLiquido(valorTotalLiquido);
        return this;
    }

    public void setValorTotalLiquido(BigDecimal valorTotalLiquido) {
        this.valorTotalLiquido = valorTotalLiquido;
    }

    public BigDecimal getValorTotalPago() {
        return this.valorTotalPago;
    }

    public Venda valorTotalPago(BigDecimal valorTotalPago) {
        this.setValorTotalPago(valorTotalPago);
        return this;
    }

    public void setValorTotalPago(BigDecimal valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public BigDecimal getValorSaldoRestante() {
        return this.valorSaldoRestante;
    }

    public Venda valorSaldoRestante(BigDecimal valorSaldoRestante) {
        this.setValorSaldoRestante(valorSaldoRestante);
        return this;
    }

    public void setValorSaldoRestante(BigDecimal valorSaldoRestante) {
        this.valorSaldoRestante = valorSaldoRestante;
    }

    public String getObservarcoes() {
        return this.observarcoes;
    }

    public Venda observarcoes(String observarcoes) {
        this.setObservarcoes(observarcoes);
        return this;
    }

    public void setObservarcoes(String observarcoes) {
        this.observarcoes = observarcoes;
    }

    public Boolean getIndicadorPossuiPagamentoPendente() {
        return this.indicadorPossuiPagamentoPendente;
    }

    public Venda indicadorPossuiPagamentoPendente(Boolean indicadorPossuiPagamentoPendente) {
        this.setIndicadorPossuiPagamentoPendente(indicadorPossuiPagamentoPendente);
        return this;
    }

    public void setIndicadorPossuiPagamentoPendente(Boolean indicadorPossuiPagamentoPendente) {
        this.indicadorPossuiPagamentoPendente = indicadorPossuiPagamentoPendente;
    }

    public Boolean getIndicadorPossuiItemPresente() {
        return this.indicadorPossuiItemPresente;
    }

    public Venda indicadorPossuiItemPresente(Boolean indicadorPossuiItemPresente) {
        this.setIndicadorPossuiItemPresente(indicadorPossuiItemPresente);
        return this;
    }

    public void setIndicadorPossuiItemPresente(Boolean indicadorPossuiItemPresente) {
        this.indicadorPossuiItemPresente = indicadorPossuiItemPresente;
    }

    public Boolean getIndicadorBloqueio() {
        return this.indicadorBloqueio;
    }

    public Venda indicadorBloqueio(Boolean indicadorBloqueio) {
        this.setIndicadorBloqueio(indicadorBloqueio);
        return this;
    }

    public void setIndicadorBloqueio(Boolean indicadorBloqueio) {
        this.indicadorBloqueio = indicadorBloqueio;
    }

    public Instant getDataHoraCadastro() {
        return this.dataHoraCadastro;
    }

    public Venda dataHoraCadastro(Instant dataHoraCadastro) {
        this.setDataHoraCadastro(dataHoraCadastro);
        return this;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return this.colaboradorCadastro;
    }

    public Venda colaboradorCadastro(String colaboradorCadastro) {
        this.setColaboradorCadastro(colaboradorCadastro);
        return this;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return this.dataHoraAtualizacao;
    }

    public Venda dataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.setDataHoraAtualizacao(dataHoraAtualizacao);
        return this;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return this.colaboradorAtualizacao;
    }

    public Venda colaboradorAtualizacao(String colaboradorAtualizacao) {
        this.setColaboradorAtualizacao(colaboradorAtualizacao);
        return this;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public Cliente getClienteQueComprou() {
        return this.clienteQueComprou;
    }

    public void setClienteQueComprou(Cliente cliente) {
        this.clienteQueComprou = cliente;
    }

    public Venda clienteQueComprou(Cliente cliente) {
        this.setClienteQueComprou(cliente);
        return this;
    }

    public Set<Colaborador> getColaboradoresQueIndicarams() {
        return this.colaboradoresQueIndicarams;
    }

    public void setColaboradoresQueIndicarams(Set<Colaborador> colaboradors) {
        this.colaboradoresQueIndicarams = colaboradors;
    }

    public Venda colaboradoresQueIndicarams(Set<Colaborador> colaboradors) {
        this.setColaboradoresQueIndicarams(colaboradors);
        return this;
    }

    public Venda addColaboradoresQueIndicaram(Colaborador colaborador) {
        this.colaboradoresQueIndicarams.add(colaborador);
        colaborador.getVendas().add(this);
        return this;
    }

    public Venda removeColaboradoresQueIndicaram(Colaborador colaborador) {
        this.colaboradoresQueIndicarams.remove(colaborador);
        colaborador.getVendas().remove(this);
        return this;
    }

    public Set<ItemVenda> getItensVendas() {
        return this.itensVendas;
    }

    public void setItensVendas(Set<ItemVenda> itemVendas) {
        this.itensVendas = itemVendas;
    }

    public Venda itensVendas(Set<ItemVenda> itemVendas) {
        this.setItensVendas(itemVendas);
        return this;
    }

    public Venda addItensVenda(ItemVenda itemVenda) {
        this.itensVendas.add(itemVenda);
        itemVenda.getVendas().add(this);
        return this;
    }

    public Venda removeItensVenda(ItemVenda itemVenda) {
        this.itensVendas.remove(itemVenda);
        itemVenda.getVendas().remove(this);
        return this;
    }

    public Set<Pagamento> getPagamentos() {
        return this.pagamentos;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Venda pagamentos(Set<Pagamento> pagamentos) {
        this.setPagamentos(pagamentos);
        return this;
    }

    public Venda addPagamentos(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.getVendas().add(this);
        return this;
    }

    public Venda removePagamentos(Pagamento pagamento) {
        this.pagamentos.remove(pagamento);
        pagamento.getVendas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venda)) {
            return false;
        }
        return id != null && id.equals(((Venda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venda{" +
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
            "}";
    }
}
