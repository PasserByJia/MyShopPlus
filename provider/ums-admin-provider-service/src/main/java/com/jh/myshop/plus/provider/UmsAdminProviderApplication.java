package com.jh.myshop.plus.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//要mapper扫描进来
@MapperScan(basePackages = "com.jh.myshop.plus.provider.mapper")
public class UmsAdminProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsAdminProviderApplication.class,args);
    }
}
