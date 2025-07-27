package com.smc.demo8.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.smc.demo8"})//扫描
public class SpringConfig {
}
