package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
@RequestMapping("/test-fiegn")
public class TestFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestFeignApplication.class, args);
	}

	@RequestMapping("test")
	public String testFiegn (){
	return "我出来了";
	}
}

