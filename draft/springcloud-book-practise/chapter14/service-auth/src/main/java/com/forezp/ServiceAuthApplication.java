package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 此类加上 EnableResourceServer 注解，开启Resource Server 。因为此程序本身需要对外暴露
 * 获取Token 的API 接口和验证Token 的API 接口，所以该程序也是一个资源服务。
 *
 * EnableDiscoveryClient 是集成服务注册与发现中心 Consul组件使用的
 */
@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
public class ServiceAuthApplication {
//	@Autowired
//	@Qualifier("dataSource")
//	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(ServiceAuthApplication.class, args);
	}



}
