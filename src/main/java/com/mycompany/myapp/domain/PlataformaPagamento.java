package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PlataformaPagamento.
 */
@Entity
@Table(name = "plataforma_pagamento")
public class PlataformaPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_plataforma_pagamento", nullable = false)
    private String nomePlataformaPagamento;

    @Column(name = "indicador_ativo")
    private Boolean indicadorAtivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlataformaPagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePlataformaPagamento() {
        return this.nomePlataformaPagamento;
    }

    public PlataformaPagamento nomePlataformaPagamento(String nomePlataformaPagamento) {
        this.setNomePlataformaPagamento(nomePlataformaPagamento);
        return this;
    }

    public void setNomePlataformaPagamento(String nomePlataformaPagamento) {
        this.nomePlataformaPagamento = nomePlataformaPagamento;
    }

    public Boolean getIndicadorAtivo() {
        return this.indicadorAtivo;
    }

    public PlataformaPagamento indicadorAtivo(Boolean indicadorAtivo) {
        this.setIndicadorAtivo(indicadorAtivo);
        return this;
    }

    public void setIndicadorAtivo(Boolean indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlataformaPagamento)) {
            return false;
        }
        return id != null && id.equals(((PlataformaPagamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlataformaPagamento{" +
            "id=" + getId() +
            ", nomePlataformaPagamento='" + getNomePlataformaPagamento() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
