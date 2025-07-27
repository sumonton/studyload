package com.smc.boot.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @Date 2022/5/15
 * @Author smc
 * @Description:
 */
@Configuration
public class MyDataSourceConfig {
    /**
     * 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
     * @return
     */
    @ConfigurationProperties("spring.datasource")//默认跟配置文件spring.datasource绑定
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        //加入监控功能
        dataSource.setFilters("stat");
        return dataSource;
    }

    /**
     * 配置druid的监控页功能
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/");
        return statViewServletServletRegistrationBean;
    }

    /**
     * WebStatFilter用于采集web-jdbc关联监控的数据
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> webStatFilterFilterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
        webStatFilterFilterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        webStatFilterFilterRegistrationBean.addInitParameter("exclusions",
                "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return webStatFilterFilterRegistrationBean;
    }
}
