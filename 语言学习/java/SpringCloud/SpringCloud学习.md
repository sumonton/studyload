# 一、微服务架构理论入门

## 1、SpringCloud使用到的技术

![截屏 2023-01-30 22.28.09.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303191445406.png)

## 2、SpringCloud技术栈

![截屏 2023-01-30 22.38.05.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121330426.png)

![截屏 2023-01-30 22.39.35.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121330086.png)

# 二、Boot和Cloud版本选型

|   技术类别    |  版本选型  |
| :-----------: | :--------: |
|     cloud     |  2021.0.5  |
|     boot      |   2.6.13   |
| cloud alibaba | 2021.0.4.0 |
|     Java      |   Java8    |
|     maven     |    3.8     |
|     mysql     |   Mysql8   |

# 三、Cloud组件停更说明和替换

![截屏 2023-01-30 23.05.57.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121332319.png)

# 四、工程构建

## 1、Maven的DependencyManagement和Dependencies的区别

* Maven使用DependencyManagement元素来提供一种管理以来版本号的方式，通常会在一个组织或者项目的最底层的父POM中看到dependencyManangement元素
* 使用pom.xml中的depencyManagement元素能让所有子项目中引用一个依赖而不用显式的列出版本号。Maven回沿着父子层次向上走，知道找到一个用用dependencyManagement元素的项目，然后它回使用这个dependencyManagement元素指定的版本号
* 这样做的好处就是：如果有多个子项目都引用同一样依赖，则可以避免在每个使用的子项目里都声明一个版本号，这样当想升级或切换到另一个版本的时，只需要在顶层父容器进行更新即可
* dependencyManagement里知识声明依赖，并不实现引入，因此子项目需要显示的声明需要用的依赖
* 如果子项目中指定里版本号，那么会使用子项目中指定的版本号。

### 1.1 POM

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.smc.springcloud</groupId>
  <artifactId>cloud2023</artifactId>
  <version>1.0-SNAPSHOT</version>
  <modules>
    <module>cloud-provider-payment8001</module>
  </modules>
  <packaging>pom</packaging>

  <!--统一管理jar包版本-->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.2</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.16.18</lombok.version>
    <mysql.version>8.0.29</mysql.version>
    <druid.version>1.2.15</druid.version>
    <mybatis.plus.spring.boot.version>3.5.1</mybatis.plus.spring.boot.version>
  </properties>
  <!--子模块继承之后，提供作用：锁定版本+子module不用写groudId和Version-->
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.6.13</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>2021.0.5</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        <version>2021.1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
      </dependency>
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>${druid.version}</version>
      </dependency>
      <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>${mybatis.plus.spring.boot.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.7.1</version>
        </plugin>
      </plugins>
    </pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <configuration>
          <locales>en,fr</locales>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>

```

# 五、微服务模块步骤（支付模块构建）

## 1. 建module

## 2. 改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2023</artifactId>
        <groupId>com.smc.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

</project>
```

## 3. 写YML

```yaml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource #当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver #mysql驱动包
    url: jdbc:mysql://localhost:3306/springcloud2023?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: smchen123

mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.smc.springcloud.domain #所有Entity别名类所在包



```

## 4. 主启动

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date 2023/1/31
 * @Author smc
 * @Description:
 */
@SpringBootApplication
public class Payment8001 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8001.class,args);
    }
}

```

## 5 业务类

1. 建表SQL

   ```sql
   -- springcloud2023.payment definition
   
   CREATE TABLE `payment` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `serial` varchar(200) COLLATE utf8_bin DEFAULT '',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
   ```

   

2. Domain

   * 主实体Payment(使用mybatis-plus插件自动生成)

     ```java
     package com.smc.springcloud.domain;
     
     import java.io.Serializable;
     
     import lombok.AllArgsConstructor;
     import lombok.Data;
     import lombok.NoArgsConstructor;
     
     /**
      *
      * @TableName payment
      */
     @Data
     @AllArgsConstructor
     @NoArgsConstructor
     public class Payment implements Serializable {
         /**
          * ID
          */
         private Long id;
     
         /**
          *
          */
         private String serial;
     
     
         @Override
         public boolean equals(Object that) {
             if (this == that) {
                 return true;
             }
             if (that == null) {
                 return false;
             }
             if (getClass() != that.getClass()) {
                 return false;
             }
             Payment other = (Payment) that;
             return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                     && (this.getSerial() == null ? other.getSerial() == null : this.getSerial().equals(other.getSerial()));
         }
     
         @Override
         public int hashCode() {
             final int prime = 31;
             int result = 1;
             result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
             result = prime * result + ((getSerial() == null) ? 0 : getSerial().hashCode());
             return result;
         }
     
         @Override
         public String toString() {
             StringBuilder sb = new StringBuilder();
             sb.append(getClass().getSimpleName());
             sb.append(" [");
             sb.append("Hash = ").append(hashCode());
             sb.append(", id=").append(id);
             sb.append(", serial=").append(serial);
             sb.append("]");
             return sb.toString();
         }
     }
     ```

   * Json封装体CommonResult

     ```java
     package com.smc.springcloud.domain;
     
     import lombok.AllArgsConstructor;
     import lombok.Data;
     import lombok.NoArgsConstructor;
     
     /**
      * @Date 2023/2/1
      * @Author smc
      * @Description:
      */
     @Data
     @AllArgsConstructor
     @NoArgsConstructor
     public class CommonResult<T> {
         private Integer code;
         private String message;
         private T data;
         public CommonResult(Integer code,String message){
             this(code,message,null);
         }
     }
     
     ```

     

3. mapper(工具生成)

   ```java
   package com.smc.springcloud.mapper;
   
   import com.smc.springcloud.domain.Payment;
   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
   import org.apache.ibatis.annotations.Mapper;
   import org.apache.ibatis.annotations.Param;
   
   /**
   * @author smc
   * @description 针对表【payment】的数据库操作Mapper
   * @createDate 2023-02-01 00:26:49
   * @Entity generator.domain.Payment
   */
   @Mapper
   public interface PaymentMapper extends BaseMapper<Payment> {
       int create(Payment payment);
   
       Payment getPaymentById(@Param("id") Long id);
   }
   
   
   
   
   
   ```

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.smc.springcloud.mapper.PaymentMapper">
   
       <resultMap id="BaseResultMap" type="com.smc.springcloud.domain.Payment">
               <id property="id" column="id" jdbcType="BIGINT"/>
               <result property="serial" column="serial" jdbcType="VARCHAR"/>
       </resultMap>
   
       <insert id="create" parameterType="com.smc.springcloud.domain.Payment" useGeneratedKeys="true" keyProperty="id">
           insert into payment(serial) values(#{serial})
       </insert>
       <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
           select * from payment where id=#{id}
       </select>
       <sql id="Base_Column_List">
           id,serial
       </sql>
   </mapper>
   
   ```

   

4. service

   ```java
   package com.smc.springcloud.service;
   
   import com.smc.springcloud.domain.Payment;
   import com.baomidou.mybatisplus.extension.service.IService;
   
   /**
   * @author smc
   * @description 针对表【payment】的数据库操作Service
   * @createDate 2023-02-01 00:26:49
   */
   public interface PaymentService extends IService<Payment> {
       int create(Payment payment);
       Payment getPaymentById(Long id);
   }
   
   ```

   ```java
   package com.smc.springcloud.service.impl;
   
   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
   import com.smc.springcloud.domain.Payment;
   import com.smc.springcloud.service.PaymentService;
   import com.smc.springcloud.mapper.PaymentMapper;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import javax.annotation.Resource;
   
   /**
   * @author smc
   * @description 针对表【payment】的数据库操作Service实现
   * @createDate 2023-02-01 00:26:49
   */
   @Service
   public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
       implements PaymentService{
       @Resource
       private PaymentMapper paymentMapper;
       @Override
       public int create(Payment payment) {
           return paymentMapper.insert(payment);
       }
   
       @Override
       public Payment getPaymentById(Long id) {
           return paymentMapper.selectById(id);
       }
   }
   
   ```

   

5. controller

   ```java
   package com.smc.springcloud.controller;
   
   import com.smc.springcloud.domain.CommonResult;
   import com.smc.springcloud.domain.Payment;
   import com.smc.springcloud.service.PaymentService;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.web.bind.annotation.*;
   
   import javax.annotation.Resource;
   
   /**
    * @Date 2023/2/1
    * @Author smc
    * @Description:
    */
   @RestController
   @RequestMapping(value = "payment")
   @Slf4j
   public class PaymentController {
       @Resource
       private PaymentService paymentServiceImpl;
       @RequestMapping(method = RequestMethod.POST,value = "/create")
       public CommonResult create(Payment payment){
           int result = paymentServiceImpl.create(payment);
   
           log.info("****插入结果："+result);
           if(result>0){
               return new CommonResult(200,"插入数据库成功",result);
           }else{
               return new CommonResult(444,"插入数据库失败",null);
           }
       }
   
       @RequestMapping(method = RequestMethod.GET,value = "/create/{id}")
       public CommonResult getPaymentById(@PathVariable("id") Long id){
           Payment result = paymentServiceImpl.getPaymentById(id);
           log.info("****插入结果："+result.toString());
           if(result!=null){
               return new CommonResult(200,"查询成功",result);
           }else{
               return new CommonResult(444,"没有记录，查询ID:"+id,null);
           }
       }
   }
   ```

## 6. 测试

* 当有多个module时会开启Run DashBoard，方便管理启动module

# 六、消费者订单模块

## 1、建module

## 2、改POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2023</artifactId>
        <groupId>com.smc.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumer-order80</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
  
</project>
```

## 3、写YML

```yaml
server:
  port: 80
```

## 4、主启动

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date 2023/2/1
 * @Author smc
 * @Description:
 */
@SpringBootApplication
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}

```

## 5、业务类

### 5.1 RestTemplate 实现服务请求

```java
package com.smc.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Date 2023/2/1
 * @Author smc
 * @Description:
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```

### 5.2 实体类

```java
package com.smc.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2023/2/1
 * @Author smc
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;
    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}

```

```java
package com.smc.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName payment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 
     */
    private String serial;

}
```

### 5.3 controller

```java
package com.smc.springcloud.controller;

import com.smc.springcloud.domain.CommonResult;
import com.smc.springcloud.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Date 2023/2/1
 * @Author smc
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping(value = "consumer")
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8001";
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET,value = "payment/create")
    public CommonResult create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @RequestMapping(method = RequestMethod.GET,value = "payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/getPaymentById/"+id,CommonResult.class);
    }
}

```

## 6、测试

* 在服务接收为实体类时，不要忘记@RequestBody注解

  ![image-20230202000452796](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121333194.png)

# 七、工程重构

* 将重复的代码放到一个工程内

  ![image-20230203001803556](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121333474.png)

* 在代码其他工程内引入

  ```xml
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  ```


# 八、服务注册管理：Eureka（已停止更新）

## 1、Eureka基础知识

### 1.1 什么是服务治理

* Spring Cloud封装了Netflix公司开发的Eureka模块来实现服务治理
* 在传统的rpc远程调用框架中，管理每个服务于服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务与服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与治理。

### 1.2 什么是服务注册与发现

* Eureka采用CS的设计架构，Eureka Server作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用gEureka的客户端连接到Eureka Server并维持心跳连接。这样系统的维护人员就可以通过Eureka Server 来监控系统中各个微服务是否正常运行
* 在服务注册与发现，又一个注册中心。当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。另一方（消费者｜服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用RPC远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系（服务治理概念）。在任何rpc远程框架中，都会有一个注册中心（存放）（存放服务地址的相关信息（接口地址））

![截屏 2023-02-07 00.14.14.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121333159.png)



### 1.3 Eureka包含两个组件：Eureka Server 和Eureka Client

* Eureka Server提供注册服务
  * 各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。
* EurekaClient通过注册中心尽心个访问
  * 是一个Java客户端，用于简化Eureka Server的交互，客户端同时也是具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（默认周期为30秒）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）。

## 2、EurekaServer服务端安装

### 2.1 IDEA生成eurekaServer端服务注册中心，类似物业公司

* 建Module

  * Cloud-eureka-server7001

