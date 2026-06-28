import { Ionicons } from '@expo/vector-icons';
import { Pressable, StyleSheet } from 'react-native';

import { colors, theme } from '@/theme';

type IconName = keyof typeof Ionicons.glyphMap;

interface FabProps {
  icon: IconName;
  onPress: () => void;
  accessibilityLabel?: string;
}

export function Fab({ icon, onPress, accessibilityLabel }: FabProps) {
  return (
    <Pressable
      onPress={onPress}
      accessibilityLabel={accessibilityLabel}
      accessibilityRole="button"
      style={({ pressed }) => [styles.fab, pressed && styles.pressed]}
    >
      <Ionicons name={icon} size={26} color={colors.onPrimary} />
    </Pressable>
  );
}

const styles = StyleSheet.create({
  fab: {
    position: 'absolute',
    right: theme.spacing.lg,
    bottom: theme.spacing.xxl,
    width: 56,
    height: 56,
    borderRadius: 28,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.primary,
    elevation: 6,
    shadowColor: '#000',
    shadowOpacity: 0.3,
    shadowRadius: 8,
    shadowOffset: { width: 0, height: 4 },
  },
  pressed: { opacity: 0.85 },
});
