<script setup lang="ts">
import { ref } from 'vue'
import request from '@/api/index'
import { Loading } from '@element-plus/icons-vue'
import { Method } from 'axios'
import { useCacheStore } from '@/store'


// -- props --
const props = withDefaults(
  defineProps<{
    /**
     * 请求配置
     */
    requestConf: { path: string, method: Method } | (() => Promise<unknown>),
    /**
     * 是否立即请求  [默认为true]
     */
    immediate?: boolean,
    /**
     * 缓存配置
     */
    cacheConf?: { key: string }
  }>(),
  {
    immediate: true
  }
)


// -- 缓存相关 --
const cacheStore = useCacheStore()


// -- 加载数据相关 --
/**
 * 数据
 */
const data = ref()


/**
 * 是否正在加载数据
 */
const loading = ref(false)


/**
 * 加载数据
 * @param forceRequest 强制加载  [默认为false]
 */
const requestData = async (forceRequest: boolean = false) => {
  // 如果配置了缓存且有缓存，则不需要请求
  if (!forceRequest && props.cacheConf && cacheStore.getCache(props.cacheConf.key) !== undefined) {
    data.value = cacheStore.getCache(props.cacheConf.key)
    return
  }

  try {
    loading.value = true
    if (props.requestConf instanceof Function) {
      data.value = await props.requestConf()
    } else {
      data.value = await request(props.requestConf.path, props.requestConf.method)
    }
  } finally {
    loading.value = false
  }

  // 如果配置了缓存，更新缓存
  if (props.cacheConf) {
    cacheStore.set(props.cacheConf.key, data.value)
  }
}

if (props.immediate) {
  requestData()
}


// -- expose --
defineExpose({
  data,
  requestData
})
</script>

<template>
  <template v-if="loading">
    <slot name="loading">
      <el-icon class="is-loading text-2xl">
        <Loading/>
      </el-icon>
    </slot>
  </template>
  <template v-else>
    <slot :data="data">
      {{ data }}
    </slot>
  </template>
</template>
