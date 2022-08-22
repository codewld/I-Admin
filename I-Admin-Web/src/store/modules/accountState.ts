import { defineStore } from 'pinia'

/**
 * 账户信息全局存储
 */
export const useAccountStore = defineStore({
  id: 'Account',
  state: (): Account.account => {
    return {
      username: ''
    }
  },
  actions: {
    /**
     * 设置用户名
     */
    setUsername(username: string) {
      this.username = username
    }
  },
  persist: true
})
