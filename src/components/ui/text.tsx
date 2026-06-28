import { StyleSheet, Text, type TextProps } from 'react-native';

import { colors } from '@/theme';

type Variant = 'display' | 'title' | 'subtitle' | 'body' | 'caption' | 'link';
type Color = 'primary' | 'secondary' | 'muted' | 'accent' | 'onPrimary' | 'danger';

export interface AppTextProps extends TextProps {
  variant?: Variant;
  color?: Color;
}

const COLOR_MAP: Record<Color, string> = {
  primary: colors.textPrimary,
  secondary: colors.textSecondary,
  muted: colors.textMuted,
  accent: colors.primary,
  onPrimary: colors.onPrimary,
  danger: colors.danger,
};

export function AppText({ variant = 'body', color = 'primary', style, ...rest }: AppTextProps) {
  return <Text style={[styles[variant], { color: COLOR_MAP[color] }, style]} {...rest} />;
}

const styles = StyleSheet.create({
  display: { fontSize: 32, lineHeight: 38, fontWeight: '800' },
  title: { fontSize: 26, lineHeight: 32, fontWeight: '700' },
  subtitle: { fontSize: 20, lineHeight: 26, fontWeight: '700' },
  body: { fontSize: 16, lineHeight: 22, fontWeight: '500' },
  caption: { fontSize: 13, lineHeight: 18, fontWeight: '500' },
  link: { fontSize: 14, lineHeight: 20, fontWeight: '600' },
});
