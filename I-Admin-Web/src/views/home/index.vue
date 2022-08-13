<!--首页-->
<script setup lang="ts">
import { ref } from 'vue'
import { useAccountStore } from '@/store'

// -- Hello World相关 --
let msg = ref('Hello World!')


// -- 账户相关 --
const accountStore = useAccountStore()
</script>

<template>
  <div class="h-full flex flex-col">
    <div>
      <p class="text-xl">
        欢迎回来， {{accountStore.username}} ！
      </p>
    </div>
    <div class="flex-1 flex justify-center items-center">
      <p class="text-9xl font-bold tracking-wider italic select-none hover:text-blue-400">
        {{ msg }}
      </p>
    </div>
  </div>
</template>
