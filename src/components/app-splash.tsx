import { Ionicons } from '@expo/vector-icons';
import { useEffect } from 'react';
import { StyleSheet, View } from 'react-native';
import Animated, {
  Easing,
  runOnJS,
  useAnimatedStyle,
  useSharedValue,
  withDelay,
  withSpring,
  withTiming,
} from 'react-native-reanimated';

import { Brand } from '@/components/brand';
import { AppText } from '@/components/ui/text';
import { colors, theme } from '@/theme';

const VISIBLE_MS = 4000; // tempo na tela (entre 3 e 5s)
const FADE_OUT_MS = 500;

/** Splash animada do FilmBoxd: logo entra com spring, texto faz fade, e o overlay
 *  some após ~4s revelando o app. */
export function AppSplash({ onFinish }: { onFinish: () => void }) {
  const overlay = useSharedValue(1);
  const logoScale = useSharedValue(0.6);
  const logoOpacity = useSharedValue(0);
  const textOpacity = useSharedValue(0);

  useEffect(() => {
    logoOpacity.value = withTiming(1, { duration: 600 });
    logoScale.value = withSpring(1, { damping: 9, stiffness: 120 });
    textOpacity.value = withDelay(450, withTiming(1, { duration: 600 }));
    overlay.value = withDelay(
      VISIBLE_MS,
      withTiming(0, { duration: FADE_OUT_MS, easing: Easing.out(Easing.ease) }, (finished) => {
        if (finished) runOnJS(onFinish)();
      }),
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const overlayStyle = useAnimatedStyle(() => ({ opacity: overlay.value }));
  const logoStyle = useAnimatedStyle(() => ({
    opacity: logoOpacity.value,
    transform: [{ scale: logoScale.value }],
  }));
  const textStyle = useAnimatedStyle(() => ({ opacity: textOpacity.value }));

  return (
    <Animated.View style={[styles.container, overlayStyle]}>
      <Animated.View style={[styles.logoWrap, logoStyle]}>
        <View style={styles.ring} />
        <View style={styles.badge}>
          <Ionicons name="film" size={52} color={colors.onPrimary} />
        </View>
      </Animated.View>

      <Animated.View style={[styles.textWrap, textStyle]}>
        <Brand variant="display" style={styles.brand} />
        <AppText color="secondary" style={styles.tagline}>
          Seu catálogo de filmes
        </AppText>
      </Animated.View>
    </Animated.View>
  );
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: colors.background,
    alignItems: 'center',
    justifyContent: 'center',
    gap: theme.spacing.xl,
    zIndex: 100,
  },
  logoWrap: { alignItems: 'center', justifyContent: 'center' },
  ring: {
    position: 'absolute',
    width: 144,
    height: 144,
    borderRadius: 72,
    backgroundColor: 'rgba(45, 45, 242, 0.16)',
  },
  badge: {
    width: 104,
    height: 104,
    borderRadius: 52,
    backgroundColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  textWrap: { alignItems: 'center', gap: theme.spacing.xs },
  brand: { textAlign: 'center' },
  tagline: { textAlign: 'center' },
});
