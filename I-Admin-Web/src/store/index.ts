import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'
import { useAccountStore } from './modules/sys/accountState'
import { useLayoutStore } from './modules/sys/layoutState'


const store = createPinia()

// 持久化插件
store.use(createPersistedState({
  storage: sessionStorage
}))

export default store

export {
  useAccountStore,
  useLayoutStore
}
