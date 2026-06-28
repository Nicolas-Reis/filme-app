import * as SecureStore from 'expo-secure-store';
import { Platform } from 'react-native';

import type { User } from '@/types';

const TOKEN_KEY = 'auth.token';
const USER_KEY = 'auth.user';

const isWeb = Platform.OS === 'web';

// expo-secure-store has no web implementation, so fall back to localStorage on
// web (fine for development). On native we keep the encrypted secure storage.
async function getItem(key: string): Promise<string | null> {
  if (isWeb) return typeof localStorage !== 'undefined' ? localStorage.getItem(key) : null;
  return SecureStore.getItemAsync(key);
}

async function setItem(key: string, value: string): Promise<void> {
  if (isWeb) {
    if (typeof localStorage !== 'undefined') localStorage.setItem(key, value);
    return;
  }
  await SecureStore.setItemAsync(key, value);
}

async function removeItem(key: string): Promise<void> {
  if (isWeb) {
    if (typeof localStorage !== 'undefined') localStorage.removeItem(key);
    return;
  }
  await SecureStore.deleteItemAsync(key);
}

export interface StoredSession {
  token: string;
  user: User;
}

/** Persists the auth session (JWT + user) — secure storage on native, localStorage on web. */
export const authStorage = {
  async load(): Promise<StoredSession | null> {
    const [token, userJson] = await Promise.all([getItem(TOKEN_KEY), getItem(USER_KEY)]);
    if (!token || !userJson) return null;
    try {
      return { token, user: JSON.parse(userJson) as User };
    } catch {
      return null;
    }
  },

  async save(session: StoredSession): Promise<void> {
    await Promise.all([
      setItem(TOKEN_KEY, session.token),
      setItem(USER_KEY, JSON.stringify(session.user)),
    ]);
  },

  async clear(): Promise<void> {
    await Promise.all([removeItem(TOKEN_KEY), removeItem(USER_KEY)]);
  },
};
