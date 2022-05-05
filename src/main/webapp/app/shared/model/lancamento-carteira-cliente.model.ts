import dayjs from 'dayjs';
import { ICarteiraCliente } from 'app/shared/model/carteira-cliente.model';

export interface ILancamentoCarteiraCliente {
  id?: number;
  dataHora?: string;
  descricaoLancamento?: string;
  valorCredito?: number | null;
  valorDebito?: number | null;
  observacoes?: string | null;
  indicadorBloqueio?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  carteirasClientes?: ICarteiraCliente[] | null;
}

export const defaultValue: Readonly<ILancamentoCarteiraCliente> = {
  indicadorBloqueio: false,
};
