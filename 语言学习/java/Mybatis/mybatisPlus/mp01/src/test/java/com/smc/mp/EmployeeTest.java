package com.smc.mp;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smc.mp.bean.Employee;
import com.smc.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/4/12
 * @Author smc
 * @Description:
 */
public class EmployeeTest {
    private ApplicationContext ioc =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper",EmployeeMapper.class);

    @Test
    public void testCommonInsert(){
        Employee employee = new Employee();
        employee.setAge(20);
//        employee.setId(0);
        employee.setLastName("sssss");
        employee.setGender("1");
        employee.setSalary(2222.2);
        employee.setEmail("sssss@163.com");
        int insert = employeeMapper.insert(employee);
        System.out.println(employee);
        System.out.println(insert);


    }

    @Test
    public void testCommonUpdate(){
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setId(4);
        employee.setLastName("ddddd");
        employee.setGender("1");
        employee.setSalary(2222.2);
        employee.setEmail("dddd@163.com");
        int i = employeeMapper.updateById(employee);

        System.out.println(employee);
        System.out.println(i);
    }

    @Test
    public void testCommonSelect(){
//        Employee employee = employeeMapper.selectById(4);
        //多个id进行查询
//        List<Integer> list = new ArrayList<>();
//        list.add(4);
//        list.add(5);
//        list.add(6);
//        List<Employee> employees = employeeMapper.selectBatchIds(list);
        //通过map封装条件查询
//        Map<String, Object> map = new HashMap<>();
//        map.put("last_name","ddddd");
//        map.put("age",20);
//        List<Employee> employees = employeeMapper.selectByMap(map);
        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(1, 2), null);
        employeePage.getRecords().forEach(System.out::println);
    }

    /**
     * 条件构造器
     * @throws SQLException
     */
    @Test
    public void testWrapperEntity(){

//        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(1, 2),
//                new QueryWrapper<Employee>().eq("gender", "1").between("age",19,21));
//        employeePage.getRecords().forEach(System.out::println);
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().
                eq("gender", 1).like("last_name", "mc")
                .or()   //WHERE (gender = ? AND last_name LIKE ? OR email LIKE ?)
                .like("email", "163"));
        employees.forEach(System.out::println);

    }

    @Test
    public void testWrapperEntityUpdate(){

//        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(1, 2),
//                new QueryWrapper<Employee>().eq("gender", "1").between("age",19,21));
//        employeePage.getRecords().forEach(System.out::println);
        Employee employee = new Employee();
        employee.setLastName("bbbb");
        employee.setAge(16);
        employee.setGender("0");
        int update = employeeMapper.update(employee, new UpdateWrapper<Employee>()
                .eq("age", 20)
                .like("last_name", "ssss"));
        System.out.println(update);

    }
    @Test
    public void testWrapperEntityDelete(){

//        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(1, 2),
//                new QueryWrapper<Employee>().eq("gender", "1").between("age",19,21));
//        employeePage.getRecords().forEach(System.out::println);
        int delete = employeeMapper.delete(new UpdateWrapper<Employee>()
                .eq("age", 16)
                .like("last_name", "bbbb"));
        System.out.println(delete);


    }
    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ioc.getBean("dataSource",DataSource.class);
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }
    /**
     * AR的插入操作
     */
    @Test
    public void testARInsert(){
        Employee employee = new Employee();
        employee.setAge(26);
        employee.setLastName("林建国");
        employee.setGender("1");
        employee.setEmail("ljg@169.com");
        boolean insert = employee.insert();
        System.out.println(insert);

    }
    /**
     * AR的根据id修改
     */
    @Test
    public void testARUpdate(){
        Employee employee = new Employee();
        employee.setId(5);
        employee.setAge(26);
        employee.setLastName("书蒙尘");
        employee.setGender("1");
        employee.setEmail("smc@169.com");
        boolean b = employee.updateById();
        System.out.println(b);

    }
    /**
     * AR的查询操作
     */
    @Test
    public void testARSelect(){
        Employee employee = new Employee();
        Model model = employee.selectById(5);
        System.out.println(model);
        employee.setId(6);
        Model model1 = employee.selectById();
        System.out.println(model1);
    }

    @Test
    public void testOptimisticLockInterceptor(){
        Employee employee = employeeMapper.selectById(8);
        employee.setLastName("vvvvv");
        employee.setGender("0");
        employee.setAge(25);
        int i = employeeMapper.updateById(employee);
        System.out.println(i);
    }
    @Test
    public void testInjector(){
        int i = employeeMapper.logicDelete();
        System.out.println(i);
    }
}