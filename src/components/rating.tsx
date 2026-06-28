import { Ionicons } from '@expo/vector-icons';
import { StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';

import { AppText } from './ui/text';

/** Compact "★ 4.9" badge used on cards and the movie detail. */
export function RatingBadge({ value, size = 14 }: { value: number; size?: number }) {
  return (
    <View style={styles.row}>
      <Ionicons name="star" size={size} color={colors.star} />
      <AppText variant="caption">{value.toFixed(1)}</AppText>
    </View>
  );
}

const styles = StyleSheet.create({
  row: { flexDirection: 'row', alignItems: 'center', gap: theme.spacing.xs },
});
