package pers.codewld.iadmin.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.crud.controller.BaseController;

/**
 * 权限-后台用户 接口
 */
@RestController
@RequestMapping("/am/admin")
public class AmAdminController extends BaseController<AmAdmin> {

}
