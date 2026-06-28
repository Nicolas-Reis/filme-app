import { Pressable, StyleSheet, View } from 'react-native';

import { colors, theme } from '@/theme';

import { AppText } from './text';

interface Tab<T extends string> {
  key: T;
  label: string;
}

interface TabBarProps<T extends string> {
  tabs: Tab<T>[];
  active: T;
  onChange: (key: T) => void;
}

export function TabBar<T extends string>({ tabs, active, onChange }: TabBarProps<T>) {
  return (
    <View style={styles.row}>
      {tabs.map((tab) => {
        const isActive = tab.key === active;
        return (
          <Pressable
            key={tab.key}
            onPress={() => onChange(tab.key)}
            style={[styles.tab, isActive && styles.tabActive]}
          >
            <AppText variant="body" color={isActive ? 'primary' : 'muted'} style={styles.label}>
              {tab.label}
            </AppText>
          </Pressable>
        );
      })}
    </View>
  );
}

const styles = StyleSheet.create({
  row: { flexDirection: 'row', borderBottomWidth: 1, borderBottomColor: colors.border },
  tab: {
    flex: 1,
    alignItems: 'center',
    paddingVertical: theme.spacing.md,
    borderBottomWidth: 2,
    borderBottomColor: 'transparent',
  },
  tabActive: { borderBottomColor: colors.primary },
  label: { fontWeight: '700' },
});
