import dayjs from 'dayjs';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { ILancamentoCarteiraCliente } from 'app/shared/model/lancamento-carteira-cliente.model';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { IPagamento } from 'app/shared/model/pagamento.model';

export interface IVenda {
  id?: number;
  dataHora?: string;
  valorTotalBruto?: number | null;
  valorTotalDesconto?: number | null;
  valorTotalLiquido?: number | null;
  valorTotalPago?: number | null;
  valorSaldoRestante?: number | null;
  observarcoes?: string | null;
  indicadorPossuiPagamentoPendente?: boolean | null;
  indicadorPossuiItemPresente?: boolean | null;
  indicadorBloqueio?: boolean | null;
  dataHoraCadastro?: string | null;
  colaboradorCadastro?: string | null;
  dataHoraAtualizacao?: string | null;
  colaboradorAtualizacao?: string | null;
  itenssVendasses?: IItemVenda[] | null;
  clienteQueComprou?: ICliente | null;
  lancamentoCarteiraCliente?: ILancamentoCarteiraCliente | null;
  colaboradoresQueIndicarams?: IColaborador[] | null;
  itensVendas?: IItemVenda[] | null;
  pagamentos?: IPagamento[] | null;
}

export const defaultValue: Readonly<IVenda> = {
  indicadorPossuiPagamentoPendente: false,
  indicadorPossuiItemPresente: false,
  indicadorBloqueio: false,
};
