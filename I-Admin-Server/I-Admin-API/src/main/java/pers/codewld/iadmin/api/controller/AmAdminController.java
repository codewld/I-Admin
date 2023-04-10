package pers.codewld.iadmin.api.controller;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.api.model.entity.AmRole;
import pers.codewld.iadmin.api.service.AmRoleService;
import pers.codewld.iadmin.common.controller.BaseController;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.security.util.MD5Utils;

import javax.annotation.Resource;

/**
 * 权限-后台用户 接口
 */
@RestController
@RequestMapping("/am-admin")
public class AmAdminController extends BaseController<AmAdmin> {

    @Resource
    AmRoleService amRoleService;

    @Override
    public boolean add(@RequestBody @Validated AmAdmin amAdmin)  {
        // 密码处理
        amAdmin.setPassword(MD5Utils.encode(amAdmin.getPassword()));
        // 角色处理
        if (StringUtils.hasLength(amAdmin.getRole())) {
            AmRole role = amRoleService
                    .lambdaQuery()
                    .eq(AmRole::getCode, amAdmin.getRole())
                    .one();
            if (role == null) {
                throw new CustomException(ResultCode.VALIDATE_FAILED, "角色错误");
            }
        }
        return super.add(amAdmin);
    }

    @Override
    public boolean update(@RequestBody @Validated AmAdmin amAdmin)  {
        // 密码处理
        if (amAdmin.getPassword() != null) {
            amAdmin.setPassword(MD5Utils.encode(amAdmin.getPassword()));
        }
        // 角色处理
        if (StringUtils.hasLength(amAdmin.getRole())) {
            AmRole role = amRoleService
                    .lambdaQuery()
                    .eq(AmRole::getCode, amAdmin.getRole())
                    .one();
            if (role == null) {
                throw new CustomException(ResultCode.VALIDATE_FAILED, "角色填写错误");
            }
        }
        return super.update(amAdmin);
    }
}
