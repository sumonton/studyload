# 核心部分

## 一、消息队列

### 1、MQ的相关概念

#### 1.1什么是MQ

* MQ(MESSAGE QUEUE)，从字面意思上看，本质是个队列，FIFO先入先出，只不过队列中存放的内容是message而已，还是一种跨进程的通信机制，用于上下游传递消息。在互联网结构中，MQ是一种非常常见的上下游“逻辑解耦+物理解耦”的消息通信服务。使用了MQ之后，消息发送上游只需依赖MQ，不用依赖其他服务。

#### 1.2为什么使用MQ

* 流量消峰![image-20220827181321880](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210018454.png)
  * 在系统中，如果过量的访问量系统无法处理，只能限制用户不能访问。而使用消息队列做缓冲我们可以取消这个限制，把一秒的访问分散成一段时间来处理，虽然返回用户处理结果较慢，但是比限制访问的体验要好。
* 应用解耦
  * 以电商应用为例，应用中有订单系统，库存系统，物流系统，支付系统。用户创建订单后，如果耦合调用库存系统、物流系统、支付系统，任何一个子系统除了故障，都会造成下单操作异常。当转变成基本消息队列的方式，系统间调用的问题会减少很多，比如物流系统因为发生故障，需要几分钟来修复。在这几分钟的时间里，物流系统要处理的内存被缓存在消息队列中，用户的下单操作可以正常完成。当物流系统恢复后，继续处理订单信息即可，下单用户感受不到物流系统的故障，提升系统的可用性。
* 异步处理![image-20220827183401069](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210018010.png)
  * 有些服务器间调用是异步的，例如A调用B，B需要花费很长时间执行，但是A需要知道B什么时候可以执行完，以前一般有两种方式，A过一段时间去调用B的查询API查询。或者A提供一个callback api，B执行完之后调用api通知A服务。这两种方式都不是很优雅，使用消息总线，可以很方便解决这个问题，A调用B服务器后，只需监听B处理完成的消息，当B处理完成后，会发送一条消息给MQ，MQ会将此消息转发给A服务。这样A服务既不用循环调用B的查询api，也不用提供callback api。同样B服务也不用做这些操作。A服务还能及时的得到一步处理成功的消息。

#### 1.3 MQ的分类

* ActiveMQ
  * 优点：单机吞吐量万级，时效性ms级，可用性高，基于主从架构实现高可用性，消息可靠性较低的有概率丢失数据
  * 缺点：官方社区现在对ActiveMQ5.x维护越来越少，高吞吐量场景较少使用
* Kafka
  * 大数据的杀手锏，谈到大数据领域内的消息传输，则绕不开Kafka，这款为大数据而生的消息中间件
  * 优点：性能卓越，单机写入TPS约在百万条/秒，最大的优点，就是吞吐量。时效性ms级可用性非常高，kafka是分布式的，一个数据多个副本，少数机器宕机，不会丢失数据，不会导致不可用，消费者采用Pull方式获取消息，消息有序，通过控制能够保证所有消息被消费且仅被消费一次；有优秀的第三方Kafaka web管理界面kafka-manager；在日志领域比较成熟，被多家公司和多个开源项目使用；功能支持：功能较为简单，主要支持简单的MQ功能，在大数据领域的实时计算以及日志采集被大规模使用
  * 缺点：Kafka单机超过64个队列/分区，Load会发生明显的飙高现象，队列越多，load越高，发送消息响应时间变长，使用短轮询方式，实时性取决于轮训间隔时间，消息是不支持重试；支持消息顺序，但是一台代理宕机后，就会产生消息乱序，社区更新较慢
* RocketMQ
  * 阿里巴巴的开源产品，用Java语言实现
  * 优点：单机吞吐量十万级，可用性非常高，分布式架构，消息可以做到零丢失，MQ功能较为完善，还是分布式的，扩展性好，支持10亿级别的消息堆积，不会因为堆积导致性能下降，源码是java。
  * 缺点：支持的客户端语言不多，目前是java及c++，社区活跃度一般，没有在MQ核心中实现JMS等接口，有些系统要迁移需要修改大量代码。
* RabbitMQ
  * 2007年发布，是一个在AMQP（高级消息队列协议）基础上完成的，可复用的企业消息系统，是当前最主流的消息中间件之一
  * 优点：由于erlang语言的高并发特性，性能较好；吞吐量到万级，MQ功能比较完备，健壮、稳定、易用、跨平台、支持多语种语言 如python、ruby、.net、java、Jms、C、PHP、ActiveScript、XMPP、STOMP等，支持AJAX，文档齐全开源提供的管理界面，社区活跃度高，更新频率高<https://www.rabbitmq.com/news.html>
  * 缺点：商业版需要收费，学习成本较高

#### 1.4 MQ的选择

* Kafka
  * kafka主要特点是基于Pull的模式来处理消息消费，追求高吞吐量，一开始的目的就是用于日志收集和传输，适合产生大量数据的互联网服务的数据收集业务。大型公司建议可以选用高，如果有日志采集功能，肯定首选kafka。
* RocketMQ
  * 天生为金融互联网而生，对于可靠性要求很高的场景，尤其是电商里面的订单扣款，以及业务消峰，在大量交易涌入时，后端可能无法及时处理的情况。RocketMQ在稳定性上可能更值得信赖，这些业务场景在阿里双11已经经历多次考研，如果你的业务有上述并发场景，建议可以选择RocketMQ
* RabbitMQ
  * 结合erlang语言本身的并发优势，性能好时时效性微秒级，社区活跃度也比较高，管理界面用起来十分方便，如果你的数据量没有那么大，中小型公司优先选择功能比较完备RabbitMQ

### 2、RabbitMQ

#### 2.1 概念

* 它接收并转发消息。你可以把它当作一个快递站点，当你要发送一个包裹时，你把你的包裹放到快递站，快递员最终会把你的快递送到收件人哪里，按照这种逻辑RabbitMQ是一个快递站，一个快递员帮你传递快件。RabbitMQ与快递站的主要区别在于，它不处理快件而是接收，存储和转发消息数据。

#### 2.2 四大核心概念

![image-20220827194306250](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210019605.png)

* 生产者：
  * 产生数据发送消息
* 交换机（可多个交换机）
  * 交换机是RabbitMQ非常重要的一个部件，一方面它接收来自生产者的消息，另一方面它将消息推送到队列中。交换机必须确切知道如何处理它接收的消息，是将这些消息推送到特定队列还是推送到多个队列，亦或是把消息丢弃，这个得由交换机类型决定
* 队列（多个队列）
  * 队列是RabbitMQ内部使用的一种数据结构，尽管消息流经RabbitMQ和应用程序，但他们只能存储在队列中。队列仅受主机的内存和磁盘限制的约束，本质上是一个大的消息缓冲区。许多生产者可以将消息发送到一个队列，许多消费者可以尝试从一个队列接收数据。
* 消费者
  * 消费者大多数的时候是一个等待接收消息的程序。情追忆生产者，消费者和消息中间件很多时候并不在一台机器上。同一个应用程序即可以是生产者也可以是消费者

#### 2.3 RabbitMQ核心部分

![image-20220827213047294](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210019564.png)



* 简单模式（hello world）
* 工作模式（work queues）
* 发布订阅模式（Publish/subscribe）
* 路由模式（Routing）
* 主题模式（Topics）
* RPC
* 发布确认模式（Publisher Confirms）

#### 2.4 各个名次介绍

![image-20220827195331729](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210020979.png)

#### 2.5 安装

* 官网地址

  * <https://www.rabbitmq.com/download.html>

* 文件位置

  ![image-20220827204342047](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210020281.png)

* 安装文件（分别按照以下顺序安装）

  * rpm -ivh erlang
  * rpm install socat -y
  * rpm -ivh rabbitmq

* 常用命令

  * 添加开机启动rabbitMQ服务器

    ```shell
    [root@localhost opt]# chkconfig rabbitmq-server on
    注意：正在将请求转发到“systemctl enable rabbitmq-server.service”。
    Created symlink from /etc/systemd/system/multi-user.target.wants/rabbitmq-server.service to /usr/lib/systemd/system/rabbitmq-server.service.
    
    ```

  * 启动服务

    ```shell
    [root@localhost opt]# systemctl start rabbitmq-server
    #查看状态
    [root@localhost opt]# systemctl status rabbitmq-server
    #停止服务
    [root@localhost opt]# systemctl stop rabbitmq-server
    
    
    ```

