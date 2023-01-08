<script setup lang="ts">
import { computed, PropType } from 'vue'
import { Plus, Delete, Edit, View, WarningFilled } from '@element-plus/icons-vue'
import IContainer from '@/components/iContainer'
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
    type: Object as PropType<crud.requestConf<unknown>>,
    required: true
  },
  /**
   * 字段列表
   */
  fieldList: {
    type: Array as PropType<crud.field[]>,
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
    type: Array as PropType<crud.order[]>,
    default: () => []
  },
  /**
   * 按钮列表
   */
  buttonList: {
    type: Array as PropType<crud.action[]>,
    default: () => ['add', 'del', 'update', 'see']
  },
  /**
   * 是否有搜索框
   */
  hasSearch: {
    type: Boolean,
    default: () => true
  },
  /**
   * 操作执行前的回调
   */
  beforeDoActionCallback: {
    type: Function as PropType<crud.beforeDoActionCallback<unknown>>,
    required: false
  }
})


// -- api 相关 --
const {
  rAdd,
  rDel,
  rUpdate,
  rGet,
  massGetConf } = useCrudApi<unknown>(props.requestConf)


// -- 搜索相关 --
const {
  searchFormData,
  resetSearch,
  doSearch } = useSearch(props.fieldList, () => doLoad())


// -- 查询参数相关 --
const {
  queryParam } = useQueryParam(searchFormData, props.fieldList, props.keyField, props.orders);


// -- table相关 --
const {
  tableData,
  hasPagination,
  pageNum,
  pageSize,
  total,
  isLoading,
  doLoad } = useTable(massGetConf, queryParam)


// -- 当前行相关 --
const {
  currentRow,
  currentRowKey,
  handleCurrentRowChange } = useCurrentRow(props.keyField)


// -- CRUD相关 --
const {
  dialogVisible,
  dialogLoading,
  formRef,
  formData,
  action,
  actionDescription,
  isGetting,
  handleAdd,
  doAdd,
  handleDel,
  doDel,
  handleUpdate,
  doUpdate,
  handleSee,
  closeDialog,
  resetAction } = useCrud(props.keyField, props.fieldList, rGet, rAdd, rDel, rUpdate, () => doLoad(), props.beforeDoActionCallback)

/**
 * 是否有当前正在进行的操作
 */
const hasAction = computed(() => {
  return action.value !== undefined
})


// -- 表单校验规则相关 --
const {
  getRules } = useRules()

defineExpose({
  currentRow,
  doLoad
})
</script>

