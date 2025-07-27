package com.smc.sprinbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //注入
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机确认回调方法
     * 1、发消息 交换机接收到了 回调
     *  1.1 correlationData 保存回调消息的ID及相关信息
     *  1.2 交换机收到消息 ack =true
     *  1.4 cause null
     * 2、发消息 交换机接收失败了 回调
     *  1.1 correlationData 保存回调消息的Id及相关信息
     *  1.2 交换机收到的消息 ack=false
     *  1.3 cause 失败的原因
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack){
            log.info("交换机已经收到了ID为：{}的消息",correlationData.getId());
        }else {
            log.info("交换机还未收到了ID为：{}的消息，由于{}",correlationData.getId(),cause);
        }
    }

    /**
     * 在信息传递过程不可达目的队列地时返回消息给生产者
     * 只有不可达目的地的时候才进行回退
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息{},被交换机{}退回，原因：{}",returnedMessage.getMessage().getBody(),
                returnedMessage.getExchange(),returnedMessage.getReplyText());

    }
}
