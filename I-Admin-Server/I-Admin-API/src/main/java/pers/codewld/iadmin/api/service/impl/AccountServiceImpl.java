package pers.codewld.iadmin.api.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.api.model.param.LoginParam;
import pers.codewld.iadmin.api.service.AccountService;
import pers.codewld.iadmin.api.service.AmAdminService;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.security.util.JWTUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 账户 Service实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AmAdminService amAdminService;

    @Resource
    PasswordEncoder md5PasswordEncoder;

    @Resource
    JWTUtil jwtUtil;

    @Transactional
    @Override
    public String login(LoginParam loginParam) {
        // 查询信息
        AmAdmin loadedAmAdmin = amAdminService
                .lambdaQuery()
                .select(AmAdmin::getId, AmAdmin::getUsername, AmAdmin::getPassword)
                .eq(AmAdmin::getUsername, loginParam.getUsername())
                .one();
        // 校验账号密码
        if (loadedAmAdmin == null || !md5PasswordEncoder.matches(loginParam.getPassword(), loadedAmAdmin.getPassword())) {
            throw new CustomException(ResultCode.FORBIDDEN, "账号密码错误");
        }
        // 保存登录记录
        boolean update = amAdminService
                .lambdaUpdate()
                .set(AmAdmin::getLastLoginTime, LocalDateTime.now())
                .eq(AmAdmin::getId, loadedAmAdmin.getId())
                .update();
        if (!update) {
            throw new CustomException(ResultCode.FAILED);
        }
        // 生成jwt
        return jwtUtil.sign(loadedAmAdmin);
    }

}
