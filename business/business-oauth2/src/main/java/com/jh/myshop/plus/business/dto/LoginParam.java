package com.jh.myshop.plus.business.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParam implements Serializable {
    private String username;
    private String password;
}