* 安装web界面插件

  ```shell
  [root@localhost opt]# rabbitmq-plugins enable rabbitmq_management
  
  ```

  * 访问地址：<http://192.168.1.109:15672/>

* 添加用户

  ```shell
  #创建用户
  [root@localhost opt]# rabbitmqctl add_user admin 123
  
  #设置用户角色
  [root@localhost opt]# rabbitmqctl set_user_tags admin administrator
  
  #设置用户权限
  set permissions [-p <vhostpath>] <user> <conf> <write> <read>
  [root@localhost opt]# rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"
  #用户user_admin 具有/vhost1 这个virtual host中所有资源的配置，写，读权限 
  
  #当前用户和角色
  [root@localhost opt]# rabbitmqctl list_users
  
  ```

## 二、Hello World

* 在本教程的一部分中，我们将用Java编写两个程序，发送耽搁消息的生产者和接收消息并打印出来的消费者。我们将介绍Java API的一些细节

* 在下图中“p”是我们的生产者，“c”是我们的消费者。中间的框是一个队列-RabbitMQ待表使用者保留的消息缓冲区

  ![image-20220827213353983](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210021693.png)

### 1、java依赖

```xml
<dependencies>
  <!--rabbitmq依赖客户端-->
  <dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>5.15.0</version>
  </dependency>
  <!--操作文件流的一个依赖-->
  <dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.11.0</version>
  </dependency>

</dependencies>
```

### 2、消息生产者

```java
//创建一个连接工厂
ConnectionFactory factory = new ConnectionFactory();
//工厂IP，连接RabbitMQ的队列
factory.setHost("192.168.1.109");
//设置用户名
factory.setUsername("admin");
//密码
factory.setPassword("123");
//创建连接
Connection connection = factory.newConnection();
//获取信道
Channel channel = connection.createChannel();
/**
* 生成一个队列
* 1、队列名称
* 2、队列里面的消息是否持久化（磁盘），默认情况消息存储在内存中
* 3、该队列是否只供一个消费者进行消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费
* 4、是否自动删除，最后一个消费者断开连接以后，该队列是否自动删除 true自动删除，false不自动删除
* 5、其他参数
*/
channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//发送的消息
String msg = "hello world";
/**
* 使用channel发送消息
* 1、发送到哪个交换机
* 2、路由的key值是哪个，本地的队列是名称
* 3、其他参数信息
* 4、发送消息是消息体
*/
channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

System.out.println("发送完毕");
```

### 3、消费者代码

```java
package com.smc.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/27
 * @Author smc
 * @Description:
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello world";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂和连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.109");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();


        //由链接创建信道
        Channel channel = connection.createChannel();

        //声明接收消息
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };
        //取消消息时回调
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("消费消息被中断");
        };
        /**
         * 消费者从信道接收信息
         * 1、消费哪个队列
         * 2、消费成功之后是否要自动应答 true代表自动应到，false代表手动应答
         * 3、消费者未成功消费的回调
         * 4、消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }

}

```

## 三、Work Queues

* 工作队列（又称任务队列）的主要思想是避免立即执行资源密集型任务，而不得不等待它完成。相反我们安排任务在之后执行。我们把任务封装为消息并将其发送到队列。在后台运行的工作进程将接收任务并最终执行作业。当有多个工作线程时，这些工作线程将一起处理任务。![image-20220828001026744](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210021855.png)

### 1、轮询分发消息

* 在这个案例中我们会启动两个工作线程，一个消息发送线程，我们来看看他们的两个工作线程是如何工作的。

#### 1.1 抽取工具类

```java
package com.smc.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:连接工厂创建信道的工具类
 */
public class RabbitMqUtils {
    //得到一个连接的channel
    public static Channel getChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.108");
        factory.setUsername("admin");
        factory.setPassword("123");
        Channel channel = null;
        try {
            channel = factory.newConnection().createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

}
```

#### 1.2 工作线程代码

```java
package com.smc.rabbitmq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Worker01 {
    //队列的名称
    public static final String QUEUE_NAME="hello wolrd";

    public static void main(String[] args) throws IOException {
        //接收消息
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收到的消息："+new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+"消息者取消消费接口回调逻辑");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }


}
```

#### 1.3启动第二个线程

* 使用idea并行执行Worker01

#### 1.4 工作队列

```java
package com.smc.rabbitmq;

import com.rabbitmq.client.Channel;
import com.smc.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Date 2022/8/28
 * @Author smc
 * @Description:
 */
public class Task01 {
    public static final String QUEUE_NAME="hello world";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台当中发送信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送信息完成："+message);
        }
    }
}

```

### 2、消息应答

#### 2.1 概念

* 消费者完成一个任务可能需要一段时间，如果其中一个消费者处理一个长的任务并仅只完成了部分它突然挂掉了，会发生什么情况。RabbitMQ一旦向消费者传递了一条消息，便立即将该消息标记为删除。在这种情况下，突然有个消费者挂掉了，我们将丢失正在处理的消息，以及后续发送给该消费者的消息，因为这些消息无法被消费者接收到。
* 为了保证消息在发送过程中不丢失，rabbitmq引入了消息应答机制，消息应答就是：消费者在接收到消息并且处理该消息之后，告诉rabbitma它已经处理，rabbitmq可以把该消息删除

#### 2.2 自动应答

* 消息发送后立即被认为已经传送成功，这种模式需要在高吞吐量和数据传输安全性方面做权衡，因为**这种模式如果消息在接收到之前，消费者那边出现连接或者channel关闭，那么消息就丢失了**，当然另一方面这种模式消费者那边可以传递过载的消息，没有对传递的数量进行限制，当然这样有可能使得消费者这边由于接收太多还来不及处理的消息，导致这些信息的积压，最终使得内存耗尽，最终这些消费者线程被操作系统杀死，所以这种模式仅适用在消费者可以高效并以某种速率能够处理这些消息的情况下使用。

#### 2.3 消息应答方法

* `Channel.basicAck`（用于肯定确认）
  * RabbitMQ已知道该消息并且成功的处理消息，可以将其丢弃了
* `Channel.basicNack`（用于否定确认）
* `Channel.basicReject`（用于否定确定）
  * 与Channel.basicNack相比少了一个参数
  * 不处理该消息了职级拒绝，可以将其直接丢弃

#### 2.4 Multiple的解释（批量）

* 手动应答的好处是可以批量应答并且减少网络拥堵
* multiple的true和false代表不同意思
  * true：代表批量应答channel上未应答的消息
  * false：只会应答当前消息

#### 2.5消息自动重新入队

* 如果消费者由于某些原因失去连接（其通道已关闭，连接已关闭或TCP连接丢失），导致消息未发送ACK确认，RabbitMQ将了解到消息未完全处理，并将对其重新排队。如果此时其他消费者可以处理，它将很快将其发送给另一个消费者。这样即使某一个消费者偶尔死亡，也可以确保不会丢失任何信息。

#### 2.6 消息手动应答代码

* 默认消息应答是自动应答，所以我们要实现消息消费过程不丢失，需要把自动应答改为手动应答

* 生产者

  ```java
  package com.smc.rabbitmq2;
  
  import com.rabbitmq.client.Channel;
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
          //声明队列
          channel.queueDeclare(TASK_QUEUE_NAME,false,false,false,null);
          //在控制台输入信息
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNext()){
              String msg = scanner.next();
              channel.basicPublish("",TASK_QUEUE_NAME,null,msg.getBytes(StandardCharsets.UTF_8));
              System.out.println("生产这发出消息："+msg);
          }
  
      }
  }
  
  ```

* 消费者

  ```java
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
  public class Worker02 {
      public static final String TASK_QUEUE_NAME="ack_queue";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          System.out.println("C1等待消息处理时间较短");
          DeliverCallback deliverCallback = (consumerTag,message)->{
              SleepUtils.sleep(1);
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
          //采用手动应答
          channel.basicConsume(TASK_QUEUE_NAME,false,deliverCallback,(consumerTag->{
              System.out.println(consumerTag+"消费者取消消费接口回调");
          }));
      }
  
  }
  
  ```

  ```java
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
          System.out.println("C1等待消息处理时间较短");
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
          //采用手动应答
          channel.basicConsume(TASK_QUEUE_NAME,false,deliverCallback,(consumerTag->{
              System.out.println(consumerTag+"消费者取消消费接口回调");
          }));
      }
  
  }
  
  ```

### 3、RabbitMQ持久化

#### 3.1 概念

