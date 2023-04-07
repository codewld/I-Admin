import request from '@/api'


/**
 * hello
 */
export function rHello(): Promise<string> {
  return request('/hello', 'get')
}
