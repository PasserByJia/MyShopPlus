package com.jh.myshop.plus.business.controller;

import com.jh.myshop.plus.business.dto.IconParam;
import com.jh.myshop.plus.business.dto.PasswordParam;
import com.jh.myshop.plus.business.dto.ProfileParam;
import com.jh.myshop.plus.commons.dto.ResponseResult;
import com.jh.myshop.plus.provider.api.UmsAdminService;

import com.jh.myshop.plus.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 个人信息管理
 */
@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "info/{username}")
    public ResponseResult<UmsAdmin> info(@PathVariable String username){
        UmsAdmin umsAdmin = umsAdminService.get(username);
        return new ResponseResult<UmsAdmin>(ResponseResult.CodeStatus.OK,"查询用户信息",umsAdmin);
    }

    /**
     * update personal data
     * @param profileParam
     * @return
     */
    @PostMapping(value = "update")
    private ResponseResult<Void> update(@RequestBody ProfileParam profileParam){
        UmsAdmin umsAdmin = new UmsAdmin();
        //spring 工具类，帮助对象转化
        BeanUtils.copyProperties(profileParam,umsAdmin);
        int result = umsAdminService.update(umsAdmin);
        if(result >0){
            return new ResponseResult<Void>(ResponseResult.CodeStatus.OK,"更新个人信息",null);
        }else{
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL,"更新个人信息失败",null);
        }
    }

    /**
     * 修改密码
     *
     * @param passwordParam {@link PasswordParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "modify/password")
    public ResponseResult<Void> modifyPassword(@RequestBody PasswordParam passwordParam) {
        UmsAdmin umsAdmin = umsAdminService.get(passwordParam.getUsername());

        // 旧密码正确
        if (passwordEncoder.matches(passwordParam.getOldPassword(), umsAdmin.getPassword())) {
            int result = umsAdminService.modifyPassword(umsAdmin.getUsername(), passwordParam.getNewPassword());
            if (result > 0) {
                return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "修改密码成功");
            }
        }

        // 旧密码错误
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "旧密码不匹配，请重试");
        }

        return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "修改密码失败");
    }

    /**
     * 修改头像
     *
     * @param iconParam {@link IconParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "modify/icon")
    public ResponseResult<Void> modifyIcon(@RequestBody IconParam iconParam) {
        System.out.println(iconParam);
        int result = umsAdminService.modifyIcon(iconParam.getUsername(), iconParam.getPath());
        System.out.println(result);
        // 成功
        if (result > 0) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "更新头像成功");
        }

        // 失败
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "更新头像失败");
        }
    }

}
