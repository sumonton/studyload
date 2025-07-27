package com.smc.demo8.service;

import com.smc.demo8.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//在注解里面value属性值可省略，默认值类名称，首字母小写
@Service(value = "userService") //类似<bean id="userservice" ..></bean>
public class UserService {

    @Value(value = "无敌")
    private String name;

    @Resource(name = "userDaoImpl1")
    private UserDao userDao;

    public void add(){
        userDao.add();
        System.out.println("UserService add..." + name);
    }
}
