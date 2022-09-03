import { defineComponent } from 'vue'
import { ElScrollbar, ElSpace } from 'element-plus'
import 'element-plus/es/components/scrollbar/style/css'
import 'element-plus/es/components/space/style/css'
import './css/index.css'


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
            {this.$slots.default?.().filter(o => typeof o.type !== 'symbol' || o.type.description !== 'Comment') }
          </el-space>
        </el-scrollbar>
      </div>
    )
  }
})
