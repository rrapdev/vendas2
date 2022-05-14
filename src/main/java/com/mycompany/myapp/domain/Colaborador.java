package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Colaborador.
 */
@Entity
@Table(name = "colaborador")
public class Colaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_colaborador", nullable = false)
    private String nomeColaborador;

    @Column(name = "nome_apresentacao")
    private String nomeApresentacao;

    @Column(name = "indicador_ativo")
    private Boolean indicadorAtivo;

    @ManyToMany(mappedBy = "colaboradoresQueIndicarams")
    @JsonIgnoreProperties(value = { "clienteQueComprou", "colaboradoresQueIndicarams", "itensVendas", "pagamentos" }, allowSetters = true)
    private Set<Venda> vendas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Colaborador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeColaborador() {
        return this.nomeColaborador;
    }

    public Colaborador nomeColaborador(String nomeColaborador) {
        this.setNomeColaborador(nomeColaborador);
        return this;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public String getNomeApresentacao() {
        return this.nomeApresentacao;
    }

    public Colaborador nomeApresentacao(String nomeApresentacao) {
        this.setNomeApresentacao(nomeApresentacao);
        return this;
    }

    public void setNomeApresentacao(String nomeApresentacao) {
        this.nomeApresentacao = nomeApresentacao;
    }

    public Boolean getIndicadorAtivo() {
        return this.indicadorAtivo;
    }

    public Colaborador indicadorAtivo(Boolean indicadorAtivo) {
        this.setIndicadorAtivo(indicadorAtivo);
        return this;
    }

    public void setIndicadorAtivo(Boolean indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
    }

    public Set<Venda> getVendas() {
        return this.vendas;
    }

    public void setVendas(Set<Venda> vendas) {
        if (this.vendas != null) {
            this.vendas.forEach(i -> i.removeColaboradoresQueIndicaram(this));
        }
        if (vendas != null) {
            vendas.forEach(i -> i.addColaboradoresQueIndicaram(this));
        }
        this.vendas = vendas;
    }

    public Colaborador vendas(Set<Venda> vendas) {
        this.setVendas(vendas);
        return this;
    }

    public Colaborador addVendas(Venda venda) {
        this.vendas.add(venda);
        venda.getColaboradoresQueIndicarams().add(this);
        return this;
    }

    public Colaborador removeVendas(Venda venda) {
        this.vendas.remove(venda);
        venda.getColaboradoresQueIndicarams().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaborador)) {
            return false;
        }
        return id != null && id.equals(((Colaborador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colaborador{" +
            "id=" + getId() +
            ", nomeColaborador='" + getNomeColaborador() + "'" +
            ", nomeApresentacao='" + getNomeApresentacao() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
