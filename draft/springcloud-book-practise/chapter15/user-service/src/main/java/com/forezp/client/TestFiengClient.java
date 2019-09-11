package com.forezp.client;


import com.forezp.client.hystrix.TestFiengClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by fangzhipeng on 2017/5/27.
 */
// 开启fiegnClient调用，调用test-fiegn项目的test服务，并且提供一个熔断器，服务访问失败 直接快速返回Null
@FeignClient(value = "test-fiegn",fallback =TestFiengClientHystrix.class )
public interface TestFiengClient {

    @PostMapping(value = "/test-fiegn/test")
    String testFiegn();

}



