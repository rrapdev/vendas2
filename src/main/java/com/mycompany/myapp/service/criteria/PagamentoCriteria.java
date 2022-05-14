package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.BandeiraCartao;
import com.mycompany.myapp.domain.enumeration.FormaPagamento;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.Pagamento} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PagamentoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FormaPagamento
     */
    public static class FormaPagamentoFilter extends Filter<FormaPagamento> {

        public FormaPagamentoFilter() {}

        public FormaPagamentoFilter(FormaPagamentoFilter filter) {
            super(filter);
        }

        @Override
        public FormaPagamentoFilter copy() {
            return new FormaPagamentoFilter(this);
        }
    }

    /**
     * Class for filtering BandeiraCartao
     */
    public static class BandeiraCartaoFilter extends Filter<BandeiraCartao> {

        public BandeiraCartaoFilter() {}

        public BandeiraCartaoFilter(BandeiraCartaoFilter filter) {
            super(filter);
        }

        @Override
        public BandeiraCartaoFilter copy() {
            return new BandeiraCartaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FormaPagamentoFilter formaPagamento;

    private InstantFilter dataHora;

    private BigDecimalFilter valor;

    private StringFilter observacoes;

    private IntegerFilter numeroParcelas;

    private BandeiraCartaoFilter bandeiraCartao;

    private StringFilter clienteOrigemPagamento;

    private BooleanFilter indicadorConferido;

    private InstantFilter dataHoraCadastro;

    private StringFilter colaboradorCadastro;

    private InstantFilter dataHoraAtualizacao;

    private StringFilter colaboradorAtualizacao;

    private LongFilter adquirentePagamentoId;

    private LongFilter vendasId;

    private Boolean distinct;

    public PagamentoCriteria() {}

    public PagamentoCriteria(PagamentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.formaPagamento = other.formaPagamento == null ? null : other.formaPagamento.copy();
        this.dataHora = other.dataHora == null ? null : other.dataHora.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.observacoes = other.observacoes == null ? null : other.observacoes.copy();
        this.numeroParcelas = other.numeroParcelas == null ? null : other.numeroParcelas.copy();
        this.bandeiraCartao = other.bandeiraCartao == null ? null : other.bandeiraCartao.copy();
        this.clienteOrigemPagamento = other.clienteOrigemPagamento == null ? null : other.clienteOrigemPagamento.copy();
        this.indicadorConferido = other.indicadorConferido == null ? null : other.indicadorConferido.copy();
        this.dataHoraCadastro = other.dataHoraCadastro == null ? null : other.dataHoraCadastro.copy();
        this.colaboradorCadastro = other.colaboradorCadastro == null ? null : other.colaboradorCadastro.copy();
        this.dataHoraAtualizacao = other.dataHoraAtualizacao == null ? null : other.dataHoraAtualizacao.copy();
        this.colaboradorAtualizacao = other.colaboradorAtualizacao == null ? null : other.colaboradorAtualizacao.copy();
        this.adquirentePagamentoId = other.adquirentePagamentoId == null ? null : other.adquirentePagamentoId.copy();
        this.vendasId = other.vendasId == null ? null : other.vendasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PagamentoCriteria copy() {
        return new PagamentoCriteria(this);
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

    public FormaPagamentoFilter getFormaPagamento() {
        return formaPagamento;
    }

    public FormaPagamentoFilter formaPagamento() {
        if (formaPagamento == null) {
            formaPagamento = new FormaPagamentoFilter();
        }
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoFilter formaPagamento) {
        this.formaPagamento = formaPagamento;
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

    public BigDecimalFilter getValor() {
        return valor;
    }

    public BigDecimalFilter valor() {
        if (valor == null) {
            valor = new BigDecimalFilter();
        }
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
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

    public IntegerFilter getNumeroParcelas() {
        return numeroParcelas;
    }

    public IntegerFilter numeroParcelas() {
        if (numeroParcelas == null) {
            numeroParcelas = new IntegerFilter();
        }
        return numeroParcelas;
    }

    public void setNumeroParcelas(IntegerFilter numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public BandeiraCartaoFilter getBandeiraCartao() {
        return bandeiraCartao;
    }

    public BandeiraCartaoFilter bandeiraCartao() {
        if (bandeiraCartao == null) {
            bandeiraCartao = new BandeiraCartaoFilter();
        }
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartaoFilter bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public StringFilter getClienteOrigemPagamento() {
        return clienteOrigemPagamento;
    }

    public StringFilter clienteOrigemPagamento() {
        if (clienteOrigemPagamento == null) {
            clienteOrigemPagamento = new StringFilter();
        }
        return clienteOrigemPagamento;
    }

    public void setClienteOrigemPagamento(StringFilter clienteOrigemPagamento) {
        this.clienteOrigemPagamento = clienteOrigemPagamento;
    }

    public BooleanFilter getIndicadorConferido() {
        return indicadorConferido;
    }

    public BooleanFilter indicadorConferido() {
        if (indicadorConferido == null) {
            indicadorConferido = new BooleanFilter();
        }
        return indicadorConferido;
    }

    public void setIndicadorConferido(BooleanFilter indicadorConferido) {
        this.indicadorConferido = indicadorConferido;
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

    public LongFilter getAdquirentePagamentoId() {
        return adquirentePagamentoId;
    }

    public LongFilter adquirentePagamentoId() {
        if (adquirentePagamentoId == null) {
            adquirentePagamentoId = new LongFilter();
        }
        return adquirentePagamentoId;
    }

    public void setAdquirentePagamentoId(LongFilter adquirentePagamentoId) {
        this.adquirentePagamentoId = adquirentePagamentoId;
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
        final PagamentoCriteria that = (PagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(formaPagamento, that.formaPagamento) &&
            Objects.equals(dataHora, that.dataHora) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(observacoes, that.observacoes) &&
            Objects.equals(numeroParcelas, that.numeroParcelas) &&
            Objects.equals(bandeiraCartao, that.bandeiraCartao) &&
            Objects.equals(clienteOrigemPagamento, that.clienteOrigemPagamento) &&
            Objects.equals(indicadorConferido, that.indicadorConferido) &&
            Objects.equals(dataHoraCadastro, that.dataHoraCadastro) &&
            Objects.equals(colaboradorCadastro, that.colaboradorCadastro) &&
            Objects.equals(dataHoraAtualizacao, that.dataHoraAtualizacao) &&
            Objects.equals(colaboradorAtualizacao, that.colaboradorAtualizacao) &&
            Objects.equals(adquirentePagamentoId, that.adquirentePagamentoId) &&
            Objects.equals(vendasId, that.vendasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            formaPagamento,
            dataHora,
            valor,
            observacoes,
            numeroParcelas,
            bandeiraCartao,
            clienteOrigemPagamento,
            indicadorConferido,
            dataHoraCadastro,
            colaboradorCadastro,
            dataHoraAtualizacao,
            colaboradorAtualizacao,
            adquirentePagamentoId,
            vendasId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagamentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (formaPagamento != null ? "formaPagamento=" + formaPagamento + ", " : "") +
            (dataHora != null ? "dataHora=" + dataHora + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (observacoes != null ? "observacoes=" + observacoes + ", " : "") +
            (numeroParcelas != null ? "numeroParcelas=" + numeroParcelas + ", " : "") +
            (bandeiraCartao != null ? "bandeiraCartao=" + bandeiraCartao + ", " : "") +
            (clienteOrigemPagamento != null ? "clienteOrigemPagamento=" + clienteOrigemPagamento + ", " : "") +
            (indicadorConferido != null ? "indicadorConferido=" + indicadorConferido + ", " : "") +
            (dataHoraCadastro != null ? "dataHoraCadastro=" + dataHoraCadastro + ", " : "") +
            (colaboradorCadastro != null ? "colaboradorCadastro=" + colaboradorCadastro + ", " : "") +
            (dataHoraAtualizacao != null ? "dataHoraAtualizacao=" + dataHoraAtualizacao + ", " : "") +
            (colaboradorAtualizacao != null ? "colaboradorAtualizacao=" + colaboradorAtualizacao + ", " : "") +
            (adquirentePagamentoId != null ? "adquirentePagamentoId=" + adquirentePagamentoId + ", " : "") +
            (vendasId != null ? "vendasId=" + vendasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
