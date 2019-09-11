package com.forezp.client.hystrix;

import com.forezp.client.TestFiengClient;
import org.springframework.stereotype.Component;

/**
 * Created by 36189 on 2019/1/8.
 */
@Component
public class TestFiengClientHystrix implements TestFiengClient {
    @Override
    public String testFiegn() {
        return null;
    }
}
