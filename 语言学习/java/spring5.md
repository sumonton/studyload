### 1、Spring框架概述

* Spring是轻量级的开源的JavaEE框架
* Spring可以解决企业应用开发的复杂性
* Spring有两个核心部分：IOC和AOP
  * IOC：控制反转，把创建对象过程交给sping进行管理
  * Aop：面向切面，不修改源代码进行功能增强
* spring框架特点：
  * 方便接耦，简化开发
  * Aop编程支持
  * 方便程序测试
  * 方便集成各种优秀框架
  * 方便进行事物操作
  * 降低API开发

### 2、入门案例

* 四个基本包+commons的logging包

![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 09.29.12.png)

![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 09.53.21.png)

* 创建spring的配置文件，在配置文件配置创建的对象

  * 配置文件是xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--配置user对象-->
        <bean id="user" class="com.smc.demo1.User"></bean>
    </beans>
    ```

  * 进行测试代码

    ```java
    @Test
        public void testAdd(){
            //加载spring配置文件
            ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
            //获取配置创建的对象
            User user = context.getBean("user", User.class);
            System.out.println(user);
            user.add();
        }  
    ```

### 3、IOC容器

* IOC底层原理

  * （Inversion of Control，编写为IOC）控制反转，把对象创建和对象之间的调用过程，交给spring进行管理；使用IOC的目的：为了耦合度降低；入门案例就是IOC实现
  * 使用到三个方法：xml解析、工厂模式、反射
  * ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 10.11.57.png)

* IOC接口（BeanFactory）

  * IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
  * Spring提供IOC容器实现两种方式：（两个接口）
    * BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供开发人员进行使用，**加载配置文件时不会创建对象，在获取对象使用时才会去取创建**
    * ApplicationContext：BeanFactory接口的字接口，提供更多更强大的功能，一般由开发人员进行使用，**加载配置文件时候就会把在配置文件对象进行创建**
  * ApplicationContext接口有实现类
    * ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 10.20.53.png)

* 什么是Bean管理

  * Spring创建对象
  * Spring注入属性

* IOC操作Bean管理（基于xml）

  * ```xml
        <bean id="user" class="com.smc.demo1.User"></bean>
    
    ```

  * 在spring配置文件中，使用bean标签，标签里面添加对应属性，就可以实现对象创建

  * 在bean标签有很多属性，介绍常用的属性

    * id属性：唯一表示
    * class属性：类全路径（包类路径）

  * 创建对象时候，默认执行无参构造方法完成对象创建

  * 基于xml方式注入属性

    * DI：依赖注入，就是注入属性

      * 第一种：通过set注入

        ```xml
        <!-- 创建对象并通过set注入属性 -->
            <bean id="book" class="com.smc.demo2.Book">
                <property name="name" value="易筋经"></property>
                <property name="author" value="达摩老祖"></property>
            </bean>
        ```

        

      * 第二种：通过有参构造注入

        ```xml
        <!-- 创建对象并有参构造注入属性 -->
            <bean id="book1" class="com.smc.demo2.Book">
                <constructor-arg name="name" value="平凡的人生"></constructor-arg>
                <constructor-arg name="author" value="路遥"></constructor-arg>
            </bean>
        ```

    * p名称空间注入：可以简化基于xml中set注入属性

      ```xml
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:p="http://www.springframework.org/schema/p"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       <!-- 创建对象并set注入属性 -->
          <bean id="book2" class="com.smc.demo2.Book" p:name="九阳神功" p:author="无名氏"></bean>
      </beans>
      ```

    * xml注入其他类型属性

      * 字面量

        * null值

          ```xml
          <!-- 创建对象并set注入属性null值 -->
              <bean id="book3" class="com.smc.demo2.Book">
                  <property name="name" value="易筋经"></property>
                  <property name="author" value="达摩老祖"></property>
                  <property name="address">
                      <null/>
                  </property>
              </bean>
          ```

          

        * 属性值包含特殊符号

          ```xml
          <!-- 创建对象并set注入属性含有特殊符号
               1、用&lt和&gt转义
               2、用带特殊符号内容写到CDATA
               -->
              <bean id="book4" class="com.smc.demo2.Book">
                  <property name="name" value="易筋经"></property>
                  <property name="author" value="达摩老祖"></property>
                  <property name="address">
                      <value><![CDATA[<<南京>>]]></value>
                  </property>
              </bean>
          ```

      * 注入属性-外部bean

        ```xml
        <bean id="bookService" class="com.smc.demo2.service.BookService">
                <!-- 注入userDao
                name:类里面的属性名称
                ref属性：创建userDao对象bean标签id值
                -->
                <property name="bookDao" ref="BookDaoImpl"></property>
            </bean>
            <bean id="BookDaoImpl" class="com.smc.demo2.dao.BookDaoImpl"></bean>
        ```

        

      * 注入属性-内部bean

        * 一对多关系：部分和员工

          ```java
          /**
           * 雇员类
           */
          public class Emp {
              private String name;
              private String gender;
              private Dept dept;
          
              public void setDept(Dept dept) {
                  this.dept = dept;
              }
          
              public void setName(String name) {
                  this.name = name;
              }
          
              public void setGender(String gender) {
                  this.gender = gender;
              }
          }
          /**
           * 部门类
           */
          public class Dept {
              private String name;
          
              public void setName(String name) {
                  this.name = name;
              }
          }
          ```

          ```xml
          <bean id="emp" class="com.smc.demo3.bean.Emp">
                  <property name="name" value="lucy"></property>
                  <property name="gender" value="女"></property>
                  <property name="dept">
                      <bean id="dept" class="com.smc.demo3.bean.Dept">
                          <property name="name" value="安保部"></property>
                      </bean>
                  </property>
              </bean>
          ```

      * 注入属性-级联赋值

        ```xml
        <!--第一种-->
        <bean id="emp" class="com.smc.demo3.bean.Emp">
                <property name="name" value="lucy"></property>
                <property name="gender" value="女"></property>
                <!--级联赋值-->
                <property name="dept" ref="dept"></property>
            </bean>
            <bean id="dept" class="com.smc.demo3.bean.Dept">
                <property name="name" value="财务部"></property>
            </bean>
        
        <!--第二种-->
        <bean id="emp1" class="com.smc.demo3.bean.Emp">
                <property name="name" value="lucy"></property>
                <property name="gender" value="女"></property>
                <!--级联赋值-->
                <property name="dept" ref="dept"></property>
                <!--需要bean中内部bean实现get方法-->
                <property name="dept.name" value="技术部"></property>
            </bean>
        ```

    * 注入数组类型属性、List集合类型属性、注入Map集合类型属性

      ```xml
      <!--集合类型属性注入-->
          <bean id="stu" class="com.smc.demo4.Stu">
              <!--数组类型注入-->
              <property name="courses">
                  <array>
                      <value>java课程</value>
                      <value>python课程</value>
                  </array>
              </property>
              <!--list类型注入-->
              <property name="list">
                  <list>
                      <value>张三</value>
                      <value>小三</value>
                  </list>
              </property>
              <!--Map类型注入-->
              <property name="maps">
                  <map>
                      <entry key="JAVA" value="java"></entry>
                      <entry key="PYTHON" value="python"></entry>
                  </map>
              </property>
              <!--Set类型注入-->
              <property name="sets">
                  <set>
                      <value>MySQL</value>
                      <value>Oracle</value>
                  </set>
              </property>
      
          </bean>
      ```

    * 在集合中设置对象类型值

      ```xml
      <!--集合类型属性注入-->
          <bean id="stu" class="com.smc.demo4.Stu">
              <!--数组类型注入-->
              <property name="courses">
                  <array>
                      <value>java课程</value>
                      <value>python课程</value>
                  </array>
              </property>
              <!--list类型注入-->
              <property name="list">
                  <list>
                      <value>张三</value>
                      <value>小三</value>
                  </list>
              </property>
              <!--Map类型注入-->
              <property name="maps">
                  <map>
                      <entry key="JAVA" value="java"></entry>
                      <entry key="PYTHON" value="python"></entry>
                  </map>
              </property>
              <!--Set类型注入-->
              <property name="sets">
                  <set>
                      <value>MySQL</value>
                      <value>Oracle</value>
                  </set>
              </property>
              <!--注入对象类型值-->
              <property name="courseList">
                  <list>
                      <ref bean="course1"></ref>
                      <ref bean="course2"></ref>
                  </list>
              </property>
          </bean>
          <bean id="course1" class="com.smc.demo4.Course">
              <property name="name" value="JAVA"></property>
          </bean>
          <bean id="course2" class="com.smc.demo4.Course">
              <property name="name" value="PYTHON"></property>
          </bean>
      ```

      

    * 把集合注入部分提取出来

      * 在spring配置文件中引入名称空间util

      * 使用util标签提取list集合类型 

        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:util="http://www.springframework.org/schema/util"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
        
            <!--提取listji集合类型注入-->
            <util:list id="bookList">
                <value>易筋经</value>
                <value>九阳真经</value>
                <value>九阳神功</value>
            </util:list>
            <!--提取list集合类型的注入使用-->
            <bean id="book" class="com.smc.demo5.Book">
                <property name="list" ref="bookList"></property>
            </bean>
        
        </beans>
        ```

  * Spring有两种类型bean，一种普通bean，另一种工程bean（FactoryBean）

    * 普通bean：在配置文件中定义bean类型就是返回类型

    * 工厂bean：在配置文件定义bean类型可以和返回类型不一样

      1. 创建类，让这个类作为工厂bean，实现接口FactoryBean
      2. 实现接口里的方法，在实现的方法中定义返回的bean类型

      ```java
      public class MyFactoryBean implements FactoryBean<Course> {
      
          //定义返回bean
          @Override
          public Course getObject() throws Exception {
              Course course = new Course();
              course.setName("CPP");
              return course;
          }
      
          @Override
          public Class<?> getObjectType() {
              return null;
          }
      }
      
      @Test
          public void factoryTest(){
              ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
              Course myFactoryBean = context.getBean("myFactoryBean", Course.class);
              System.out.println(myFactoryBean.toString());
          }
      
      ```

      ```xml
      <bean id="myFactoryBean" class="com.smc.demo6.MyFactoryBean"></bean>
      ```

  * Bean的作用域

    * 在Spring里面设置创建bean实例是单实例还是多实例
    * 在Spring里面，在默认情况下是单实例对象
    * 如何设置单实例还是多实例（设置scope）
      * 默认值：singleton，表示单实例
      * prototype：多实例
      * request：每次创建对象会放在request中
      * session：每次创建对象都会放在session中

  * Bean的生命周期

    * 通过构造器创建bean实例（无参数构造）

    * 为bean的属性设置值和对其他bean引用（调用set方法）

    * 调用bean的初始化方法（需要进行配置）

    * bean可以使用（对象获取到了）

    * 当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

      ```java
      package com.smc.demo7;
      
      public class Order {
          private String oname;
          public Order() {
              System.out.println("第一步，执行无参构造方法");
          }
      
          public void setOname(String oname) {
              System.out.println("第二步，设置属性值");
              this.oname = oname;
          }
          //初始化方法
          public void initMethod(){
              System.out.println("第三步，初始化方法");
          }
          //销毁方法
          public void destroyMethod(){
              System.out.println("第五步，销毁bean方法");
          }
      }
      @Test
          public void testOrder(){
              ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
              Order order = context.getBean("order", Order.class);
              System.out.println("第四步，获取到bean："+order.toString());
              //手动销毁
              ((ClassPathXmlApplicationContext)context).close();
      
      
          }
      ```

      ```xml
      <bean id="order" class="com.smc.demo7.Order" init-method="initMethod" destroy-method="destroyMethod">
              <property name="oname" value="Java全集"></property>
          </bean>
      ```

    * bean的后置处理器(加起来共七步)

      * 在第三步初始化方法前后还各有一个过程

        * 把bean实例传递给bean的后置处理器方法
        * 把bean实例传递bean后置处理器的方法

      * 实现BeanPostProcessor接口

        ```java
        public class MyBeanPost implements BeanPostProcessor {
        
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("在初始化方法之前");
                return bean;
            }
        
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("在初始化方法之后");
                return bean;
            }
        
        }
        ```

        ```xml
        <!--配置后置处理器,配置后将会在所有bean生效-->
            <bean id="myBeanPost" class="com.smc.demo7.MyBeanPost"></bean>
        ```

  * xml的属性自动装配(很少使用)

    ```xml
    <!--实现自动装配
            bean标签属性autowire，自动装配
            autowires属性常用两个值：
                byName根据名称注入，bean的id值要和类中的属性名一致
                byType根据属性类型注入,相同类型的bean不能定义多个
        -->
        <bean id="emp" class="com.smc.autowire.Emp" autowire="byName">
    <!--        <property name="dept" ref="dept"></property>-->
    
        </bean>
        <bean id="dept" class="com.smc.autowire.Dept"></bean>
    ```

  * 引入外部属性文件

    * 直接配置数据库信息

      * 配置druid连接池

        ```xml
        <!--配置druid连接池-->
        
            <bean id="dept" class="com.alibaba.druid.pool.DruidDataSource">
                <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
                <property name="url" value="jdbc:mysql://localhost:8080/rookie"></property>
                <property name="username" value="root"></property>
                <property name="password" value="Smchen123"></property>
            </bean>
        ```

        

    * 引入外部属性文件配置数据库连接池

      * 配置文件

        ```properties
        driverClass=com.mysql.jdbc.Driver
        url=jdbc:mysql://localhost:8080/rookie
        username=root
        password=Smchen123
        ```

      * 属性文件引入spring配置文件汇总：引入context名称空间

        ```xml
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:context="http://www.springframework.org/schema/context"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
        
        ```

      * 在spring配置文件使用标签引入外部属性文件

        ```xml
        <!--引入外部属性文件-->
            <context:property-placeholder location="jdbc.properties"/>
            <!--配置druid连接池-->
            <bean id="dept" class="com.alibaba.druid.pool.DruidDataSource">
                <property name="driverClassName" value="${driverClass}"></property>
                <property name="url" value="${url}"></property>
                <property name="username" value="${username}"></property>
                <property name="password" value="${password}"></property>
            </bean>
        ```

        

