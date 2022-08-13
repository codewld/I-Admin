<!--首页-->
<script setup lang="ts">
import { useAccountStore } from '@/store'

// -- 账户相关 --
const accountStore = useAccountStore()
</script>

<template>
  首页
  <p>欢迎回来，{{accountStore.username}}</p>
</template>
