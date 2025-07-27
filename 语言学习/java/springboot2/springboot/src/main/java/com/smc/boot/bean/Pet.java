package com.smc.boot.bean;

import lombok.Data;

/**
 * @Date 2022/5/1
 * @Author smc
 * @Description:
 */
@Data
public class Pet {
    private String name;
    private Integer age;
    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
    }
}