* IOC操作Bean管理（基于注解）

  * 什么是注解

    * 注解是代码特殊标记，格式：@注解名称(属性名称=属性值，属性名称=属性值..)
    * 使用注解，注解作用在类上面，方法方面，属性上面
    * 使用注解目的：简化xml配置

  * Spring针对Bean管理创建对象提供注解

    * @Component
    * @Service
    * @Controller
    * @Repository

    **上面的四个注解功能是一样的，都可以用来创建bean实例**

  * 基于注解方式创建实例

    * 引入依赖![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 15.52.29.png)

    * 开启组件扫

      ```xml
      <!--开启组件扫描
              1、如果扫描多个包可以使用","隔开
              2、扫描多个包的上层目录
          -->
          <context:component-scan base-package="com.smc.demo8"></context:component-scan>
      ```

    * 创建类，在类上面添加创建对象注解

      ```java
      //在注解里面value属性值可省略，默认值类名称，首字母小写
      //四个注解效果一样
      @Component(value = "userservice") //类似<bean id="userservice" ..></bean>
      public class UserService {
          public void add(){
              System.out.println("UserService add...");
          }
      }
      
      ```

    * 测试

      ```java
      @Test
          public void test(){
              ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
              UserService userService = context.getBean("userService", UserService.class);
              userService.add();
          }
      ```

  * 开启组件扫描中的细节配置

    ```xml
    <!--
            use-default-filters="false"：表示不实用默认的过滤器，自己配置filter
        -->
        <context:component-scan base-package="com.smc.demo8" use-default-filters="false">
            <!--表示只扫描@Controller的注解的类-->
            <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>
    
        <context:component-scan base-package="com.smc.demo8">
            <!--表示除了@Controller的注解的类其他都扫描-->
            <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        </context:component-scan>
    ```

  * 基于注解方式实现属性注入

    * @AutoWired：根据属性类型注入

      1. 把service和dao对象创建，在service和dao类添加创建对象注解

      2. 在service注入dao对象，在service类添加dao类型属性，在属性上面使用注解

         ```java
         @Repository
         public class UserDaoImpl implements UserDao{
             @Override
             public void add() {
                 System.out.println("UserDaoImpl add...");
             }
         }
         
         //在注解里面value属性值可省略，默认值类名称，首字母小写
         @Service(value = "userService") //类似<bean id="userservice" ..></bean>
         public class UserService {
         
             //定义属性
             //不需要set方法
             //添加注入属性注解
             @Autowired
             private UserDao userDao;
             public void add(){
                 System.out.println("UserService add...");
             }
         }
         ```

         

    * @Qualifier：根据属性名称进行注入，这个注解的使用过河上面@Autowired一起使用

      ```java
      //在注解里面value属性值可省略，默认值类名称，首字母小写
      @Service(value = "userService") //类似<bean id="userservice" ..></bean>
      public class UserService {
      
          //定义属性
          //不需要set方法
          //添加注入属性注解
          @Autowired
          @Qualifier(value = "userDaoImpl2")//根据名称注入
          private UserDao userDao;
          public void add(){
              userDao.add();
              System.out.println("UserService add...");
          }
      }
      
      @Repository(value = "userDaoImpl2")
      public class UserDaoImpl2 implements UserDao{
          @Override
          public void add() {
              System.out.println("UserDaoImpl2 add...");
          }
      }
      ```

      

    * @Resource：可以根据类型注入也可根据名称注入.Resource是javax的注解，Spring官方不推荐使用。

      ```java
      //在注解里面value属性值可省略，默认值类名称，首字母小写
      @Service(value = "userService") //类似<bean id="userservice" ..></bean>
      public class UserService {
      
      
          @Resource(name = "userDaoImpl1")
          private UserDao userDao;
      
          public void add(){
              userDao.add();
              System.out.println("UserService add...");
          }
      }
      @Repository(value = "userDaoImpl1")
      public class UserDaoImpl1 implements UserDao{
          @Override
          public void add() {
              System.out.println("UserDaoImpl1 add...");
          }
      }
      ```

      

    * @Value：注入普通类型属性

      ```java
      //在注解里面value属性值可省略，默认值类名称，首字母小写
      @Service(value = "userService") //类似<bean id="userservice" ..></bean>
      public class UserService {
      
          @Value(value = "无敌")
          private String name;
      
          @Resource(name = "userDaoImpl1")
          private UserDao userDao;
      
          public void add(){
              userDao.add();
              System.out.println("UserService add..." + name);
          }
      }
      ```

  * 完全注解开发

    * 创建配置类，替代xml配置文件

      ```java
      @Configuration //作为配置类，替代xml配置文件
      @ComponentScan(basePackages = {"com.smc.demo8"})//扫描
      public class SpringConfig {
      }
      
      ```

      

    * 编写测试类

      ```java
      //测试
       @Test
          public void test2(){
              ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
              UserService userService = context.getBean("userService", UserService.class);
              userService.add();
          }
      ```

