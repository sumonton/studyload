package com.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@ComponentScan(basePackages = {"com.transaction"})
//@EnableTransactionManagement //开启事务
public class TxConfig {
    //创建数据库
    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource =new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/user_db");
        dataSource.setUsername("root");
        dataSource.setPassword("smchen123");
        return dataSource;
    }

    //创建JdbcTemplate模版对象
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        //根据类型到IOC容器找到dataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        //注入datasoruce
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
    //创建事务管理器
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
