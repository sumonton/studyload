package com.smc.rabbitmq;

import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Task01 {
    public static final String QUEUE_NAME="hello world";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台当中发送信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送信息完成："+message);
        }
    }
}
