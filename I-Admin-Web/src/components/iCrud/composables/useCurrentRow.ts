import { computed, ref, Ref } from 'vue'


/**
 * 当前行组合式函数
 * @param keyField 主键字段
 */
export default function useCurrentRow(keyField: string) {

  /**
   * 当前行
   */
  const currentRow = ref<common.KVObj>()

  /**
   * 当前行主键值
   */
  const currentRowKey: Ref<string> = computed(() => currentRow.value?.[keyField] as string)

  /**
   * 当前行改变
   */
  const handleCurrentRowChange = (val: common.KVObj) => {
    currentRow.value = val
  }

  return {
    currentRow,
    currentRowKey,
    handleCurrentRowChange
  }
}
