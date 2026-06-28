import { createContext, use, useEffect, useMemo, useState, type ReactNode } from 'react';

import { authApi } from '@/services/auth';
import { setAuthToken } from '@/services/http';
import { authStorage, type StoredSession } from '@/services/storage';
import { Role, type LoginRequest, type RegisterRequest, type User } from '@/types';

type AuthStatus = 'loading' | 'authenticated' | 'unauthenticated';

interface AuthContextValue {
  user: User | null;
  status: AuthStatus;
  isAdmin: boolean;
  login: (data: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  logout: () => Promise<void>;
  /** Replaces the cached user (e.g. after an avatar upload) and persists it. */
  updateUser: (user: User) => Promise<void>;
}

const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [session, setSession] = useState<StoredSession | null>(null);
  const [status, setStatus] = useState<AuthStatus>('loading');

  // Restore a persisted session on startup.
  useEffect(() => {
    let active = true;
    authStorage.load().then((stored) => {
      if (!active) return;
      if (stored) {
        setAuthToken(stored.token);
        setSession(stored);
        setStatus('authenticated');
      } else {
        setStatus('unauthenticated');
      }
    });
    return () => {
      active = false;
    };
  }, []);

  const value = useMemo<AuthContextValue>(() => {
    async function authenticate(data: LoginRequest) {
      const { token, usuario } = await authApi.login(data);
      const next: StoredSession = { token, user: usuario };
      setAuthToken(token);
      await authStorage.save(next);
      setSession(next);
      setStatus('authenticated');
    }

    return {
      user: session?.user ?? null,
      status,
      isAdmin: session?.user.cargos.includes(Role.ADMIN) ?? false,

      login: authenticate,

      // The backend does not return a token on signup, so we log in right after.
      async register(data: RegisterRequest) {
        await authApi.register(data);
        await authenticate({ email: data.email, senha: data.senha });
      },

      async logout() {
        setAuthToken(null);
        await authStorage.clear();
        setSession(null);
        setStatus('unauthenticated');
      },

      async updateUser(user: User) {
        if (!session) return;
        const next: StoredSession = { token: session.token, user };
        await authStorage.save(next);
        setSession(next);
      },
    };
  }, [session, status]);

  return <AuthContext value={value}>{children}</AuthContext>;
}

export function useAuth(): AuthContextValue {
  const ctx = use(AuthContext);
  if (!ctx) throw new Error('useAuth deve ser usado dentro de <AuthProvider>.');
  return ctx;
}
