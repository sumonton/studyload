package com.smc.demo6;

import com.smc.demo4.Course;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class MyFactoryBeanTest {
    @Test
    public void factoryTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        Course myFactoryBean = context.getBean("myFactoryBean", Course.class);
        System.out.println(myFactoryBean.toString());
    }

}