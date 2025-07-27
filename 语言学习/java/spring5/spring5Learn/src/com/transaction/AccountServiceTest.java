package com.transaction;

import com.transaction.config.TxConfig;
import com.transaction.domain.User;
import com.transaction.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.Assert.*;

public class AccountServiceTest {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.accountBalance();
    }

    @Test
    public void test2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        AccountService accountService = context.getBean("accountService", AccountService.class);
        accountService.accountBalance();
    }
    @Test
    public void test3(){
        //创建GenericApplicationContext对象
        GenericApplicationContext context = new GenericApplicationContext();
        //调用context方法进行对象注册
        context.refresh();
//        context.registerBean(User.class,() -> new User());
        context.registerBean("user1",User.class,() -> new User());
        //获取spring中注册的对象
//        User user = (User) context.getBean("com.transaction.domain.User");
//        System.out.println(user);
        //获取spring中注册的对象
        User user1 = (User) context.getBean("user1");
        System.out.println(user1);
    }

}