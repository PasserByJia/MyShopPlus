package com.jh.myshop.plus.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * login user information
 */
@Data
public class LoginInfo implements Serializable {
    private String name;
    private String avatar;
}
