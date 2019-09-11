package com.forezp.config;


import com.forezp.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * uaa-service 服务对外提供获取JWT 的API 接口， uaa-service 服务是一个授权服务器，同时也是资源服务器，需要配置该服务的Spring Security
 * configuration + EnableWebSecurity 开启Web 资源的保护功能
 */

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *向IoC 容器注入AuthenticationManager 对象的Bean ，该Bean 在0Auth2 的配置中使用，因为只有在OAuth2 中配置了AuthenticationManager，
     * 密码类型的验证才会开启。在本案例中，采用的是密码类型的验证
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * configure方法中配置所有的请求都需要验证，如果请求验证不通过，则重定位到401的界面
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll()
                .antMatchers("/**").authenticated()
            .and()
                .httpBasic();
    }


    /**
     *注入的就是com.forezp.service.UserServiceDetail 实现类
     */
    @Autowired
    UserServiceDetail userServiceDetail;

    /**
     * configure(AuthenticationManagerBuilder auth）方法中配置验证的用户信息源、密码加密的策略。
     * 采用BCryptPasswordEncoder 对密码进行加密， 在创建用户时，密码加密也必须使用这个类。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
