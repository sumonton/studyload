package com.smc.rabbitmq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Worker01 {
    //队列的名称
    public static final String QUEUE_NAME="hello world";

    public static void main(String[] args) throws IOException {
        //接收消息
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收到的消息："+new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+"消息者取消消费接口回调逻辑");
        };
        System.out.println("C2接收消息");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }


}
