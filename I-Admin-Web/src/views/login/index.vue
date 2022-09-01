<!--登录页面-->
<script setup lang="ts">
import { Ref, ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
import { useRouter } from 'vue-router'
import { useAccountStore } from '@/store'
import { rLogin } from '@/api/account'


// -- 路由相关 --
/** 路由 */
const router = useRouter()


// -- 登录相关 --
/** 登录表单数据 */
const loginFormData: Ref<Account.loginParam> = ref({
  username: '',
  password: ''
})

/** 账户信息全局存储 */
const accountStore = useAccountStore()

/**
 * 登录
 */
const login = () => {
  rLogin(loginFormData)
      .then(res => {
        accountStore.setUsername(loginFormData.value.username)
        accountStore.setJWT(res)
        router.push({ name: 'home' })
        ElMessage.success('登录成功')
      }).catch(err => {
        ElMessage.warning(err)
      })
}
</script>

<template>
  <div class="h-full flex justify-center items-center bg-login bg-center bg-fixed bg-no-repeat bg-cover">
    <div class="w-112 h-112 px-10 pt-16 pb-6 rounded-3xl space-y-8 bg-white bg-opacity-70 text-center">
      <h1 class="text-6xl font-bold tracking-wider select-none">
        I-Admin
      </h1>
      <el-form label-position="top" :model="loginFormData" ref="loginForm">
        <el-form-item label="账号：" prop="username">
          <el-input type="text" v-model.trim="loginFormData.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input type="password" v-model.trim="loginFormData.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="mt-6">
          <el-button @click="login" type="primary" class="w-full">
            立即登录
          </el-button>
        </el-form-item>
      </el-form>
      <!--<div>-->
      <!--  <label class="text-gray-500">-->
      <!--    没有账号？-->
      <!--  </label>-->
      <!--  <router-link to="/register" class="text-blue-500 hover:text-blue-700">-->
      <!--    注册新账号-->
      <!--  </router-link>-->
      <!--</div>-->
    </div>
  </div>
</template>

<style scoped>
:deep(.el-input__wrapper) {
  box-shadow: none;
}
</style>
