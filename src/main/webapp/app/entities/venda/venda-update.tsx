import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntities as getColaboradors } from 'app/entities/colaborador/colaborador.reducer';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { getEntities as getItemVendas } from 'app/entities/item-venda/item-venda.reducer';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { getEntities as getPagamentos } from 'app/entities/pagamento/pagamento.reducer';
import { IVenda } from 'app/shared/model/venda.model';
import { getEntity, updateEntity, createEntity, reset } from './venda.reducer';

export const VendaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clientes = useAppSelector(state => state.cliente.entities);
  const colaboradors = useAppSelector(state => state.colaborador.entities);
  const itemVendas = useAppSelector(state => state.itemVenda.entities);
  const pagamentos = useAppSelector(state => state.pagamento.entities);
  const vendaEntity = useAppSelector(state => state.venda.entity);
  const loading = useAppSelector(state => state.venda.loading);
  const updating = useAppSelector(state => state.venda.updating);
  const updateSuccess = useAppSelector(state => state.venda.updateSuccess);
  const handleClose = () => {
    props.history.push('/venda' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClientes({}));
    dispatch(getColaboradors({}));
    dispatch(getItemVendas({}));
    dispatch(getPagamentos({}));
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
      ...vendaEntity,
      ...values,
      colaboradoresQueIndicarams: mapIdList(values.colaboradoresQueIndicarams),
      itensVendas: mapIdList(values.itensVendas),
      pagamentos: mapIdList(values.pagamentos),
      clienteQueComprou: clientes.find(it => it.id.toString() === values.clienteQueComprou.toString()),
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
          ...vendaEntity,
          dataHora: convertDateTimeFromServer(vendaEntity.dataHora),
          dataHoraCadastro: convertDateTimeFromServer(vendaEntity.dataHoraCadastro),
          dataHoraAtualizacao: convertDateTimeFromServer(vendaEntity.dataHoraAtualizacao),
          clienteQueComprou: vendaEntity?.clienteQueComprou?.id,
          colaboradoresQueIndicarams: vendaEntity?.colaboradoresQueIndicarams?.map(e => e.id.toString()),
          itensVendas: vendaEntity?.itensVendas?.map(e => e.id.toString()),
          pagamentos: vendaEntity?.pagamentos?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.venda.home.createOrEditLabel" data-cy="VendaCreateUpdateHeading">
            <Translate contentKey="vendas2App.venda.home.createOrEditLabel">Create or edit a Venda</Translate>
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
                  id="venda-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.venda.dataHora')}
                id="venda-dataHora"
                name="dataHora"
                data-cy="dataHora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.venda.valorTotalBruto')}
                id="venda-valorTotalBruto"
                name="valorTotalBruto"
                data-cy="valorTotalBruto"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.valorTotalDesconto')}
                id="venda-valorTotalDesconto"
                name="valorTotalDesconto"
                data-cy="valorTotalDesconto"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.valorTotalLiquido')}
                id="venda-valorTotalLiquido"
                name="valorTotalLiquido"
                data-cy="valorTotalLiquido"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.valorTotalPago')}
                id="venda-valorTotalPago"
                name="valorTotalPago"
                data-cy="valorTotalPago"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.valorSaldoRestante')}
                id="venda-valorSaldoRestante"
                name="valorSaldoRestante"
                data-cy="valorSaldoRestante"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.observarcoes')}
                id="venda-observarcoes"
                name="observarcoes"
                data-cy="observarcoes"
                type="textarea"
              />
              <ValidatedField
                label={translate('vendas2App.venda.indicadorPossuiPagamentoPendente')}
                id="venda-indicadorPossuiPagamentoPendente"
                name="indicadorPossuiPagamentoPendente"
                data-cy="indicadorPossuiPagamentoPendente"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.venda.indicadorPossuiItemPresente')}
                id="venda-indicadorPossuiItemPresente"
                name="indicadorPossuiItemPresente"
                data-cy="indicadorPossuiItemPresente"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.venda.indicadorBloqueio')}
                id="venda-indicadorBloqueio"
                name="indicadorBloqueio"
                data-cy="indicadorBloqueio"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.venda.dataHoraCadastro')}
                id="venda-dataHoraCadastro"
                name="dataHoraCadastro"
                data-cy="dataHoraCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.venda.colaboradorCadastro')}
                id="venda-colaboradorCadastro"
                name="colaboradorCadastro"
                data-cy="colaboradorCadastro"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.venda.dataHoraAtualizacao')}
                id="venda-dataHoraAtualizacao"
                name="dataHoraAtualizacao"
                data-cy="dataHoraAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.venda.colaboradorAtualizacao')}
                id="venda-colaboradorAtualizacao"
                name="colaboradorAtualizacao"
                data-cy="colaboradorAtualizacao"
                type="text"
              />
              <ValidatedField
                id="venda-clienteQueComprou"
                name="clienteQueComprou"
                data-cy="clienteQueComprou"
                label={translate('vendas2App.venda.clienteQueComprou')}
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
              <ValidatedField
                label={translate('vendas2App.venda.colaboradoresQueIndicaram')}
                id="venda-colaboradoresQueIndicaram"
                data-cy="colaboradoresQueIndicaram"
                type="select"
                multiple
                name="colaboradoresQueIndicarams"
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
                label={translate('vendas2App.venda.itensVenda')}
                id="venda-itensVenda"
                data-cy="itensVenda"
                type="select"
                multiple
                name="itensVendas"
              >
                <option value="" key="0" />
                {itemVendas
                  ? itemVendas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.quantidade}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('vendas2App.venda.pagamentos')}
                id="venda-pagamentos"
                data-cy="pagamentos"
                type="select"
                multiple
                name="pagamentos"
              >
                <option value="" key="0" />
                {pagamentos
                  ? pagamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.valor}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/venda" replace color="info">
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

export default VendaUpdate;
