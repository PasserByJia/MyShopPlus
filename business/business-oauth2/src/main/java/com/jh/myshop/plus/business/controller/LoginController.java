package com.jh.myshop.plus.business.controller;


import com.google.common.collect.Maps;
import com.jh.myshop.plus.business.dto.LoginParam;
import com.jh.myshop.plus.commons.dto.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class LoginController {
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String,Object>> login(@RequestBody LoginParam loginParam){
        Map<String,Object> result = Maps.newHashMap();
        result.put("token","123456");
        return new ResponseResult<Map<String,Object>>(20000,HttpStatus.OK.toString(),result);
    }
}
