package com.forezp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 36189 on 2018/12/28.
 * EnableWebSecurity 开启web security
 * Configuration 开启配置加载
 * EnableGlobalMethodSecurity 开启方法级别的安全保护
 */

//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration1 extends WebSecurityConfigurerAdapter {

    /**
     * 此方法配置了如何验证用户信息
     * 这个版本的spring security 一定要指定密码的编码类型 否则访问会报错
     *
     * 本段代码的作用
     * 1. 在内存中创建了认证用户，第一种方式： 使用内存方式调用withUser方法创建用户
     * 2. 应用的每一个请求都需要认证
     * 3. 自动生成了一个登录表单
     * 4. 可以用用户名 密码 来进行认证
     * 5. 用户可以注销
     * 6. session Fixation保护
     * 7. 安全Hea der 集成了以下内容。
     *      HTTP Strict Transport Sec旧ity for secure requests
     *      X-Content-Type-Options integration
     *      Cache Control
     *      X-XSS-Protection integration
     *      XFrame-Options integration to help prevent Clickjacking
     * 8. 集成了以下的ServletAPI 的方法
     *      HttpServletRequest#getRemoteUser()
     *      HttpServletRequest.html # getUserPrincipal()
     *      HttpServletRequest.html# isUserlnRole(java.lang.String )
     *      HttpServletRequest.html#login(java.lang.String,java.lang.String)
     *      HttpServletRequest.html # logout()
     *
     * 启动成功后，访问http://localhost:8080 用forezp用户可以访问Index页面， 但admin用户不可以，因为admin用户没有USER权限
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth)throws  Exception{
          /* 第一种在内从中创建用户的方式*/
        // 必须制定BcryptPasswordEncoder 密码编码
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("forezp").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");
    }

    /**
     * Spring Security如何知道哪些用户需要身份验证，又如何知道要支持基于表单的身份验证？ 工程的哪些资
     * 源需要验证， 哪些资源不需要验证。 都是通过此方法配置HttpSecurity 从而达到目的
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/blogs/**").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .and()
                .exceptionHandling().accessDeniedPage("/401");
        http.logout().logoutSuccessUrl("/");

        /**
         * 以"/css"开头的资源和"/index "资源不需要验证，可直接访问
         * 访问/user/**与/blogs/**资源需要User权限
         * 表单登录的地址是"／login "，登录失败的地址是"／login-error"
         * 异常处理会重定向到"／401"界面。
         * 注销登录成功，重定向到首页
         */
    }
}
