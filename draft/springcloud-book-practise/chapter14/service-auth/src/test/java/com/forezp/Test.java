package com.forezp;

import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by 36189 on 2019/1/2.
 */
public class Test {
    public static void main (String [] args){

        try {
            System.out.println(Base64.encode("service-hi:123456".getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
