declare namespace Account {

  /**
   * 登录参数
   */
  interface loginParam {
    /** 用户名 */
    username: string,
    /** 密码 */
    password: string
  }

  /**
   * 账户信息
   */
  interface account {
    /** 用户名 */
    username: string,
    /** JWT */
    JWT: string | undefined
  }

}
