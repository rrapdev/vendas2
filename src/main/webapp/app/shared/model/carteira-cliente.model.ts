import dayjs from 'dayjs';
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { TipoIndicadorSaldo } from 'app/shared/model/enumerations/tipo-indicador-saldo.model';

export interface ICarteiraCliente {
  id?: number;
  saldoConsolidado?: number;
  tipoIndicadorSaldo?: TipoIndicadorSaldo | null;
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
