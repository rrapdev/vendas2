import dayjs from 'dayjs';
import { IServico } from 'app/shared/model/servico.model';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IVenda } from 'app/shared/model/venda.model';

export interface IItemVenda {
  id?: number;
  dataHora?: string | null;
  quantidade?: number;
  valorUnitario?: number | null;
  valorTotal?: number | null;
  valorDescontoPercentual?: number | null;
  valorDescontoReal?: number | null;
  valorTotalComDesconto?: number | null;
  indicadorItemPresente?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  servico?: IServico | null;
  colaboradorQueIndicou?: IColaborador | null;
  clienteQueVaiRealizar?: ICliente | null;
  vendas?: IVenda[] | null;
}

export const defaultValue: Readonly<IItemVenda> = {
  indicadorItemPresente: false,
};
