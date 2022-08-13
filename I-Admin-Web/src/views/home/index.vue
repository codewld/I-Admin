<!--首页-->
<script setup lang="ts">
import { useAccountStore } from '@/store'

const accountStore = useAccountStore()
</script>

<template>
  首页
  <p>欢迎回来，{{accountStore.getUsername}}</p>
</template>
