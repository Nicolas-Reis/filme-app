import { Platform } from 'react-native';

/**
 * Base URL of the `catalogo-filme` backend.
 *
 * `localhost` is unreachable from a device/emulator, so we fall back to
 * platform-aware defaults:
 * - Android emulator reaches the host machine through `10.0.2.2`.
 * - iOS simulator / web can use `localhost`.
 *
 * For a physical device (Expo Go) set `EXPO_PUBLIC_API_URL` to the dev machine
 * LAN address, e.g. `EXPO_PUBLIC_API_URL=http://192.168.0.10:8081`.
 */
const DEFAULT_PORT = 8081;

function resolveBaseUrl(): string {
  const fromEnv = process.env.EXPO_PUBLIC_API_URL;
  if (fromEnv) return fromEnv.replace(/\/+$/, '');

  const host = Platform.OS === 'android' ? '10.0.2.2' : 'localhost';
  return `http://${host}:${DEFAULT_PORT}`;
}

export const API_BASE_URL = resolveBaseUrl();
