package com.forezp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务类
 *
 * Configuration + EnableResourceServer注解开启 Resource Server功能
 * EnableGlobalMethodSecurity开启方法级别的安全保护
 *
 * configure方法通过ant表达式配置哪些请求需要验证，哪些请求不需要验证
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/user/registry").permitAll() // 不需要验证
                .anyRequest().authenticated();// 其他请求都需要验证
    }

}
