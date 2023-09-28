<script setup lang="ts">
import zhCn from 'element-plus/lib/locale/lang/zh-cn'


// -- 国际化相关 --
const locale = zhCn
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>
