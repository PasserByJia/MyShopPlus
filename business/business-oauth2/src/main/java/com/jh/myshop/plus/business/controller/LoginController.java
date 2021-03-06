package com.jh.myshop.plus.business.controller;


import com.google.common.collect.Maps;
import com.jh.myshop.plus.business.dto.LoginInfo;
import com.jh.myshop.plus.business.dto.LoginParam;
import com.jh.myshop.plus.business.feign.ProfileFeign;
import com.jh.myshop.plus.commons.dto.ResponseResult;
import com.jh.myshop.plus.commons.utils.MapperUtils;
import com.jh.myshop.plus.commons.utils.OkHttpClientUtil;
import com.jh.myshop.plus.provider.domain.UmsAdmin;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
public class LoginController {
    private static final String URL_OAUTH_TOKEN = "http://localhost:9001/oauth/token";

    /**
     * 动态加载，载入application.yml中的grant_type
     */
    @Value("${business.oauth2.grant_type}")

    public String oauth2GrantType;
    /**
     * 动态加载，载入application.yml中的client_id
     */
    @Value("${business.oauth2.client_id}")
    public String oauth2ClientId;

    /**
     * 动态加载，载入application.yml中的client_id
     */
    @Value("${business.oauth2.client_secret}")
    public String oauth2ClientSecret;

    @Resource(name = "userDetailsServiceBean")
    public UserDetailsService userDetailsService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @Resource
    public TokenStore tokenStore;

    @Resource
    private ProfileFeign profileFeign;

    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String,Object>> login(@RequestBody LoginParam loginParam){

        //封装返回结果集
        Map<String,Object> result = Maps.newHashMap();

        //验证账号密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if(userDetails == null || !passwordEncoder.matches(loginParam.getPassword(),userDetails.getPassword())){
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.FAIL, "账号或密码错误", null);
        }
        //System.out.println(oauth2ClientId+" "+oauth2ClientSecret+" "+oauth2GrantType);
        Map<String,String> params = Maps.newHashMap();
        params.put("username",loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("client_id", oauth2ClientId);
        params.put("grant_type", oauth2GrantType);
        params.put("client_secret", oauth2ClientSecret);

        try {
            Response response = OkHttpClientUtil.getInstance().postData(URL_OAUTH_TOKEN, params);
            String jsonString = Objects.requireNonNull(response.body()).string();
            Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));
            //System.out.println(jsonMap);
            result.put("token",token);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResult<Map<String,Object>>(20000,HttpStatus.OK.toString(),result);
    }

    @GetMapping(value = "/user/info")
    public ResponseResult<LoginInfo> info(HttpServletRequest request) throws Exception {
        // 获取 token
        String token = request.getParameter("access_token");
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jsonString = profileFeign.info(authentication.getName());
        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);
        // 封装并返回结果.
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(umsAdmin.getUsername());
        loginInfo.setAvatar(umsAdmin.getIcon());
        loginInfo.setNickName(umsAdmin.getNickName());
        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    /**
     * 注销登录
     * @param request
     * @return
     */
    @PostMapping(value = "/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request){
        // 获取 token
        String token = request.getParameter("access_token");
        // 删除 token 以注销
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户已注销");
    }
}
