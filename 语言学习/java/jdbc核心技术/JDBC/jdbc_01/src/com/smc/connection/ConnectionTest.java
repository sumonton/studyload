package com.smc.connection;



import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void testConnection1() throws SQLException {
        //获取厂商的实现类对象
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/rookie";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","smchen123");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    @Test
    public void testConnection2() throws Exception {
        //方式二
        //获取Driver实现类对象，使用反射来实现
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/rookie";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","smchen123");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);


    }

    @Test
    public void testConnection3() throws Exception {
        //方式三，使用DriverManagert替换Driver
        //1、获取Driver实现类对象，使用反射来实现
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2、提供三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/rookie";
        String username ="root";
        String password = "smchen123";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connect = DriverManager.getConnection(url,username,password);
        System.out.println(connect);
    }

    @Test
    public void testConnection4() throws Exception {
        //方式四，可是只是加载驱动，无需注册驱动
        //1、使用反射来加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //在加载过程中能自动注册驱动，因为在Mysql的Driver中有一个静态代码块
        /**
         * static {
        *              try {
        *             DriverManager.registerDriver(new Driver());
        *         } catch (SQLException var1) {
        *             throw new RuntimeException("Can't register driver!");
        *         }
        *     }
         */
        //2、提供三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/rookie";
        String username ="root";
        String password = "smchen123";
        //获取连接
        Connection connect = DriverManager.getConnection(url,username,password);
        System.out.println(connect);
    }

    @Test
    public void testConnection5() throws Exception {

        //1、读取配置文件中的四个基本信息
        InputStream in =  ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(in);

        //2、获取四个基本信息
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String driverClass = properties.getProperty("driverClass");
        //3、使用反射来加载驱动
        Class.forName(driverClass);
        //在加载过程中能自动注册驱动，因为在Mysql的Driver中有一个静态代码块

        //获取连接
        Connection connect = DriverManager.getConnection(url,username,password);
        System.out.println(connect);
    }

}
