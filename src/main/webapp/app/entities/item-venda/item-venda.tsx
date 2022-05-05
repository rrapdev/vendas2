import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItemVenda } from 'app/shared/model/item-venda.model';
import { getEntities } from './item-venda.reducer';

export const ItemVenda = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const itemVendaList = useAppSelector(state => state.itemVenda.entities);
  const loading = useAppSelector(state => state.itemVenda.loading);
  const totalItems = useAppSelector(state => state.itemVenda.totalItems);

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
      <h2 id="item-venda-heading" data-cy="ItemVendaHeading">
        <Translate contentKey="vendas2App.itemVenda.home.title">Item Vendas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="vendas2App.itemVenda.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/item-venda/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="vendas2App.itemVenda.home.createLabel">Create new Item Venda</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {itemVendaList && itemVendaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="vendas2App.itemVenda.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHora')}>
                  <Translate contentKey="vendas2App.itemVenda.dataHora">Data Hora</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quantidade')}>
                  <Translate contentKey="vendas2App.itemVenda.quantidade">Quantidade</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorUnitario')}>
                  <Translate contentKey="vendas2App.itemVenda.valorUnitario">Valor Unitario</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotal')}>
                  <Translate contentKey="vendas2App.itemVenda.valorTotal">Valor Total</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorDescontoPercentual')}>
                  <Translate contentKey="vendas2App.itemVenda.valorDescontoPercentual">Valor Desconto Percentual</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorDescontoReal')}>
                  <Translate contentKey="vendas2App.itemVenda.valorDescontoReal">Valor Desconto Real</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorTotalComDesconto')}>
                  <Translate contentKey="vendas2App.itemVenda.valorTotalComDesconto">Valor Total Com Desconto</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorItemPresente')}>
                  <Translate contentKey="vendas2App.itemVenda.indicadorItemPresente">Indicador Item Presente</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraCadastro')}>
                  <Translate contentKey="vendas2App.itemVenda.dataHoraCadastro">Data Hora Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorCadastro')}>
                  <Translate contentKey="vendas2App.itemVenda.colaboradorCadastro">Colaborador Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraAtualizacao')}>
                  <Translate contentKey="vendas2App.itemVenda.dataHoraAtualizacao">Data Hora Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorAtualizacao')}>
                  <Translate contentKey="vendas2App.itemVenda.colaboradorAtualizacao">Colaborador Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.itemVenda.servico">Servico</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.itemVenda.colaboradorQueIndicou">Colaborador Que Indicou</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.itemVenda.clienteQueVaiRealizar">Cliente Que Vai Realizar</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.itemVenda.venda">Venda</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {itemVendaList.map((itemVenda, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/item-venda/${itemVenda.id}`} color="link" size="sm">
                      {itemVenda.id}
                    </Button>
                  </td>
                  <td>{itemVenda.dataHora ? <TextFormat type="date" value={itemVenda.dataHora} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{itemVenda.quantidade}</td>
                  <td>{itemVenda.valorUnitario}</td>
                  <td>{itemVenda.valorTotal}</td>
                  <td>{itemVenda.valorDescontoPercentual}</td>
                  <td>{itemVenda.valorDescontoReal}</td>
                  <td>{itemVenda.valorTotalComDesconto}</td>
                  <td>{itemVenda.indicadorItemPresente ? 'true' : 'false'}</td>
                  <td>
                    {itemVenda.dataHoraCadastro ? (
                      <TextFormat type="date" value={itemVenda.dataHoraCadastro} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{itemVenda.colaboradorCadastro}</td>
                  <td>
                    {itemVenda.dataHoraAtualizacao ? (
                      <TextFormat type="date" value={itemVenda.dataHoraAtualizacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{itemVenda.colaboradorAtualizacao}</td>
                  <td>{itemVenda.servico ? <Link to={`/servico/${itemVenda.servico.id}`}>{itemVenda.servico.nomeServico}</Link> : ''}</td>
                  <td>
                    {itemVenda.colaboradorQueIndicou ? (
                      <Link to={`/colaborador/${itemVenda.colaboradorQueIndicou.id}`}>
                        {itemVenda.colaboradorQueIndicou.nomeApresentacao}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {itemVenda.clienteQueVaiRealizar ? (
                      <Link to={`/cliente/${itemVenda.clienteQueVaiRealizar.id}`}>{itemVenda.clienteQueVaiRealizar.nomeCompleto}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{itemVenda.venda ? <Link to={`/venda/${itemVenda.venda.id}`}>{itemVenda.venda.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/item-venda/${itemVenda.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/item-venda/${itemVenda.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/item-venda/${itemVenda.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="vendas2App.itemVenda.home.notFound">No Item Vendas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={itemVendaList && itemVendaList.length > 0 ? '' : 'd-none'}>
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

export default ItemVenda;