* 刚刚我们已经看到了如何处理任务不丢失的情况，但是如何保障当RabbitMQ服务停掉以后消息生产者发送过来的消息不丢失。默认情况下RabbitMQ推出或由于某种原因崩溃时，它忽视队列和消息，除非告知它不要这样做。确保消息不会丢失需要做两件事：**我们需要将队列和消息都标记为持久化**。

#### 3.2 队列如何实现持久化

* 在生产者声明队列时将第二个参数改为true即实现队列的持久化![image-20220828142301715](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210022097.png)
* 会出错，需要将先该命名的非持久化队列删除

![image-20220828142401636](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302227850.png)

* 持久化成功后ui界面![image-20220828142455736](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302227803.png)

#### 3.3消息持久化

* 想要让消息实现持久化，需要在消息生产者将basicPublish的第三个参数改为MessageProperties.PERSISTENT_TEXT_PLAIN![image-20220828143023546](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228049.png)
* 将消息标记为持久化并不能完全保证不会丢失消息。尽管它告诉RabbitMQ将消息保存到磁盘，但是这里依然存在当消息刚准备存储在磁盘的时候，但是还没有存储完，消息还在缓存的一个间隔点。此时并没有真正的写入磁盘。持久化保证并不强，但是对于我们的简单任务队列而言，已经绰绰有余。如果需要更强有力的持久化策略需要发布确认策略

#### 3.4 不公平分发

* 在最开始的时候我们学习到的RabbitMQ分发消息采用的轮询分发，但是在某种场景下这种策略并不是很好，比方说有两个消费者在处理任务，其中有个消费者1处理任务的速度非常快，消费者2的处理速度非常慢，**这样会使在消费者2后的消息处于长时间等待状态**，这种分配方式在这种情况下其实就不太好，但是RabbitMQ并不知道这种情况依然在公平分发。
* 为了避免这种情况，我们可以在**消费方设置参数**`channel.basicQos(1);`,需要参与这个方式的消费者都需哟啊设置![image-20220828145458555](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228114.png)
* ui显示![image-20220828145639456](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228880.png)

#### 3.5 预取值

* 本身消息的发送就是一步发送的，所以在任何时候，channel上肯定不止只有一个消息，另外来自消费者的手动确认本质上也是一步的。因此这里就存在一个未确认的消息缓冲区，因此希望开发人员能限制此缓冲区的大小，以避免缓冲区里面无限制的未确认消息问题。这个时候就可以通过使用basic.qos方式设置“预取计数”值来完成的。该值定义通道上允许的未确认消息的最大数量。一旦数量达到配置的数量，RabbitMQ将停止在通道上传递更多消息，除非至少有一个未处理的消息被确认，例如，假设在通道上有未确认的消息5、6、7、8，并且通道的预取计数设置为4，此时RabbitMQ将不会在该通道上再传递任何消息，除非至少有一个未应答的消息被ack，比方说tag=6这个消息刚刚被确认ACK，RabbitMQ将会感知这个情况并再发送一条消息。消息应答和Qos预取值队用户吞吐量有重大影响。
* 通常与预取将提高向消费者传递消息的速度。虽然自动应答传输速率是最佳的，但是，在这种情况下已传递但尚未处理的消息的数量也会增加，从而增加了消费者的RAM消耗，应该小心使用具有无限预处理的自动确认模式或手动确认模式，消费者消费了大量的消息如果没有确认的话，会导致消费者连接节点的内存消耗变大，所以找到一个合适的预取值时一个反复试验的过程，不同的负载该取值也不同，**100到300范围内的值通常可提供最佳的吞吐量**，并且不会给消费者带来太大的风险。预取值1是最保守的。当然这将使吞吐量变得很低，特别是消费者连接延迟严重的情况下，特别的是在消费者连接等待时间较长的环境中。对于大多数应用来说，稍微高一点的值将是最佳的。

![image-20220828151553759](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228379.png)![image-20220828151608286](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302234067.png)![image-20220828152148031](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228765.png)

## 四、发布确认原理

### 1、发布确认原理

* 生产者将信道设置成confirm模式，一旦信道进入confirm模式，所有在该信道上面发布的消息都将会被指派一个唯一的ID（从1开始），一旦消息被投递到所有匹配的队列后，broker就会发送一个确认给生产者（包含消息的唯一ID），这就使得生产者知道消息已经正确到达目的队列了，如果消息和队列是可持久化的，那么确认消息会在将消息写入磁盘之后发出，borker会传给生产者的确认消息中delivery-tag域包含了确认消息的序列号，此外broker也可以设置basic.ack的multiple域，表示这个序列号之前的所有消息都已经得到了处理
* confirm模式最大的好处在于他是异步的，一旦发布一条消息，生产者应用程序就可以在等待信道返回确认的同时继续发送吓一跳消息，当消息最终得到最热之后，生产者应用便可以通过回调方法来确认消息，如果RabbitMQ因为自身内部错误导致信息丢失，就会发送一条nack消息，生产者应用程序同样可以在回调方法中处理该nack消息

### 2、发布确认的策略

#### 2.1 开启发布确认的方法

* 发布确认默认是没有开启的，如果要开启信道需要调用方法confirmSelect![image-20220828154403411](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302228616.png)

#### 2.2 单个确认发布

* 这是一种简单的确认方式，它是一种同步确认发布的方式，也就是发布一个消息之后只有它被确认发布，后续的消息才能继续发布，wai tForConfirmsOrDie(long)这个方法只有在消息被确认的时候才会返回，如果在指定时间范围内这个消息没有被确认那么它将抛出异常
* 这种确认方式有一个最大的缺点就是：发布速度特别的慢，因为如果没有确认发布的消息就会阻塞所有的后续发布，这种方式最多提供美妙不超过数百条发布消息的吞吐量。当然对于某些应用程序是足够的了。

```java
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
    if (flag){
      System.out.println("消息发送成功");
    }

  }

  System.out.println("耗时："+(System.currentTimeMillis()-begin));

}
```

#### 2.3 批量发布确认

* 与单个等待确认消息相比，先发布一批消息然后一起确认可以极大地提高吞吐量，当然这种方式的缺点就是：当发生故障时，不知道是哪个消息出现了问题，我们必须将整个批处理保存在内存中，以记录重要的信息而后重新发布消息。当然这种方案仍然是同步的，也一样阻塞消息的发布

```java
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
```

#### 2.4 异步确认发布

* 异步确认虽然编程逻辑比上两个复杂，但是性价比最高，无论是可靠性还是效率都没得说，他是零用回调函数来达到消息可靠性传递的，这个中间件也是通过函数回调来保证是否投递成功。

```java
//异步发布确认
public static void publishMessageSync() throws IOException, InterruptedException {
  Channel channel = RabbitMqUtils.getChannel();
  //队列的声明
  String queueName = UUID.randomUUID().toString();
  channel.queueDeclare(queueName,true,false,false,null);
  //开启发布确认
  channel.confirmSelect();
  //开始时间
  long begin = System.currentTimeMillis();

  /**
         * 消息监听器，监听哪些消息成功来，哪些消息失败了
         * 1、监听哪些消息成功了
         * 2、监听哪些消息失败了
         */

  channel.addConfirmListener(
    ((deliverTag,multiple)->{
      System.out.println("确认的消息："+deliverTag);
    }),
    ((deliverTag,multiple)->{
      System.out.println("未确认的消息："+deliverTag);
    }));
  //批量发消息
  for (int i = 0; i < MESSAGE_COUNT; i++) {
    String message = i+"";
    channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));

  }

  System.out.println("耗时："+(System.currentTimeMillis()-begin));
}
```

#### 2.5 如何处理异步未确认消息

* 最好的解决方案就是把未确认的消息放到一个基于内存的能被发布线程访问的队列，比如说ConcurrentListkedQueue，这个队列在confirm callbacks与发布线程之间进行消息传递

```java
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
```

## 五、交换机

* 在上一节中，我们创建了一个工作队列。我们假设的是工作队列背后，每个任务都恰好交付给一个消费者。在这一部分中，我们将做一些消息传达给多个消费者。这种模式成为“发布/订阅”
* 为了说明这种模式，我们将构建一个简单的日志系统。它将由两个程序组成：第一个程序将发出日志消息，第二个程序是消费者。其中我们会启动过两个消费者。其中一个消费者接收到消息之后把日志存储在磁盘，另一个消费者接收到消息之后把日志打印在屏幕，事实上一个程序发出的日志消息广播给了两个消费者

![image-20220828171128552](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302234187.png)

### 1、Exchanges

#### 1.1 概念

