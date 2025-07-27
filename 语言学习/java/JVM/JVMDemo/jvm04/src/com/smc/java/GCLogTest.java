package com.smc.java;

import java.util.ArrayList;

/**
 * @Date 2023/12/5
 * @Author smc
 * @Description:
 */
public class GCLogTest {
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            byte[] bytes = new byte[1024 * 100];
            objects.add(bytes);
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
