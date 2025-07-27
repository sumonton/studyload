package com.smc.sprinbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:
 */
@Configuration
public class DelayedExchangeConfig {

    //准备交换机
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //队列
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    //routingkey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    //声明交换机
    @Bean
    public CustomExchange delayedExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        /**
         * 1、交换机名称
         * 2、交换机类型
         * 3、是否需要持久化
         * 4、是否需要自动删除
         * 5、其他的参数
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message",
                true, false, arguments);
    }

    //声明队列
    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();

    }

    //绑定队列
    @Bean
    public Binding delayedBinding(@Qualifier("delayedQueue") Queue delayedQueue,
                                  @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
