package com.smc.java;

/**
 * @Date 2023/8/22
 * @Author smc
 * @Description:
 */
public class EdenDemo {
    public static void main(String[] args) {
        System.out.println("EdenDemo");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
