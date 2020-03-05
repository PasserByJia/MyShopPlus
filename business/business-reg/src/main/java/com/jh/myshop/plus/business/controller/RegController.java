package com.jh.myshop.plus.business.controller;

import com.jh.myshop.plus.commons.dto.ResponseResult;
import com.jh.myshop.plus.provider.api.UmsAdminService;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * user register.
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping(value = "reg")
public class RegController {
    @Reference(version ="1.0.0")
    private UmsAdminService umsAdminService;

    /**
     * register
     * @param umsAdmin {@link UmsAdmin}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "")
    public ResponseResult<UmsAdmin> reg(@RequestBody UmsAdmin umsAdmin){
        String message = validateReg(umsAdmin);
        // approved
        if (message == null) {
            int result = umsAdminService.insert(umsAdmin);
            UmsAdmin admin = umsAdminService.get(umsAdmin.getUsername());
            //register successfully
            if (result > 0){
                return new ResponseResult<UmsAdmin>(HttpStatus.OK.value(),"User registration successful",admin);
            }
        }
        // validate failed
        return new ResponseResult<UmsAdmin>(HttpStatus.NOT_ACCEPTABLE.value(),message!=null?message:"User registration failed");
    }

    /**
     * registration information  verification
     * @param umsAdmin {@link UmsAdmin}
     * @return verification result
     */
    private String validateReg(UmsAdmin umsAdmin){
        UmsAdmin admin = umsAdminService.get(umsAdmin.getUsername());
        if (admin != null) {
            return "username already exist ,please enter another";
        }

        return null;

    }
}
