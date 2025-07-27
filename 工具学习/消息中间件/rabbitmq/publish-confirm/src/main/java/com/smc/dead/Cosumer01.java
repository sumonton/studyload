package com.smc.dead;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Cosumer01 {

    //普通交换机
    public static final String NORMAL_EXCHANGE= "normal_exchange";
    //普通队列
    public static final String NORMAL_QUEUE= "normal_queue";
    //死信交换机
    public static final String DEAD_EXCHANGE= "dead_exchange";
    //死信队列
    public static final String DEAD_QUEUE= "dead_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        //转发死信交换机
        HashMap<String, Object> arguments = new HashMap<>();
        //过期时间参数
//        arguments.put("x-message-ttl",10000);
        //正常队列转发死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信routinKey
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置正常队列长度的设置
//        arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);

        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");


        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");

        System.out.println("等待接收消息");
        channel.basicConsume(NORMAL_QUEUE, false,
                ((consumeTag, message) -> {
                    if (new String(message.getBody(), StandardCharsets.UTF_8).equals("info5")) {
                        System.out.println("拒绝的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
                        /**
                         * 1、消息标签
                         * 2、是否放回队列
                         */
                        channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
                    }else{
                        System.out.println("NORMAL控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    }
                }),
                (consumeTag -> {
                    System.out.println("aaa");
                }));


    }
}
