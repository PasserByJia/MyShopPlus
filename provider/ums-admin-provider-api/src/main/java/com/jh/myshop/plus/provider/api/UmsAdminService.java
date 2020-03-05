package com.jh.myshop.plus.provider.api;

import com.jh.myshop.plus.provider.domain.UmsAdmin;


/**
 * User's management Service
 * @author jh
 */
public interface UmsAdminService {

    /**
     * insert user
     * @author jh
     * Greater than zero indicates success
     * */
    int insert(UmsAdmin umsAdmin);

    /**
     * get user
     * @param username
     * @return {@link UmsAdmin}
     */
    UmsAdmin get(String username);

    /**
     * get user
     * @param umsAdmin
     * @return {@link UmsAdmin}
     */
    UmsAdmin get(UmsAdmin umsAdmin);
}
