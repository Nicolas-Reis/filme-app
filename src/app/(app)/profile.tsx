import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { router } from 'expo-router';
import { useState } from 'react';
import { ActivityIndicator, Alert, FlatList, Pressable, StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { AppHeader } from '@/components/app-header';
import { ManageMovieItem } from '@/components/manage-movie-item';
import { MyReviewCard } from '@/components/my-review-card';
import { ReviewModal } from '@/components/review-modal';
import { Fab } from '@/components/ui/fab';
import { LoadingView, MessageView } from '@/components/ui/feedback';
import { TabBar } from '@/components/ui/tab-bar';
import { AppText } from '@/components/ui/text';
import { useAuth } from '@/contexts/auth-context';
import { useAsync } from '@/hooks/use-async';
import { pickImageFromLibrary } from '@/lib/pick-image';
import { ApiError, buildImageFormData, moviesApi, reviewsApi, usersApi } from '@/services';
import { colors, theme } from '@/theme';
import type { Movie, Review } from '@/types';

type TabKey = 'reviews' | 'manage';

export default function ProfileScreen() {
  const { user, isAdmin, updateUser } = useAuth();
  const [tab, setTab] = useState<TabKey>('reviews');
  const [editingReview, setEditingReview] = useState<Review | null>(null);
  const [uploadingAvatar, setUploadingAvatar] = useState(false);

  const { data, loading, error, reload } = useAsync(async () => {
    const mine = await reviewsApi.listMine();
    const movies = isAdmin ? await moviesApi.list() : [];
    return { mine, movies };
  }, [isAdmin]);

  const mine = data?.mine ?? [];
  const movies = data?.movies ?? [];
  const filmsRated = new Set(mine.map((review) => review.filme.id)).size;
  const activeTab: TabKey = isAdmin ? tab : 'reviews';

  async function changeAvatar() {
    const uri = await pickImageFromLibrary([1, 1]);
    if (!uri) return;
    setUploadingAvatar(true);
    try {
      const updated = await usersApi.uploadAvatar(buildImageFormData(uri));
      await updateUser(updated);
    } catch (err) {
      Alert.alert('Erro', err instanceof ApiError ? err.message : 'Não foi possível enviar a imagem.');
    } finally {
      setUploadingAvatar(false);
    }
  }

  function confirmDelete(label: string, onConfirm: () => void) {
    Alert.alert('Confirmar exclusão', `Excluir ${label}?`, [
      { text: 'Cancelar', style: 'cancel' },
      { text: 'Excluir', style: 'destructive', onPress: onConfirm },
    ]);
  }

  async function runDelete(action: Promise<void>) {
    try {
      await action;
      reload();
    } catch (err) {
      Alert.alert('Erro', err instanceof ApiError ? err.message : 'Não foi possível excluir.');
    }
  }

  const tabs: { key: TabKey; label: string }[] = isAdmin
    ? [
        { key: 'reviews', label: 'Minhas Reviews' },
        { key: 'manage', label: 'Gerenciar Filmes' },
      ]
    : [{ key: 'reviews', label: 'Minhas Reviews' }];

  const header = (
    <View style={styles.headerContent}>
      <View style={styles.profileCard}>
        <Pressable onPress={changeAvatar} style={styles.avatarWrap} disabled={uploadingAvatar}>
          {user?.urlImage ? (
            <Image source={user.urlImage} style={styles.avatar} contentFit="cover" />
          ) : (
            <View style={[styles.avatar, styles.avatarPlaceholder]}>
              <Ionicons name="person" size={40} color={colors.textMuted} />
            </View>
          )}
          <View style={styles.avatarBadge}>
            {uploadingAvatar ? (
              <ActivityIndicator size="small" color={colors.onPrimary} />
            ) : (
              <Ionicons name="pencil" size={14} color={colors.onPrimary} />
            )}
          </View>
        </Pressable>

        <AppText variant="title">{user?.nome}</AppText>
        <AppText color="secondary">{user?.email}</AppText>

        <View style={styles.stats}>
          <Stat value={mine.length} label="RATINGS" highlight />
          <View style={styles.statDivider} />
          <Stat value={filmsRated} label="FILMES" />
        </View>
      </View>

      <TabBar tabs={tabs} active={activeTab} onChange={setTab} />
    </View>
  );

  const listEmpty = loading ? (
    <LoadingView />
  ) : error ? (
    <MessageView message={error} actionLabel="Tentar de novo" onAction={reload} />
  ) : (
    <MessageView
      message={
        activeTab === 'reviews'
          ? 'Você ainda não fez avaliações.'
          : 'Nenhum filme cadastrado.'
      }
    />
  );

  return (
    <View style={styles.container}>
      <SafeAreaView edges={['top']} style={styles.headerWrap}>
        <AppHeader />
      </SafeAreaView>

      {activeTab === 'reviews' ? (
        <FlatList<Review>
          data={mine}
          keyExtractor={(item) => String(item.id)}
          ListHeaderComponent={header}
          contentContainerStyle={styles.list}
          showsVerticalScrollIndicator={false}
          ListEmptyComponent={listEmpty}
          renderItem={({ item }) => (
            <MyReviewCard
              review={item}
              onEdit={() => setEditingReview(item)}
              onDelete={() =>
                confirmDelete('esta review', () => runDelete(reviewsApi.remove(item.id)))
              }
            />
          )}
        />
      ) : (
        <FlatList<Movie>
          data={movies}
          keyExtractor={(item) => String(item.id)}
          ListHeaderComponent={header}
          contentContainerStyle={styles.list}
          showsVerticalScrollIndicator={false}
          ListEmptyComponent={listEmpty}
          renderItem={({ item }) => (
            <ManageMovieItem
              movie={item}
              onEdit={() => router.push({ pathname: '/movie/form', params: { id: item.id } })}
              onDelete={() =>
                confirmDelete(`"${item.titulo}"`, () => runDelete(moviesApi.remove(item.id)))
              }
            />
          )}
        />
      )}

      {activeTab === 'manage' ? (
        <Fab icon="add" accessibilityLabel="Adicionar filme" onPress={() => router.push('/movie/form')} />
      ) : null}

      <ReviewModal
        visible={!!editingReview}
        review={editingReview ?? undefined}
        onClose={() => setEditingReview(null)}
        onSubmitted={() => {
          setEditingReview(null);
          reload();
        }}
      />
    </View>
  );
}

function Stat({ value, label, highlight }: { value: number; label: string; highlight?: boolean }) {
  return (
    <View style={styles.stat}>
      <AppText variant="title" color={highlight ? 'accent' : 'primary'}>
        {value}
      </AppText>
      <AppText variant="caption" color="secondary">
        {label}
      </AppText>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: colors.background },
  headerWrap: { backgroundColor: colors.surface },
  list: { flexGrow: 1, paddingHorizontal: theme.spacing.lg, paddingBottom: theme.spacing.xxl, gap: theme.spacing.md },
  headerContent: { gap: theme.spacing.lg, paddingTop: theme.spacing.lg },
  profileCard: {
    alignItems: 'center',
    gap: theme.spacing.xs,
    backgroundColor: colors.surface,
    borderRadius: theme.radius.lg,
    padding: theme.spacing.xl,
  },
  avatarWrap: { marginBottom: theme.spacing.sm },
  avatar: { width: 96, height: 96, borderRadius: 48, backgroundColor: colors.surfaceElevated },
  avatarPlaceholder: { alignItems: 'center', justifyContent: 'center' },
  avatarBadge: {
    position: 'absolute',
    right: 0,
    bottom: 0,
    width: 30,
    height: 30,
    borderRadius: 15,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.primary,
    borderWidth: 2,
    borderColor: colors.surface,
  },
  stats: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.xl, marginTop: theme.spacing.md },
  stat: { alignItems: 'center', gap: theme.spacing.xs },
  statDivider: { width: 1, height: 36, backgroundColor: colors.border },
});
