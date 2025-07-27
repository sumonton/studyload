package com.smc.direct;

import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("console", true, false, false, null);
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warning");
        channel.basicConsume("console", true,
                ((consumeTag, message) -> {
                    System.out.println("ReceiveDirect01控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                }),
                (consumeTag -> {
                    System.out.println("aaa");
                }));
    }
}
