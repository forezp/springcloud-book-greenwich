package com.forezp.web;

import com.forezp.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fangzhipeng on 2017/6/21.
 * 在Hi Controller 上加上＠RestController 注解，开启RestController 的功能，写一个API 接口“/hi”，在该接口调用了Hi Service 的sayHi （）方法。
 * HiService 通过EurekaClientFeign 远程调用eureka-client 服务的API 接口"/hi"
 */
@RestController
public class HiController {
    @Autowired
    HiService hiService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam( defaultValue = "forezp",required = false)String name){
        return hiService.sayHi(name);
    }
}
