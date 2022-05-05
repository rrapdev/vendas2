import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ItemVenda from './item-venda';
import ItemVendaDetail from './item-venda-detail';
import ItemVendaUpdate from './item-venda-update';
import ItemVendaDeleteDialog from './item-venda-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ItemVendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ItemVendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ItemVendaDetail} />
      <ErrorBoundaryRoute path={match.url} component={ItemVenda} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ItemVendaDeleteDialog} />
  </>
);

export default Routes;
