import ICrud from './i-crud.vue'


/**
 * ICrud增删改查组件
 * <p>特点:
 * <p>1. 方便地构建增删改查页面
 * <p>2. 传入字段列表配置字段信息、表格列展示方式、表单展示方式
 * <p>3. 支持通过方法、路径等方式配置请求
 * <p>4. 支持丰富的自定义功能
 * <p>Props:
 * <p>1. requestConf - 请求配置
 * <p>2. fieldList - 字段列表
 * <p>3. keyField - 主键字段
 * <p>4. orders - 排序列表
 * <p>5. buttonList - 按钮列表
 * <p>6. hasSearch - 是否有搜索框
 * <p>Slots:
 * <p>1. search-item-front - 搜索区搜索项头部
 * <p>2. search-item-`code` - 搜索区指定搜索项
 * <p>3. search-item-rear - 搜索区搜索项尾部
 * <p>4. table-button-front - 按钮区按钮项头部
 * <p>5. table-button-rear - 按钮区按钮项尾部
 * <p>6. table-column-front - 表格区表格列头部
 * <p>7. table-column-`code` - 表格区指定表格列
 * <p>8. table-column-rear - 表格区表格列尾部
 * <p>9. form-item-front - 对话框表单项头部
 * <p>10. table-column-`code` - 对话框指定表单项
 * <p>11. form-item-rear - 对话框表单项
 */
export default ICrud
