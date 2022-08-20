package pers.codewld.iadmin.common.exception;

import lombok.Getter;
import pers.codewld.iadmin.common.model.vo.ResultVO;

/**
 * 自定义异常
 */
@Getter
public class CustomException extends RuntimeException {

    private final ResultVO.Code code;

    public CustomException(ResultVO.Code code) {
        super(code.getMsg());
        this.code = code;
    }

    public CustomException(ResultVO.Code code, String msg) {
        super(msg);
        this.code = code;
    }

}
