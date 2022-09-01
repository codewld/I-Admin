import { ElLoading } from 'element-plus'
import 'element-plus/es/components/loading/style/css'


/**
 * 加载动画
 */
export default function useLoading() {

  /** 加载动画实例 */
  let loadingInstance: ReturnType<typeof ElLoading.service>

  /**
   * 开始加载动画
   * @param config 配置
   */
  const startLoading = (config: Parameters<typeof ElLoading.service>[0]) => {
    loadingInstance = ElLoading.service(config)
  }

  /**
   * 停止加载动画
   */
  const endLoading = () => {
    loadingInstance.close()
  }

  return {
    startLoading,
    endLoading
  }

}
