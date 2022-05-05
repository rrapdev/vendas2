import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './venda.reducer';

export const VendaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const vendaEntity = useAppSelector(state => state.venda.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vendaDetailsHeading">
          <Translate contentKey="vendas2App.venda.detail.title">Venda</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.id}</dd>
          <dt>
            <span id="dataHora">
              <Translate contentKey="vendas2App.venda.dataHora">Data Hora</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.dataHora ? <TextFormat value={vendaEntity.dataHora} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="valorTotalBruto">
              <Translate contentKey="vendas2App.venda.valorTotalBruto">Valor Total Bruto</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.valorTotalBruto}</dd>
          <dt>
            <span id="valorTotalDesconto">
              <Translate contentKey="vendas2App.venda.valorTotalDesconto">Valor Total Desconto</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.valorTotalDesconto}</dd>
          <dt>
            <span id="valorTotalLiquido">
              <Translate contentKey="vendas2App.venda.valorTotalLiquido">Valor Total Liquido</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.valorTotalLiquido}</dd>
          <dt>
            <span id="valorTotalPago">
              <Translate contentKey="vendas2App.venda.valorTotalPago">Valor Total Pago</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.valorTotalPago}</dd>
          <dt>
            <span id="valorSaldoRestante">
              <Translate contentKey="vendas2App.venda.valorSaldoRestante">Valor Saldo Restante</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.valorSaldoRestante}</dd>
          <dt>
            <span id="observarcoes">
              <Translate contentKey="vendas2App.venda.observarcoes">Observarcoes</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.observarcoes}</dd>
          <dt>
            <span id="indicadorPossuiPagamentoPendente">
              <Translate contentKey="vendas2App.venda.indicadorPossuiPagamentoPendente">Indicador Possui Pagamento Pendente</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.indicadorPossuiPagamentoPendente ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicadorPossuiItemPresente">
              <Translate contentKey="vendas2App.venda.indicadorPossuiItemPresente">Indicador Possui Item Presente</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.indicadorPossuiItemPresente ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicadorBloqueio">
              <Translate contentKey="vendas2App.venda.indicadorBloqueio">Indicador Bloqueio</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.indicadorBloqueio ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataHoraCadastro">
              <Translate contentKey="vendas2App.venda.dataHoraCadastro">Data Hora Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {vendaEntity.dataHoraCadastro ? <TextFormat value={vendaEntity.dataHoraCadastro} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="colaboradorCadastro">
              <Translate contentKey="vendas2App.venda.colaboradorCadastro">Colaborador Cadastro</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.colaboradorCadastro}</dd>
          <dt>
            <span id="dataHoraAtualizacao">
              <Translate contentKey="vendas2App.venda.dataHoraAtualizacao">Data Hora Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {vendaEntity.dataHoraAtualizacao ? (
              <TextFormat value={vendaEntity.dataHoraAtualizacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorAtualizacao">
              <Translate contentKey="vendas2App.venda.colaboradorAtualizacao">Colaborador Atualizacao</Translate>
            </span>
          </dt>
          <dd>{vendaEntity.colaboradorAtualizacao}</dd>
          <dt>
            <Translate contentKey="vendas2App.venda.clienteQueComprou">Cliente Que Comprou</Translate>
          </dt>
          <dd>{vendaEntity.clienteQueComprou ? vendaEntity.clienteQueComprou.nomeCompleto : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.venda.lancamentoCarteiraCliente">Lancamento Carteira Cliente</Translate>
          </dt>
          <dd>{vendaEntity.lancamentoCarteiraCliente ? vendaEntity.lancamentoCarteiraCliente.descricaoLancamento : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.venda.colaboradoresQueIndicaram">Colaboradores Que Indicaram</Translate>
          </dt>
          <dd>
            {vendaEntity.colaboradoresQueIndicarams
              ? vendaEntity.colaboradoresQueIndicarams.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nomeApresentacao}</a>
                    {vendaEntity.colaboradoresQueIndicarams && i === vendaEntity.colaboradoresQueIndicarams.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="vendas2App.venda.itensVenda">Itens Venda</Translate>
          </dt>
          <dd>
            {vendaEntity.itensVendas
              ? vendaEntity.itensVendas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.quantidade}</a>
                    {vendaEntity.itensVendas && i === vendaEntity.itensVendas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="vendas2App.venda.pagamentos">Pagamentos</Translate>
          </dt>
          <dd>
            {vendaEntity.pagamentos
              ? vendaEntity.pagamentos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.valor}</a>
                    {vendaEntity.pagamentos && i === vendaEntity.pagamentos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/venda" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venda/${vendaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VendaDetail;
