import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServico } from 'app/shared/model/servico.model';
import { getEntities as getServicos } from 'app/entities/servico/servico.reducer';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntities as getColaboradors } from 'app/entities/colaborador/colaborador.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IVenda } from 'app/shared/model/venda.model';
import { getEntities as getVendas } from 'app/entities/venda/venda.reducer';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { getEntity, updateEntity, createEntity, reset } from './item-venda.reducer';

export const ItemVendaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servicos = useAppSelector(state => state.servico.entities);
  const colaboradors = useAppSelector(state => state.colaborador.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const vendas = useAppSelector(state => state.venda.entities);
  const itemVendaEntity = useAppSelector(state => state.itemVenda.entity);
  const loading = useAppSelector(state => state.itemVenda.loading);
  const updating = useAppSelector(state => state.itemVenda.updating);
  const updateSuccess = useAppSelector(state => state.itemVenda.updateSuccess);
  const handleClose = () => {
    props.history.push('/item-venda' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServicos({}));
    dispatch(getColaboradors({}));
    dispatch(getClientes({}));
    dispatch(getVendas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataHora = convertDateTimeToServer(values.dataHora);
    values.dataHoraCadastro = convertDateTimeToServer(values.dataHoraCadastro);
    values.dataHoraAtualizacao = convertDateTimeToServer(values.dataHoraAtualizacao);

    const entity = {
      ...itemVendaEntity,
      ...values,
      servico: servicos.find(it => it.id.toString() === values.servico.toString()),
      colaboradorQueIndicou: colaboradors.find(it => it.id.toString() === values.colaboradorQueIndicou.toString()),
      clienteQueVaiRealizar: clientes.find(it => it.id.toString() === values.clienteQueVaiRealizar.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dataHora: displayDefaultDateTime(),
          dataHoraCadastro: displayDefaultDateTime(),
          dataHoraAtualizacao: displayDefaultDateTime(),
        }
      : {
          ...itemVendaEntity,
          dataHora: convertDateTimeFromServer(itemVendaEntity.dataHora),
          dataHoraCadastro: convertDateTimeFromServer(itemVendaEntity.dataHoraCadastro),
          dataHoraAtualizacao: convertDateTimeFromServer(itemVendaEntity.dataHoraAtualizacao),
          servico: itemVendaEntity?.servico?.id,
          colaboradorQueIndicou: itemVendaEntity?.colaboradorQueIndicou?.id,
          clienteQueVaiRealizar: itemVendaEntity?.clienteQueVaiRealizar?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.itemVenda.home.createOrEditLabel" data-cy="ItemVendaCreateUpdateHeading">
            <Translate contentKey="vendas2App.itemVenda.home.createOrEditLabel">Create or edit a ItemVenda</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="item-venda-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.itemVenda.dataHora')}
                id="item-venda-dataHora"
                name="dataHora"
                data-cy="dataHora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.quantidade')}
                id="item-venda-quantidade"
                name="quantidade"
                data-cy="quantidade"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.valorUnitario')}
                id="item-venda-valorUnitario"
                name="valorUnitario"
                data-cy="valorUnitario"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.valorTotal')}
                id="item-venda-valorTotal"
                name="valorTotal"
                data-cy="valorTotal"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.valorDescontoPercentual')}
                id="item-venda-valorDescontoPercentual"
                name="valorDescontoPercentual"
                data-cy="valorDescontoPercentual"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 1, message: translate('entity.validation.max', { max: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.valorDescontoReal')}
                id="item-venda-valorDescontoReal"
                name="valorDescontoReal"
                data-cy="valorDescontoReal"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.valorTotalComDesconto')}
                id="item-venda-valorTotalComDesconto"
                name="valorTotalComDesconto"
                data-cy="valorTotalComDesconto"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.indicadorItemPresente')}
                id="item-venda-indicadorItemPresente"
                name="indicadorItemPresente"
                data-cy="indicadorItemPresente"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.dataHoraCadastro')}
                id="item-venda-dataHoraCadastro"
                name="dataHoraCadastro"
                data-cy="dataHoraCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.colaboradorCadastro')}
                id="item-venda-colaboradorCadastro"
                name="colaboradorCadastro"
                data-cy="colaboradorCadastro"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.dataHoraAtualizacao')}
                id="item-venda-dataHoraAtualizacao"
                name="dataHoraAtualizacao"
                data-cy="dataHoraAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.itemVenda.colaboradorAtualizacao')}
                id="item-venda-colaboradorAtualizacao"
                name="colaboradorAtualizacao"
                data-cy="colaboradorAtualizacao"
                type="text"
              />
              <ValidatedField
                id="item-venda-servico"
                name="servico"
                data-cy="servico"
                label={translate('vendas2App.itemVenda.servico')}
                type="select"
              >
                <option value="" key="0" />
                {servicos
                  ? servicos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomeServico}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="item-venda-colaboradorQueIndicou"
                name="colaboradorQueIndicou"
                data-cy="colaboradorQueIndicou"
                label={translate('vendas2App.itemVenda.colaboradorQueIndicou')}
                type="select"
              >
                <option value="" key="0" />
                {colaboradors
                  ? colaboradors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomeApresentacao}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="item-venda-clienteQueVaiRealizar"
                name="clienteQueVaiRealizar"
                data-cy="clienteQueVaiRealizar"
                label={translate('vendas2App.itemVenda.clienteQueVaiRealizar')}
                type="select"
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomeCompleto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item-venda" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ItemVendaUpdate;
