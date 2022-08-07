package pers.codewld.iadmin.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.codewld.iadmin.api.mapper.AmAdminMapper;
import pers.codewld.iadmin.api.model.entity.AmAdmin;
import pers.codewld.iadmin.api.service.AmAdminService;

/**
 * 权限-后台用户 Service实现类
 */
@Service
public class AmAdminServiceImpl extends ServiceImpl<AmAdminMapper, AmAdmin> implements AmAdminService {

}
