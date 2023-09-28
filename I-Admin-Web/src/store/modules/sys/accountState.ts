import { defineStore } from 'pinia'


/**
 * 账户信息全局存储
 */
export const useAccountStore = defineStore({
  id: 'Account',
  state: (): account.Account => {
    return {
      username: undefined,
      JWT: undefined
    }
  },
  getters: {
    /**
     * 已登录
     */
    isLoggedIn: state => {
      return state.JWT !== undefined
    }
  },
  actions: {
    /**
     * 设置用户名
     */
    setUsername(username: string) {
      this.username = username
    },
    /**
     * 设置JWT
     */
    setJWT(JWT: string) {
      this.JWT = JWT
    }
  },
  persist: true
})
