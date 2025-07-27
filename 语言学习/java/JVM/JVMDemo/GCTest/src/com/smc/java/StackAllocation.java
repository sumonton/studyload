package com.smc.java;

/**
 * @Date 2023/8/24
 * @Author smc
 * @Description:
 */
public class StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        String a；
        long end = System.currentTimeMillis();
        System.out.println("话费时间：" + (end - start) + "ms");
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void alloc() {
        String user = new String("aaa");
    }


}


