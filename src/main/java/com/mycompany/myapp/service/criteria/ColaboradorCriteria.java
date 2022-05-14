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
 * Criteria class for the {@link com.mycompany.myapp.domain.Colaborador} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ColaboradorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /colaboradors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ColaboradorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeColaborador;

    private StringFilter nomeApresentacao;

    private BooleanFilter indicadorAtivo;

    private LongFilter vendasId;

    private Boolean distinct;

    public ColaboradorCriteria() {}

    public ColaboradorCriteria(ColaboradorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeColaborador = other.nomeColaborador == null ? null : other.nomeColaborador.copy();
        this.nomeApresentacao = other.nomeApresentacao == null ? null : other.nomeApresentacao.copy();
        this.indicadorAtivo = other.indicadorAtivo == null ? null : other.indicadorAtivo.copy();
        this.vendasId = other.vendasId == null ? null : other.vendasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ColaboradorCriteria copy() {
        return new ColaboradorCriteria(this);
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

    public StringFilter getNomeColaborador() {
        return nomeColaborador;
    }

    public StringFilter nomeColaborador() {
        if (nomeColaborador == null) {
            nomeColaborador = new StringFilter();
        }
        return nomeColaborador;
    }

    public void setNomeColaborador(StringFilter nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public StringFilter getNomeApresentacao() {
        return nomeApresentacao;
    }

    public StringFilter nomeApresentacao() {
        if (nomeApresentacao == null) {
            nomeApresentacao = new StringFilter();
        }
        return nomeApresentacao;
    }

    public void setNomeApresentacao(StringFilter nomeApresentacao) {
        this.nomeApresentacao = nomeApresentacao;
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
        final ColaboradorCriteria that = (ColaboradorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeColaborador, that.nomeColaborador) &&
            Objects.equals(nomeApresentacao, that.nomeApresentacao) &&
            Objects.equals(indicadorAtivo, that.indicadorAtivo) &&
            Objects.equals(vendasId, that.vendasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeColaborador, nomeApresentacao, indicadorAtivo, vendasId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColaboradorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeColaborador != null ? "nomeColaborador=" + nomeColaborador + ", " : "") +
            (nomeApresentacao != null ? "nomeApresentacao=" + nomeApresentacao + ", " : "") +
            (indicadorAtivo != null ? "indicadorAtivo=" + indicadorAtivo + ", " : "") +
            (vendasId != null ? "vendasId=" + vendasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
