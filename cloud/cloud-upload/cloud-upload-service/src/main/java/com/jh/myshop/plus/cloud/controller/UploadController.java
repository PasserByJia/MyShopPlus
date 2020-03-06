package com.jh.myshop.plus.cloud.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.google.common.collect.Maps;
import com.jh.myshop.plus.cloud.dto.FileInfo;
import com.jh.myshop.plus.commons.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 文件上传服务
 * <p>
 * Description:
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-08-26 07:20:41
 * @see com.jh.myshop.plus.cloud.controller
 */
@RestController
@RequestMapping(value = "upload")
public class UploadController {

    private static final String CLOUD_NAME = "deboqrr2s";
    private static final String API_KEY = "848566197981432";
    private static final String API_SECRET = "XF_JyCqKOMoVhqHZS1M1dSpDyWs";

    @PostMapping(value = "")
    public ResponseResult<Map<String,Object>> login(MultipartFile multipartFile) throws IOException {
        //生成新的文件名
        String newName = UUID.randomUUID()+"";
        //转转换文件格式
        File toFile = null;
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            toFile = new File(newName);
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        //配置验证信息
        Map config = new HashMap();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        Cloudinary cloudinary = new Cloudinary(config);
        //指定文件夹
        Map params = ObjectUtils.asMap("public_id", "develop / "+newName);
        try {
            Map<String,Object> uploadResult = cloudinary.uploader().upload(toFile, params);
            String url = String.valueOf(uploadResult.get("url"));
            //封装返回结果集
            Map<String,Object> result = Maps.newHashMap();
            result.put("url",url);
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK,"文件上传成功",result);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.FAIL,"文件上传失败请重试",null);
        }
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}