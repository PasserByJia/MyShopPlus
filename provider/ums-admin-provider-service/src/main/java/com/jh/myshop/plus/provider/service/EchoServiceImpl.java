package com.jh.myshop.plus.provider.service;
import com.jh.myshop.plus.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Service;

//使用dubbo实现的服务必须使用该注解括号里是版本号，自定义
@Service(version = "1.0.0")
class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String string) {
        return "Echo Hello Dubbo " + string;
    }
}