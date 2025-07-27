package com.sun.aop;

import org.springframework.stereotype.Component;

@Component
public class User {
    public void add(){
//        int a = 10/0;
        System.out.println("User add...");
    }
}
