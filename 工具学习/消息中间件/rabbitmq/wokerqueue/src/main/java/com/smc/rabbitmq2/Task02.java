package com.smc.rabbitmq2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:消息在手动应答时时不丢失的、放回队列中重新消费
 */
public class Task02 {
    //名称
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        //开启发布确认
        channel.confirmSelect();
        //声明队列
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
        //在控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产这发出消息："+msg);
        }

    }
}
