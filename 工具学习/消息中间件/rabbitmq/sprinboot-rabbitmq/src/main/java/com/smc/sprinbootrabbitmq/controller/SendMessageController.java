package com.smc.sprinbootrabbitmq.controller;

import com.smc.sprinbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{}，发送一条信息给TTL队列：{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s：" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s：" + message);
    }

    //发送的消息有ttl时间
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{}，发送一条时长{}信息给QC队列：{}", new Date().toString(), ttlTime, message);

        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            //发送的消息的时候，延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //发送的消息插件延迟
    @GetMapping("/sendDelayPluginMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer ttlTime) {
        log.info("当前时间：{}，发送一条时长{}信息给插件延时队列：{}", new Date(), ttlTime, message);

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey", message, msg -> {
            //发送的消息的时候，延迟时长
            msg.getMessageProperties().setDelay(ttlTime);
            return msg;
        });
    }

    //测试routingkey错误发布确认
    @GetMapping("/confirm/{message}")
    public void sendMessage(@PathVariable String message){
        CorrelationData correlationData1 = new CorrelationData("1");
        CorrelationData correlationData2 = new CorrelationData("2");
        log.info("当前时间：{}，发送一条信息给插件延时队列：{}", new Date(), message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message+"k10",correlationData1);
        log.info("当前时间：{}，发送一条信息给插件延时队列：{}", new Date(), message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+"aa",message+"可1",correlationData2);
    }
}
