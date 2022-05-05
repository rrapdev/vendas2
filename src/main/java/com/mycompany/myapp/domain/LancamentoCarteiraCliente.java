package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LancamentoCarteiraCliente.
 */
@Entity
@Table(name = "lancamento_carteira_cliente")
public class LancamentoCarteiraCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private Instant dataHora;

    @NotNull
    @Column(name = "descricao_lancamento", nullable = false)
    private String descricaoLancamento;

    @Column(name = "valor_credito", precision = 21, scale = 2)
    private BigDecimal valorCredito;

    @Column(name = "valor_debito", precision = 21, scale = 2)
    private BigDecimal valorDebito;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "indicador_bloqueio")
    private Boolean indicadorBloqueio;

    @Column(name = "data_hora_cadastro")
    private Instant dataHoraCadastro;

    @Column(name = "colaborador_cadastro")
    private String colaboradorCadastro;

    @Column(name = "data_hora_atualizacao")
    private Instant dataHoraAtualizacao;

    @Column(name = "colaborador_atualizacao")
    private String colaboradorAtualizacao;

    @ManyToMany(mappedBy = "lancamentoCarteiraClientes")
    @JsonIgnoreProperties(value = { "lancamentoCarteiraClientes" }, allowSetters = true)
    private Set<CarteiraCliente> carteirasClientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LancamentoCarteiraCliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return this.dataHora;
    }

    public LancamentoCarteiraCliente dataHora(Instant dataHora) {
        this.setDataHora(dataHora);
        return this;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricaoLancamento() {
        return this.descricaoLancamento;
    }

    public LancamentoCarteiraCliente descricaoLancamento(String descricaoLancamento) {
        this.setDescricaoLancamento(descricaoLancamento);
        return this;
    }

    public void setDescricaoLancamento(String descricaoLancamento) {
        this.descricaoLancamento = descricaoLancamento;
    }

    public BigDecimal getValorCredito() {
        return this.valorCredito;
    }

    public LancamentoCarteiraCliente valorCredito(BigDecimal valorCredito) {
        this.setValorCredito(valorCredito);
        return this;
    }

    public void setValorCredito(BigDecimal valorCredito) {
        this.valorCredito = valorCredito;
    }

    public BigDecimal getValorDebito() {
        return this.valorDebito;
    }

    public LancamentoCarteiraCliente valorDebito(BigDecimal valorDebito) {
        this.setValorDebito(valorDebito);
        return this;
    }

    public void setValorDebito(BigDecimal valorDebito) {
        this.valorDebito = valorDebito;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public LancamentoCarteiraCliente observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getIndicadorBloqueio() {
        return this.indicadorBloqueio;
    }

    public LancamentoCarteiraCliente indicadorBloqueio(Boolean indicadorBloqueio) {
        this.setIndicadorBloqueio(indicadorBloqueio);
        return this;
    }

    public void setIndicadorBloqueio(Boolean indicadorBloqueio) {
        this.indicadorBloqueio = indicadorBloqueio;
    }

    public Instant getDataHoraCadastro() {
        return this.dataHoraCadastro;
    }

    public LancamentoCarteiraCliente dataHoraCadastro(Instant dataHoraCadastro) {
        this.setDataHoraCadastro(dataHoraCadastro);
        return this;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return this.colaboradorCadastro;
    }

    public LancamentoCarteiraCliente colaboradorCadastro(String colaboradorCadastro) {
        this.setColaboradorCadastro(colaboradorCadastro);
        return this;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return this.dataHoraAtualizacao;
    }

    public LancamentoCarteiraCliente dataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.setDataHoraAtualizacao(dataHoraAtualizacao);
        return this;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return this.colaboradorAtualizacao;
    }

    public LancamentoCarteiraCliente colaboradorAtualizacao(String colaboradorAtualizacao) {
        this.setColaboradorAtualizacao(colaboradorAtualizacao);
        return this;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public Set<CarteiraCliente> getCarteirasClientes() {
        return this.carteirasClientes;
    }

    public void setCarteirasClientes(Set<CarteiraCliente> carteiraClientes) {
        if (this.carteirasClientes != null) {
            this.carteirasClientes.forEach(i -> i.removeLancamentoCarteiraCliente(this));
        }
        if (carteiraClientes != null) {
            carteiraClientes.forEach(i -> i.addLancamentoCarteiraCliente(this));
        }
        this.carteirasClientes = carteiraClientes;
    }

    public LancamentoCarteiraCliente carteirasClientes(Set<CarteiraCliente> carteiraClientes) {
        this.setCarteirasClientes(carteiraClientes);
        return this;
    }

    public LancamentoCarteiraCliente addCarteirasCliente(CarteiraCliente carteiraCliente) {
        this.carteirasClientes.add(carteiraCliente);
        carteiraCliente.getLancamentoCarteiraClientes().add(this);
        return this;
    }

    public LancamentoCarteiraCliente removeCarteirasCliente(CarteiraCliente carteiraCliente) {
        this.carteirasClientes.remove(carteiraCliente);
        carteiraCliente.getLancamentoCarteiraClientes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LancamentoCarteiraCliente)) {
            return false;
        }
        return id != null && id.equals(((LancamentoCarteiraCliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LancamentoCarteiraCliente{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", descricaoLancamento='" + getDescricaoLancamento() + "'" +
            ", valorCredito=" + getValorCredito() +
            ", valorDebito=" + getValorDebito() +
            ", observacoes='" + getObservacoes() + "'" +
            ", indicadorBloqueio='" + getIndicadorBloqueio() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            "}";
    }
}
