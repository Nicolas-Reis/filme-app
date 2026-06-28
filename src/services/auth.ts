import type { LoginRequest, LoginResponse, RegisterRequest, User } from '@/types';

import { http } from './http';

export const authApi = {
  login: (data: LoginRequest) => http.post<LoginResponse>('/auth/login', data),
  register: (data: RegisterRequest) => http.post<User>('/auth/cadastro', data),
};
