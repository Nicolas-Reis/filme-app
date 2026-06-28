import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { useState } from 'react';
import { ActivityIndicator, StyleSheet, View } from 'react-native';
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import { SafeAreaProvider } from 'react-native-safe-area-context';

import { AppSplash } from '@/components/app-splash';
import { AuthProvider, useAuth } from '@/contexts/auth-context';
import { colors } from '@/theme';

export default function RootLayout() {
  const [splashDone, setSplashDone] = useState(false);

  return (
    <GestureHandlerRootView style={styles.root}>
      <SafeAreaProvider>
        <AuthProvider>
          <StatusBar style="light" />
          <RootNavigator />
        </AuthProvider>
        {!splashDone ? <AppSplash onFinish={() => setSplashDone(true)} /> : null}
      </SafeAreaProvider>
    </GestureHandlerRootView>
  );
}

function RootNavigator() {
  const { status } = useAuth();

  if (status === 'loading') {
    return (
      <View style={styles.loading}>
        <ActivityIndicator color={colors.primary} size="large" />
      </View>
    );
  }

  const isAuthenticated = status === 'authenticated';

  return (
    <Stack
      screenOptions={{ headerShown: false, contentStyle: { backgroundColor: colors.background } }}
    >
      <Stack.Protected guard={isAuthenticated}>
        <Stack.Screen name="(app)" />
      </Stack.Protected>
      <Stack.Protected guard={!isAuthenticated}>
        <Stack.Screen name="(auth)" />
      </Stack.Protected>
    </Stack>
  );
}

const styles = StyleSheet.create({
  root: { flex: 1 },
  loading: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.background,
  },
});
