import { ref, Ref } from 'vue'


/**
 * 搜索
 * @param fieldList 字段列表
 * @param doLoad 加载数据
 */
export default function useSearch(fieldList: crud.field[], doLoad: () => void) {

  /**
   * 搜索表单数据
   */
  const searchFormData: Ref<common.KVObj> = ref({})

  /**
   * 重置搜索
   */
  const resetSearch = () => {
    searchFormData.value = {}
    doLoad()
  }

  /**
   * 执行搜索
   */
  const doSearch = () => {
    doLoad()
  }

  return {
    searchFormData,
    resetSearch,
    doSearch
  }
}
