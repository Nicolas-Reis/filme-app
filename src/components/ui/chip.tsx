import { Pressable, StyleSheet } from 'react-native';

import { colors, theme } from '@/theme';

import { AppText } from './text';

interface ChipProps {
  label: string;
  selected?: boolean;
  onPress: () => void;
}

export function Chip({ label, selected = false, onPress }: ChipProps) {
  return (
    <Pressable onPress={onPress} style={[styles.chip, selected && styles.selected]}>
      <AppText variant="caption" color={selected ? 'onPrimary' : 'secondary'} style={styles.label}>
        {label}
      </AppText>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  chip: {
    paddingVertical: theme.spacing.sm,
    paddingHorizontal: theme.spacing.lg,
    borderRadius: theme.radius.pill,
    borderWidth: 1,
    borderColor: colors.border,
  },
  selected: { backgroundColor: colors.primary, borderColor: colors.primary },
  label: { fontWeight: '600' },
});
