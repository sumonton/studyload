package com.smc.helloworld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/27
 * @Author smc
 * @Description:
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "mirror world";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP，连接RabbitMQ的队列
        factory.setHost("192.168.1.109");
        //设置用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 1、队列名称
         * 2、队列里面的消息是否持久化（磁盘），默认情况消息存储在内存中
         * 3、该队列是否只供一个消费者进行消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费
         * 4、是否自动删除，最后一个消费者断开连接以后，该队列是否自动删除 true自动删除，false不自动删除
         * 5、其他参数
         */
        HashMap<String, Object> arguments = new HashMap<>();
        //官方允许是0-255之间，此处设置10 允许优先级范围为0-10 不要设置过大，浪费cpu与内存
        arguments.put("x-max-priority",10);
        channel.queueDeclare(QUEUE_NAME,false,false,false,arguments);
        //发送的消息
        String msg = "hello world";
        /**
         * 使用channel发送消息
         * 1、发送到哪个交换机
         * 2、路由的key值是哪个，本地的队列是名称
         * 3、其他参数信息
         * 4、发送消息是消息体
         */
        for (int i = 0; i < 11; i++) {
            String temp = msg+i;
            if(i == 5){
                AMQP.BasicProperties properties = new AMQP.BasicProperties().
                        builder().priority(5).build();
                channel.basicPublish("",QUEUE_NAME,properties,temp.getBytes());
            }else{
                channel.basicPublish("",QUEUE_NAME,null,temp.getBytes());
            }
        }



        System.out.println("发送完毕");


    }
}
