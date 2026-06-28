import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';
import type { Movie } from '@/types';

import { RatingBadge } from './rating';
import { AppText } from './ui/text';

interface MovieCardProps {
  movie: Movie;
  rating?: number | null;
  onPress: () => void;
}

export function MovieCard({ movie, rating, onPress }: MovieCardProps) {
  return (
    <Pressable style={styles.card} onPress={onPress}>
      {movie.urlImage ? (
        <Image source={movie.urlImage} style={styles.poster} contentFit="cover" transition={200} />
      ) : (
        <View style={[styles.poster, styles.placeholder]}>
          <Ionicons name="film-outline" size={40} color={colors.textMuted} />
        </View>
      )}
      <View style={styles.overlay}>
        <AppText variant="body" numberOfLines={1} style={styles.title}>
          {movie.titulo}
        </AppText>
        {rating != null && rating > 0 ? <RatingBadge value={rating} /> : null}
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  card: {
    flex: 1,
    aspectRatio: 3 / 4,
    borderRadius: theme.radius.lg,
    overflow: 'hidden',
    backgroundColor: colors.surface,
  },
  poster: { position: 'absolute', top: 0, left: 0, right: 0, bottom: 0 },
  placeholder: { alignItems: 'center', justifyContent: 'center' },
  overlay: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
    padding: theme.spacing.md,
    gap: theme.spacing.xs,
    backgroundColor: 'rgba(10, 10, 25, 0.55)',
  },
  title: { fontWeight: '700' },
});
