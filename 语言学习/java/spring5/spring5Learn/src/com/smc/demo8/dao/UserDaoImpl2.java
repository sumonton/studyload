package com.smc.demo8.dao;

import org.springframework.stereotype.Repository;

@Repository(value = "userDaoImpl2")
public class UserDaoImpl2 implements UserDao{
    @Override
    public void add() {
        System.out.println("UserDaoImpl2 add...");
    }
}
