export interface IPlataformaPagamento {
  id?: number;
  nomePlataformaPagamento?: string;
  indicadorAtivo?: boolean | null;
}

export const defaultValue: Readonly<IPlataformaPagamento> = {
  indicadorAtivo: false,
};
