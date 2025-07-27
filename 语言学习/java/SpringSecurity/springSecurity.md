## 1、框架简介

### 1、概要

* 是spring家族中的成员
* 关于安全方面的两个主要区域是“认证”和“授权”，一般来说，Web应用的安全性包括**用户认证（Authentication）和用户授权（Authorization）两个部分**，这两袋内也是Spring Secutiry重要核心功能
  * 用户认证：系统认为用户是否能登录
  * 用户授权：系统判断用户是否有权限去做某些事情

### 2、历史

* 2003年开始Acegi，2007年更名Spring Security ，成为Spring组合项目

### 3、同款产品对比

* springSecurity
  * 和spring无缝结合
  * 全面的权限控制
  * 专门为Web开发和设计
    * 旧版本不能脱离web环境
    * 新版本对整个框架进行了分层抽取，分成了核心模块和Web模块。单独引入核心模块就可以脱离Web环境
  * 重量级
* Shiro：Apache旗下的轻量级空两集权限
  * 轻量级。Shiro主张的理念是把复杂的事情变简单。针对性能有更高要求的互联网应用有更好表现
  * 通用性
    * 好处：不能局限于Web环境，可以脱离Web环境使用
    * 缺陷：在Web环境下一些特定的需求需要手动编写代码定制
* 常见的技术栈
  * SSM+Shiro
  * Spring Boot/Spring Cloud + SpringSecurity



## 2、入门案例（SpringSecurity的helloworld）

### 1、创建SpringBoot工程

### 2、引入相关依赖

* 引入security依赖包

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```



### 3、编写一个Controller

* security的默认用户名是user
* 密码在每次启动时看到

### 4、springSecurity基本原理

#### 1、SpringSecurity过滤器链（重点三个）

##### 1、FilterSecurityInterceptor:是一个方法级的权限过滤器，基本为于过滤器的最底层

##### 2、ExceptionTranslationFilter：是个异常过滤器，用来处理在认证授权过程中跑出的异常

##### 3、UsernamePasswordAuthenticationFilter：对/login的post做拦截，判断用户名密码是否正确

#### 2、过滤器加载过程

##### 1、使用SpringSecurity配置过滤器

* DelegatingFilterProxy:加载所有过滤器，进行过滤

### 5、UserDeitailService接口讲解

* 当什么也没有配置的时候，账号和密码是由Security自动生成
* 如果需要自定义逻辑时，只需要实现UserDetailsService接口即可

> * 创建类继承UsernamePasswordAuthenticationFilter，重写三个方法
> * 创建类实现UserDetailService，编写查询数据过程，返回User对象，这个User对象时安全框架提供对象

### 6、PasswordEncoder接口

* 密码加密



## 3、Web权限方案

### 1、设置登录的用户名密码

#### a、通过配置文件

```xml
spring:
  security:
    user:
      name: smc
      password: smc123
