package com.smc.demo3;

/**
 * 部门类
 */
public class Dept {
    private String name;

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Dept{" +
                "name='" + name + '\'' +
                '}';
    }
}
