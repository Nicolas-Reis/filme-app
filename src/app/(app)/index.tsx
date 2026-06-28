import { Ionicons } from '@expo/vector-icons';
import { router } from 'expo-router';
import { useMemo, useState } from 'react';
import { FlatList, ScrollView, StyleSheet, TextInput, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { AppHeader } from '@/components/app-header';
import { MovieCard } from '@/components/movie-card';
import { Chip } from '@/components/ui/chip';
import { LoadingView, MessageView } from '@/components/ui/feedback';
import { AppText } from '@/components/ui/text';
import { useAsync } from '@/hooks/use-async';
import { averageRatingByMovie } from '@/lib/ratings';
import { moviesApi, reviewsApi } from '@/services';
import { colors, theme } from '@/theme';
import { GENRES } from '@/types';

type GenreFilter = number | 'all';

const GENRE_CHIPS: { key: GenreFilter; label: string }[] = [
  { key: 'all', label: 'Todos' },
  ...Object.entries(GENRES).map(([code, label]) => ({ key: Number(code) as GenreFilter, label })),
];

export default function HomeScreen() {
  const { data, loading, error, reload } = useAsync(async () => {
    const [movies, reviews] = await Promise.all([moviesApi.list(), reviewsApi.list()]);
    return { movies, ratings: averageRatingByMovie(reviews) };
  }, []);

  const [searchVisible, setSearchVisible] = useState(false);
  const [term, setTerm] = useState('');
  const [genre, setGenre] = useState<GenreFilter>('all');

  const movies = data?.movies ?? [];
  const ratings = data?.ratings ?? {};

  const visibleMovies = useMemo(() => {
    const normalized = term.trim().toLowerCase();
    return movies
      .filter((movie) => (normalized ? movie.titulo.toLowerCase().includes(normalized) : true))
      .filter((movie) => (genre === 'all' ? true : movie.generos.some((g) => g.codigo === genre)))
      .sort((a, b) => (ratings[b.id] ?? 0) - (ratings[a.id] ?? 0));
  }, [movies, ratings, term, genre]);

  function toggleSearch() {
    setSearchVisible((visible) => {
      if (visible) {
        setTerm('');
        setGenre('all');
      }
      return !visible;
    });
  }

  return (
    <View style={styles.container}>
      <SafeAreaView edges={['top']} style={styles.headerWrap}>
        <AppHeader onSearchPress={toggleSearch} />
      </SafeAreaView>

      {loading ? (
        <LoadingView />
      ) : error ? (
        <MessageView message={error} actionLabel="Tentar de novo" onAction={reload} />
      ) : (
        <FlatList
          data={visibleMovies}
          keyExtractor={(item) => String(item.id)}
          numColumns={2}
          columnWrapperStyle={styles.row}
          contentContainerStyle={styles.list}
          showsVerticalScrollIndicator={false}
          ListHeaderComponent={
            <View style={styles.listHeader}>
              {searchVisible ? (
                <>
                  <View style={styles.searchBox}>
                    <Ionicons name="search" size={18} color={colors.textMuted} />
                    <TextInput
                      style={styles.searchInput}
                      placeholder="Buscar..."
                      placeholderTextColor={colors.textMuted}
                      value={term}
                      onChangeText={setTerm}
                      autoFocus
                    />
                  </View>
                  <ScrollView
                    horizontal
                    showsHorizontalScrollIndicator={false}
                    contentContainerStyle={styles.chips}
                  >
                    {GENRE_CHIPS.map((chip) => (
                      <Chip
                        key={String(chip.key)}
                        label={chip.label}
                        selected={genre === chip.key}
                        onPress={() => setGenre(chip.key)}
                      />
                    ))}
                  </ScrollView>
                </>
              ) : (
                <View style={styles.sectionTitle}>
                  <View style={styles.titleBar} />
                  <AppText variant="title">Em alta</AppText>
                </View>
              )}
            </View>
          }
          renderItem={({ item }) => (
            <MovieCard
              movie={item}
              rating={ratings[item.id] ?? null}
              onPress={() => router.push({ pathname: '/movie/[id]', params: { id: item.id } })}
            />
          )}
          ListEmptyComponent={
            <MessageView
              message={
                term || genre !== 'all'
                  ? 'Nenhum filme encontrado.'
                  : 'Nenhum filme cadastrado ainda.'
              }
            />
          }
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: colors.background },
  headerWrap: { backgroundColor: colors.surface },
  list: { paddingHorizontal: theme.spacing.lg, paddingBottom: theme.spacing.xxl, gap: theme.spacing.md },
  row: { gap: theme.spacing.md },
  listHeader: { paddingVertical: theme.spacing.lg, gap: theme.spacing.md },
  sectionTitle: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.md },
  titleBar: { width: 4, height: 26, borderRadius: 2, backgroundColor: colors.primary },
  searchBox: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.sm,
    backgroundColor: colors.surface,
    borderRadius: theme.radius.pill,
    borderWidth: 1,
    borderColor: colors.border,
    paddingHorizontal: theme.spacing.lg,
    height: 48,
  },
  searchInput: { flex: 1, color: colors.textPrimary, fontSize: 16 },
  chips: { gap: theme.spacing.sm, paddingRight: theme.spacing.lg },
});
