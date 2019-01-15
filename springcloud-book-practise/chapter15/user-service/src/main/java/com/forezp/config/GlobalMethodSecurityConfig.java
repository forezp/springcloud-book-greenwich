package com.forezp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 为资源服务器新增＠EnableGlobalMethodSecurity(prePostEnabled =true）注解开启方法级别的安全验证
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig {

}
