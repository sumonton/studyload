package com.smc.sprinbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Date 2022/8/29
 * @Author smc
 * @Description: TTL队列 配置文件类代码
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机名称
    public static final String X_EXCHANGE = "X";
    //死信交换机名称
    public static final String Y_DEAD_EXCHANGE = "Y";
    //普通队列的名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";

    //死信队列的名称
    public static final String QUEUE_DEAD_D = "QD";

    //声明X交换机
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    //声明死信交换机Y
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_EXCHANGE);
    }

    //声明AB两个普通队列
    @Bean("queueA")
    public Queue queueA(){
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",10000);
        Queue queueA = QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
        return queueA;
    }
    // 声明队列 A 绑定 X 交换机
    @Bean
    public Binding queueaBindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    //声明AB两个普通队列
    @Bean("queueB")
    public Queue queueB(){
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",40000);
        Queue queueB = QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
        return queueB;
    }

    //声明队列 B 绑定 X 交换机
    @Bean
    public Binding queuebBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    @Bean("queueC")
    public Queue queueC(){
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        Queue queueC = QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
        return queueC;
    }
    //声明队列 C 绑定 X 交换机
    @Bean
    public Binding queuecCindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
    //死信队列
    @Bean("queueD")
    public Queue queueD(){
        Queue queueD = QueueBuilder.durable(QUEUE_DEAD_D).build();
        return queueD;
    }
    //声明死信队列 QD 绑定关系
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                        @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