* 改POM

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <parent>
          <artifactId>cloud2023</artifactId>
          <groupId>com.smc.springcloud</groupId>
          <version>1.0-SNAPSHOT</version>
      </parent>
      <modelVersion>4.0.0</modelVersion>
  
      <artifactId>cloud-eureka-server7001</artifactId>
      <dependencies>
          <!--eureka2.0引入eurekaServer包-->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-actuator</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
              <scope>runtime</scope>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
          <dependency>
              <groupId>com.smc.springcloud</groupId>
              <artifactId>cloud-api-commons</artifactId>
              <version>1.0-SNAPSHOT</version>
          </dependency>
      </dependencies>
      <properties>
          <maven.compiler.source>8</maven.compiler.source>
          <maven.compiler.target>8</maven.compiler.target>
      </properties>
  
  </project>
  ```

* 改YML

  ```yaml
  server:
    port: 7001
  
  eureka:
    instance:
      hostname: localhost #eureka服务器实例名称
    client:
      #false表示不向注册中心注册自己
      register-with-eureka: false
      #false表示自己端就是注册中心，我们的职责就是维护服务实例，并不需要去检索服务
      fetch-registry: false
      service-url:
        #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
        defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  
  ```

* 主启动

  ```java
  package com.smc.springcloud;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
  
  import javax.swing.*;
  
  /**
   * @Date 2023/2/7
   * @Author smc
   * @Description:
   		@EnableEurekaServer ：eurekaServer服务启动
   */
  @SpringBootApplication
  @EnableEurekaServer
  public class EurekaMain7001 {
      public static void main(String[] args) {
          SpringApplication.run(EurekaMain7001.class);
      }
  }
  
  ```

* 测试

  * http://localhost:7001

  ![image-20230207233224579](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121333283.png)

## 3、EurekaClient客户端安装

### 3.1 EurekaClient端cloud-provider-payment8001将注册进EurekaServer成为服务的提供者provider

* 引入eureke2.0的client包

  ```xml
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```

* 修改yml文件

  ```yaml
  spring:
    application:
      name: cloud-payment-service #注册的服务名
  
  eureka:
    client:
      #表示是否将自己注册金EurekaServer，默认为true
      register-with-eureka: true
      #是否将EurekaServer抓取已有的注册信息，默认为true。单点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
      fetch-registry: true
      service-url:
        #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
        defaultZone: http://localhost:7001/eureka/
  ```

* 启动类添加EurekaClient注解

  ```java
  package com.smc.springcloud;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
  
  /**
   * @Date 2023/1/31
   * @Author smc
   * @Description:
   */
  @SpringBootApplication
  @EnableEurekaClient
  public class Payment8001 {
      public static void main(String[] args) {
          SpringApplication.run(Payment8001.class,args);
      }
  }
  
  ```

* 服务注册结果

  ![image-20230207234448725](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121334399.png)

* 自我保护机制

  ![image-20230207234544061](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121334794.png)

### 3.2 EurekaClient端cloud-consumer-order80,将注册进EurekaServer成为服务消费者consumer

* 同上

* 结果

  ![image-20230207235217116](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121334754.png)

## 4、Eureka集群原理说明

### 4.1 Eureka集群原理说明

![截屏 2023-02-08 00.02.25.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121335647.png)

* 搭建Eureka注册中心集群，实现负载均衡+故障容错，实现高可用

* 互相注册，相互守望

  ![截屏 2023-02-08 00.05.40.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121335211.png)

### 4.2 Eureka集群搭建

* 参考7001,新建7002

* 修改映射配置

  ![image-20230209002347106](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121335903.png)

* 写Yml（相互注册，相互守望）

  * 7001

    ```yaml
    server:
      port: 7001
    
    eureka:
      instance:
        hostname: eureka7001.com #eureka服务器实例名称
      client:
        #false表示不向注册中心注册自己
        register-with-eureka: false
        #false表示自己端就是注册中心，我们的职责就是维护服务实例，并不需要去检索服务
        fetch-registry: false
        service-url:
          #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
          defaultZone: http://eureka7002:7002/eureka/
    
    
    ```

  * 7002

    ```yaml
    server:
      port: 7002
    
    eureka:
      instance:
        hostname: eureka7002.com #eureka服务器实例名称
      client:
        #false表示不向注册中心注册自己
        register-with-eureka: false
        #false表示自己端就是注册中心，我们的职责就是维护服务实例，并不需要去检索服务
        fetch-registry: false
        service-url:
          #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
          defaultZone: http://eureka7001.com:7001/eureka/
    
    
    ```

* 测试

  * 互相能访问到

  ![image-20230209010324297](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121335004.png)

  ![image-20230209010344648](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302121335605.png)


### 4.3 订单支付两微服务注册进Eureka集群

* 8001和80的eureka配置未集群模式

  ```yaml
  eureka:
    client:
      #表示是否将自己注册金EurekaServer，默认为true
      register-with-eureka: true
      #是否将EurekaServer抓取已有的注册信息，默认为true。单点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
      fetch-registry: true
      service-url:
        #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
        #      defaultZone: http://localhost:7001/eureka/  #单机版
        defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
  ```

* 当其中一台宕机后自动挂上另外一台eureka

### 4.4 支付服务提供者8001的集群环境构建

#### 1）参考8001搭建8002

#### 2) 复制POM

#### 3）写YML

#### 4）启动类

#### 5）业务类

* 直接从8001粘贴

#### 6）修改8001/8002的Controller

* 加上端口打印出来识别

  ![image-20230226231846586](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302262318992.png)

#### 7）80请求的地址不写死，使用该服务名称请求

* 修改请求地址

  ```java
  //    public static final String PAYMENT_URL = "http://localhost:8001";
  //修改为服务名称，能够集群访问
  public static final String PAYMENT_URL = "http://cloud-payment-service";
  @Resource
  private RestTemplate restTemplate;
  ```

  

* 并在applicationCOntextConfig加上@LoadBalanced注解

  ```java
  @Configuration
  public class ApplicationContextConfig {
  
      @Bean
      //服务RestTemplate负载均衡的能力
      @LoadBalanced
      public RestTemplate getRestTemplate(){
          return new RestTemplate();
      }
  }
  ```

### 4.5 微服务信息完善

#### 1）主机名称：服务名称修改

```yaml
eureka:
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: true
    #是否将EurekaServer抓取已有的注册信息，默认为true。单点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetch-registry: true
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
#      defaultZone: http://localhost:7001/eureka  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
  instance:
    instance-id: payment8002 #修改服务名称
```

![image-20230227234840363](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302272348504.png)

#### 2）访问信息又ip显示

```yaml
eureka:
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: true
    #是否将EurekaServer抓取已有的注册信息，默认为true。单点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetch-registry: true
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
#      defaultZone: http://localhost:7001/eureka  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
  instance:
    instance-id: payment8001
    prefer-ip-address: true # ip显示
```

### 4.6 服务发现Discovery

* 对于注册进eureka的微服务，可以通过服务发现来获得该服务的信息

#### 1) 修改controller

```java
@Resource
private DiscoveryClient discoveryClient;
@GetMapping(value = "/payment/discovery")
public Object discovery(){
  //获取服务清单列表
  List<String> services = discoveryClient.getServices();
  for (String element:
       services) {
    log.info("***element"+element);
  }
  //获取某个服务下的实例
  List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
  for (ServiceInstance instance:instances
      ) {
    log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getHost()
             +"\t"+instance.getPort()+"\t"+instance.getUri());
  }
  return this.discoveryClient;
}
```

#### 2) 启动类@EnableDiscoveryClient注解

```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class Payment8001 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8001.class,args);
    }
}
```

### 4.7 Eureka的自我保护

![image-20230228001000747](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202302280010814.png)

#### 1） 概述

* 保护模式主要用于一组客户端和Eureka Server之间存在网络分区场景下的保护。一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不在删除服务注册表的数据，也就是不会注销任何微服务。

#### 2） 导致原因

* 某时刻某一个微服务不可用了，Eureka不会立刻清理
* 属于CAP的AP分支
* 为什么会产生自我保护机制
  * 为了防止EurekaClient可以正常运行，但是与EurekaServer网络不通情况下，EurekaServer不会立刻将EurekaClient服务剔除
* 什么是自我保护模式
  * 默认情况下，EurekaServer在一定时间内没有接收到某个微服务的实例心跳，EurekaServer将会注销该实例（默认90秒）。但是在网路分区故障发生（延时、卡顿、拥挤）时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险-因为微服务本身其实还是健康的，此时不应该注销该微服务。Eureka通过“自我保护机制”来解决这个问题-当EurekaServer节点在短时间跌势过多客户端时，那么这个节点就会进入自我保护模式。

#### 3） 怎么禁止自我保护

* 注册中心EurekeServer

  ```yaml
  eureka:
    server:
      #关闭自我保护机制，保证不可用服务被及时剔除
      enable-self-preservation: false
      #延迟时间设置
      eviction-interval-timer-in-ms: 2000
  ```

* 客户端eurekaClient

  ```yaml
  eureka:
    instance:
      #Eureka客户端向服务端发送心跳的时间间隔，单位为秒（默认是30秒）
      lease-renewal-interval-in-seconds: 1
      #Eureka服务端在接受到最后一次心跳后等待时间上限，单位为秒（默认是90秒），超时将剔除服务
      lease-expiration-duration-in-seconds: 2
  ```

# 九、服务注册管理：Zookeeper

## 1、基本介绍

* zookeeper是一个分布式协调工具，可以实现注册中心功能
* 关闭Linux服务器防火墙后启动zookeeper服务器
* zookeeper服务器取代Eureka服务器，zk作为服务注册中心

## 2、服务提供者

### 2.1 新建cloud-provider-payment8004

### 2.2 POM

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--SpringBoot整合zookeeper整合-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
  </dependency>
</dependencies>
```



### 2.3 YML

```yaml
server:
  port: 8004

spring:
  application:
    name: cloud-payment-service
  cloud:
    zookeeper:
      connect-string: 192.168.3.37:2181
```

### 2.4 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/3/1
 * @Author smc
 * @Description:
 */
@SpringBootApplication
//该注解用于向使用consul或者zookeeper作为注册中心时注册服务
@EnableDiscoveryClient
public class Payment8004 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8004.class,args);
    }
}

```

### 2.5 Controller

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Date 2023/3/1
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(method = RequestMethod.GET,value = "/port")
    public String paymentPort(){
        return "sprintcloud with zookeeper:"+serverPort+"\t"+ UUID.randomUUID().toString();
    }
}

```

### 2.6 启动8004注册进zookeeper

* 服务器docker安装zookeeper

  > <https://blog.csdn.net/duyun0/article/details/128437451>

### 2.7 验证测试

#### 1)测试1

![image-20230305155944036](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051559931.png)

#### 2）测试2

```shell
ls /services/cloud-payment-service
17:36:42.684 [main] DEBUG org.apache.zookeeper.ZooKeeperMain - Processing ls
17:36:42.689 [main-SendThread(localhost:2181)] DEBUG org.apache.zookeeper.ClientCnxn - Reading reply session id: 0x100007839c60003, packet:: clientPath:null serverPath:null finished:false header:: 7,12  replyHeader:: 7,10,0  request:: '/services/cloud-payment-service,F  response:: v{'8e547ac0-3208-47aa-99b8-064c2a27d0a6},s{5,5,1677604606760,1677604606760,0,1,0,0,0,1,6} 
[8e547ac0-3208-47aa-99b8-064c2a27d0a6]


get /services/cloud-payment-service/8e547ac0-3208-47aa-99b8-064c2a27d0a6
17:39:05.107 [main-SendThread(localhost:2181)] DEBUG org.apache.zookeeper.ClientCnxn - Reading reply session id: 0x100007839c60003, packet:: clientPath:null serverPath:null finished:false header:: 11,4  replyHeader:: 11,10,0  request:: '/services/cloud-payment-service/8e547ac0-3208-47aa-99b8-064c2a27d0a6,F  response:: #7b226e616d65223a22636c6f75642d7061796d656e742d73657276696365222c226964223a2238653534376163302d333230382d343761612d393962382d303634633261323764306136222c2261646472657373223a223139322e3136382e332e3233222c22706f7274223a383030342c2273736c506f7274223a6e756c6c2c227061796c6f6164223a7b2240636c617373223a226f72672e737072696e676672616d65776f726b2e636c6f75642e7a6f6f6b65657065722e646973636f766572792e5a6f6f6b6565706572496e7374616e6365222c226964223a22636c6f75642d7061796d656e742d73657276696365222c226e616d65223a22636c6f75642d7061796d656e742d73657276696365222c226d65746164617461223a7b22696e7374616e63655f737461747573223a225550227d7d2c22726567697374726174696f6e54696d65555443223a313637383030323336303034352c227365727669636554797065223a2244594e414d4943222c2275726953706563223a7b227061727473223a5b7b2276616c7565223a22736368656d65222c227661726961626c65223a747275657d2c7b2276616c7565223a223a2f2f222c227661726961626c65223a66616c73657d2c7b2276616c7565223a2261646472657373222c227661726961626c65223a747275657d2c7b2276616c7565223a223a222c227661726961626c65223a66616c73657d2c7b2276616c7565223a22706f7274222c227661726961626c65223a747275657d5d7d7d,s{6,6,1677604606766,1677604606766,0,0,0,72058110403280897,561,0,6} 
{"name":"cloud-payment-service","id":"8e547ac0-3208-47aa-99b8-064c2a27d0a6","address":"192.168.3.23","port":8004,"sslPort":null,"payload":{"@class":"org.springframework.cloud.zookeeper.discovery.ZookeeperInstance","id":"cloud-payment-service","name":"cloud-payment-service","metadata":{"instance_status":"UP"}},"registrationTimeUTC":1678002360045,"serviceType":"DYNAMIC","uriSpec":{"parts":[{"value":"scheme","variable":true},{"value":"://","variable":false},{"value":"address","variable":true},{"value":":","variable":false},{"value":"port","variable":true}]}}

```

### 2.8 注册进zookeeper的服务时临时还是永久？

* 是临时的，关闭服务后，心跳极致后会关闭服务

## 3、服务消费者

### 3.1 新建cloud-consumerzk-order80

### 3.2 POM

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
  </dependency>
</dependencies>
```



### 3.3 YML

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order
  cloud:
    zookeeper:
      connect-string: 192.168.3.37:2181
```

### 3.4 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZK80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZK80.class,args);
    }
}

```

### 3.5 业务配置类

* 实现转发

  ```java
  package com.smc.springcloud.config;
  
  import org.springframework.cloud.client.loadbalancer.LoadBalanced;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.client.RestTemplate;
  
  /**
   * @Date 2023/3/5
   * @Author smc
   * @Description:
   */
  @Configuration
  public class ApplicationContextConfig {
      @Bean
      @LoadBalanced
      public RestTemplate getRestTemplate(){
          return new RestTemplate();
      }
  }
  
  ```

  

### 3.6 Controller

```java
package com.smc.springcloud.controller;

import com.smc.springcloud.domain.CommonResult;
import com.smc.springcloud.domain.Payment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "/consumer")
public class OrderController {

    public static final String PAYMENT_URL = "http://cloud-payment-service";
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET,value = "payment/port")
    public String getPort(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/port",String.class);
    }

}

```

### 2.6 启动80注册进zookeeper

* 服务器docker安装zookeeper

  > <https://blog.csdn.net/duyun0/article/details/128437451>

### 2.7 验证测试

![image-20230305163621888](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051636955.png)

# 十、服务注册管理：consul

## 1、基本介绍

<https://developer.hashicorp.com/consul/docs/intro>

## 2、安装并运行Consul

* 官网教程<https://developer.hashicorp.com/consul/downloads>
* docker安装<https://www.cnblogs.com/lfzm/p/10633595.html>

## 3、服务提供者

### 3.1 新建cloud-provider-payment8006

### 3.2 POM

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--SpringBoot整合consul整合-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
  </dependency>
</dependencies>
```

### 3.3 YML

```yaml
server:
  port: 8006

spring:
  application:
    name: cloud-payment-service
  cloud:
    consul:
      host: 192.168.3.37
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```

