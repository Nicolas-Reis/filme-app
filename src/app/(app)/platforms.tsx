import { useState } from 'react';
import { Alert, FlatList, StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { AppHeader } from '@/components/app-header';
import { PlatformItem } from '@/components/platform-item';
import { PlatformModal } from '@/components/platform-modal';
import { AppButton } from '@/components/ui/button';
import { LoadingView, MessageView } from '@/components/ui/feedback';
import { AppText } from '@/components/ui/text';
import { useAsync } from '@/hooks/use-async';
import { ApiError, platformsApi } from '@/services';
import { colors, theme } from '@/theme';
import type { Platform } from '@/types';

export default function PlatformsScreen() {
  const { data, loading, error, reload } = useAsync(() => platformsApi.list(), []);
  const [modalVisible, setModalVisible] = useState(false);
  const [editing, setEditing] = useState<Platform | null>(null);

  const platforms = data ?? [];

  function openNew() {
    setEditing(null);
    setModalVisible(true);
  }

  function openEdit(platform: Platform) {
    setEditing(platform);
    setModalVisible(true);
  }

  function confirmDelete(platform: Platform) {
    Alert.alert('Excluir plataforma', `Excluir "${platform.nome}"?`, [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: 'Excluir',
        style: 'destructive',
        onPress: async () => {
          try {
            await platformsApi.remove(platform.id);
            reload();
          } catch (err) {
            Alert.alert('Erro', err instanceof ApiError ? err.message : 'Não foi possível excluir.');
          }
        },
      },
    ]);
  }

  const listHeader = (
    <View style={styles.listHeader}>
      <AppText variant="title">Plataformas</AppText>
      <AppButton title="Nova plataforma" variant="secondary" onPress={openNew} />
    </View>
  );

  const listEmpty = loading ? (
    <LoadingView />
  ) : error ? (
    <MessageView message={error} actionLabel="Tentar de novo" onAction={reload} />
  ) : (
    <MessageView message="Nenhuma plataforma cadastrada." />
  );

  return (
    <View style={styles.container}>
      <SafeAreaView edges={['top']} style={styles.headerWrap}>
        <AppHeader />
      </SafeAreaView>

      <FlatList
        data={platforms}
        keyExtractor={(item) => String(item.id)}
        ListHeaderComponent={listHeader}
        ListEmptyComponent={listEmpty}
        contentContainerStyle={styles.list}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <PlatformItem
            platform={item}
            onEdit={() => openEdit(item)}
            onDelete={() => confirmDelete(item)}
          />
        )}
      />

      <PlatformModal
        visible={modalVisible}
        platform={editing ?? undefined}
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
  headerWrap: { backgroundColor: colors.surface },
  list: { flexGrow: 1, paddingHorizontal: theme.spacing.lg, paddingBottom: theme.spacing.xxl, gap: theme.spacing.md },
  listHeader: { paddingVertical: theme.spacing.lg, gap: theme.spacing.md },
});
