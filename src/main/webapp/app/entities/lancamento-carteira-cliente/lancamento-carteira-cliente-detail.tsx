import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lancamento-carteira-cliente.reducer';

export const LancamentoCarteiraClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const lancamentoCarteiraClienteEntity = useAppSelector(state => state.lancamentoCarteiraCliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lancamentoCarteiraClienteDetailsHeading">
          <Translate contentKey="vendas2App.lancamentoCarteiraCliente.detail.title">LancamentoCarteiraCliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.id}</dd>
          <dt>
            <span id="descricaoLancamento">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.descricaoLancamento">Descricao Lancamento</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.descricaoLancamento}</dd>
          <dt>
            <span id="dataHora">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHora">Data Hora</Translate>
            </span>
          </dt>
          <dd>
            {lancamentoCarteiraClienteEntity.dataHora ? (
              <TextFormat value={lancamentoCarteiraClienteEntity.dataHora} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="valorCredito">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.valorCredito">Valor Credito</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.valorCredito}</dd>
          <dt>
            <span id="valorDebito">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.valorDebito">Valor Debito</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.valorDebito}</dd>
          <dt>
            <span id="observacoes">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.observacoes">Observacoes</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.observacoes}</dd>
          <dt>
            <span id="indicadorBloqueio">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.indicadorBloqueio">Indicador Bloqueio</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.indicadorBloqueio ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataHoraCadastro">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHoraCadastro">Data Hora Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {lancamentoCarteiraClienteEntity.dataHoraCadastro ? (
              <TextFormat value={lancamentoCarteiraClienteEntity.dataHoraCadastro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorCadastro">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.colaboradorCadastro">Colaborador Cadastro</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.colaboradorCadastro}</dd>
          <dt>
            <span id="dataHoraAtualizacao">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHoraAtualizacao">Data Hora Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {lancamentoCarteiraClienteEntity.dataHoraAtualizacao ? (
              <TextFormat value={lancamentoCarteiraClienteEntity.dataHoraAtualizacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorAtualizacao">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.colaboradorAtualizacao">Colaborador Atualizacao</Translate>
            </span>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.colaboradorAtualizacao}</dd>
          <dt>
            <Translate contentKey="vendas2App.lancamentoCarteiraCliente.venda">Venda</Translate>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.venda ? lancamentoCarteiraClienteEntity.venda.id : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.lancamentoCarteiraCliente.pagamento">Pagamento</Translate>
          </dt>
          <dd>{lancamentoCarteiraClienteEntity.pagamento ? lancamentoCarteiraClienteEntity.pagamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lancamento-carteira-cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lancamento-carteira-cliente/${lancamentoCarteiraClienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LancamentoCarteiraClienteDetail;
