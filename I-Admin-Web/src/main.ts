import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'
import { vLoading } from 'element-plus'
import 'element-plus/theme-chalk/el-loading.css'


// 基础样式
import '@/assets/css/base.css'

// tailwind css
import '@/assets/css/tailwind.css'

createApp(App)
  .use(router)
  .use(store)
  .directive('loading', vLoading)
  .mount('#app')
