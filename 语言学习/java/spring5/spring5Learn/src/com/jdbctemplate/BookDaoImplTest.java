package com.jdbctemplate;

import com.jdbctemplate.config.Config;
import com.jdbctemplate.domain.Book;
import com.jdbctemplate.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookDaoImplTest {
    @Test
    public void addTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book();
        book.setUserId("1");
        book.setUsername("java");
        book.setuStatus("a");

        bookService.addBook(book);
    }

    @Test
    public void updateTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book();
        book.setUserId("1");
        book.setUsername("python");
        book.setuStatus("b");

        bookService.updateBook(book);
    }

    @Test
    public void deleteTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);


        bookService.deleteBook("1");
    }
    @Test
    public void countTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);


        bookService.countBook();
    }
    @Test
    public void selectTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);


        bookService.findOne("1");
    }

    @Test
    public void selectAllTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);


        bookService.findAll();
    }

    @Test
    public void batchAddTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] obj1 = {"3","javaweb","cc"};
        Object[] obj2 = {"4","spring","dd"};
        Object[] obj3 = {"5","mysql","ee"};
        batchArgs.add(obj1);
        batchArgs.add(obj2);
        batchArgs.add(obj3);
        bookService.batchAdd(batchArgs);
    }

    @Test
    public void batchUpdateTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] obj1 = {"javawebUP","cc","3"};
        Object[] obj2 = {"springUP","dd","4"};
        Object[] obj3 = {"mysqlUP","ee","5"};
        batchArgs.add(obj1);
        batchArgs.add(obj2);
        batchArgs.add(obj3);
        bookService.batchUpdate(batchArgs);
    }

    @Test
    public void batchDeleteTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] obj1 = {"3"};
        Object[] obj2 = {"4"};
        batchArgs.add(obj1);
        batchArgs.add(obj2);
        bookService.batchDelete(batchArgs);
    }
}