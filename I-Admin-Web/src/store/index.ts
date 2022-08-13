import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'
import { useAccountStore } from './modules/accountState'
import { useLayoutStore } from './modules/layoutState'

const store = createPinia()

// 持久化插件
store.use(createPersistedState({
  storage: localStorage
}))

export default store

export {
  useAccountStore,
  useLayoutStore
}
