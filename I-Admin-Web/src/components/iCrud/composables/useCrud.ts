import { computed, Ref, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
import { getDiff, isEmpty } from '@/utils/objUtils'
import useCrudApi from '@/components/iCrud/composables/useCrudApi'


/**
 * CRUD
 * @param keyField 主键字段
 * @param fieldList 字段列表
 * @param requestConf 请求配置
 * @param currentRowKey 当前行主键值
 * @param beforeAction 操作前处理
 * @param afterAction 操作后处理
 * @param afterActionSuccess 操作成功后处理
 * @param beforeDoActionCallback 操作执行前的回调
 */
export default function useCrud<T>(
  keyField: string,
  fieldList: crud.field[],
  requestConf: crud.requestConf<T>,
  currentRowKey: Ref<string>,
  beforeAction: (action: crud.action) => void,
  afterAction: (action: crud.action) => void,
  afterActionSuccess: (action: crud.action) => void,
  beforeDoActionCallback?: crud.beforeDoActionCallback<T>) {

  // -- api 相关 --
  const {
    rAdd,
    rDel,
    rUpdate,
    rGet } = useCrudApi<unknown>(requestConf)


  // -- 表单相关 --
  /**
   * 表单Ref
   */
  const formRef = ref()


  // -- dialog相关 --
  /**
   * dialog是否可见
   */
  const dialogVisible = ref(false)

  watch(dialogVisible, (dialogVisible, preDialogVisible) => {
    if (!dialogVisible) {
      // 确保dialog已经关闭，以避免界面异常
      setTimeout(() => {
        resetAction()

        // 调用传入的操作后处理
        afterAction(<crud.action>action.value)
      }, 300)
    }
  })

  /**
   * 关闭Dialog
   */
  const closeDialog = () => {
    dialogVisible.value = false
  }

  /**
   * dialog是否loading
   */
  const dialogLoading = ref(false)


  // -- 操作相关 --
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

  /**
   * 重置操作
   */
  const resetAction = () => {
    // 重置正在进行的操作
    action.value = undefined

    // 重置当前行
    iCurrentRow.value = undefined
  }


  // -- 数据相关 --
  /**
   * 当前行
   */
  const iCurrentRow = ref()

  /**
   * 表单数据
   */
  const formData: Ref<common.KVObj<any>> = ref({})


  // -- 回调相关 --
  /**
   * 操作执行前的处理
   * @param action 操作
   */
  const beforeDoAction = async (action: crud.action) => {
    if (beforeDoActionCallback) {
      const res = await beforeDoActionCallback(action, currentRowKey.value, iCurrentRow.value, <any>formData.value)
      if (!res) {
        return true
      }
      if (res.continue === false) {
        return false
      }
      if (res.formData !== undefined) {
        formData.value = res.formData
      }
    }
    return true
  }


  // -- 增删改查相关 --
  /**
   * 准备操作
   */
  const handleAction = async (iAction: crud.action) => {
    try {
      // 如果已经有正在进行的操作，则拒绝新操作
      if (action.value !== undefined) {
        return
      }

      // 记录正在进行的操作
      action.value = iAction

      // 调用传入的操作前处理
      beforeAction(action.value)

      if (action.value === 'add') {
        formData.value = {}
        fieldList.forEach(item => {
          formData.value[item.code] = item.formConf?.addDefault
        })
      } else if (action.value === 'del') {

      } else if (action.value === 'update') {
        iCurrentRow.value = await rGet(currentRowKey.value)
        formData.value = { ...iCurrentRow.value }
      } else {
        iCurrentRow.value = await rGet(currentRowKey.value)
        formData.value = { ...iCurrentRow.value }
      }

      dialogVisible.value = true
    } catch (e) {
      ElMessage.warning(<string>e)
      resetAction()
    }
  }

  /**
   * 执行操作
   */
  const doAction = async () => {
    formRef.value?.validate(async (isValid: boolean) => {
      if (!action.value || action.value === 'see') {
        return
      }

      if (!isValid) {
        return
      }

      if (!await beforeDoAction(action.value)) {
        return
      }

      let actionPromise
      if (action.value === 'add') {
        actionPromise = rAdd(formData.value)
      } else if (action.value === 'del') {
        actionPromise = rDel(currentRowKey.value)
      } else {
        const diff = getDiff(formData.value, iCurrentRow.value)
        if (isEmpty(diff)) {
          ElMessage.warning('请修改')
          return
        }
        if (diff[keyField] === undefined) {
          diff[keyField] = currentRowKey.value
        }

        actionPromise = rUpdate(diff)
      }

      dialogLoading.value = true

      actionPromise
        .then(() => {
          ElMessage.success('操作成功')

          // 调用传入的操作成功后处理
          afterActionSuccess(<crud.action>action.value)

          closeDialog()
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          dialogLoading.value = false
        })
    })
  }

  return {
    formRef,
    dialogVisible,
    closeDialog,
    dialogLoading,
    action,
    actionDescription,
    hasAction,
    formData,
    handleAction,
    doAction
  }
}
