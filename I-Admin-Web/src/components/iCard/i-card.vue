<script setup lang="ts">
// -- props --
const props = defineProps({
  /**
   * 标题
   */
  title: {
    type: String,
    required: true
  }
})
</script>

<template>
  <el-card shadow="never">
    <template #header>
      <div class="h-10 flex justify-between items-center">
        <!--标题区-->
        <p class="text-base">
          {{ props.title }}
        </p>
        <!--按钮区-->
        <el-button-group>
          <slot name="button" />
        </el-button-group>
      </div>
    </template>
    <!--内容区-->
    <slot />
  </el-card>
</template>
