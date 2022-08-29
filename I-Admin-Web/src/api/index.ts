import axios, { AxiosRequestConfig, Method } from 'axios'
import { SERVER_HOST, SERVER_PORT, JWT_HEADER_FIELD } from '@/config'
import { unref } from 'vue'
import useLoading from '@/composables/useLoading'
import { useAccountStore } from '@/store'

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
    if (res.data.code === 0) {
      return res.data.data
    }
    return Promise.reject(res.data.msg)
  }, () => {
    return Promise.reject('响应错误')
  }
)

/**
 * 发送网络请求
 * @param path 请求路径
 * @param method HTTP方法
 * @param data 数据
 * @param isLoading 是否展示加载动画
 * @param loadingText 加载动画的提示文字
 * @return Promise 响应数据
 */
function request<T, R>(path: string, method: Method = 'get', data?: T, isLoading: boolean = false, loadingText: string = '加载中'): Promise<R> {

  const unrefData = unref(data)

  const requestConfig: AxiosRequestConfig = {
    url: path,
    method: method
  }

  if (['GET', 'get'].includes(method)) {
    requestConfig.params = unrefData
  } else {
    requestConfig.data = unrefData
  }

  return new Promise((resolve, reject) => {

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
      })
  })
}

export default request
