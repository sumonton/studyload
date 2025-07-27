package com.smc.utils;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class SleepUtils {

    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
