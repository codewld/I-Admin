package pers.codewld.iadmin.common.exception;

import pers.codewld.iadmin.common.model.vo.ResultVO;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    private final ResultVO.Code code;

    public CustomException(ResultVO.Code code) {
        super(code.getMsg());
        this.code = code;
    }

    public CustomException(String msg) {
        super(msg);
        this.code = ResultVO.Code.FAILED;
    }

    public CustomException(ResultVO.Code code, String msg) {
        super(msg);
        this.code = code;
    }

    public ResultVO.Code getResultCode() {
        return code;
    }

}
