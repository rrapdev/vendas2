import venda from 'app/entities/venda/venda.reducer';
import servico from 'app/entities/servico/servico.reducer';
import itemVenda from 'app/entities/item-venda/item-venda.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import colaborador from 'app/entities/colaborador/colaborador.reducer';
import pagamento from 'app/entities/pagamento/pagamento.reducer';
import plataformaPagamento from 'app/entities/plataforma-pagamento/plataforma-pagamento.reducer';
import carteiraCliente from 'app/entities/carteira-cliente/carteira-cliente.reducer';
import lancamentoCarteiraCliente from 'app/entities/lancamento-carteira-cliente/lancamento-carteira-cliente.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  venda,
  servico,
  itemVenda,
  cliente,
  colaborador,
  pagamento,
  plataformaPagamento,
  carteiraCliente,
  lancamentoCarteiraCliente,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
