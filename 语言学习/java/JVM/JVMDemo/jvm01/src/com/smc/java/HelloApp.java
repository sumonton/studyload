package com.smc.java;

/**
 * @Date 2023/7/6
 * @Author smc
 * @Description:
 */
public class HelloApp {
    private static int a = 1;
    static {
        a=2;
    }
    public static void main(String[] args) {
        System.out.println(a);
    }
}
