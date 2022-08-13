import { createPinia } from 'pinia'
import { useAccountStore } from './modules/accountState'

const store = createPinia()

export default store

export {
  useAccountStore
}
