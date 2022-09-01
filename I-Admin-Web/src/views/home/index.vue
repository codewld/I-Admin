<!--首页-->
<script setup lang="ts">
import { ref } from 'vue'
import { rHello } from '@/api/hello'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'


// -- Hello World相关 --
/** Hello World文本 */
const msg = ref('Hello World!')

/**
 * 发送Hello World请求
 */
const hello = () => {
  rHello()
      .then(res => {
        ElMessage.success(res)
      })
      .catch(err => {
        ElMessage.warning(err)
      })
}
</script>

<template>
  <div class="h-full flex flex-col">
    <div class="flex-1 flex flex-col justify-center items-center">
      <p class="text-9xl font-bold tracking-wider italic select-none hover:text-blue-400">
        {{ msg }}
      </p>
      <el-button @click="hello" type="success" plain class="m-20">
        发个请求试试
      </el-button>
    </div>
  </div>
</template>
