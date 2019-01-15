package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 注解包含了@SpringBootConfiguration 、@EnableAutoConfiguration
 * 和@ComponentScan ， 开启了包扫描、配置和自动配置的功能
 */
@SpringBootApplication
public class SpringbootFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFirstApplication.class, args);
	}
}
