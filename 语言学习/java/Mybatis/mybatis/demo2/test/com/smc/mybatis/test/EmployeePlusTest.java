package com.smc.mybatis.test;

import com.smc.mybatis.bean.Department;
import com.smc.mybatis.bean.Employee;
import com.smc.mybatis.dao.DepartmentMapper;
import com.smc.mybatis.dao.EmployeeMapper;
import com.smc.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/4/2
 * @Author smc
 * @Description:
 */
public class EmployeePlusTest {

    @Test
    public void test() throws IOException {

        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            Employee byId = mapper.findById(1);
            System.out.println(byId.toString());
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void test1() throws IOException {

        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//            Employee byId = mapper.getEmpAndDept(1);
//            Employee byId = mapper.getEmpByIdStep(1);
            Employee byId = mapper.getEmpByIdStep1(4);
//            System.out.println(byId.getLastName());
            System.out.println(byId);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void test2() throws IOException {

        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//            Department byIdAndEmps = mapper.findByIdAndEmps(1);
            Department byIdAndEmps = mapper.findDeptByIdStep(1);
            System.out.println(byIdAndEmps);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

}