import type { RegistrationStatus } from './enums';

/** A genre as returned inside a movie (`{ codigo, nome }`). */
export interface Genre {
  codigo: number;
  nome: string;
}

export interface Platform {
  id: number;
  nome: string;
  urlImage: string | null;
  status: RegistrationStatus;
}

export interface User {
  id: number;
  nome: string;
  email: string;
  urlImage: string | null;
  status: RegistrationStatus;
  /** Role names, e.g. `['ROLE_ADMIN']`. */
  cargos: string[];
}

export interface Movie {
  id: number;
  titulo: string;
  descricao: string;
  diretor: string | null;
  /** ISO date `YYYY-MM-DD`. */
  dataLancamento: string | null;
  urlImage: string | null;
  status: RegistrationStatus;
  generos: Genre[];
  plataforma: Platform | null;
}

export interface Review {
  id: number;
  comentario: string;
  /** 0–5. */
  nota: number;
  status: RegistrationStatus;
  usuario: User;
  filme: Movie;
}
