import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './colaborador.reducer';

export const ColaboradorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const colaboradorEntity = useAppSelector(state => state.colaborador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="colaboradorDetailsHeading">
          <Translate contentKey="vendas2App.colaborador.detail.title">Colaborador</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{colaboradorEntity.id}</dd>
          <dt>
            <span id="nomeColaborador">
              <Translate contentKey="vendas2App.colaborador.nomeColaborador">Nome Colaborador</Translate>
            </span>
          </dt>
          <dd>{colaboradorEntity.nomeColaborador}</dd>
          <dt>
            <span id="nomeApresentacao">
              <Translate contentKey="vendas2App.colaborador.nomeApresentacao">Nome Apresentacao</Translate>
            </span>
          </dt>
          <dd>{colaboradorEntity.nomeApresentacao}</dd>
          <dt>
            <span id="indicadorAtivo">
              <Translate contentKey="vendas2App.colaborador.indicadorAtivo">Indicador Ativo</Translate>
            </span>
          </dt>
          <dd>{colaboradorEntity.indicadorAtivo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/colaborador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/colaborador/${colaboradorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ColaboradorDetail;
