package com.smc.mybatis.test;
import com.smc.mybatis.bean.Department;

import com.smc.mybatis.bean.Employee;
import com.smc.mybatis.dao.EmployeeMapper;
import com.smc.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(empById);
        } finally {
            openSession.close();
        }


    }

    // @Test
// public void Test2() throws IOException {
//  //获取SqlSessionFactory
//  SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//  //获取sqlSession对象
//  SqlSession openSession = sqlSessionFactory.openSession();
//
//  try{
//   //获取接口实现类对象
//   //会为接口创建一个代理对象，代理对象去实现增删改查
//   EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
//   Employee empById = mapper.findEmpById(1);
//   System.out.println(mapper.getClass());
//   System.out.println(empById);
//  }finally {
//   openSession.close();
//  }
//
//
//
// }

    /**
     * mybatis允许增删该查直接定义一下类型的返回值
     * Integer，Long，Boolean
     * @throws IOException
     */
    @Test
    public void Test3() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);

            System.out.println(mapper.getClass());
            System.out.println(empById);
            //添加
            Employee employee = new Employee();
            employee.setId(2);
            employee.setGender("0");
            employee.setEmail("1234@163.com");
            employee.setLastName("smchen");
            System.out.println(mapper.addEmp(employee));
            //修改
            employee.setEmail("4321@163.com");

            System.out.println(mapper.updateEmp(employee));
            //删除

            System.out.println(mapper.deleteEmpById(3));
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void Test4() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);

            System.out.println(mapper.getClass());
            System.out.println(empById);
            //添加
            Employee employee = new Employee();
            employee.setGender("0");
            employee.setEmail("123456789@163.com");
            employee.setLastName("smchen123");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void Test5() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            List<Employee> employees = mapper.findEmployees();
            employees.stream().forEach(System.out::println);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void Test6() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Object> empByIdReturnMap = mapper.findEmpByIdReturnMap(1);
            System.out.println(empByIdReturnMap);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void Test7() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Employee> empByIdMap = mapper.findEmpByIdMap();
            System.out.println(empByIdMap);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void test8() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
//            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee();
            employee.setId(1);
            employee.setGender(null);
            employee.setEmail(null);
            employee.setLastName("tom1");

//            List<Employee> empsByConditionIf = mapper.findEmpsByConditionIf(employee);
//            List<Employee> empsByConditionIf = mapper.findEmpsByConditionTrim(employee);
//            List<Employee> empsByConditionIf = mapper.findEmpsByConditionChoose(employee);
            Long aLong = mapper.updateEmployee(employee);
            System.out.println(aLong);
//            empsByConditionIf.stream().forEach(System.out::println);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test9() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);

            List<Employee> employeeForeach = mapper.findEmployeeForeach(Arrays.asList(1, 2, 3, 4));

            employeeForeach.stream().forEach(System.out::println);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test10() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(null,"smith1","1","smith@123.com",new Department(1));
            Employee employee1 = new Employee(null,"zhangsan1","1","zhangsan@123.com",new Department(1));
            List<Employee> list = new ArrayList<>();
            list.add(employee);
            list.add(employee1);
            Long aLong = mapper.addEmpsForeach(list);
            System.out.println(aLong);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void test11() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(null,"mc","1","smith@123.com",new Department(1));
            List<Employee> empsTestInnerParameter = mapper.getEmpsTestInnerParameter(employee);
            empsTestInnerParameter.stream().forEach(System.out::println);
            //提交
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
    @Test
    public void test12() throws IOException {
        //获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession对象没有加参数是不会自动提交的openSession，需要手动提交
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            //获取接口实现类对象
            //会为接口创建一个代理对象，代理对象去实现增删改查
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.findEmpById(1);
            System.out.println(empById);
            openSession.clearCache();
            Employee empById1 = mapper.findEmpById(1);
            System.out.println(empById1);
            System.out.println(empById1 == empById);//false
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