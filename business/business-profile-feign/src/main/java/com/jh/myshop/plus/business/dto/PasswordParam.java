package com.jh.myshop.plus.business.dto;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码参数
 * <p>
 * Description:
 * </p>
 *
 * @author Lusifer
 * @version v1.0.0
 * @date 2019-08-23 04:38:15
 */
@Data
public class PasswordParam implements Serializable {

    private String username;
    private String oldPassword;
    private String newPassword;

}
