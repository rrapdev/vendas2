import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LancamentoCarteiraCliente from './lancamento-carteira-cliente';
import LancamentoCarteiraClienteDetail from './lancamento-carteira-cliente-detail';
import LancamentoCarteiraClienteUpdate from './lancamento-carteira-cliente-update';
import LancamentoCarteiraClienteDeleteDialog from './lancamento-carteira-cliente-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LancamentoCarteiraClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LancamentoCarteiraClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LancamentoCarteiraClienteDetail} />
      <ErrorBoundaryRoute path={match.url} component={LancamentoCarteiraCliente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LancamentoCarteiraClienteDeleteDialog} />
  </>
);

export default Routes;
