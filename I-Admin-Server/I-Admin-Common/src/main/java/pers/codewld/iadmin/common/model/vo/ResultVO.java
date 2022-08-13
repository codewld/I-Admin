package pers.codewld.iadmin.common.model.vo;

import lombok.Getter;
import pers.codewld.iadmin.common.model.enums.ResultCode;

/**
 * 响应体
 */
@Getter
public class ResultVO {

    /**
     * 状态码
     */
    private final int code;

    /**
     * 描述
     */
    private final String msg;

    /**
     * 数据
     */
    private final Object data;

    public static ResultVO success() {
        return new ResultVO(ResultCode.SUCCESS, null);
    }

    public static ResultVO success(Object data) {
        return new ResultVO(ResultCode.SUCCESS, data);
    }

    public static ResultVO fail() {
        return new ResultVO(ResultCode.FAILED, null);
    }

    public static ResultVO fail(String msg) {
        return new ResultVO(ResultCode.FAILED.getCode(), msg, null);
    }

    public static ResultVO result(ResultCode resultCode) {
        return new ResultVO(resultCode, null);
    }

    public static ResultVO result(ResultCode resultCode, String msg) {
        return new ResultVO(resultCode.getCode(), msg, null);
    }

    public static ResultVO result(ResultCode resultCode, String msg, Object data) {
        return new ResultVO(resultCode.getCode(), msg, data);
    }

    private ResultVO(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    private ResultVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}