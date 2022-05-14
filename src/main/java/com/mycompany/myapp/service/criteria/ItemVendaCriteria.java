package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.ItemVenda} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ItemVendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ItemVendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataHora;

    private IntegerFilter quantidade;

    private BigDecimalFilter valorUnitario;

    private BigDecimalFilter valorTotal;

    private BigDecimalFilter valorDescontoPercentual;

    private BigDecimalFilter valorDescontoReal;

    private BigDecimalFilter valorTotalComDesconto;

    private BooleanFilter indicadorItemPresente;

    private InstantFilter dataHoraCadastro;

    private StringFilter colaboradorCadastro;

    private InstantFilter dataHoraAtualizacao;

    private StringFilter colaboradorAtualizacao;

    private LongFilter servicoId;

    private LongFilter colaboradorQueIndicouId;

    private LongFilter clienteQueVaiRealizarId;

    private LongFilter vendasId;

    private Boolean distinct;

    public ItemVendaCriteria() {}

    public ItemVendaCriteria(ItemVendaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dataHora = other.dataHora == null ? null : other.dataHora.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.valorUnitario = other.valorUnitario == null ? null : other.valorUnitario.copy();
        this.valorTotal = other.valorTotal == null ? null : other.valorTotal.copy();
        this.valorDescontoPercentual = other.valorDescontoPercentual == null ? null : other.valorDescontoPercentual.copy();
        this.valorDescontoReal = other.valorDescontoReal == null ? null : other.valorDescontoReal.copy();
        this.valorTotalComDesconto = other.valorTotalComDesconto == null ? null : other.valorTotalComDesconto.copy();
        this.indicadorItemPresente = other.indicadorItemPresente == null ? null : other.indicadorItemPresente.copy();
        this.dataHoraCadastro = other.dataHoraCadastro == null ? null : other.dataHoraCadastro.copy();
        this.colaboradorCadastro = other.colaboradorCadastro == null ? null : other.colaboradorCadastro.copy();
        this.dataHoraAtualizacao = other.dataHoraAtualizacao == null ? null : other.dataHoraAtualizacao.copy();
        this.colaboradorAtualizacao = other.colaboradorAtualizacao == null ? null : other.colaboradorAtualizacao.copy();
        this.servicoId = other.servicoId == null ? null : other.servicoId.copy();
        this.colaboradorQueIndicouId = other.colaboradorQueIndicouId == null ? null : other.colaboradorQueIndicouId.copy();
        this.clienteQueVaiRealizarId = other.clienteQueVaiRealizarId == null ? null : other.clienteQueVaiRealizarId.copy();
        this.vendasId = other.vendasId == null ? null : other.vendasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ItemVendaCriteria copy() {
        return new ItemVendaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDataHora() {
        return dataHora;
    }

    public InstantFilter dataHora() {
        if (dataHora == null) {
            dataHora = new InstantFilter();
        }
        return dataHora;
    }

    public void setDataHora(InstantFilter dataHora) {
        this.dataHora = dataHora;
    }

    public IntegerFilter getQuantidade() {
        return quantidade;
    }

    public IntegerFilter quantidade() {
        if (quantidade == null) {
            quantidade = new IntegerFilter();
        }
        return quantidade;
    }

    public void setQuantidade(IntegerFilter quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimalFilter getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimalFilter valorUnitario() {
        if (valorUnitario == null) {
            valorUnitario = new BigDecimalFilter();
        }
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimalFilter valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimalFilter getValorTotal() {
        return valorTotal;
    }

    public BigDecimalFilter valorTotal() {
        if (valorTotal == null) {
            valorTotal = new BigDecimalFilter();
        }
        return valorTotal;
    }

    public void setValorTotal(BigDecimalFilter valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimalFilter getValorDescontoPercentual() {
        return valorDescontoPercentual;
    }

    public BigDecimalFilter valorDescontoPercentual() {
        if (valorDescontoPercentual == null) {
            valorDescontoPercentual = new BigDecimalFilter();
        }
        return valorDescontoPercentual;
    }

    public void setValorDescontoPercentual(BigDecimalFilter valorDescontoPercentual) {
        this.valorDescontoPercentual = valorDescontoPercentual;
    }

    public BigDecimalFilter getValorDescontoReal() {
        return valorDescontoReal;
    }

    public BigDecimalFilter valorDescontoReal() {
        if (valorDescontoReal == null) {
            valorDescontoReal = new BigDecimalFilter();
        }
        return valorDescontoReal;
    }

    public void setValorDescontoReal(BigDecimalFilter valorDescontoReal) {
        this.valorDescontoReal = valorDescontoReal;
    }

    public BigDecimalFilter getValorTotalComDesconto() {
        return valorTotalComDesconto;
    }

    public BigDecimalFilter valorTotalComDesconto() {
        if (valorTotalComDesconto == null) {
            valorTotalComDesconto = new BigDecimalFilter();
        }
        return valorTotalComDesconto;
    }

    public void setValorTotalComDesconto(BigDecimalFilter valorTotalComDesconto) {
        this.valorTotalComDesconto = valorTotalComDesconto;
    }

    public BooleanFilter getIndicadorItemPresente() {
        return indicadorItemPresente;
    }

    public BooleanFilter indicadorItemPresente() {
        if (indicadorItemPresente == null) {
            indicadorItemPresente = new BooleanFilter();
        }
        return indicadorItemPresente;
    }

    public void setIndicadorItemPresente(BooleanFilter indicadorItemPresente) {
        this.indicadorItemPresente = indicadorItemPresente;
    }

    public InstantFilter getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public InstantFilter dataHoraCadastro() {
        if (dataHoraCadastro == null) {
            dataHoraCadastro = new InstantFilter();
        }
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(InstantFilter dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public StringFilter getColaboradorCadastro() {
        return colaboradorCadastro;
    }

    public StringFilter colaboradorCadastro() {
        if (colaboradorCadastro == null) {
            colaboradorCadastro = new StringFilter();
        }
        return colaboradorCadastro;
    }

    public void setColaboradorCadastro(StringFilter colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public InstantFilter getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public InstantFilter dataHoraAtualizacao() {
        if (dataHoraAtualizacao == null) {
            dataHoraAtualizacao = new InstantFilter();
        }
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(InstantFilter dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public StringFilter getColaboradorAtualizacao() {
        return colaboradorAtualizacao;
    }

    public StringFilter colaboradorAtualizacao() {
        if (colaboradorAtualizacao == null) {
            colaboradorAtualizacao = new StringFilter();
        }
        return colaboradorAtualizacao;
    }

    public void setColaboradorAtualizacao(StringFilter colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public LongFilter getServicoId() {
        return servicoId;
    }

    public LongFilter servicoId() {
        if (servicoId == null) {
            servicoId = new LongFilter();
        }
        return servicoId;
    }

    public void setServicoId(LongFilter servicoId) {
        this.servicoId = servicoId;
    }

    public LongFilter getColaboradorQueIndicouId() {
        return colaboradorQueIndicouId;
    }

    public LongFilter colaboradorQueIndicouId() {
        if (colaboradorQueIndicouId == null) {
            colaboradorQueIndicouId = new LongFilter();
        }
        return colaboradorQueIndicouId;
    }

    public void setColaboradorQueIndicouId(LongFilter colaboradorQueIndicouId) {
        this.colaboradorQueIndicouId = colaboradorQueIndicouId;
    }

    public LongFilter getClienteQueVaiRealizarId() {
        return clienteQueVaiRealizarId;
    }

    public LongFilter clienteQueVaiRealizarId() {
        if (clienteQueVaiRealizarId == null) {
            clienteQueVaiRealizarId = new LongFilter();
        }
        return clienteQueVaiRealizarId;
    }

    public void setClienteQueVaiRealizarId(LongFilter clienteQueVaiRealizarId) {
        this.clienteQueVaiRealizarId = clienteQueVaiRealizarId;
    }

    public LongFilter getVendasId() {
        return vendasId;
    }

    public LongFilter vendasId() {
        if (vendasId == null) {
            vendasId = new LongFilter();
        }
        return vendasId;
    }

    public void setVendasId(LongFilter vendasId) {
        this.vendasId = vendasId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemVendaCriteria that = (ItemVendaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataHora, that.dataHora) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(valorUnitario, that.valorUnitario) &&
            Objects.equals(valorTotal, that.valorTotal) &&
            Objects.equals(valorDescontoPercentual, that.valorDescontoPercentual) &&
            Objects.equals(valorDescontoReal, that.valorDescontoReal) &&
            Objects.equals(valorTotalComDesconto, that.valorTotalComDesconto) &&
            Objects.equals(indicadorItemPresente, that.indicadorItemPresente) &&
            Objects.equals(dataHoraCadastro, that.dataHoraCadastro) &&
            Objects.equals(colaboradorCadastro, that.colaboradorCadastro) &&
            Objects.equals(dataHoraAtualizacao, that.dataHoraAtualizacao) &&
            Objects.equals(colaboradorAtualizacao, that.colaboradorAtualizacao) &&
            Objects.equals(servicoId, that.servicoId) &&
            Objects.equals(colaboradorQueIndicouId, that.colaboradorQueIndicouId) &&
            Objects.equals(clienteQueVaiRealizarId, that.clienteQueVaiRealizarId) &&
            Objects.equals(vendasId, that.vendasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dataHora,
            quantidade,
            valorUnitario,
            valorTotal,
            valorDescontoPercentual,
            valorDescontoReal,
            valorTotalComDesconto,
            indicadorItemPresente,
            dataHoraCadastro,
            colaboradorCadastro,
            dataHoraAtualizacao,
            colaboradorAtualizacao,
            servicoId,
            colaboradorQueIndicouId,
            clienteQueVaiRealizarId,
            vendasId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemVendaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dataHora != null ? "dataHora=" + dataHora + ", " : "") +
            (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
            (valorUnitario != null ? "valorUnitario=" + valorUnitario + ", " : "") +
            (valorTotal != null ? "valorTotal=" + valorTotal + ", " : "") +
            (valorDescontoPercentual != null ? "valorDescontoPercentual=" + valorDescontoPercentual + ", " : "") +
            (valorDescontoReal != null ? "valorDescontoReal=" + valorDescontoReal + ", " : "") +
            (valorTotalComDesconto != null ? "valorTotalComDesconto=" + valorTotalComDesconto + ", " : "") +
            (indicadorItemPresente != null ? "indicadorItemPresente=" + indicadorItemPresente + ", " : "") +
            (dataHoraCadastro != null ? "dataHoraCadastro=" + dataHoraCadastro + ", " : "") +
            (colaboradorCadastro != null ? "colaboradorCadastro=" + colaboradorCadastro + ", " : "") +
            (dataHoraAtualizacao != null ? "dataHoraAtualizacao=" + dataHoraAtualizacao + ", " : "") +
            (colaboradorAtualizacao != null ? "colaboradorAtualizacao=" + colaboradorAtualizacao + ", " : "") +
            (servicoId != null ? "servicoId=" + servicoId + ", " : "") +
            (colaboradorQueIndicouId != null ? "colaboradorQueIndicouId=" + colaboradorQueIndicouId + ", " : "") +
            (clienteQueVaiRealizarId != null ? "clienteQueVaiRealizarId=" + clienteQueVaiRealizarId + ", " : "") +
            (vendasId != null ? "vendasId=" + vendasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
