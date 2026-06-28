import { Drawer } from 'expo-router/drawer';

import { DrawerContent } from '@/components/drawer-content';
import { colors } from '@/theme';

// The header hamburger (AppHeader) opens this Drawer. Routes are auto-discovered;
// the custom DrawerContent decides which ones show in the menu (movie/[id] stays
// hidden and is reached via push).
export default function AppLayout() {
  return (
    <Drawer
      drawerContent={(props) => <DrawerContent {...props} />}
      screenOptions={{
        headerShown: false,
        drawerStyle: { backgroundColor: colors.surface, width: 280 },
      }}
    />
  );
}
