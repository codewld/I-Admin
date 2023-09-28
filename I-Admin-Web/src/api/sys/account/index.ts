import request from '@/api'
import { Ref } from 'vue'


const PREFIX = '/account'

/**
 * 登录
 */
export function rLogin(data: Ref<account.LoginParam>): Promise<string> {
  return request(PREFIX + '/login', 'post', undefined, data)
}
