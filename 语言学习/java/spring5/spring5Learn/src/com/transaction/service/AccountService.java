package com.transaction.service;

import com.transaction.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountDao accountDao;
    public void accountBalance(){
            accountDao.reduceMoney();
//            int i = 10/0;
            accountDao.addMoney();
    }
}
