package com.smc.rabbitmq2;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.smc.utils.RabbitMqUtils;
import com.smc.utils.SleepUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Worker03 {
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待消息处理时间较长");
        DeliverCallback deliverCallback = (consumerTag,message)->{
            SleepUtils.sleep(30);
            System.out.println("接收到的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
            /**
             * 手动应答
             * 1、消息的标记
             * 2、是否批量应答，false：不批量，true：批量
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback callback = (consumerTag)->{

        };
        //设置不公平分发
//        channel.basicQos(1);
        //预取值5
        channel.basicQos(5);
        //采用手动应答
        channel.basicConsume(TASK_QUEUE_NAME,false,deliverCallback,(consumerTag->{
            System.out.println(consumerTag+"消费者取消消费接口回调");
        }));
    }

}