### 3.4 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Payment8006 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8006.class,args);
    }
}

```

### 3.5 Controller

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
    @Value("${server.port}")
    private String port;
    
    @RequestMapping(method = RequestMethod.GET,value = "/port")
    public String getPort(){
        return "springcloud with consul:"+port+"\t"+ UUID.randomUUID().toString();
    }
}

```

### 3.6 启动8006注册进consul

### 3.7 验证测试

![image-20230305173818623](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051738695.png)

![image-20230305155944036](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051738013.png)

## 4、服务消费者

### 4.1 新建cloud-consumercs-order80

### 4.2 POM

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--引入consul包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
  </dependency>
</dependencies>
```



### 3.3 YML

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order
  cloud:
    consul:
      host: 192.168.3.37
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```

### 3.4 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderCS {
    public static void main(String[] args) {
        SpringApplication.run(OrderCS.class, args);
    }
}

```

### 3.5 业务配置类

* 实现转发

  ```java
  package com.smc.springcloud.config;
  
  import org.springframework.cloud.client.loadbalancer.LoadBalanced;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.client.RestTemplate;
  
  /**
   * @Date 2023/3/5
   * @Author smc
   * @Description:
   */
  @Configuration
  public class ApplicationContextConfig {
      @Bean
      @LoadBalanced
      public RestTemplate restTemplate(){
          return new RestTemplate();
      }
  }
  
  ```

  

### 3.6 Controller

```java
package com.smc.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Date 2023/3/5
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "/consumer")
public class ConsulController {

    @Resource
    private RestTemplate restTemplate;
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @RequestMapping(method = RequestMethod.GET,value = "/payment/port")
    public String getPort(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/port",String.class);
    }
}

```

### 2.6 启动80注册进consul

![image-20230305174936617](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051749706.png)

### 2.7 验证测试

![image-20230305175015334](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051750481.png)

# 十一、服务注册中心：eureka，zookeeper，consul异同点

| 组件名    | 语言 | CAP  | 服务健康检查 | 对外暴露接口 | SpringCloud集成 |
| --------- | ---- | ---- | ------------ | ------------ | --------------- |
| Eureka    | JAVA | AP   | 可配支持     | HTTP         | 已集成          |
| Consul    | GO   | CP   | 支持         | HTTP/DNS     | 已集成          |
| zookeeper | JAVA | CP   | 支持         | 客户端       | 已集成          |

## 1、CAP

* C:Consistency(强一致性)
* A: Availability(可用性)
* P: Partition tolerance (分区容错性)
* CAP理论关注粒度的数据，而不是整体系统设计的策略

* 经典CAP图

  ![截屏 2023-03-05 18.00.05.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303051800245.png)

* 最多只能同时较好的满足两个
  * CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求，因此，根据CAP原理将NoSQL数据库分成了满足CA原则、满足CP原则和满足AP原则三大类
    * CA：单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大
    * CP：满足一致性，分区容错性的系统，通常性能表示特别高
    * AP：满足可用性，分区容错性的系统沟通后藏可能对一致性要求低一些

# 十二、服务调用：Ribbon，LoadBalancer

## 1、基本介绍

* Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端，负载均衡的工具
* 简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon客户端组件提供一系列完善的配置项如连接时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮训，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。

### 1.1 官网

* <https://github.com/Netflix/ribbon/wiki/Getting-Started>
* 替换方案：SpringCloud Loadbalancer

### 1.2 能干啥

* LB负载均衡
  * 集中式LB
  * 进程式LB
* LB是什么：简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA（高可用）
* 常见的负载均衡有软件Nginx，LVS，硬件F5等

### 1.3 Ribbon本地负载均衡和Nginx服务端负载均衡区别

* Nginx是服务器负载均衡，客户端所有的请求都会交给Nginx，然后由Nginx实现转发功能。即负载均衡是由服务端实现的
* Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而实现本地实现RPC远程服务调用技术。

## 2、Ribbon负载均衡演示

### 2.1 架构说明

* Ribbon其实就是一个软负载均衡的客户端组件，他可以和其他所需请求的客户端结合使用，和eureka结合只是其中的一个实例

  ![截屏 2023-03-06 23.32.43.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303062334200.png)

* Ribbon在工作时分成两步

  * 第一步先选择EurekaServer，它优先选择在同一个区域内负载较少的server
  * 第二步再根据用户指定的策略，在从server去到的服务注册列表中选择一个地址。其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权

* eureka已经整合了Ribbon,由于Ribbon闭源了，3.0以上版本使用loadbalancer替换了Ribbon

  ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```

  

### 2.2 二说RestTemplate的使用

* 官网

  > https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html

* getForObject/getForEntity

  * getForObject:一般获取的是Json字符串
  * getForEntity:获取的是ResponseEntity对象,包含一些响应头，响应信息。

  ```java
  @RequestMapping(method = RequestMethod.GET,value = "payment/getForEntity/{id}")
  public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
    ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
    if (entity.getStatusCode().is2xxSuccessful()){
      return entity.getBody();
    } else {
      return new CommonResult<>(444,"操作失败");
    }
  }
  ```

## 3、Ribbon核心组件IRule

* IRule：根据特定算法中从服务列表中选取一个要访问的服务

![](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303070013952.png)

![截屏 2023-03-07 00.14.31.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303070015282.png)

### 3.1 如何替换

#### 1） 修改cloud-consumer-order80

* 手动引入Ribbon

#### 2） 注意配置细节

#### 3） 新建package

* com.smc.myrule
* 官方警告：这个自定义类不能放在ComponentScan所扫描的档期哪包下以及子包下，否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊化定制的目的了。

#### 4）上面包下新建MySelfRule规则类

```java
package com.smc.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2023/3/7
 * @Author smc
 * @Description:
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        //定义为随机
        return new RandomRule();
    }

}


```



#### 5） 主启动类添加@RibbonClient

```java
package com.smc.springcloud;

import com.smc.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @Date 2023/2/1
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}

```



#### 6）测试

### 3.2 负载均衡算法

* rest接口第几次请求数%服务器集群总数量=实际调用服务器位置下标，每次服务器重新启动后rest接口计数从1开始
* 源码：CAS，自旋锁

### 3.3 手写代码(cas+自旋锁)

* 实现接口

  ```java
  package com.smc.springcloud.lb;
  
  import org.springframework.cloud.client.ServiceInstance;
  
  import java.util.List;
  
  /**
   * @Date 2023/3/8
   * @Author smc
   * @Description:
   */
  public interface LoadBalancer {
   ServiceInstance instances(List<ServiceInstance> serviceInstances);
  }
  ```

* 实现类

  ```java
  package com.smc.springcloud.lb;
  
  import org.springframework.cloud.client.ServiceInstance;
  import org.springframework.stereotype.Component;
  
  import java.util.List;
  import java.util.concurrent.atomic.AtomicInteger;
  
  /**
   * @Date 2023/3/9
   * @Author smc
   * @Description:
   */
  @Component
  public class MyLB implements LoadBalancer{
      private AtomicInteger atomicInteger = new AtomicInteger(0);
  
      public final int getAndIncrement(){
          int current;
          int next;
          do {
              current = this.atomicInteger.get();
              next = current>=Integer.MAX_VALUE ? 0 : current+1;
          } while (!this.atomicInteger.compareAndSet(current,next));
          System.out.println("**next" + next);
          return next;
      }
      @Override
      public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
          int index=getAndIncrement()%serviceInstances.size();
  
          return serviceInstances.get(index);
      }
  }
  
  ```

* controller方法

  ```java
  @RequestMapping(method = RequestMethod.GET,value = "/payment/lbport")
  public String getPaymentLB(){
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    if (instances==null || instances.size()<=0){
      return null;
    }
    ServiceInstance serviceInstance = loadBalancer.instances(instances);
    URI uri = serviceInstance.getUri();
    return restTemplate.getForObject(uri+"/payment/lbport",String.class);
  }
  ```

# 十三、服务调用：OpenFeign

## 1、基本介绍

* 官网：https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/
* feign是一个声明式的Web服务客户端，让编写Web服务客户端变的非常容易，只需创建一个接口并在接口上添加注解即可。

### 1.1 Feign能干什么

* Feign旨在编写Java Http客户端变得更容易
* 前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用个，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步的封装，由于他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它（以前Dao接口上面标注Mapper注解，现在是一个微服务接口上面标注一个Feign注解即可），即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

### 1.2 Feign集成了Ribbon

* 利用Ribbon维护了Payment的服务列表信息，并且通过轮询实现了客户端的负载均衡。耳语Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用。

## 2、OpenFeign的使用步骤

### 2.1 新建Cloud-consumer-feign-order80

* Feign在消费端使用

### 2.2 POM

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  <!--引入openFeign-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
  </dependency>
</dependencies>
```

### 2.3 YML

```yaml
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order

eureka:
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```

### 2.4主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Date 2023/3/9
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}

```

### 2.5 服务接口

```java
package com.smc.springcloud.service;

import com.smc.springcloud.domain.CommonResult;
import com.smc.springcloud.domain.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Date 2023/3/9
 * @Author smc
 * @Description:
 */
@Service
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymenFeignService {
    @RequestMapping(method = RequestMethod.GET,value = "/payment/create")
    int create(Payment payment);
    @RequestMapping(method = RequestMethod.GET,value = "/payment/getPaymentById/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
    @RequestMapping(method = RequestMethod.GET,value = "/payment/lbport")
    String lbport();
}

```

### 2.6 业务类

* 业务逻辑接口+@FeignClient配置调用provider服务

* 新建PaymentFeignService接口并新增注解@FeignClient

* 控制层Controller

  ```java
  package com.smc.springcloud.controller;
  
  import com.smc.springcloud.domain.CommonResult;
  import com.smc.springcloud.domain.Payment;
  import com.smc.springcloud.service.PaymenFeignService;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.cloud.client.discovery.DiscoveryClient;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RequestMethod;
  import org.springframework.web.bind.annotation.RestController;
  import org.springframework.web.client.RestTemplate;
  
  import javax.annotation.Resource;
  import java.net.URI;
  import java.util.List;
  
  /**
   * @Date 2023/2/1
   * @Author smc
   * @Description:
   */
  @RestController
  @Slf4j
  @RequestMapping(value = "consumer")
  public class OrderController {
      @Value("${server.port}")
      private String serverPort;
      @Resource
      private PaymenFeignService paymenFeignService;
  
      @RequestMapping(method = RequestMethod.POST,value = "/payment/create")
      public CommonResult create( Payment payment){
          int result = paymenFeignService.create(payment);
          log.info("****插入结果："+result);
          if(result>0){
              return new CommonResult(200,"插入数据库成功:serverport:"+serverPort,result);
          }else{
              return new CommonResult(444,"插入数据库失败:serverport:"+serverPort,null);
          }
      }
  
      @RequestMapping(method = RequestMethod.GET,value = "/payment/get/{id}")
      public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
          CommonResult<Payment> paymentById = paymenFeignService.getPaymentById(id);
          return paymentById;
      }
  
      @RequestMapping(method = RequestMethod.GET,value = "/payment/lbport")
      public String getPaymentLB(){
          return paymenFeignService.lbport();
      }
  
  }
  
  ```

## 3、OpenFeign超时控制

### 3.1 在服务提供者写测试方法

```java
@RequestMapping(method = RequestMethod.GET,value = "timeout")
public String paymentFeignTimeout(){
  try {
    TimeUnit.SECONDS.sleep(3);
  } catch (InterruptedException e) {
    e.printStackTrace();
  }
  return serverPort;
}
```

### 3.2 openFeign服务接口

```java
@RequestMapping(method = RequestMethod.GET,value = "/payment/timeout")
String paymentFeignTimeout();
```

### 3.3 openFeign controller

```java
@RequestMapping(method = RequestMethod.GET,value = "/payment/timeout")
public String paymentFeignTimeout(){
  //openfeign-ribbon,客户端默认等待1秒钟
  String port = paymenFeignService.paymentFeignTimeout();
  return port;
};
```

### 3.4 设置超时时长

```yaml
feign:
  client:
    config:
      default:
        # 日志级别
        loggerLevel: full
        # 超时设置 2 秒超时
        connectTimeout: 2000
        readTimeout: 2000
  # 断路器
  circuitbreaker:
    enabled: true
```

## 4、OpenFeign日志打印功能

### 4.1 日志级别

* NONE：默认的，不显示任何日志
* BASIC：仅记录请求方法、URL、响应状态码及执行时间
* HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息
* FULL：除了HEADERS中定义的信息外，还有请求和响应的正文及元数据

### 4.2 配置日志bean

```java
package com.smc.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level fegnLoggerLevel(){
        return Logger.Level.FULL;
    }
}

```

### 4.3 yaml配置监听接口

```yaml
logging:
  level:
    com.smc.springcloud.service.PaymenFeignService: debug
```

### 4.4 后台日志查看

![image-20230311002040732](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303110020696.png)

# 十四、服务降级：Hystrix断路器（豪猪哥）

## 1、基础概念

### 1.1 分布式系统面临的问题

* 复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免的失败。

### 1.2 服务雪崩

* 多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其他的微服务，这就是的**“扇出”**。如果扇出的链路上某个微服务的调用该响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，即所谓的**雪崩效应**
* 对于高流量的应用来说，单一的后端可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序可能还会导致服务之间的延迟增加，备份队列，县城和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便耽搁依赖关系的失败，不能取消整个应用程序或系统。
* 所以，通常当你发现一个模块下的某个实例失败后，这时候这个模块依赖还会接收流量，然后这个又问题的问题模块还调用了其他的模块，这样就会发生级联故障，或者叫雪崩

### 1.3 Hystrix概念

* Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证子啊一个依赖出问题时的情况下，不会导致整体服务是比啊，避免级联故障，以提高分布式系统的弹性
* “断路器”是一种开关装置，当某个服务但愿发生故障后，通过断路器的故障监控（类似熔断保险丝），向调用官方返回一个预期的、可处理的备选响应（FallBack），而不是常见的等待或者抛出调用方法无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统的蔓延，乃至雪崩。

### 1.4 官网资料

* 地址：https://github.com/Netflix/Hystrix/wiki/How-To-Use

