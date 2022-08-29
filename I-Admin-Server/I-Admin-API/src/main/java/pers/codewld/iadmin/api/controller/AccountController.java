package pers.codewld.iadmin.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.api.model.param.LoginParam;
import pers.codewld.iadmin.api.service.AccountService;

import javax.annotation.Resource;

/**
 * 账户 接口
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginParam loginParam) {
        return accountService.login(loginParam);
    }

}
