package pers.codewld.iadmin.api.service;

import pers.codewld.iadmin.api.model.param.LoginParam;

/**
 * 系统-账户 Service接口
 */
public interface SysAccountService {

    /**
     * 登录
     */
    String login(LoginParam loginParam);

}
