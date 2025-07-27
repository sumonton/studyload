package com.smc.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 * 发消息给交换机
 */
public class TopicLog {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            //输入逗号前面为key，后面为消息
            String[] message=msg.split(",");
            channel.basicPublish(EXCHANGE_NAME,message[0],null,message[1].getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息："+msg);
        }

    }

}
