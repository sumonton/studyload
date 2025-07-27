package com.smc.sprinbootrabbitmq.consumer;

import com.smc.sprinbootrabbitmq.config.DelayedExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:基于插件的延时消息
 */
@Slf4j
@Component
public class DelayConsumer {
 //监听消息
    @RabbitListener(queues = DelayedExchangeConfig.DELAYED_QUEUE_NAME)
    public void receiveDelay(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到延时队列的消息：{}",new Date(),msg);
    }

}
