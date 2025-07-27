package com.smc.java;

/**
 * @Date 2023/8/21
 * @Author smc
 * @Description:
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms:" + initialMemory+"M");
        System.out.println("-Xmx:" + maxMemory+"M");
//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
