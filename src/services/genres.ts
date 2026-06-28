import type { Genre } from '@/types';

import { http } from './http';

export const genresApi = {
  list: () => http.get<Genre[]>('/generos'),
};