* RabbitMQ消息传递模式的核心思想：生产者生产的消息从不会直接发送到队列。实际上，通常生产者甚至都不知道这些消息传递到了哪些队列中
* 相反，生产者只能将消息传递给交换机（Exchanges），交换机工作的内容非常简单，一方面它接收来自生产者的消息，另一方面将他们推入队列。交换机必须确切的知道如何处理收到的消息。是应该把这些消息放到特定的队列还是放到许多个队列，还是说丢弃它们。这就由交换机来决定

#### 1.2 类型

* 直接类型（direct），主题（topic），标题（headers），扇出（fanout）

#### 1.3 无名exchange

* 在前面我们能够将消息发送到队列是因为我们使用了默认交换，我们通过空字符串（“”）来标识![image-20220828171809116](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302235638.png)
* 第一个参数是交换机的名称，空字符串表示默认或无名交换机：消息能路由发送到队列中其实是由routing(bindingkey)绑定key指定的，如古偶它存在的话

### 2、临时队列

* 每当我们连接到rabbit时，我们都需要一个全新的空队列，为此我们可以创建一个具有随机名称的队列，或者让服务器为我们选择一个随机队列名称就更好了。其实一旦我们断开了消费者的连接，队列将被自动删除。

### 3、绑定（bindings）

* 是exchange和queue之间的桥梁，它告诉我们exchange和哪个队列进行绑定

### 4、Fanout（扇出、发布订阅模式）

#### 4.1 Fanout介绍

* Fanout这种类型非常简单。正如名称中猜到的那样，它是将接收到的所有消息广播到它知道的所有队列中。系统中有默认类型的exchange![image-20220828200236403](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302235525.png)

#### 4.2 Fanout实战

* 消费者1

  ```java
  package com.smc.pc;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class Receive01 {
      public static final String EXCHANGE_NAME = "logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          //声明一个交换机
          channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
          /**
           * 声明1个队列，临时队列
           *
           * 生成一个临时队列，队列名称是随机的
           * 当消费者断开与队列的连接时候，队列就自动删除
           */
          String queue = channel.queueDeclare().getQueue();
          /**
           * 绑定交换机和队列
           */
          channel.queueBind(queue, EXCHANGE_NAME, "");
          System.out.println("等待接收消息，把接收到消息打到屏幕上。。。。。。");
  
  
          //接收消息
          channel.basicConsume(queue, true,
                  ((consumeTag, message) -> {
                      System.out.println("Receive01控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("aaa");
                  }));
  
  
      }
  }
  
  ```

* 消费者2

  ```java
  package com.smc.pc;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class Receive02 {
      public static final String EXCHANGE_NAME ="logs";
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          //声明一个交换机
          channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
          /**
           * 声明1个队列，临时队列
           *
           * 生成一个临时队列，队列名称是随机的
           * 当消费者断开与队列的连接时候，队列就自动删除
           */
          String queue = channel.queueDeclare().getQueue();
          /**
           * 绑定交换机和队列
           */
          channel.queueBind(queue,EXCHANGE_NAME,"");
          System.out.println("等待接收消息，把接收到消息打到屏幕上。。。。。。");
  
  
          //接收消息
          channel.basicConsume(queue,true,
                  ((consumeTag,message)->{
                      System.out.println("Receive02控制台打印接收到的消息："+new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag->{
                      System.out.println("bbb");
                  }));
  
  
  
      }
  }
  
  ```

* 生产者

  ```java
  package com.smc.pc;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  import java.util.Scanner;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   * 发消息给交换机
   */
  public class EmitLog {
      public static final String EXCHANGE_NAME = "logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNext()){
              String msg = scanner.next();
              channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes(StandardCharsets.UTF_8));
              System.out.println("生产者发送消息："+msg);
          }
  
  
      }
  
  }
  
  ```

### 5、Direct exchange

#### 5.1 回顾

* 在上一节中，我们构建了一个简单的日志记录系统。我们能够向许多接收者广播日志消息。在本届我们将向其中添加一些特别的功能-比方说我们只让某个消费者订阅发布的部分消息。例如我们只把严重错误的信息定向存储到日志文件，同时仍然能够在控制台上打印所有日志消息
* 我们再次来回顾下什么是bindings，绑定是交换机和队列之间的桥梁关系。也可以这么理解：队列只对它绑定的交换机的消息感兴趣。绑定用参数：routingKey来表示也可称参数为binding key，常见绑定我们用代码：channel.queueBind(queueName,EXCHANGE_NAME,"routingKey");绑定之后的意义由其交换类型决定

#### 5.2 Direct Exchange介绍

* 上一节中的我们的日志系统将所有消息广播给所有消费者，对此我们想做一些改变，例如我们希望将日志信息写入磁盘的程序仅接收严重错误（error），而不存储哪些警告或细腻下的日志，避免空间浪费。Fanout这种交换类型并不能给我们带来很大的灵活性-它只能进行无意识的广播，在这里我们将使用direct这种类型来进行替换，这种类型的工作方式消息只去它绑定的routingKey中去。

![image-20220828204921937](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302236786.png)

* 在上面这种绑定情况下，生产者发布消息到exchange上，绑定键为orange的消息会被发布到队列Q1，绑定键为black、green的消息会被发布到Q2，其他绑定键会被丢弃

#### 5.3 多种绑定

* 当然如果exchange的绑定类型是direct，但是它绑定的多个队列的key如果相同，在这种情况下虽然绑定类型是direct，但是它的表现的酒喝fanout优点类似来，就跟广播差不多

#### 5.4 实战

* console消费者

  ```java
  package com.smc.direct;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class ReceiveLogsDirect01 {
      public static final String EXCHANGE_NAME = "direct_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.queueDeclare("console", true, false, false, null);
          channel.queueBind("console", EXCHANGE_NAME, "info");
          channel.queueBind("console", EXCHANGE_NAME, "warning");
          channel.basicConsume("console", true,
                  ((consumeTag, message) -> {
                      System.out.println("ReceiveDirect01控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("aaa");
                  }));
      }
  }
  
  ```

* disk消费者

  ```java
  package com.smc.direct;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class ReceiveLogsDirect02 {
      public static final String EXCHANGE_NAME = "direct_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.queueDeclare("disk", true, false, false, null);
          channel.queueBind("disk", EXCHANGE_NAME, "error");
          channel.basicConsume("disk", true,
                  ((consumeTag, message) -> {
                      System.out.println("ReceiveDirect02控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("bbb");
                  }));
      }
  }
  
  ```

* 生产者

  ```java
  package com.smc.direct;
  
  import com.rabbitmq.client.BuiltinExchangeType;
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  import java.util.Scanner;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   * 发消息给交换机
   */
  public class DirectLog {
      public static final String EXCHANGE_NAME = "direct_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNext()){
              String msg = scanner.next();
              //输入逗号前面为key，后面为消息
              String[] message=msg.split(",");
              channel.basicPublish(EXCHANGE_NAME,message[0],null,message[1].getBytes(StandardCharsets.UTF_8));
              System.out.println("生产者发送消息："+msg);
          }
  
      }
  
  }
  
  ```

### 6、Topics

#### 6.1 之前类型的问题

* 在上一个小结中，我们改进了日志记录系统。我们没有使用只能进行随意广播的fanout交换机，而是使用了direct交换机，从而有能实现选择性地接收日志
* 尽管使用direct交换机改进了我们的系统，但是它仍然存在局限性-比方说我们想接收的日志类型有info.base和info.advantage，某个对队列只有info.base的消息，那这个时候direct就办不到了。这个时候就只能使用topic类型。

#### 6.2 Topic的要求

* 发送的类型是topic交换机的消息的routing_key不能随意写，必须满足一定的要求，它必须是一个单词列表，以点号隔开。这些单词可以是任意单词，比如说：”stock.usd.nyse“这种类型的。当然单词列表最多不能超过255个字节
* 在这个规则列表中，其中有两个替换符
  * *可以代替一个单词
  * #可以代替零个活多个单词
* 注意事项
  * 当一个队列绑定键是#，那么这个队列将接收所有数据，优点像fanout了
  * 如果队列绑定键当中没有#和*出现，那么该队列绑定类型就是direct了

#### 6.3 实战

* 消费者1

  ```java
  package com.smc.topic;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class ReceiveLogsTopic01 {
      public static final String EXCHANGE_NAME = "topic_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.queueDeclare("console", true, false, false, null);
          channel.queueBind("console", EXCHANGE_NAME, "*.*.info");
          channel.queueBind("console", EXCHANGE_NAME, "warning.#");
          channel.basicConsume("console", true,
                  ((consumeTag, message) -> {
                      System.out.println("ReceiveDirect01控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("aaa");
                  }));
      }
  }
  
  ```

