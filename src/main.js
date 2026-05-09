// import { createApp } from 'vue'
// import App from './views/App.vue'

// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

createApp(App)
  .use(router) // 这里必须要有，让 Vue 使用路由
  .mount('#app')