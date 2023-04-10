import axios, { AxiosRequestConfig, Method } from 'axios'
import { SERVER_HOST, SERVER_PORT, JWT_HEADER_FIELD } from '@/config'
import { unref } from 'vue'
import useLoading from '@/composables/useLoading'
import { useAccountStore } from '@/store'
import useAccount from '@/composables/useAccount'


/** axios实例 */
const instance = axios.create({
  baseURL: `http://${ SERVER_HOST }:${ SERVER_PORT }`,
  timeout: 5000
})

/**
 * 请求拦截器
 */
instance.interceptors.request.use(
  (config: any) => {
    const accountStore = useAccountStore()
    if (accountStore.isLoggedIn) {
      config.headers[JWT_HEADER_FIELD] = accountStore.JWT
    }
    return config
  }, () => {
    return Promise.reject('网络错误')
  }
)

/**
 * 响应拦截器
 */
instance.interceptors.response.use(
  res => {
    // 请求成功
    if (res.data.code === 0) {
      return res.data.data
    }
    // 身份验证错误
    if (res.data.code >= 8000 && res.data.code < 9000) {
      const { logout } = useAccount()
      logout()
      return Promise.reject('身份验证失败，自动退出登录')
    }
    return Promise.reject(res.data.msg)
  }, () => {
    return Promise.reject('响应错误')
  }
)

/**
 * 请求Map
 * <p>将正在进行中的请求保存，用于避免重复请求
 */
const requestMap: Map<string, Promise<unknown>> = new Map()

/**
 * 发送网络请求
 * @param path 请求路径
 * @param method HTTP方法
 * @param params URL参数
 * @param data 请求体参数
 * @param isLoading 是否展示加载动画
 * @param loadingText 加载动画的提示文字
 * @param forceRequest 强制请求  [为true时，即使有正在进行的相同请求，也会始终发起请求；默认为false]
 * @return Promise 响应数据
 */
function request<P, T, R>(path: string, method: Method = 'get', params?: P, data?: T, isLoading: boolean = false, loadingText: string = '加载中', forceRequest: boolean = false): Promise<R> {

  const requestConfig: AxiosRequestConfig = {
    url: path,
    method: method
  }

  requestConfig.params = unref(params)
  requestConfig.data = unref(data)

  const requestConfigStr = JSON.stringify(requestConfig)

  if (!forceRequest && requestMap.has(requestConfigStr)) {
    return <Promise<R>>requestMap.get(requestConfigStr)
  }

  const request: Promise<R> = new Promise((resolve, reject) => {

    const { startLoading, endLoading } = useLoading()
    const loadingConfig = {
      text: loadingText
    }
    isLoading && startLoading(loadingConfig)

    instance.request<any, R>(requestConfig)
      .then(res => {
        resolve(res)
      })
      .catch(err => {
        reject(err)
      })
      .finally(() => {
        isLoading && endLoading()
        requestMap.delete(requestConfigStr)
      })
  })

  requestMap.set(requestConfigStr, request)

  return request
}

export default request
