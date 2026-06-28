import type { User } from './models';

/** Payloads sent to the backend (request bodies). */

export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResponse {
  token: string;
  tipo: string; // "Bearer"
  usuario: User;
}

export interface RegisterRequest {
  nome: string;
  email: string;
  senha: string;
  urlImage?: string;
}

export interface MovieCreateRequest {
  titulo: string;
  descricao: string;
  diretor: string;
  /** `YYYY-MM-DD`. */
  dataLancamento: string;
  urlImage?: string;
  /** Genre codes (1–12). */
  generos: number[];
  plataformaId: number;
}

export interface MovieUpdateRequest {
  titulo?: string;
  descricao?: string;
  diretor?: string;
  dataLancamento?: string;
  urlImage?: string;
  generos?: number[];
}

export interface ReviewCreateRequest {
  comentario: string;
  /** 0–5. */
  nota: number;
  filmeId: number;
}

export interface ReviewUpdateRequest {
  comentario?: string;
  nota?: number;
}

export interface PlatformCreateRequest {
  nome: string;
  urlImage?: string;
}

export interface PlatformUpdateRequest {
  nome?: string;
  urlImage?: string;
}
