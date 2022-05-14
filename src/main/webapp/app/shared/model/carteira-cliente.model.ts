import dayjs from 'dayjs';
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { TipoSaldo } from 'app/shared/model/enumerations/tipo-saldo.model';

export interface ICarteiraCliente {
  id?: number;
  nomeCarteiraCliente?: string;
  saldoConsolidado?: number | null;
  tipoIndicadorSaldo?: TipoSaldo | null;
  indicadorBloqueio?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  lancamentoCarteiraClientes?: ILancamentoCarteiraCliente[] | null;
}

export const defaultValue: Readonly<ICarteiraCliente> = {
  indicadorBloqueio: false,
};
