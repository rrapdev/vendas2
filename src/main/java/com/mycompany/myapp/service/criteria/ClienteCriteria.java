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
 * Criteria class for the {@link com.mycompany.myapp.domain.Cliente} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeCompleto;

    private StringFilter telefone;

    private StringFilter nomeApresentacao;

    private BooleanFilter indicadorAtivo;

    private LongFilter carteiraClienteId;

    private Boolean distinct;

    public ClienteCriteria() {}

    public ClienteCriteria(ClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeCompleto = other.nomeCompleto == null ? null : other.nomeCompleto.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.nomeApresentacao = other.nomeApresentacao == null ? null : other.nomeApresentacao.copy();
        this.indicadorAtivo = other.indicadorAtivo == null ? null : other.indicadorAtivo.copy();
        this.carteiraClienteId = other.carteiraClienteId == null ? null : other.carteiraClienteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
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

    public StringFilter getNomeCompleto() {
        return nomeCompleto;
    }

    public StringFilter nomeCompleto() {
        if (nomeCompleto == null) {
            nomeCompleto = new StringFilter();
        }
        return nomeCompleto;
    }

    public void setNomeCompleto(StringFilter nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
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

    public LongFilter getCarteiraClienteId() {
        return carteiraClienteId;
    }

    public LongFilter carteiraClienteId() {
        if (carteiraClienteId == null) {
            carteiraClienteId = new LongFilter();
        }
        return carteiraClienteId;
    }

    public void setCarteiraClienteId(LongFilter carteiraClienteId) {
        this.carteiraClienteId = carteiraClienteId;
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
        final ClienteCriteria that = (ClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeCompleto, that.nomeCompleto) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(nomeApresentacao, that.nomeApresentacao) &&
            Objects.equals(indicadorAtivo, that.indicadorAtivo) &&
            Objects.equals(carteiraClienteId, that.carteiraClienteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeCompleto, telefone, nomeApresentacao, indicadorAtivo, carteiraClienteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeCompleto != null ? "nomeCompleto=" + nomeCompleto + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (nomeApresentacao != null ? "nomeApresentacao=" + nomeApresentacao + ", " : "") +
            (indicadorAtivo != null ? "indicadorAtivo=" + indicadorAtivo + ", " : "") +
            (carteiraClienteId != null ? "carteiraClienteId=" + carteiraClienteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