## 2、Hystrix重要概念

### 2.1 服务降级（fallback）

* 服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback
* 哪些情况会触发降级
  * 程序运行异常
  * 超时
  * 服务熔断出发服务降级
  * 线程池/信号量打满也会导致服务降级

### 2.2 服务熔断（break）

* 类比保险丝达到最大哦服务访问后，直接拒绝访问，拉闸断电，然后调用服务降级的方法并返回友好提示
* 即保险丝：服务降级->进而熔断->恢复调用链路

### 2.3 服务限流（flowlimit）

* 秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒中N个，有序进行。

## 3、hystrix案例

### 3.1 构建

#### 1）构建cloud-provider-hystrix-payment8001

#### 2) pom引入hystrix熔断包

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    <version>2.2.10.RELEASE</version>
  </dependency>
</dependencies>
```

#### 3） 配置yml

```yaml
server:
  port: 8001

spring:
  application:
    name: cloud-provider-hystrix-payment

eureka:
  client:
    #表示是否将自己注册进EurekaServer，默认为true
    register-with-eureka: true
    #是否将EurekaServer抓取已有的注册信息，默认为true。单点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetch-registry: true
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```

#### 4）启动类

```java
package com.smc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class PaymengHystrix8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymengHystrix8001.class,args);
    }
}

```

#### 5）service接口和实现类

```java
package com.smc.springcloud.service;

import org.springframework.stereotype.Service;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
public interface PaymentService {
    String paymengtInfo_OK(Integer id);
    String paymengtInfo_FAIL(Integer id);

}

```

```java
package com.smc.springcloud.service.impl;

import com.smc.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymengtInfo_OK(Integer id) {
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_OK,id:"+id;
    }

    @Override
    public String paymengtInfo_FAIL(Integer id) {
        //暂停几秒中
        Integer timeout=3;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_FAIL,id:"+id+"\t"+"超时"+timeout+"秒异常";
    }
}

```

#### 6）controller

```java
package com.smc.springcloud.controller;

import com.smc.springcloud.service.PaymentService;
import com.smc.springcloud.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "payment")
public class PaymentController {
    @Resource
    private PaymentService paymentServiceImpl;

    @Value("${server.port}")
    private String port;

    @RequestMapping(method = RequestMethod.GET,value = "/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentServiceImpl.paymengtInfo_OK(id);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/hystrix/fail/{id}")
    public String paymentInfo_FAIL(@PathVariable("id") Integer id){
        return paymentServiceImpl.paymengtInfo_FAIL(id);
    }
}

```

### 3.2 高并发测试

* 上节构建为平台，从正确->错误->降级熔断->恢复
* 上述在非高并发的情形下，还能勉强满足

#### 1）Jmeter压测

* 开起Jmeter请求超时接口
* 导致服务器压力大，所以请求变慢

#### 2） Jmeter压测结论

* tomcat默认的工作线程数被打满，没有多余的线程来分解压力和处理
* 上面还是服务提供者8001自己测试，加入此时外部的消费者80也来访问，那消费者只能干等，最终导致消费端80不满意，服务端8001直接被拖死

#### 3） 订单微服务调用支付服务

* 新建cloud-consumer-feign-hystrix-order80
* 测试会发现更加慢
  * 8001同一层次的其他接口服务被困死，因为tomcat线程池里面的工作线程已经被挤占完毕
  * 80此时调用8001，客户端访问响应缓慢，转圈圈
* 正是因为上述故障或不佳表现，才有我们的降级/容错/限流等技术诞生
* 问题：
  * 超时导致服务器变慢：超时不在等待
  * 出错（宕机或程序允信给出错）：出错要有兜底
* 解决
  * 对方服务超时了，调用者不能一直卡死等地啊，必须有服务降级
  * 对方服务宕机了，调用者不能一直卡死等地啊，必须有服务降级
  * 对方服务OK，调用者自己出故障或有自我要求（自己的等待时间小于服务提供者），自己降级处理

### 3.3 服务降级

#### 1） 降级配置

* @HystrixCommand

#### 2） 8001先从自身找问题

* 设置自身调用超时时间的峰值，峰值内可以正常运行，超过了需要有兜底的方法处理，作服务降级fallback

#### 3）服务侧fallback

* 业务类启用：@HystrixCommand报异常后如何处理

  * 一旦调用该服务方法失败并抛出错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的制定方法

  ```java
  @Override
  @HystrixCommand(fallbackMethod = "paymentInfo_FailHandler",commandProperties = {
   @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")})
  public String paymentInfo_FAIL(Integer id) {
    //暂停几秒中
    Integer timeout=5;
    //或 int age=10/0;异常都会调用降级fallback处理方法
    try {
      TimeUnit.SECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "线程池："+Thread.currentThread().getName()+" paymentInfo_FAIL,id:"+id+"\t"+"超时"+timeout+"秒异常";
  }
  @Override
  public String paymentInfo_FailHandler(Integer id) {
    return "线程池："+Thread.currentThread().getName()+" paymentInfo_FailHandler,id:"+id+"\t"+"😭";
  }
  ```

  

* 主启动类激活

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @Date 2023/3/11
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
//添加@EnableHystrix注解激活
@EnableHystrix
public class PaymentHystrix8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrix8001.class,args);
    }
}

```

#### 4）订单侧fallback

* controller配置

  ```java
  @RequestMapping(method = RequestMethod.GET,value = "/payment/hystrix/fail/{id}")
  @HystrixCommand(fallbackMethod = "paymentInfo_FailHandler",commandProperties = {
   @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000")})
  String paymengtInfo_FAIL(@PathVariable("id") Integer id){
    //或报错 int age=10/0;
    return hystrixService.paymentInfo_FAIL(id);
  }
  
  public String paymentInfo_FailHandler(Integer id) {
    return "线程池："+Thread.currentThread().getName()+" paymentInfo_FailHandler,id:"+id+"\t"+"😭";
  }
  ```

* 启动类添加注解@EnableHystrix

  ```java
  package com.smc.springcloud;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.cloud.netflix.hystrix.EnableHystrix;
  import org.springframework.cloud.openfeign.EnableFeignClients;
  
  /**
   * @Date 2023/3/11
   * @Author smc
   * @Description:
   */
  @SpringBootApplication
  @EnableFeignClients
  @EnableHystrix
  public class OrderHystrix80 {
      public static void main(String[] args) {
          SpringApplication.run(OrderHystrix80.class,args);
      }
  }
  
  
  ```

#### 5）全局降级DefaultProperties

* 目前问题

  * 每个业务方法对应一个兜底方法，代码膨胀
  * 统一和自定义的分开

* 解决方法

  * 每个方法配置一个（膨胀）的解决方式

    * feign接口系列

    * @DefaultProperties(defaultFallbacj=""):统一跳转到统一处理结果页面

    * controller配置

      ```java
      package com.smc.springcloud.controller;
      
      import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
      import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
      import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
      import com.smc.springcloud.service.HystrixService;
      import org.springframework.beans.factory.annotation.Value;
      import org.springframework.web.bind.annotation.PathVariable;
      import org.springframework.web.bind.annotation.RequestMapping;
      import org.springframework.web.bind.annotation.RequestMethod;
      import org.springframework.web.bind.annotation.RestController;
      
      import javax.annotation.Resource;
      
      /**
       * @Date 2023/3/11
       * @Author smc
       * @Description:
       */
      @RestController
      @RequestMapping(value = "consumer")
      @DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
      public class OrderHystrixController {
          @Resource
          private HystrixService hystrixService;
          @RequestMapping(method = RequestMethod.GET,value = "/payment/hystrix/ok/{id}")
          String paymengtInfo_OK(@PathVariable("id") Integer id){
              return hystrixService.paymentInfo_OK(id);
          }
          @RequestMapping(method = RequestMethod.GET,value = "/payment/hystrix/fail/{id}")
          @HystrixCommand
          String paymengtInfo_FAIL(@PathVariable("id") Integer id){
              int age =10/0;
              return hystrixService.paymentInfo_FAIL(id);
          }
      
          public String paymentInfo_FailHandler(Integer id) {
              return "线程池："+Thread.currentThread().getName()+" paymentInfo_FailHandler,id:"+id+"\t"+"😭";
          }
      
          //全局fallback
          public String payment_Global_FallbackMethod(){
              return "Global异常处理信息，请稍后再试，😭";
          }
      }
      
      ```

  * 跟业务逻辑混在一起（混乱）的解决方法

    * 测试：客户端段去调用服务端，碰上服务端宕机或关闭
    * 本次案例服务降级处理是在客户端80实现完成，与服务端8001没有关系，只需要为Feign客户端定义的接口添加一个服务降级处理的实现类即可实现解耦
    * 未来我们要面对的异常
      * 运行
      * 超时
      * 宕机

    * 根据cloud-consumer-feign-h ystrix-order80已经有的HystrixService接口，重新新建一个类（PaymentFallbackService）实现该接口，统一接口里面的方法进行异常处理

    * PaymentFallbackService类实现HystrixService接口

      ```java
      package com.smc.springcloud.service.impl;
      
      import com.smc.springcloud.service.HystrixService;
      import org.springframework.stereotype.Service;
      
      /**
       * @Date 2023/3/11
       * @Author smc
       * @Description:
       */
      @Service
      public class PaymentFallbackService implements HystrixService {
          @Override
          public String paymentInfo_OK(Integer id) {
              return "-----PaymentFallbackService fall back-paymentInfo_OK,😭";
          }
      
          @Override
          public String paymentInfo_FAIL(Integer id) {
              return "-----PaymentFallbackService fall back-paymentInfo_FAIL,😭";
          }
      }
      
      ```

    * HystrixService接口注解@FeignClient加配置fallback

      ```java
      @Service
      @FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
      public interface HystrixService {
       @RequestMapping(method = RequestMethod.GET,value = "/payment/hystrix/ok/{id}")
       String paymentInfo_OK(@PathVariable("id") Integer id);
       @RequestMapping(method = RequestMethod.GET,value = "/payment/hystrix/fail/{id}")
       String paymentInfo_FAIL(@PathVariable("id") Integer id);
      }
      ```

    * yml配置

      ```yaml
      #用于服务降级，在注解@FeignClient中添加fallback属性值
      feign:
        circuitbreaker:
          enabled: true
      ```

      

    * 客户端自己调用提示：故意关闭服务8001，此时服务端的provider已经down了，但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器。

### 3.4 服务熔断

#### 1）熔断是什么

* 熔断机制是应对雪崩效应的一种服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息
* 当检测到该节点微服务调用响应正常后，恢复调用链路
* 在Spring Cloud框架里，熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，当失败的调用到一定阙值，缺省是5秒内20次调用失败，就会启动熔断机制。熔断机制的注解是@HystrixCommand

#### 2）实操

* 论文：https://martinfowler.com/bliki/CircuitBreaker.html

  ![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303112206382.png)

* 修改cloud-provider-hystrix-payment8001

* PaymentServiceImpl配置

  ```java
  //服务熔断
  @Override
  @HystrixCommand(fallbackMethod = "paymentCircuitBreak_fallback",commandProperties = {
    @HystrixProperty(name="circuitBreaker.enabled",value="true"),//是否开启断路器
    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),//请求次数
    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),//时间窗口期
    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")})//失败率达到多少后跳闸
  public String paymentCircuitBreak(Integer id) {
    if (id<0){
      throw new RuntimeException("id不能为负数");
    }
    String uuid = IdUtil.simpleUUID();
    return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+uuid;
  }
  
  @Override
  public String paymentCircuitBreak_fallback(Integer id) {
    return "线程池："+Thread.currentThread().getName()+" paymentCircuitBreak_fallback,id:"+id+"\t"+"😭";
  }
  ```

* PaymentController

  ```java
  //服务熔断
  @RequestMapping(method = RequestMethod.GET,value = "/hystrix/ciruit/{id}")
  public String paymentCircuitBreaker(@PathVariable("id") Integer id){
    log.info("fail:"+id);
    return paymentServiceImpl.paymentCircuitBreak(id);
  }
  ```

* 重点测试：多次错误，然后慢慢正确，发现刚开始不满足条件，就算正确访问了地址也不能进行返回正确数据

#### 3）小总结

* 熔断类型

  * 熔断打开：请求不在进行调用当前服务，内部设置时钟一般为MTTR（平均故障处理时间），当打开时长达到设始终则进入半熔断状态
  * 熔断关闭：熔断关闭不会对服务进行熔断
  * 熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断。

* 官网断路器流程图

  ![截屏 2023-03-11 23.21.03.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303112322279.png)

* 断路器在什么情况下起作用

  ![image-20230311232246828](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303112322095.png)

  * 涉及到断路器的三个重要参数：快照时间窗，请求总数阙值，错误百分比阙值
    * 快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近10秒
    * 请求总数阙值：在快照时间窗内，必须满足请求总数阙值才有资格熔断。默认为20，意味者在10秒内，如果该hystrix命令的调用次数不足20次，即使所有的请求都超时或其他原因失败，断路器都不会打开。
    * 错误百分比阙值：当请求总数子啊快照时间窗内超过了阙值，比如发生了30次调用，如果在这30次调用中，有15次发生了潮水异常，也就是超过50%的错误百分比，在默认设定50%阙值的情况下，这时候就会将断路器打开。

* 断路器开启或关闭的条件

  * 当满足一定的阙值的时候（默认10内超过20个请求次数）
  * 当失败率达到一定的时候（默认10秒内超过50%的请求失败）
  * 到达以上阙值，断路器将会开启
  * 当开启的时候，所有请求都不会进行转发
  * 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发。如果成功，断路器会关闭，若失败，继续开启。重复4和5

* 断路器打开之后

  * 再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback。通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。
  * 原来的主逻辑要如何恢复呢？
    * 对于这个问题，hystrix也为我们实现了自动恢复功能
    * 当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间内，降级逻辑是临时成为主逻辑
    * 当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

* All配置：HystrixCommandProperties

### 3.5 服务限流：alibaba的Sentinel说明

## 4、Hystrix工作流程

* 官网：https://github.com/Netflix/Hystrix/wiki/How-it-Works

![img](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303120005702.png)

## 5、服务监控hystrixDashboard

### 5.1 概述

* 处理隔离依赖服务的调用之外，Hystrix还提供了准实时的调用监控(Hystrix Dashboard)，Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计暴毙哦啊和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。

### 5.2 仪表盘9001

#### 1）新建cloud-consumer-hystrix-dashboard9001

#### 2）POM

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <optional>true</optional>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>com.smc.springcloud</groupId>
  <artifactId>cloud-api-commons</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
<!--eureka2.0引入eurekaClent包-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!--引入openFeign-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
<!--引入hystrix-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
  <version>2.2.10.RELEASE</version>
</dependency>
<!--hystrix仪表板-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
  <version>2.2.10.RELEASE</version>
</dependency>
```

#### 3）yml

```yaml
server:
  port: 9001

spring:
  application:
    name: cloud-consumer-order


eureka:
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
#用于服务降级，在注解@FeignClient中添加fallback属性值
feign:
  circuitbreaker:
    enabled: true

hystrix:
  dashboard:
    proxy-stream-allow-list: "localhost"

```



#### 4）主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Date 2023/3/12
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboard9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboard9001.class,args);
    }
}

```

#### 5）所有provider服务提供类（8001/8002）都需要监控依赖配置

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



#### 6）启动9001该微服务后续将监控微服务8001

* http://localhost:9001/hystrix

![image-20230312002629805](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303120026925.png)

### 5.3 监控实战

#### 1）修改8001

* 新版本Hystrix需要在8001主启动类中制定监控路径

* 需要配置，要不然会报错：Unable to connect to Command Metric Stream.

  ```java
  package com.smc.springcloud;
  
  import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.boot.web.servlet.ServletRegistrationBean;
  import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
  import org.springframework.cloud.netflix.hystrix.EnableHystrix;
  import org.springframework.context.annotation.Bean;
  
  /**
   * @Date 2023/3/11
   * @Author smc
   * @Description:
   */
  @SpringBootApplication
  @EnableEurekaClient
  @EnableHystrix
  public class PaymentHystrix8001 {
      public static void main(String[] args) {
          SpringApplication.run(PaymentHystrix8001.class,args);
      }
  
      /**
       * 次配置是未来服务监控而配置，与服务容错本身无关，springcloud升级后的影响
       * ServletRegistrationBean因为sprinboot默认路径不是"/hystrix.stream"
       * 只要在自己的项目里配置上下面的servlet就可以了
       * @return
       */
      @Bean
      public ServletRegistrationBean getServlet(){
          HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
          ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(hystrixMetricsStreamServlet);
          servletRegistrationBean.setLoadOnStartup(1);
          servletRegistrationBean.addUrlMappings("/hystrix.stream");
          servletRegistrationBean.setName("HystrixMetricsStreamServlet");
          return servletRegistrationBean;
      }
  }
  
  
  ```

  ![image-20230312010045738](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303120100899.png)

![image-20230312010002351](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303120100656.png)

# 十五、服务网关：zuul2和gateway

## 1、概述简介

### 1.1 官网

* zuul 2.X：https://github.com/Netflix/zuul/wiki
* Gateway:https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/

### 1.2 作用

* Gateway是在Spring生态系统之上构建的API网关服务，基于Spring 5，Spring Boot2和Project Reactor技术。Gateway旨在提供一种简单而有效的方式来对API进行路由，以及提供一些强大的过滤器功能，例如：熔断、限流、重试等。

* SpringCloud Gateway是基于WebFlus框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty。
* Spring Cloud Gateway的目标提供统一的路由方式且基于Filter链的方式提供网关基本的功能，例如：安全，监控/指标，和限流。

### 1.3 网关位置

![截屏 2023-03-14 23.44.54.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303142345512.png)

### 1.4 为什么选择gateway

* Gateway是基于异步非阻塞上进行开发的，性能方面不需要担心。

* 特性

  * 基于Spring Framework5，Project Reactor和Spring Boot2.0进行构建
  * 动态路由：能够匹配任何请求的属性
  * 可以对路由制定Predicate（断言）和Filter（过滤器）
  * 继承Hystrix的断路器功能
  * 继承Spring Cloud服务发现功能
  * 易于编写的Predicate（断言）和Filter（过滤器）
  * 请求限流功能
  * 支持路径重写

* Zuul1.x模型

  * Springcloud中所继承的Zuul版本，采用的是tomcat容器，使用的是传统的Servlet IO处理模型
  * servlet由servlet container进行生命周期管理
    * container启动时构造servler对象并调用servlet init()进行初始化
    * container运行时接受请求你，并为每个请求分配一个线程(一般从线程池中获取空闲线程)然后调用service
    * container关闭时调用servlet destroy销毁servlet

  ![截屏 2023-03-15 00.01.06.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303150001997.png)

  * 上述的缺点
    * servlet是一个简单的网络IO模型，当请求进入servlet container时，servlet container就会为其绑定一个线程，在并发不高的场景下这种模型时适用的。但是一旦高并发（比如抽风用jemeter压测），线程数量会暴涨，而线程资源代价昂贵（上下文切换，内存消耗大）严重影响请求的处理时间。在一些简单业务场景下，不希望为每个request分配一个线程，只需要1个或几个线程就能应对极大的并发请求

* Gateway 模型

  * spring webflux
    * 在Servlet3.1之后又了异步非阻塞的支持。而WebFlux是一个典型非阻塞异步的框架，它的核心是基于Reactor的相关API实现的。相对于创痛的web框架来说，他可以运行在租入Netty，Undertow及支持Servlet3.1的容器上。非阻塞+函数式编程

## 2、三大核心概念

### 2.1 Route（路由）

* 路由是构建网关的基本模块，它由ID，目标URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由

### 2.2 Predicate（断言）

* 参考的是Java8的java.util.function.Predicate
* 开发人员可以匹配HTTP请求中的所有内容（例如请求头或请求参数），如果请求与断言相比配则进行路由

### 2.3 过滤

* 指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改

## 3、 总体

* web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制
* predicate就是我们的匹配条件；而filter就可以理解为一个无所不能的拦截器。有了这两个元素，再加上目标uri，就可以实现一个具体的路由了

## 4、入门配置

### 4.1 新建cloud-gateway-gateway9527

### 4.2 POM

* 不需要web和actuator包

```xml
<dependencies>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  <!--gateway-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
  </dependency>
</dependencies>
```

### 4.3 yml

```yaml
server:
  port: 9527

spring:
  application:
    name: cloud-gateway


eureka:
  instance:
    hostname: cloud-gateway-service
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```

### 4.4 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Date 2023/3/15
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class GateWay9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWay9527.class,args);
    }
}

