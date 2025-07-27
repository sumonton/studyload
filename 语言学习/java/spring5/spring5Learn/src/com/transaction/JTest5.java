package com.transaction;

import com.transaction.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:jdbc.xml")
@SpringJUnitConfig(locations = "classpath:jdbc.xml")
public class JTest5 {
    @Autowired
    private AccountService accountService;
    @Test
    public void test(){
        accountService.accountBalance();
    }
}
