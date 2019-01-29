package com.forezp.web;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by forezp on 2017/6/9.
 */
@RestController
public class HiController {

    @Autowired
    Tracer tracer;
    @Value("${server.port}")
    String port;
    @GetMapping("/hi")
    public String home(@RequestParam String name) {
        tracer.currentSpan().tag("name","forezp");
        return "hi "+name+",i am from port:" +port;
    }

}
