package com.smc.demo7;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class OrderTest {
    @Test
    public void testOrder(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
        Order order = context.getBean("order", Order.class);
        System.out.println("第四步，获取到bean："+order.toString());
        //手动销毁
        ((ClassPathXmlApplicationContext)context).close();


    }
}