```

### 4.5 9527网关做路由映射

* 不想暴露8001端口，希望在8001外面套一层9527

### 4.6 yml新增配置

```yaml
server:
  port: 9527

spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      routes:
        - id: payment_route #payment_route 路由的ID，没有固定规则但要求唯一，建议配置服务名
          uri: http://localhost:8001  #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**  #断言，路径相匹配的进行路由
        - id: payment_route2 #payment_route 路由的ID，没有固定规则但要求唯一，建议配置服务名
          uri: http://localhost:8001  #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**  #断言，路径相匹配的进行路由



eureka:
  instance:
    hostname: cloud-gateway-service
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```

### 4.7 测试

* http://localhost:8001/payment/lbport

* http://localhost:9527/payment/lbport

### 4.8 Gateway配置路由的两种方式

* 在yal中配置，详见以上步骤

* 代码中注入RouteLocator的Bean

  ```java
  package com.smc.springcloud.config;
  
  import org.springframework.cloud.gateway.route.RouteLocator;
  import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  /**
   * @Date 2023/3/16
   * @Author smc
   * @Description:
   *  配置一个id为route-name的路由规则，
   *  当访问http://localhost:9527/guonei时会转发到http://www.baidu.com
   */
  @Configuration
  public class GateWayConfig {
      @Bean
      public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilter) {
          RouteLocatorBuilder.Builder routes = routeLocatorBuilter.routes();
          routes.route("path_route_smc", r -> r.path("/").
                  uri("http://www.baidu.com/")).build();
          return routes.build();
      }
  }
  
  ```

## 5、GateWay配置动态路由

* 默认情况下Gateway会根据注册中心注册的服务列表，以注册中心上微服务名为路径创建动态路由进行转发，从而实现动态路由

### 5.1 YML配置

```yaml
server:
  port: 9527

spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_route #payment_route 路由的ID，没有固定规则但要求唯一，建议配置服务名
          #uri: http://localhost:8001  #匹配后提供服务的路由地址
          uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/getElementById/**  #断言，路径相匹配的进行路由
        - id: payment_route2 #payment_route 路由的ID，没有固定规则但要求唯一，建议配置服务名
          #uri: http://localhost:8001  #匹配后提供服务的路由地址
          uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lbport/**  #断言，路径相匹配的进行路由



eureka:
  instance:
    hostname: cloud-gateway-service
  client:
    #表示是否将自己注册金EurekaServer，默认为true
    register-with-eureka: false
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```



## 6、Predicate的使用

### 6.1 介绍

* Spring Cloud Gateway将路由匹配作为Spring WebFlux HandlerMapping基础架构的一部分
* Spring Cloud Gateway包括许多内置的Route Predicate工厂。所有这些Predicate都与HTTP请求的不同属性匹配。多个Route Predicate工厂可以尽心骨折
* Spring Cloud Gateway创建Route对象时，使用RoutePredicateFactory创建Predicate对象，Predicate对象可以赋值Route。Spring Cloud Gateway包括许多内置的Route Predicate Factories
* 所有这些谓词都匹配HTTP请求的不同属性。多种谓词工厂可以组合，并通过逻辑and。
* 官网：https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-cookie-route-predicate-factory

### 6.2 常用的Route Predicate

#### 1）After Route Predicate

```yaml
predicates:
- After=2023-03-18T16:09:42.734+08:00[Asia/Shanghai] #该路由在这个时间后生效
```

#### 2）Before Route Predicate

#### 3）Between Route Predicate

#### 4）Cookie Route Predicate

* 不带cookies访问
* 带上cookies访问
* Cookie Route Predicate需要两个参数，一个是Cookie name，一个是正则表达式。路由规则会通过获取对应的Cookie name值和正则表达式去匹配，如果匹配上就会执行路由，如果没有匹配上则不执行。

```yaml
predicates:
- Cookie=username,smc
```

* 请求

  ![image-20230318163205072](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303181632317.png)

  

#### 5）Header Route Predicate

* 两个参数：一个是属性名称和一个正则表达式，这个属性值和正则表达式匹配则执行

  ```yaml
  predicates:
  - Header=X-Request-Id,\d+ #请求头要有X-Request-Id属性，并且值为证书的正则表达式
  ```

  ```shell
  smc@linjianguodeMacBook-Pro ~ % curl http://localhost:9527/payment/lbport -H "X-Request-Id:123" 
  cloud-payment-service:8002%                                                     smc@linjianguodeMacBook-Pro ~ % curl http://localhost:9527/payment/lbport -H "X-Request-Id:123"
  cloud-payment-service:8001%                                                     smc@linjianguodeMacBook-Pro ~ % 
  ```

#### 6）Host Route Predicate

#### 7）Method Route Predicate

#### 8）Path Route Predicate

#### 9）Query Route Predicate

#### 10）总结

* Predicate就是为了实现一组匹配规则，让请求过来找到对应的Route进行处理

## 7、Gateway的Filter

### 7.1 介绍

* web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制。
* predicate就是我们的匹配条件
* 而filter，就可以理解为一个无所不能的拦截器。有了这两个元素，再加上目标uri，就可以实现一个具体的路由了。
* 路由过滤器用于修改进入的HTTP请求和返回的HTTP响应，路由过滤器只能制定路由进行使用
* Spring Cloud Gateway内置了多种路由过滤器，他们都由GatewayFilter的工厂类来产生
* 生命周期
  * pre
  * post

### 7.2 常用的filter

* GatewayFilter：https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gatewayfilter-factories

* GlobalFilter：https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#global-filters



### 7.3 自定义过滤器

* 两个主要接口

  * GlobalFilter
  * Ordered

* 作用

  * 全局日志记录
  * 统一网关鉴权

* 案例代码

  ```java
  package com.smc.springcloud.filter;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.cloud.gateway.filter.GatewayFilterChain;
  import org.springframework.cloud.gateway.filter.GlobalFilter;
  import org.springframework.core.Ordered;
  import org.springframework.http.HttpStatus;
  import org.springframework.stereotype.Component;
  import org.springframework.web.server.ServerWebExchange;
  import reactor.core.publisher.Mono;
  
  import java.util.Date;
  
  /**
   * @Date 2023/3/18
   * @Author smc
   * @Description:
   */
  @Component
  @Slf4j
  public class MyLogGateWayFilter implements GlobalFilter, Ordered {
      @Override
      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
          log.info("*****My GlobalLogFilter"+new Date());
          String uname = exchange.getRequest().getQueryParams().getFirst("uname");
          if (uname == null){
              log.info("*****用户名为null，非法用户");
              exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
              return exchange.getResponse().setComplete();
          }
          return chain.filter(exchange);
      }
  
      @Override
      public int getOrder() {
          return 0;
      }
  }
  
  ```

* 测试

  ![image-20230318170650913](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303181706034.png)

# 十六、分布式服务配置：Spring Cloud config

## 1、概述

### 1.1 分布式面临的配置问题

* 微服务意味着要将单体应用汇总的业务拆分成一个个字服务，每个服务的力度相对较小，因此系统中会出现大量的服务。由于每个服务都需要必要的配置细腻下才能运行，所以一套集中式的、动态的配置管理设施是必不可少的。

### 1.2 介绍

#### 1）是什么

* SpringCloud Config 为微服务架构中的微服务提供的集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。

  ![截屏 2023-03-19 16.31.40.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303191636461.png)

#### 2）怎么玩

* SpringCloud Config分为服务端和客户端两个部分
* 服务端成为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密/揭秘信息等访问接口
* 客户端则是通过制定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样有助与对环紧个配置进行版本管理，并且通过git客户端工具来方便的管理和访问配置内容。

#### 3）能干嘛

* 集中管理配置文件
* 不同环境不同配置，动态话的配置更新，分环境部署比如dev/test/prod/beta/release
* 运行期间动态调整配置，不在需要在每个服务部署的机器上编写配置文件，服务会像配置中心统一拉去配置自己的信息
* 当配置发生变化时，服务不需要重启即可感知到配置IE变化并应用新的配置
* 将配置信息以REST接口的形式暴露：post、curl访问刷新均可

### 1.3 与GitHub整合配置

* 由于SpringCloud Config默认使用gGit来存储配置文件（也有其他方式，比如支持SVN和支持本地文件），但最推荐的还是Git，而且使用的是http/https访问的形式

### 1.4 官网

* https://docs.spring.io/spring-cloud-config/docs/current/reference/html/

## 2、Config服务端配置与测试

### 2.1 用你自己的账号在GitHub上新建一个名为springcloud-config的新Repository

### 2.2 clone到本地

### 2.3 新建配置文件

### 2.4 新建module模块cloud-config-center3344

### 2.5 pom

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  <!--springcloud-config-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
  </dependency>
  <!--SpringCloud 2020.* 版本把bootstrap禁用了，导致在读取文件的时候读取不到而报错,需要重新导入bootstrap-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
  </dependency>
</dependencies>
```

