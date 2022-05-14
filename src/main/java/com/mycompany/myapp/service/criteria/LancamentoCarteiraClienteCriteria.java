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
 * Criteria class for the {@link com.mycompany.myapp.domain.LancamentoCarteiraCliente} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.LancamentoCarteiraClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lancamento-carteira-clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LancamentoCarteiraClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricaoLancamento;

    private InstantFilter dataHora;

    private BigDecimalFilter valorCredito;

    private BigDecimalFilter valorDebito;

    private StringFilter observacoes;

    private BooleanFilter indicadorBloqueio;

    private InstantFilter dataHoraCadastro;

    private StringFilter colaboradorCadastro;

    private InstantFilter dataHoraAtualizacao;

    private StringFilter colaboradorAtualizacao;

    private LongFilter vendaId;

    private LongFilter pagamentoId;

    private LongFilter carteirasClienteId;

    private Boolean distinct;

    public LancamentoCarteiraClienteCriteria() {}

    public LancamentoCarteiraClienteCriteria(LancamentoCarteiraClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricaoLancamento = other.descricaoLancamento == null ? null : other.descricaoLancamento.copy();
        this.dataHora = other.dataHora == null ? null : other.dataHora.copy();
        this.valorCredito = other.valorCredito == null ? null : other.valorCredito.copy();
        this.valorDebito = other.valorDebito == null ? null : other.valorDebito.copy();
        this.observacoes = other.observacoes == null ? null : other.observacoes.copy();
        this.indicadorBloqueio = other.indicadorBloqueio == null ? null : other.indicadorBloqueio.copy();
        this.dataHoraCadastro = other.dataHoraCadastro == null ? null : other.dataHoraCadastro.copy();
        this.colaboradorCadastro = other.colaboradorCadastro == null ? null : other.colaboradorCadastro.copy();
        this.dataHoraAtualizacao = other.dataHoraAtualizacao == null ? null : other.dataHoraAtualizacao.copy();
        this.colaboradorAtualizacao = other.colaboradorAtualizacao == null ? null : other.colaboradorAtualizacao.copy();
        this.vendaId = other.vendaId == null ? null : other.vendaId.copy();
        this.pagamentoId = other.pagamentoId == null ? null : other.pagamentoId.copy();
        this.carteirasClienteId = other.carteirasClienteId == null ? null : other.carteirasClienteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LancamentoCarteiraClienteCriteria copy() {
        return new LancamentoCarteiraClienteCriteria(this);
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

    public StringFilter getDescricaoLancamento() {
        return descricaoLancamento;
    }

    public StringFilter descricaoLancamento() {
        if (descricaoLancamento == null) {
            descricaoLancamento = new StringFilter();
        }
        return descricaoLancamento;
    }

    public void setDescricaoLancamento(StringFilter descricaoLancamento) {
        this.descricaoLancamento = descricaoLancamento;
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

    public BigDecimalFilter getValorCredito() {
        return valorCredito;
    }

    public BigDecimalFilter valorCredito() {
        if (valorCredito == null) {
            valorCredito = new BigDecimalFilter();
        }
        return valorCredito;
    }

    public void setValorCredito(BigDecimalFilter valorCredito) {
        this.valorCredito = valorCredito;
    }

    public BigDecimalFilter getValorDebito() {
        return valorDebito;
    }

    public BigDecimalFilter valorDebito() {
        if (valorDebito == null) {
            valorDebito = new BigDecimalFilter();
        }
        return valorDebito;
    }

    public void setValorDebito(BigDecimalFilter valorDebito) {
        this.valorDebito = valorDebito;
    }

    public StringFilter getObservacoes() {
        return observacoes;
    }

    public StringFilter observacoes() {
        if (observacoes == null) {
            observacoes = new StringFilter();
        }
        return observacoes;
    }

    public void setObservacoes(StringFilter observacoes) {
        this.observacoes = observacoes;
    }

    public BooleanFilter getIndicadorBloqueio() {
        return indicadorBloqueio;
    }

    public BooleanFilter indicadorBloqueio() {
        if (indicadorBloqueio == null) {
            indicadorBloqueio = new BooleanFilter();
        }
        return indicadorBloqueio;
    }

    public void setIndicadorBloqueio(BooleanFilter indicadorBloqueio) {
        this.indicadorBloqueio = indicadorBloqueio;
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

    public LongFilter getVendaId() {
        return vendaId;
    }

    public LongFilter vendaId() {
        if (vendaId == null) {
            vendaId = new LongFilter();
        }
        return vendaId;
    }

    public void setVendaId(LongFilter vendaId) {
        this.vendaId = vendaId;
    }

    public LongFilter getPagamentoId() {
        return pagamentoId;
    }

    public LongFilter pagamentoId() {
        if (pagamentoId == null) {
            pagamentoId = new LongFilter();
        }
        return pagamentoId;
    }

    public void setPagamentoId(LongFilter pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public LongFilter getCarteirasClienteId() {
        return carteirasClienteId;
    }

    public LongFilter carteirasClienteId() {
        if (carteirasClienteId == null) {
            carteirasClienteId = new LongFilter();
        }
        return carteirasClienteId;
    }

    public void setCarteirasClienteId(LongFilter carteirasClienteId) {
        this.carteirasClienteId = carteirasClienteId;
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
        final LancamentoCarteiraClienteCriteria that = (LancamentoCarteiraClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricaoLancamento, that.descricaoLancamento) &&
            Objects.equals(dataHora, that.dataHora) &&
            Objects.equals(valorCredito, that.valorCredito) &&
            Objects.equals(valorDebito, that.valorDebito) &&
            Objects.equals(observacoes, that.observacoes) &&
            Objects.equals(indicadorBloqueio, that.indicadorBloqueio) &&
            Objects.equals(dataHoraCadastro, that.dataHoraCadastro) &&
            Objects.equals(colaboradorCadastro, that.colaboradorCadastro) &&
            Objects.equals(dataHoraAtualizacao, that.dataHoraAtualizacao) &&
            Objects.equals(colaboradorAtualizacao, that.colaboradorAtualizacao) &&
            Objects.equals(vendaId, that.vendaId) &&
            Objects.equals(pagamentoId, that.pagamentoId) &&
            Objects.equals(carteirasClienteId, that.carteirasClienteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            descricaoLancamento,
            dataHora,
            valorCredito,
            valorDebito,
            observacoes,
            indicadorBloqueio,
            dataHoraCadastro,
            colaboradorCadastro,
            dataHoraAtualizacao,
            colaboradorAtualizacao,
            vendaId,
            pagamentoId,
            carteirasClienteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LancamentoCarteiraClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricaoLancamento != null ? "descricaoLancamento=" + descricaoLancamento + ", " : "") +
            (dataHora != null ? "dataHora=" + dataHora + ", " : "") +
            (valorCredito != null ? "valorCredito=" + valorCredito + ", " : "") +
            (valorDebito != null ? "valorDebito=" + valorDebito + ", " : "") +
            (observacoes != null ? "observacoes=" + observacoes + ", " : "") +
            (indicadorBloqueio != null ? "indicadorBloqueio=" + indicadorBloqueio + ", " : "") +
            (dataHoraCadastro != null ? "dataHoraCadastro=" + dataHoraCadastro + ", " : "") +
            (colaboradorCadastro != null ? "colaboradorCadastro=" + colaboradorCadastro + ", " : "") +
            (dataHoraAtualizacao != null ? "dataHoraAtualizacao=" + dataHoraAtualizacao + ", " : "") +
            (colaboradorAtualizacao != null ? "colaboradorAtualizacao=" + colaboradorAtualizacao + ", " : "") +
            (vendaId != null ? "vendaId=" + vendaId + ", " : "") +
            (pagamentoId != null ? "pagamentoId=" + pagamentoId + ", " : "") +
            (carteirasClienteId != null ? "carteirasClienteId=" + carteirasClienteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
