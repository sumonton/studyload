package com.smc.java;

/**
 * @Date 2023/7/7
 * @Author smc
 * @Description:
 */
public class ClassLoaderTest {
    public static void main(String[] args) {

        //获取系统类加载器：sun.misc.Launcher$AppClassLoader@18b4aac2
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        //获取其上次能：扩展类加载器：sun.misc.Launcher$ExtClassLoader@5aaa6d82
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);

        //获取其上层：null
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);

        //对于用户自定义类来说：默认使用系统该类加载器进行加载：sun.misc.Launcher$AppClassLoader@18b4aac2
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);

        //String类使用引导类进行加载。-->Java核心类库都是使用引导类加载器进行加载：null
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);
    }
}
