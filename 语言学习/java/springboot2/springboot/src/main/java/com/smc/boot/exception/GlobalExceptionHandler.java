package com.smc.boot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Date 2022/5/8
 * @Author smc
 * @Description:
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ArithmeticException.class,NullPointerException.class})//处理异常
    public String handlerAtrithException(Exception e) {
        log.error("异常是：{}", e);
        //返回视图地址
        return "login";
    }
}
