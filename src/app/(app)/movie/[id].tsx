import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { router, useLocalSearchParams } from 'expo-router';
import { useState } from 'react';
import { Pressable, ScrollView, StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { RatingBadge } from '@/components/rating';
import { ReviewCard } from '@/components/review-card';
import { ReviewModal } from '@/components/review-modal';
import { Fab } from '@/components/ui/fab';
import { LoadingView, MessageView } from '@/components/ui/feedback';
import { AppText } from '@/components/ui/text';
import { useAsync } from '@/hooks/use-async';
import { moviesApi, reviewsApi } from '@/services';
import { colors, theme } from '@/theme';

export default function MovieDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const movieId = Number(id);
  const [modalVisible, setModalVisible] = useState(false);

  const { data, loading, error, reload } = useAsync(async () => {
    const [movie, allReviews] = await Promise.all([
      moviesApi.getById(movieId),
      reviewsApi.list(),
    ]);
    const reviews = allReviews.filter((review) => review.filme.id === movieId);
    const rating = reviews.length
      ? reviews.reduce((sum, review) => sum + review.nota, 0) / reviews.length
      : null;
    return { movie, reviews, rating };
  }, [movieId]);

  if (loading) {
    return (
      <View style={styles.container}>
        <LoadingView />
      </View>
    );
  }

  if (error || !data) {
    return (
      <View style={styles.container}>
        <MessageView
          message={error ?? 'Filme não encontrado.'}
          actionLabel="Voltar"
          onAction={() => router.back()}
        />
      </View>
    );
  }

  const { movie, reviews, rating } = data;
  const year = movie.dataLancamento?.slice(0, 4) ?? null;
  const meta = [year, movie.generos[0]?.nome].filter(Boolean).join(' • ');
  const reviewCount = `${reviews.length} ${reviews.length === 1 ? 'avaliação' : 'avaliações'}`;

  return (
    <View style={styles.container}>
      <ScrollView showsVerticalScrollIndicator={false} contentContainerStyle={styles.scroll}>
        <View style={styles.hero}>
          {movie.urlImage ? (
            <Image source={movie.urlImage} style={styles.poster} contentFit="cover" />
          ) : (
            <View style={[styles.poster, styles.placeholder]}>
              <Ionicons name="film-outline" size={64} color={colors.textMuted} />
            </View>
          )}
          <SafeAreaView edges={['top']} style={styles.backWrap}>
            <Pressable onPress={() => router.back()} style={styles.backButton} hitSlop={8}>
              <Ionicons name="chevron-back" size={24} color={colors.textPrimary} />
            </Pressable>
          </SafeAreaView>
        </View>

        <View style={styles.content}>
          <View style={styles.titleRow}>
            <View style={styles.titleCol}>
              <AppText variant="title">{movie.titulo}</AppText>
              {meta ? <AppText color="secondary">{meta}</AppText> : null}
            </View>
            {rating != null && rating > 0 ? (
              <View style={styles.ratingCard}>
                <RatingBadge value={rating} size={18} />
                <AppText variant="caption" color="muted">
                  / 5
                </AppText>
              </View>
            ) : null}
          </View>

          <View style={styles.section}>
            <AppText variant="subtitle">Sinopse</AppText>
            <AppText color="secondary">{movie.descricao}</AppText>
          </View>

          <View style={styles.section}>
            <View style={styles.reviewsHeader}>
              <AppText variant="subtitle">Avaliações</AppText>
              <AppText color="muted">{reviewCount}</AppText>
            </View>
            {reviews.length === 0 ? (
              <AppText color="muted">Ainda não há avaliações. Seja o primeiro!</AppText>
            ) : (
              <View style={styles.reviewsList}>
                {reviews.map((review) => (
                  <ReviewCard key={review.id} review={review} />
                ))}
              </View>
            )}
          </View>
        </View>
      </ScrollView>

      <Fab
        icon="create"
        accessibilityLabel="Fazer review"
        onPress={() => setModalVisible(true)}
      />

      <ReviewModal
        visible={modalVisible}
        movie={{ id: movie.id, titulo: movie.titulo }}
        onClose={() => setModalVisible(false)}
        onSubmitted={() => {
          setModalVisible(false);
          reload();
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: colors.background },
  scroll: { paddingBottom: theme.spacing.xxl * 2 },
  hero: { height: 360, backgroundColor: colors.surface },
  poster: { width: '100%', height: '100%' },
  placeholder: { alignItems: 'center', justifyContent: 'center' },
  backWrap: { position: 'absolute', left: theme.spacing.lg, top: 0 },
  backButton: {
    marginTop: theme.spacing.sm,
    width: 40,
    height: 40,
    borderRadius: 20,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.overlay,
  },
  content: { padding: theme.spacing.lg, gap: theme.spacing.xl },
  titleRow: { flexDirection: 'row', alignItems: 'flex-start', gap: theme.spacing.md },
  titleCol: { flex: 1, gap: theme.spacing.xs },
  ratingCard: {
    alignItems: 'center',
    backgroundColor: colors.surfaceElevated,
    borderRadius: theme.radius.md,
    paddingVertical: theme.spacing.sm,
    paddingHorizontal: theme.spacing.md,
  },
  section: { gap: theme.spacing.sm },
  reviewsHeader: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' },
  reviewsList: { gap: theme.spacing.md, marginTop: theme.spacing.xs },
});
