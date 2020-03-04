package com.jh.myshop.plus.provider;
import java.util.Date;


import com.jh.myshop.plus.provider.api.UmsAdminService;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import com.jh.myshop.plus.provider.mapper.UmsAdminMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@Transactional
@Rollback
@SpringBootTest
public class UmsAdminTests {
    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private UmsAdminService umsAdminService;

    @Test
    public void testSeletAll(){
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectAll();
        umsAdmins.forEach(umsAdmin -> {
            System.out.println(umsAdmin);
        });
    }
    //加密密码
    @Test
    public void testPasswordEncoder(){
        System.out.println( new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    public void testInsert(){
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("jh");
        umsAdmin.setPassword("$2a$10$JoB3ZTq33.TTRV.xwknPau/Y/BTai5ID52ib/0DHD90DKYQSp6dbK");
        umsAdmin.setIcon("");
        umsAdmin.setEmail("");
        umsAdmin.setNickName("");
        umsAdmin.setNote("");
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());
        umsAdmin.setStatus(0);
        int result = umsAdminMapper.insert(umsAdmin);
        Assert.assertEquals(result, 1);
    }
}
