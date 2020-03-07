package com.jh.myshop.plus.cloud.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadParam {
    private String username;
    private MultipartFile multipartFile;
}
