package com.smc.boot.Config;

import com.smc.boot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date 2022/5/7
 * @Author smc
 * @Description:
 * 1、编写一个拦截器实现HandlerInterceptor接口
 * 2、拦截器注册到容器中（实现WebMvcOnfigurer的addInterceptor）
 * 3、指定拦截规则【如拦截所有，静态资源也会被拦截】
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())//所有请求都被拦截，包括静态资源
                .addPathPatterns("/**").excludePathPatterns("/","/login","/css/**","/js/**");//放行的请求
    }
}
