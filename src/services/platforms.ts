import type { Platform, PlatformCreateRequest, PlatformUpdateRequest } from '@/types';

import { http } from './http';

export const platformsApi = {
  list: () => http.get<Platform[]>('/plataformas'),
  getById: (id: number) => http.get<Platform>(`/plataformas/${id}`),
  create: (data: PlatformCreateRequest) => http.post<Platform>('/plataformas', data),
  update: (id: number, data: PlatformUpdateRequest) => http.put<Platform>(`/plataformas/${id}`, data),
  remove: (id: number) => http.delete<void>(`/plataformas/${id}`),
  /** Uploads the logo (multipart `file`) and returns the updated platform. */
  uploadImage: (id: number, form: FormData) =>
    http.upload<Platform>(`/plataformas/${id}/imagem`, form),
};
