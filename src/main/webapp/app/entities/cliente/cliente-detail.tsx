import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cliente.reducer';

export const ClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">
          <Translate contentKey="vendas2App.cliente.detail.title">Cliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.id}</dd>
          <dt>
            <span id="nomeCompleto">
              <Translate contentKey="vendas2App.cliente.nomeCompleto">Nome Completo</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nomeCompleto}</dd>
          <dt>
            <span id="telefone">
              <Translate contentKey="vendas2App.cliente.telefone">Telefone</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.telefone}</dd>
          <dt>
            <span id="nomeApresentacao">
              <Translate contentKey="vendas2App.cliente.nomeApresentacao">Nome Apresentacao</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nomeApresentacao}</dd>
          <dt>
            <span id="indicadorAtivo">
              <Translate contentKey="vendas2App.cliente.indicadorAtivo">Indicador Ativo</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.indicadorAtivo ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="vendas2App.cliente.carteiraCliente">Carteira Cliente</Translate>
          </dt>
          <dd>{clienteEntity.carteiraCliente ? clienteEntity.carteiraCliente.nomeCarteiraCliente : ''}</dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
