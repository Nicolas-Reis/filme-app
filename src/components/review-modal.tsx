import { Ionicons } from '@expo/vector-icons';
import { useEffect, useState } from 'react';
import { KeyboardAvoidingView, Modal, Platform, Pressable, StyleSheet, TextInput, View } from 'react-native';

import { ApiError, reviewsApi } from '@/services';
import { colors, theme } from '@/theme';
import type { Review } from '@/types';

import { StarRating } from './star-rating';
import { AppButton } from './ui/button';
import { AppText } from './ui/text';

interface ReviewModalProps {
  visible: boolean;
  onClose: () => void;
  /** Called after the review is created/updated successfully. */
  onSubmitted: () => void;
  /** Create mode — the movie being reviewed. */
  movie?: { id: number; titulo: string };
  /** Edit mode — the existing review to update. */
  review?: Review;
}

export function ReviewModal({ visible, onClose, onSubmitted, movie, review }: ReviewModalProps) {
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const editing = !!review;
  const title = review?.filme.titulo ?? movie?.titulo ?? '';

  // Reset / prefill whenever the modal opens.
  useEffect(() => {
    if (!visible) return;
    setRating(review ? Math.round(review.nota) : 0);
    setComment(review?.comentario ?? '');
    setError(null);
    setSubmitting(false);
  }, [visible, review]);

  async function submit() {
    setError(null);
    if (rating === 0) {
      setError('Escolha uma nota.');
      return;
    }
    if (!comment.trim()) {
      setError('Escreva um comentário.');
      return;
    }
    setSubmitting(true);
    try {
      if (review) {
        await reviewsApi.update(review.id, { comentario: comment.trim(), nota: rating });
      } else if (movie) {
        await reviewsApi.create({ comentario: comment.trim(), nota: rating, filmeId: movie.id });
      }
      onSubmitted();
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Não foi possível salvar a review.');
      setSubmitting(false);
    }
  }

  return (
    <Modal visible={visible} transparent animationType="fade" onRequestClose={onClose}>
      <KeyboardAvoidingView
        style={styles.backdrop}
        behavior={Platform.OS === 'ios' ? 'padding' : undefined}
      >
        <Pressable style={StyleSheet.absoluteFill} onPress={onClose} />

        <View style={styles.card}>
          <View style={styles.header}>
            <AppText variant="subtitle">{editing ? 'Editar Review' : 'Fazer Review'}</AppText>
            <Pressable onPress={onClose} hitSlop={8} accessibilityLabel="Fechar">
              <Ionicons name="close" size={22} color={colors.textSecondary} />
            </Pressable>
          </View>

          <View style={styles.ratingBlock}>
            <AppText color="secondary">
              Sua nota para{' '}
              <AppText color="primary" style={styles.bold}>
                {title}
              </AppText>
            </AppText>
            <StarRating value={rating} onChange={setRating} size={32} />
          </View>

          <View style={styles.field}>
            <AppText color="secondary">O que você achou?</AppText>
            <TextInput
              style={styles.input}
              placeholder="Escreva sua review aqui..."
              placeholderTextColor={colors.textMuted}
              value={comment}
              onChangeText={setComment}
              multiline
              textAlignVertical="top"
            />
          </View>

          {error ? (
            <AppText variant="caption" color="danger">
              {error}
            </AppText>
          ) : null}

          <AppButton
            title={editing ? 'Salvar' : 'Publicar Review'}
            loading={submitting}
            onPress={submit}
          />
        </View>
      </KeyboardAvoidingView>
    </Modal>
  );
}

const styles = StyleSheet.create({
  backdrop: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: theme.spacing.lg,
    backgroundColor: colors.overlay,
  },
  card: {
    width: '100%',
    backgroundColor: colors.surface,
    borderRadius: theme.radius.lg,
    padding: theme.spacing.lg,
    gap: theme.spacing.lg,
  },
  header: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' },
  bold: { fontWeight: '700' },
  ratingBlock: { alignItems: 'center', gap: theme.spacing.md },
  field: { gap: theme.spacing.sm },
  input: {
    minHeight: 110,
    backgroundColor: colors.surfaceElevated,
    borderRadius: theme.radius.md,
    padding: theme.spacing.md,
    color: colors.textPrimary,
    fontSize: 16,
  },
});
