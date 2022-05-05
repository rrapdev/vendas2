import dayjs from 'dayjs';
import { IPlataformaPagamento } from 'app/shared/model/plataforma-pagamento.model';
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { IVenda } from 'app/shared/model/venda.model';
import { FormaPagamento } from 'app/shared/model/enumerations/forma-pagamento.model';
import { BandeiraCartao } from 'app/shared/model/enumerations/bandeira-cartao.model';

export interface IPagamento {
  id?: number;
  formaPagamento?: FormaPagamento;
  dataHora?: string;
  valor?: number;
  observacoes?: string | null;
  numeroParcelas?: number | null;
  bandeiraCartao?: BandeiraCartao | null;
  clienteOrigemPagamento?: string | null;
  indicadorConferido?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  plataformaPagamento?: IPlataformaPagamento | null;
  lancamentoCarteiraCliente?: ILancamentoCarteiraCliente | null;
  vendas?: IVenda[] | null;
}

export const defaultValue: Readonly<IPagamento> = {
  indicadorConferido: false,
};
