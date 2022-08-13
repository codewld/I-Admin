import { defineStore } from 'pinia'

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
  getters: {
    /**
     * 获取用户名
     */
    getUsername(): string {
      return this.username
    }
  },
  persist: true
})
