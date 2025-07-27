package com.sun.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//增强类
@Component
@Aspect
@Order(3)
public class UserProxy {
    //相同的切入点抽取
    @Pointcut(value = "execution(* com.sun.aop.User.add(..))")
    public void pointCut(){

    }
    //前置通知
    @Before(value = "pointCut()")
    public void before(){
        System.out.println("before...");
    }
    //后置通知（返回通知）：在返回结果后执行，有异常不执行
    @AfterReturning(value = "pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }
    //最终通知，不管什么情况都会执行
    @After(value = "execution(* com.sun.aop.User.add(..))")
    public void after(){
        System.out.println("after...");
    }
    //异常通知
    @AfterThrowing(value = "execution(* com.sun.aop.User.add(..))")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }
    //环绕通知，执行前和执行后都执行
    @Around(value = "execution(* com.sun.aop.User.add(..))")
    public void around(ProceedingJoinPoint pjo) throws Throwable {
        System.out.println("around before...");
        //被增强的方法执行
        pjo.proceed();
        System.out.println("around after...");
    }
}
