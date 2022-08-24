package pers.codewld.iadmin.common.controller.advice;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.common.model.vo.ResultVO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理 接口增强类
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public ResultVO validateFailedException(Exception e) {
        String msg = null;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            msg = ((ConstraintViolation<?>) (ex.getConstraintViolations().toArray()[0])).getMessage();
        }
        return ResultVO.result(ResultCode.VALIDATE_FAILED, msg);
    }

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
        return ResultVO.result(ResultCode.FAILED, e.getMessage());
    }

}
