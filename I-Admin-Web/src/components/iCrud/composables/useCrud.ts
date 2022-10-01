import { computed, Ref, ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
import { ElMessageBox } from 'element-plus/es'
import 'element-plus/es/components/message-box/style/css'


/**
 * CRUD
 * @param fieldList 字段列表
 * @param rGet 查询
 * @param rAdd 添加
 * @param rDel 删除
 * @param rUpdate 修改
 * @param doLoad 加载数据
 * @param beforeDoActionCallback 操作执行前的回调
 */
export default function useCrud<T>(
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


  // -- 状态相关 --
  /**
   * dialog是否可见
   */
  const dialogVisible = ref(false)

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
  const formData = ref()


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
    } catch (e) {
      ElMessage.warning(<string>e)
      throw e
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
  const beforeAction = async (action: crud.action, currentRowKey?: string) => {
    // 如果已经有正在进行的操作，则拒绝新操作
    if (iAction.value !== undefined) {
      throw '有正在进行的操作'
    }

    // 记录正在进行的操作
    iAction.value = action

    // 记录当前行主键值
    iCurrentRowKey.value = currentRowKey
  }


  // -- 后操作相关 --
  /**
   * 关闭Dialog
   */
  const closeDialog = () => {
    dialogVisible.value = false
  }

  /**
   * 重置状态
   * <p>应该在关闭dialog后调用此方法，避免界面异常
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
      const res = await beforeDoActionCallback(action, iCurrentRowKey.value, iCurrentRow.value, formData.value)
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
      await beforeAction('add')

      formData.value = {}
      fieldList.forEach(item => {
        formData.value[item.code] = item.formConf?.addDefault
      })

      dialogVisible.value = true
    } catch (e) {
      console.log(e)
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

      rAdd(formData.value)
        .then(() => {
          ElMessage.success('操作成功')
          doLoad()
          closeDialog()
        })
        .catch(err => {
          ElMessage.warning(err)
        })
    })
  }

  /**
   * 准备删除
   */
  const handleDel = async (currentRowKey: string) => {
    try {
      await beforeAction('del', currentRowKey)

      await ElMessageBox.confirm(
        '是否要进行删除?',
        '删除确认',
        {
          confirmButtonText: '删除',
          confirmButtonClass: 'el-button--danger',
          cancelButtonText: '取消',
          type: 'error',
          draggable: true
        }
      )

      if (!await beforeDoAction('del')) {
        return
      }

      rDel(iCurrentRowKey.value)
        .then(() => {
          ElMessage.success('操作成功')
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          doLoad()
          resetAction()
        })
    } catch (e) {
      console.log(e)
      resetAction()
    }
  }

  /**
   * 准备修改
   */
  const handleUpdate = async (currentRowKey: string) => {
    try {
      await beforeAction('update', currentRowKey)

      await getCurrentRow()
      formData.value = { ...iCurrentRow.value }

      dialogVisible.value = true
    } catch (e) {
      console.log(e)
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

      rUpdate(formData.value)
        .then(() => {
          ElMessage.success('操作成功')
          doLoad()
          closeDialog()
        })
        .catch(err => {
          ElMessage.warning(err)
        })
    })
  }

  /**
   * 准备查看
   */
  const handleSee = async (currentRowKey: string) => {
    try {
      await beforeAction('see', currentRowKey)

      await getCurrentRow()
      formData.value = { ...iCurrentRow.value }

      dialogVisible.value = true
    } catch (e) {
      console.log(e)
    }
  }

  return {
    dialogVisible,
    formRef,
    formData,
    action: iAction,
    actionDescription,
    isGetting: isGettingCurrentRow,
    handleAdd,
    doAdd,
    handleDel,
    handleUpdate,
    doUpdate,
    handleSee,
    closeDialog,
    resetAction
  }
}
