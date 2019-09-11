package com.forezp.client;


import com.forezp.client.hystrix.AuthServiceHystrix;
import com.forezp.entity.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fangzhipeng on 2017/5/27.
 */
// 开启fiegnClient调用，调用uaa-service项目的/oauth/token服务获取JWT，并且提供一个熔断器，服务访问失败 直接快速返回Null
@FeignClient(value = "uaa-service",fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {

    /**
     * 在“ ／oauth/token" API 接口中需要在请求头传入Authorization 信息， 并需要传请求参数认证类型grant_type 、用户名usemame 和密码password
     * @param authorization
     * @param type
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type,
                 @RequestParam("username") String username, @RequestParam("password") String password);

}