### 4、AOP

* **Aspect Oriented Programming**面向切面编程，利用AOP可以对业务逻辑各个部分进行隔离，从而使得业务逻辑个部分之间的耦合度降低，提高程序的课可冲用性，同时提高了开发的效率。

* 通俗描述：不改变源代码方式添加新功能到主干功能里面

* AOP底层原理

  * AOP底层使用动态代理（有两种情况）

    * 有接口情况，使用JDK动态代理.创建接口实现类的代理对象，增强类的方法

      ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 16.59.06.png)

    * 没接口情况，使用gCGLIB动态代理，创建子类的代理对象，增强类的方法

      ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-27 17.02.11.png)

* JDK动态代理

  * 使用jdk的proxy类

  * 使用gnewProxyInstance方法

    * 第一参数：类加载器
    * 第二参数：增强方法所在的类，这个类实现的接口，支持多个接口
    * 第三参数：实现这个几口InvocationHandler，创建代理对象，写增强方法

  * 代码

    * 创建接口，定义方法

      ```java
      public interface UserDao {
          int add(int a,int b);
          String update(String id);
      }
      
      ```

      

    * 创建实现类，实现方法

      ```java
      public class UserDaoImpl implements UserDao{
          @Override
          public int add(int a, int b) {
              System.out.println("add方法执行了。。。");
              return a+b;
          }
      
          @Override
          public String update(String id) {
              System.out.println("update方法执行了。。。");
              return id;
          }
      }
      
      
      ```

    * 使用Proxy类创建接口代理对象

      ```java
      public class JDKProxy {
          public static void main(String[] args) {
              //创建实现子类的代理对象
              Class[] interfaces = {UserDao.class};
              UserDao userDao = new UserDaoImpl();
              UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
              int result = dao.add(1, 2);
              System.out.println(result);
              System.out.println(dao.update("11111"));
      
          }
      
      }
      //创建代理对象代码
      class UserDaoProxy implements InvocationHandler{
          //把被代理对象传递过来
          private Object obj;
          public UserDaoProxy(Object obj) {
              this.obj = obj;
          }
      
          //增强的逻辑
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              //方法之前
              System.out.println("方法执行之前。。。。"+method.getName()+ " 传递的参数" + Arrays.toString(args));
              //被增强方法执行
              Object res = method.invoke(obj,args);
              //方法之后
              System.out.println("方法执行之后。。。"+res);
      
              return res;
          }
      }
      ```

