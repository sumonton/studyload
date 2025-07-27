package com.smc.demo4;

import com.smc.demo5.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StuTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu.toString());
    }
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
        Book book = context.getBean("book", Book.class);
        System.out.println(book.toString());
    }

}