/**
 * 表单校验规则
 */
export default function useRules() {

  /**
   * 获取校验规则数组
   * @param field 字段列表
   */
  const getRules = (field: crud.field) => {
    if (!field.formConf?.rules) {
      return []
    }
    return field.formConf?.rules
      .map(item => getRule(item, field.name))
      .filter(item => item != null)
  }

  /**
   * 将规则枚举转换为对应的校验规则对象
   */
  const getRule = (rule: crud.rule, name: string) => {
    switch (rule) {
      case 'required':
        return  {required: true, message: `请输入${name}`, trigger: 'blur'}
      default:
    }
  }

  return {
    getRules
  }
}
