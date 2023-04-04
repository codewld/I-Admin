import { Ref, ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
import { getDiff, isEmpty } from '@/utils/objUtils'
import useCrudApi from '@/components/iCrud/composables/useCrudApi'
import useCrudAction from '@/components/iCrud/composables/useCrudAction'


/**
 * CRUD
 * @param keyField 主键字段
 * @param fieldList 字段列表
 * @param requestConf 请求配置
 * @param currentRowKey 当前行主键值
 * @param beforeAction 操作前处理
 * @param afterAction 操作后处理
 * @param beforeDoActionCallback 操作执行前的回调
 */
export default function useCrud<T>(
  keyField: string,
  fieldList: crud.field[],
  requestConf: crud.requestConf<T>,
  currentRowKey: Ref<string>,
  beforeAction: (action: crud.action) => void,
  afterAction: (action: crud.action) => void,
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

  /**
   * 关闭Dialog
   */
  const closeDialog = () => {
    dialogVisible.value = false
  }

  /**
   *
   * <p>应该监听dialog的closed事件并调用此方法，以避免界面异常
   */
  const afterDialogClosed = () => {
    resetAction()
  }

  /**
   * dialog是否loading
   */
  const dialogLoading = ref(false)


  // -- 状态相关 --
  const {
    action ,
    actionDescription,
    hasAction
  } = useCrudAction()


  // -- 数据相关 --
  /**
   * 当前行
   */
  const iCurrentRow = ref()

  /**
   * 表单数据
   */
  const formData: Ref<common.KVObj<any>> = ref({})


  // -- 预操作相关 --
  /**
   * 是否正在查询完整行数据
   */
  const isGettingCurrentRow = ref(false)

  /**
   * 查询完整行数据
   * @throws 请求异常
   */
  const getCurrentRow = async () => {
    try {
      isGettingCurrentRow.value = true
      iCurrentRow.value = await rGet(currentRowKey.value)
    } finally {
      isGettingCurrentRow.value = false
    }
  }


  // -- 后操作相关 --
  /**
   * 重置状态
   */
  const resetAction = () => {
    // 重置正在进行的操作
    action.value = undefined

    // 重置当前行
    iCurrentRow.value = undefined
  }


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
      beforeAction(iAction)

      if (iAction === 'add') {
        formData.value = {}
        fieldList.forEach(item => {
          formData.value[item.code] = item.formConf?.addDefault
        })
      } else if (iAction === 'del') {

      } else if (iAction === 'update') {
        await getCurrentRow()
        formData.value = { ...iCurrentRow.value }
      } else {
        await getCurrentRow()
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

          // 调用传入的操作后处理
          afterAction(<crud.action>action.value)

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
    afterDialogClosed,
    dialogLoading,
    action,
    actionDescription,
    hasAction,
    formData,
    isGettingCurrentRow,
    handleAction,
    doAction
  }
}
