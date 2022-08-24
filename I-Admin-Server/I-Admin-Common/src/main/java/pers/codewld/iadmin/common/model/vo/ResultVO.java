package pers.codewld.iadmin.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pers.codewld.iadmin.common.model.enums.ResultCode;

/**
 * 响应体 VO
 */
@ApiModel("响应体")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultVO {

    @ApiModelProperty("状态码")
    private final int code;

    @ApiModelProperty("描述")
    private final String msg;

    @ApiModelProperty("数据")
    private final Object data;

    public static ResultVO success() {
        return new ResultVO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static ResultVO success(Object data) {
        return new ResultVO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static ResultVO fail() {
        return new ResultVO(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }

    public static ResultVO result(ResultCode resultCode) {
        return new ResultVO(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static ResultVO result(ResultCode resultCode, String msg) {
        return new ResultVO(resultCode.getCode(), msg, null);
    }

}
