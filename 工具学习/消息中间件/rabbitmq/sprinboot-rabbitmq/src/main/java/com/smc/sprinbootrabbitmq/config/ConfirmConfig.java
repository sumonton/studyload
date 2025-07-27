package com.smc.sprinbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description: 发布确认高级
 */
@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";

    //routingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";

    //备份备份交换机
    public static final String BACKUP_EXCHANGE_NAME="backup_exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";

    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";


    //声明交换机
    @Bean
    public DirectExchange confirmExchange() {
        //绑定备份交换机
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).
                withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    //声明备份交换机
    @Bean
    public FanoutExchange backUpExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    //声明备份队列
    @Bean
    public Queue backUpQueue() {

        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    //声明报警队列
    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBinding(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                @Qualifier("confirmQueue") Queue confirmQueue) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding warningBinding(@Qualifier("backUpExchange") FanoutExchange backupExchange,
                                @Qualifier("warningQueue") Queue warningQueue) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }


    @Bean
    public Binding backUpBinding(@Qualifier("backUpExchange") FanoutExchange backupExchange,
                                @Qualifier("backUpQueue") Queue backUpQueue) {
        return BindingBuilder.bind(backUpQueue).to(backupExchange);
    }
}
