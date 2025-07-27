package com.smc.demo2.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class BookServiceTest {
    @Test
    public void serviceTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.add();
    }
}