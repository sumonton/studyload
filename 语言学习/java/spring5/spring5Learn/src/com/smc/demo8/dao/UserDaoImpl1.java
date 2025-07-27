package com.smc.demo8.dao;

import org.springframework.stereotype.Repository;

@Repository(value = "userDaoImpl1")
public class UserDaoImpl1 implements UserDao{
    @Override
    public void add() {
        System.out.println("UserDaoImpl1 add...");
    }
}
