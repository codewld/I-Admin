import { computed, Ref } from 'vue'

/**
 * 查询参数
 * @param searchFormData 搜索表单数据
 * @param fieldList 字段列表
 * @param keyField 主键字段
 * @param orders 排序列表
 */
export default function useQueryParam(
  searchFormData: Ref<common.KVObj>,
  fieldList: crud.field[],
  keyField: string,
  orders: crud.order[]) {

  /**
   * 查询参数-条件
   */
  const conditions = computed(() => {
    const conditions: crud.condition[] = []
    for (let key in searchFormData.value) {
      if (searchFormData.value[key] === '') {
        break
      }
      const operator = fieldList.find(item => item.code === key)?.searchConf?.operator ?? 'LIKE'
      const condition: crud.condition = {
        field: key,
        value: [searchFormData.value[key]],
        operator: operator
      }
      conditions.push(condition)
    }
    if (conditions.length === 0) {
      return undefined
    }
    return conditions
  })

  /**
   * 查询参数
   */
  const queryParam: Ref<crud.queryParam> = computed(() => {
    // 字段列表
    const fields = fieldList
      .filter(item => item.tableConf?.display !== false)
      .map(item => item.code)
    if (!fields.includes(keyField)) {
      fields.push(keyField)
    }

    // 排序列表
    const iOrders: crud.order[] = orders
      .map(item => {
        return {
          field: item.field,
          type: item.type ?? 'ASC'
        }
      })

    return {
      fields: fields,
      conditions: conditions.value,
      orders: iOrders
    }
  })

  return {
    queryParam
  }
}
