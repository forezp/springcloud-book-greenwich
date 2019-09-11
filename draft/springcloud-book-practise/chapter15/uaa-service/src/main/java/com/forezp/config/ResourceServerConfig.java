package com.forezp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ResourceServerConfigure的类， 该类继承了ResourceServerConfigureAdapter 类，在ResourceServerConfig 类上加＠EnableResourceServer 注解，开
 * 启Resource Server 功能。
 *
 * 作为Resource Server,需要配置HttpSecurity 和ResourceServerSecurityConfigurer这两个选选项。
 * HttpSecurity 配置了哪些请求需要验证，哪些请求不需要验证。在本案例中，“/user/login 登录和“/user/register ”（注册〉两个API 接口不需要验证，其他请求
 * 都需要验证。
 * ResourceServerSecurityConfigurer 需要配置tokenStore, tokenStore 为JwtConfig类中注入到IoC容器中的token Store
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    /**
     * HttpSecurity 配置了哪些请求需要验证，哪些请求不需要验证。在本案例中，“/user/login 登录和“/user/register ”（注册〉两个API 接口不需要验证，其他请求
     * 都需要验证。
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/actuator/health").permitAll()
                .antMatchers("/**").authenticated();

    }

}
