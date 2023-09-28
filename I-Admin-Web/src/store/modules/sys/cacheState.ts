import { defineStore } from 'pinia'


/**
 * 缓存全局存储
 */
export const useCacheStore = defineStore({
  id: 'Cache',
  state: (): { cacheManager: common.KVObj } => {
    return {
      cacheManager: {}
    }
  },
  getters: {
    /**
     * 获取缓存
     */
    getCache: state => {
      return (key: string) => state.cacheManager[key]
    }
  },
  actions: {
    /**
     * 设置缓存
     */
    set(key: string, value: any) {
      this.cacheManager[key] = value
    },
    /**
     * 移除缓存
     */
    remove(key: string) {
      this.cacheManager[key] = undefined
    }
  }
})
