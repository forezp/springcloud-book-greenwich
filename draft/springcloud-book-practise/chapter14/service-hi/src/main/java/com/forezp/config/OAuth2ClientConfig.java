package com.forezp.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * OAuth2客户端
 *
 * OAuth2ClientConfig 用来访问被0Auth2 保护的资源。
 * 配置0Auth2 Client ，简单来说， 需要配置3 个选项：
 *   一是配置受保护的资源的信息， 即ClientCredentialsResourceDetails ；
 *   二是配置一个过滤器， 存储当前请求和上下文；
 *   三是在Request 域内创建AccessTokenRequest 类型的Bean。
 * 在0Auth2ClientConfig 类上;IJQ @Enable0Auth2Client 注解， 开启0Auth2 Client 的功能：
 * 并配置了一个ClientCredentialsResourceDetails 类型的Bean ， 该Bean 是通过读取配置文件中前缀为security.oauth2.client
 * 的配置来获取Bean 的配置属性的； 注入一个Oauth2FeignRequestInterceptor类型过滤器的Bean
 *
 * 最后注入了一个用于向Uaa 服务请求的0Auth2RestTemplate 类型的Bean
 */

@EnableOAuth2Client
@EnableConfigurationProperties
@Configuration
public class OAuth2ClientConfig {


    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    /**
     *  @EnableOAuth2Client.
     * 1.oauth2ClientContextFilter
     * 2.AccessTokenRequest
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}
