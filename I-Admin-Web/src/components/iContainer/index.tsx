import { defineComponent } from 'vue'
import { ElScrollbar, ElSpace } from 'element-plus'
import 'element-plus/es/components/scrollbar/style/css'
import 'element-plus/es/components/space/style/css'
import './index.css'


/**
 * 容器组件
 * <p>集成了el-scrollbar和el-space，适用于布局多个垂直块元素
 * <p>写成jsx形式以避免el-space误处理注释导致其占据空间
 */
export default defineComponent({
  components: {
    ElScrollbar,
    ElSpace
  },
  render() {
    return (
      <div class="scrollbar-container">
        <el-scrollbar>
          <el-space direction="vertical" fill>
            {/* 读取默认插槽中的内容，排除注释 */}
            {this.$slots.default?.().filter(o => typeof o.type !== 'symbol')}
          </el-space>
        </el-scrollbar>
      </div>
    )
  }
})
