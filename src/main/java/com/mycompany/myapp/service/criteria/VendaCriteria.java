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
 * Criteria class for the {@link com.mycompany.myapp.domain.Venda} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class VendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataHora;

    private BigDecimalFilter valorTotalBruto;

    private BigDecimalFilter valorTotalDesconto;

    private BigDecimalFilter valorTotalLiquido;

    private BigDecimalFilter valorTotalPago;

    private BigDecimalFilter valorSaldoRestante;

    private BooleanFilter indicadorPossuiPagamentoPendente;

    private BooleanFilter indicadorPossuiItemPresente;

    private BooleanFilter indicadorBloqueio;

    private InstantFilter dataHoraCadastro;

    private StringFilter colaboradorCadastro;

    private InstantFilter dataHoraAtualizacao;

    private StringFilter colaboradorAtualizacao;

    private LongFilter clienteQueComprouId;

    private LongFilter colaboradoresQueIndicaramId;

    private LongFilter itensVendaId;

    private LongFilter pagamentosId;

    private Boolean distinct;

    public VendaCriteria() {}

    public VendaCriteria(VendaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dataHora = other.dataHora == null ? null : other.dataHora.copy();
        this.valorTotalBruto = other.valorTotalBruto == null ? null : other.valorTotalBruto.copy();
        this.valorTotalDesconto = other.valorTotalDesconto == null ? null : other.valorTotalDesconto.copy();
        this.valorTotalLiquido = other.valorTotalLiquido == null ? null : other.valorTotalLiquido.copy();
        this.valorTotalPago = other.valorTotalPago == null ? null : other.valorTotalPago.copy();
        this.valorSaldoRestante = other.valorSaldoRestante == null ? null : other.valorSaldoRestante.copy();
        this.indicadorPossuiPagamentoPendente =
            other.indicadorPossuiPagamentoPendente == null ? null : other.indicadorPossuiPagamentoPendente.copy();
        this.indicadorPossuiItemPresente = other.indicadorPossuiItemPresente == null ? null : other.indicadorPossuiItemPresente.copy();
        this.indicadorBloqueio = other.indicadorBloqueio == null ? null : other.indicadorBloqueio.copy();
        this.dataHoraCadastro = other.dataHoraCadastro == null ? null : other.dataHoraCadastro.copy();
        this.colaboradorCadastro = other.colaboradorCadastro == null ? null : other.colaboradorCadastro.copy();
        this.dataHoraAtualizacao = other.dataHoraAtualizacao == null ? null : other.dataHoraAtualizacao.copy();
        this.colaboradorAtualizacao = other.colaboradorAtualizacao == null ? null : other.colaboradorAtualizacao.copy();
        this.clienteQueComprouId = other.clienteQueComprouId == null ? null : other.clienteQueComprouId.copy();
        this.colaboradoresQueIndicaramId = other.colaboradoresQueIndicaramId == null ? null : other.colaboradoresQueIndicaramId.copy();
        this.itensVendaId = other.itensVendaId == null ? null : other.itensVendaId.copy();
        this.pagamentosId = other.pagamentosId == null ? null : other.pagamentosId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VendaCriteria copy() {
        return new VendaCriteria(this);
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

    public BigDecimalFilter getValorTotalBruto() {
        return valorTotalBruto;
    }

    public BigDecimalFilter valorTotalBruto() {
        if (valorTotalBruto == null) {
            valorTotalBruto = new BigDecimalFilter();
        }
        return valorTotalBruto;
    }

    public void setValorTotalBruto(BigDecimalFilter valorTotalBruto) {
        this.valorTotalBruto = valorTotalBruto;
    }

    public BigDecimalFilter getValorTotalDesconto() {
        return valorTotalDesconto;
    }

    public BigDecimalFilter valorTotalDesconto() {
        if (valorTotalDesconto == null) {
            valorTotalDesconto = new BigDecimalFilter();
        }
        return valorTotalDesconto;
    }

    public void setValorTotalDesconto(BigDecimalFilter valorTotalDesconto) {
        this.valorTotalDesconto = valorTotalDesconto;
    }

    public BigDecimalFilter getValorTotalLiquido() {
        return valorTotalLiquido;
    }

    public BigDecimalFilter valorTotalLiquido() {
        if (valorTotalLiquido == null) {
            valorTotalLiquido = new BigDecimalFilter();
        }
        return valorTotalLiquido;
    }

    public void setValorTotalLiquido(BigDecimalFilter valorTotalLiquido) {
        this.valorTotalLiquido = valorTotalLiquido;
    }

    public BigDecimalFilter getValorTotalPago() {
        return valorTotalPago;
    }

    public BigDecimalFilter valorTotalPago() {
        if (valorTotalPago == null) {
            valorTotalPago = new BigDecimalFilter();
        }
        return valorTotalPago;
    }

    public void setValorTotalPago(BigDecimalFilter valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public BigDecimalFilter getValorSaldoRestante() {
        return valorSaldoRestante;
    }

    public BigDecimalFilter valorSaldoRestante() {
        if (valorSaldoRestante == null) {
            valorSaldoRestante = new BigDecimalFilter();
        }
        return valorSaldoRestante;
    }

    public void setValorSaldoRestante(BigDecimalFilter valorSaldoRestante) {
        this.valorSaldoRestante = valorSaldoRestante;
    }

    public BooleanFilter getIndicadorPossuiPagamentoPendente() {
        return indicadorPossuiPagamentoPendente;
    }

    public BooleanFilter indicadorPossuiPagamentoPendente() {
        if (indicadorPossuiPagamentoPendente == null) {
            indicadorPossuiPagamentoPendente = new BooleanFilter();
        }
        return indicadorPossuiPagamentoPendente;
    }

    public void setIndicadorPossuiPagamentoPendente(BooleanFilter indicadorPossuiPagamentoPendente) {
        this.indicadorPossuiPagamentoPendente = indicadorPossuiPagamentoPendente;
    }

    public BooleanFilter getIndicadorPossuiItemPresente() {
        return indicadorPossuiItemPresente;
    }

    public BooleanFilter indicadorPossuiItemPresente() {
        if (indicadorPossuiItemPresente == null) {
            indicadorPossuiItemPresente = new BooleanFilter();
        }
        return indicadorPossuiItemPresente;
    }

    public void setIndicadorPossuiItemPresente(BooleanFilter indicadorPossuiItemPresente) {
        this.indicadorPossuiItemPresente = indicadorPossuiItemPresente;
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

    public LongFilter getClienteQueComprouId() {
        return clienteQueComprouId;
    }

    public LongFilter clienteQueComprouId() {
        if (clienteQueComprouId == null) {
            clienteQueComprouId = new LongFilter();
        }
        return clienteQueComprouId;
    }

    public void setClienteQueComprouId(LongFilter clienteQueComprouId) {
        this.clienteQueComprouId = clienteQueComprouId;
    }

    public LongFilter getColaboradoresQueIndicaramId() {
        return colaboradoresQueIndicaramId;
    }

    public LongFilter colaboradoresQueIndicaramId() {
        if (colaboradoresQueIndicaramId == null) {
            colaboradoresQueIndicaramId = new LongFilter();
        }
        return colaboradoresQueIndicaramId;
    }

    public void setColaboradoresQueIndicaramId(LongFilter colaboradoresQueIndicaramId) {
        this.colaboradoresQueIndicaramId = colaboradoresQueIndicaramId;
    }

    public LongFilter getItensVendaId() {
        return itensVendaId;
    }

    public LongFilter itensVendaId() {
        if (itensVendaId == null) {
            itensVendaId = new LongFilter();
        }
        return itensVendaId;
    }

    public void setItensVendaId(LongFilter itensVendaId) {
        this.itensVendaId = itensVendaId;
    }

    public LongFilter getPagamentosId() {
        return pagamentosId;
    }

    public LongFilter pagamentosId() {
        if (pagamentosId == null) {
            pagamentosId = new LongFilter();
        }
        return pagamentosId;
    }

    public void setPagamentosId(LongFilter pagamentosId) {
        this.pagamentosId = pagamentosId;
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
        final VendaCriteria that = (VendaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataHora, that.dataHora) &&
            Objects.equals(valorTotalBruto, that.valorTotalBruto) &&
            Objects.equals(valorTotalDesconto, that.valorTotalDesconto) &&
            Objects.equals(valorTotalLiquido, that.valorTotalLiquido) &&
            Objects.equals(valorTotalPago, that.valorTotalPago) &&
            Objects.equals(valorSaldoRestante, that.valorSaldoRestante) &&
            Objects.equals(indicadorPossuiPagamentoPendente, that.indicadorPossuiPagamentoPendente) &&
            Objects.equals(indicadorPossuiItemPresente, that.indicadorPossuiItemPresente) &&
            Objects.equals(indicadorBloqueio, that.indicadorBloqueio) &&
            Objects.equals(dataHoraCadastro, that.dataHoraCadastro) &&
            Objects.equals(colaboradorCadastro, that.colaboradorCadastro) &&
            Objects.equals(dataHoraAtualizacao, that.dataHoraAtualizacao) &&
            Objects.equals(colaboradorAtualizacao, that.colaboradorAtualizacao) &&
            Objects.equals(clienteQueComprouId, that.clienteQueComprouId) &&
            Objects.equals(colaboradoresQueIndicaramId, that.colaboradoresQueIndicaramId) &&
            Objects.equals(itensVendaId, that.itensVendaId) &&
            Objects.equals(pagamentosId, that.pagamentosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dataHora,
            valorTotalBruto,
            valorTotalDesconto,
            valorTotalLiquido,
            valorTotalPago,
            valorSaldoRestante,
            indicadorPossuiPagamentoPendente,
            indicadorPossuiItemPresente,
            indicadorBloqueio,
            dataHoraCadastro,
            colaboradorCadastro,
            dataHoraAtualizacao,
            colaboradorAtualizacao,
            clienteQueComprouId,
            colaboradoresQueIndicaramId,
            itensVendaId,
            pagamentosId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dataHora != null ? "dataHora=" + dataHora + ", " : "") +
            (valorTotalBruto != null ? "valorTotalBruto=" + valorTotalBruto + ", " : "") +
            (valorTotalDesconto != null ? "valorTotalDesconto=" + valorTotalDesconto + ", " : "") +
            (valorTotalLiquido != null ? "valorTotalLiquido=" + valorTotalLiquido + ", " : "") +
            (valorTotalPago != null ? "valorTotalPago=" + valorTotalPago + ", " : "") +
            (valorSaldoRestante != null ? "valorSaldoRestante=" + valorSaldoRestante + ", " : "") +
            (indicadorPossuiPagamentoPendente != null ? "indicadorPossuiPagamentoPendente=" + indicadorPossuiPagamentoPendente + ", " : "") +
            (indicadorPossuiItemPresente != null ? "indicadorPossuiItemPresente=" + indicadorPossuiItemPresente + ", " : "") +
            (indicadorBloqueio != null ? "indicadorBloqueio=" + indicadorBloqueio + ", " : "") +
            (dataHoraCadastro != null ? "dataHoraCadastro=" + dataHoraCadastro + ", " : "") +
            (colaboradorCadastro != null ? "colaboradorCadastro=" + colaboradorCadastro + ", " : "") +
            (dataHoraAtualizacao != null ? "dataHoraAtualizacao=" + dataHoraAtualizacao + ", " : "") +
            (colaboradorAtualizacao != null ? "colaboradorAtualizacao=" + colaboradorAtualizacao + ", " : "") +
            (clienteQueComprouId != null ? "clienteQueComprouId=" + clienteQueComprouId + ", " : "") +
            (colaboradoresQueIndicaramId != null ? "colaboradoresQueIndicaramId=" + colaboradoresQueIndicaramId + ", " : "") +
            (itensVendaId != null ? "itensVendaId=" + itensVendaId + ", " : "") +
            (pagamentosId != null ? "pagamentosId=" + pagamentosId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
