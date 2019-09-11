package com.forezp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理类为ExceptionHandle 类，在该类中加上＠ ControllerAdvice 注解表明该类是一个异常统一处理类
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    /**
     * 通过＠ExceptionHandler 注解配置了统一处理UserLoginException 类的异常方法， 统一返回了异常的message 信息，
     */
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> handleException(Exception e) {

        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
