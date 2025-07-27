package com.smc.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:连接工厂创建信道的工具类
 */
public class RabbitMqUtils {
    //得到一个连接的channel
    public static Channel getChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.109");
        factory.setUsername("admin");
        factory.setPassword("123");
        Channel channel = null;
        try {
            channel = factory.newConnection().createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

}
