package pers.codewld.iadmin.service.impl;

import pers.codewld.iadmin.model.entity.AmAdmin;
import pers.codewld.iadmin.mapper.AmAdminMapper;
import pers.codewld.iadmin.service.AmAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 权限-后台用户 Service实现类
 */
@Service
public class AmAdminServiceImpl extends ServiceImpl<AmAdminMapper, AmAdmin> implements AmAdminService {

}
