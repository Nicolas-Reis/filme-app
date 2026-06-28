import type { Movie, MovieCreateRequest, MovieUpdateRequest } from '@/types';

import { http } from './http';

export const moviesApi = {
  list: () => http.get<Movie[]>('/filmes'),
  listAll: () => http.get<Movie[]>('/filmes/todos'),
  search: (term: string) => http.get<Movie[]>(`/filmes/buscar?nome=${encodeURIComponent(term)}`),
  getById: (id: number) => http.get<Movie>(`/filmes/${id}`),
  create: (data: MovieCreateRequest) => http.post<Movie>('/filmes', data),
  update: (id: number, data: MovieUpdateRequest) => http.put<Movie>(`/filmes/${id}`, data),
  remove: (id: number) => http.delete<void>(`/filmes/${id}`),
  /** Uploads the poster (multipart `file`) and returns the updated movie. */
  uploadImage: (id: number, form: FormData) => http.upload<Movie>(`/filmes/${id}/imagem`, form),
};
