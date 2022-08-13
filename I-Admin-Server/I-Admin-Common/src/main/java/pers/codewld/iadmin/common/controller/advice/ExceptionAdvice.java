package pers.codewld.iadmin.common.controller.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.vo.ResultVO;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 自定义异常处理
     */
    @ExceptionHandler({CustomException.class})
    public ResultVO customException(Exception e) {
        CustomException ex = (CustomException) e;
        return ResultVO.result(ex.getResultCode(), ex.getMessage());
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResultVO exception(Exception e) {
        e.printStackTrace();
        return ResultVO.fail();
    }

}
