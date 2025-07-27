package com.smc.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Date 2022/5/8
 * @Author smc
 * @Description:
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户数量过多")
public class UserTooManyException extends RuntimeException{
    public UserTooManyException() {
    }

    public UserTooManyException(String message) {
        super(message);
    }
}
