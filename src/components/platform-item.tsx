import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';
import type { Platform } from '@/types';

import { AppText } from './ui/text';

interface PlatformItemProps {
  platform: Platform;
  onEdit: () => void;
  onDelete: () => void;
}

export function PlatformItem({ platform, onEdit, onDelete }: PlatformItemProps) {
  return (
    <View style={styles.item}>
      {platform.urlImage ? (
        <Image source={platform.urlImage} style={styles.logo} contentFit="cover" />
      ) : (
        <View style={[styles.logo, styles.logoPlaceholder]}>
          <Ionicons name="tv-outline" size={20} color={colors.textMuted} />
        </View>
      )}

      <AppText variant="body" style={styles.name} numberOfLines={1}>
        {platform.nome}
      </AppText>

      <View style={styles.actions}>
        <Pressable onPress={onEdit} style={styles.iconButton} hitSlop={6} accessibilityLabel="Editar plataforma">
          <Ionicons name="create-outline" size={20} color={colors.textSecondary} />
        </Pressable>
        <Pressable onPress={onDelete} style={styles.iconButton} hitSlop={6} accessibilityLabel="Excluir plataforma">
          <Ionicons name="trash-outline" size={20} color={colors.danger} />
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  item: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.md,
    backgroundColor: colors.surface,
    borderRadius: theme.radius.lg,
    padding: theme.spacing.md,
  },
  logo: { width: 44, height: 44, borderRadius: theme.radius.sm, backgroundColor: colors.surfaceElevated },
  logoPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  name: { flex: 1, fontWeight: '700' },
  actions: { flexDirection: 'row', gap: theme.spacing.sm },
  iconButton: {
    width: 40,
    height: 40,
    borderRadius: theme.radius.md,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.surfaceElevated,
  },
});
