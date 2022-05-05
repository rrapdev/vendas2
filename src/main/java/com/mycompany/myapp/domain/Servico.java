package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Servico.
 */
@Entity
@Table(name = "servico")
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_servico", nullable = false)
    private String nomeServico;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @Column(name = "indicador_ativo")
    private Boolean indicadorAtivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeServico() {
        return this.nomeServico;
    }

    public Servico nomeServico(String nomeServico) {
        this.setNomeServico(nomeServico);
        return this;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Servico valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getIndicadorAtivo() {
        return this.indicadorAtivo;
    }

    public Servico indicadorAtivo(Boolean indicadorAtivo) {
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
        if (!(o instanceof Servico)) {
            return false;
        }
        return id != null && id.equals(((Servico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servico{" +
            "id=" + getId() +
            ", nomeServico='" + getNomeServico() + "'" +
            ", valor=" + getValor() +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
