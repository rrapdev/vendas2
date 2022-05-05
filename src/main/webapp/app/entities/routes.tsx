import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Venda from './venda';
import Servico from './servico';
import ItemVenda from './item-venda';
import Cliente from './cliente';
import Colaborador from './colaborador';
import Pagamento from './pagamento';
import PlataformaPagamento from './plataforma-pagamento';
import CarteiraCliente from './carteira-cliente';
import LancamentoCarteiraCliente from './lancamento-carteira-cliente';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}venda`} component={Venda} />
        <ErrorBoundaryRoute path={`${match.url}servico`} component={Servico} />
        <ErrorBoundaryRoute path={`${match.url}item-venda`} component={ItemVenda} />
        <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
        <ErrorBoundaryRoute path={`${match.url}colaborador`} component={Colaborador} />
        <ErrorBoundaryRoute path={`${match.url}pagamento`} component={Pagamento} />
        <ErrorBoundaryRoute path={`${match.url}plataforma-pagamento`} component={PlataformaPagamento} />
        <ErrorBoundaryRoute path={`${match.url}carteira-cliente`} component={CarteiraCliente} />
        <ErrorBoundaryRoute path={`${match.url}lancamento-carteira-cliente`} component={LancamentoCarteiraCliente} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
