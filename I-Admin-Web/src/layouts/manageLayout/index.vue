<!--管理页面布局-->
<script setup lang="ts">
import { onMounted } from 'vue'
import IHeader from './components/i-header.vue'
import IAside from './components/i-aside.vue'
import { useLayoutStore } from '@/store'

// -- 侧边栏相关 --
const layoutStore = useLayoutStore()

onMounted(() => {
  // 获取初始宽度
  let width = document.body.offsetWidth
  // 根据初始宽度设置侧边栏宽度
  layoutStore.setAsideFold(width < 1000)
})
</script>

<template>
  <el-container direction="vertical" class="h-full">
    <i-header/>
    <el-container>
      <i-aside/>
      <el-main class="relative">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
