import {fileURLToPath,URL} from 'node:url'

// import path from 'path'
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
import ElementPlus from 'unplugin-element-plus/vite'


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    ElementPlus({
      useSource: true,
    }),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src',
        import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': { //获取路径中包含了/api的请求
        target: 'http://localhost:3000', //后台服务所在的源
        changeOrigin: true,
        rewrite:(path)=>path.replace(/^\/api/,'')//api替换为''
      }
    }
  }
  // css: {
  //   preprocessorOptions: {
  //     scss: {
  //       additionalData: `@use "~/styles/element/index.scss" as *;`,
  //     },
  //   },
  // },

})