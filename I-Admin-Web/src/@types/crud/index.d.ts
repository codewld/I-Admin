declare namespace crud {

  /**
   * 操作
   */
  type action = 'add' | 'del' | 'update' | 'see'

  /**
   * 请求配置
   */
  type requestConf<T> = requestByPath | requestByFuncPage | requestByFuncList

  /**
   * 请求配置-基于路径
   */
  type requestByPath = {
    /** 请求路径 */
    path: string,
    /** 分页  [默认为true] */
    page?: boolean
  }

  /**
   * 请求配置-基于方法-分页
   */
  type requestByFuncPage = {
    addFunc: addFunc<T>,
    delFunc: delFunc,
    updateFunc: updateFunc<T>,
    getFunc: getFunc<T>,
    pageFunc: pageFunc<T>
  }

  /**
   * 请求配置-基于方法-列表
   */
  type requestByFuncList = {
    addFunc: addFunc<T>,
    delFunc: delFunc,
    updateFunc: updateFunc<T>,
    getFunc: getFunc<T>,
    listFunc: listFunc<T>
  }

  /**
   * 添加
   */
  type addFunc<T> = (data: T) => Promise<string>

  /**
   * 删除
   */
  type delFunc = (id: string | number) => Promise<string>

  /**
   * 修改
   */
  type updateFunc<T> = (data: T) => Promise<string>

  /**
   * 查询
   */
  type getFunc<T> = (id: string | number) => Promise<T>

  /**
   * 分页查询
   */
  type pageFunc<T> = (pageNum: number, pageSize: number, queryParam: crud.queryParam) => Promise<pageData<T>>

  /**
   * 列表查询
   */
  type listFunc<T> = (queryParam: crud.queryParam) => Promise<T[]>

  /**
   * 批量查询配置
   */
  type massGetConf<T> = {page: true, func: crud.pageFunc<T>} | {page: false, func: crud.listFunc<T>}

  /**
   * 查询参数  [适用于批量查询]
   */
  type queryParam = {
    /** 查询参数 */
    fields?: string[],
    /** 条件列表 */
    conditions?: condition[],
    /** 排序列表 */
    orders?: orders[]
  }

  /**
   * 查询参数-条件
   */
  type condition = {
    /** 字段名 */
    field: string,
    /** 值 */
    value: string[],
    /** 操作符 */
    operator: operator
  }

  /**
   * 操作符
   */
  type operator = 'EQ' | 'NE' | 'GT' | 'GE' | 'LT' | 'LE' | 'BETWEEN' | 'NOT_BETWEEN' | 'LIKE' | 'NOT_LIKE' | 'LIKE_LEFT'
    | 'LIKE_RIGHT' | 'IS_NULL' | 'IS_NOT_NULL' | 'IN' | 'NOT_IN'

  /**
   * 查询参数-排序
   */
  type orders = {}

  /**
   * 分页查询结果
   */
  interface pageData<T> {
    /** 总数 */
    total: number,
    /** 数据列表 */
    records: T[]
  }

  /**
   * 字段信息
   */
  interface field {
    /** 编码 */
    code: string,
    /** 名称 */
    name: string,
    /** 搜索配置 */
    searchConf?: searchConf,
    /** 表格配置 */
    tableConf?: tableConf,
    /** 表单配置 */
    formConf?: formConf,
  }

  /**
   * 搜索配置
   */
  interface searchConf {
    /** 显示  [默认为true] */
    display?: boolean,
    /** 操作符  [默认为LIKE] */
    operator?: operator
  }

  /**
   * 表格配置
   */
  interface tableConf {
    /** 显示  [默认为true] */
    display?: boolean,
    /** 宽度 */
    width?: number,
    /** 最小宽度 */
    minWidth?: number,
    /** 侧边固定 */
    fixed?: 'left' | 'right',
    /** 对齐方式，默认为center */
    align?: 'left' | 'center' | 'right'
  }

  /**
   * 表单校验规则
   */
  type rule = 'required'

  /**
   * 表单配置
   */
  interface formConf {
    /** 添加时可见 */
    add?: boolean,
    /** 修改时可见 */
    update?: boolean,
    /** 查看时可见 */
    see?: boolean,
    /** 表单校验规则 */
    rules?: rule[]
    /** 添加时默认值 */
    addDefault?: string,
  }

  /**
   * 排序
   */
  interface order {
    /** 字段名 */
    field: string,
    /** 排序方式  [默认为升序] */
    type?: 'ASC' | 'DESC'
  }

}
