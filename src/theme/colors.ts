/**
 * FilmBoxd palette — dark theme with an electric-blue accent, extracted from the
 * Figma mockups. The app is dark-only for now, so this is a single palette.
 */
export const colors = {
  background: '#15162B',
  surface: '#1E2038',
  surfaceElevated: '#272A45',

  primary: '#2D2DF2',
  primaryPressed: '#2424C9',
  onPrimary: '#FFFFFF',

  textPrimary: '#FFFFFF',
  textSecondary: '#9AA0B4',
  textMuted: '#6B7088',

  border: '#2C2F4F',
  star: '#4F7CFF',
  danger: '#E5484D',
  success: '#46A758',
  overlay: 'rgba(0, 0, 0, 0.6)',
} as const;

export type AppColors = typeof colors;
