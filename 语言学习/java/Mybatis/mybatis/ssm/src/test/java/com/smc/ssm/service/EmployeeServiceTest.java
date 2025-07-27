package com.smc.ssm.service;


import com.smc.ssm.bean.Employee;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Date 2022/4/8
 * @Author smc
 * @Description:
 */
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService = new EmployeeService();
    /**
     * 插件编写
     * 1、编写Interceptor的实现类
     * 2、使用@Interceptors注解完成插件签名
     * 3、将写好的插件注册到全局配置文件中
     */
    @Test
    public void test(){
        List<Employee> employees = employeeService.getEmployees();
        employees.forEach(System.out::println);

    }

}