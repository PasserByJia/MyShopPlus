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

    /**
     * update UmsAdmin
     * @param umsAdmin
     * @return than zero indicates success
     */
    int update(UmsAdmin umsAdmin);

    /**
     * 修改密码
     *
     * @param username {@code String} 用户名
     * @param password {@code String} 明文密码
     * @return {@code int} 大于 0 则表示更新成功
     */
    int modifyPassword(String username, String password);

    /**
     * 修改头像
     * @param username 用户名
     * @param path 头像路径
     * @return
     */
    int modifyIcon(String username,String path);
}
