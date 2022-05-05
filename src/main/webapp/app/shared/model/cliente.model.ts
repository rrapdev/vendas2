import { ICarteiraCliente } from 'app/shared/model/carteira-cliente.model';

export interface ICliente {
  id?: number;
  nomeCompleto?: string;
  telefone?: string;
  nomeApresentacao?: string | null;
  indicadorAtivo?: boolean | null;
  carteiraCliente?: ICarteiraCliente | null;
}

export const defaultValue: Readonly<ICliente> = {
  indicadorAtivo: false,
};
