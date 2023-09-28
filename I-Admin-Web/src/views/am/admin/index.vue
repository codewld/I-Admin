<!--权限-后台用户管理-->
<script setup lang="ts">
import ICrud from '@/components/iCrud/i-crud.vue'
import IStatus from '@/components/iStatus/i-status.vue'
import IDataContainer from '@/components/iDataContainer/i-data-container.vue'
import { EditPen } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { rMarkList } from '@/api/am/role'


// -- CRUD相关 --
const crudRef = ref()

const requestConf: crud.RequestByPath = {
  path: '/am-admin',
  page: true
}

const fieldList: crud.Field[] = [
  {
    code: 'id',
    name: 'id',
    searchConf: { display: false },
    tableConf: { display: false },
    formConf: { add: false, update: false, see: false }
  },
  { code: 'username', name: '用户名', formConf: { rules: [ 'required' ] } },
  {
    code: 'password',
    name: '密码',
    searchConf: { display: false },
    tableConf: { display: false },
    formConf: { rules: [ 'required' ], update: false, see: false }
  },
  { code: 'role', name: '角色' },
  { code: 'nickName', name: '昵称' },
  { code: 'email', name: '邮箱' },
  {
    code: 'status',
    name: '启用状态',
    searchConf: { operator: 'EQ' },
    formConf: { rules: [ 'required' ], addDefault: 10 }
  },
  {
    code: 'lastLoginTime',
    name: '最后登录时间',
    searchConf: { display: false },
    tableConf: { display: false },
    formConf: { add: false, update: false }
  },
  { code: 'note', name: '备注', searchConf: { display: false }, tableConf: { display: false } },
  {
    code: 'createTime',
    name: '创建时间',
    searchConf: { display: false },
    tableConf: { display: false },
    formConf: { add: false, update: false }
  },
  {
    code: 'updateTime',
    name: '更新时间',
    searchConf: { display: false },
    tableConf: { display: false },
    formConf: { add: false, update: false }
  }
]

const orders: crud.Order[] = [
  { field: 'create_time', type: 'DESC' }
]


// -- 修改密码相关 --
/**
 * 是否修改密码
 */
const changePassword = ref(false)

/**
 * 准备修改密码
 */
const handleChangePassword = () => {
  changePassword.value = true
  crudRef.value.handleAction('update')
}

/**
 * 恢复状态为不修改密码
 */
const resetChangePassword = () => {
  changePassword.value = false
}


// -- 角色相关 --
/**
 * 获取角色名
 * @param role 角色编码
 * @param roleMarkList 角色标记列表
 */
const getRoleName = (role: string, roleMarkList: common.KVObj[]) => {
  return roleMarkList.find(i => i.code === role)?.name
}
</script>

<template>
  <i-crud
      ref="crudRef"
      :request-conf="requestConf"
      :field-list="fieldList"
      :orders="orders"
      @after-action="resetChangePassword"
  >
    <!--自定义搜索-->
    <template #search-item-role="{ row, placeholder }">
      <i-data-container :request-conf="rMarkList" :cache-conf="{ key: 'am-role-mark-list' }">
        <template #default="{ data }">
          <el-select v-model="row.role" :placeholder="placeholder" clearable class="w-full">
            <el-option v-for="item in data" :key="item.code" :value="item.code" :label="item.name" />
          </el-select>
        </template>
      </i-data-container>
    </template>
    <template #search-item-status="{ row, placeholder }">
      <el-select v-model="row.status" :placeholder="placeholder" clearable class="w-full">
        <el-option :value="10" label="启用" />
        <el-option :value="0" label="禁用" />
      </el-select>
    </template>
    <!--自定义表格按钮-->
    <template #table-button-rear="{ currentRow, currentRowKey, isLoading, hasAction }">
      <el-button
          :disabled="!currentRow || isLoading || hasAction"
          @click="handleChangePassword"
          :loading="changePassword"
          :icon="EditPen"
          type="info"
      >
        改密
      </el-button>
    </template>
    <!--自定义表格-->
    <template #table-column-role="{ row }">
      <i-data-container :request-conf="rMarkList" :cache-conf="{ key: 'am-role-mark-list' }">
        <template #default="{ data }">
          {{ getRoleName(row.role, data) }}
        </template>
      </i-data-container>
    </template>
    <template #table-column-status="{ row }">
      <i-status :status="row.status === 10" />
    </template>
    <!--自定义表单-->
    <template #form-item-role="{ row, disabled, placeholder }">
      <i-data-container :request-conf="rMarkList" :cache-conf="{ key: 'am-role-mark-list' }">
        <template #default="{ data }">
          <el-select v-model="row.role" :disabled="disabled" :placeholder="placeholder" clearable class="w-full">
            <el-option v-for="item in data" :key="item.code" :value="item.code" :label="item.name" />
          </el-select>
        </template>
      </i-data-container>
    </template>
    <template #form-item-status="{ row, disabled, placeholder }">
      <el-select v-model="row.status" :disabled="disabled" :placeholder="placeholder" clearable class="w-full">
        <el-option :value="10" label="启用" />
        <el-option :value="0" label="禁用" />
      </el-select>
    </template>

    <!--自定义修改密码表单-->
    <template v-if="changePassword" #form="{ row }">
      <el-form-item prop="password" label="密码" :rules="{ required: true, message: '请输入密码', trigger: 'blur' }">
        <el-input v-model.trim="row.password" placeholder="请输入密码" />
      </el-form-item>
    </template>
  </i-crud>
</template>