* 消费者2

  ```java
  package com.smc.topic;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class ReceiveLogsTopic02 {
      public static final String EXCHANGE_NAME = "topic_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.queueDeclare("disk", true, false, false, null);
          channel.queueBind("disk", EXCHANGE_NAME, "*.error.*");
          channel.basicConsume("disk", true,
                  ((consumeTag, message) -> {
                      System.out.println("ReceiveDirect02控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("bbb");
                  }));
      }
  }
  
  ```

* 生产者

  ```java
  package com.smc.topic;
  
  import com.rabbitmq.client.BuiltinExchangeType;
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  import java.util.Scanner;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   * 发消息给交换机
   */
  public class TopicLog {
      public static final String EXCHANGE_NAME = "topic_logs";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
          Scanner scanner = new Scanner(System.in);
          while (scanner.hasNext()){
              String msg = scanner.next();
              //输入逗号前面为key，后面为消息
            	/**
            	* aa.error.info,aa.error.info,aa.error.inb,waring.error.aa,warning.aerror.aa
            	*/
              String[] message=msg.split(",");
              channel.basicPublish(EXCHANGE_NAME,message[0],null,message[1].getBytes(StandardCharsets.UTF_8));
              System.out.println("生产者发送消息："+msg);
          }
  
      }
  
  }
  ```

## 六、死信队列

### 1、概念

* 先从概念上搞清楚这个定义，死信，顾名思义就是无法被消费的消息，字面意思可以这样理解，一般来说，producer将信息投递到broker或者直接到queue里里，consumer从queue取出消息进行消费，但某些时候由于特定的原因导致queue中的某些消息无法被消费，这样的消息如果没有后续的处理，就会变成死信，有死信自然就有了死信队列
* 应用场景：为了保证订单业务的消息数据不丢失，需要使用到RabbitMQ的死信队列机制，当消息消费发生异常时，将消息投入死信队列中。还有比如说：用户在商城下单成功并点击去支付后在指定时间未支付自动失效

### 2、死信的来源

* 消息TTL过期
* 队列达到最多长度（队列满了，无法在添加数据到mq中）
* 消息被拒绝（basic.reject或basic.nack）并且requeue=false

### 3、死信实战

#### 3.1 代码结构图

![image-20220828221426318](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302235874.png)



#### 3.2 实战（过期时间）

* 普通消费者

  ```java
  package com.smc.dead;
  
  import com.rabbitmq.client.BuiltinExchangeType;
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  import java.util.HashMap;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class Cosumer01 {
  
      //普通交换机
      public static final String NORMAL_EXCHANGE= "normal_exchange";
      //普通队列
      public static final String NORMAL_QUEUE= "normal_queue";
      //死信交换机
      public static final String DEAD_EXCHANGE= "dead_exchange";
      //死信队列
      public static final String DEAD_QUEUE= "dead_queue";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          //转发死信交换机
          HashMap<String, Object> arguments = new HashMap<>();
          //过期时间参数
  //        arguments.put("x-message-ttl",10000);
          //正常队列转发死信交换机
          arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
          //设置死信routinKey
          arguments.put("x-dead-letter-routing-key","lisi");
          channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
  
          channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
  
  
          channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
  
          channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
  
          System.out.println("等待接收消息");
          channel.basicConsume(NORMAL_QUEUE, true,
                  ((consumeTag, message) -> {
                      System.out.println("NORMAL控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("aaa");
                  }));
  
  
      }
  }
  
  ```

* 死信消费者

  ```java
  package com.smc.dead;
  
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   */
  public class Cosumer02 {
  
  
      //死信交换机
      public static final String DEAD_EXCHANGE= "dead_exchange";
      //死信队列
      public static final String DEAD_QUEUE= "dead_queue";
  
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
  
          channel.queueDeclare(DEAD_QUEUE,false,false,false,null);
          channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
  
          channel.basicConsume(DEAD_QUEUE, true,
                  ((consumeTag, message) -> {
                      System.out.println("Dead控制台打印接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
                  }),
                  (consumeTag -> {
                      System.out.println("bbb");
                  }));
  
      }
  }
  
  ```

* 生产者

  ```java
  package com.smc.dead;
  
  import com.rabbitmq.client.AMQP;
  import com.rabbitmq.client.BuiltinExchangeType;
  import com.rabbitmq.client.Channel;
  import com.smc.utils.RabbitMqUtils;
  
  import java.io.IOException;
  import java.nio.charset.StandardCharsets;
  import java.util.Scanner;
  
  /**
   * @Date 2022/8/28
   * @Author smc
   * @Description:
   * 死信队列生产者代码
   */
  public class DeadProducer {
      public static final String NORMAL_EXCHANGE= "normal_exchange";
      //死信交换机
      public static final String DEAD_EXCHANGE= "dead_exchange";
      public static void main(String[] args) throws IOException {
          Channel channel = RabbitMqUtils.getChannel();
          //声明普通交换机
          channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
          //声明死信交换机
          channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
          //死信消息，设置TTL
          AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
          for (int i = 0; i < 10; i++) {
              String message = "info"+i;
              channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",properties,message.getBytes(StandardCharsets.UTF_8));
  
          }
  //        Scanner scanner = new Scanner(System.in);
  //        while (scanner.hasNext()){
  //            String msg = scanner.next();
  //            //输入逗号前面为key，后面为消息
  //            String[] message=msg.split(",");
  //            channel.basicPublish(NORMAL_EXCHANGE,message[0],null,message[1].getBytes(StandardCharsets.UTF_8));
  //            System.out.println("生产者发送消息："+msg);
  //        }
  
      }
  
  }
  
  ```

#### 3.3 队列达到最大长度

![image-20220828225824464](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302235484.png)

#### 3.4 死信实战（消息被拒）

![image-20220828231044413](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302235588.png)

## 七、延迟队列

### 1、延迟队列的概念

* 延时队列，队列内部时有序的，最重要的特性就体现在它的延时属性上，延时队列中的元素是希望在指定时间到了以后或之前取出和处理，简单来说，延时队列就是用来存放需要在指定时间被处理的元素的队列。

### 2、延迟队列使用场景

* 使用场景

  * 订单在十分钟之内未支付则自动取消

  * 新创建的店铺，如果在十天内没有上传过商品，则自动发送消息提醒

  * 用户注册成功后，如果三天内没有得到处理则通知相关运营人员

  * 用户发起退款，如果三天内没有得到处理则通知相关运营人员

  * 预定会议后，需要在预定的时间点前十分钟通知各个与会人员参加会议

* 这些场景都有一个特点，需要在某个事件发生之后或者之前的指定时间点完成某一项任务，如：发生订单生成事件，在十分钟之后检查该订单支付状态，然后将未支付的订单进行关闭；看起来私护使用定时任务，一致轮询数据，每秒查一次，去除需要被处理的数据，然后处理不就完事了吗？如果数据量比较少，确实可以这样做，比如：对于“如果账单一周未支付则进行自动结算”这样的需求，如果对于事件不是严格限制，而是宽松意义上的一周，那么每天晚上跑个定时任务检查一下所有未支付订单也是一个可行的方案。但对于数据量比较大，并且时效性较强的场景，如：“订单十分钟未支付则关闭”，短期内未支付的订单数据可能会非常多，活动期间甚至会达到百万设置千万级别，对这么庞大的数据量仍旧使用轮询的方式显然是不可取的，很可能在一秒内无法完成所有订单的检查某同事会给数据库带来很大压力，无法满足业务要求而且性能低下。

### 3、RabbitMQ中的TTL

* TTL 是什么呢？TTL 是 RabbitMQ 中一个消息或者队列的属性，表明一条消息或者该队列中的所有消息的最大存活时间，单位是毫秒。换句话说，如果一条消息设置了 TTL 属性或者进入了设置 TTL 属性的队列，那么这条消息如果在 TTL 设置的时间内没有被消费，则会成为"死信"。如果同时配置了队列的 TTL 和消息的TTL，那么较小的那个值将会被使用，有两种方式设置 TTL。

#### 3.1 队列设置TTL

* 第一种是在创建队列的时候设置队列的“x-message-ttl”属性![image-20220829234319038](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302236386.png)

#### 3.2 消息设置TTL

* 针对每天消息设置TTL![image-20220829234349411](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302236291.png)

#### 3.3 两者的区别

