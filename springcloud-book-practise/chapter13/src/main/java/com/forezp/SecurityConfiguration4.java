package com.forezp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 36189 on 2018/12/28.
 * 此类主要用于演示： 从数据库中读取用户的认证信息
 *
 * EnableWebSecurity 开启web security
 * Configuration 开启配置加载
 * EnableGlobalMethodSecurity 开启方法级别的安全保护
 *      EnableGlobalMethodSecurity括号后面的参数可选，可选的参数如下：
 *      prePostEnabled: Spring Security 的Pre 和Post 注解是否可用，@PreAuthorize 和@PostAuthorize 是否可用。
 *      secureEnabled: Spring Security 的＠Secured 注解是否可用。
 *      jsr250Enabled: Spring Security 对JSR-250 的注解是否可用。
 *      一般只会用到prePostEnabled ，此类型当中也只有PreAuthorize用的比较多，进入方法前认证。
 *      在方法上写权限注解的两种办法：
 *      a. @PreAuthorize("hasRole('ADMIN')")
 *      b. @PreAuthorize("hasAuthority('ROLE_ADMIN')")  两种写法相等
 *      一个方法多个权限点的写法：
 *      a. @PreAuthorize("hasAnyRole('ADMIN','USER')")
 *      b. @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
 *      方法级别的安全保护示例，请见BlogController中delete 方法
 */

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration4 extends WebSecurityConfigurerAdapter {

    /**
     * 此方法配置了如何验证用户信息
     * 这个版本的spring security 一定要指定密码的编码类型 否则访问会报错
     *
     * 本段代码的作用
     * 1. 在内存中创建了认证用户 第二种方式：
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
     * 在这个示例中，给admin加了两个角色ADMIN与USER，但是只有具有ADMIN角色的ADMIN用户才能删除博客
     * 访问http://localhost:8080/ 登录后进入index页面，然后点击博客管理进入博客列表， 点击删除按钮 forezp没有权限删除数据，但admin用户可以删除数据
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth)throws  Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        // passwordEncorder 是指定spring security在验证密码时使用的 编码格式 user表中的密码必须是用BCryptPasswordEncoder加密之后的代码
     }

    // 此处注入其实是本工程中的UserService类，因为com.forezp.service.impl.UserService类实现了UserDetailService接口
     @Autowired
     UserDetailsService userDetailsService;

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