* AOP术语

  * 连接点：类里面哪些方法可以被增强，这些方法称为连接点
  * 切入点：实际被真正增强的方法，被称为切入点
  * 通知（增强）：实际增强的逻辑部分称为通知；通知有多种类型：
    * 前置通知
    * 后置通知
    * 环绕通知
    * 异常通知
    * 最终通知
  * 切面：是一个动作
    * 把通知应用到切入点过程

* AOP操作（准备）

  * Spring框架一般基于AspectJ实现AOP操作

    * AspectJ：**不是Spring组成部分，独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作**

  * 基于AspectJ实现AOP操作

    * 基于xml配置文件实现
    * 基于注解方法实现（一般都是使用这个）

  * 在项目中引入AOP依赖  

  * 切入表达式

    * 切入点表达作用：知道对哪个类里面的哪个方法进行增强

    * 语法结构：execution([权限修饰符] [返回类型] [类全路径] [方法名称] [参数列表])

      * 对com.smc.dao.BookDao类里面的add进行增强

        > execution(\*com.smc.dao.BookDao.add(..))

      * 对com.smc.dao.BookDao包里面所有类，类里面所有方法进行增强

        > execution(\*com.smc.dao.\*.*(..))

*  AOP操纵（基于AspectJ注解）

  * 创建类，在类里面定义方法

    ```java
    public class User {
        public void add(){
            System.out.println("User add...");
        }
    }
    
    ```

    

  * 创建增强类（编写增强的逻辑）

    * 在增强类里面创建方法，让不同的方法代表不同通知类型

      ```java
      //增强类
      public class UserProxy {
          //前置通知
          public void before(){
              System.out.println("before...");
          }
      }
      ```

  * 进行通知的配置

    * 在spring配置文件中，开启注解扫描

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:context="http://www.springframework.org/schema/context"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                      http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                      http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
          <!--开启组件扫描
              1、如果扫描多个包可以使用","隔开
              2、扫描多个包的上层目录
          -->
          <context:component-scan base-package="com.sun.aop"></context:component-scan>
      
      </beans>
      ```

      

    * 使用注解创建User和UserProxy对象

      ```java
      @Component
      public class User {
          public void add(){
              System.out.println("User add...");
          }
      }
      
      //增强类
      @Component
      public class UserProxy {
          //前置通知
          public void before(){
              System.out.println("before...");
          }
      }
      
      ```

      

    * 在增强上面添加注解@Aspect

      ```java
      //增强类
      @Component
      @Aspect
      public class UserProxy {
          //前置通知
          public void before(){
              System.out.println("before...");
          }
      }
      
      ```

      

    * 在spring配置文件中开启生成代理对象

      ```xml
      <!--开启Aspect生成代理对象-->
          <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
      ```

  * 配置不同类型的通知

    * 在增强类的里面，在作为通知方法上面添加通知类型注解，使用切入点表达式配置 

      ```java
      //增强类
      @Component
      @Aspect
      public class UserProxy {
          //前置通知
          @Before(value = "execution(* com.sun.aop.User.add(..))")
          public void before(){
              System.out.println("before...");
          }
          //后置通知（返回通知）：在返回结果后执行，有异常不执行
          @AfterReturning(value = "execution(* com.sun.aop.User.add(..))")
          public void afterReturning(){
              System.out.println("afterReturning...");
          }
          //最终通知，不管什么情况都会执行
          @After(value = "execution(* com.sun.aop.User.add(..))")
          public void after(){
              System.out.println("after...");
          }
          //异常通知
          @AfterThrowing(value = "execution(* com.sun.aop.User.add(..))")
          public void afterThrowing(){
              System.out.println("afterThrowing...");
          }
          //环绕通知，执行前和执行后都执行
          @Around(value = "execution(* com.sun.aop.User.add(..))")
          public void around(ProceedingJoinPoint pjo) throws Throwable {
              System.out.println("around before...");
              //被增强的方法执行
              pjo.proceed();
              System.out.println("around after...");
          }
      }
      ```

  * 相同的切入点的抽取

    ```java
    //相同的切入点抽取
        @Pointcut(value = "execution(* com.sun.aop.User.add(..))")
        public void pointCut(){
    
        }
        //前置通知
        @Before(value = "pointCut()")
        public void before(){
            System.out.println("before...");
        }
    ```

  * 有多个增强类对同一个方法进行增强，设置增强优先级

    * 在增强类上面添加注解@Order(数字类型值)，数字类型值越小优先级越高

      ```java
      @Component
      @Aspect
      @Order(1)
      public class PersonProxy {
          //前置通知
          @Before(value = "execution(* com.sun.aop.User.add(..))")
          public void before(){
              System.out.println("Person before...");
          }
      }
      ```

* AOP操作（Aspectj配置文件，一般不会使用）

  * 创建两个类，增强类和被增强类，创建方法

  * 在spring配置文件中创建两个类对象

    

  * 在spring配置文件中配置切入点

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                    http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    
        <bean id="book" class="com.sun.aopxml.Book"></bean>
        <bean id="bookProxy" class="com.sun.aopxml.BookProxy"></bean>
        <!--配置AOP增强-->
        <aop:config>
            <!--切入点-->
            <aop:pointcut id="p" expression="execution(* com.sun.aopxml.Book.buy(..))"/>
            <!--配置切面-->
            <aop:aspect ref="bookProxy">
                <!--增强作用在具体的方法上-->
                <aop:before method="befor" pointcut-ref="p"/>
            </aop:aspect>
        </aop:config>
    </beans>
    ```

