import {createRouter, createWebHashHistory} from 'vue-router'
import { useAccountStore } from '@/store'
import useAccount from '@/composables/useAccount'
import { ElMessage } from 'element-plus/es'
import 'element-plus/es/components/message/style/css'


// -- 布局 --
const manageLayout = () => import('@/layouts/manageLayout/index.vue')


// -- 页面 --
const login = () => import('@/views/login/index.vue')
const home = () => import('@/views/home/index.vue')
const admin = () => import('@/views/am/admin/index.vue')
const role = () => import('@/views/am/role/index.vue')


// -- router --
/**
 * 创建路由
 */
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

/**
 * 路由导航守卫
 */
router.beforeEach(async (to, from, next) => {
  const accountStore = useAccountStore()
  const { logout } = useAccount()
  // 未登录
  if (!accountStore.isLoggedIn) {
    if (to.name !== 'login') {
      logout()
      ElMessage.warning('请重新登录')
      next()
      return
    }
    next()
    return
  }
  if (to.name === 'login') {
    ElMessage.warning('您已登录')
    next(from)
    return
  }
  next()
})

export default router
