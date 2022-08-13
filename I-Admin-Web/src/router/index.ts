import {createRouter, createWebHashHistory} from 'vue-router'

const manageLayout = () => import('@/layouts/manageLayout/index.vue')

const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')
const admin = () => import('@/views/am/admin/index.vue')
const role = () => import('@/views/am/role/index.vue')

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      component: manageLayout,
      children: [
        {
          path: '',
          name: 'home',
          component: home
        }
      ]
    },
    {
      path: '/am',
      component: manageLayout,
      children: [
        {
          path: 'admin',
          name: 'admin',
          component: admin
        },
        {
          path: 'role',
          name: 'role',
          component: role
        }
      ]
    }
  ]
})

export default router
