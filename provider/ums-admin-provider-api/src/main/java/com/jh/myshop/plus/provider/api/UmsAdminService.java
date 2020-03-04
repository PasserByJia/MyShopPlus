package com.jh.myshop.plus.provider.api;

import com.jh.myshop.plus.provider.domain.UmsAdmin;


/**
 * 用户管理服务
 * @author 贾昊
 */
public interface UmsAdminService {

    /**
     * 新增用户
     * @author 贾昊
     * */
    int insert(UmsAdmin umsAdmin);
}
