import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlataformaPagamento } from 'app/shared/model/plataforma-pagamento.model';
import { getEntity, updateEntity, createEntity, reset } from './plataforma-pagamento.reducer';

export const PlataformaPagamentoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plataformaPagamentoEntity = useAppSelector(state => state.plataformaPagamento.entity);
  const loading = useAppSelector(state => state.plataformaPagamento.loading);
  const updating = useAppSelector(state => state.plataformaPagamento.updating);
  const updateSuccess = useAppSelector(state => state.plataformaPagamento.updateSuccess);
  const handleClose = () => {
    props.history.push('/plataforma-pagamento' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...plataformaPagamentoEntity,
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
          ...plataformaPagamentoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.plataformaPagamento.home.createOrEditLabel" data-cy="PlataformaPagamentoCreateUpdateHeading">
            <Translate contentKey="vendas2App.plataformaPagamento.home.createOrEditLabel">Create or edit a PlataformaPagamento</Translate>
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
                  id="plataforma-pagamento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.plataformaPagamento.nomePlataformaPagamento')}
                id="plataforma-pagamento-nomePlataformaPagamento"
                name="nomePlataformaPagamento"
                data-cy="nomePlataformaPagamento"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.plataformaPagamento.indicadorAtivo')}
                id="plataforma-pagamento-indicadorAtivo"
                name="indicadorAtivo"
                data-cy="indicadorAtivo"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plataforma-pagamento" replace color="info">
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

export default PlataformaPagamentoUpdate;