* 如果设置了队列的 TTL 属性，那么一旦消息过期，就会被队列丢弃(如果配置了死信队列被丢到死信队列中)，而第二种方式，消息即使过期，也不一定会被马上丢弃，因为**消息是否过期是在即将投递到消费者****之前判定的**，如果当前队列有严重的消息积压情况，则已过期的消息也许还能存活较长时间；另外，还需要注意的一点是，如果不设置 TTL，表示消息永远不会过期，如果将 TTL 设置为 0，则表示除非此时可以直接投递该消息到消费者，否则该消息将会被丢弃。
* 前一小节我们介绍了死信队列，刚刚又介绍了 TTL，至此利用 RabbitMQ 实现延时队列的两大要素已经集齐，接下来只需要将它们进行融合，再加入一点点调味料，延时队列就可以新鲜出炉了。想想看，延时队列，不就是想要消息延迟多久被处理吗，TTL 则刚好能让消息在延迟多久之后成为死信，另一方面，成为死信的消息都会被投递到死信队列里，这样只需要消费者一直消费死信队列里的消息就完事了，因为里面的消息都是希望被立即处理的消息。

### 4、整合springboot

#### 4.1 创建项目

#### 4.2 添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
  </dependency>
  <!--swagger-->
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
  </dependency>
  <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
  </dependency>
  <!--rabbitMQ测试依赖-->
  <dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

#### 4.3 修改配置文件

```yaml
spring:
  rabbitmq:
    host: 192.168.1.109
    port: 5672
    username: admin
    password: 123
```

#### 4.4 添加Swagger配置

### 5、队列TTL

#### 5.1 代码架构图

* 创建两个队列QA和QB，两者队列TTL分别设置10s和40s，然后创建一个交换机x和死信交换机Y，他们的类型都是direct，创建一个死信个队列QD，它们的绑定关系如下：![image-20220829234823856](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302226597.png)

#### 5.2 配置文件类代码

```java
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
    public Binding queuebBindingX(@Qualifier("queueB") Queue queue1B,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queue1B).to(xExchange).with("XB");
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

```

#### 5.3 生产者

```java
package com.smc.sprinbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{}，发送一条信息给TTL队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s："+message);
    }

}

```

#### 5.4 私信队列监听

```java
package com.smc.sprinbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Date 2022/8/30
 * @Author smc
 * @Description:
 * Ttl的消费者
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    //接收消息
    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列消息：{}",new Date().toString(),msg);
    }
}

```

### 6、延迟队列优化

#### 6.1 代码结构图

* 在这里新增一个队列QC，绑定关系如下，该队列不设置TTL时间![image-20220830212845577](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302226274.png)

#### 6.1 配置文件类和生产者代码

```java
//配置文件代码
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

//发送者代码
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
```

* 如果使用消息属性上设置TTL的方式，消息可能并不会按时死亡，因为RabbitMQ只会检查第一个消息是否过期，如果过期则丢到死信队列，如果第一个消息的延时时长很长，而第二个消息的延时时长很短，第二个消息并不会优先得到执行。

### 7、RabbitMQ插件实现延时队列

* 上文6中的问题，如果不能实现消息粒度的TTL，并使其在设置的TTL的时间及时死亡，就无法设计成一个通用的延时队列。

#### 7.1 安装延时队列插件

* 官网:<https://www.rabbitmq.com/community-plugins.html>
* 下载[rabbitmq-delayed-message-exchange](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)插件，然后解压放置到RabbitMQ的插件目录。
* 进入RabbitMQ的安装目录下的plugin目录，执行下面命令让插件赊销，然后重启RabbitMQ

```shell
[root@localhost opt]# mv rabbitmq_delayed_message_exchange-3.10.2.ez /usr/lib/rabbitmq/lib/rabbitmq_server-3.10.7/plugins/
[root@localhost opt]# cd /usr/lib/rabbitmq/lib/rabbitmq_server-3.10.7/plugins/

#安装
[root@localhost plugins]# rabbitmq-plugins enable rabbitmq_delayed_message_exchange

#重启
[root@localhost plugins]# systemctl restart rabbitmq-server

```

![image-20220830221652961](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302225364.png)

#### 7.3 配置文件类代码

* 在我们自定义的交换机中，这是一种新的交换类型，该类型消息支持延迟投递机制，消息传递后并不会立即投递到目标队列中，而是存储在mnesia（一个分布式数据系统）表中，当达到头哦滴时间是，才会投递到目标队列

```java
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

```

#### 7.4 生产者

```java
//发送的消息插件延迟
@GetMapping("/sendDelayPluginMsg/{message}/{ttlTime}")
public void sendMsg(@PathVariable String message, @PathVariable Integer ttlTime) {
  log.info("当前时间：{}，发送一条时长{}信息给插件延时队列：{}", new Date(), ttlTime, message);

  rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey", message, msg -> {
    //发送的消息的时候，延迟时长
    msg.getMessageProperties().setDelay(ttlTime);
    return msg;
  });

```

#### 7.5 消费者

```java
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
 
```



### 8、总结

* 延迟队列在需要延时处理的场景下非常有用,使用RabbitMQ来实现延时队列可以很好的利用RabbitMQ的特性，如：消息可靠发送、消息可靠投递、死信队列来保障消息至少被消费一次以及未被正确处理的消息不回被丢弃。另外，通过RabbitMQ集群的特性，可以很好的解决单点故障问题，不会因为单个节点挂掉导致延时队列不可用或者消息丢失。
* 当然，延时队列还有很多其他选择，比如利用Java的DelayQueue，利用Redis的zset，利用Quartz或者利用kafka的时间轮，这些方式各有特点，看需要使用的场景

# 高级部分

## 八、发布确认高级

* 在生产环境中由于一些不明原因，导致rabbitmq重启，在RabbitMQ重启期间生产者消息投递失败，导致信息丢失，需要手动处理和恢复。于是我们开始思考，如何才能进行RabbitMQ的消息可靠传递呢？特被是在这样比较极端的情况，RabbitMQ集群不可用的时候，无法投递的消息该如何处理呢。

### 1、发布确认springboot版本

#### 1.1 确认机制方案

![image-20220830232117250](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302225212.png)

#### 1.2 配置文件

* 在配置文件当中需要添加![image-20220830232307341](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302225789.png)
  * NONE:禁用发布确认模式，是默认值
  * CORRELATED:发布消息到交换机后会出发回调方法
  * SIMPLE：经测试有两种结果
    * 其一效果和CORRELATED值一样会出发回调方法
    * 其二在发布消息成功后使用rabbitTemplate调用waitForConfirms或waitForConfirmsOrDie方法等待broker节点返回发送结果，根据返回结果来判定下一步的逻辑，要注意的点是waitForConfirmsOrDie方法如果返回false则会关闭channel，则接下来无法发送消息到broker

#### 1.3 添加配置类

```java
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

    //声明交换机
    @Bean
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBinding(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                @Qualifier("confirmQueue") Queue confirmQueue) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

}

```



#### 1.4 消息生产者

```java
//测试发布确认
@GetMapping("/confirm/{message}")
public void sendMessage(@PathVariable String message){
  //给消息设置ID
  CorrelationData correlationData = new CorrelationData("1");
  log.info("当前时间：{}，发送一条信息给插件延时队列：{}", new Date(), message);
  rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData);
}
```



#### 1.5 回调接口

```java
package com.smc.sprinbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
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
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //注入
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
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
}

```



#### 1.6 消息消费者

```java
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

}

```



#### 1.7 结果分析

* 当消息无法被收到时，如交换机宕机，或routingkey写错，导致消息无法到达队列则会回调消息至回调方法，打印错误消息，成功是也会也会回调消息actk=true

### 2、回退消息

#### 2.1 Mandatory参数

* 在仅开启了生产者确认机制的情况下，交换机接收到消息后，会直接给消息生产者发送确认消息，如果发现该消息不可路由，那么消息会被直接丢弃，此时生产者是不知道消息被丢弃这个事件的。那么如何让无法被路由的消息帮我想办法处理一下？最起码通知我一声，我好自己处理啊。通过设置mandatory参数可以在当消息传递过程中不可达目的地时将消息返回给生产者。

#### 2.2 配置文件

![image-20220831205949452](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302232710.png)

#### 2.3 配置类，接口`RabbitTemplate.ReturnsCallback`

```java
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

```

### 3、备份交换机

