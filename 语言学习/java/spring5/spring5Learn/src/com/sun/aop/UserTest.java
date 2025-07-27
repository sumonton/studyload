package com.sun.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void test(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        User user = context.getBean("user", User.class);
        user.add();
    }

}