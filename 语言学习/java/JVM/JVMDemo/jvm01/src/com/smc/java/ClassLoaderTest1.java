package com.smc.java;

import sun.misc.Launcher;

import java.net.URL;
import java.util.Properties;

/**
 * @Date 2023/7/7
 * @Author smc
 * @Description:
 */
public class ClassLoaderTest1 {
    public static void main(String[] args)  {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL element:urLs
             ) {
            System.out.println(element.toExternalForm());
        }

        System.out.println("***扩展类加载器***");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path:
             extDirs.split(";")) {
            System.out.println(path);
        }
    }
}