### 2.6 yml

```yaml
server:
  port: 3344

spring:
  application:
    name: cloud-config-center #注册金Eureka服务器的微服务名
  cloud:
    config:
      server:
        git:
          uri: git@github.com:sumonton/springcloud-config.git #GitHub 上面git仓库名
          # 搜索目录
          search-paths:
            - springcloud-config
      #读取分支
      label: main


eureka:
  client:
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```

### 2.7 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Date 2023/3/19
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCenter4455 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter4455.class,args);
    }
}

```

### 2.8 windows下修改hosts文件，增加映射

### 2.9 测试通过Config微服务是否可以从Github上获取配置内容

* 启动3344

* 获取配置内容：http://config-3344.com:3344/main/config-dev.yml

### 2.10 配置读取规则

* 官网：https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_spring_cloud_config_server
* /{label}/{applicaiton}-{prfile}.yml
  * master分支
    * http://config-3344.com:3344/main/config-dev.yml
    * http://config-3344.com:3344/main/config-test.yml
    * http://config-3344.com:3344/main/config-prod.yml
  * dev分支
    * http://config-3344.com:3344/dev/config-dev.yml
    * http://config-3344.com:3344/dev/config-test.yml
    * http://config-3344.com:3344/dev/config-prod.yml

* /{applicaiton}-{prfile}.yml
  * 默认查找main下
* /{applicaiton}/{prfile}/{label}
  * 读取的json串，需要自己解析

## 3、Config客户端配置与测试

### 3.1 新建module模块cloud-config-client-3355

### 3.2 pom

```xml
<dependencies>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  <!--springcloud-config-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
  </dependency>

</dependencies>
```

### 3.3 bootstrap.yml

* 是什么
  * application.yml 是用户级的资源配置项
  * bootstrap.yml 是系统级，优先级更高
* Spring Cloud会创建一个“Bootstrap Context”，作为Spring应用的‘Applicaiton Context’的父上下文。初始化的是时候‘Bootstrap Context’负责从外部源加载配置属性冰解析配置。着两个上下文共享一个从外部获取的‘Environment’。
* ‘Bootstrap’属性有高优先级，默认情况下没他们不会被本地配置覆盖。‘Bootstrap context’和‘Application Context’有着不同的约定，所以新增一个‘Bootstrap.yml’，保证‘Bootstrap Context’和‘Applicaiton Context’配置的分离
* 要将Client模块下的application.yml文件改为bootstrap.yml，这是很关键的。因为bootstrap.yml是比applicaiton.yml先加载的。

```yaml
spring:
  cloud:
    config:
      label: main #分支名称
      name: config #配置文件名称
      profile: dev #读取后缀名称
      #上述三个综合：main分支上config-dev.yml的配置文件读取：http://config-3344.com:3344/main/config-dev.yml
      uri: http://config-3344.com:3344 #配置中心地址

```

### 3.4 修改config-dev.yml的配置并提交github

```yaml
server:
  port: 3355

spring:
  application:
    name: cloud-config-client #注册金Eureka服务器的微服务名


eureka:
  instance:
    hostname: cloud-config-client
  client:
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版
```



### 3.5 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Date 2023/3/20
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class Configcloud3355 {
    public static void main(String[] args) {
        SpringApplication.run(Configcloud3355.class,args);
    }
}

```

### 3.6 业务类

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2023/3/20
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "config")
public class ConfigClientController {
    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String applicaitonName;

    @RequestMapping(method = RequestMethod.GET,value = "/configinfo")
    public String getConfig(){
        return port+":"+applicaitonName;
    }
}

```

### 3.7 测试

![image-20230320005517945](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303200055162.png)

### 3.8 分布式配置的动态刷新问题

* 动态修改配置文件后，3344访问立刻刷新，但是3355访问无法立刻刷新，需要重启3355客户端服务才能刷新

  ![image-20230320010225070](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303200102253.png)

  ![image-20230320010237450](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303200102528.png)

## 4、Config客户端之动态刷新

* 避免每次更新配置都要都要重启客户端微服务3355

### 4.1 动态刷新

#### 1）修改3355模块

#### 2）POM中引入actuator监控

#### 3）修改yml，暴露监控端口

```yaml
spring:
  cloud:
    config:
      label: main #分支名称
      name: config #配置文件名称
      profile: dev #读取后缀名称
      #上述三个综合：main分支上config-dev.yml的配置文件读取：http://config-3344.com:3344/main/config-dev.yml
      uri: http://config-3344.com:3344 #配置中心地址
# 暴露监控端口
management:
  endpoints:
    web:
      exposure:
        include: "*"



```

#### 4）@RefreshScope业务类controller修改

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2023/3/20
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "config")
@RefreshScope
public class ConfigClientController {
    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String applicaitonName;

    @RequestMapping(method = RequestMethod.GET,value = "/configinfo")
    public String getConfig(){
        return port+":"+applicaitonName;
    }
}

```

#### 5）运维人员发送Post请求刷新3355,则3355配置动态刷新

```shell
smc@linjianguodeMacBook-Pro springcloud-config % curl -X POST "http://localhost:3355/actuator/refresh"
["config.client.version","eureka.instance.hostname","spring.application.name"]
```

### 4.2 还有什么问题

* 假设有多个微服务客户端 3355/3366/3377
* 每个微服务都要刷新一次post请求，手动刷新？
* 可否广播，一次通知，处处生效
* 只定点通知？

# 十七、消息总线：SpringCloud Bus

## 1、概述

* springcloud config无法实现分布式自动刷新配置功能，为了实现分布式自动刷新，SpringCloud Bus 配合SpringCloud Config使用可以实现配置的动态刷新。

### 1.1 是什么

* Bus支持两种消息代理：RabbitMQ和Kafka

![截屏 2023-03-21 00.03.57.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210005982.png)

* SpringCloud Bus 是用来将分布式系统的节点与轻量级消息系统链接起来的框架，它整合了Java的时间处理机制和消息中间件的功能

### 1.2 能干嘛

* Spring Cloud Bus能管理和传播分布式系统件的消息，就像一个分布式的执行器，可用于广播状态更改、事件推送等，也可以当作微服务间的通信通道

![截屏 2023-03-21 00.06.33.png](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202303210007581.png)

### 1.3 什么是总线

* 在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例舰艇和消费，所以称它为消息总线。在总线的各个实例，都可以方便的广播一些需要让其他连接在该主题上的实例都知道的消息。

### 1.4 基本原理

* configClient实例都监听MQ中同一个topic（默认是springcloudbus）。当一个服务刷新数据时，它会把这个信息放入到Topic中，这样其他舰艇同一个Topic的服务就能得到通知，然后去更新自身的配置。

## 2、RabbitMQ环境配置

### 2.1必须先具备良好的RabbitMQ环境先

* 演示广播效果，增加复杂度，再以3355位模板制作一个3366

### 2.2 设计思想

* 利用消息总线出发一个客户端/bus/refresh，从而刷新所有客户端的配置
* 利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置。√

### 2.3 给cloud-config-center-3344配置中心服务端添加消息总线支持

* POM

```xml
 <!--添加消息总线RabbitMQ支持 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

* yml

```yaml
spring:
	#rabbitMQ相关配置
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123

#rabbitmq相关配置，暴露bus刷新配置的端点
management:
	#登录rabbitmq不需要安全认证
  security:
    enabled: false
  endpoints:
    web:
      exposure:
        include: busrefresh

```



### 2.4 给cloud-config-client-3355客户端添加消息总线支持

* Pom

```xml
 <!--添加消息总线RabbitMQ支持 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

* yml

```yaml
spring:
	#rabbitMQ相关配置
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123
# 暴露监控端口
management:
	#登录rabbitmq不需要安全认证
  security:
    enabled: false
  endpoints:
    web:
      exposure:
        include: "*"
```

### 2.5 给cloud-config-client-3366客户端添加消息总线支持

* Pom

```xml
 <!--添加消息总线RabbitMQ支持 -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

* yml

```yaml
spring:
	#rabbitMQ相关配置
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123
# 暴露监控端口
management:
  security:
    enabled: false
  endpoints:
    web:
      exposure:
        include: "*"
```

### 2.6 运维工程师

* 修改Github上配置文件增加版本号
* 发送POST请求`curl -X POST "http://localhost:3344/actuator/busrefresh"`
* 一次发送，处处生效

### 2.7 动态刷新定点通知

* 不想全部通知，只想通知某个点

` curl -X POST "http://localhost:3344/actuator/busrefresh/config-client:3355"`

# 十八、消息驱动：SpringCloud Stream

## 1、概述

### 1.1 为什么引入

* 如果平台用到多种消息中间件，那么开发和运维人员难以维护。是否有一种新的技术，让我们不在关注具体MQ的细节，我们只需要用一种适配绑定的方式，各种MQ内切换。

* 屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型

### 1.2 官网

> https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/

### 1.3 SpringCloud Stream是什么

* 是一个构件消息驱动微服务的框架
* 应用程序通过inputs或者outputs来与Spring Cloud Stream中binder对象交互
* 通过我们配置来binding（绑定），而SpringCloud Stream的binder对象负责与消息中间件交互
* SpringCloud Steam为一些供应商的消息中间件铲平提供了个性化的自动化配置实现，引用了发布-订阅、消费组、分区三个核心概念
* 目前仅支持RabbitMQ、Kafka

### 1.4 设计思想

#### 1）标准MQ

* 生产者/消费者之间靠消息没接传递消息内容：Message
* 消息必须走特定的通道：消息通道MessageChannel
* 消息通道里的消息如何被消费呢，谁负责收发处理å

#### 2）使用SpringCloud

* stream凭什么能统一底层差异

  * 在没用绑定的这个概念的情况下，我们的SpringBoot应用要直接与消息中间件进行信息交互的时候，由于各消息中间件构件的初衷不同，他们的实现细节上会有较大的差异性
  * 通过定义绑定器作为中间层，完美地实现了应用程序与消息中间件细节之间的隔离
  * 通过应用程序暴露统一的Channel通道，是的应用程序不需要在考虑各种不同的消息中间件的视线
  * 通过定义绑定器Binder作为中间层，实现了应用程序与消息中间件细节之间的隔离

* Bingder

  * INPUT对应于消费者
  * OUTPUT对应于生产者

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306050003149.png" alt="image-20230605000332847" style="zoom:50%;" />

#### 3）Stream中消息通信方式遵循了发布-订阅模式（Topic主题进行广播）

* 在RabbitMQ就是Exchange
* 在Kakfa中就是Topic

## 2、Stream标准流程套路

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306050009715.png" alt="image-20230605000938622" style="zoom:50%;" />



* Binder：很方便连接中间，屏蔽差异
* Channel：通道，是队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介，通过Channel对队列进行配置
* Source和Sink：简单的可理解为参照对象是Spring Cloud Stream自身，从Stream发布消息就是输出，接收消息就是输入

### 2.1 常用注解

| 组成            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| Middleware      | 中间件，目前只支持RabbitMQ和Kafka                            |
| Binder          | Binder是应用于消息中间件之间的封装，目前实行了Kafka和RabbitMQ的Binder，通过Binder可以很方便的链接中间件，可以动态的改变消息类型（对应于Kafka的topic，RabbitMQ的exchange），这些都可以通过配置文件来实现 |
| @Input          | 注解表示输入通道，通过该输入通道接收到的消息进入应用程序     |
| @Output         | 注解表示输出通道，发布的消息将通过该通道离开应用程序         |
| @StreamListener | 监听队列，用于消费者的队列的消息接收                         |
| @EnableBinding  | 直行道channel和exchange绑定在一起                            |

## 3、消息驱动指生产者

### 3.1 POM

```xml
<dependencies>
  <!--添加消息总线RabbitMQ支持 -->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
    <version>4.0.1</version>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--eureka2.0引入eurekaClent包-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
    <version>4.0.3</version>
  </dependency>

</dependencies>
```

### 3.2 YML

```yaml
server:
  port: 8801

spring:
  application:
    name: cloud-stream-provider #注册金Eureka服务器的微服务名
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123
  cloud:
    stream:
      binders: #在此处要绑定rabbitmq的服务信息
        defaultRabbit: #表示定义的名称，用于binding整合
          type: rabbit #消息组件类型
      bindings: #服务的整合处理
        output: #这个名字是一个通道的名称
          destination: studyExchange #表示要使用的Exchange名称定义
          content-type: application/json #设置消息类型，本次为json，文本则设置"text/plain"
          binder: {defaultRabbit} #设置要绑定的消息服务的具体设置
eureka:
  instance:
    instance-id: send-8801.com #在信息列表是显示主机名称
    lease-renewal-interval-in-seconds: 2 #设置心跳的时间间隔（默认30秒）
    lease-expiration-duration-in-seconds: 5 #如果现在超过了5秒的间隔（默认90秒）
    prefer-ip-address: true #访问的路径变为IP地址
  client:
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版



```

### 3.3 主启动类

```java
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Date 2023/6/6
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamMQMain8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8801.class,args);
    }
}

