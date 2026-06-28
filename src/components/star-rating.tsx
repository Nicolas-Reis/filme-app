import { Ionicons } from '@expo/vector-icons';
import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';

interface StarRatingProps {
  value: number;
  /** When provided, the stars become tappable (rating input). */
  onChange?: (value: number) => void;
  size?: number;
}

const STARS = [1, 2, 3, 4, 5];

export function StarRating({ value, onChange, size = 20 }: StarRatingProps) {
  return (
    <View style={styles.row}>
      {STARS.map((star) => {
        const filled = star <= value;
        const icon = (
          <Ionicons
            name={filled ? 'star' : 'star-outline'}
            size={size}
            color={filled ? colors.star : colors.textMuted}
          />
        );
        return onChange ? (
          <Pressable key={star} onPress={() => onChange(star)} hitSlop={4}>
            {icon}
          </Pressable>
        ) : (
          <View key={star}>{icon}</View>
        );
      })}
    </View>
  );
}

const styles = StyleSheet.create({
  row: { flexDirection: 'row', gap: theme.spacing.xs },
});
