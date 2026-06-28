import { AppText, type AppTextProps } from './ui/text';

/** The "FilmBoxd" wordmark used in the auth screens and the app header. */
export function Brand({ variant = 'display', ...rest }: Omit<AppTextProps, 'children'>) {
  return (
    <AppText variant={variant} {...rest}>
      FilmBoxd
    </AppText>
  );
}
