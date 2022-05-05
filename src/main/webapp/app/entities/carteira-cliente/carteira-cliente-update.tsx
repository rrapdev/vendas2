import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { getEntities as getLancamentoCarteiraClientes } from 'app/entities/lancamento-carteira-cliente/lancamento-carteira-cliente.reducer';
import { ICarteiraCliente } from 'app/shared/model/carteira-cliente.model';
import { TipoIndicadorSaldo } from 'app/shared/model/enumerations/tipo-indicador-saldo.model';
import { getEntity, updateEntity, createEntity, reset } from './carteira-cliente.reducer';

export const CarteiraClienteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const lancamentoCarteiraClientes = useAppSelector(state => state.lancamentoCarteiraCliente.entities);
  const carteiraClienteEntity = useAppSelector(state => state.carteiraCliente.entity);
  const loading = useAppSelector(state => state.carteiraCliente.loading);
  const updating = useAppSelector(state => state.carteiraCliente.updating);
  const updateSuccess = useAppSelector(state => state.carteiraCliente.updateSuccess);
  const tipoIndicadorSaldoValues = Object.keys(TipoIndicadorSaldo);
  const handleClose = () => {
    props.history.push('/carteira-cliente' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLancamentoCarteiraClientes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dataHoraCadastro = convertDateTimeToServer(values.dataHoraCadastro);
    values.dataHoraAtualizacao = convertDateTimeToServer(values.dataHoraAtualizacao);

    const entity = {
      ...carteiraClienteEntity,
      ...values,
      lancamentoCarteiraClientes: mapIdList(values.lancamentoCarteiraClientes),
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
          dataHoraCadastro: displayDefaultDateTime(),
          dataHoraAtualizacao: displayDefaultDateTime(),
        }
      : {
          tipoIndicadorSaldo: 'POSITIVO',
          ...carteiraClienteEntity,
          dataHoraCadastro: convertDateTimeFromServer(carteiraClienteEntity.dataHoraCadastro),
          dataHoraAtualizacao: convertDateTimeFromServer(carteiraClienteEntity.dataHoraAtualizacao),
          lancamentoCarteiraClientes: carteiraClienteEntity?.lancamentoCarteiraClientes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.carteiraCliente.home.createOrEditLabel" data-cy="CarteiraClienteCreateUpdateHeading">
            <Translate contentKey="vendas2App.carteiraCliente.home.createOrEditLabel">Create or edit a CarteiraCliente</Translate>
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
                  id="carteira-cliente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.saldoConsolidado')}
                id="carteira-cliente-saldoConsolidado"
                name="saldoConsolidado"
                data-cy="saldoConsolidado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.tipoIndicadorSaldo')}
                id="carteira-cliente-tipoIndicadorSaldo"
                name="tipoIndicadorSaldo"
                data-cy="tipoIndicadorSaldo"
                type="select"
              >
                {tipoIndicadorSaldoValues.map(tipoIndicadorSaldo => (
                  <option value={tipoIndicadorSaldo} key={tipoIndicadorSaldo}>
                    {translate('vendas2App.TipoIndicadorSaldo.' + tipoIndicadorSaldo)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.indicadorBloqueio')}
                id="carteira-cliente-indicadorBloqueio"
                name="indicadorBloqueio"
                data-cy="indicadorBloqueio"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.dataHoraCadastro')}
                id="carteira-cliente-dataHoraCadastro"
                name="dataHoraCadastro"
                data-cy="dataHoraCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.colaboradorCadastro')}
                id="carteira-cliente-colaboradorCadastro"
                name="colaboradorCadastro"
                data-cy="colaboradorCadastro"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.dataHoraAtualizacao')}
                id="carteira-cliente-dataHoraAtualizacao"
                name="dataHoraAtualizacao"
                data-cy="dataHoraAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.colaboradorAtualizacao')}
                id="carteira-cliente-colaboradorAtualizacao"
                name="colaboradorAtualizacao"
                data-cy="colaboradorAtualizacao"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.carteiraCliente.lancamentoCarteiraCliente')}
                id="carteira-cliente-lancamentoCarteiraCliente"
                data-cy="lancamentoCarteiraCliente"
                type="select"
                multiple
                name="lancamentoCarteiraClientes"
              >
                <option value="" key="0" />
                {lancamentoCarteiraClientes
                  ? lancamentoCarteiraClientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.descricaoLancamento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carteira-cliente" replace color="info">
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

export default CarteiraClienteUpdate;
