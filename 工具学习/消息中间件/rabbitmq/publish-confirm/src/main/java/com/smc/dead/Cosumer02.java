package com.smc.dead;

import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Cosumer02 {


    //死信交换机
    public static final String DEAD_EXCHANGE= "dead_exchange";
    //死信队列
    public static final String DEAD_QUEUE= "dead_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");

        channel.basicConsume(DEAD_QUEUE, true,
                ((consumeTag, message) -> {
                    System.out.println("Dead控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                }),
                (consumeTag -> {
                    System.out.println("bbb");
                }));

    }
}
