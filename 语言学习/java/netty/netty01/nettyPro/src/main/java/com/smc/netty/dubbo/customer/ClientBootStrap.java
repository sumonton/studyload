package com.smc.netty.dubbo.customer;

import com.smc.netty.dubbo.netty.NettyClient;
import com.smc.netty.dubbo.publicinterface.HelloService;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:
 */
public class ClientBootStrap {
    //定义协议头
    public static final String providerName= "HelloService#hello#";

    public static void main(String[] args) {
        //创建一个消费者
        NettyClient nettyClient = new NettyClient();

        //创建代理对象
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, providerName);

        //通过代理对象调用这个服务提供者的方法
        String res = helloService.hello("你好，dubbo");

        System.out.println("调用的结果:" + res);
    }
}
