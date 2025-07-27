package com.smc.preparedstatement.crud;

import com.smc.connection.ConnectionTest;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用PreparedStatement来替换Statement，来对数据库进行增删改查
 * @author smc
 */
public class PreparedStatementUpdateTest {


    @Test
    public void testInsert() {
        //1、读取配置文件中的四个基本信息
        InputStream in =  ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");


        Connection connect = null;
        PreparedStatement ps = null;
        try {
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
            connect = DriverManager.getConnection(url,username,password);
//            System.out.println(connect);

            //4、获取PreparedStatement对象
            String sql = "insert into customers(cust_id,cust_name,cust_city) values(?,?,?)";
            ps = connect.prepareStatement(sql);
            //5、填充占位符
            ps.setString(1,"10007");
            ps.setString(2,"smchen");
            ps.setString(3,"xiamen");

            //6、执行
            ps.execute();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
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


    @Test
    public void testUpdate() {
        //1、获取数据库连接
        Connection connect = null;
        PreparedStatement ps = null;
        try {
            connect = JDBCUtils.getConnection();
            //2、预编译sql语句，返回PreparedStatement的实例
            String sql = "update customers set cust_name = ? where cust_id = ?";
            ps = connect.prepareStatement(sql);
            //3、填充占位符
            ps.setString(1, "smc2");
            ps.setInt(2, 10006);
            //4、执行
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5、资源的关闭
            JDBCUtils.closeResources(connect, ps);
        }

    }
    @Test
    public void testCommonUpdate(){
        String sql = "delete from customers where cust_id = ?";
        update(sql,10006);
    }
    //通用的增删改操作
    public void update(String sql ,Object ...ags) {
        //1、获取数据库连接
        Connection connect = null;
        PreparedStatement ps = null;
        try {
            connect = JDBCUtils.getConnection();
            //2、预编译sql语句，返回PreparedStatement的实例
            ps = connect.prepareStatement(sql);
            //3、填充占位符
            for (int i = 0; i < ags.length; i++) {
                ps.setObject(i + 1, ags[i]);
            }
            //4、执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5、资源的关闭
            JDBCUtils.closeResources(connect, ps);
        }

    }

}
