import { IVenda } from 'app/shared/model/venda.model';

export interface IColaborador {
  id?: number;
  nomeColaborador?: string;
  nomeApresentacao?: string | null;
  indicadorAtivo?: boolean | null;
  vendas?: IVenda[] | null;
}

export const defaultValue: Readonly<IColaborador> = {
  indicadorAtivo: false,
};
