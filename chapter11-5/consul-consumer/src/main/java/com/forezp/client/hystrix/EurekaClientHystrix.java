package com.forezp.client.hystrix;

import com.forezp.client.EurekaClientFeign;
import org.springframework.stereotype.Component;

/**
 * Created by 36189 on 2019/1/14.
 */
@Component
public class EurekaClientHystrix implements EurekaClientFeign{

    @Override
    public String sayHiFromClientEureka(String name) {
        return "进入Hystrix了";
    }
}
