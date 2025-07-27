package com.smc.java;

import java.lang.ref.Reference;

/**
 * @Date 2023/11/25
 * @Author smc
 * @Description:
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        System.gc();
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 重写了Finalize");
    }
}
