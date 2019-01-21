package com.forezp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fangzhipeng on 2017/6/13.
 */
@Service
public class RibbonService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 在该类的hi()方法用restTemplate调用eureka-client的API接口  Uri 上不需要使用硬编码（比如IP），只需要写服务名eureka-client即可
     * 程序会根据服务名称 eureka-client到Eureka-server注册中心去自动获取IP和端口信息。
     * @param name
     * @return
     */
    public String hi(String name) {
        return restTemplate.getForObject("http://eureka-client/hi?name="+name,String.class);
    }
}
