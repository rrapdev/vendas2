package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.TipoSaldo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CarteiraCliente.
 */
@Entity
@Table(name = "carteira_cliente")
public class CarteiraCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_carteira_cliente", nullable = false)
    private String nomeCarteiraCliente;

    @Column(name = "saldo_consolidado", precision = 21, scale = 2)
    private BigDecimal saldoConsolidado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_indicador_saldo")
    private TipoSaldo tipoIndicadorSaldo;

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

    @ManyToMany
    @JoinTable(
        name = "rel_carteira_cliente__lancamento_carteira_cliente",
        joinColumns = @JoinColumn(name = "carteira_cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "lancamento_carteira_cliente_id")
    )
    @JsonIgnoreProperties(value = { "venda", "pagamento", "carteirasClientes" }, allowSetters = true)
    private Set<LancamentoCarteiraCliente> lancamentoCarteiraClientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarteiraCliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCarteiraCliente() {
        return this.nomeCarteiraCliente;
    }

    public CarteiraCliente nomeCarteiraCliente(String nomeCarteiraCliente) {
        this.setNomeCarteiraCliente(nomeCarteiraCliente);
        return this;
    }

    public void setNomeCarteiraCliente(String nomeCarteiraCliente) {
        this.nomeCarteiraCliente = nomeCarteiraCliente;
    }

    public BigDecimal getSaldoConsolidado() {
        return this.saldoConsolidado;
    }

    public CarteiraCliente saldoConsolidado(BigDecimal saldoConsolidado) {
        this.setSaldoConsolidado(saldoConsolidado);
        return this;
    }

    public void setSaldoConsolidado(BigDecimal saldoConsolidado) {
        this.saldoConsolidado = saldoConsolidado;
    }

    public TipoSaldo getTipoIndicadorSaldo() {
        return this.tipoIndicadorSaldo;
    }

    public CarteiraCliente tipoIndicadorSaldo(TipoSaldo tipoIndicadorSaldo) {
        this.setTipoIndicadorSaldo(tipoIndicadorSaldo);
        return this;
    }

    public void setTipoIndicadorSaldo(TipoSaldo tipoIndicadorSaldo) {
        this.tipoIndicadorSaldo = tipoIndicadorSaldo;
    }

    public Boolean getIndicadorBloqueio() {
        return this.indicadorBloqueio;
    }

    public CarteiraCliente indicadorBloqueio(Boolean indicadorBloqueio) {
        this.setIndicadorBloqueio(indicadorBloqueio);
        return this;
    }

    public void setIndicadorBloqueio(Boolean indicadorBloqueio) {
        this.indicadorBloqueio = indicadorBloqueio;
    }

    public Instant getDataHoraCadastro() {
        return this.dataHoraCadastro;
    }

    public CarteiraCliente dataHoraCadastro(Instant dataHoraCadastro) {
        this.setDataHoraCadastro(dataHoraCadastro);
        return this;
    }

    public void setDataHoraCadastro(Instant dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getColaboradorCadastro() {
        return this.colaboradorCadastro;
    }

    public CarteiraCliente colaboradorCadastro(String colaboradorCadastro) {
        this.setColaboradorCadastro(colaboradorCadastro);
        return this;
    }

    public void setColaboradorCadastro(String colaboradorCadastro) {
        this.colaboradorCadastro = colaboradorCadastro;
    }

    public Instant getDataHoraAtualizacao() {
        return this.dataHoraAtualizacao;
    }

    public CarteiraCliente dataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.setDataHoraAtualizacao(dataHoraAtualizacao);
        return this;
    }

    public void setDataHoraAtualizacao(Instant dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public String getColaboradorAtualizacao() {
        return this.colaboradorAtualizacao;
    }

    public CarteiraCliente colaboradorAtualizacao(String colaboradorAtualizacao) {
        this.setColaboradorAtualizacao(colaboradorAtualizacao);
        return this;
    }

    public void setColaboradorAtualizacao(String colaboradorAtualizacao) {
        this.colaboradorAtualizacao = colaboradorAtualizacao;
    }

    public Set<LancamentoCarteiraCliente> getLancamentoCarteiraClientes() {
        return this.lancamentoCarteiraClientes;
    }

    public void setLancamentoCarteiraClientes(Set<LancamentoCarteiraCliente> lancamentoCarteiraClientes) {
        this.lancamentoCarteiraClientes = lancamentoCarteiraClientes;
    }

    public CarteiraCliente lancamentoCarteiraClientes(Set<LancamentoCarteiraCliente> lancamentoCarteiraClientes) {
        this.setLancamentoCarteiraClientes(lancamentoCarteiraClientes);
        return this;
    }

    public CarteiraCliente addLancamentoCarteiraCliente(LancamentoCarteiraCliente lancamentoCarteiraCliente) {
        this.lancamentoCarteiraClientes.add(lancamentoCarteiraCliente);
        lancamentoCarteiraCliente.getCarteirasClientes().add(this);
        return this;
    }

    public CarteiraCliente removeLancamentoCarteiraCliente(LancamentoCarteiraCliente lancamentoCarteiraCliente) {
        this.lancamentoCarteiraClientes.remove(lancamentoCarteiraCliente);
        lancamentoCarteiraCliente.getCarteirasClientes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteiraCliente)) {
            return false;
        }
        return id != null && id.equals(((CarteiraCliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteiraCliente{" +
            "id=" + getId() +
            ", nomeCarteiraCliente='" + getNomeCarteiraCliente() + "'" +
            ", saldoConsolidado=" + getSaldoConsolidado() +
            ", tipoIndicadorSaldo='" + getTipoIndicadorSaldo() + "'" +
            ", indicadorBloqueio='" + getIndicadorBloqueio() + "'" +
            ", dataHoraCadastro='" + getDataHoraCadastro() + "'" +
            ", colaboradorCadastro='" + getColaboradorCadastro() + "'" +
            ", dataHoraAtualizacao='" + getDataHoraAtualizacao() + "'" +
            ", colaboradorAtualizacao='" + getColaboradorAtualizacao() + "'" +
            "}";
    }
}
