package com.forezp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 36189 on 2018/12/28.
 */
public class Test {

    public static void  main (String [] args){
        String password = new BCryptPasswordEncoder().encode("123456");
        System.out.println("============: " + password);
    }
}
