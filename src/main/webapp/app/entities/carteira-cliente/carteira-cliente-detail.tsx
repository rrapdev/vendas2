import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carteira-cliente.reducer';

export const CarteiraClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const carteiraClienteEntity = useAppSelector(state => state.carteiraCliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carteiraClienteDetailsHeading">
          <Translate contentKey="vendas2App.carteiraCliente.detail.title">CarteiraCliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.id}</dd>
          <dt>
            <span id="nomeCarteiraCliente">
              <Translate contentKey="vendas2App.carteiraCliente.nomeCarteiraCliente">Nome Carteira Cliente</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.nomeCarteiraCliente}</dd>
          <dt>
            <span id="saldoConsolidado">
              <Translate contentKey="vendas2App.carteiraCliente.saldoConsolidado">Saldo Consolidado</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.saldoConsolidado}</dd>
          <dt>
            <span id="tipoIndicadorSaldo">
              <Translate contentKey="vendas2App.carteiraCliente.tipoIndicadorSaldo">Tipo Indicador Saldo</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.tipoIndicadorSaldo}</dd>
          <dt>
            <span id="indicadorBloqueio">
              <Translate contentKey="vendas2App.carteiraCliente.indicadorBloqueio">Indicador Bloqueio</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.indicadorBloqueio ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataHoraCadastro">
              <Translate contentKey="vendas2App.carteiraCliente.dataHoraCadastro">Data Hora Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {carteiraClienteEntity.dataHoraCadastro ? (
              <TextFormat value={carteiraClienteEntity.dataHoraCadastro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorCadastro">
              <Translate contentKey="vendas2App.carteiraCliente.colaboradorCadastro">Colaborador Cadastro</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.colaboradorCadastro}</dd>
          <dt>
            <span id="dataHoraAtualizacao">
              <Translate contentKey="vendas2App.carteiraCliente.dataHoraAtualizacao">Data Hora Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {carteiraClienteEntity.dataHoraAtualizacao ? (
              <TextFormat value={carteiraClienteEntity.dataHoraAtualizacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorAtualizacao">
              <Translate contentKey="vendas2App.carteiraCliente.colaboradorAtualizacao">Colaborador Atualizacao</Translate>
            </span>
          </dt>
          <dd>{carteiraClienteEntity.colaboradorAtualizacao}</dd>
          <dt>
            <Translate contentKey="vendas2App.carteiraCliente.lancamentoCarteiraCliente">Lancamento Carteira Cliente</Translate>
          </dt>
          <dd>
            {carteiraClienteEntity.lancamentoCarteiraClientes
              ? carteiraClienteEntity.lancamentoCarteiraClientes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.descricaoLancamento}</a>
                    {carteiraClienteEntity.lancamentoCarteiraClientes && i === carteiraClienteEntity.lancamentoCarteiraClientes.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/carteira-cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carteira-cliente/${carteiraClienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarteiraClienteDetail;
