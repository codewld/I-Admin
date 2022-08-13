<!--管理页面布局-侧边栏-->
<script setup lang="ts">
import { useRoute } from 'vue-router'
import { computed, ref, nextTick } from 'vue'
import { Fold, Expand, User, House } from '@element-plus/icons-vue'
import { useLayoutStore } from '@/store/modules/layoutState'

// -- 路由相关 --
const route = useRoute()

/** 当前路由 */
const activeRoute = computed(() => route.path)


// -- 侧边栏相关 --
const layoutStore = useLayoutStore()

/** 侧边栏切换按钮是否可见 */
const AsideTriggerVisible = ref(true)

/**
 * 切换侧边栏
 */
const triggerAside = async () => {
  AsideTriggerVisible.value = false
  await nextTick()
  layoutStore.setAsideFold()
  setTimeout(() => {
    AsideTriggerVisible.value = true
  }, 300)
}
</script>

<template>
  <el-aside :width="layoutStore.getAsideWidth" class="duration-300 overflow-x-hidden">
    <el-menu router :default-active="activeRoute" :collapse="layoutStore.isAsideFold" class="h-full select-none">
      <div class="h-8 flex justify-center items-center border-b">
        <el-tooltip :content="layoutStore.isAsideFold ? '展开菜单' : '折叠菜单'">
          <el-icon v-if="AsideTriggerVisible" :size="16" @click="triggerAside" class="cursor-pointer">
            <fold v-if="!layoutStore.isAsideFold"/>
            <expand v-else/>
          </el-icon>
        </el-tooltip>
      </div>
      <el-menu-item index="/home">
        <el-icon><house/></el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="/am">
        <template #title>
          <el-icon><user/></el-icon>
          <span>权限管理</span>
        </template>
        <el-menu-item index="/am/admin">
          <span>后台用户管理</span>
        </el-menu-item>
        <el-menu-item index="/am/role">
          <span>角色管理</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-aside>
</template>
