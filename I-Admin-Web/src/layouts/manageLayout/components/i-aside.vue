<!--管理页面布局-侧边栏-->
<script setup lang="ts">
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { Fold, Expand, User, House } from '@element-plus/icons-vue'
import useLayout from '@/composables/useLayout'


// -- 路由相关 --
/** 当前路由 */
const activeRoute = computed(() => useRoute().path)


// -- 布局相关 --
const { triggerAside, getAsideWidth, isAsideFold } = useLayout()
</script>

<template>
  <el-scrollbar :class="{fold: isAsideFold()}">
    <el-menu
        router
        :default-active="activeRoute"
        :collapse="isAsideFold()"
        :width="getAsideWidth()"
        :collapse-transition="false"
        class="h-full select-none"
    >
      <div class="h-8 flex justify-center items-center border-b">
        <el-tooltip :content="isAsideFold() ? '展开菜单' : '折叠菜单'">
          <el-icon :size="16" @click="triggerAside" class="cursor-pointer">
            <fold v-if="!isAsideFold()" />
            <expand v-else />
          </el-icon>
        </el-tooltip>
      </div>
      <el-menu-item index="/home">
        <el-icon>
          <house />
        </el-icon>
        <span>
          首页
        </span>
      </el-menu-item>
      <el-sub-menu index="/am">
        <template #title>
          <el-icon>
            <user />
          </el-icon>
          <span>
            权限管理
          </span>
        </template>
        <el-menu-item index="/am/admin">
          <span>
            后台用户管理
          </span>
        </el-menu-item>
        <el-menu-item index="/am/role">
          <span>
            角色管理
          </span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-scrollbar>
</template>

<style scoped>
.el-scrollbar {
  overflow: unset;
}

:deep(.el-scrollbar__view) {
  height: 100%;
}

.el-scrollbar, .el-scrollbar :deep(.el-scrollbar__wrap) {
  width: 205px;
}

.el-scrollbar.fold, .el-scrollbar.fold :deep(.el-scrollbar__wrap) {
  width: 65px;
}
</style>
