import { Link } from 'expo-router';
import { useState } from 'react';
import { StyleSheet, View } from 'react-native';

import { Brand } from '@/components/brand';
import { AppButton } from '@/components/ui/button';
import { Screen } from '@/components/ui/screen';
import { AppText } from '@/components/ui/text';
import { TextField } from '@/components/ui/text-field';
import { useAuth } from '@/contexts/auth-context';
import { ApiError } from '@/services';
import { theme } from '@/theme';

export default function LoginScreen() {
  const { login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit() {
    setError(null);
    if (!email.trim() || !password) {
      setError('Preencha e-mail e senha.');
      return;
    }
    setSubmitting(true);
    try {
      await login({ email: email.trim(), senha: password });
      // Navigation happens automatically once the auth status flips.
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Não foi possível entrar. Tente novamente.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <Screen scroll contentStyle={styles.content}>
      <View style={styles.header}>
        <Brand />
        <AppText color="secondary">Seu catálogo de filmes</AppText>
      </View>

      <View style={styles.form}>
        <TextField
          label="E-mail"
          placeholder="voce@email.com"
          keyboardType="email-address"
          autoComplete="email"
          value={email}
          onChangeText={setEmail}
        />
        <TextField
          label="Senha"
          placeholder="Sua senha"
          secure
          value={password}
          onChangeText={setPassword}
        />

        {error ? (
          <AppText variant="caption" color="danger">
            {error}
          </AppText>
        ) : null}

        <AppButton title="Entrar" loading={submitting} onPress={handleSubmit} />
      </View>

      <View style={styles.footer}>
        <AppText color="secondary">Não tem conta? </AppText>
        <Link href="/register">
          <AppText variant="link" color="accent">
            Criar conta
          </AppText>
        </Link>
      </View>
    </Screen>
  );
}

const styles = StyleSheet.create({
  content: { flexGrow: 1, justifyContent: 'center', gap: theme.spacing.xxl },
  header: { alignItems: 'center', gap: theme.spacing.xs },
  form: { gap: theme.spacing.lg },
  footer: { flexDirection: 'row', justifyContent: 'center', alignItems: 'center' },
});
