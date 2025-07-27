package com.smc.java;

import java.util.ArrayList;

/**
 * @Date 2023/12/24
 * @Author smc
 * @Description:
 */
public class GcDumpTest {
    public static void main(String[] args) {
        ArrayList<byte[]> objects = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[1024 * 100];
            objects.add(bytes);
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
