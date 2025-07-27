package com.smc.mybatis.test;

import com.smc.mybatis.bean.Employee;
import com.smc.mybatis.dao.EmployeeMapper;
import com.smc.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description:
 */
public class EmployeeTest {
    /**
     * 1、根据xml配置w文件（全局配置文件）创建一个SqlSessionFFactory对象
     * 有数据源的一些环境信息
     * 2、sql映射文件，配置每一个sql，以及sql的封装规则
     * 3、将sql文件注册在全局文件中
     * 4、写代码
     * a、根据全局文件得到SqlSessionFactory
     * b、使用sqlSessiong工厂得到，获取sqlSession对象使用他来增删改查，一个sqlSession就代表和数据库的一次会话
     * 用完需要关闭
     * c、使用sql的唯一标识来告诉mybatis执行哪一个sql
     *
     * @throws IOException
     */
    @Test
    public void Test() throws IOException {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        //这个实例能够执行已经映射的sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.smc.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee.toString());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test1() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(empById);
        }finally {
            openSession.close();
        }



    }
    @Test
    public void Test2() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee empById = mapper.findEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(empById);
        }finally {
            openSession.close();
        }



    }
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

}