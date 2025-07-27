package com.smc.redis02springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Date 2022/8/11
 * @Author smc
 * @Description:
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String name;

    private Integer age;
}
