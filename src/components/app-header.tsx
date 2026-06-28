import { Ionicons } from '@expo/vector-icons';
import { useNavigation } from 'expo-router';
import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';

import { Brand } from './brand';

interface AppHeaderProps {
  /** When provided, renders the search icon on the right. */
  onSearchPress?: () => void;
}

export function AppHeader({ onSearchPress }: AppHeaderProps) {
  // expo-router's navigation exposes openDrawer() inside a Drawer layout (SDK 56).
  const navigation = useNavigation<{ openDrawer: () => void }>();

  return (
    <View style={styles.header}>
      <View style={styles.left}>
        <Pressable onPress={() => navigation.openDrawer()} hitSlop={8} accessibilityLabel="Abrir menu">
          <Ionicons name="menu" size={26} color={colors.textPrimary} />
        </Pressable>
        <Brand variant="subtitle" />
      </View>
      {onSearchPress ? (
        <Pressable onPress={onSearchPress} hitSlop={8} accessibilityLabel="Buscar">
          <Ionicons name="search" size={22} color={colors.textPrimary} />
        </Pressable>
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: theme.spacing.lg,
    paddingVertical: theme.spacing.md,
  },
  left: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.md },
});
