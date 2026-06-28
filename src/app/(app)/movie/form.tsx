import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { router, useLocalSearchParams } from 'expo-router';
import { useEffect, useState } from 'react';
import { Alert, Pressable, ScrollView, StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { AppButton } from '@/components/ui/button';
import { Chip } from '@/components/ui/chip';
import { LoadingView, MessageView } from '@/components/ui/feedback';
import { AppText } from '@/components/ui/text';
import { TextField } from '@/components/ui/text-field';
import { useAsync } from '@/hooks/use-async';
import { pickImageFromLibrary } from '@/lib/pick-image';
import { ApiError, buildImageFormData, moviesApi, platformsApi } from '@/services';
import { colors, theme } from '@/theme';
import { GENRES } from '@/types';

const DATE_PATTERN = /^\d{4}-\d{2}-\d{2}$/;
const GENRE_OPTIONS = Object.entries(GENRES).map(([code, label]) => ({ code: Number(code), label }));

export default function MovieFormScreen() {
  const { id } = useLocalSearchParams<{ id?: string }>();
  const editingId = id ? Number(id) : null;
  const editing = editingId != null;

  const { data, loading, error, reload } = useAsync(async () => {
    const platforms = await platformsApi.list();
    const movie = editingId ? await moviesApi.getById(editingId) : null;
    return { platforms, movie };
  }, [editingId]);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [director, setDirector] = useState('');
  const [releaseDate, setReleaseDate] = useState('');
  const [genres, setGenres] = useState<number[]>([]);
  const [platformId, setPlatformId] = useState<number | null>(null);
  const [posterPreview, setPosterPreview] = useState<string | null>(null);
  const [newPosterUri, setNewPosterUri] = useState<string | null>(null);
  const [formError, setFormError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  // Prefill when editing.
  useEffect(() => {
    const movie = data?.movie;
    if (!movie) return;
    setTitle(movie.titulo);
    setDescription(movie.descricao);
    setDirector(movie.diretor ?? '');
    setReleaseDate(movie.dataLancamento ?? '');
    setGenres(movie.generos.map((genre) => genre.codigo));
    setPlatformId(movie.plataforma?.id ?? null);
    setPosterPreview(movie.urlImage);
  }, [data?.movie]);

  function toggleGenre(code: number) {
    setGenres((prev) => (prev.includes(code) ? prev.filter((c) => c !== code) : [...prev, code]));
  }

  async function choosePoster() {
    const uri = await pickImageFromLibrary([2, 3]);
    if (!uri) return;
    setNewPosterUri(uri);
    setPosterPreview(uri);
  }

  function validate(): string | null {
    if (!title.trim() || !description.trim() || !director.trim())
      return 'Preencha título, descrição e diretor.';
    if (!DATE_PATTERN.test(releaseDate)) return 'Data inválida. Use o formato AAAA-MM-DD.';
    if (genres.length === 0) return 'Selecione ao menos um gênero.';
    if (!editing && platformId == null) return 'Selecione uma plataforma.';
    return null;
  }

  async function submit() {
    const validationError = validate();
    if (validationError) {
      setFormError(validationError);
      return;
    }
    setFormError(null);
    setSubmitting(true);
    try {
      const movieId = editingId
        ? (
            await moviesApi.update(editingId, {
              titulo: title.trim(),
              descricao: description.trim(),
              diretor: director.trim(),
              dataLancamento: releaseDate,
              generos: genres,
            })
          ).id
        : (
            await moviesApi.create({
              titulo: title.trim(),
              descricao: description.trim(),
              diretor: director.trim(),
              dataLancamento: releaseDate,
              generos: genres,
              plataformaId: platformId!,
            })
          ).id;

      if (newPosterUri) {
        await moviesApi.uploadImage(movieId, buildImageFormData(newPosterUri));
      }
      router.back();
    } catch (err) {
      setFormError(err instanceof ApiError ? err.message : 'Não foi possível salvar o filme.');
      setSubmitting(false);
    }
  }

  const platforms = data?.platforms ?? [];
  const currentPlatform = data?.movie?.plataforma ?? null;

  return (
    <View style={styles.container}>
      <SafeAreaView edges={['top']} style={styles.headerWrap}>
        <View style={styles.header}>
          <Pressable onPress={() => router.back()} hitSlop={8} accessibilityLabel="Voltar">
            <Ionicons name="chevron-back" size={24} color={colors.textPrimary} />
          </Pressable>
          <AppText variant="subtitle">{editing ? 'Editar filme' : 'Novo filme'}</AppText>
          <View style={styles.headerSpacer} />
        </View>
      </SafeAreaView>

      {loading ? (
        <LoadingView />
      ) : error ? (
        <MessageView message={error} actionLabel="Tentar de novo" onAction={reload} />
      ) : (
        <ScrollView
          contentContainerStyle={styles.content}
          showsVerticalScrollIndicator={false}
          keyboardShouldPersistTaps="handled"
        >
          <Pressable onPress={choosePoster} style={styles.posterPicker}>
            {posterPreview ? (
              <Image source={posterPreview} style={styles.poster} contentFit="cover" />
            ) : (
              <View style={[styles.poster, styles.posterPlaceholder]}>
                <Ionicons name="image-outline" size={32} color={colors.textMuted} />
              </View>
            )}
            <AppText variant="caption" color="accent">
              {posterPreview ? 'Trocar pôster' : 'Adicionar pôster'}
            </AppText>
          </Pressable>

          <TextField label="Título" placeholder="Título do filme" value={title} onChangeText={setTitle} autoCapitalize="sentences" />
          <TextField
            label="Descrição"
            placeholder="Sinopse"
            value={description}
            onChangeText={setDescription}
            autoCapitalize="sentences"
            multiline
          />
          <TextField label="Diretor" placeholder="Nome do diretor" value={director} onChangeText={setDirector} autoCapitalize="words" />
          <TextField
            label="Data de lançamento"
            placeholder="AAAA-MM-DD"
            value={releaseDate}
            onChangeText={setReleaseDate}
            keyboardType="numbers-and-punctuation"
          />

          <View style={styles.section}>
            <AppText variant="caption" color="secondary">
              Gêneros
            </AppText>
            <View style={styles.chips}>
              {GENRE_OPTIONS.map((option) => (
                <Chip
                  key={option.code}
                  label={option.label}
                  selected={genres.includes(option.code)}
                  onPress={() => toggleGenre(option.code)}
                />
              ))}
            </View>
          </View>

          <View style={styles.section}>
            <AppText variant="caption" color="secondary">
              Plataforma
            </AppText>
            {editing ? (
              <AppText color="muted">
                {currentPlatform?.nome ?? 'Sem plataforma'} (não editável)
              </AppText>
            ) : platforms.length === 0 ? (
              <View style={styles.emptyPlatforms}>
                <AppText color="muted">Nenhuma plataforma cadastrada.</AppText>
                <AppButton
                  title="Gerenciar plataformas"
                  variant="secondary"
                  onPress={() => router.push('/platforms')}
                />
              </View>
            ) : (
              <View style={styles.chips}>
                {platforms.map((platform) => (
                  <Chip
                    key={platform.id}
                    label={platform.nome}
                    selected={platformId === platform.id}
                    onPress={() => setPlatformId(platform.id)}
                  />
                ))}
              </View>
            )}
          </View>

          {formError ? (
            <AppText variant="caption" color="danger">
              {formError}
            </AppText>
          ) : null}

          <AppButton title="Salvar" loading={submitting} onPress={submit} />
        </ScrollView>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: colors.background },
  headerWrap: { backgroundColor: colors.surface },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: theme.spacing.lg,
    paddingVertical: theme.spacing.md,
  },
  headerSpacer: { width: 24 },
  content: { padding: theme.spacing.lg, gap: theme.spacing.lg, paddingBottom: theme.spacing.xxl },
  posterPicker: { alignItems: 'center', gap: theme.spacing.sm },
  poster: { width: 120, height: 180, borderRadius: theme.radius.md, backgroundColor: colors.surfaceElevated },
  posterPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  section: { gap: theme.spacing.sm },
  chips: { flexDirection: 'row', flexWrap: 'wrap', gap: theme.spacing.sm },
  emptyPlatforms: { gap: theme.spacing.md },
});
