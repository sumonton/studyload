package com.smc.demo2;

import com.smc.demo1.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class BookTest {
    @Test
    public void testBook(){
        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        System.out.println(book);
        book.toString();
    }
    @Test
    public void testBook1(){
        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book1", Book.class);
        System.out.println(book);
        book.toString();
    }

    @Test
    public void testBook2(){
        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book2", Book.class);
        System.out.println(book);
        book.toString();
    }

    @Test
    public void testBook3(){
        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book3", Book.class);
        System.out.println(book);
        book.toString();
    }
    @Test
    public void testBook4(){
        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //获取配置创建的对象
        Book book = context.getBean("book4", Book.class);
        System.out.println(book);
        book.toString();
    }
}