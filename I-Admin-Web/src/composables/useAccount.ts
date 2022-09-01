import router from '@/router'
import { useAccountStore } from '@/store'
import { ElMessage } from 'element-plus/es'
import 'element-plus/es/components/message/style/css'

/**
 * 账户相关
 */
export default function useAccount() {

  /**
   * 退出登录
   * @param showMessage 是否显示提示消息
   */
  const logout = (showMessage?: boolean): void => {
    // 重置accountStore
    let accountStore = useAccountStore()
    accountStore.$reset()
    // 跳转至登录页
    router.replace({ name: 'login' })
      .then(() => {
        showMessage && ElMessage.success('退出成功')
      })
      .catch(() => {
        showMessage && ElMessage.error('退出失败')
      })
  }

  return {
    logout
  }
}
