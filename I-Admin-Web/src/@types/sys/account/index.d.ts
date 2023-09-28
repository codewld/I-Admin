declare namespace account {

  /**
   * 登录参数
   */
  interface LoginParam {
    /** 用户名 */
    username: string,
    /** 密码 */
    password: string
  }

  /**
   * 账户信息
   */
  interface Account {
    /** 用户名 */
    username?: string,
    /** JWT */
    JWT?: string
  }

}
