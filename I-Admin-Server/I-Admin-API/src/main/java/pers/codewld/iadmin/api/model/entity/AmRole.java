package pers.codewld.iadmin.api.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限-角色 实体类
 */
@ApiModel("权限-角色 实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("am_role")
public class AmRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("编码")
    @Size(min = 3, max = 20, message = "编码长度应在3-20之间")
    private String code;

    @ApiModelProperty("名称")
    @Size(min = 3, max = 20, message = "名称长度应在3-20之间")
    private String name;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
