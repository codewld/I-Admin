package pers.codewld.iadmin.common.controller;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.iadmin.common.exception.CustomException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 错误 接口
 * <p>用于异常处理，如果不便抛出异常，可以跳转至此
 */
@RestController
@RequestMapping("/err")
public class ErrController {

    @RequestMapping
    public void err(@RequestAttribute("exception") Exception e) throws Exception {
        throw e;
    }

    /**
     * 跳转至错误接口
     */
    public static void forward2Err(ServletRequest request, ServletResponse response, CustomException exception) throws ServletException, IOException {
        request.setAttribute("exception", exception);
        request.getRequestDispatcher("/err").forward(request, response);
    }

}
