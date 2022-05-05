import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { getEntities } from './lancamento-carteira-cliente.reducer';

export const LancamentoCarteiraCliente = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const lancamentoCarteiraClienteList = useAppSelector(state => state.lancamentoCarteiraCliente.entities);
  const loading = useAppSelector(state => state.lancamentoCarteiraCliente.loading);
  const totalItems = useAppSelector(state => state.lancamentoCarteiraCliente.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="lancamento-carteira-cliente-heading" data-cy="LancamentoCarteiraClienteHeading">
        <Translate contentKey="vendas2App.lancamentoCarteiraCliente.home.title">Lancamento Carteira Clientes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="vendas2App.lancamentoCarteiraCliente.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/lancamento-carteira-cliente/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="vendas2App.lancamentoCarteiraCliente.home.createLabel">Create new Lancamento Carteira Cliente</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lancamentoCarteiraClienteList && lancamentoCarteiraClienteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHora')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHora">Data Hora</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descricaoLancamento')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.descricaoLancamento">Descricao Lancamento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorCredito')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.valorCredito">Valor Credito</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorDebito')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.valorDebito">Valor Debito</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('observacoes')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.observacoes">Observacoes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorBloqueio')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.indicadorBloqueio">Indicador Bloqueio</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraCadastro')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHoraCadastro">Data Hora Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorCadastro')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.colaboradorCadastro">Colaborador Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraAtualizacao')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.dataHoraAtualizacao">Data Hora Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorAtualizacao')}>
                  <Translate contentKey="vendas2App.lancamentoCarteiraCliente.colaboradorAtualizacao">Colaborador Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lancamentoCarteiraClienteList.map((lancamentoCarteiraCliente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lancamento-carteira-cliente/${lancamentoCarteiraCliente.id}`} color="link" size="sm">
                      {lancamentoCarteiraCliente.id}
                    </Button>
                  </td>
                  <td>
                    {lancamentoCarteiraCliente.dataHora ? (
                      <TextFormat type="date" value={lancamentoCarteiraCliente.dataHora} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{lancamentoCarteiraCliente.descricaoLancamento}</td>
                  <td>{lancamentoCarteiraCliente.valorCredito}</td>
                  <td>{lancamentoCarteiraCliente.valorDebito}</td>
                  <td>{lancamentoCarteiraCliente.observacoes}</td>
                  <td>{lancamentoCarteiraCliente.indicadorBloqueio ? 'true' : 'false'}</td>
                  <td>
                    {lancamentoCarteiraCliente.dataHoraCadastro ? (
                      <TextFormat type="date" value={lancamentoCarteiraCliente.dataHoraCadastro} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{lancamentoCarteiraCliente.colaboradorCadastro}</td>
                  <td>
                    {lancamentoCarteiraCliente.dataHoraAtualizacao ? (
                      <TextFormat type="date" value={lancamentoCarteiraCliente.dataHoraAtualizacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{lancamentoCarteiraCliente.colaboradorAtualizacao}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/lancamento-carteira-cliente/${lancamentoCarteiraCliente.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lancamento-carteira-cliente/${lancamentoCarteiraCliente.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lancamento-carteira-cliente/${lancamentoCarteiraCliente.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="vendas2App.lancamentoCarteiraCliente.home.notFound">No Lancamento Carteira Clientes found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={lancamentoCarteiraClienteList && lancamentoCarteiraClienteList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default LancamentoCarteiraCliente;
