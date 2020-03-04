package com.jh.myshop.plus.business.controller;

import com.jh.myshop.plus.provider.api.UmsAdminService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class RegController {
    @Resource
    private UmsAdminService umsAdminService;


}
