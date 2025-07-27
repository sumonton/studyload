package com.jdbctemplate.service;

import com.jdbctemplate.dao.BookDao;
import com.jdbctemplate.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务类
 */
@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public void addBook(Book book){
        bookDao.add(book);
    }

    public void updateBook(Book book){
        bookDao.updateBook(book);
    }
    public void deleteBook(String id){
        bookDao.deleteBook(id);
    }

    public void countBook(){
        bookDao.countBook();
    }
    public void findOne(String id){
        bookDao.findOne(id);
    }

    public void findAll(){
        bookDao.findAll();
    }

    public void batchAdd(List<Object[]> batchs){
        bookDao.batchAdd(batchs);
    }

    public void batchUpdate(List<Object[]> batchs){
        bookDao.batchUpdate(batchs);
    }

    public void batchDelete(List<Object[]> batchs){
        bookDao.batchDelete(batchs);
    }
}
