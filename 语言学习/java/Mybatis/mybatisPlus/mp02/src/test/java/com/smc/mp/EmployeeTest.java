package com.smc.mp;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/4/12
 * @Author smc
 * @Description:
 */
public class EmployeeTest {
    private ApplicationContext ioc =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    /**
     * 代码生成器
     */
    @Test
    public void testGenerate(){
        //1、全局配置

        //2、数据源配置
        //3、策略配置
        //4、包名策略配置
        //5、整合配置
    }


}