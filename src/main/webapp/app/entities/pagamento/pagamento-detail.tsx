import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pagamento.reducer';

export const PagamentoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pagamentoEntity = useAppSelector(state => state.pagamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pagamentoDetailsHeading">
          <Translate contentKey="vendas2App.pagamento.detail.title">Pagamento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.id}</dd>
          <dt>
            <span id="formaPagamento">
              <Translate contentKey="vendas2App.pagamento.formaPagamento">Forma Pagamento</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.formaPagamento}</dd>
          <dt>
            <span id="dataHora">
              <Translate contentKey="vendas2App.pagamento.dataHora">Data Hora</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.dataHora ? <TextFormat value={pagamentoEntity.dataHora} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="valor">
              <Translate contentKey="vendas2App.pagamento.valor">Valor</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.valor}</dd>
          <dt>
            <span id="observacoes">
              <Translate contentKey="vendas2App.pagamento.observacoes">Observacoes</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.observacoes}</dd>
          <dt>
            <span id="numeroParcelas">
              <Translate contentKey="vendas2App.pagamento.numeroParcelas">Numero Parcelas</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.numeroParcelas}</dd>
          <dt>
            <span id="bandeiraCartao">
              <Translate contentKey="vendas2App.pagamento.bandeiraCartao">Bandeira Cartao</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.bandeiraCartao}</dd>
          <dt>
            <span id="clienteOrigemPagamento">
              <Translate contentKey="vendas2App.pagamento.clienteOrigemPagamento">Cliente Origem Pagamento</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.clienteOrigemPagamento}</dd>
          <dt>
            <span id="indicadorConferido">
              <Translate contentKey="vendas2App.pagamento.indicadorConferido">Indicador Conferido</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.indicadorConferido ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataHoraCadastro">
              <Translate contentKey="vendas2App.pagamento.dataHoraCadastro">Data Hora Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {pagamentoEntity.dataHoraCadastro ? (
              <TextFormat value={pagamentoEntity.dataHoraCadastro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorCadastro">
              <Translate contentKey="vendas2App.pagamento.colaboradorCadastro">Colaborador Cadastro</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.colaboradorCadastro}</dd>
          <dt>
            <span id="dataHoraAtualizacao">
              <Translate contentKey="vendas2App.pagamento.dataHoraAtualizacao">Data Hora Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {pagamentoEntity.dataHoraAtualizacao ? (
              <TextFormat value={pagamentoEntity.dataHoraAtualizacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorAtualizacao">
              <Translate contentKey="vendas2App.pagamento.colaboradorAtualizacao">Colaborador Atualizacao</Translate>
            </span>
          </dt>
          <dd>{pagamentoEntity.colaboradorAtualizacao}</dd>
          <dt>
            <Translate contentKey="vendas2App.pagamento.plataformaPagamento">Plataforma Pagamento</Translate>
          </dt>
          <dd>{pagamentoEntity.plataformaPagamento ? pagamentoEntity.plataformaPagamento.nomePlataformaPagamento : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.pagamento.lancamentoCarteiraCliente">Lancamento Carteira Cliente</Translate>
          </dt>
          <dd>{pagamentoEntity.lancamentoCarteiraCliente ? pagamentoEntity.lancamentoCarteiraCliente.descricaoLancamento : ''}</dd>
        </dl>
        <Button tag={Link} to="/pagamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pagamento/${pagamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PagamentoDetail;
