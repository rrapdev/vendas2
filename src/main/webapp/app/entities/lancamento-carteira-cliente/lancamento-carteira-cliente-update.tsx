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
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { getEntity, updateEntity, createEntity, reset } from './lancamento-carteira-cliente.reducer';

export const LancamentoCarteiraClienteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const carteiraClientes = useAppSelector(state => state.carteiraCliente.entities);
  const lancamentoCarteiraClienteEntity = useAppSelector(state => state.lancamentoCarteiraCliente.entity);
  const loading = useAppSelector(state => state.lancamentoCarteiraCliente.loading);
  const updating = useAppSelector(state => state.lancamentoCarteiraCliente.updating);
  const updateSuccess = useAppSelector(state => state.lancamentoCarteiraCliente.updateSuccess);
  const handleClose = () => {
    props.history.push('/lancamento-carteira-cliente' + props.location.search);
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
    values.dataHora = convertDateTimeToServer(values.dataHora);
    values.dataHoraCadastro = convertDateTimeToServer(values.dataHoraCadastro);
    values.dataHoraAtualizacao = convertDateTimeToServer(values.dataHoraAtualizacao);

    const entity = {
      ...lancamentoCarteiraClienteEntity,
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
      ? {
          dataHora: displayDefaultDateTime(),
          dataHoraCadastro: displayDefaultDateTime(),
          dataHoraAtualizacao: displayDefaultDateTime(),
        }
      : {
          ...lancamentoCarteiraClienteEntity,
          dataHora: convertDateTimeFromServer(lancamentoCarteiraClienteEntity.dataHora),
          dataHoraCadastro: convertDateTimeFromServer(lancamentoCarteiraClienteEntity.dataHoraCadastro),
          dataHoraAtualizacao: convertDateTimeFromServer(lancamentoCarteiraClienteEntity.dataHoraAtualizacao),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.lancamentoCarteiraCliente.home.createOrEditLabel" data-cy="LancamentoCarteiraClienteCreateUpdateHeading">
            <Translate contentKey="vendas2App.lancamentoCarteiraCliente.home.createOrEditLabel">
              Create or edit a LancamentoCarteiraCliente
            </Translate>
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
                  id="lancamento-carteira-cliente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.dataHora')}
                id="lancamento-carteira-cliente-dataHora"
                name="dataHora"
                data-cy="dataHora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.descricaoLancamento')}
                id="lancamento-carteira-cliente-descricaoLancamento"
                name="descricaoLancamento"
                data-cy="descricaoLancamento"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.valorCredito')}
                id="lancamento-carteira-cliente-valorCredito"
                name="valorCredito"
                data-cy="valorCredito"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.valorDebito')}
                id="lancamento-carteira-cliente-valorDebito"
                name="valorDebito"
                data-cy="valorDebito"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.observacoes')}
                id="lancamento-carteira-cliente-observacoes"
                name="observacoes"
                data-cy="observacoes"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.indicadorBloqueio')}
                id="lancamento-carteira-cliente-indicadorBloqueio"
                name="indicadorBloqueio"
                data-cy="indicadorBloqueio"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.dataHoraCadastro')}
                id="lancamento-carteira-cliente-dataHoraCadastro"
                name="dataHoraCadastro"
                data-cy="dataHoraCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.colaboradorCadastro')}
                id="lancamento-carteira-cliente-colaboradorCadastro"
                name="colaboradorCadastro"
                data-cy="colaboradorCadastro"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.dataHoraAtualizacao')}
                id="lancamento-carteira-cliente-dataHoraAtualizacao"
                name="dataHoraAtualizacao"
                data-cy="dataHoraAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.lancamentoCarteiraCliente.colaboradorAtualizacao')}
                id="lancamento-carteira-cliente-colaboradorAtualizacao"
                name="colaboradorAtualizacao"
                data-cy="colaboradorAtualizacao"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lancamento-carteira-cliente" replace color="info">
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

export default LancamentoCarteiraClienteUpdate;
