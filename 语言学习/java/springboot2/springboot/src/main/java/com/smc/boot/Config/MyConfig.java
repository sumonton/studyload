package com.smc.boot.Config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.smc.boot.bean.Admin;
import com.smc.boot.bean.Pet;
import com.smc.boot.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * @Date 2022/5/1
 * @Author smc
 * @Description:告诉SpringBoot这个是配置文件
 * 配置类里面使用@Bean标注在方法上注册组件，默认是单实例的
 * 配置类本身也是组件
 * proxyBeanMethods:full模式和Lite模式
 *     true:运行时组件方法是都会判定是否容器中有该组件，有则返回该组件。
 *     false：每次运行都会创建新的组件
 *     即配置类组件间的依赖关系是lite模式时加速启动过程，减少判断
 *     配置类组件间的依赖是Full模式时，方法会调用之前的单实例
 *
 */
@Import({User.class, SimpleObjectIdResolver.class})
@Configuration(proxyBeanMethods = false)
@ImportResource("classpath:beans.xml")
//@EnableConfigurationProperties(Admin.class)
//开启Admin配置绑定功能
//把这个Admin自动注册到容器中
public class MyConfig {
    /**
     * 给容器添加组件，此方法名作为bean的id，返回值为容器中的实例
     * @return
     */
    @ConditionalOnMissingBean(name = "tom")
    @Bean
    public User user(){
        return new User("张三",18);
    }


//    @Bean("tom")
    public Pet pet(){
        return new Pet("tomcat");
    }
}
