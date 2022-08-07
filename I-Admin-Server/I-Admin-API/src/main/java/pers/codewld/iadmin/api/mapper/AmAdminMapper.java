package pers.codewld.iadmin.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.codewld.iadmin.api.model.entity.AmAdmin;

/**
 * 权限-后台用户 Mapper接口
 */
@Mapper
public interface AmAdminMapper extends BaseMapper<AmAdmin> {

}
