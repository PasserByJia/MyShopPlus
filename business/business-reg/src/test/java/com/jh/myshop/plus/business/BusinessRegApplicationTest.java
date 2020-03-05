package com.jh.myshop.plus.business;

import com.jh.myshop.plus.provider.api.UmsAdminService;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@Rollback
@SpringBootTest
public class BusinessRegApplicationTest {

    @Resource
    private UmsAdminService umsAdminService;
    @Test
    public void testSeletAll(){
        UmsAdmin umsAdmin = umsAdminService.get("jh");
        UmsAdmin u = umsAdminService.get(umsAdmin);
        System.out.println(u);
    }
}

