import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plataforma-pagamento.reducer';

export const PlataformaPagamentoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plataformaPagamentoEntity = useAppSelector(state => state.plataformaPagamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plataformaPagamentoDetailsHeading">
          <Translate contentKey="vendas2App.plataformaPagamento.detail.title">PlataformaPagamento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plataformaPagamentoEntity.id}</dd>
          <dt>
            <span id="nomePlataformaPagamento">
              <Translate contentKey="vendas2App.plataformaPagamento.nomePlataformaPagamento">Nome Plataforma Pagamento</Translate>
            </span>
          </dt>
          <dd>{plataformaPagamentoEntity.nomePlataformaPagamento}</dd>
          <dt>
            <span id="indicadorAtivo">
              <Translate contentKey="vendas2App.plataformaPagamento.indicadorAtivo">Indicador Ativo</Translate>
            </span>
          </dt>
          <dd>{plataformaPagamentoEntity.indicadorAtivo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/plataforma-pagamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plataforma-pagamento/${plataformaPagamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlataformaPagamentoDetail;
