package com.forezp.adminclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AdminClientApplication {

    private Logger logger = LoggerFactory.getLogger(AdminClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AdminClientApplication.class, args);
    }




    @GetMapping("/test")
    public String test() {
        logger.info("1234wedwe");
        return "ok";
    }
}

