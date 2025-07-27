package com.smc.java;

/**
 * @Date 2023/7/18
 * @Author smc
 * @Description:
 */
public class StackOverErrorMain {
    public static  int count =1;
    public static void main(String[] args) {
        count++;
        System.out.println(count);
        main(args);

    }
}
