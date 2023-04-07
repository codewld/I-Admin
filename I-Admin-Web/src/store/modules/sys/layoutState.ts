import { defineStore } from 'pinia'


/**
 * 布局信息全局存储
 */
export const useLayoutStore = defineStore({
  id: 'Layout',
  state: (): Layout.layout => {
    return {
      isAsideFold: false
    }
  },
  getters: {
    /**
     * 获取侧边栏宽度
     */
    getAsideWidth: state => {
      return state.isAsideFold ? '70px' : '200px'
    }
  },
  actions: {
    /**
     * 设置侧边栏是否折叠
     */
    setAsideFold(isAsideFold?: boolean) {
      this.isAsideFold = isAsideFold ?? !this.isAsideFold
    }
  },
  persist: true
})
