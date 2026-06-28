import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { Pressable, StyleSheet, View } from 'react-native';

import { RatingBadge } from '@/components/rating';
import { colors, theme } from '@/theme';
import type { Review } from '@/types';

import { AppText } from './ui/text';

interface MyReviewCardProps {
  review: Review;
  onEdit: () => void;
  onDelete: () => void;
}

/** A review shown on the profile — movie-centric (poster + title + the comment). */
export function MyReviewCard({ review, onEdit, onDelete }: MyReviewCardProps) {
  const poster = review.filme.urlImage;

  return (
    <View style={styles.card}>
      <View style={styles.top}>
        {poster ? (
          <Image source={poster} style={styles.poster} contentFit="cover" />
        ) : (
          <View style={[styles.poster, styles.posterPlaceholder]}>
            <Ionicons name="film-outline" size={22} color={colors.textMuted} />
          </View>
        )}
        <View style={styles.titleCol}>
          <AppText variant="body" style={styles.title} numberOfLines={2}>
            {review.filme.titulo}
          </AppText>
        </View>
        <RatingBadge value={review.nota} />
      </View>

      <AppText color="secondary">{review.comentario}</AppText>

      <View style={styles.actions}>
        <Pressable onPress={onEdit} style={styles.action} hitSlop={6}>
          <Ionicons name="pencil" size={16} color={colors.primary} />
          <AppText variant="link" color="accent">
            Editar
          </AppText>
        </Pressable>
        <Pressable onPress={onDelete} style={styles.action} hitSlop={6}>
          <Ionicons name="trash-outline" size={16} color={colors.danger} />
          <AppText variant="link" color="danger">
            Excluir
          </AppText>
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.surface,
    borderRadius: theme.radius.lg,
    padding: theme.spacing.lg,
    gap: theme.spacing.md,
  },
  top: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.md },
  poster: { width: 48, height: 64, borderRadius: theme.radius.sm, backgroundColor: colors.surfaceElevated },
  posterPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  titleCol: { flex: 1 },
  title: { fontWeight: '700' },
  actions: { flexDirection: 'row', justifyContent: 'flex-end', gap: theme.spacing.lg },
  action: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.xs },
});
