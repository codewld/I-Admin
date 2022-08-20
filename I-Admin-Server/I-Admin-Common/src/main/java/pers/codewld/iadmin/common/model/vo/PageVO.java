package pers.codewld.iadmin.common.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页数据VO
 */
@ApiModel("分页数据VO")
@Data
public class PageVO<T> {

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("数据列表")
    private List<T> records;

    public PageVO(Page<T> page) {
        this.total = page.getTotal();
        this.records = page.getRecords();
    }

}
