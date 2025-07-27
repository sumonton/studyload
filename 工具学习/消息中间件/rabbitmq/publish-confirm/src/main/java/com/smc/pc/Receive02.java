package com.smc.pc;

import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Receive02 {
    public static final String EXCHANGE_NAME ="logs";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
//        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        /**
         * 声明1个队列，临时队列
         *
         * 生成一个临时队列，队列名称是随机的
         * 当消费者断开与队列的连接时候，队列就自动删除
         */
        String queue = channel.queueDeclare().getQueue();
        /**
         * 绑定交换机和队列
         */
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("等待接收消息，把接收到消息打到屏幕上。。。。。。");


        //接收消息
        channel.basicConsume(queue,true,
                ((consumeTag,message)->{
                    System.out.println("Receive02控制台打印接收到的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
                }),
                (consumeTag->{
                    System.out.println("bbb");
                }));



    }
}
