<!--管理页面布局-顶栏-->
<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import 'element-plus/es/components/message-box/style/css'
import useAccount from '@/composables/useAccount'

// -- 路由相关 --
/** 路由 */
const router = useRouter()


// -- 退出登录相关 --
/**
 * 退出登录
 */
const handleLogout = () => {
  ElMessageBox.confirm(
      '是否要退出登录?',
      '提醒',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
  )
      .then(() => {
        const { logout } = useAccount()
        logout(true)
      })
      .catch(() => {
      })
}
</script>

<template>
  <el-header class="p-0">
    <div class="h-full px-6 z-10 flex justify-between items-center border-0 border-b border-gray-300 select-none">
      <h1 @click="router.push({name: 'home'})" class="text-4xl font-bold tracking-wider cursor-pointer">
        I-Admin
      </h1>
      <div class="space-x-4">
        <el-button link class="text-red-500" @click="handleLogout">
          退出登录
        </el-button>
      </div>
    </div>
  </el-header>
</template>
