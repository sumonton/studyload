package com.smc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;

public class C3p0Test {
    @Test
    public void testGetSourcer() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/rookie");
        cpds.setUser("root");
        cpds.setPassword("Smchen123");
        //设置初始时数据库连接池中的数据处
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);

        //销毁c3p0连接池
        DataSources.destroy(cpds);
    }
}
