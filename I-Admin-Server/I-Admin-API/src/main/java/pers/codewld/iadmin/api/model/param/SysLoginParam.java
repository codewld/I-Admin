package pers.codewld.iadmin.api.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 系统-登录参数 参数类
 */
@ApiModel("系统-查询参数 参数类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginParam {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 20, message = "用户名长度应在5-20之间")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 5, max = 20, message = "密码长度应在5-20之间")
    private String password;

}
