package com.forezp.service;

import com.forezp.client.EurekaClientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangzhipeng on 2017/6/21.
 */
@Service
public class HiService {

    @Autowired
    EurekaClientFeign eurekaClientFeign;

    /**
     * Hi Service 类注入EurekaClientFeign 的Bean ，通过EurekaClientF eign 去调用sayHiFromClientEureka（）方法
     * @param name
     * @return
     */
    public String sayHi(String name){
        return  eurekaClientFeign.sayHiFromClientEureka(name);
    }
}
