package com.sun.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.sun.aop"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Config {
}
