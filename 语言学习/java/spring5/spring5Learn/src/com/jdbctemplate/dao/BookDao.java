package com.jdbctemplate.dao;

import com.jdbctemplate.domain.Book;

import java.util.List;

public interface BookDao {
    void add(Book book);

    void updateBook(Book book);

    void deleteBook(String id);

    void countBook();

    void findOne(String id);

    void findAll();

    void batchAdd(List<Object[]> batchs);

    void batchUpdate(List<Object[]> batchs);

    void batchDelete(List<Object[]> batchs);
}
