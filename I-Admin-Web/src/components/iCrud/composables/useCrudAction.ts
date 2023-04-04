import { ref, Ref, computed } from 'vue'


/**
 * Crud 状态
 */
export default function useCrudAction() {

  /**
   * 当前正在进行的操作
   */
  const action: Ref<crud.action | undefined> = ref(undefined)

  /**
   * 正在进行的操作描述
   */
  const actionDescription = computed(() => {
    let description
    switch (action.value) {
      case 'add':
        description = '添加'
        break
      case 'del':
        description = '删除'
        break
      case 'update':
        description = '修改'
        break
      case 'see':
        description = '查看'
        break
      default:
        description = ''
    }
    return description
  })

  /**
   * 是否有当前正在进行的操作
   */
  const hasAction = computed(() => {
    return action.value !== undefined
  })

  return {
    action,
    actionDescription,
    hasAction
  }
}
