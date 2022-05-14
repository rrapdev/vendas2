package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "nome_apresentacao")
    private String nomeApresentacao;

    @Column(name = "indicador_ativo")
    private Boolean indicadorAtivo;

    @JsonIgnoreProperties(value = { "lancamentoCarteiraClientes" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CarteiraCliente carteiraCliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public Cliente nomeCompleto(String nomeCompleto) {
        this.setNomeCompleto(nomeCompleto);
        return this;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Cliente telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeApresentacao() {
        return this.nomeApresentacao;
    }

    public Cliente nomeApresentacao(String nomeApresentacao) {
        this.setNomeApresentacao(nomeApresentacao);
        return this;
    }

    public void setNomeApresentacao(String nomeApresentacao) {
        this.nomeApresentacao = nomeApresentacao;
    }

    public Boolean getIndicadorAtivo() {
        return this.indicadorAtivo;
    }

    public Cliente indicadorAtivo(Boolean indicadorAtivo) {
        this.setIndicadorAtivo(indicadorAtivo);
        return this;
    }

    public void setIndicadorAtivo(Boolean indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
    }

    public CarteiraCliente getCarteiraCliente() {
        return this.carteiraCliente;
    }

    public void setCarteiraCliente(CarteiraCliente carteiraCliente) {
        this.carteiraCliente = carteiraCliente;
    }

    public Cliente carteiraCliente(CarteiraCliente carteiraCliente) {
        this.setCarteiraCliente(carteiraCliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", nomeApresentacao='" + getNomeApresentacao() + "'" +
            ", indicadorAtivo='" + getIndicadorAtivo() + "'" +
            "}";
    }
}
