<!--权限-后台用户管理-->
<script setup lang="ts">
import ICrud from '@/components/iCrud/i-crud.vue'
import IStatus from '@/components/iStatus'
import md5 from 'md5'


const requestConf: crud.requestByPath = {
  path: '/am-admin',
  page: true
}

const fieldList: crud.field[] = [
  {code: 'id', name: 'id', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false, see: false}},
  {code: 'username', name: '用户名', formConf: {rules: ['required']}},
  {code: 'password', name: '密码', searchConf: {display: false}, tableConf: {display: false}, formConf: {rules: ['required']}},
  {code: 'nickName', name: '昵称'},
  {code: 'email', name: '邮箱'},
  {code: 'status', name: '启用状态', searchConf: {operator: 'EQ'}, formConf: {rules: ['required'], addDefault: 10}},
  {code: 'lastLoginTime', name: '最后登录时间', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false}},
  {code: 'note', name: '备注', searchConf: {display: false}, tableConf: {display: false}},
  {code: 'createTime', name: '创建时间', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false}},
  {code: 'updateTime', name: '更新时间', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false}}
]

const orders: crud.order[] = [
  {field: 'create_time', type: 'DESC'}
]

const beforeDoActionCallback: crud.beforeDoActionCallback<common.KVObj<any>> = async (
    action,
    currentRowKey,
    currentRow,
    formData) => {
  if (['add', 'update'].includes(action)) {
    // 对密码做MD5处理
    const password = formData?.['password']
    if (password) {
      return {
        formData: {...formData, ...{password: md5(password)}}
      }
    }
  }
}
</script>

<template>
  <i-crud :request-conf="requestConf" :field-list="fieldList" :orders="orders" :before-do-action-callback="beforeDoActionCallback">
    <!--自定义搜索-->
    <template #search-item-status="{ row, placeholder }">
      <el-select v-model="row.status" :placeholder="placeholder" clearable class="w-full">
        <el-option :value="10" label="启用"></el-option>
        <el-option :value="0" label="禁用"></el-option>
      </el-select>
    </template>
    <!--自定义表格-->
    <template #table-column-status="{ row }">
      <i-status :status="row.status === 10"/>
    </template>
    <!--自定义表单-->
    <template #form-item-status="{ row, disabled, placeholder }">
      <el-select v-model="row.status" :disabled="disabled" :placeholder="placeholder" clearable class="w-full">
        <el-option :value="10" label="启用"></el-option>
        <el-option :value="0" label="禁用"></el-option>
      </el-select>
    </template>
  </i-crud>
</template>
