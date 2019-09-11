package com.forezp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 *0Auth2Config 类继承了AuthorizationServerConfigurerAdapter类， 并在0Auth2Config 类加上＠EnableAuthorizationServer 注解，
 * 开启Authorization Server的功能
 *Authorization Server 需要配置两个选项，即ClientDetailsServiceConfigurer 和AuthorizationServerEndpointsConfigurer
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    /**
     * ClientDetailsServiceConfigurer 配置了客户端的一些基本信息， clients.inMemory （）方法是
     * 将客户端的信息存储在内存中， .withClient("user-service")方法创建了一个Clientld 为"user-service "
     * 的客户端，刷出orizedGrantTypes (" refresh_token"，"password")方法配置类验证类型为refresh _token
     * 和password, . scopes(" service")方法配置了客户端域为" service ", .accessTokenValiditySeconds(3600)
     * 方法配置了Token 的过期时间为3600 秒
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String encodePassword = "$2a$10$YDxPzaI6durzWnrI1hfMn.YiXNVx8I0p.swU85vQCJNwU15SZ2co6"; // 123456对应的密文
        clients.inMemory()
                .withClient("user-service")
                .secret(encodePassword)
                .scopes("service")
                .autoApprove(true)
                .authorizedGrantTypes("implicit","refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(3600);//5min过期
    }

    /**
     * AuthorizationServerEndpointsConfigurer 配置了token Store 和authenticationManager
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
    }

    /**
     * 在OAuth2 中配置了WebSecurityConfig类中的 AuthenticationManager，密码类型的验证才会开启
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 使用JwtTokenStore存储token
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * token Store 需要一个JwtAccessTokenConverter 对象， 该对象用于Token 转换。本案例中使用了非对称性加密RSA对JWT 进行加密
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fzp-jwt"));
        return converter;
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
