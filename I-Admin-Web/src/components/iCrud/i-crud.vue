<script setup lang="ts">
import { computed, PropType } from 'vue'
import { Plus, Delete, Edit, View, WarningFilled } from '@element-plus/icons-vue'
import IContainer from '@/components/iContainer/i-container'
import ICard from '@/components/iCard/i-card.vue'
import useCrudApi from '@/components/iCrud/composables/useCrudApi'
import useCurrentRow from '@/components/iCrud/composables/useCurrentRow'
import useTable from '@/components/iCrud/composables/useTable'
import useCrud from '@/components/iCrud/composables/useCrud'
import useRules from '@/components/iCrud/composables/useRules'
import useSearch from '@/components/iCrud/composables/useSearch'
import useQueryParam from '@/components/iCrud/composables/useQueryParam'


// -- props --
const props = defineProps({
  /**
   * 请求配置
   */
  requestConf: {
    type: Object as PropType<crud.RequestConf>,
    required: true
  },
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<crud.Field[]>,
    required: true
  },
  /**
   * 主键字段
   */
  keyField: {
    type: String,
    default: () => 'id'
  },
  /**
   * 排序列表
   */
  orders: {
    type: Array as PropType<crud.Order[]>,
    default: () => []
  },
  /**
   * 按钮列表
   */
  buttonList: {
    type: Array as PropType<crud.Action[]>,
    default: () => [ 'add', 'del', 'update', 'see' ]
  },
  /**
   * 是否有搜索框
   */
  hasSearch: {
    type: Boolean,
    default: () => true
  }
})


// -- emits --
const emits = defineEmits<{
  (e: 'beforeAction', action: crud.Action): void
  (e: 'afterAction', action: crud.Action): void
  (e: 'afterActionSuccess', action: crud.Action): void
}>()


// -- api相关 --
const {
  massGetConf
} = useCrudApi(props.requestConf)


// -- 搜索相关 --
const {
  searchFormData,
  resetSearch,
  doSearch
} = useSearch(() => doLoad())


// -- 查询参数相关 --
const {
  queryParam
} = useQueryParam(searchFormData, props.fieldList, props.keyField, props.orders)


// -- table相关 --
const {
  tableData,
  hasPagination,
  pageNum,
  pageSize,
  total,
  isLoading,
  doLoad
} = useTable(massGetConf, queryParam)

// -- 当前行相关 --
const {
  currentRow,
  currentRowKey,
  handleCurrentRowChange
} = useCurrentRow(props.keyField)

// -- CRUD相关 --
const {
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
} = useCrud(
    props.keyField,
    props.fieldList,
    props.requestConf,
    currentRowKey,
    (action) => {
      emits('beforeAction', action)
    },
    (action) => {
      emits('afterAction', action)
    },
    (action) => {
      emits('afterActionSuccess', action)
      doLoad()
    }
)


// -- 表单校验规则相关 --
const {
  getRules
} = useRules()


// -- 界面相关 --
/**
 * 搜索区表单项标签宽度
 */
const searchFormItemLabelWidth = computed(() => {
  const fieldNameLengthList = props.fieldList
      ?.filter(i => i.searchConf === undefined || i.searchConf.display === undefined || i.searchConf.display)
      .map(i => i.name?.length ?? 0)
  const maxFieldNameLength = fieldNameLengthList.length == 0 ? 0 : Math.max(...fieldNameLengthList)
  return `${ (maxFieldNameLength + 1) * 14 + 12 }px`
})


// -- expose --
defineExpose({
  currentRow,
  doLoad,
  handleAction,
  formData,
  tableData
})
</script>

