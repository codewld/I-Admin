<!--管理页面布局-->
<script setup lang="ts">
import IHeader from './components/i-header.vue'
import IAside from './components/i-aside.vue'


</script>

<template>
  <el-container direction="vertical" class="h-full">
    <i-header/>
    <el-container class="overflow-hidden">
      <i-aside/>
      <el-main class="relative">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
