import { Ionicons } from '@expo/vector-icons';
import { router, usePathname, type Href } from 'expo-router';
import { Pressable, ScrollView, StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { useAuth } from '@/contexts/auth-context';
import { colors, theme } from '@/theme';

import { Brand } from './brand';
import { AppText } from './ui/text';

type IconName = keyof typeof Ionicons.glyphMap;

interface NavItem {
  label: string;
  icon: IconName;
  path: Href;
}

const ITEMS: NavItem[] = [
  { label: 'Início', icon: 'home-outline', path: '/' },
  { label: 'Perfil', icon: 'person-outline', path: '/profile' },
];

const ADMIN_ITEMS: NavItem[] = [
  { label: 'Plataformas', icon: 'albums-outline', path: '/platforms' },
];

// Only the part of the drawer props we actually use — avoids the type clash
// between expo-router's vendored react-navigation and @react-navigation/drawer.
interface DrawerContentProps {
  navigation: { closeDrawer: () => void };
}

export function DrawerContent({ navigation }: DrawerContentProps) {
  const pathname = usePathname();
  const { user, isAdmin, logout } = useAuth();
  const items = isAdmin ? [...ITEMS, ...ADMIN_ITEMS] : ITEMS;

  function go(item: NavItem) {
    navigation.closeDrawer();
    router.push(item.path);
  }

  return (
    <SafeAreaView style={styles.container} edges={['top', 'bottom']}>
      <ScrollView contentContainerStyle={styles.content}>
        <View style={styles.header}>
          <Brand variant="title" />
          {user ? (
            <AppText variant="caption" color="secondary">
              {user.nome}
            </AppText>
          ) : null}
        </View>

        <View style={styles.items}>
          {items.map((item) => {
            const active = pathname === item.path;
            return (
              <Pressable
                key={item.label}
                onPress={() => go(item)}
                style={[styles.item, active && styles.itemActive]}
              >
                <Ionicons
                  name={item.icon}
                  size={20}
                  color={active ? colors.primary : colors.textSecondary}
                />
                <AppText color={active ? 'accent' : 'secondary'}>{item.label}</AppText>
              </Pressable>
            );
          })}
        </View>

        <View style={styles.spacer} />

        <Pressable onPress={logout} style={styles.logout}>
          <Ionicons name="log-out-outline" size={20} color={colors.danger} />
          <AppText color="danger">Sair</AppText>
        </Pressable>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1 },
  content: { flexGrow: 1, paddingHorizontal: theme.spacing.md },
  header: {
    paddingVertical: theme.spacing.xl,
    paddingHorizontal: theme.spacing.sm,
    gap: theme.spacing.xs,
  },
  items: { gap: theme.spacing.xs },
  item: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.md,
    paddingVertical: theme.spacing.md,
    paddingHorizontal: theme.spacing.sm,
    borderRadius: theme.radius.md,
  },
  itemActive: { backgroundColor: colors.surfaceElevated },
  spacer: { flex: 1 },
  logout: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing.md,
    paddingVertical: theme.spacing.md,
    paddingHorizontal: theme.spacing.sm,
  },
});
