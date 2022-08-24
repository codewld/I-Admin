package pers.codewld.iadmin.common.model.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码及描述 枚举类
 */
@Getter
@AllArgsConstructor
@ApiModel("响应状态码及描述 枚举类")
public enum ResultCode {

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
