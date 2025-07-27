package com.smc.java;

import static java.lang.Double.NaN;

/**
 * @Date 2023/12/10
 * @Author smc
 * @Description:
 */
public class CmpTest {
    public static void main(String[] args) {
        double a = 10.0;
        double b = NaN;
        double c = NaN;
        System.out.println(a > b);
        System.out.println(a < b);
        System.out.println(c > b);
    }
}
