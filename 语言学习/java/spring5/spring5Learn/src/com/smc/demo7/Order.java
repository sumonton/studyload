package com.smc.demo7;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class Order {
    private String oname;
    public Order() {
        System.out.println("第一步，执行无参构造方法");
    }

    public void setOname(String oname) {
        System.out.println("第二步，设置属性值");
        this.oname = oname;
    }


    //初始化方法
    public void initMethod(){
        System.out.println("第三步，初始化方法");
    }

    //销毁方法
    public void destroyMethod(){
        System.out.println("第五步，销毁bean方法");
    }
}
