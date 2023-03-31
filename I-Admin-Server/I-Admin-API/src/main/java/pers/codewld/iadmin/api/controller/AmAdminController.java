package pers.codewld.iadmin.api.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.common.controller.BaseController;
import pers.codewld.iadmin.security.util.MD5Utils;

/**
 * 权限-后台用户 接口
 */
@RestController
@RequestMapping("/am-admin")
public class AmAdminController extends BaseController<AmAdmin> {

    @Override
    public boolean add(@RequestBody @Validated AmAdmin amAdmin)  {
        amAdmin.setPassword(MD5Utils.encode(amAdmin.getPassword()));
        return super.add(amAdmin);
    }

    @Override
    public boolean update(@RequestBody @Validated AmAdmin amAdmin)  {
        if (amAdmin.getPassword() != null) {
            amAdmin.setPassword(MD5Utils.encode(amAdmin.getPassword()));
        }
        return super.update(amAdmin);
    }
}
