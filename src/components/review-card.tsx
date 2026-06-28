import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';
import type { Review } from '@/types';

import { StarRating } from './star-rating';
import { AppText } from './ui/text';

export function ReviewCard({ review }: { review: Review }) {
  const avatar = review.usuario.urlImage;

  return (
    <View style={styles.card}>
      <View style={styles.header}>
        <View style={styles.user}>
          {avatar ? (
            <Image source={avatar} style={styles.avatar} contentFit="cover" />
          ) : (
            <View style={[styles.avatar, styles.avatarPlaceholder]}>
              <Ionicons name="person" size={18} color={colors.textMuted} />
            </View>
          )}
          <AppText variant="body" style={styles.name}>
            {review.usuario.nome}
          </AppText>
        </View>
        <StarRating value={Math.round(review.nota)} size={14} />
      </View>
      <AppText color="secondary">{review.comentario}</AppText>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.surface,
    borderRadius: theme.radius.lg,
    padding: theme.spacing.lg,
    gap: theme.spacing.sm,
  },
  header: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' },
  user: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.sm, flexShrink: 1 },
  avatar: { width: 40, height: 40, borderRadius: 20, backgroundColor: colors.surfaceElevated },
  avatarPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  name: { fontWeight: '700' },
});
