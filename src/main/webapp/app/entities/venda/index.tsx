import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Venda from './venda';
import VendaDetail from './venda-detail';
import VendaUpdate from './venda-update';
import VendaDeleteDialog from './venda-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VendaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Venda} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VendaDeleteDialog} />
  </>
);

export default Routes;
