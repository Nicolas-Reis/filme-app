import type { Review, ReviewCreateRequest, ReviewUpdateRequest } from '@/types';

import { http } from './http';

export const reviewsApi = {
  list: () => http.get<Review[]>('/avaliacoes'),
  /** Reviews authored by the logged-in user. */
  listMine: () => http.get<Review[]>('/avaliacoes/minhas'),
  getById: (id: number) => http.get<Review>(`/avaliacoes/${id}`),
  create: (data: ReviewCreateRequest) => http.post<Review>('/avaliacoes', data),
  update: (id: number, data: ReviewUpdateRequest) => http.put<Review>(`/avaliacoes/${id}`, data),
  remove: (id: number) => http.delete<void>(`/avaliacoes/${id}`),
};
