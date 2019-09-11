package com.forezp.logging;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Created by 36189 on 2019/1/14.
 */
@Configuration
public class FeignClientConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        // 设置日志
        return Logger.Level.NONE;
    }

}
