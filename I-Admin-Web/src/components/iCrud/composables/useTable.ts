import { computed, ref, Ref, watchEffect } from 'vue'
import { ElMessage } from 'element-plus/es'
import 'element-plus/es/components/message/style/css'


/**
 * 表格
 * @param massGetConf 批量查询配置
 * @param queryParam 查询参数
 */
export default function useTable<T>(massGetConf: crud.massGetConf<T>, queryParam: Ref<crud.queryParam>) {

  // -- 数据相关 --
  /**
   * 数据
   */
  const tableData: Ref<unknown[]> = ref([])


  // -- 分页相关 --
  /**
   * 是否有分页
   */
  const hasPagination = computed(() => {
    return massGetConf.page
  })

  /**
   * 当前页数
   */
  const pageNum: Ref<number> = ref(1)

  /**
   * 每页条数
   */
  const pageSize: Ref<number> = ref(2)

  /**
   * 数据总数
   */
  const total: Ref<number> = ref(0)


  // -- 数据加载相关 --
  /**
   * 是否正在加载
   */
  const isLoading = ref(false)

  /**
   * 加载数据
   */
  let doLoad: () => void

  if (massGetConf.page) {
    doLoad = () => {
      isLoading.value = true
      massGetConf.func(pageNum.value, pageSize.value, queryParam.value)
        .then(res => {
          total.value = res.total
          tableData.value = res.records
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          isLoading.value = false
        })
    }

    watchEffect(
      (onCleanup) => {
        // 使watchEffect监听pageNum、pageSize
        let temp = {
          pageNum: pageNum.value,
          pageSize: pageSize.value
        }

        // 执行查询
        const timeout = setTimeout(() => {
          doLoad()
        }, 10)

        // 取消查询操作
        onCleanup(() => clearTimeout(timeout))
      }
    )
  } else {
    doLoad = () => {
      isLoading.value = true
      massGetConf.func(queryParam.value)
        .then(res => {
          tableData.value = res
        })
        .catch(err => {
          ElMessage.warning(err)
        })
        .finally(() => {
          isLoading.value = false
        })
    }

    doLoad()
  }

  return {
    tableData,
    hasPagination,
    pageNum,
    pageSize,
    total,
    isLoading,
    doLoad
  }
}
