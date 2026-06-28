import { ActivityIndicator, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';

import { AppButton } from './button';
import { AppText } from './text';

export function LoadingView() {
  return (
    <View style={styles.center}>
      <ActivityIndicator color={colors.primary} size="large" />
    </View>
  );
}

interface MessageViewProps {
  message: string;
  actionLabel?: string;
  onAction?: () => void;
}

/** Centered message for empty/error states, with an optional action button. */
export function MessageView({ message, actionLabel, onAction }: MessageViewProps) {
  return (
    <View style={styles.center}>
      <AppText color="secondary" style={styles.text}>
        {message}
      </AppText>
      {actionLabel && onAction ? (
        <AppButton title={actionLabel} variant="secondary" onPress={onAction} />
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  center: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: theme.spacing.xl,
    gap: theme.spacing.lg,
  },
  text: { textAlign: 'center' },
});
