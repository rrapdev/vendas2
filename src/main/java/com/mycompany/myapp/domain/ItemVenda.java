package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ItemVenda.
 */
@Entity
@Table(name = "item_venda")
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_hora")
    private Instant dataHora;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor_unitario", precision = 21, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "valor_desconto_percentual", precision = 21, scale = 2)
    private BigDecimal valorDescontoPercentual;

    @Column(name = "valor_desconto_real", precision = 21, scale = 2)
    private BigDecimal valorDescontoReal;

    @Column(name = "valor_total_com_desconto", precision = 21, scale = 2)
    private BigDecimal valorTotalComDesconto;

    @Column(name = "indicador_item_presente")
    private Boolean indicadorItemPresente;

    @Column(name = "data_hora_cadastro")
    private Instant dataHoraCadastro;

    @Column(name = "colaborador_cadastro")
    private String colaboradorCadastro;

    @Column(name = "data_hora_atualizacao")
    private Instant dataHoraAtualizacao;

    @Column(name = "colaborador_atualizacao")
    private String colaboradorAtualizacao;

    @ManyToOne
    private Servico servico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vendas" }, allowSetters = true)
    private Colaborador colaboradorQueIndicou;

    @ManyToOne
    @JsonIgnoreProperties(value = { "carteiraCliente" }, allowSetters = true)
    private Cliente clienteQueVaiRealizar;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "itenssVendasses", "clienteQueComprou", "lancamentoCarteiraCliente", "colaboradoresQueIndicarams", "itensVendas", "pagamentos",
        },
        allowSetters = true
    )
    private Venda venda;

    @ManyToMany(mappedBy = "itensVendas")
    @JsonIgnoreProperties(
        value = {
            "itenssVendasses", "clienteQueComprou", "lancamentoCarteiraCliente", "colaboradoresQueIndicarams", "itensVendas", "pagamentos",
        },
        allowSetters = true
    )
    private Set<Venda> vendas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemVenda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return this.dataHora;
    }

    public ItemVenda dataHora(Instant dataHora) {
        this.setDataHora(dataHora);
        return this;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public ItemVenda quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return this.valorUnitario;
    }

    public ItemVenda valorUnitario(BigDecimal valorUnitario) {
        this.setValorUnitario(valorUnitario);
        return this;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public ItemVenda valorTotal(BigDecimal valorTotal) {
        this.setValorTotal(valorTotal);
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDescontoPercentual() {
        return this.valorDescontoPercentual;
    }

    public ItemVenda valorDescontoPercentual(BigDecimal valorDescontoPercentual) {
        this.setValorDescontoPercentual(valorDescontoPercentual);
        return this;
    }

    public void setValorDescontoPercentual(BigDecimal valorDescontoPercentual) {
        this.valorDescontoPercentual = valorDescontoPercentual;
    }

    public BigDecimal getValorDescontoReal() {
        return this.valorDescontoReal;
    }

    public ItemVenda valorDescontoReal(BigDecimal valorDescontoReal) {
        this.setValorDescontoReal(valorDescontoReal);
        return this;
    }

    public void setValorDescontoReal(BigDecimal valorDescontoReal) {
        this.valorDescontoReal = valorDescontoReal;
    }

    public BigDecimal getValorTotalComDesconto() {
        return this.valorTotalComDesconto;
    }

    public ItemVenda valorTotalComDesconto(BigDecimal valorTotalComDesconto) {
        this.setValorTotalComDesconto(valorTotalComDesconto);
        return this;
    }

    public void setValorTotalComDesconto(BigDecimal valorTotalComDesconto) {
        this.valorTotalComDesconto = valorTotalComDesconto;
    }

    public Boolean getIndicadorItemPresente() {
        return this.indicadorItemPresente;
    }

    public ItemVenda indicadorItemPresente(Boolean indicadorItemPresente) {
        this.setIndicadorItemPresente(indicadorItemPresente);
        return this;
    }

    public void setIndicadorItemPresente(Boolean indicadorItemPresente) {
        this.indicadorItemPresente = indicadorItemPresente;
    }

    public Instant getDataHoraCadastro() {
        return this.dataHoraCadastro;
    }

    public ItemVenda dataHoraCadastro(Instant dataHoraCadastro) {
        this.setDataHoraCadastro(dataHoraCadastro);
        return this;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return this.colaboradorCadastro;
    }

    public ItemVenda colaboradorCadastro(String colaboradorCadastro) {
        this.setColaboradorCadastro(colaboradorCadastro);
        return this;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return this.dataHoraAtualizacao;
    }

    public ItemVenda dataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.setDataHoraAtualizacao(dataHoraAtualizacao);
        return this;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return this.colaboradorAtualizacao;
    }

    public ItemVenda colaboradorAtualizacao(String colaboradorAtualizacao) {
        this.setColaboradorAtualizacao(colaboradorAtualizacao);
        return this;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public Servico getServico() {
        return this.servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public ItemVenda servico(Servico servico) {
        this.setServico(servico);
        return this;
    }

    public Colaborador getColaboradorQueIndicou() {
        return this.colaboradorQueIndicou;
    }

    public void setColaboradorQueIndicou(Colaborador colaborador) {
        this.colaboradorQueIndicou = colaborador;
    }

    public ItemVenda colaboradorQueIndicou(Colaborador colaborador) {
        this.setColaboradorQueIndicou(colaborador);
        return this;
    }

    public Cliente getClienteQueVaiRealizar() {
        return this.clienteQueVaiRealizar;
    }

    public void setClienteQueVaiRealizar(Cliente cliente) {
        this.clienteQueVaiRealizar = cliente;
    }

    public ItemVenda clienteQueVaiRealizar(Cliente cliente) {
        this.setClienteQueVaiRealizar(cliente);
        return this;
    }

    public Venda getVenda() {
        return this.venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ItemVenda venda(Venda venda) {
        this.setVenda(venda);
        return this;
    }

    public Set<Venda> getVendas() {
        return this.vendas;
    }

    public void setVendas(Set<Venda> vendas) {
        if (this.vendas != null) {
            this.vendas.forEach(i -> i.removeItensVenda(this));
        }
        if (vendas != null) {
            vendas.forEach(i -> i.addItensVenda(this));
        }
        this.vendas = vendas;
    }

    public ItemVenda vendas(Set<Venda> vendas) {
        this.setVendas(vendas);
        return this;
    }

    public ItemVenda addVendas(Venda venda) {
        this.vendas.add(venda);
        venda.getItensVendas().add(this);
        return this;
    }

    public ItemVenda removeVendas(Venda venda) {
        this.vendas.remove(venda);
        venda.getItensVendas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemVenda)) {
            return false;
        }
        return id != null && id.equals(((ItemVenda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemVenda{" +
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
            "}";
    }
}