```

### 3.4 业务类

* serviceImpl

```java
package com.smc.springcloud.service.serviceImpl;

import com.smc.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Date 2023/6/6
 * @Author smc
 * @Description:
 */
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("***serial:" + serial);
        return null;
    }
}

```

* controller

```java
package com.smc.springcloud.service.controller;

import com.smc.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2023/6/6
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping(value = "/stream")
public class SendMessageController {
    @Resource
    private IMessageProvider messageProviderImpl;

    @RequestMapping(method = RequestMethod.GET,value = "/sendMsg")
    public String sendMessage(){
        return messageProviderImpl.send();
    }
}

```

## 4、消息驱动消费者

### 4.1 其他同生产者，yml不同

```yaml
server:
  port: 8802

spring:
  application:
    name: cloud-stream-consumer #注册金Eureka服务器的微服务名
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123
  cloud:
    stream:
      binders: #在此处要绑定rabbitmq的服务信息
        defaultRabbit: #表示定义的名称，用于binding整合
          type: rabbit #消息组件类型
      bindings: #服务的整合处理
        input: #这个名字是一个通道的名称
          destination: studyExchange #表示要使用的Exchange名称定义
          content-type: application/json #设置消息类型，本次为json，文本则设置"text/plain"
          binder: {defaultRabbit} #设置要绑定的消息服务的具体设置
eureka:
  instance:
    instance-id: receive-8802.com #在信息列表是显示主机名称
    lease-renewal-interval-in-seconds: 2 #设置心跳的时间间隔（默认30秒）
    lease-expiration-duration-in-seconds: 5 #如果现在超过了5秒的间隔（默认90秒）
    prefer-ip-address: true #访问的路径变为IP地址
  client:
    service-url:
      #设置与Eureka Server交互的地址查询服务和注册服务都需要去依赖这个地址
      #      defaultZone: http://localhost:7001/eureka/  #单机版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka #集群版



```

### 4.2 业务类

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2023/6/8
 * @Author smc
 * @Description:
 */
@RestController
@EnableBinding(Sink.class)
public class ReceverMessageController {
    @Value("${server.port}")
    private String serverPort;
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("消费者1号，---收到的消息：" + message.getPayload() + "\t" + serverPort);
    }
}

```

## 5、分组消费与持久化

### 5.1 依照8802,新建8803#

### 5.2 运行后两个问题

#### 1）重复消费

* 目前8802/8803都收到了，存在重复消费的问题

* 导致原因

  * 默认分组group是不同的，组流水号不一样，被认为不同组，可以消费

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306080100845.png" alt="image-20230608010020693" style="zoom:50%;" />

* 解决方法：自定义配置分组，分为同一个组，即可解决

* 实际场景

  * 比如在如下场景中，订单系统我们做集群部署，都会从RabbitMQ中获取订单信息，那如果一个订单同时被两个服务获取到，那么就会造成数据错误，我们避免这种情况。这是我们就可以使用Stream中的消息分组来解决
  * 注意在Stream中处于同一个group中的多个消费者是竞争关系，能够保证消息只会被其中一个应用消费一次。不同组是可以全面消费的（重复消费），同一个组是竞争关系，只有其中一个可以消费

#### 2）消息持久化问题

* 若消费者未设置分组，当生产者发送的数据，消费者不在线时会丢失，若消费者设置分组，消费者启动后依然能够接受到消息。

### 5.3 使用分组解决重复消费问题和持久化问题

#### 1）原理

* 微服务应用放置于同一个group中，就能够保证消息只会被其中一个应用消费一次。不同的组是可以消费，同一个组会发生竞争关系，只有其中一个可以消费

#### 2）实现流程

* 自定义分组

```yaml
spring:
  application:
    name: cloud-stream-consumer8803 #注册金Eureka服务器的微服务名
  rabbitmq:
    host: 192.168.3.37
    port: 5672
    username: admin
    password: 123
  cloud:
    stream:
      binders: #在此处要绑定rabbitmq的服务信息
        defaultRabbit: #表示定义的名称，用于binding整合
          type: rabbit #消息组件类型
      bindings: #服务的整合处理
        input: #这个名字是一个通道的名称
          destination: studyExchange #表示要使用的Exchange名称定义
          content-type: application/json #设置消息类型，本次为json，文本则设置"text/plain"
          binder: {defaultRabbit} #设置要绑定的消息服务的具体设置
          group: smc2 #自定义组
```

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306080105533.png" alt="image-20230608010517181" style="zoom:50%;" />

* 将8802和8803自定义分配到相同组smc

# 十九、分布式请求链路跟踪：SpringCloud Sleuth

## 1、概述

## 1.1 为什么引用

* 在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的服务节点调用来协同产生最后的请求结果，每一个前段请求都会形成一个复杂的分布式服务调用链路，链路中的任何一环出现高延时或错误都会引起整个请求最后的失败。

## 2、是什么

* 官网：` https://spring.io/projects/spring-cloud-sleuth`
* Spring Cloud Sleuth 提供了一套完整的服务跟踪的解决方案
* 在分布式系统中提供追踪解决方案并且支持zipkin(监控)

## 2、搭建链路监控步骤

### 2.1 zipkin

#### 1）下载

* SpringCloud从F版已不需要自己构件Zipkin Server了，只需调用jar包即可

* 使用docker安装zipkin

#### 2）



#### 2）运行jar

#### 3）运行控制

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306082329924.png" alt="image-20230608232930826" style="zoom:50%;" />

* 表示一请求李兰路，一条链路通过Trace Id唯一标识，Span表示发起的请求信息，各span通过parent id关联起来

* 简化：一条链路通过Trace Id唯一标识，Span表示发起的请求信息，哥span通过parent id关联起来

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306082331291.png" alt="image-20230608233102030" style="zoom:50%;" />

* Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识
* span：表示调用链路来源，通俗的理解span就是一次请求信息

### 2.2 服务提供者

#### 1）cloud-provider-payment8001

#### 2）POM

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-zipkin</artifactId>
  <version>2.2.8.RELEASE</version>
</dependency>
```

#### 3）YML

```yaml
spring:
  application:
    name: cloud-payment-service
  zipkin:
    base-url: http://192.168.3.37:9411
  sleuth:
    #采样率值介于0到1之间，1表示全部采集
    sampler:
      probability: 1
```



#### 4）业务类PaymentController

```java
@RequestMapping(method = RequestMethod.GET,value = "/zipkin")
public CommonResult paymentZipkin(){
  return new CommonResult(200,"hi,im paymentZipkin erver fallbac");
}
```



### 2.3 服务消费者（调用方）

* Cloud-consumer-order80
* pom同上
* yaml同上
* 业务类

```java
@RequestMapping(method = RequestMethod.GET,value = "/payment/zipkin")
public CommonResult zipkin(){
  return restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin",CommonResult.class);
}
```



### 2.4 一次启动eureka7001/8001/80

### 2.5 打开浏览器访问：http://localhost:9411

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306090025670.png" alt="image-20230609002535455" style="zoom:50%;" />

# 二十、SpringCloud Alibaba 入门简介

## 1、为什么出现SpringCloud Alibaba

### 1.1 Spring Cloud Netflix 将不在开发新的组件

* SpringCloud 版本迭代比较快，因而出现了很多重大ISSUE都还来不及Fix就又推另一个Release了。进入维护模式意思就是目前已知以后一段时间Spring Cloud Netflix提供的服务和功能就这么多了，不在开发新的组件和功能了。以后将以维护Merge分支Full Request为主

## 2、 Spring Cloud alibaba 带来了什么

* 官网：` https://github.com/alibaba/spring-cloud-alibaba/blob/2.2.x/README-zh.md`
* 官网：` https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html`

## 3、能干啥

* 服务限流降级：默认支持Servlet、Feign、RestTemplate、Dubbo和RocketMQ限流降级功能的接入，可以在运行时通过控制台是实时修改限流降级规则，还支持查看限流降级Metrics监控
* 服务注册与发现：适配Spring Cloud服务注册与发现标准，默认集成了Ribbon的支持
* 分布式配置管理：支持分布式系统中的外部化配置，配置更改时自动刷新
* 消息驱动能力：基于Spring Cloud Stream为微服务应用构建消息驱动能力
* 阿里云对象存储：阿里云提供的海量、安全、低成本、高可靠的云存储服务。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。
* 分布式任务调度：提供秒级、精准、高可靠、高可用的定时（基于Cron表达式）任务调度服务。同时提供分布式的任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有Worker（schedulerx-client）上执行

# 二十一、SpringCloud Alibaba Nacos服务注册和配置中心

## 1、Nacos简介

* 为什么叫nacos：前四个字母分别为Naming和Configuration的前两个字母，最后的s位service

### 1.1 是什么

* 一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台
* Nacos就是注册中心+配置中心的组合（Eureka+Config+Bus）

### 1.2 能干嘛

* 替代Eureka做服务注册中心
* 替代Config做服务配置中心

### 1.3 官网

> Https://github.com/alibaba/Nacos

> https://nacos.io/zh-cn/

## 2、安装并运行Nacos

### 2.1 下载并解压

* 地址：` https://github.com/alibaba/nacos/releases/`

```shell
git clone git@github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bi

//启动
sh startup.sh -m standalone
//关闭
sh shutdown.sh
```

* 默认账号密码：nacos

## 3、nacos作为服务注册中心演示

### 3.1 官网文档

> https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html#_service_registrationdiscovery_nacos_discovery

### 3.2 基于Nacos的服务提供者

#### 1）新建Module

* 新建cloudalibaba-provider-payment9001

#### 2）POM

* 父POM

  ```xml
  <dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>2021.0.4.0</version>
    <type>pom</type>
    <scope>import</scope>
  </dependency>
  ```

  

* 本模块POM

  ```xml
  <dependencies>
    <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>com.smc.springcloud</groupId>
      <artifactId>cloud-api-commons</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
  ```

  

#### 3）YML

```yaml
server:
  port: 9001

spring:
  applicaiton:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.3.42:8848/nacos
        service: nacos


management:
  endpoints:
    web:
      exposure:
        include: '*'
```



#### 4）主启动

```java
package com.smc.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/6/19
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9001.class,args);
    }
}

```



#### 5）业务类

```java
package com.smc.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Date 2023/6/19
 * @Author smc
 * @Description:
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value="/nacos/{id}",method = RequestMethod.GET)
    public String getPayment(@PathVariable("id") Integer id){
        return "nacos registry,serverPort:"+ serverPort+"\t id"+id;
    }
}

```



#### 6）测试

* 启动9001

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306190119848.png" alt="image-20230619011932177" style="zoom:50%;" />

#### 7）参考9001新建9002和使用虚拟端口建立9011

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306190134579.png" alt="image-20230619013457824" style="zoom:50%;" />

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306190135110.png" alt="image-20230619013551711" style="zoom:50%;" />

### 3.3 基于Nacos的服务消费者

#### 1）新建Module

* Cloudalibaba-consumer-nacos-order83

#### 2）POM

```xml
<dependencies>
  <dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--高版本没有引入ribbon，需手动引入-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
  </dependency>
</dependencies>
```

#### 3）YML

```yaml
server:
  port: 83
  
spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.3.42:8848/nacos
        service: nacos-consumer
        
#消费者将要去访问的微服务名称（注册成功仅nacos的微服务提供者）
service-url:
  nacos-user-service: http://nacos-payment-provider
```

#### 4）主启动类

```java
package com.smc.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/6/19
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9002.class,args);
    }
}

```

#### 5）配置类

```java
package com.smc.springcloud.alibaba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Date 2023/6/19
 * @Author smc
 * @Description:
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
  	@LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```

#### 6）业务类

```java
package com.smc.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Date 2023/6/19
 * @Author smc
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping(value = "/consumer")
public class OrderNacosController {
    @Resource
    private RestTemplate restTemplate;
    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @RequestMapping(value="/payment/nacos/{id}")
    public String PaymentInfo(@PathVariable("id") Long id){
        return restTemplate.getForObject(serverURL+"/payment/nacos/"+id,String.class);
    }
}
```

### 3.4 服务注册中心对比

| 服务注册与发现框架 | CAP模型 | 控制台管理 | 社区活跃度        |
| ------------------ | ------- | ---------- | ----------------- |
| Eureka             | AP      | 支持       | 低（2.x版本闭源） |
| Zookeeper          | CP      | 不支持     | 中                |
| Consul             | CP      | 支持       | 高                |
| Nacos              | AP、CP  | 支持       | 高                |

* 据说Nacos在Alibaba内部有超过10万的实例运行，已经过了类似双十一等各种大型流量的考验
* C是所有节点在同一时间看到的数据是一致的；而A的定义是所有的请求都会收到响应
* nacos支持CP和AP的切换

## 4、nacos作为配置中心-基础配置

### 4.1 新建module

### 4.2 POM

```xml
<dependencies>
  <!--nacos-config-->
  <dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
  </dependency>
  <dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.smc.springcloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
  <!--高版本没有引入ribbon，需手动引入-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
  </dependency>
  <!--bootstrap配置引入-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
    <version>3.1.1</version>
	</dependency>
</dependencies>
```



### 4.3 YML

* Nacos同Springcloud-config一样，在初始化项目时，要保证先从配置中心进行配置拉取，拉取配置之后，才能保证项目的正常启动
* springboot中配置文件的加兹安是存在优先级顺序的，bootstrap优先级高于application

```yaml
#bootstrap.yml
server:
  port: 3377

spring:
  application:
    name: nacos-config-clent
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.3.42:8848/nacos
        service: nacos-client3377
      config:
        file-extension: yaml #指定yaml格式的配置
        server-addr: 192.168.3.42:8848/nacos #Nacos作为配置中心地址

# application.yml
spring:
  profiles:
    active: dev #表示开发环境
```



### 4.4 主启动

```JAVA
package com.smc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date 2023/6/28
 * @Author smc
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClientMain3377 {
    public static void main(String[] args) {
        SpringApplication.run(ClientMain3377.class,args);
    }
}

```



### 4.5 业务类