*  完全注解开发

  ```java
  @Configuration
  @ComponentScan(basePackages = {"com.sun.aop"})
  @EnableAspectJAutoProxy(proxyTargetClass = true)
  public class Config {
  }
  
  ```


### 5、JdbcTemplate

* 什么事JdbcTemplate

  * Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库操作

* 准备工作

  * 引入相关jar包

    > spring-jdbc-5.3.17.jar

    spring的jdbc依赖包

    > spring-tx-5.3.17.jar

    事务相关依赖包

    > spring-orm-5.3.17.jar

    引入其他数据库连接框架依赖

    ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/WX20220328-113222@2x.png)

  * 在spring配置文件配置数据库连接池

    ```xml
    <!--引入外部属性文件-->
        <context:property-placeholder location="jdbc.properties"/>
        <!--配置druid连接池-->
        <bean id="dept" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${driverClass}"></property>
            <property name="url" value="${url}"></property>
            <property name="username" value="${username}"></property>
            <property name="password" value="${password}"></property>
        </bean>
    ```

  * 配置JdbcTemplate对象，注入DataSouce

    ```xml
    <!--JdbcTemplate对象-->
        <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
            <!--注入dataSource-->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
    ```

  * 创建service类，创建dao类，在dao注入jdbcTemplate对象

    ```java
    /**
     * dao类
     */
    @Repository
    public class BookDaoImpl implements BookDao{
        @Autowired
        private JdbcTemplate jdbcTemplate;
    }
    
    /**
     * 服务类
     */
    @Service
    public class BookService {
    
        @Autowired
        private BookDao bookDao;
    }
    
    ```

