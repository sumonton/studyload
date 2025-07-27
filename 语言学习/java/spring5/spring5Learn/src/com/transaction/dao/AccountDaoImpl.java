package com.transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addMoney() {
        //创建sql语句
        String sql = "update t_account set balance = balance + ? where username=?";
        Object[] objects = {100, "mary"};
        int update = jdbcTemplate.update(sql, objects);
        System.out.println(update);
    }

    @Override
    public void reduceMoney() {
        //创建sql语句
        String sql = "update t_account set balance = balance - ? where username=?";
        Object[] objects = {100, "lucy"};
        int update = jdbcTemplate.update(sql, objects);
        System.out.println(update);
    }
}
