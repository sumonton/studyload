package com.jdbctemplate.dao;

import com.jdbctemplate.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * dao类
 */
@Repository
public class BookDaoImpl implements BookDao{
    //注入jdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        //创建sql语句
        String sql = "insert into t_book values(?,?,?)";
        //调用实现方法
        Object[] objects = {book.getUserId(), book.getUsername(), book.getuStatus()};
        int update = jdbcTemplate.update(sql, objects);
        System.out.println(update);
    }

    @Override
    public void updateBook(Book book) {
        //创建sql语句
        String sql = "update t_book set username = ?,ustatus=? where user_id=?";
        Object[] objects = {book.getUsername(), book.getuStatus(),book.getUserId()};
        int update = jdbcTemplate.update(sql, objects);
        System.out.println(update);
    }

    @Override
    public void deleteBook(String id) {
        //创建sql语句
        String sql = "delete from t_book where user_id=?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println(update);
    }

    @Override
    public void countBook() {
        String sql = "select count(user_id) from t_book";
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        System.out.println(count);

    }

    @Override
    public void findOne(String id) {
        String sql = "select * from t_book where user_id = ?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
        System.out.println(book.toString());

    }

    @Override
    public void findAll() {
        String sql = "select * from t_book";
        List<Book> books = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
        books.stream().forEach(System.out::println);
    }

    @Override
    public void batchAdd(List<Object[]> batchs) {
        String sql ="insert into t_book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdate(List<Object[]> batchs) {
        String sql ="update t_book set username = ?,ustatus=? where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDelete(List<Object[]> batchs) {
        //创建sql语句
        String sql = "delete from t_book where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
        System.out.println(Arrays.toString(ints));
    }

}