```java
package com.smc.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2023/6/28
 * @Author sm
 * @Description:
 */
@RestController
@RefreshScope //支持Nacos的动态刷新功能
@RequestMapping(value = "config")
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @RequestMapping(method = RequestMethod.GET,value = "info")
    public String getConfigInfo(){
        return configInfo;
    }
}

```



### 4.6 在Nacos中添加配置信息

* Nacos中的配置规则` ${prefix}-${spring.profile.active}.${file-extension}`
  * prefix默认为spring.application.name的值，也可以通过配置项spring.cloud.nacos.config.prefix来配置
  * spring.profile.active即为当前环境对应的profile，注意：当spring.profile.active为空是，对应的连接符-也将不存在，dataId的拼接格式变成 ` ${prefix}.${file-extension}`
  * 通过Spring Cloud原生注解@RefreshScope实现配置自动更新
* ` nacos-config-client-dev.yml`

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280047192.png" alt="image-20230628004736065" style="zoom:50%;" />

### 4.7 测试

### 4.8 自带动态刷新

## 5、nacos作为配置中心-分类配置

### 5.1 问题：多环境多项目管理

* 实际开发中，通常一个系统会准备
  * dev开发环境
  * test测试环境
  * prod生产环境
  * 如何保证指定环境启动时服务能正确读取到Nacos上相应环境的配置文件呢？
* 一个大型分布式微服务系统会有很多微服务子项目，每个微服务又都会有相应的开发环境、测试环境、预发环境、正式环境...，那怎么对这些微服务配置进行管理呢？

### 5.2 Nacos的图形化管理界面

* 配置管理

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280102574.png" alt="image-20230628010258166" style="zoom:50%;" />

* 命名空间

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280103636.png" alt="image-20230628010326262" style="zoom:50%;" />

### 5.3 Namespace+Group+Data ID三者关系？为什么这么设计？

* 类似Java里面的package名和类名
* 最外层的namespace是可以用与分区部署环境的，Group和DataID逻辑上区分两个目标对象

<img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280105975.png" alt="image-20230628010554717" style="zoom:50%;" />

* 默认情况：Namespace=public，Group=DEFAULT_GROUP，默认Cluster是DEFAULT
* nacos默认的命名空间是public，Namespace主要用来实现隔离
  * 比如说我们现在有三个环境：开发、测试、生产环境，我们就可以创建三个Namespace,不同的Namespace之间是隔离的
* Group默认是DEFAULT_GROUP，Group可以把不同的微服务划分到同一个分组里面去
* Service就是未付；一个Service可以包含多个Cluster（集群），Nacos默认Cluster是DEFAULT，Cluster是对指定微服务的一个虚拟划分。比方说为容灾，将Service微服务分别部署在了杭州机房和广州机房，这时就可以给杭州机房的Service微服务起一个集群名称（HZ），给广州机房的Service微服务起一个集群名称（GZ），还可以尽量让同一个机房的微服务互相调用，以提升性能
* 最后是Instance，就是微服务的实例

### 5.4 三个方案加载配置

#### 1）DataID方案

* 指定spring.profile.active和配置文件的DataID来是不同环境读取不同的配置文件

* 默认空间+默认分组+新建dev和test两个DataID

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280120785.png" alt="image-20230628012015400" style="zoom:50%;" />

* 通过spring.profile.active属性就能进行多环境下配置文件读取

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280120021.png" alt="image-20230628012054500" style="zoom:50%;" />

* 测试

#### 2）Group方案

* 通过Group实现环境分区

* 在nacos图形界面控制台上面新建配置文件DataID

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280126468.png" alt="image-20230628012644094" style="zoom:50%;" />

* bootstrap+application

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280127461.png" alt="image-20230628012746774" style="zoom:50%;" />

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280128726.png" alt="image-20230628012849379" style="zoom:50%;" />

#### 3）Namespace方案

* 新建dev/test的Namespace

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280132583.png" alt="image-20230628013207409" style="zoom:50%;" />

* 回到服务管理-服务列表查看

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280133865.png" alt="image-20230628013304476" style="zoom:50%;" />

* 按照域名配置填写

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280141544.png" alt="image-20230628014145412" style="zoom:50%;" />

* YML

  <img src="https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202306280140646.png" alt="image-20230628014052259" style="zoom:50%;" />

## 6、Nacos集群和持久化配置

### 6.1 管网说明

* 默认Nacos使用嵌入式数据库实现数据的存储。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的。为了解决这个问题,Nacos采用了集中式存储的方式来支持集群化部署，目前只支持MySQL的存储
* Nacos支持三种部署模式
  * 单机模式：用于测试和单机试用
  * 集群模式：用于生产环境，确保高可用
  * 多集群模式：用于多数据中心场景
* 单机模式支持mysql
  * 在0.7版本之前，在单机模式时nacos使用嵌入式数据库实现数据的存储，不方便观察数据存储的基本情况。0.7版本增加了支持mysql数据源能力，具体的操作步骤：
    * 安装mysql，版本要求5.6.5
    * 初始化mysql数据库，数据库初始化文件：nacos-mysql.sql
    * 修改conf、application.properties文件，增加支持mysql数据源配置（目前只支持mysql），添加  mysql数据源的url、用户名和密码。 

### 6.2 Nacos持久化配置解释 

*  Nacos默认自带的是嵌入式数据库derby

* derby到mysql切换配置步骤
  * mysql-schema.sql脚本，执行脚本
  * application.properties
* 启动Nacos，可以看到是个全新的空记录界面，以前是记录进derby

### 6.3 Linux版Nacos+MySQL生产环境配置

* 需要一个Nginx+3个nacos注册中心+1个mysql
* nacos下载Linux版

#### 配置步骤（重点）

* Linxu服务器上mysql数据库配置

  * 使用docker安装mysql
  * 执行mysql-schema.sql脚本

* Application.properties配置

  * 切换数据库

  ```properties
  spring.datasource.platform=mysql
   spring.sql.init.platform=mysql
  
  ### Count of DB:
   db.num=1
  
  ### Connect URL of DB:
   db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
   db.user.0=root
   db.password.0=123456
  
  ```

  

* Linxu服务器上nacos的集群配置cluster.conf

  * 梳理出3台nacos集器的不同服务端号
  * 复制出cluster.conf

  ```conf
  #it is ip
  #example
  #192.168.16.101:8847
  #192.168.16.102
  #192.168.16.103
  ## 使用hostname -i查询出来的ip
  192.168.3.43:8848
  192.168.3.43:3333
  192.168.3.43:4444
  ```

  

* 编辑Nacos的启动脚本startup.sh,使它能够接受不同的启动端口

  * 修改配置文件

    ```sh
    #修改前
    while getopts ":m:f:s:c:p:" opt
    do
        case $opt in
            m)
                MODE=$OPTARG;;
            f)
                FUNCTION_MODE=$OPTARG;;
            s)
                SERVER=$OPTARG;;
            c)
                MEMBER_LIST=$OPTARG;;
            p)
                EMBEDDED_STORAGE=$OPTARG;;
            ?)
            echo "Unknown parameter"
            exit 1;;
        esac
    done
    # start
    echo "$JAVA $JAVA_OPT_EXT_FIX ${JAVA_OPT}" > ${BASE_DIR}/logs/start.out 2>&1 &
    
    if [[ "$JAVA_OPT_EXT_FIX" == "" ]]; then
      nohup "$JAVA" ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
    else
      nohup "$JAVA" "$JAVA_OPT_EXT_FIX" ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
    fi
    
    echo "nacos is starting，you can check the ${BASE_DIR}/logs/start.out"
    
    
    #修改后
    while getopts ":m:f:s:c:p:t:" opt
    do
        case $opt in
            m)
                MODE=$OPTARG;;
            f)
                FUNCTION_MODE=$OPTARG;;
            s)
                SERVER=$OPTARG;;
            c)
                MEMBER_LIST=$OPTARG;;
            p)
                EMBEDDED_STORAGE=$OPTARG;;
            t)
                PORT=$OPTARG;;
            ?)
            echo "Unknown parameter"
            exit 1;;
        esac
    done
    
    # start
    echo "$JAVA $JAVA_OPT_EXT_FIX ${JAVA_OPT}" > ${BASE_DIR}/logs/start.out 2>&1 &
    
    if [[ "$JAVA_OPT_EXT_FIX" == "" ]]; then
      nohup $JAVA -Dserver.port=${PORT} ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
    else
      nohup $JAVA -Dserver.port=${PORT} "$JAVA_OPT_EXT_FIX" ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out 2>&1 &
    fi
    
    echo "nacos is starting，you can check the ${BASE_DIR}/logs/start.out"                                             
    ```

    

* Nginx的配置，由它作为负载均衡器

  ```conf
   upstream cluster {
          server 127.0.0.1:8848;
          server 172.0.0.1:3333;
          server 172.0.0.1:4444;
     }
      server {
          listen       5555;
          server_name  localhost;
  
          #charset koi8-r;
  
          #access_log  logs/host.access.log  main;
  
          location / {
              #root   html;
              #index  index.html index.htm;
              proxy_pass http://cluster;
          }
  
  ```

  

* 截止到此处，1个Nginx+3个nacos注册中心+1个mysql

  * 启动nginx

# 二十二、SpringCloud Alibaba Sentinel实现熔断与限流

## 1、什么是Sentinel

* 官网：` https://github.com/alibaba/Sentinel`
* 单独一个组件，可以独立出来
* 直接界面话的细粒度统一配置
* Sentinel分为两个部分
  * 核心库（Java客户端）不依赖任何框架/库，能够运行于所有Java运行时环境，同时对Dubbo/Spring Cloud等框架也有较好的支持
  * 控制台（Dashboard）基于Spring Boot开发，打包后可直接运行，不需要额外的Tomcat等应用容器

## 2、流控规则

* QPS直接失败
* 线程数直接失败
* 关联

## 3、降级规则

* 基本介绍
  * Sentinel熔断降级会在调用链路中某个资源出现不稳定状态时（例如调用超时或异常比例升高），对这个资源的调用进行限制，让请求快速失败，避免影响到其他的资源而导致级联错误
  * 当资源被降级后，在接下来的降级时间窗口内，对该资源的调用自动熔断
  * Sentinel的熔断器没有半开状态

* RT(平均相应时间，秒级)
  * 平均响应时间 超出阈值且在时间窗口内通过的请求>=5,两个条件同时满足后出发降级
  * 窗口期过后关闭断路器
  * RT最大4900
* 异常比例（秒级）
  * QPS >=5且异常比例（秒级统计）超过阈值时，触发降级；时间窗口结束后，关闭降级
* 异常数（分钟级）
  * 异常数超过阈值时，触发降级；时间窗口结束后，关闭降级

## 4、热点key限流

* 基本介绍：热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的Top K数据，并对其访问进行限制
  * 商品ID为参数，统计一段时间内最常购买的商品ID并进行限制
  * 用户ID为参数，针对一段时间内频繁访问的用户ID进行限制

## 5、系统规则

* 系统保护规则是从应用级别的入口流量进行控制，从单台机器的load、CPU使用率、平均RT、入口QPS和并发线程数等几个维度监控应用指标，让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性
* 系统保护规则是应用整体维度的，而不是资源维度的，并且仅对入口流量生效。入口流量指的是进入应用的流量（EntryType.IN），比如Web服务或Dubbo服务端接收的亲够，都属于入口流量
* 系统规则支持一下模式
  * Load自适应（仅对Linux/Unix-like机器生效）：系统的load作为启发指标，进行自适应系统保护。当系统load1超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护（BBR阶段）。系统容量又系统`  maxQps*minRt `估算得出。设定参考值一般` CPU cores *2.5`
  * 平均RT：当单台机器上所有入口流量的平均RT打到阈值即出发系统保护，单位是毫秒
  * 并发线程数：当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护
  * 入口QPS：当单台机器上所有入口流量的QPS打到阈值即出发系统保护

## 6、@SentinelResource

### 6.1 按资源名称限流+后续处理

### 6.2 按照Url地址限流+后续处理

### 6.3 上面兜底方案面临的问题

### 6.4 客户自定义限流处理逻辑

### 6.5 更多注解属性说明

## 7、服务熔断功能

### 7.1 sentinel整合ribbon+openFeign+fallback

### 7.2 Ribbon系列

### 7.3 Feign系列

### 7.4 熔断框架比较

## 8、规则持久化

* 是什么：一旦我们重启应用，sentinel规则将消失，生产环境需要将配置规则进行持久化
* 怎么做：将限流配置规则持久化进行nacos，只要刷新8401挪个rest地址，sentinel控制台的流控刘泽就能看到，只要nacos里面的配置不删除，针对8401上的sentinel上流控规则一直生效

# 二十三、SpringCloud Alibaba Seata 处理分布式事务

## 1、分布式事务问题

* 一个业务操作需要跨多个数据源或多个系统进行远程调用，就会产生分布式事务问题
* 单个应用被拆分成微服务应用，原来的三个模块被拆分成三个独立的应用，分别使用三个独立的数据源
* 业务操作需要调用三个服务来完成。此时每个服务内部的数据一致性由本地事务来保证，但是全局的数据一致性问题没法保证

## 2、Seata简介

### 2.1 是什么

* Seata是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务
* 官网地址：` http://seata.io/zh-cn/`

### 2.2 能干嘛

* 一个典型的分布式事务过程
  * 分布式事务处理过程的以ID+三组件模型
    * Transaction ID XID：全局唯一的事务ID
    * 3组件概念：
      * Transaction Coordinator（TC）：事务协调器，维护全局事务事务的运行状态，负责协调并驱动全局事务的提交或回滚
      * Transaction Manager（TM）：控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或全局回滚的决议
      * Resource Manager（RM）：控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚
  * 处理过程
    * TM向TC申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的XID
    * XID在微服务调用链路的上下文中传播
    * RM向TC注册分支事务，将其纳入XID对应全局事务的管辖
    * TM向TC发起针对XID的全局提交或回滚协议
    * TC调度XID下管辖的全部分支事务完成提交或回滚请求

### 2.3 去哪下

` http://seata.io/zh-cn/blog/download.html`

### 2.4 怎么玩

* 本地@Transactional
* 全员@GlobalTransaction：Seata的分布式交易解决方案

