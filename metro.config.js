// Learn more: https://docs.expo.dev/guides/customizing-metro/
const { getDefaultConfig } = require('expo/metro-config');

const config = getDefaultConfig(__dirname);

// This is a mono-repo: the Java backend lives in `catalogo-filme-backend/`.
// Its MySQL Docker volume (`catalogo-filme-backend/db/data/`) contains a Unix
// socket file that Metro's file watcher cannot stat (EACCES), crashing the
// bundler. Exclude the whole backend folder from Metro — the app never imports
// from it.
const backend = /catalogo-filme-backend[\\/].*/;
config.resolver.blockList = config.resolver.blockList
  ? [].concat(config.resolver.blockList, backend)
  : backend;

module.exports = config;
