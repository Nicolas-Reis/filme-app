/**
 * Builds the `multipart/form-data` body expected by the image upload endpoints
 * (`POST /<resource>/{id}/imagem`) from a local image URI (e.g. one returned by
 * `expo-image-picker`).
 */
export function buildImageFormData(uri: string): FormData {
  const name = uri.split('/').pop() || `upload-${Date.now()}.jpg`;
  const extension = name.split('.').pop()?.toLowerCase();
  const type = extension === 'png' ? 'image/png' : 'image/jpeg';

  const form = new FormData();
  // React Native's FormData accepts this file shape; the cast keeps TS happy.
  form.append('file', { uri, name, type } as unknown as Blob);
  return form;
}
