package com.forezp.web;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @RestController 是Spring4.0版本的一个注解， 它的功能相当于@Controller 注解和@ResponseBody 注解之和
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

}