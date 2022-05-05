import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlataformaPagamento from './plataforma-pagamento';
import PlataformaPagamentoDetail from './plataforma-pagamento-detail';
import PlataformaPagamentoUpdate from './plataforma-pagamento-update';
import PlataformaPagamentoDeleteDialog from './plataforma-pagamento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlataformaPagamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlataformaPagamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlataformaPagamentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlataformaPagamento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlataformaPagamentoDeleteDialog} />
  </>
);

export default Routes;
