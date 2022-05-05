export interface IServico {
  id?: number;
  nomeServico?: string;
  valor?: number | null;
  indicadorAtivo?: boolean | null;
}

export const defaultValue: Readonly<IServico> = {
  indicadorAtivo: false,
};
