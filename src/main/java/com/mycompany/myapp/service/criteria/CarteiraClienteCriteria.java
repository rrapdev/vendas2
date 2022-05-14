package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.TipoSaldo;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.CarteiraCliente} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CarteiraClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /carteira-clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CarteiraClienteCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoSaldo
     */
    public static class TipoSaldoFilter extends Filter<TipoSaldo> {

        public TipoSaldoFilter() {}

        public TipoSaldoFilter(TipoSaldoFilter filter) {
            super(filter);
        }

        @Override
        public TipoSaldoFilter copy() {
            return new TipoSaldoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeCarteiraCliente;

    private BigDecimalFilter saldoConsolidado;

    private TipoSaldoFilter tipoIndicadorSaldo;

    private BooleanFilter indicadorBloqueio;

    private InstantFilter dataHoraCadastro;

    private StringFilter colaboradorCadastro;

    private InstantFilter dataHoraAtualizacao;

    private StringFilter colaboradorAtualizacao;

    private LongFilter lancamentoCarteiraClienteId;

    private Boolean distinct;

    public CarteiraClienteCriteria() {}

    public CarteiraClienteCriteria(CarteiraClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeCarteiraCliente = other.nomeCarteiraCliente == null ? null : other.nomeCarteiraCliente.copy();
        this.saldoConsolidado = other.saldoConsolidado == null ? null : other.saldoConsolidado.copy();
        this.tipoIndicadorSaldo = other.tipoIndicadorSaldo == null ? null : other.tipoIndicadorSaldo.copy();
        this.indicadorBloqueio = other.indicadorBloqueio == null ? null : other.indicadorBloqueio.copy();
        this.dataHoraCadastro = other.dataHoraCadastro == null ? null : other.dataHoraCadastro.copy();
        this.colaboradorCadastro = other.colaboradorCadastro == null ? null : other.colaboradorCadastro.copy();
        this.dataHoraAtualizacao = other.dataHoraAtualizacao == null ? null : other.dataHoraAtualizacao.copy();
        this.colaboradorAtualizacao = other.colaboradorAtualizacao == null ? null : other.colaboradorAtualizacao.copy();
        this.lancamentoCarteiraClienteId = other.lancamentoCarteiraClienteId == null ? null : other.lancamentoCarteiraClienteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CarteiraClienteCriteria copy() {
        return new CarteiraClienteCriteria(this);
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

    public StringFilter getNomeCarteiraCliente() {
        return nomeCarteiraCliente;
    }

    public StringFilter nomeCarteiraCliente() {
        if (nomeCarteiraCliente == null) {
            nomeCarteiraCliente = new StringFilter();
        }
        return nomeCarteiraCliente;
    }

    public void setNomeCarteiraCliente(StringFilter nomeCarteiraCliente) {
        this.nomeCarteiraCliente = nomeCarteiraCliente;
    }

    public BigDecimalFilter getSaldoConsolidado() {
        return saldoConsolidado;
    }

    public BigDecimalFilter saldoConsolidado() {
        if (saldoConsolidado == null) {
            saldoConsolidado = new BigDecimalFilter();
        }
        return saldoConsolidado;
    }

    public void setSaldoConsolidado(BigDecimalFilter saldoConsolidado) {
        this.saldoConsolidado = saldoConsolidado;
    }

    public TipoSaldoFilter getTipoIndicadorSaldo() {
        return tipoIndicadorSaldo;
    }

    public TipoSaldoFilter tipoIndicadorSaldo() {
        if (tipoIndicadorSaldo == null) {
            tipoIndicadorSaldo = new TipoSaldoFilter();
        }
        return tipoIndicadorSaldo;
    }

    public void setTipoIndicadorSaldo(TipoSaldoFilter tipoIndicadorSaldo) {
        this.tipoIndicadorSaldo = tipoIndicadorSaldo;
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

    public LongFilter getLancamentoCarteiraClienteId() {
        return lancamentoCarteiraClienteId;
    }

    public LongFilter lancamentoCarteiraClienteId() {
        if (lancamentoCarteiraClienteId == null) {
            lancamentoCarteiraClienteId = new LongFilter();
        }
        return lancamentoCarteiraClienteId;
    }

    public void setLancamentoCarteiraClienteId(LongFilter lancamentoCarteiraClienteId) {
        this.lancamentoCarteiraClienteId = lancamentoCarteiraClienteId;
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
        final CarteiraClienteCriteria that = (CarteiraClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeCarteiraCliente, that.nomeCarteiraCliente) &&
            Objects.equals(saldoConsolidado, that.saldoConsolidado) &&
            Objects.equals(tipoIndicadorSaldo, that.tipoIndicadorSaldo) &&
            Objects.equals(indicadorBloqueio, that.indicadorBloqueio) &&
            Objects.equals(dataHoraCadastro, that.dataHoraCadastro) &&
            Objects.equals(colaboradorCadastro, that.colaboradorCadastro) &&
            Objects.equals(dataHoraAtualizacao, that.dataHoraAtualizacao) &&
            Objects.equals(colaboradorAtualizacao, that.colaboradorAtualizacao) &&
            Objects.equals(lancamentoCarteiraClienteId, that.lancamentoCarteiraClienteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nomeCarteiraCliente,
            saldoConsolidado,
            tipoIndicadorSaldo,
            indicadorBloqueio,
            dataHoraCadastro,
            colaboradorCadastro,
            dataHoraAtualizacao,
            colaboradorAtualizacao,
            lancamentoCarteiraClienteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteiraClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeCarteiraCliente != null ? "nomeCarteiraCliente=" + nomeCarteiraCliente + ", " : "") +
            (saldoConsolidado != null ? "saldoConsolidado=" + saldoConsolidado + ", " : "") +
            (tipoIndicadorSaldo != null ? "tipoIndicadorSaldo=" + tipoIndicadorSaldo + ", " : "") +
            (indicadorBloqueio != null ? "indicadorBloqueio=" + indicadorBloqueio + ", " : "") +
            (dataHoraCadastro != null ? "dataHoraCadastro=" + dataHoraCadastro + ", " : "") +
            (colaboradorCadastro != null ? "colaboradorCadastro=" + colaboradorCadastro + ", " : "") +
            (dataHoraAtualizacao != null ? "dataHoraAtualizacao=" + dataHoraAtualizacao + ", " : "") +
            (colaboradorAtualizacao != null ? "colaboradorAtualizacao=" + colaboradorAtualizacao + ", " : "") +
            (lancamentoCarteiraClienteId != null ? "lancamentoCarteiraClienteId=" + lancamentoCarteiraClienteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