* JdbcTemplate对数据库操作（添加）

  * 数据库建实体类对应表

  * 编写service和dao

    * 在dao进行数据库添加操作

    * 调用JdbcTemplate对象里面update方法实现添加操作

      ```java
        @Override
          public void add(Book book) {
              //创建sql语句
              String sql = "insert into t_book values(?,?,?)";
              //调用实现方法
              Object[] objects = {book.getUserId(), book.getUsername(), book.getuStatus()};
              int update = jdbcTemplate.update(sql, objects);
              System.out.println(update);
          }
      ```

  * 测试

    ```java
    @Test
        public void addTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
            BookService bookService = context.getBean("bookService", BookService.class);
            Book book = new Book();
            book.setUserId("1");
            book.setUsername("java");
            book.setuStatus("a");
    
            bookService.addBook(book);
        }
    ```

* 修改删除操作(同上使用update)

* 查询返回某个值

  * queryForObject()

    * 第一个参数：sql语句
    * 第二个参数：返回值类型

    ```java
    @Override
        public void countBook() {
            String sql = "select count(user_id) from t_book";
            int count = jdbcTemplate.queryForObject(sql,Integer.class);
            System.out.println(count);
    
        }
    ```

* 查询返回对象

  * JdbcTemplate查询返回对象

  * queryForObject()

    * 第一个参数：sql语句
    * 第二个参数：RowMapper：是接口，返回不同类型数据，使用这个接口里面实现类完成数据封装
    * 第三个参数：sql语句值

    ```java
     @Override
        public void findOne(String id) {
            String sql = "select * from t_book where user_id = ?";
            Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
            System.out.println(book.toString());
    
        }  
    ```

* 查询返回集合

  ```java
  @Override
      public void findAll() {
          String sql = "select * from t_book";
          List<Book> books = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
          books.stream().forEach(System.out::println);
      }
  ```

* 批量操作

  * batchUpdate(..)两个参数

    * 第一个参数：sql语句
    * 第二个参数：List集合，添加多条记录数据

    ```java
     @Override
        public void batchAdd(List<Object[]> batchs) {
            String sql ="insert into t_book values(?,?,?)";
            int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
            System.out.println(Arrays.toString(ints));
        }
    
    //测试
    @Test
        public void batchAddTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
            BookService bookService = context.getBean("bookService", BookService.class);
            List<Object[]> batchArgs = new ArrayList<>();
            Object[] obj1 = {"3","javaweb","cc"};
            Object[] obj2 = {"4","spring","dd"};
            Object[] obj3 = {"5","mysql","ee"};
            batchArgs.add(obj1);
            batchArgs.add(obj2);
            batchArgs.add(obj3);
            bookService.batchAdd(batchArgs);
        }
    ```

* 批量修改和删除

  ```java
  @Override
      public void batchUpdate(List<Object[]> batchs) {
          String sql ="update t_book set username = ?,ustatus=? where user_id=?";
          int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
          System.out.println(Arrays.toString(ints));
      } 
  
  @Override
      public void batchDelete(List<Object[]> batchs) {
          //创建sql语句
          String sql = "delete from t_book where user_id=?";
          int[] ints = jdbcTemplate.batchUpdate(sql, batchs);
          System.out.println(Arrays.toString(ints));
      }
  ```

### 6、spring事务管理

* spring事务管理介绍

  * 事务一般添加到JavaEE三层结构里面service层（业务逻辑层）

  * 在spring进行事务管理操作

    * 有两种方法：编程式事务管理和声明式事务管理

  * 声明式事务管理

    * 基于注解方式（使用）
    * 基于xml配置文件方式

  * 在spring进行声明式事务管理，底层使用AOP原理

  * 在Spring事务管理API

    * 提供一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类

      ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 16.51.52.png)

* 注解声明式事务管理

  * 在spring配置文件配置事务管理器

    ```xml
    <!--创建事务管理器-->
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <!--注入数据源-->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
    ```

  * 在spring配置文件中开启事务注解

    * 在spring配置文件中需要引入名称空间tx

      ```xml
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:context="http://www.springframework.org/schema/context"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xmlns:tx="http://www.springframework.org/schema/tx"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                      http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
      http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
      http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
      </beans>
      ```

    * 开启事务注解

      ```xml
      <!--开启事务注解-->
          <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
      ```

  * 在service类上面（获取service类里面方法上面）添加事务注解

    * @Transactional，这个注解添加到类上面，也可以添加方法上面

    * 如果把这个注解添加类上面，这个类里面所有的方法都添加事务

    * 如果把注解添加方法上面，为这个方法添加事务

      ```java
      @Service
      @Transactional
      public class AccountService {
          @Autowired
          private AccountDao accountDao;
          public void accountBalance(){
                  accountDao.reduceMoney();
                  accountDao.addMoney();
          }
      }
      
      ```

* 声明式事务管理参数配置

  * 在service类上面添加注解@Transactional，在这个注解里面可以配置相关参数![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 17.06.30.png)

    * propagation:事务传播行为

      * 多事务方法直接进行调用，这个过程事务是如何进行管理的

      * 七种传播行为

        * REQUIRED：如果有事务在运行，当前的方法就在这个事务内运行，否则就启动一个新的事务在自己的事务内运行

        * REQUIRED_NEW：当前的方法必须启动新事物，并在它自己的事务内运行如果有事务正在运行，应该将它挂起

          ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 17.18.40.png)

    * ioslation：事务隔离级别

      * 脏读，不可重复读，幻读
      * ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 17.29.37.png)

    * timeout：超过时间

      * 事务需要在一定时间内进行提交，如果不提交进行回滚
      * 默认值是-1，以秒为单位

    * readOnly：是否只读

      * readOnly默认值false，表示可以查询也可以增改删

    * rollbackFor：回滚

      * 设置查询哪些异常进行事务回滚

    * noRollbackFor：不回滚

      * 设置出现哪些异常不进行事务回滚

