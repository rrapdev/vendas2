import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPagamento } from 'app/shared/model/pagamento.model';
import { getEntities } from './pagamento.reducer';

export const Pagamento = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const pagamentoList = useAppSelector(state => state.pagamento.entities);
  const loading = useAppSelector(state => state.pagamento.loading);
  const totalItems = useAppSelector(state => state.pagamento.totalItems);

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
      <h2 id="pagamento-heading" data-cy="PagamentoHeading">
        <Translate contentKey="vendas2App.pagamento.home.title">Pagamentos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="vendas2App.pagamento.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pagamento/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="vendas2App.pagamento.home.createLabel">Create new Pagamento</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pagamentoList && pagamentoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="vendas2App.pagamento.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('formaPagamento')}>
                  <Translate contentKey="vendas2App.pagamento.formaPagamento">Forma Pagamento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHora')}>
                  <Translate contentKey="vendas2App.pagamento.dataHora">Data Hora</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valor')}>
                  <Translate contentKey="vendas2App.pagamento.valor">Valor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('observacoes')}>
                  <Translate contentKey="vendas2App.pagamento.observacoes">Observacoes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroParcelas')}>
                  <Translate contentKey="vendas2App.pagamento.numeroParcelas">Numero Parcelas</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('bandeiraCartao')}>
                  <Translate contentKey="vendas2App.pagamento.bandeiraCartao">Bandeira Cartao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clienteOrigemPagamento')}>
                  <Translate contentKey="vendas2App.pagamento.clienteOrigemPagamento">Cliente Origem Pagamento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('indicadorConferido')}>
                  <Translate contentKey="vendas2App.pagamento.indicadorConferido">Indicador Conferido</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraCadastro')}>
                  <Translate contentKey="vendas2App.pagamento.dataHoraCadastro">Data Hora Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorCadastro')}>
                  <Translate contentKey="vendas2App.pagamento.colaboradorCadastro">Colaborador Cadastro</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dataHoraAtualizacao')}>
                  <Translate contentKey="vendas2App.pagamento.dataHoraAtualizacao">Data Hora Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colaboradorAtualizacao')}>
                  <Translate contentKey="vendas2App.pagamento.colaboradorAtualizacao">Colaborador Atualizacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="vendas2App.pagamento.adquirentePagamento">Adquirente Pagamento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pagamentoList.map((pagamento, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pagamento/${pagamento.id}`} color="link" size="sm">
                      {pagamento.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`vendas2App.FormaPagamento.${pagamento.formaPagamento}`} />
                  </td>
                  <td>{pagamento.dataHora ? <TextFormat type="date" value={pagamento.dataHora} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pagamento.valor}</td>
                  <td>{pagamento.observacoes}</td>
                  <td>{pagamento.numeroParcelas}</td>
                  <td>
                    <Translate contentKey={`vendas2App.BandeiraCartao.${pagamento.bandeiraCartao}`} />
                  </td>
                  <td>{pagamento.clienteOrigemPagamento}</td>
                  <td>{pagamento.indicadorConferido ? 'true' : 'false'}</td>
                  <td>
                    {pagamento.dataHoraCadastro ? (
                      <TextFormat type="date" value={pagamento.dataHoraCadastro} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pagamento.colaboradorCadastro}</td>
                  <td>
                    {pagamento.dataHoraAtualizacao ? (
                      <TextFormat type="date" value={pagamento.dataHoraAtualizacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pagamento.colaboradorAtualizacao}</td>
                  <td>
                    {pagamento.adquirentePagamento ? (
                      <Link to={`/plataforma-pagamento/${pagamento.adquirentePagamento.id}`}>
                        {pagamento.adquirentePagamento.nomePlataformaPagamento}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pagamento/${pagamento.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pagamento/${pagamento.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/pagamento/${pagamento.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="vendas2App.pagamento.home.notFound">No Pagamentos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={pagamentoList && pagamentoList.length > 0 ? '' : 'd-none'}>
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

export default Pagamento;
