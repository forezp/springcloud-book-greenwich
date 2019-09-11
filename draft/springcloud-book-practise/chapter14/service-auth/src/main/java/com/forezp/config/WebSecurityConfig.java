package com.forezp.config;

import com.forezp.service.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * auth-service 需要对外暴露检查Token 的API 接口，所以auth-service 也是一个资源服
 * 务，需要在工程中引入Spring Security，并做相关的配置，对auth-service 资源进行保护
 * 1. configure方法中的HttpSecurity配置此项目所有请求都需要安全认证
 * 2. configure方法中的AuthenticationManagerBuilder 中配置了验证的用户信息源和密码加密的策略
 * 3. 配置了验证管理的Bean 为AuthenticationManager
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // 此处注入的是com.forezp.service.security.UserServiceDetail类
    @Autowired
    private UserServiceDetail userServiceDetail;

    /**
     * 此方法为HttpSecurity 中配置了所有的请求都需要安全验证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * AuthenticationManagerBuilder 中配置了
     * 验证的用户信息源和密码加密的策略，并且向IoC 容器注入了AuthenticationManager
     * 对象。这是要在0Auth2 中配置，因为在0Auth2 中配置了AuthenticationManager,
     * 密码验证才会开启。在本例中，采用的是密码验证。
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置了验证管理的Bean 必须加入这个Bean注入才能开启密码授权模式
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
