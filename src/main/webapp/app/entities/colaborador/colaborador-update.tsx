import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVenda } from 'app/shared/model/venda.model';
import { getEntities as getVendas } from 'app/entities/venda/venda.reducer';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntity, updateEntity, createEntity, reset } from './colaborador.reducer';

export const ColaboradorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const vendas = useAppSelector(state => state.venda.entities);
  const colaboradorEntity = useAppSelector(state => state.colaborador.entity);
  const loading = useAppSelector(state => state.colaborador.loading);
  const updating = useAppSelector(state => state.colaborador.updating);
  const updateSuccess = useAppSelector(state => state.colaborador.updateSuccess);
  const handleClose = () => {
    props.history.push('/colaborador' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getVendas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...colaboradorEntity,
      ...values,
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
          ...colaboradorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.colaborador.home.createOrEditLabel" data-cy="ColaboradorCreateUpdateHeading">
            <Translate contentKey="vendas2App.colaborador.home.createOrEditLabel">Create or edit a Colaborador</Translate>
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
                  id="colaborador-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.colaborador.nomeColaborador')}
                id="colaborador-nomeColaborador"
                name="nomeColaborador"
                data-cy="nomeColaborador"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.colaborador.nomeApresentacao')}
                id="colaborador-nomeApresentacao"
                name="nomeApresentacao"
                data-cy="nomeApresentacao"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.colaborador.indicadorAtivo')}
                id="colaborador-indicadorAtivo"
                name="indicadorAtivo"
                data-cy="indicadorAtivo"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/colaborador" replace color="info">
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

export default ColaboradorUpdate;
