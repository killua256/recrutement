import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import * as path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src/"),
        "@shared": `${path.resolve(__dirname, "./src/shared/")}`,
        "@config": `${path.resolve(__dirname, "./src/config/")}`,
        "@utils": `${path.resolve(__dirname, "./src/utils/")}`,
        "@contexts": `${path.resolve(__dirname, "./src/contexts/")}`,
        "@assets": `${path.resolve(__dirname, "./src/assets/")}`,
        "@modules": `${path.resolve(__dirname, "./src/modules/")}`,
        "@layout": `${path.resolve(__dirname, "./src/layout/")}`,
        "@routes": `${path.resolve(__dirname, "./src/routes/")}`,
        "@i18n": `${path.resolve(__dirname, "./src/i18n/")}`,
        "@hooks": `${path.resolve(__dirname, "./src/hooks/")}`,
      },
  },
})
