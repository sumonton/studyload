package com.sun.aopxml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class BookTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean13.xml");
        Book book = context.getBean("book", Book.class);
        book.buy();
    }

}