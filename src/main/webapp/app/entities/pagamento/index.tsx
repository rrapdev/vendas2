import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pagamento from './pagamento';
import PagamentoDetail from './pagamento-detail';
import PagamentoUpdate from './pagamento-update';
import PagamentoDeleteDialog from './pagamento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PagamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PagamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PagamentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pagamento} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PagamentoDeleteDialog} />
  </>
);

export default Routes;