<template>
  <i-container>
    <!--搜索区-->
    <i-card v-if="hasSearch" title="搜索区">
      <!--按钮区-->
      <template #button>
        <el-button :disabled="isLoading" @click="resetSearch">重置</el-button>
        <el-button :disabled="isLoading" type="primary" @click="doSearch">搜索</el-button>
      </template>

      <!--表单区-->
      <el-form
          :model="searchFormData"
          v-loading="isLoading"
          inline
          :label-width="searchFormItemLabelWidth"
          style="margin-bottom: -18px"
      >
        <slot name="search" :row="searchFormData">
          <slot name="search-item-front" />
          <template v-for="(field, key) in fieldList" :key="key">
            <el-form-item
                v-if="field?.searchConf?.display ?? true"
                :label="`${field.name}：`"
                class="overflow-hidden w-full md:w-1/2D32 xl:w-1/3D32"
            >
              <slot
                  :name="`search-item-${field.code}`"
                  :row="searchFormData"
                  :placeholder="`请输入${field.name}`"
              >
                <el-input
                    v-model.trim="searchFormData[field.code]"
                    :placeholder="`请输入${field.name}`"
                    clearable
                />
              </slot>
            </el-form-item>
          </template>
          <slot name="search-item-rear" />
        </slot>
      </el-form>
    </i-card>

    <!--数据区-->
    <i-card title="数据区">
      <!--按钮区-->
      <template #button>
        <slot
            name="table-button-front"
            :currentRow="currentRow"
            :currentRowKey="currentRowKey"
            :isLoading="isLoading"
            :action="action"
            :hasAction="hasAction"
        />
        <el-button
            v-if="buttonList?.includes('add')"
            :disabled="isLoading || hasAction"
            @click="handleAction('add')"
            :loading="action === 'add'"
            :icon="Plus"
            type="primary"
        >
          添加
        </el-button>
        <el-button
            v-if="buttonList?.includes('del')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleAction('del')"
            :loading="action === 'del'"
            :icon="Delete"
            type="danger"
        >
          删除
        </el-button>
        <el-button
            v-if="buttonList?.includes('update')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleAction('update')"
            :loading="action === 'update'"
            :icon="Edit"
            type="warning"
        >
          修改
        </el-button>
        <el-button
            v-if="buttonList?.includes('see')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleAction('see')"
            :loading="action === 'see'"
            :icon="View"
            type="success"
        >
          查看
        </el-button>
        <slot
            name="table-button-rear"
            :currentRow="currentRow"
            :currentRowKey="currentRowKey"
            :isLoading="isLoading"
            :action="action"
            :hasAction="hasAction"
        />
      </template>

      <!--表格区-->
      <el-table
          v-loading="isLoading"
          :data="tableData"
          @current-change="handleCurrentRowChange"
          :row-key="keyField"
          highlight-current-row
      >
        <slot name="table">
          <slot name="table-column-front" />
          <template v-for="(field, key) in fieldList" :key="key">
            <el-table-column
                v-if="field.tableConf?.display ?? true"
                :prop="field.code"
                :label="field.name"
                :width="field.tableConf?.width!"
                :min-width="field.tableConf?.minWidth!"
                :fixed="field.tableConf?.fixed!"
                :align="field.tableConf?.align ?? 'center'"
            >
              <template #default="{ row }">
                <slot :name="`table-column-${field.code}`" :row="row">
                  {{ row[field.code] }}
                </slot>
              </template>
            </el-table-column>
          </template>
          <slot name="table-column-rear" />
        </slot>
      </el-table>

      <!--分页区-->
      <el-pagination
          v-if="hasPagination"
          :page-sizes="[5, 10, 20]"
          :total="total"
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          class="justify-center mt-5"
      />
    </i-card>
  </i-container>

  <!--增、改、查 对话框-->
  <el-dialog
      v-model="dialogVisible"
      :title="actionDescription"
      :close-on-click-modal="false"
      destroy-on-close
      draggable
  >
    <el-form ref="formRef" :model="formData" v-loading="dialogLoading" inline label-position="top">
      <template v-if="action && ['add', 'update', 'see'].includes(action)">
        <slot name="form" :row="formData" :action="action">
          <slot name="form-item-front" :row="formData" :action="action" />
          <template v-for="(field, key) in fieldList" :key="key">
            <slot :name="`form-item-${field.code}-front`" :row="formData" :action="action" />
            <el-form-item
                v-if="field?.formConf?.[action] ?? true"
                :prop="field.code"
                :label="`${field.name}：`"
                :rules="getRules(field)"
                style="width: calc(50% - 32px)"
            >
              <slot
                  :name="`form-item-${field.code}`"
                  :row="formData"
                  :disabled="action === 'see'"
                  :placeholder="action === 'see' ? '' : `请输入${field.name}`"
              >
                <el-input
                    v-model.trim="formData[field.code]"
                    :disabled="action === 'see'"
                    :placeholder="action === 'see' ? '' : `请输入${field.name}`"
                />
              </slot>
            </el-form-item>
            <slot :name="`form-item-${field.code}-rear`" :row="formData" :action="action" />
          </template>
          <slot name="form-item-rear" :row="formData" :action="action" />
        </slot>
      </template>
      <template v-else>
        <slot name="form-del" :row="formData">
          <div class="flex items-center text-xl">
            <el-icon class="text-red-400 mr-2">
              <warning-filled />
            </el-icon>
            是否要删除？
          </div>
        </slot>
      </template>
    </el-form>
    <template #footer>
      <el-button
          :disabled="dialogLoading"
          @click="closeDialog"
      >
        关闭
      </el-button>
      <el-button
          v-if="action && ['add', 'del', 'update'].includes(action)"
          :loading="dialogLoading"
          type="primary"
          @click="doAction"
      >
        确认
      </el-button>
    </template>
  </el-dialog>
</template>
