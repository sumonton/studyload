package com.smc.boot;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.smc.boot.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Date 2022/5/1
 * @Author smc
 * @Description:主程序类
 * @SpringBootApplication：这是一个SpringBoot程序
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.smc.boot.servlet")
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class);
//        String[] beanNamesForType = run.getBeanNamesForType(User.class);
//        System.out.println("======");
//        for (String s : beanNamesForType) {
//            System.out.println(s);
//        }
        boolean bean = run.containsBean("user");
        System.out.println(bean);
        boolean bean1 = run.containsBean("user01");
        System.out.println("user01:"+bean1);

    }
}
