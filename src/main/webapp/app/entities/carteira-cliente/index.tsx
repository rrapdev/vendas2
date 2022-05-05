import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarteiraCliente from './carteira-cliente';
import CarteiraClienteDetail from './carteira-cliente-detail';
import CarteiraClienteUpdate from './carteira-cliente-update';
import CarteiraClienteDeleteDialog from './carteira-cliente-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarteiraClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarteiraClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarteiraClienteDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarteiraCliente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CarteiraClienteDeleteDialog} />
  </>
);

export default Routes;
