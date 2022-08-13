import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'

// 基础样式
import '@/assets/css/base.css'

// tailwind css
import '@/assets/css/tailwind.css'

createApp(App)
  .use(router)
  .use(store)
  .mount('#app')
