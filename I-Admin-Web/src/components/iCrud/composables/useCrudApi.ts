import request from '@/api'


/**
 * Crud Api
 */
export default function useCrudApi(requestConf: crud.RequestConf) {

  /**
   * 添加
   */
  let rAdd: crud.AddFunc<common.KVObj> | undefined

  /**
   * 删除
   */
  let rDel: crud.DelFunc | undefined

  /**
   * 修改
   */
  let rUpdate: crud.UpdateFunc<common.KVObj> | undefined

  /**
   * 查询
   */
  let rGet: crud.GetFunc<common.KVObj> | undefined

  /**
   * 批量查询配置
   */
  let massGetConf: crud.MassGetConf

  if ((<crud.RequestByPath>requestConf).path !== undefined) {
    const path = (<crud.RequestByPath>requestConf).path
    rAdd = (data) => request(path, 'post', undefined, data)
    rDel = (id) => request(`${ path }/${ id }`, 'delete')
    rUpdate = (data) => request(`${ path }`, 'put', undefined, data)
    rGet = (id) => request(`${ path }/${ id }`, 'get')
    if ((<crud.RequestByPath>requestConf).page ?? true) {
      massGetConf = {
        page: true,
        func: (pageNum: number, pageSize: number, queryParam: crud.QueryParam) =>
          request(`${ path }/page`, 'post', { pageNum: pageNum, pageSize: pageSize }, queryParam)
      }
    } else {
      massGetConf = {
        page: false,
        func: (queryParam: crud.QueryParam) =>
          request(`${ path }/list`, 'post', undefined, queryParam)
      }
    }
  } else if ((<crud.RequestByFuncPage>requestConf).pageFunc) {
    const iRequestConf = <crud.RequestByFuncPage>requestConf
    rAdd = iRequestConf.addFunc
    rDel = iRequestConf.delFunc
    rUpdate = iRequestConf.updateFunc
    rGet = iRequestConf.getFunc
    massGetConf = {
      page: true,
      func: iRequestConf.pageFunc
    }
  } else {
    const iRequestConf = <crud.RequestByFuncList>requestConf
    rAdd = iRequestConf.addFunc
    rDel = iRequestConf.delFunc
    rUpdate = iRequestConf.updateFunc
    rGet = iRequestConf.getFunc
    massGetConf = {
      page: false,
      func: iRequestConf.listFunc
    }
  }

  return {
    rAdd,
    rDel,
    rUpdate,
    rGet,
    massGetConf
  }

}
