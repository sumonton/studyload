package com.smc.boot.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/5/2
 * @Author smc
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "admin")
public class Admin {
    private String name;
    private Integer age;
}
