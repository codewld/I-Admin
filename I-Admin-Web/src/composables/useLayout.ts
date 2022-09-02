import { useLayoutStore } from '@/store'
import { onMounted } from 'vue'


/**
 * 布局相关
 */
export default function useLayout() {

  /** 布局信息全局存储 */
  const layoutStore = useLayoutStore()

  /**
   * 切换侧边栏
   */
  const triggerAside = async () => {
    layoutStore.setAsideFold()
  }

  /**
   * 自动切换侧边栏
   */
  const autoTriggerAside = () => {
    // 获取初始宽度
    let width = document.body.offsetWidth
    // 根据初始宽度设置侧边栏宽度
    layoutStore.setAsideFold(width < 1000)
  }

  onMounted(autoTriggerAside)

  window.onresize = autoTriggerAside

  /**
   * 获取侧边栏宽度
   */
  const getAsideWidth = () => {
    return layoutStore.getAsideWidth
  }

  /**
   * 侧边栏是否折叠
   */
  const isAsideFold = () => {
    return layoutStore.isAsideFold
  }

  return {
    triggerAside,
    getAsideWidth,
    isAsideFold
  }
}