* 有了mandatory参数和回退消息，我们获得了对无法投递消息额感知能力，有机会在生产者的消息无法被投递时发现并处理。但有时候，我们并不知道该如何处理这些无法被路由的消息，最多打个日志，然后触发警报，再来手动处理。而通过日志来处理这些无法路由的消息是很不雅的做法，特别是当生产者所在的服务器有多台机器的时候，手动复制日志会更加麻烦而且容易出错。而且设置mandatory参数会增加生产者的复杂性，该怎么做呢？前面在设置死信队列的文章中，我们提到，可以为队列设置死信交换机来存储那些处理失败的信息，可是这些不可路由消息根本没有机会进入到队列，因此无法使用死信队列来保存消息。在RabbitMQ中，有一种备份交换机的机制存在，可以很好的应对这个问题。什么事备份交换机呢？备份交换机可以立即为RabbitMQ中交换机的备胎，当我们为某一个交换机声明一个对应的备份交换机时，就是为它创建备胎，当交换机接收到一条不可路由消息时，将会把这条消息转发到备份交换机中，由备份交换机来进行转发和处理，通常备份交换机的类型为Fanout，这样就能把所有消息都投递到与其绑定的队列中，然后我们在备份交换机下绑定一个队列，这样所有那些原交换机无法被路由的消息，就会都进入这个队列了。当然，我们还可以建立一个报警队列，用独立的消费者来进行检测和报警。

#### 3.1代码架构图

![image-20220831213123462](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302232679.png)

#### 3.2 修改配置类

```java
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

```

#### 3.3报警消费者

```java
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

```

#### 3.4 生产者

```java
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
```

#### 3.5 结果分析

* Mandatory参数与备份交换机一起使用的时候，如果两者同时开启，消息究竟何去何从？谁优先级高，经过上面结果显示答案是备份交换机优先级高。

# 集群部分

## 九、RabbitMQ其他知识点

### 1、幂等性

#### 1.1 概念

* 用户对于同一操作发起的一次或多次请求的结果时一致的，不会因为多次点击而产生了副作用。举个简单的例子，那就是支付，用户购买商品后支付，支付扣款成功，但是返回结果的时候网络异常，此时钱已经扣了，用户在此点击按钮，此时不回进行第二次扣款，返回结果成功，用户查询余额发现钱多扣了，流水记录也变成了两条。在此前的单应用系统中，我们只需要把数据操作放入事物中即可，发生错误立即回滚，但是再响应客户端的时候也有可能出现网络中断或者异常等等

#### 1.2 消息重复消费

* 消费者在消费MQ中的消息时，MQ已把消息发送给了消费者，消费者在给MQ返回ack时网络中断，故MQ未收到确认信息。该条消息会重新发送给其他消费者，或者在网络重连后再次发送给该消费者，但是实际上该消费者已成功消费了该条消息，造成了消费者消费了重复的消息。

#### 1.3 解决思路

* MQ消费者的幂等性的解决一般使用全局ID或者写个唯一标识比如时间戳或者UUID或者订单消费者消费MQ中的消息也可利用MQ的该id来判断，或者可按自己的规则生成一个全局唯一id，每次消费消息时用该id先判断该消息是否被消费过。

#### 1.4 唯一ID+指纹码机制

* 指纹码：我们的一些规则或者时间戳加别的服务给到的唯一信息码，它并不一定是我们系统生成的，基本都是由我们的业务规则拼接而来，但是一定要保证唯一性，然后利用查询语句来进行判断这个id是否存在数据库中，优势就是实现简单的一个拼接，然后查询判断是否重复；劣势就是在高并发时，如果是单个数据库就会有写入性能瓶颈，当然也可以分库分表提升性能，但也不是我们最推就的方式

#### 1.5 Redis原子性

* 利用redis执行setnx命令，天然具有幂等性，从而实现不重复消费

### 2、优先级队列

#### 2.1 使用场景

* 在我们系统中有一个订单催付的场景，我们的客户在天猫上下的订单，淘宝会及时将订单推送给我们，如果在用户设定的时间内未付款那么就会给用户推送一条短信提醒，但是tmall商家对我们来说肯定要分大客户和小客户，理所应当，大客户的订单必须得到优先处理，而曾经我们的后端系统是使用gredis来存放的定时轮询，而redis只能用List做一个简单的消息队列，并不能实现一个优先级的场景，所以订单量大了后采用RabbitMQ进行改造和优化，如果发现是大客户的订单给一个相对比较高的优先级，否则就是默认优先级。

#### 2.2 如何添加

* 控制台：设置队列优先级最大值![image-20220831222019854](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302232969.png)
* 队列中代码添加优先级![image-20220831222225491](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302232381.png)
* 消息中代码添加优先级![image-20220831222246941](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302232334.png)
* 注意事项：要让队列实现优先级需要做的事情有如下事情：队列需要设置为优先级队列，消息需要设置消息的优先级，消费者需要等待消息已经发送到队列中才去消费，因为这样才有机会对消息进行排序

#### 2.3 实战

* 生产者

```java
package com.smc.helloworld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/27
 * @Author smc
 * @Description:
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "hello world";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP，连接RabbitMQ的队列
        factory.setHost("192.168.1.109");
        //设置用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 1、队列名称
         * 2、队列里面的消息是否持久化（磁盘），默认情况消息存储在内存中
         * 3、该队列是否只供一个消费者进行消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费
         * 4、是否自动删除，最后一个消费者断开连接以后，该队列是否自动删除 true自动删除，false不自动删除
         * 5、其他参数
         */
        HashMap<String, Object> arguments = new HashMap<>();
        //官方允许是0-255之间，此处设置10 允许优先级范围为0-10 不要设置过大，浪费cpu与内存
        arguments.put("x-max-priority",10);
        channel.queueDeclare(QUEUE_NAME,false,false,false,arguments);
        //发送的消息
        String msg = "hello world";
        /**
         * 使用channel发送消息
         * 1、发送到哪个交换机
         * 2、路由的key值是哪个，本地的队列是名称
         * 3、其他参数信息
         * 4、发送消息是消息体
         */
        for (int i = 0; i < 11; i++) {
            String temp = msg+i;
            if(i == 5){
              //设置消息优先级
                AMQP.BasicProperties properties = new AMQP.BasicProperties().
                        builder().priority(5).build();
                channel.basicPublish("",QUEUE_NAME,properties,temp.getBytes());
            }else{
                channel.basicPublish("",QUEUE_NAME,null,temp.getBytes());
            }
        }



        System.out.println("发送完毕");


    }
}

```

### 3、惰性队列

#### 3.1 使用场景

* RabbitMQ从3.6.0版本开始引入了惰性队列的概念。惰性队列会尽可能的将消息存入磁盘中，而在消费者消费到相应的消息时才会被加载到内存中，它的一个重要设计目标时能够支持更长的队列，即支持更多的消息存储。当消费者由于各种各样的原因（比如消费者下线、宕机亦或者是由于维护而关闭等）而致使长时间内不能消费造成堆积时，惰性队列就很有必要
* 默认情况下，当生产者将消息发送到RabbitMQ的时候，队列中的消息会尽可能的存放在内存之中，这样可以更加快速的将消息发送给消费者。即使时持久化的消息，在被写入磁盘的同时也会在内存中驻留一份备份。当RabbitMQ需要释放内存的时候，会将内存中的消息换页值磁盘中，这个操作会耗费很长的时间，也会阻塞队列的操作，进而无法接收新的消息。虽然RabbitMQ的开发者们一致在升级相关的算法，但是始终不太理想，尤其是在消息量特别大的时候。

#### 3.2 两种模式

* 队列具备两种模式：default和lazy。默认的为default模式，在3.6.0之前的版本无需做任何变更。lazy模式即为惰性队列的模式，可以通过调用channel.queueDeclare方法的时候在参数中设置，也可以通过Plicy的方式设置，如果一个队列同时使用这两种方式设置的话，那么Policy的方式更具有优先级。如果要通过声明的方式改变已有队列的模式的话，那么只能先删除队列，然后再重新声明一个新的。

* 在队列的声明的以后通过`x-queue-mode`参数来设置对立的模式，取值为“default”和“lazy”。下面示例中演示了一个惰性队列的声明细节：

  ```java
  Map<String,Object> args = new HashMap<>();
  args.put("x-queue-mode","lazy");
  channel.queueDeclare("myqueue",false,false,false,args);
  ```

## 十 、RabbitMQ集群

### 1、clustering

#### 1.1 使用集群的原因

