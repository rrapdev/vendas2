package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.PlataformaPagamento} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PlataformaPagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plataforma-pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PlataformaPagamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomePlataformaPagamento;

    private BooleanFilter indicadorAtivo;

    private Boolean distinct;

    public PlataformaPagamentoCriteria() {}

    public PlataformaPagamentoCriteria(PlataformaPagamentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomePlataformaPagamento = other.nomePlataformaPagamento == null ? null : other.nomePlataformaPagamento.copy();
        this.indicadorAtivo = other.indicadorAtivo == null ? null : other.indicadorAtivo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlataformaPagamentoCriteria copy() {
        return new PlataformaPagamentoCriteria(this);
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

    public StringFilter getNomePlataformaPagamento() {
        return nomePlataformaPagamento;
    }

    public StringFilter nomePlataformaPagamento() {
        if (nomePlataformaPagamento == null) {
            nomePlataformaPagamento = new StringFilter();
        }
        return nomePlataformaPagamento;
    }

    public void setNomePlataformaPagamento(StringFilter nomePlataformaPagamento) {
        this.nomePlataformaPagamento = nomePlataformaPagamento;
    }

    public BooleanFilter getIndicadorAtivo() {
        return indicadorAtivo;
    }

    public BooleanFilter indicadorAtivo() {
        if (indicadorAtivo == null) {
            indicadorAtivo = new BooleanFilter();
        }
        return indicadorAtivo;
    }

    public void setIndicadorAtivo(BooleanFilter indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
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
        final PlataformaPagamentoCriteria that = (PlataformaPagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomePlataformaPagamento, that.nomePlataformaPagamento) &&
            Objects.equals(indicadorAtivo, that.indicadorAtivo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomePlataformaPagamento, indicadorAtivo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlataformaPagamentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomePlataformaPagamento != null ? "nomePlataformaPagamento=" + nomePlataformaPagamento + ", " : "") +
            (indicadorAtivo != null ? "indicadorAtivo=" + indicadorAtivo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
