package pers.codewld.iadmin.api.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.codewld.iadmin.security.model.entity.IUserDetails;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限-后台用户 实体类
 */
@ApiModel("权限-后台用户 实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("am_admin")
@JsonIgnoreProperties({"enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class AmAdmin extends IUserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("用户名")
    @Size(min = 5, max = 20, message = "用户名长度应在5-20之间")
    private String username;

    @ApiModelProperty("密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("昵称")
    @Size(min = 5, max = 20, message = "昵称长度应在5-20之间")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("启用状态  [0:禁用;10:启用]")
    private Integer status;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