* 最开始我们介绍了如何安装及运行 RabbitMQ 服务，不过这些是单机版的，无法满足目前真实应用的要求。如果 RabbitMQ 服务器遇到内存崩溃、机器掉电或者主板故障等情况，该怎么办？单台 RabbitMQ服务器可以满足每秒 1000 条消息的吞吐量，那么如果应用需要 RabbitMQ 服务满足每秒 10 万条消息的吞吐量呢？购买昂贵的服务器来增强单机 RabbitMQ 务的性能显得捉襟见肘，搭建一个 RabbitMQ 集群才是解决实际问题的关键.

#### 1.2 搭建步骤

* 修改三台机器的主机名称

  ```shell
  [root@smc ~]# vim /etc/hostname 
  
  ```

* 配置各个节点的hosts，使相互之间能够识别

  ```shell
  [root@smc ~]# vim /etc/hosts
  
  ```

  ![image-20220831232552320](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302233744.png)

* 以确保各个节点的 cookie 文件使用的是同一个值,在smc服务器上执行

  ```shell
  #将cookie文件传输给node1
  [root@smc ~]# scp /var/lib/rabbitmq/.erlang.cookie root@node1:/var/lib/rabbitmq/.erlang.cookie
  
  #将cookie文件传输给node1
  [root@smc ~]# scp /var/lib/rabbitmq/.erlang.cookie root@node2:/var/lib/rabbitmq/.erlang.cookie
  
  ```

* 启动 RabbitMQ 服务,顺带启动 Erlang 虚拟机和 RbbitMQ 应用服务(在三台节点上分别执行以下命令)

  ```shell
  [root@smc ~]# rabbitmq-server -detached
  
  ```

* 在节点2 执行

  ```shell
  #rabbitmqctl stop 会将 Erlang 虚拟机关闭，rabbitmqctl stop_app 只关闭 RabbitMQ 服务
  [root@node1 ~]# rabbitmqctl stop_app
  [root@node1 ~]# rabbitmqctl reset
  #将节点加入到smc创建集群
  [root@node1 ~]# rabbitmqctl join_cluster rabbit@smc
  [root@node1 ~]# rabbitmqctl start_app
  
  ```

* .在节点 3 执行

  ```shell
  #rabbitmqctl stop 会将 Erlang 虚拟机关闭，rabbitmqctl stop_app 只关闭 RabbitMQ 服务
  [root@node1 ~]# rabbitmqctl stop_app
  [root@node1 ~]# rabbitmqctl reset
  #将节点加入集群
  [root@node1 ~]# rabbitmqctl join_cluster rabbit@node1
  [root@node1 ~]# rabbitmqctl start_app
  ```

* 集群状态

  ```shell
  [root@node2 ~]# rabbitmqctl cluster_status
  
  ```

  ![image-20220831235134512](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302231454.png)

* 创建用户

  ```shell
  #创建账号
  [root@node2 ~]# rabbitmqctl add_user admin 123
  #设置用户角色
  [root@node2 ~]# rabbitmqctl set_user_tags admin administrator
  #设置用户权限
  [root@node2 ~]# rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"
  
  ```

* 接除集群node1和node2,在各机器上分别执行

  ```shell
  rabbitmqctl stop_app
  rabbitmqctl reset
  rabbitmqctl start_app
  rabbitmqctl cluster_status
  #可以不用执行遗忘操作
  rabbitmqctl forget_cluster_node rabbit@node2(node1 机器上执行)
  ```

### 2、镜像队列

#### 2.1 使用镜像队列的原因

* 如果RabbitMQ集群中只有Broker节点，那么该节点的失效将导致整体服务的临时性不可用，并且也可能会导致消息的丢失。可以将所有消息都设置为持久化，并且对应队列的durable属性也设置为true，但是这仍然无法避免由于缓存导致的问题：因为消息在发送之后和被写入磁盘并执行刷盘动作之间存在一个短暂却会产生问题的时间窗。通过publisherconfirm机制能够确保客户端知道那些消息已经存入磁盘，尽管如此，一般不希望遇到因单点故障导致的服务不可用
* 引入镜像队列（Mirror Queue）的机制，可以将队列镜像到集群中的其他Broker节点之上，如果集群中的一个节点失效了，队列能够自动地切换到镜像中的另一个节点上以保证服务的可用性

#### 2.2 搭建步骤

* 启动三台集群节点

* 随便找一个节点添加policy

  ![image-20220901001706306](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302231250.png)![image-20220901001825739](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302231729.png)![image-20220901001843801](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302233317.png)

### 3、Haproxy+keepalive实现高可用负载均衡

#### 3.1 Haproxy 实现负载均衡

### 4、Federation Exchange

#### 4.1 使用它的原因

* 两个交换机相距很远，各地可以访问各地的交换机，若要访问远的交换机的数据会有延迟，性能低下，所以要同步两个交换机数据，使用户访问本地交换机就能访问到想要的数据。Federation Exchange差劲解决了这个问题

#### 4.2 搭建

* 需要保证在每台节点单独运行

* 在每台机器上开启federation exchange

  ```shell
  [root@smc ~]# rabbitmq-plugins enable rabbitmq_federation
  [root@smc ~]# rabbitmq-plugins enable rabbitmq_federation_management
  
  ```

  ![image-20220901003535861](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302231587.png)

* 原理（先运行consumer在node2创建fed_exchange）![image-20220901003821498](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230303.png)

* 使用代码生成交换机、队列并绑定

  ```java
  package com.smc.helloworld;
  
  import com.rabbitmq.client.*;
  
  import java.io.IOException;
  import java.util.concurrent.TimeoutException;
  
  /**
   * @Date 2022/8/27
   * @Author smc
   * @Description:
   */
  public class Consumer {
      //队列名称
      public static final String QUEUE_NAME = "mirror world";
  
      public static void main(String[] args) throws IOException, TimeoutException {
          //创建工厂和连接
          ConnectionFactory factory = new ConnectionFactory();
          factory.setHost("192.168.1.120");
          factory.setUsername("admin");
          factory.setPassword("123");
          Connection connection = factory.newConnection();
  
  
          //由链接创建信道
          Channel channel = connection.createChannel();
          channel.exchangeDeclare("fed_exchange",BuiltinExchangeType.DIRECT);
          channel.queueDeclare("node1_queue",true,false,false,null);
          channel.queueBind("node1_queue","fed_exchange","routingkey");
  //        声明接收消息
          DeliverCallback deliverCallback=(consumerTag,message)->{
              System.out.println(new String(message.getBody()));
          };
  //        取消消息时回调
          CancelCallback cancelCallback = consumerTag ->{
              System.out.println("消费消息被中断");
          };
          /**
           * 消费者从信道接收信息
           * 1、消费哪个队列
           * 2、消费成功之后是否要自动应答 true代表自动应到，false代表手动应答
           * 3、消费者未成功消费的回调
           * 4、消费者取消消费的回调
           */
          channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
  
      }
  
  }
  
  ```

  

* 在 downstream(node2)配置 upstream(node1)

  ![image-20220901004618887](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230854.png)

* 添加 policy

  ![image-20220901004827624](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230060.png)

  

* 成功

  ![image-20220901005003780](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230067.png)

### 5、Federation Queue

#### 5.1 使用它的原因

* 联邦队列可以在多个 Broker 节点(或者集群)之间为单个队列提供均衡负载的功能。一个联邦队列可以连接一个或者多个上游队列(upstream queue)，并从这些上游队列中获取消息以满足本地消费者消费消息的需求。

#### 5.2 搭建

![image-20220901005123013](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230096.png)

* 添加upstream（同上）

* 添加policy

  ![image-20220901005343187](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302230921.png)

### 6、shovel

#### 6.1 使用它的原因

* Federation 具备的数据转发功能类似，Shovel 够可靠、持续地从一个 Broker 中的队列(作为源端，即source)拉取数据并转发至另一个 Broker 中的交换器(作为目的端，即 destination)。作为源端的队列和作为目的端的交换器可以同时位于同一个 Broker，也可以位于不同的 Broker 上。Shovel 可以翻译为"铲子"，是一种比较形象的比喻，这个"铲子"可以将消息从一方"铲子"另一方。Shovel 行为就像优秀的客户端应用程序能够负责连接源和目的地、负责消息的读写及负责连接失败问题的处理。

* 原理

  ![image-20220901005620410](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302229244.png)

#### 6.2 搭建步骤

* 开启插件(需要的机器都开启)

  ```shell
  rabbitmq-plugins enable rabbitmq_shovel
  rabbitmq-plugins enable rabbitmq_shovel_management
  ```

* .添加 shovel 源和目的地

![image-20220901005736589](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202305302229894.png)





