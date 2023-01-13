package pers.codewld.iadmin.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.api.model.entity.AmRole;
import pers.codewld.iadmin.common.controller.BaseController;

/**
 * 权限-角色 接口
 */
@RestController
@RequestMapping("/am-role")
public class AmRoleController extends BaseController<AmRole> {

}
