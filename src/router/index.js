// src/router/index.js
import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/login.vue'
import ScienceListVue from '../views/ScienceList.vue'
import ActionListVue from '../views/ActionList.vue'

const routes = [
  {
    path: '/',//首页
    name: 'Home',
    component: Home
  },
  {
    path: '/login',//登录页
    name: 'Login',
    component: Login
  },
  {
    path: '/sciencelistvue',//登录页
    name: 'ScienceListVue',
    component: ScienceListVue
  },
  {
    path: '/actionlistvue',//登录页
    name: 'ActionListVue',
    component: ActionListVue
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router