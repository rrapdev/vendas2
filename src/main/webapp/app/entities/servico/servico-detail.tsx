import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servico.reducer';

export const ServicoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const servicoEntity = useAppSelector(state => state.servico.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servicoDetailsHeading">
          <Translate contentKey="vendas2App.servico.detail.title">Servico</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servicoEntity.id}</dd>
          <dt>
            <span id="nomeServico">
              <Translate contentKey="vendas2App.servico.nomeServico">Nome Servico</Translate>
            </span>
          </dt>
          <dd>{servicoEntity.nomeServico}</dd>
          <dt>
            <span id="valor">
              <Translate contentKey="vendas2App.servico.valor">Valor</Translate>
            </span>
          </dt>
          <dd>{servicoEntity.valor}</dd>
          <dt>
            <span id="indicadorAtivo">
              <Translate contentKey="vendas2App.servico.indicadorAtivo">Indicador Ativo</Translate>
            </span>
          </dt>
          <dd>{servicoEntity.indicadorAtivo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/servico" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servico/${servicoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServicoDetail;
