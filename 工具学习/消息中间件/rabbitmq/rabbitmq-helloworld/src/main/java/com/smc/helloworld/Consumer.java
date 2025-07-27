package com.smc.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/27
 * @Author smc
 * @Description:
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "mirror world";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂和连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.120");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();


        //由链接创建信道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("fed_exchange",BuiltinExchangeType.DIRECT);
        channel.queueDeclare("node1_queue",true,false,false,null);
        channel.queueBind("node1_queue","fed_exchange","routingkey");
//        声明接收消息
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };
//        取消消息时回调
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("消费消息被中断");
        };
        /**
         * 消费者从信道接收信息
         * 1、消费哪个队列
         * 2、消费成功之后是否要自动应答 true代表自动应到，false代表手动应答
         * 3、消费者未成功消费的回调
         * 4、消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }

}
