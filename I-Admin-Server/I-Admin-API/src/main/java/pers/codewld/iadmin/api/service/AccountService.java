package pers.codewld.iadmin.api.service;

import pers.codewld.iadmin.api.model.param.LoginParam;

/**
 * 账户 Service接口
 */
public interface AccountService {

    /**
     * 登录
     */
    String login(LoginParam loginParam);

}
