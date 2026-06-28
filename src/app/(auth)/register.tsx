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

export default function RegisterScreen() {
  const { register } = useAuth();
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  function validate(): string | null {
    if (!name.trim() || !email.trim() || !password) return 'Preencha todos os campos.';
    if (password !== confirmPassword) return 'As senhas não conferem.';
    return null;
  }

  async function handleSubmit() {
    const validationError = validate();
    if (validationError) {
      setError(validationError);
      return;
    }
    setError(null);
    setSubmitting(true);
    try {
      await register({ nome: name.trim(), email: email.trim(), senha: password });
      // Auto-login on success → navigation happens automatically.
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Não foi possível criar a conta.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <Screen scroll contentStyle={styles.content}>
      <View style={styles.header}>
        <Brand variant="title" />
        <AppText color="secondary">Crie sua conta</AppText>
      </View>

      <View style={styles.form}>
        <TextField label="Nome" placeholder="Seu nome" value={name} onChangeText={setName} />
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
          placeholder="Crie uma senha"
          secure
          value={password}
          onChangeText={setPassword}
        />
        <TextField
          label="Confirmar senha"
          placeholder="Repita a senha"
          secure
          value={confirmPassword}
          onChangeText={setConfirmPassword}
        />

        {error ? (
          <AppText variant="caption" color="danger">
            {error}
          </AppText>
        ) : null}

        <AppButton title="Criar conta" loading={submitting} onPress={handleSubmit} />
      </View>

      <View style={styles.footer}>
        <AppText color="secondary">Já tem conta? </AppText>
        <Link href="/login">
          <AppText variant="link" color="accent">
            Entrar
          </AppText>
        </Link>
      </View>
    </Screen>
  );
}

const styles = StyleSheet.create({
  content: { flexGrow: 1, justifyContent: 'center', gap: theme.spacing.xl },
  header: { alignItems: 'center', gap: theme.spacing.xs },
  form: { gap: theme.spacing.lg },
  footer: { flexDirection: 'row', justifyContent: 'center', alignItems: 'center' },
});
