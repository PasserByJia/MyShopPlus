package com.jh.myshop.plus.provider.service;

import com.jh.myshop.plus.provider.api.UmsAdminService;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import com.jh.myshop.plus.provider.mapper.UmsAdminMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;


/**
 * User's management Service
 * @author jh
 */
@Service(version = "1.0.0")
public class UmsAdminServiceImpl implements UmsAdminService {
    @Resource
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public int insert(UmsAdmin umsAdmin) {

        //nit umsAdmin object
        initUmsAdmin(umsAdmin);

        return umsAdminMapper.insert(umsAdmin);
    }

    @Override
    public UmsAdmin get(String username) {
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("username",username);
        return umsAdminMapper.selectOneByExample(example);
    }

    @Override
    public UmsAdmin get(UmsAdmin umsAdmin) {
        return umsAdminMapper.selectOne(umsAdmin);
    }

    /**
     * update admin data
     * @param umsAdmin
     * @return
     */
    @Override
    public int update(UmsAdmin umsAdmin) {
        UmsAdmin oldUmsAdmin = get(umsAdmin.getUsername());
        oldUmsAdmin.setEmail(umsAdmin.getEmail());
        oldUmsAdmin.setNickName(umsAdmin.getNickName());
        oldUmsAdmin.setNote(umsAdmin.getNote());
        oldUmsAdmin.setStatus(umsAdmin.getStatus());
        return umsAdminMapper.updateByPrimaryKey(oldUmsAdmin);
    }

    /**
     * Init umsAdmin object
     * @param umsAdmin
     */
    private void initUmsAdmin(UmsAdmin umsAdmin){
        //Initial creation time
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());

        //Init account status
        if (umsAdmin.getStatus()==null){
            umsAdmin.setStatus(0);
        }

        //encoding password
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
    }
}
