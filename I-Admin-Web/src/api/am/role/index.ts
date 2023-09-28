import request from '@/api/index'


const PREFIX = '/am-role'

/**
 * 标记列表查询
 * <p>仅包含编码及名称
 */
export function rMarkList() {
  return request(PREFIX + '/list', 'post', undefined, { fields: [ 'code', 'name' ] })
}
