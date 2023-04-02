/**
 * 获取对象与原对象的差异
 */
export function getDiff(newObj: common.KVObj<any>, oldObj: common.KVObj<any>): common.KVObj<any> {
  let diff: common.KVObj<any> = {}
  for (let key in newObj) {
    if (newObj[key] !== oldObj[key]) {
      diff[key] = newObj[key]
    }
  }
  return diff
}

/**
 * 判断对象是否为空
 */
export function isEmpty(obj?: any) {
  if (!obj) {
    return true
  }
  return Object.keys(obj).length === 0
}
