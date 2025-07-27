package com.smc.java;

import java.sql.DriverManager;

/**
 * @Date 2023/7/7
 * @Author smc
 * @Description:
 */
public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);

        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        System.out.println(parent);




    }
}
