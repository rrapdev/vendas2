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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Servico} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ServicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /servicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ServicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeServico;

    private BigDecimalFilter valor;

    private BooleanFilter indicadorAtivo;

    private Boolean distinct;

    public ServicoCriteria() {}

    public ServicoCriteria(ServicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeServico = other.nomeServico == null ? null : other.nomeServico.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.indicadorAtivo = other.indicadorAtivo == null ? null : other.indicadorAtivo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ServicoCriteria copy() {
        return new ServicoCriteria(this);
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

    public StringFilter getNomeServico() {
        return nomeServico;
    }

    public StringFilter nomeServico() {
        if (nomeServico == null) {
            nomeServico = new StringFilter();
        }
        return nomeServico;
    }

    public void setNomeServico(StringFilter nomeServico) {
        this.nomeServico = nomeServico;
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
        final ServicoCriteria that = (ServicoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeServico, that.nomeServico) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(indicadorAtivo, that.indicadorAtivo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeServico, valor, indicadorAtivo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeServico != null ? "nomeServico=" + nomeServico + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (indicadorAtivo != null ? "indicadorAtivo=" + indicadorAtivo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
