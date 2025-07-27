package com.smc.preparedstatement.crud;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 获取数据库连接
     *
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //1、读取配置文件中的四个基本信息
        InputStream in =  ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
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

        //3、获取连接
        Connection connect = DriverManager.getConnection(url,username,password);
        return connect;
    }

    public static void closeResources(Connection connect, Statement ps){
        try {
            //7、关闭z资源
            if (ps!=null){
                ps.close();
            }
            if (connect!=null){
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
