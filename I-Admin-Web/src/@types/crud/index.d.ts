declare namespace crud {

  /**
   * 操作
   */
  type Action = 'add' | 'del' | 'update' | 'see'

  /**
   * 请求配置
   */
  type RequestConf = RequestByPath | RequestByFuncPage | RequestByFuncList

  /**
   * 请求配置-基于路径
   */
  type RequestByPath = {
    /** 请求路径 */
    path: string,
    /** 分页  [默认为true] */
    page?: boolean
  }

  /**
   * 请求配置-基于方法-分页
   */
  type RequestByFuncPage = {
    addFunc?: AddFunc<common.KVObj>,
    delFunc?: DelFunc,
    updateFunc?: UpdateFunc<common.KVObj>,
    getFunc?: GetFunc<common.KVObj>,
    pageFunc: PageFunc<common.KVObj>
  }

  /**
   * 请求配置-基于方法-列表
   */
  type RequestByFuncList = {
    addFunc?: AddFunc<common.KVObj>,
    delFunc?: DelFunc,
    updateFunc?: UpdateFunc<common.KVObj>,
    getFunc?: GetFunc<common.KVObj>,
    listFunc: ListFunc<common.KVObj>
  }

  /**
   * 添加
   */
  type AddFunc<T> = (data: T) => Promise<void>

  /**
   * 删除
   */
  type DelFunc = (id: string) => Promise<void>

  /**
   * 修改
   */
  type UpdateFunc<T> = (data: T) => Promise<void>

  /**
   * 查询
   */
  type GetFunc<T> = (id: string) => Promise<T>

  /**
   * 分页查询
   */
  type PageFunc<T> = (pageNum: number, pageSize: number, queryParam: crud.QueryParam) => Promise<PageData<T>>

  /**
   * 列表查询
   */
  type ListFunc<T> = (queryParam: crud.QueryParam) => Promise<T[]>

  /**
   * 批量查询配置
   */
  type MassGetConf =
    {
      page: true,
      func: crud.PageFunc<common.KVObj>
    }
    |
    {
      page: false,
      func: crud.ListFunc<common.KVObj>
    }

  /**
   * 查询参数  [适用于批量查询]
   */
  type QueryParam = {
    /** 查询参数 */
    fields?: string[],
    /** 条件列表 */
    conditions?: Condition[],
    /** 排序列表 */
    orders?: Order[]
  }

  /**
   * 查询参数-条件
   */
  type Condition = {
    /** 字段名 */
    field: string,
    /** 值 */
    value: unknown[],
    /** 操作符 */
    operator: Operator
  }

  /**
   * 操作符
   */
  type Operator =
    'EQ'
    | 'NE'
    | 'GT'
    | 'GE'
    | 'LT'
    | 'LE'
    | 'BETWEEN'
    | 'NOT_BETWEEN'
    | 'LIKE'
    | 'NOT_LIKE'
    | 'LIKE_LEFT'
    | 'LIKE_RIGHT'
    | 'IS_NULL'
    | 'IS_NOT_NULL'
    | 'IN'
    | 'NOT_IN'

  /**
   * 查询参数-排序
   */
  interface Order {
    /** 字段名 */
    field: string,
    /** 排序方式  [默认为升序] */
    type?: 'ASC' | 'DESC'
  }

  /**
   * 分页查询结果
   */
  interface PageData<T> {
    /** 总数 */
    total: number,
    /** 数据列表 */
    records: T[]
  }

  /**
   * 字段信息
   */
  interface Field {
    /** 编码 */
    code: string,
    /** 名称 */
    name: string,
    /** 搜索配置 */
    searchConf?: SearchConf,
    /** 表格配置 */
    tableConf?: TableConf,
    /** 表单配置 */
    formConf?: FormConf
  }

  /**
   * 搜索配置
   */
  interface SearchConf {
    /** 显示  [默认为true] */
    display?: boolean,
    /** 操作符  [默认为LIKE] */
    operator?: Operator
  }

  /**
   * 表格配置
   */
  interface TableConf {
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
  type Rule = 'required'

  /**
   * 表单配置
   */
  interface FormConf {
    /** 添加时可见 */
    add?: boolean,
    /** 修改时可见 */
    update?: boolean,
    /** 查看时可见 */
    see?: boolean,
    /** 表单校验规则 */
    rules?: Rule[],
    /** 添加时默认值 */
    addDefault?: unknown
  }
}
