package com.smc.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 * 发布确认模式
 * 1、单个确认模式
 * 2、批量确认模式
 * 3、异步确认模式
 */
public class ConfirmMessage {
    //发消息的个数
    public static final int MESSAGE_COUNT=1000;
    public static void main(String[] args) throws IOException, InterruptedException {
        //1、单个确认
//        ConfirmMessage.publishMessageIndividually();//耗时：3078
        //2、批量确认
//        ConfirmMessage.publishMessageByBatch();//耗时：296
        //3、异步批量确认
        ConfirmMessage.publishMessageSync();
    }
    //单个确认
    public static void publishMessageIndividually() throws IOException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();

        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i+"";
            channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
            //单个消息就马上发布确认
            boolean flag = channel.waitForConfirms();
//            if (flag){
//                System.out.println("消息发送成功");
//            }

        }

        System.out.println("耗时："+(System.currentTimeMillis()-begin));

    }

    //批量发布确认
    public static void publishMessageByBatch() throws IOException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();

        //批量确认消息大小
        int batchSize = 100;
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i+"";
            channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));

            if (i%100==0){
                //达到一百确认
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("消息发送成功");
                }
            }

        }

        System.out.println("耗时："+(System.currentTimeMillis()-begin));
    }

    //异步发布确认
    public static void publishMessageSync() throws IOException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全有序的一个哈希表，适用于高并发的情况下
         *1、将序号和消息进行关联
         *2、批量删除条目，只要给到序号
         * 3、支持高并发
         */
        ConcurrentSkipListMap<Long,String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        //开始时间
        long begin = System.currentTimeMillis();

        /**
         * 消息监听器，监听哪些消息成功来，哪些消息失败了
         * 1、监听哪些消息成功了
         * 2、监听哪些消息失败了
         */

        channel.addConfirmListener(
        ((deliverTag,multiple)->{
            //2、删除确认的消息
            if(multiple){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap
                        = concurrentSkipListMap.headMap(deliverTag);
                longStringConcurrentNavigableMap.clear();
            }else {
                concurrentSkipListMap.remove(deliverTag);
            }

            System.out.println("确认的消息："+deliverTag);
        }),
        ((deliverTag,multiple)->{
            //3、打印未确认的消息
            String message = concurrentSkipListMap.get(deliverTag);
            System.out.println("未确认的消息："+message);
        }));
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i+"消息";
            channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
            //1、记录发布的消息
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),message);
        }

        System.out.println("耗时："+(System.currentTimeMillis()-begin));
    }
}
