package pers.codewld.iadmin.api.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限-后台用户 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("am_admin")
@ApiModel("权限-后台用户")
public class AmAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Size(min = 5, max = 20, message = "用户名长度应在5-20之间")
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @Size(min = 5, max = 20, message = "昵称长度应在5-20之间")
    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("启用状态  [0:禁用;10:启用]")
    private Integer status;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;
}
