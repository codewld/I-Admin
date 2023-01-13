package pers.codewld.iadmin.api.service.impl;

import pers.codewld.iadmin.api.model.entity.AmRole;
import pers.codewld.iadmin.api.mapper.AmRoleMapper;
import pers.codewld.iadmin.api.service.AmRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 权限-角色 Service实现类
 */
@Service
public class AmRoleServiceImpl extends ServiceImpl<AmRoleMapper, AmRole> implements AmRoleService {

}