* 基于xml方式声明式事务管理

  * 在spring配置文件中配置

  * 配置通知

  * 配置切入点和切面

    ```xml
    <!--创建事务管理器-->
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <!--注入数据源-->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
        <!--开启事务注解-->
        <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
        <!--配置通知-->
        <tx:advice id="txAdvice">
            <!--配置事务参数-->
            <tx:attributes>
                <!--指定在那种规则的方法上执行事务-->
                <tx:method name="accountBalance" propagation="REQUIRED"/>
            </tx:attributes>
        </tx:advice>
        <!--配置切入点和切面-->
        <aop:config>
            <!--配置切入点-->
            <aop:pointcut id="pt" expression="execution(* com.transaction.service.AccountService.*(..))"/>
            <!--配置切面-->
            <aop:advisor advice-ref="txAdvice" pointcut-ref="pt"/>
    
        </aop:config>
    ```

* 完全注解声明式事务管理

  ```java
  @Configuration
  @ComponentScan(basePackages = {"com.transaction"})
  @EnableTransactionManagement //开启事务
  public class TxConfig {
      //创建数据库
      @Bean
      public DruidDataSource getDataSource(){
          DruidDataSource dataSource =new DruidDataSource();
          dataSource.setDriverClassName("com.mysql.jdbc.Driver");
          dataSource.setUrl("jdbc:mysql://localhost:3306/user_db");
          dataSource.setUsername("root");
          dataSource.setPassword("smchen123");
          return dataSource;
      }
  
      //创建JdbcTemplate模版对象
      @Bean
      public JdbcTemplate getJdbcTemplate(DataSource dataSource){
          //根据类型到IOC容器找到dataSource
          JdbcTemplate jdbcTemplate = new JdbcTemplate();
          //注入datasoruce
          jdbcTemplate.setDataSource(dataSource);
          return jdbcTemplate;
      }
      //创建事务管理器
      @Bean
      public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
          DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
          dataSourceTransactionManager.setDataSource(dataSource);
          return dataSourceTransactionManager;
      }
  
  }
  ```

### 7、Spring5框架新功能

* 整个Spring5框架代码基于java8，运行兼容java9，许多不建议使用的类和方法在代码库中删除

