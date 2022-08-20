package pers.codewld.iadmin.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应体
 */
@ApiModel("响应体")
@Getter
public class ResultVO {

    @ApiModelProperty("状态码")
    private final int code;

    @ApiModelProperty("描述")
    private final String msg;

    @ApiModelProperty("数据")
    private final Object data;

    public static ResultVO success() {
        return new ResultVO(Code.SUCCESS, null);
    }

    public static ResultVO success(Object data) {
        return new ResultVO(Code.SUCCESS, data);
    }

    public static ResultVO fail() {
        return new ResultVO(Code.FAILED, null);
    }

    public static ResultVO fail(String msg) {
        return new ResultVO(Code.FAILED.getCode(), msg, null);
    }

    public static ResultVO result(Code code) {
        return new ResultVO(code, null);
    }

    public static ResultVO result(Code code, String msg) {
        return new ResultVO(code.getCode(), msg, null);
    }

    public static ResultVO result(Code code, String msg, Object data) {
        return new ResultVO(code.getCode(), msg, data);
    }

    private ResultVO(Code code, Object data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    private ResultVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 响应体-状态码及描述
     */
    @Getter
    @AllArgsConstructor
    @ApiModel("响应体-状态码及描述")
    public enum Code {

        SUCCESS(0, "操作成功"),

        FAILED(1000, "操作失败"),

        VALIDATE_FAILED(2000, "参数错误"),

        UNAUTHORIZED(8000, "未登录"),

        FORBIDDEN(9000, "未授权");

        @ApiModelProperty("状态码")
        private final int code;

        @ApiModelProperty("描述")
        private final String msg;

    }

}
