package pers.codewld.iadmin.common.exception;

import lombok.Getter;
import pers.codewld.iadmin.common.model.enums.ResultCode;

/**
 * 自定义异常
 */
@Getter
public class CustomException extends RuntimeException {

    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

}
