import { ActivityIndicator, Pressable, StyleSheet, type PressableProps } from 'react-native';

import { colors, theme } from '@/theme';

import { AppText } from './text';

type Variant = 'primary' | 'secondary' | 'ghost';

export interface AppButtonProps extends Omit<PressableProps, 'children' | 'style'> {
  title: string;
  variant?: Variant;
  loading?: boolean;
}

export function AppButton({
  title,
  variant = 'primary',
  loading = false,
  disabled,
  ...rest
}: AppButtonProps) {
  const isDisabled = disabled || loading;
  const labelColor = variant === 'primary' ? 'onPrimary' : 'accent';

  return (
    <Pressable
      accessibilityRole="button"
      disabled={isDisabled}
      style={({ pressed }) => [
        styles.base,
        styles[variant],
        pressed && styles.pressed,
        isDisabled && styles.disabled,
      ]}
      {...rest}
    >
      {loading ? (
        <ActivityIndicator color={variant === 'primary' ? colors.onPrimary : colors.primary} />
      ) : (
        <AppText variant="body" color={labelColor} style={styles.label}>
          {title}
        </AppText>
      )}
    </Pressable>
  );
}

const styles = StyleSheet.create({
  base: {
    height: 52,
    borderRadius: theme.radius.md,
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: theme.spacing.lg,
  },
  primary: { backgroundColor: colors.primary },
  secondary: { backgroundColor: colors.surfaceElevated },
  ghost: { backgroundColor: 'transparent' },
  pressed: { opacity: 0.85 },
  disabled: { opacity: 0.5 },
  label: { fontWeight: '700' },
});
