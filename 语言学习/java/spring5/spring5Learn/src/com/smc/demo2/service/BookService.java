package com.smc.demo2.service;

import com.smc.demo2.dao.BookDao;

public class BookService {
    //创建
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void add(){
        System.out.println("service add");
        bookDao.update();
    }
}
