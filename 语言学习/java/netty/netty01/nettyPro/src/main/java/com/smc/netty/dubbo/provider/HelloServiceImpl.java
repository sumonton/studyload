package com.smc.netty.dubbo.provider;

import com.smc.netty.dubbo.publicinterface.HelloService;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:
 */
public class HelloServiceImpl implements HelloService {
    /**
     * 当有消费方调用该方法时，就返回一个结果
     *
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        String returnMsg = null;
        //根据msg返回不同结果
        if (msg != null) {
            returnMsg = "你好客户端，我已经接收到你的消息[" + msg + "]";
        } else {
            returnMsg = "你好客户端，我没有接收到你的消息";
        }
        return returnMsg;
    }
}
