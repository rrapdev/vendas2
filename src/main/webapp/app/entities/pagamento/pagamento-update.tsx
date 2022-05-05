import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlataformaPagamento } from 'app/shared/model/plataforma-pagamento.model';
import { getEntities as getPlataformaPagamentos } from 'app/entities/plataforma-pagamento/plataforma-pagamento.reducer';
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { getEntities as getLancamentoCarteiraClientes } from 'app/entities/lancamento-carteira-cliente/lancamento-carteira-cliente.reducer';
import { IVenda } from 'app/shared/model/venda.model';
import { getEntities as getVendas } from 'app/entities/venda/venda.reducer';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { FormaPagamento } from 'app/shared/model/enumerations/forma-pagamento.model';
import { BandeiraCartao } from 'app/shared/model/enumerations/bandeira-cartao.model';
import { getEntity, updateEntity, createEntity, reset } from './pagamento.reducer';

export const PagamentoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plataformaPagamentos = useAppSelector(state => state.plataformaPagamento.entities);
  const lancamentoCarteiraClientes = useAppSelector(state => state.lancamentoCarteiraCliente.entities);
  const vendas = useAppSelector(state => state.venda.entities);
  const pagamentoEntity = useAppSelector(state => state.pagamento.entity);
  const loading = useAppSelector(state => state.pagamento.loading);
  const updating = useAppSelector(state => state.pagamento.updating);
  const updateSuccess = useAppSelector(state => state.pagamento.updateSuccess);
  const formaPagamentoValues = Object.keys(FormaPagamento);
  const bandeiraCartaoValues = Object.keys(BandeiraCartao);
  const handleClose = () => {
    props.history.push('/pagamento' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPlataformaPagamentos({}));
    dispatch(getLancamentoCarteiraClientes({}));
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
      ...pagamentoEntity,
      ...values,
      plataformaPagamento: plataformaPagamentos.find(it => it.id.toString() === values.plataformaPagamento.toString()),
      lancamentoCarteiraCliente: lancamentoCarteiraClientes.find(it => it.id.toString() === values.lancamentoCarteiraCliente.toString()),
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
          formaPagamento: 'DINHEIRO',
          bandeiraCartao: 'MASTER',
          ...pagamentoEntity,
          dataHora: convertDateTimeFromServer(pagamentoEntity.dataHora),
          dataHoraCadastro: convertDateTimeFromServer(pagamentoEntity.dataHoraCadastro),
          dataHoraAtualizacao: convertDateTimeFromServer(pagamentoEntity.dataHoraAtualizacao),
          plataformaPagamento: pagamentoEntity?.plataformaPagamento?.id,
          lancamentoCarteiraCliente: pagamentoEntity?.lancamentoCarteiraCliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendas2App.pagamento.home.createOrEditLabel" data-cy="PagamentoCreateUpdateHeading">
            <Translate contentKey="vendas2App.pagamento.home.createOrEditLabel">Create or edit a Pagamento</Translate>
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
                  id="pagamento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('vendas2App.pagamento.formaPagamento')}
                id="pagamento-formaPagamento"
                name="formaPagamento"
                data-cy="formaPagamento"
                type="select"
              >
                {formaPagamentoValues.map(formaPagamento => (
                  <option value={formaPagamento} key={formaPagamento}>
                    {translate('vendas2App.FormaPagamento.' + formaPagamento)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('vendas2App.pagamento.dataHora')}
                id="pagamento-dataHora"
                name="dataHora"
                data-cy="dataHora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.valor')}
                id="pagamento-valor"
                name="valor"
                data-cy="valor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.observacoes')}
                id="pagamento-observacoes"
                name="observacoes"
                data-cy="observacoes"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.numeroParcelas')}
                id="pagamento-numeroParcelas"
                name="numeroParcelas"
                data-cy="numeroParcelas"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.bandeiraCartao')}
                id="pagamento-bandeiraCartao"
                name="bandeiraCartao"
                data-cy="bandeiraCartao"
                type="select"
              >
                {bandeiraCartaoValues.map(bandeiraCartao => (
                  <option value={bandeiraCartao} key={bandeiraCartao}>
                    {translate('vendas2App.BandeiraCartao.' + bandeiraCartao)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('vendas2App.pagamento.clienteOrigemPagamento')}
                id="pagamento-clienteOrigemPagamento"
                name="clienteOrigemPagamento"
                data-cy="clienteOrigemPagamento"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.indicadorConferido')}
                id="pagamento-indicadorConferido"
                name="indicadorConferido"
                data-cy="indicadorConferido"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.dataHoraCadastro')}
                id="pagamento-dataHoraCadastro"
                name="dataHoraCadastro"
                data-cy="dataHoraCadastro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.colaboradorCadastro')}
                id="pagamento-colaboradorCadastro"
                name="colaboradorCadastro"
                data-cy="colaboradorCadastro"
                type="text"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.dataHoraAtualizacao')}
                id="pagamento-dataHoraAtualizacao"
                name="dataHoraAtualizacao"
                data-cy="dataHoraAtualizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('vendas2App.pagamento.colaboradorAtualizacao')}
                id="pagamento-colaboradorAtualizacao"
                name="colaboradorAtualizacao"
                data-cy="colaboradorAtualizacao"
                type="text"
              />
              <ValidatedField
                id="pagamento-plataformaPagamento"
                name="plataformaPagamento"
                data-cy="plataformaPagamento"
                label={translate('vendas2App.pagamento.plataformaPagamento')}
                type="select"
              >
                <option value="" key="0" />
                {plataformaPagamentos
                  ? plataformaPagamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nomePlataformaPagamento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="pagamento-lancamentoCarteiraCliente"
                name="lancamentoCarteiraCliente"
                data-cy="lancamentoCarteiraCliente"
                label={translate('vendas2App.pagamento.lancamentoCarteiraCliente')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pagamento" replace color="info">
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

export default PagamentoUpdate;
