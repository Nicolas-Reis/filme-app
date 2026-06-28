import type { User } from '@/types';

import { http } from './http';

export const usersApi = {
  list: () => http.get<User[]>('/usuarios'),
  getById: (id: number) => http.get<User>(`/usuarios/${id}`),
  promoteToAdmin: (id: number) => http.put<User>(`/usuarios/${id}/promover-admin`),
  /** Uploads the avatar of the logged-in user (multipart `file`). */
  uploadAvatar: (form: FormData) => http.upload<User>('/usuarios/me/imagem', form),
};
