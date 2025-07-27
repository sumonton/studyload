package com.smc.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2025/2/12
 * @Author smc
 * @Description:
 */
@RestController
public class CountController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String count(){
        Long count = redisTemplate.opsForValue().increment("penple-count");
        return "有【"+count+"】访问";
    }
}
