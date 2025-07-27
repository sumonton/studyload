package com.smc.boot.bean;

import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.Date;

/**
 * @Date 2022/5/3
 * @Author smc
 * @Description:
 */
@Profile("test")
@Data
public class Person {
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
}
