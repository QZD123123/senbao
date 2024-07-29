import {createApp} from 'vue'
import App from './App.vue'
import router from '@/router'
//导入ElementPlus相关的依赖
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
// import './styles/element/index.scss'

import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

//导入封装的api对象
import api from './api/api.js/index.js'

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia)
app.use(router)
// app.use(ElementPlus,{size:"small",zIndex:3000})
app.use(ElementPlus, {
    locale: zhCn
})

//注册图标库
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

//将api对象设置为app的全局属性
app.config.globalProperties.$api = api

app.mount('#app')