import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';
import type { Movie } from '@/types';

import { AppText } from './ui/text';

interface ManageMovieItemProps {
  movie: Movie;
  onEdit: () => void;
  onDelete: () => void;
}

export function ManageMovieItem({ movie, onEdit, onDelete }: ManageMovieItemProps) {
  const year = movie.dataLancamento?.slice(0, 4);

  return (
    <View style={styles.item}>
      {movie.urlImage ? (
        <Image source={movie.urlImage} style={styles.poster} contentFit="cover" />
      ) : (
        <View style={[styles.poster, styles.posterPlaceholder]}>
          <Ionicons name="film-outline" size={22} color={colors.textMuted} />
        </View>
      )}

      <View style={styles.info}>
        <AppText variant="body" style={styles.title} numberOfLines={1}>
          {movie.titulo}
        </AppText>
        {year ? <AppText variant="caption" color="muted">{`Lançado em ${year}`}</AppText> : null}
      </View>

      <View style={styles.actions}>
        <Pressable onPress={onEdit} style={styles.iconButton} hitSlop={6} accessibilityLabel="Editar filme">
          <Ionicons name="create-outline" size={20} color={colors.textSecondary} />
        </Pressable>
        <Pressable onPress={onDelete} style={styles.iconButton} hitSlop={6} accessibilityLabel="Excluir filme">
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
  poster: { width: 44, height: 60, borderRadius: theme.radius.sm, backgroundColor: colors.surfaceElevated },
  posterPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  info: { flex: 1, gap: 2 },
  title: { fontWeight: '700' },
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
