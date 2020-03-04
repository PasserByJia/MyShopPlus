package com.jh.myshop.plus.provider.service;

import com.jh.myshop.plus.provider.api.UmsAdminService;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import com.jh.myshop.plus.provider.mapper.UmsAdminMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;


/**
 * 用户管理服务
 * @author 贾昊
 */
@Service(version = "1.0.0")
public class UmsAdminServiceImpl implements UmsAdminService {
    @Resource
    private UmsAdminMapper umsAdminMapper;
    @Override
    public int insert(UmsAdmin umsAdmin) {
        return umsAdminMapper.insert(umsAdmin);
    }
}
