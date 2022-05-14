package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.BandeiraCartao;
import com.mycompany.myapp.domain.enumeration.FormaPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private Instant dataHora;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "observacoes")
    private String observacoes;

    @Min(value = 1)
    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    @Enumerated(EnumType.STRING)
    @Column(name = "bandeira_cartao")
    private BandeiraCartao bandeiraCartao;

    @Column(name = "cliente_origem_pagamento")
    private String clienteOrigemPagamento;

    @Column(name = "indicador_conferido")
    private Boolean indicadorConferido;

    @Column(name = "data_hora_cadastro")
    private Instant dataHoraCadastro;

    @Column(name = "colaborador_cadastro")
    private String colaboradorCadastro;

    @Column(name = "data_hora_atualizacao")
    private Instant dataHoraAtualizacao;

    @Column(name = "colaborador_atualizacao")
    private String colaboradorAtualizacao;

    @ManyToOne
    private PlataformaPagamento adquirentePagamento;

    @ManyToMany(mappedBy = "pagamentos")
    @JsonIgnoreProperties(value = { "clienteQueComprou", "colaboradoresQueIndicarams", "itensVendas", "pagamentos" }, allowSetters = true)
    private Set<Venda> vendas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaPagamento getFormaPagamento() {
        return this.formaPagamento;
    }

    public Pagamento formaPagamento(FormaPagamento formaPagamento) {
        this.setFormaPagamento(formaPagamento);
        return this;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Instant getDataHora() {
        return this.dataHora;
    }

    public Pagamento dataHora(Instant dataHora) {
        this.setDataHora(dataHora);
        return this;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Pagamento valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Pagamento observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getNumeroParcelas() {
        return this.numeroParcelas;
    }

    public Pagamento numeroParcelas(Integer numeroParcelas) {
        this.setNumeroParcelas(numeroParcelas);
        return this;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public BandeiraCartao getBandeiraCartao() {
        return this.bandeiraCartao;
    }

    public Pagamento bandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.setBandeiraCartao(bandeiraCartao);
        return this;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getClienteOrigemPagamento() {
        return this.clienteOrigemPagamento;
    }

    public Pagamento clienteOrigemPagamento(String clienteOrigemPagamento) {
        this.setClienteOrigemPagamento(clienteOrigemPagamento);
        return this;
    }

    public void setClienteOrigemPagamento(String clienteOrigemPagamento) {
        this.clienteOrigemPagamento = clienteOrigemPagamento;
    }

    public Boolean getIndicadorConferido() {
        return this.indicadorConferido;
    }

    public Pagamento indicadorConferido(Boolean indicadorConferido) {
        this.setIndicadorConferido(indicadorConferido);
        return this;
    }

    public void setIndicadorConferido(Boolean indicadorConferido) {
        this.indicadorConferido = indicadorConferido;
    }

    public Instant getDataHoraCadastro() {
        return this.dataHoraCadastro;
    }

    public Pagamento dataHoraCadastro(Instant dataHoraCadastro) {
        this.setDataHoraCadastro(dataHoraCadastro);
        return this;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return this.colaboradorCadastro;
    }

    public Pagamento colaboradorCadastro(String colaboradorCadastro) {
        this.setColaboradorCadastro(colaboradorCadastro);
        return this;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return this.dataHoraAtualizacao;
    }

    public Pagamento dataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.setDataHoraAtualizacao(dataHoraAtualizacao);
        return this;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return this.colaboradorAtualizacao;
    }

    public Pagamento colaboradorAtualizacao(String colaboradorAtualizacao) {
        this.setColaboradorAtualizacao(colaboradorAtualizacao);
        return this;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public PlataformaPagamento getAdquirentePagamento() {
        return this.adquirentePagamento;
    }

    public void setAdquirentePagamento(PlataformaPagamento plataformaPagamento) {
        this.adquirentePagamento = plataformaPagamento;
    }

    public Pagamento adquirentePagamento(PlataformaPagamento plataformaPagamento) {
        this.setAdquirentePagamento(plataformaPagamento);
        return this;
    }

    public Set<Venda> getVendas() {
        return this.vendas;
    }

    public void setVendas(Set<Venda> vendas) {
        if (this.vendas != null) {
            this.vendas.forEach(i -> i.removePagamentos(this));
        }
        if (vendas != null) {
            vendas.forEach(i -> i.addPagamentos(this));
        }
        this.vendas = vendas;
    }

    public Pagamento vendas(Set<Venda> vendas) {
        this.setVendas(vendas);
        return this;
    }

    public Pagamento addVendas(Venda venda) {
        this.vendas.add(venda);
        venda.getPagamentos().add(this);
        return this;
    }

    public Pagamento removeVendas(Venda venda) {
        this.vendas.remove(venda);
        venda.getPagamentos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagamento)) {
            return false;
        }
        return id != null && id.equals(((Pagamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pagamento{" +
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
            "}";
    }
}
