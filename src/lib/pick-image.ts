import * as ImagePicker from 'expo-image-picker';

/**
 * Opens the device gallery and returns the selected image URI, or `null` if the
 * user cancels or denies permission. The URI is meant to be passed to
 * `buildImageFormData` and uploaded to the backend image endpoints.
 */
export async function pickImageFromLibrary(aspect?: [number, number]): Promise<string | null> {
  const permission = await ImagePicker.requestMediaLibraryPermissionsAsync();
  if (!permission.granted) return null;

  const result = await ImagePicker.launchImageLibraryAsync({
    mediaTypes: ['images'],
    allowsEditing: true,
    aspect,
    quality: 0.8,
  });

  if (result.canceled) return null;
  return result.assets[0]?.uri ?? null;
}
