import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'

// 基础样式
import '@/assets/css/base.css'

// tailwind css
import '@/assets/css/tailwind.css'

createApp(App)
  .use(router)
  .mount('#app')
