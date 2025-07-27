package com.smc.dead;

import com.rabbitmq.client.AMQP;
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
 * 死信队列生产者代码
 */
public class DeadProducer {
    public static final String NORMAL_EXCHANGE= "normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE= "dead_exchange";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明普通交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明死信交换机
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //死信消息，设置TTL
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info"+i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes(StandardCharsets.UTF_8));

        }
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()){
//            String msg = scanner.next();
//            //输入逗号前面为key，后面为消息
//            String[] message=msg.split(",");
//            channel.basicPublish(NORMAL_EXCHANGE,message[0],null,message[1].getBytes(StandardCharsets.UTF_8));
//            System.out.println("生产者发送消息："+msg);
//        }

    }

}