<template>
  <i-container>
    <!--搜索区-->
    <i-card v-if="hasSearch"  title="搜索区">
      <!--按钮区-->
      <template #button>
        <el-button :disabled="isLoading" @click="resetSearch">重置</el-button>
        <el-button :disabled="isLoading" type="primary" @click="doSearch">搜索</el-button>
      </template>

      <!--表单区-->
      <el-form :model="searchFormData" v-loading="isLoading" inline label-width="120px">
        <slot name="search-item-front"/>
        <template v-for="(field, key) in fieldList" :key="key">
          <el-form-item
              v-if="field?.searchConf?.display ?? true"
              :label="`${field.name}：`"
              class="overflow-hidden"
              style="width: calc(33.3% - 32px)">
            <slot
                :name="`search-item-${field.code}`"
                :row="searchFormData"
                :placeholder="`请输入${field.name}`">
              <el-input
                  v-model.trim="searchFormData[field.code]"
                  :placeholder="`请输入${field.name}`"
                  clearable/>
            </slot>
          </el-form-item>
        </template>
        <slot name="search-item-rear"/>
      </el-form>
    </i-card>

    <!--数据区-->
    <i-card title="数据区">
      <!--按钮区-->
      <template #button>
        <slot name="table-button-front" :currentRow="currentRow"/>
        <el-button
            v-if="buttonList.includes('add')"
            :disabled="isLoading || hasAction"
            @click="handleAdd"
            :icon="Plus"
            type="primary">
          添加
        </el-button>
        <el-button
            v-if="buttonList.includes('del')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleDel(currentRowKey)"
            :icon="Delete"
            type="danger">
          删除
        </el-button>
        <el-button
            v-if="buttonList.includes('update')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleUpdate(currentRowKey)"
            :loading="action === 'update' && isGetting"
            :icon="Edit"
            type="warning">
          修改
        </el-button>
        <el-button
            v-if="buttonList.includes('see')"
            :disabled="!currentRow || isLoading || hasAction"
            @click="handleSee(currentRowKey)"
            :loading="action === 'see' && isGetting"
            :icon="View"
            type="success">
          查看
        </el-button>
        <slot name="table-button-rear" :currentRow="currentRow"/>
      </template>

      <!--表格区-->
      <el-table
          v-loading="isLoading"
          :data="tableData"
          @current-change="handleCurrentRowChange"
          :row-key="keyField"
          highlight-current-row>
        <slot name="table-column-front"/>
        <template v-for="(field, key) in fieldList" :key="key">
          <el-table-column
              v-if="field.tableConf?.display ?? true"
              :prop="field.code"
              :label="field.name"
              :width="field.tableConf?.width ?? undefined"
              :min-width="field.tableConf?.minWidth ?? undefined"
              :fixed="field.tableConf?.fixed ?? undefined"
              :align="field.tableConf?.align ?? 'center'">
            <template #default="{ row }">
              <slot :name="`table-column-${field.code}`" :row="row">
                {{ row[field.code] }}
              </slot>
            </template>
          </el-table-column>
        </template>
        <slot name="table-column-rear"/>
      </el-table>

      <!--分页区-->
      <el-pagination
          v-if="hasPagination"
          :page-sizes="[2, 4, 8, 16]"
          :total="total"
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          class="justify-center mt-5">
      </el-pagination>
    </i-card>
  </i-container>

  <!--增、改、查 对话框-->
  <el-dialog
      v-model="dialogVisible"
      @close="closeDialog"
      @closed="resetAction"
      :title="actionDescription"
      :close-on-click-modal="false"
      destroy-on-close
      draggable>
    <el-form ref="formRef" :model="formData" v-loading="dialogLoading" inline label-position="top">
      <slot name="form-item-front"/>
      <template v-if="['add', 'update', 'see'].includes(action)" v-for="(field, key) in fieldList" :key="key">
        <el-form-item
            v-if="field?.formConf?.[action] ?? true"
            :prop="field.code"
            :label="`${field.name}：`"
            :rules="getRules(field)"
            style="width: calc(50% - 32px)">
          <slot
              :name="`form-item-${field.code}`"
              :row="formData"
              :disabled="action === 'see'"
              :placeholder="action === 'see' ? '' : `请输入${field.name}`">
            <el-input
                v-model.trim="formData[field.code]"
                :disabled="action === 'see'"
                :placeholder="action === 'see' ? '' : `请输入${field.name}`"/>
          </slot>
        </el-form-item>
      </template>
      <template v-else>
        <div class="flex items-center text-xl">
          <el-icon class="text-red-400 mr-2">
            <warning-filled/>
          </el-icon>
          是否要删除？
        </div>
      </template>
      <slot name="form-item-rear"/>
    </el-form>
    <template #footer>
      <el-button :disabled="dialogLoading" @click="closeDialog">关闭</el-button>
      <el-button v-if="action === 'add'" :loading="dialogLoading" type="primary" @click="doAdd">确认</el-button>
      <el-button v-if="action === 'del'" :loading="dialogLoading" type="danger" @click="doDel">确认</el-button>
      <el-button v-if="action === 'update'" :loading="dialogLoading" type="primary" @click="doUpdate">确认</el-button>
    </template>
  </el-dialog>
</template>
