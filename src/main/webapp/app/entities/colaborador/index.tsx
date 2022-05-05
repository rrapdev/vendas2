import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Colaborador from './colaborador';
import ColaboradorDetail from './colaborador-detail';
import ColaboradorUpdate from './colaborador-update';
import ColaboradorDeleteDialog from './colaborador-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ColaboradorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ColaboradorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ColaboradorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Colaborador} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ColaboradorDeleteDialog} />
  </>
);

export default Routes;
