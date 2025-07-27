package com.smc.sprinbootrabbitmq.consumer;

import com.smc.sprinbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:
 */
@Slf4j
@Component
public class CofirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message){
        log.info("当前时间：{}接收到消息:{}",new Date(),new String(message.getBody()));
    }

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMessage(Message message){
        log.info("当前时间：{}报警队列接收到消息:{}",new Date(),new String(message.getBody()));
    }
}
