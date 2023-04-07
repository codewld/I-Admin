package pers.codewld.iadmin.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统-测试 接口
 */
@RestController
public class SysHelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
