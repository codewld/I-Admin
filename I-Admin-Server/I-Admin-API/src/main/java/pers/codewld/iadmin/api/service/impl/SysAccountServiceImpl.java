package pers.codewld.iadmin.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.api.model.param.SysLoginParam;
import pers.codewld.iadmin.api.service.SysAccountService;
import pers.codewld.iadmin.api.service.AmAdminService;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.security.util.JWTUtils;
import pers.codewld.iadmin.security.util.MD5Utils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 账户 Service实现类
 */
@Service
public class SysAccountServiceImpl implements SysAccountService {

    @Resource
    AmAdminService amAdminService;

    @Transactional
    @Override
    public String login(SysLoginParam sysLoginParam) {
        // 查询信息
        AmAdmin loadedAmAdmin = amAdminService
                .lambdaQuery()
                .select(AmAdmin::getId, AmAdmin::getUsername, AmAdmin::getPassword, AmAdmin::getRole)
                .eq(AmAdmin::getUsername, sysLoginParam.getUsername())
                .one();
        // 校验账号密码
        if (loadedAmAdmin == null || !MD5Utils.matches(sysLoginParam.getPassword(), loadedAmAdmin.getPassword())) {
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
        return JWTUtils.sign(loadedAmAdmin);
    }

}
