import { API_BASE_URL } from '@/config/env';

/** Thrown for any non-2xx response. `message` carries the backend text. */
export class ApiError extends Error {
  constructor(
    public readonly status: number,
    message: string,
  ) {
    super(message);
    this.name = 'ApiError';
  }
}

let authToken: string | null = null;

/** Set (or clear) the bearer token attached to every request. */
export function setAuthToken(token: string | null): void {
  authToken = token;
}

interface RequestOptions {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
  /** JSON-serialized automatically; pass a `FormData` to send multipart. */
  body?: unknown;
}

async function request<T>(path: string, options: RequestOptions = {}): Promise<T> {
  const { method = 'GET', body } = options;
  const isFormData = body instanceof FormData;

  const headers = new Headers();
  if (authToken) headers.set('Authorization', `Bearer ${authToken}`);
  // Let the runtime set the multipart boundary; only JSON needs an explicit type.
  if (!isFormData && body !== undefined) headers.set('Content-Type', 'application/json');

  const response = await fetch(`${API_BASE_URL}${path}`, {
    method,
    headers,
    body: isFormData ? (body as FormData) : body !== undefined ? JSON.stringify(body) : undefined,
  });

  if (!response.ok) {
    throw new ApiError(response.status, await readErrorMessage(response));
  }

  if (response.status === 204) return undefined as T;
  const text = await response.text();
  return (text ? JSON.parse(text) : undefined) as T;
}

/** Backend errors come as plain text; some handlers may return JSON. */
async function readErrorMessage(response: Response): Promise<string> {
  const text = await response.text().catch(() => '');
  if (!text) return fallbackMessage(response.status);
  try {
    const json = JSON.parse(text);
    return json.message ?? json.error ?? text;
  } catch {
    return text;
  }
}

function fallbackMessage(status: number): string {
  switch (status) {
    case 401:
      return 'Sessão expirada. Faça login novamente.';
    case 403:
      return 'Você não tem permissão para esta ação.';
    case 404:
      return 'Registro não encontrado.';
    default:
      return 'Não foi possível completar a operação.';
  }
}

export const http = {
  get: <T>(path: string) => request<T>(path, { method: 'GET' }),
  post: <T>(path: string, body?: unknown) => request<T>(path, { method: 'POST', body }),
  put: <T>(path: string, body?: unknown) => request<T>(path, { method: 'PUT', body }),
  delete: <T>(path: string) => request<T>(path, { method: 'DELETE' }),
  upload: <T>(path: string, form: FormData) => request<T>(path, { method: 'POST', body: form }),
};
