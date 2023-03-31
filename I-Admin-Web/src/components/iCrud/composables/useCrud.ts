import { computed, Ref, ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
import { getDiff } from '@/utils/objUtils'


/**
 * CRUD
 * @param keyField 主键字段
 * @param fieldList 字段列表
 * @param rGet 查询
 * @param rAdd 添加
 * @param rDel 删除
 * @param rUpdate 修改
 * @param doLoad 加载数据
 * @param beforeDoActionCallback 操作执行前的回调
 */
export default function useCrud<T>(
  keyField: string,
  fieldList: crud.field[],
  rGet: crud.getFunc<T>,
  rAdd: crud.addFunc<T>,
  rDel: crud.delFunc,
  rUpdate: crud.updateFunc<T>,
  doLoad: () => void,
  beforeDoActionCallback?: crud.beforeDoActionCallback<T>) {

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
   * dialog是否loading
   */
  const dialogLoading = ref(false)


  // -- 状态相关 --
  /**
   * 当前正在进行的操作
   */
  const iAction: Ref<crud.action | undefined> = ref(undefined)

  /**
   * 正在进行的操作描述
   */
  const actionDescription = computed(() => {
    if (!iAction.value) {
      return ''
    }
    const actionMap = {
      'add': '添加',
      'del': '删除',
      'update': '修改',
      'see': '查看'
    }
    return actionMap[iAction.value]
  })


  // -- 数据相关 --
  /**
   * 当前行主键值
   */
  const iCurrentRowKey = ref()

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
      iCurrentRow.value = await rGet(iCurrentRowKey.value)
    } finally {
      isGettingCurrentRow.value = false
    }
  }

  /**
   * 操作预处理
   * <p>1. 记录正在进行的操作
   * <p>2. 记录当前行主键值
   * @param action 操作
   * @param currentRowKey 当前行主键值
   * @throws 有正在进行的操作
   */
  const beforeAction = (action: crud.action, currentRowKey?: string) => {
    // 记录正在进行的操作
    iAction.value = action

    // 记录当前行主键值
    iCurrentRowKey.value = currentRowKey
  }


  // -- 后操作相关 --
  /**
   * 重置状态
   * <p>应该监听dialog的关闭，在关闭后调用此方法，以避免界面异常
   */
  const resetAction = () => {
    // 重置正在进行的操作
    iAction.value = undefined

    // 重置当前行主键值
    iCurrentRowKey.value = undefined

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
      const res = await beforeDoActionCallback(action, iCurrentRowKey.value, iCurrentRow.value, <any>formData.value)
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
   * 准备添加
   */
  const handleAdd = async () => {
    try {
      // 如果已经有正在进行的操作，则拒绝新操作
      if (iAction.value !== undefined) {
        return
      }

      beforeAction('add')

      formData.value = {}
      fieldList.forEach(item => {
        formData.value[item.code] = item.formConf?.addDefault
      })

      dialogVisible.value = true
    } catch (e) {
      ElMessage.warning(<string>e)
      resetAction()
      doLoad()
    }
  }

  /**
   * 执行添加
   */
  const doAdd = async () => {
    formRef.value?.validate(async (isValid: boolean) => {
      if (!isValid) {
        return
      }

      if (!await beforeDoAction('add')) {
        return
      }

      dialogLoading.value = true
      rAdd(<any>formData.value)
        .then(() => {
          ElMessage.success('操作成功')
          closeDialog()
          doLoad()
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          dialogLoading.value = false
        })
    })
  }

  /**
   * 准备删除
   */
  const handleDel = async (currentRowKey: string) => {
    try {
      // 如果已经有正在进行的操作，则拒绝新操作
      if (iAction.value !== undefined) {
        return
      }

      beforeAction('del', currentRowKey)

      dialogVisible.value = true
    } catch (e) {
      ElMessage.warning(<string>e)
      resetAction()
      doLoad()
    }
  }

  /**
   * 执行删除
   */
  const doDel = async () => {
    if (!await beforeDoAction('del')) {
      return
    }

    dialogLoading.value = true
    rDel(iCurrentRowKey.value)
      .then(() => {
        ElMessage.success('操作成功')
        closeDialog()
        doLoad()
      })
      .catch(err => {
        ElMessage.warning(err)
      })
      .finally(() => {
        dialogLoading.value = false
      })
  }

  /**
   * 准备修改
   */
  const handleUpdate = async (currentRowKey: string) => {
    try {
      // 如果已经有正在进行的操作，则拒绝新操作
      if (iAction.value !== undefined) {
        return
      }

      beforeAction('update', currentRowKey)

      await getCurrentRow()
      formData.value = { ...iCurrentRow.value }

      dialogVisible.value = true
    } catch (e) {
      ElMessage.warning(<string>e)
      resetAction()
      doLoad()
    }
  }

  /**
   * 执行修改
   */
  const doUpdate = async () => {
    formRef.value?.validate(async (isValid: boolean) => {
      if (!isValid) {
        return
      }

      if (!await beforeDoAction('update')) {
        return
      }

      const diff = getDiff(formData.value, iCurrentRow.value)
      if (diff[keyField] === undefined) {
        diff[keyField] = iCurrentRowKey.value
      }

      dialogLoading.value = true
      rUpdate(<any>diff)
        .then(() => {
          ElMessage.success('操作成功')
          closeDialog()
          doLoad()
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          dialogLoading.value = false
        })
    })
  }

  /**
   * 准备查看
   */
  const handleSee = async (currentRowKey: string) => {
    try {
      // 如果已经有正在进行的操作，则拒绝新操作
      if (iAction.value !== undefined) {
        return
      }

      beforeAction('see', currentRowKey)

      await getCurrentRow()
      formData.value = { ...iCurrentRow.value }

      dialogVisible.value = true
    } catch (e) {
      ElMessage.warning(<string>e)
      resetAction()
      doLoad()
    }
  }

  return {
    dialogVisible,
    dialogLoading,
    formRef,
    formData,
    action: iAction,
    actionDescription,
    isGettingCurrentRow,
    handleAdd,
    doAdd,
    handleDel,
    doDel,
    handleUpdate,
    doUpdate,
    handleSee,
    closeDialog,
    resetAction
  }
}
