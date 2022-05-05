import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item-venda.reducer';

export const ItemVendaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const itemVendaEntity = useAppSelector(state => state.itemVenda.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemVendaDetailsHeading">
          <Translate contentKey="vendas2App.itemVenda.detail.title">ItemVenda</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.id}</dd>
          <dt>
            <span id="dataHora">
              <Translate contentKey="vendas2App.itemVenda.dataHora">Data Hora</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.dataHora ? <TextFormat value={itemVendaEntity.dataHora} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="quantidade">
              <Translate contentKey="vendas2App.itemVenda.quantidade">Quantidade</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.quantidade}</dd>
          <dt>
            <span id="valorUnitario">
              <Translate contentKey="vendas2App.itemVenda.valorUnitario">Valor Unitario</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.valorUnitario}</dd>
          <dt>
            <span id="valorTotal">
              <Translate contentKey="vendas2App.itemVenda.valorTotal">Valor Total</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.valorTotal}</dd>
          <dt>
            <span id="valorDescontoPercentual">
              <Translate contentKey="vendas2App.itemVenda.valorDescontoPercentual">Valor Desconto Percentual</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.valorDescontoPercentual}</dd>
          <dt>
            <span id="valorDescontoReal">
              <Translate contentKey="vendas2App.itemVenda.valorDescontoReal">Valor Desconto Real</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.valorDescontoReal}</dd>
          <dt>
            <span id="valorTotalComDesconto">
              <Translate contentKey="vendas2App.itemVenda.valorTotalComDesconto">Valor Total Com Desconto</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.valorTotalComDesconto}</dd>
          <dt>
            <span id="indicadorItemPresente">
              <Translate contentKey="vendas2App.itemVenda.indicadorItemPresente">Indicador Item Presente</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.indicadorItemPresente ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataHoraCadastro">
              <Translate contentKey="vendas2App.itemVenda.dataHoraCadastro">Data Hora Cadastro</Translate>
            </span>
          </dt>
          <dd>
            {itemVendaEntity.dataHoraCadastro ? (
              <TextFormat value={itemVendaEntity.dataHoraCadastro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorCadastro">
              <Translate contentKey="vendas2App.itemVenda.colaboradorCadastro">Colaborador Cadastro</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.colaboradorCadastro}</dd>
          <dt>
            <span id="dataHoraAtualizacao">
              <Translate contentKey="vendas2App.itemVenda.dataHoraAtualizacao">Data Hora Atualizacao</Translate>
            </span>
          </dt>
          <dd>
            {itemVendaEntity.dataHoraAtualizacao ? (
              <TextFormat value={itemVendaEntity.dataHoraAtualizacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="colaboradorAtualizacao">
              <Translate contentKey="vendas2App.itemVenda.colaboradorAtualizacao">Colaborador Atualizacao</Translate>
            </span>
          </dt>
          <dd>{itemVendaEntity.colaboradorAtualizacao}</dd>
          <dt>
            <Translate contentKey="vendas2App.itemVenda.servico">Servico</Translate>
          </dt>
          <dd>{itemVendaEntity.servico ? itemVendaEntity.servico.nomeServico : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.itemVenda.colaboradorQueIndicou">Colaborador Que Indicou</Translate>
          </dt>
          <dd>{itemVendaEntity.colaboradorQueIndicou ? itemVendaEntity.colaboradorQueIndicou.nomeApresentacao : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.itemVenda.clienteQueVaiRealizar">Cliente Que Vai Realizar</Translate>
          </dt>
          <dd>{itemVendaEntity.clienteQueVaiRealizar ? itemVendaEntity.clienteQueVaiRealizar.nomeCompleto : ''}</dd>
          <dt>
            <Translate contentKey="vendas2App.itemVenda.venda">Venda</Translate>
          </dt>
          <dd>{itemVendaEntity.venda ? itemVendaEntity.venda.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item-venda" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item-venda/${itemVendaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemVendaDetail;
