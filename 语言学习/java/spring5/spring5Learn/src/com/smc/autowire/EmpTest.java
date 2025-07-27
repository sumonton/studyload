package com.smc.autowire;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmpTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean9.xml");
        Emp emp = context.getBean("emp", Emp.class);

        System.out.println(emp.toString());
    }
}