```

#### b、通过配置类

#### c、自定义编写实现类(UserDetailService)

> 第一步 创建配置类，设置使用那个userDtailsService实现类
>
> 第二步 编写实现类，返回user对象，User对象有户名和密码和操作权限

### 1、自定义登录页面

### 2、不需要认证可以访问

### 3、角色权限访问控制

* hasAuthority()

  * 如果当前的主体具有指定的权限，则返回true，否则返回false

  ```java
  //1、在配置类设置当前访问地址有哪些权限
  //当前登录永辉，只有具有admins权限才可以访问这个路径
  .addMathcer("/test/index").hasAuthority("admins")
  //2、在UserDitailsService，把返回User对象设置权限
  <autedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins")
  ```

*  hasAnyAuthority方法

  * 如果是当前的主体有任何提供的角色（给定的作为一个逗号分隔的字符串列表）的话，就会返回true

  ```java
  //hasAnyAuthority
  .antMathchers("test/index").hasAnyAuthority("admins,manager")
  <autedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins")
  ```

* hasRole方法

  * 如果当前主体具有指定的角色，则返回true

  ```java
  .antMathchers("test/index").hasRole("sale")
    
  List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,Role_sale")
  ```

* hasAnyRole()方法

  * 表示用户具有任何一个条件都可以访问

### 4、自定义403页面

* 在配置类中进行配置，建立完静态页面后，设置路径

```java
http.execptionHandling().accessDeniedPage("/unauth.html")
```

### 5、注解使用

* @Secured

  * 判断是否具有具有角色，另外需要注意的是这里匹配的字符串需要添加前缀"ROLE_"

  * 使用注解需要先开启注解

    ```java
    @SpringBootApplication
    @EnableGlobalMethodSecurity(securedEnabled = true)//开启注解
    public class SecurityDemoApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SecurityDemoApplication.class, args);
        }
    
    }
    ```

  * 在控制器方法上添加注解

    ```java
    @RequestMapping("sayHello")
    @Secured({"ROLE_normal","Role_admin"})//在controller的方法上添加注解
    public String sayHello(){
      return "hello security";
    }
    ```

  * 在userDetailService设置用户角色

* PreAuthorize

  * 注解适合进入方法前的权限验证，@PreAuthorize可以将登陆用户的roles/permission参数传到方法中

  * 使用注解需要先开启注解

    ```java
    @EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true)
    public class SecurityDemoApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(SecurityDemoApplication.class, args);
        }
    
    }
    ```

  * 在controller方法上添加注解

    ```java
    @PreAuthorize("hasAnyAuthority('admins')")
    public String sayHello(){
      return "hello security";
    }
    ```

* @PostAuthorize

  * 在方法进行后进行权限验证，适合验证带有返回值权限

* @PostFilter

  * 权限验证之后对返回的数据进行过滤，留下用户名是admin1的数据

  * 表达中的filterObject饮用是方法返回值List中的某一个元素

    ```java
    @PostFilter("filterObject.username == 'admin1'")//只返回了用户名为admin1的数据
    public ArrayList<UserInfo> sayHello(){
      ArrayList<UserInfo> list = new ArrayList<UserInfo>();
      list.add(new UserInfo(1l,"admin1","20"),"33");
      list.add(new UserInfo(2l,"admin2","20"),"44");
      return list;
    }
    ```

* @PreFilter
  * 进入控制器之前对数据进行过滤（即对传入的参数进行过滤）

### 6、用户注销功能

* 在配置类中添加退出映射地址

  ```java
  http.logout().logoutUrl("logout").logoutSuccessUrl("test/hello").permitAll();
  ```

  ```html
  <a href="/logout">退出</a>
  ```

### 7、基于数据库实现记住我

* 基本原理![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-09 16.45.19.png)
* 具体流程![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-09 17.17.10.png)
* 具体实现
  * 创建数据库表persistent_logins
  * 配置类,注入数据源，配置数据库操作
  * 配置类中配置自动登录
  * 在登录也中添加复选框（名称必须为remember-me）


### 8、CSRF理解

* 跨站请求伪造（英语：Cross-site request forgery），也被称为one-click attack和session riding，通常缩写为CSRF或者XSRF，是一种挟制用户在当前已登录的Web应用程序上执行非本意的操作的攻击方法。跟跨网站脚本（XSS）相比，XSS利用利用的是用户对指定网站的信任，CSRF利用的是网站对用户网页浏览器的信任
* 跨站请求攻击，简单的说，是攻击者通过一些技术手段欺骗用户的浏览器去访问一个自己曾经认证过的网站并运行一些操作（如发邮件，发消息，设置财产操作如转账和购买商品），由于浏览器曾经认证过，所以被访问网站会认为是真正的用户操作而去运行。这利用了web中用户身份验证的一个漏洞：简单的身份验证只能保证请求发自某个用户的浏览器，却不能保证请求本身是用户自愿发出的。
* 从Spring Security4.0开始，默认情况下会启用CSRF保护，以防止CSRF攻击应用程序，Spring Security CSRF会针对PATCH，POST，PUT和DELETE方法进行保护
* spring Security4.0实现CSRC防护原理![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-09 17.53.05.png)

## 4、微服务权限方案

### 1、什么是微服务

* 微服务的由来
  * 微服务最早有Martin Fowler与James Lewis于2014年共同提出，微服务架构风格是一种使用一套小服务来开发耽搁应用的方式途径，每个服务运行在自己的进程中，并使用轻量级极致通信，通常是HTTP API，这些服务基于业务欧能力构建，并能够通过自动化部署极致来独立部署，这些服务使用不同的变成语言实现，以及不同数据存储技术，并保持最低限度的几种水管理
* 微服务优势
  * 微服务每个模块就相当于一个单独的项目，代码量明显减少，遇到问题也相对来说比较好解决
  * 微服务每个模块都可以使用不同的存储方式（比如有的用redis，有的用mysql等），数据库也是单个木块对应自己的数据库
  * 微服务的每个模块都可以使用不同的开发技术，开发模式灵活
* 微服务的本质
  * 微服务，关键其实不仅仅是微服务本身，而是系统要提供一套基础的架构，这种高架构是的微服务可以独立的部署、运行、升级，不仅如此，这个系统高架构还让微服务与微服务之间的结构上“松耦合”，而在功能上则表现为一个统一的整体。这种所谓的“统一的整体”表现出来的统一的风格的界面，统一的权限管理，统一的安全策略，统一的上线过程，统一的日志和审计方法，统一的调度方式，统一的访问入口等等。
  * 微服务的目的是有效的拆分应用，实现敏捷开发和部署

### 2、微服务认证

#### a、认证授权过程分析

* 如果是基于session，那么Spring-security会对cookie里的sessionid进行解析，找到服务器存储的session信息，然后判断当前用户是否符合请求的需求
* 如果是token，则是解析出token，然后将当前请求加入到Spring-security管理的权限信息中去![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-09 18.24.41.png)

#### b、案例

* 技术栈

![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-10 09.52.43.png)

* 搭建流程![](/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-10 10.00.07.png)

* 目录介绍

   <img src="/Users/smc/Desktop/smc/语言学习/java/SpringSecurity/resources/截屏2022-07-10 10.27.47.png" style="zoom:80%;" />

  * All_parent:在父工程pom文件，定义依赖版本
  * service_base：编写使用工具类，比如MD5加密等等
  * spring_security:springSecurity相关配置
  * gate_way:配置gateway网关
  * service_acl:实现权限管理功能代码

### 2、微服务认证和授权实现过程

### 3、完成基于SpringSecurity认证授权案例



## 5、原理总结

