import { Ionicons } from '@expo/vector-icons';
import { Image } from 'expo-image';
import { useEffect, useState } from 'react';
import { KeyboardAvoidingView, Modal, Platform as RNPlatform, Pressable, StyleSheet, View } from 'react-native';

import { pickImageFromLibrary } from '@/lib/pick-image';
import { ApiError, buildImageFormData, platformsApi } from '@/services';
import { colors, theme } from '@/theme';
import type { Platform } from '@/types';

import { AppButton } from './ui/button';
import { AppText } from './ui/text';
import { TextField } from './ui/text-field';

interface PlatformModalProps {
  visible: boolean;
  /** Edit mode — the existing platform. */
  platform?: Platform;
  onClose: () => void;
  onSubmitted: () => void;
}

export function PlatformModal({ visible, platform, onClose, onSubmitted }: PlatformModalProps) {
  const [name, setName] = useState('');
  const [logoPreview, setLogoPreview] = useState<string | null>(null);
  const [newLogoUri, setNewLogoUri] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const editing = !!platform;

  useEffect(() => {
    if (!visible) return;
    setName(platform?.nome ?? '');
    setLogoPreview(platform?.urlImage ?? null);
    setNewLogoUri(null);
    setError(null);
    setSubmitting(false);
  }, [visible, platform]);

  async function chooseLogo() {
    const uri = await pickImageFromLibrary([1, 1]);
    if (!uri) return;
    setNewLogoUri(uri);
    setLogoPreview(uri);
  }

  async function submit() {
    setError(null);
    if (!name.trim()) {
      setError('Informe o nome da plataforma.');
      return;
    }
    setSubmitting(true);
    try {
      const saved = platform
        ? await platformsApi.update(platform.id, { nome: name.trim() })
        : await platformsApi.create({ nome: name.trim() });
      if (newLogoUri) {
        await platformsApi.uploadImage(saved.id, buildImageFormData(newLogoUri));
      }
      onSubmitted();
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Não foi possível salvar a plataforma.');
      setSubmitting(false);
    }
  }

  return (
    <Modal visible={visible} transparent animationType="fade" onRequestClose={onClose}>
      <KeyboardAvoidingView
        style={styles.backdrop}
        behavior={RNPlatform.OS === 'ios' ? 'padding' : undefined}
      >
        <Pressable style={StyleSheet.absoluteFill} onPress={onClose} />

        <View style={styles.card}>
          <View style={styles.header}>
            <AppText variant="subtitle">{editing ? 'Editar plataforma' : 'Nova plataforma'}</AppText>
            <Pressable onPress={onClose} hitSlop={8} accessibilityLabel="Fechar">
              <Ionicons name="close" size={22} color={colors.textSecondary} />
            </Pressable>
          </View>

          <Pressable onPress={chooseLogo} style={styles.logoPicker}>
            {logoPreview ? (
              <Image source={logoPreview} style={styles.logo} contentFit="cover" />
            ) : (
              <View style={[styles.logo, styles.logoPlaceholder]}>
                <Ionicons name="image-outline" size={24} color={colors.textMuted} />
              </View>
            )}
            <AppText variant="caption" color="accent">
              {logoPreview ? 'Trocar logo' : 'Adicionar logo'}
            </AppText>
          </Pressable>

          <TextField label="Nome" placeholder="Ex.: Netflix" value={name} onChangeText={setName} autoCapitalize="words" />

          {error ? (
            <AppText variant="caption" color="danger">
              {error}
            </AppText>
          ) : null}

          <AppButton title="Salvar" loading={submitting} onPress={submit} />
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
  logoPicker: { alignItems: 'center', gap: theme.spacing.sm },
  logo: { width: 72, height: 72, borderRadius: theme.radius.md, backgroundColor: colors.surfaceElevated },
  logoPlaceholder: { alignItems: 'center', justifyContent: 'center' },
});