* spring5框架自带通用的日志封装

  * spring5已经移除了log4jconfigListener，官方建议使用Log4j2
  * spring5框架整合了Log4j2

  1. 引入jar包![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 20.53.03.png)
  2. 创建log4j2.xml配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
  <!--Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，可以看到log4j2内部各种详细输出-->
  <configuration status="INFO">
      <!--先定义所有的appender-->
      <appenders>
          <!--输出日志信息到控制台-->
          <console name="Console" target="SYSTEM_OUT">
              <!--控制日志输出的格式-->
              <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
      </console>
      </appenders>
      <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
      <!--root：用于指定项目的根日志，如果没有单独指定Logger，则会使用root作为默认的日志输出-->
      <loggers>
          <root level="info">
              <appender-ref ref="Console"/>
          </root>
      </loggers>
  </configuration>
  ```

* Spring5框架核心容器支持@Nullable注解

  * @Nullable注解可以使用在方法上面，属性上面，参数上面，表示方法返回可以为空，属性值可以为空，参数值可以为空。
  * 注解用在方法上，方法返回值可以为空
  * 注解使用在方法参数里面，方法参数可以为空
  * 注解用在属性上面，属性值可以为空

* Spring5核心容器支持函数式风格GenericApplicationContext注册bean

  ```java
  @Test
      public void test3(){
          //创建GenericApplicationContext对象
          GenericApplicationContext context = new GenericApplicationContext();
          //调用context方法进行对象注册
          context.refresh();
  //        context.registerBean(User.class,() -> new User());
          context.registerBean("user1",User.class,() -> new User());
          //获取spring中注册的对象
  //        User user = (User) context.getBean("com.transaction.domain.User");
  //        System.out.println(user);
          //获取spring中注册的对象
          User user1 = (User) context.getBean("user1");
          System.out.println(user1);
      }
  ```

* Spring5支持整合JUnit5

  * 整合Junit4

    * 引入spring相关针对测试的依赖

      > spring-test-5.3.17.jar

    * 创建测试类，使用注解方式完成

      ```java
      @RunWith(SpringJUnit4ClassRunner.class)//指定单元测试框架版本
      @ContextConfiguration("classpath:jdbc2.xml")//加载配置文件
      public class JTest4 {
          @Autowired
          private AccountService accountService;
          @Test
          public void test(){
              accountService.accountBalance();
          }
      }
      
      ```

      ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 22.03.11.png)

  * 整合Junit5

    * 第一步，引入JUnit5的依赖

    * 第二步，创建测试类，使用注解方式完成

      ```java
      @ExtendWith(SpringExtension.class)
      @ContextConfiguration("classpath:jdbc.xml")
      public class JTest5 {
          @Autowired
          private AccountService accountService;
          @Test
          public void test(){
              accountService.accountBalance();
          }
      }
      ```

      ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-28 22.17.00.png)

    * 使用复合注解替代上面两个注解

      ```java
      @SpringJUnitConfig(locations = "classpath:jdbc.xml")
      public class JTest5 {
          @Autowired
          private AccountService accountService;
          @Test
          public void test(){
              accountService.accountBalance();
          }
      }
      ```

* SpringWebFlux

  * SpringWebflux介绍

    ![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/WX20220328-222147@2x.png)

    * 是Spring5添加新的模块，用于web开发的，功能SpringMVC类似的，Webflux使用当前一种比较流行响应式编程出现的框架
    * 使用传统web框架，比如SpringMVC，这些基于Servlet容器，Webflux是一种异步非阻塞的框架，异步非阻塞的框架在Servlet3.1以后才支持，核心是基于Reactor的相关API实现的
    * 什么是异步非阻塞：异步和同步针对调用者，调用者发送请求无需等待回应就能去做其他事情即为异步，反之则为同步；阻塞和非阻塞针对被调用者，被调用者受到请求之后，昨晚请求之后才给出反馈就是阻塞，受到请求之后马上给出反馈然后再去做事情就是非阻塞。
    * Webflux特点：
      * 非阻塞：在有限资源下，提高系统吞吐量和身塑形，以Reactor为基础实现响应式编程
      * 函数式编程：Spring5框架基于java8，Webflux使用Java8函数式编程方式实现路由请求
    * 比较SpringMVC![](/Users/smc/Desktop/smc/语言学习/java/spring5/resources/截屏2022-03-29 10.20.01.png)
      * 两个框架都可以使用注解方式，都运行在Tomcat等容器中
      * SpringMVC采用命令式编程，Webflux采用异步响应式编程

  * 响应式编程（Reactive Programming）

    * 响应式编程是一种面向数据流和变化传播的编程范式。这意味着可以在编程语言中很方便地表达静态或动态的数据流，而相关的计算模型会自动地变化的值通过数据流进行传播（类似观察者）

    * Java8及其之前版本

      * 提供的观察者模式两个类Observer和Observable

        ```java
        public class ObserverDemo extends Observable {
            public static void main(String[] args) {
                ObserverDemo od = new ObserverDemo();
                //添加观察者
                od.addObserver((o,arg)->{
                   System.out.println("发生变化");
                });
        
                od.addObserver((o,arg)->{
                    System.out.println("手动被观察者通知，准备改变");
                });
                od.setChanged();//数据变化
                od.notifyObservers();//通知
            }
        }
        ```

    * Reactor实现

      * 响应式编程操作中，Reactor是满足Reactive规范框架

      * Reactor有两个核心类，Mono和Flux，这两个类实现接口Publisher，提供丰富操作符。Flux对象实现发布者，返回N个元素；Mono实现发布者，返回0或者1个元素。

      * Flux和Mono都是数据流的发布者，使用Flux和Mono都可以发出三种数据信号：元素值，错误信号，完成信号。错误信号和完成信号都是代表终止信号，终止信号用于告诉订阅者数据流结束了，错误信号终止数据流同时把错误信息传递给订阅者。

      * 代码掩饰Flux和Mono

        1. 引入依赖

        ```xml
        <dependency>
                    <groupId>io.projectreactor</groupId>
                    <artifactId>reactor-core</artifactId>
                    <version>3.2.9.RELEASE</version>
                </dependency>
        ```

        2. 代码演示Flux和Mono

           ```java
           public class TestReactor {
               public static void main(String[] args) {
                   //just方法直接生命
                   Flux.just(1,2,3,4);
                   Mono.just(1);
                   //其他的方法
                   Integer[] array = {1,2,3,4};
                   Flux.fromArray(array);
           
                   List<Integer> list = Arrays.asList(array);
                   Flux.fromIterable(list);
           
                   Stream<Integer> stream = list.stream();
                   Flux.fromStream(stream);
               }
           }
           ```

      * 三种信号的特点

        * 错误信号和完成信号都是终止信号，不能共存
        * 如果没有发送任何元素值，而是直接发送错误或者完成信号，表示空数据流
        * 如果没有错误信号，没有完成信号，表示无限数据流

      * 调用just或者其他方法只是声明数据流，数据流并没有发出，只有进行订阅之后才会触发数据流，不订阅啥都不会发生

        ```java
        Flux.just(1,2,3,4).subscribe(System.out :: println);
        Mono.just(1).subscribe(System.out :: println);
        ```

      * 操作符：对我们的数据流进行一道道操作，称为操作符

        * map：元素映射为新的元素
        * flatMap：元素映射为流，并合并为大流

  * SpringWebflux执行流程和核心API

    * 基于Reactor，默认容器是Netty，Netty是高性能的NIO框架，异步非阻塞框架
    * SpringWebFlux执行过程和SpringgMVC相似
      * 核心控制器：DIspatcherHandler，实现接口WebHandle。负责请求的的处理
        * HandlerMapping：匹配找哪个方法
        * HandlerAdapter：真正负责请求处理
        * HandlerResultHandler：响应结果处理
      * 实现函数式编程，两个接口：RouterFunction（路由处理）和HandlerFunction（处理函数）

  * SpringWebflux（基于注解编程模型）

    * 使用Mono和flux包裹元素
    * 与SpringMVC基本一致，但SpringMVC是同步阻塞的方式，基于SpringMVC+Servlet+Tomcat
    * SpringWebflux是异步非阻塞方式，基于SpringWebflux+Reactor+Netty

  * SpringWebflux（基于函数式编程模型）

    * 在使用函数式编程模型操作时候，需要自己初始化服务器
    * 久雨函数式编程模型时候，有两个核心接口：RouterFunction（实现路由功能，氢气转发个对应的handler）和HandlerFunction（处理请求生成响应的函数）。核心任务定义两个函数式接口的实现并且启动需要的服务器
    * SpringWebFlux情趣和响应不再是ServletRequest和ServletResponse，而是ServerRequest和ServerResponse
    * 使用WebClient调用，模拟浏览器请求