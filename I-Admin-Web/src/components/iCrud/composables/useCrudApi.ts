import request from '@/api'


/**
 * Crud Api
 */
export default function useCrudApi<T>(requestConf: crud.requestConf<T>) {

  /**
   * 添加
   */
  let rAdd: crud.addFunc<T>

  /**
   * 删除
   */
  let rDel: crud.delFunc

  /**
   * 修改
   */
  let rUpdate: crud.updateFunc<T>

  /**
   * 查询
   */
  let rGet: crud.getFunc<T>

  /**
   * 批量查询配置
   */
  let massGetConf: crud.massGetConf<T>

  if ((<crud.requestByPath>requestConf).path !== undefined) {
    const path = (<crud.requestByPath>requestConf).path
    rAdd = (data) => request(path, 'post', undefined, data)
    rDel = (id) => request(`${ path }/${ id }`, 'delete')
    rUpdate = (data) => request(`${ path }`, 'put', undefined, data)
    rGet = (id) => request(`${ path }/${ id }`, 'get')
    if ((<crud.requestByPath>requestConf).page ?? true) {
      massGetConf = {page: true, func: (pageNum: number, pageSize: number, queryParam: crud.queryParam) => request(`${ path }/page`, 'post', {pageNum: pageNum, pageSize: pageSize}, queryParam)}
    } else {
      massGetConf = {page: false, func: (queryParam: crud.queryParam) => request(`${ path }/list`, 'post', undefined, queryParam)}
    }
  } else if ((<crud.requestByFuncPage>requestConf).pageFunc) {
    const iRequestConf = <crud.requestByFuncPage>requestConf
    rAdd = iRequestConf.addFunc
    rDel = iRequestConf.delFunc
    rUpdate = iRequestConf.updateFunc
    rGet = iRequestConf.getFunc
    massGetConf = {page: true, func: iRequestConf.pageFunc}
  } else {
    const iRequestConf = <crud.requestByFuncList>requestConf
    rAdd = iRequestConf.addFunc
    rDel = iRequestConf.delFunc
    rUpdate = iRequestConf.updateFunc
    rGet = iRequestConf.getFunc
    massGetConf = {page: false, func: iRequestConf.listFunc}
  }

  return {
    rAdd,
    rDel,
    rUpdate,
    rGet,
    massGetConf
  }

}
