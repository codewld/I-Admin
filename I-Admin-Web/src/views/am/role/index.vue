<!--权限-角色管理-->
<script setup lang="ts">
import ICrud from '@/components/iCrud/i-crud.vue'


const requestConf: crud.requestByPath = {
  path: '/am-role',
  page: true
}

const fieldList: crud.field[] = [
  {code: 'id', name: 'id', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false, see: false}},
  {code: 'code', name: '编码', formConf: {rules: ['required']}},
  {code: 'name', name: '名称', formConf: {rules: ['required']}},
  {code: 'note', name: '备注', searchConf: {display: false}, tableConf: {display: false}},
  {code: 'createTime', name: '创建时间', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false}},
  {code: 'updateTime', name: '更新时间', searchConf: {display: false}, tableConf: {display: false}, formConf: {add: false, update: false}}
]

const orders: crud.order[] = [
  {field: 'create_time', type: 'DESC'}
]
</script>

<template>
  <i-crud :request-conf="requestConf" :field-list="fieldList" :orders="orders">
  </i-crud>
</template>
