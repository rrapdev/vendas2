import dayjs from 'dayjs';
import { IVenda } from 'app/shared/model/venda.model';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { ICarteiraCliente } from 'app/shared/model/carteira-cliente.model';

export interface ILancamentoCarteiraCliente {
  id?: number;
  descricaoLancamento?: string;
  dataHora?: string | null;
  valorCredito?: number | null;
  valorDebito?: number | null;
  observacoes?: string | null;
  indicadorBloqueio?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  venda?: IVenda | null;
  pagamento?: IPagamento | null;
  carteirasClientes?: ICarteiraCliente[] | null;
}

export const defaultValue: Readonly<ILancamentoCarteiraCliente> = {
  indicadorBloqueio: false,
};
