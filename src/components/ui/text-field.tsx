import { useState } from 'react';
import { Pressable, StyleSheet, TextInput, View, type TextInputProps } from 'react-native';

import { colors, theme } from '@/theme';

import { AppText } from './text';

export interface TextFieldProps extends TextInputProps {
  label: string;
  error?: string | null;
  /** Renders a password field with a show/hide toggle. */
  secure?: boolean;
}

export function TextField({
  label,
  error,
  secure = false,
  multiline,
  style,
  ...rest
}: TextFieldProps) {
  const [hidden, setHidden] = useState(secure);

  return (
    <View style={styles.container}>
      <AppText variant="caption" color="secondary">
        {label}
      </AppText>
      <View style={[styles.inputRow, multiline && styles.inputRowMultiline, !!error && styles.inputError]}>
        <TextInput
          style={[styles.input, multiline && styles.inputMultiline, style]}
          placeholderTextColor={colors.textMuted}
          secureTextEntry={hidden}
          autoCapitalize="none"
          multiline={multiline}
          textAlignVertical={multiline ? 'top' : 'auto'}
          {...rest}
        />
        {secure ? (
          <Pressable onPress={() => setHidden((value) => !value)} hitSlop={8}>
            <AppText variant="caption" color="accent">
              {hidden ? 'Mostrar' : 'Ocultar'}
            </AppText>
          </Pressable>
        ) : null}
      </View>
      {error ? (
        <AppText variant="caption" color="danger">
          {error}
        </AppText>
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { gap: theme.spacing.xs },
  inputRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.sm,
    backgroundColor: colors.surface,
    borderRadius: theme.radius.md,
    borderWidth: 1,
    borderColor: colors.border,
    paddingHorizontal: theme.spacing.lg,
  },
  inputRowMultiline: { alignItems: 'flex-start' },
  inputError: { borderColor: colors.danger },
  input: {
    flex: 1,
    height: 52,
    color: colors.textPrimary,
    fontSize: 16,
  },
  inputMultiline: { height: 110, paddingVertical: theme.spacing.md },
});
