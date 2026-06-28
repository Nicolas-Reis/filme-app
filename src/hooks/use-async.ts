import { useFocusEffect } from 'expo-router';
import { useCallback, useState } from 'react';

import { ApiError } from '@/services';

interface AsyncState<T> {
  data: T | null;
  loading: boolean;
  error: string | null;
}

function toMessage(err: unknown): string {
  return err instanceof ApiError ? err.message : 'Não foi possível carregar os dados.';
}

/**
 * Runs an async function whenever the screen gains focus — both on first mount
 * and every time you navigate back to it — so lists stay fresh after
 * creating/editing data elsewhere. The loading state only shows on the first
 * load; subsequent focus refetches happen silently in the background.
 *
 * Use the returned `reload` for explicit refreshes that don't change focus
 * (e.g. after submitting a modal on the same screen).
 */
export function useAsync<T>(fn: () => Promise<T>, deps: unknown[] = []) {
  const [state, setState] = useState<AsyncState<T>>({ data: null, loading: true, error: null });
  // eslint-disable-next-line react-hooks/exhaustive-deps
  const callback = useCallback(fn, deps);

  const run = useCallback(() => {
    let active = true;
    setState((prev) => ({ ...prev, loading: prev.data == null, error: null }));
    callback()
      .then((data) => {
        if (active) setState({ data, loading: false, error: null });
      })
      .catch((err) => {
        if (active) setState((prev) => ({ data: prev.data, loading: false, error: toMessage(err) }));
      });
    return () => {
      active = false;
    };
  }, [callback]);

  // Refetch on every focus (includes the initial mount).
  useFocusEffect(run);

  return { ...state, reload: run };
}
