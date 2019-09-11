package com.forezp;

import com.forezp.service.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Configuration + EnableAuthorizationServer注解，开启服务授权服务功能
 * 作为授权服务需要配置3 个选项，分别为:
 *  ClientDetailsServiceConfigurer 详细说明见此类中的configure(ClientDetailsServiceConfigurer clients) 方法
 *  AuthorizationServerEndpointsConfigurer 详细说明见此类中的configure(AuthorizationServerEndpointsConfigurer endpoints)方法
 *  AuthorizationServerSecurityConfigurer 详细说明见此类中的configure(AuthorizationServerEndpointsConfigurer endpoints) 方法
 */
@Configuration
@EnableAuthorizationServer
public  class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
//    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceDetail userServiceDetail;


    /**
     * ClientDetailsServiceConfigurer 配置了客户端的一些基本信息， clients.inMemory()
     * 方法配置了将客户端的信息存储在内存中， .withClient(" browser")方法创建了一个clientld 为
     * browser 的客户端， authorizedGrantTypes(" refresh _token " ， "password"）方法配置了验证类型为
     * refresh token 和password, .scopes("ui")方法配置了客户端域为“ ui "。接着创建了另一个client,
     * 它的Id 为“ service-hi "。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        String encodePassword = "$2a$10$YDxPzaI6durzWnrI1hfMn.YiXNVx8I0p.swU85vQCJNwU15SZ2co6"; // 123456对应的密文
        clients.inMemory()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("ui")
                .and()
                .withClient("service-hi")
                .secret(encodePassword)
                .authorizedGrantTypes("client_credentials", "refresh_token","password") // 提供三种认证模式 客户端模式  刷新token方式 密码模式
                .scopes("server");

    }

    /**
     * AuthorizationServerEndpointsConfigurer 需要配置tokenStore 、authenticationManager 和
     * userServiceDetail 。其中， tokenStore ( Token 的存储方式〉采用的方式是将Token 存储在内存中，
     * 即使用InMemoryTokenStore 。如果资源服务和授权服务是同一个服务，用InMemoryTokenStore
     * 是最好的选择。如果资源服务和授权服务不是同一个服务，则不用InMemoryTokenStore 进行存储Token 。
     * 因为当授权服务出现故障，需要重启服务，之前存在内存中Token 全部丢失，导致资源服务的Token 全部失效。
     * 另外一种方式是用JdbcTokenStore ，即使用数据库去存储，使用JdbcTokenStore 存储需要引入连接数据库依赖，
     * 如本例中的MySQL 连接器、JPA ， 并且需要初始化14.2.1 节的数据库脚本。authenticationManager 需要配置AuthenticationManager 这个
     * Bean ， 这个Bean 来源于WebSecurityConfigurerAdapter（可见com.forezp.config.WebSecurityConfig类的需要配置AuthenticationManager装载）中的配置，
     * 只有配置了这个Bean 才会开启密码类型的验证。
     * 最后配置了com.forezp.service.security.UserServiceDetail类 ，用来读取验证用户的信息。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        JdbcTokenStore tokenStore=new JdbcTokenStore(dataSource);
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userServiceDetail);
    }

    /**
     * AuthorizationServerSecurityConfigurer 配置了获取Token 的策略，在本案例中对获取Token
     * 请求不进行拦截，只需要验证获取Token 的验证信息，这些信息准确无误，就返回Token 。另外配置了检查Token 的策略。
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients().passwordEncoder(new BCryptPasswordEncoder());
        /**
         * 必须设置allowFormAuthenticationForClients 否则没有办法用postman获取token
         * 也需要指定密码加密方式BCryptPasswordEncoder
          */


    }
}