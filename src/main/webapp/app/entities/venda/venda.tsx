import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVenda } from 'app/shared/model/venda.model';
import { getEntities } from './venda.reducer';

export const Venda = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const vendaList = useAppSelector(state => state.venda.entities);
  const loading = useAppSelector(state => state.venda.loading);
  const totalItems = useAppSelector(state => state.venda.totalItems);

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
      <h2 id="venda-heading" data-cy="VendaHeading">
        <Translate contentKey="vendas2App.venda.home.title">Vendas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="vendas2App.venda.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/venda/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="vendas2App.venda.home.createLabel">Create new Venda</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vendaList && vendaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="vendas2App.venda.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHora')}>
                  <Translate contentKey="vendas2App.venda.dataHora">Data Hora</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotalBruto')}>
                  <Translate contentKey="vendas2App.venda.valorTotalBruto">Valor Total Bruto</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotalDesconto')}>
                  <Translate contentKey="vendas2App.venda.valorTotalDesconto">Valor Total Desconto</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotalLiquido')}>
                  <Translate contentKey="vendas2App.venda.valorTotalLiquido">Valor Total Liquido</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotalPago')}>
                  <Translate contentKey="vendas2App.venda.valorTotalPago">Valor Total Pago</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorSaldoRestante')}>
                  <Translate contentKey="vendas2App.venda.valorSaldoRestante">Valor Saldo Restante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('observarcoes')}>
                  <Translate contentKey="vendas2App.venda.observarcoes">Observarcoes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorPossuiPagamentoPendente')}>
                  <Translate contentKey="vendas2App.venda.indicadorPossuiPagamentoPendente">Indicador Possui Pagamento Pendente</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorPossuiItemPresente')}>
                  <Translate contentKey="vendas2App.venda.indicadorPossuiItemPresente">Indicador Possui Item Presente</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorBloqueio')}>
                  <Translate contentKey="vendas2App.venda.indicadorBloqueio">Indicador Bloqueio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraCadastro')}>
                  <Translate contentKey="vendas2App.venda.dataHoraCadastro">Data Hora Cadastro</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorCadastro')}>
                  <Translate contentKey="vendas2App.venda.colaboradorCadastro">Colaborador Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraAtualizacao')}>
                  <Translate contentKey="vendas2App.venda.dataHoraAtualizacao">Data Hora Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorAtualizacao')}>
                  <Translate contentKey="vendas2App.venda.colaboradorAtualizacao">Colaborador Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.venda.clienteQueComprou">Cliente Que Comprou</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vendaList.map((venda, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/venda/${venda.id}`} color="link" size="sm">
                      {venda.id}
                    </Button>
                  </td>
                  <td>{venda.dataHora ? <TextFormat type="date" value={venda.dataHora} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{venda.valorTotalBruto}</td>
                  <td>{venda.valorTotalDesconto}</td>
                  <td>{venda.valorTotalLiquido}</td>
                  <td>{venda.valorTotalPago}</td>
                  <td>{venda.valorSaldoRestante}</td>
                  <td>{venda.observarcoes}</td>
                  <td>{venda.indicadorPossuiPagamentoPendente ? 'true' : 'false'}</td>
                  <td>{venda.indicadorPossuiItemPresente ? 'true' : 'false'}</td>
                  <td>{venda.indicadorBloqueio ? 'true' : 'false'}</td>
                  <td>
                    {venda.dataHoraCadastro ? <TextFormat type="date" value={venda.dataHoraCadastro} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{venda.colaboradorCadastro}</td>
                  <td>
                    {venda.dataHoraAtualizacao ? (
                      <TextFormat type="date" value={venda.dataHoraAtualizacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{venda.colaboradorAtualizacao}</td>
                  <td>
                    {venda.clienteQueComprou ? (
                      <Link to={`/cliente/${venda.clienteQueComprou.id}`}>{venda.clienteQueComprou.nomeCompleto}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/venda/${venda.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/venda/${venda.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/venda/${venda.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="vendas2App.venda.home.notFound">No Vendas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={vendaList && vendaList.length > 0 ? '' : 'd-none'}>
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

export default Venda;
