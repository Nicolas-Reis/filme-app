/** Soft-delete flag shared by every entity. */
export type RegistrationStatus = 'ATIVO' | 'INATIVO';

/** Role names returned by the backend in `usuario.cargos`. */
export const Role = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
} as const;

/**
 * Movie genres. The backend serializes a genre as its integer code, so we keep
 * the code → label map here and expose it to UI as needed.
 */
export const GENRES: Record<number, string> = {
  1: 'Ação',
  2: 'Aventura',
  3: 'Comédia',
  4: 'Drama',
  5: 'Terror',
  6: 'Ficção Científica',
  7: 'Romance',
  8: 'Suspense',
  9: 'Animação',
  10: 'Documentário',
  11: 'Fantasia',
  12: 'Musical',
} as const;

export function genreLabel(code: number): string {
  return GENRES[code] ?? 'Desconhecido';
}
