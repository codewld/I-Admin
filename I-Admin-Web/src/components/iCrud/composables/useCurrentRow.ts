import { computed, ref, Ref } from 'vue'


/**
 * 当前行
 * @param keyField 主键字段
 */
export default function useCurrentRow(keyField: string) {

  /**
   * 当前行
   */
  const currentRow: Ref<common.KVObj> = ref({})

  /**
   * 当前行主键值
   */
  const currentRowKey = computed(() => {
    return currentRow.value?.[keyField]
  })

  /**
   * 当前行改变
   */
  const handleCurrentRowChange = (val: any) => {
    currentRow.value = val
  }

  return {
    currentRow,
    currentRowKey,
    handleCurrentRowChange
  }
}
