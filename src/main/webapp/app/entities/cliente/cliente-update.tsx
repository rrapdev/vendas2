import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICarteiraCliente } from 'app/shared/model/carteira-cliente.model';
import { getEntities as getCarteiraClientes } from 'app/entities/carteira-cliente/carteira-cliente.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntity, updateEntity, createEntity, reset } from './cliente.reducer';

export const ClienteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const carteiraClientes = useAppSelector(state => state.carteiraCliente.entities);
  const clienteEntity = useAppSelector(state => state.cliente.entity);
  const loading = useAppSelector(state => state.cliente.loading);
  const updating = useAppSelector(state => state.cliente.updating);
  const updateSuccess = useAppSelector(state => state.cliente.updateSuccess);
  const handleClose = () => {
    props.history.push('/cliente' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCarteiraClientes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clienteEntity,
      ...values,
      carteiraCliente: carteiraClientes.find(it => it.id.toString() === values.carteiraCliente.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clienteEntity,
          carteiraCliente: clienteEntity?.carteiraCliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.cliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">
            <Translate contentKey="vendas2App.cliente.home.createOrEditLabel">Create or edit a Cliente</Translate>
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
                  id="cliente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.cliente.nomeCompleto')}
                id="cliente-nomeCompleto"
                name="nomeCompleto"
                data-cy="nomeCompleto"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.cliente.telefone')}
                id="cliente-telefone"
                name="telefone"
                data-cy="telefone"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.cliente.nomeApresentacao')}
                id="cliente-nomeApresentacao"
                name="nomeApresentacao"
                data-cy="nomeApresentacao"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.cliente.indicadorAtivo')}
                id="cliente-indicadorAtivo"
                name="indicadorAtivo"
                data-cy="indicadorAtivo"
                check
                type="checkbox"
              />
              <ValidatedField
                id="cliente-carteiraCliente"
                name="carteiraCliente"
                data-cy="carteiraCliente"
                label={translate('vendas2App.cliente.carteiraCliente')}
                type="select"
              >
                <option value="" key="0" />
                {carteiraClientes
                  ? carteiraClientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.saldoConsolidado}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cliente" replace color="info">
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

export default ClienteUpdate;
