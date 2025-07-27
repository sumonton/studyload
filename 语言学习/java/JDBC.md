### 1、JDBC概述

* 数据的持久化

  * 持久化：把数据保存到可掉电式存储设备中以供之后使用。大多数情况下，特别是企业级应用，数据持久化意味着将内存中的数据保存到硬盘上加以“固化”，而持久化的实现过程大多通过各种关系数据库来完成

* Java中的数据存储技术

  * JDBC直接访问数据库
  * JDO（JAVA Data Object）技术
  * 第三方O/R工具，如Hibernate，Mybatis等

  * JDBC是java访问数据库的基石，JDO、Hibernate、MyBatis等只是更好的封装了JDBC。

* JDBC（JAVA Database Connectivity）:是一个**独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口**（一组API），定义了用来访问数据库的标准Java类库，（**java.sql，javax.sql**）使用这些类库可以以一种标准的方法、方便地访问数据库资源

  * JDBC为了访问不同的数据库提供了一种统一的路径，为开发者屏蔽了一些细节问题
  * JDBC的目标是使java程序员使用JDBC可以连接任何提供JDBC驱动程序的数据库系统，这样就使得程序员无需对特定的数据库系统的特点有过多的了解，从而大大简化和加快了开发过程。
  * ![](/Users/smc/Desktop/smc/语言学习/java/resource/img/截屏2022-03-19 19.01.48.png)

* JDBC的体系结构

  * JDBC接口（API）包括两个层次

    * 面向应用的API：Java API，提供抽象接口，供应用程序开发人员使用（连接数据库，执行SQL语句，获得结果）
    * 面结数据库的API：Java Driver API，供开发商开发数据库驱动程序用

    > ODBC（Open Database Connectivity ，开放式数据库连接）：是微软在Windows平台下推出的。使用者在程序中只需要调用ODBC API，由ODBC驱动程序将调用转换成为特定数据库的调用请求

### 2、获取数据库连接

* 要素一:Dirver接口实现类

  * java.sql.Driver 接口是所有JDBC驱动需要实现的接口，这个接口是提供给数据库厂商使用的，不同数据库厂商提供不同的实现。
  * 在程序中需要直接去访问Driver接口的类，而是由驱动管理器类（jdava.sql.DriverManager）去调用这些Driver实现

* 要素二：URL

  * JDBC URL用于表示一个被注册的驱动程序，驱动程序管理器通过这个URL选择正确的驱动程序，从而建立到数据库连接
  * JDBC URL的标准由三个部分组成，各部分间用冒号分隔。
    * jdbc：子协议：子名称
    * 协议：JDBC URL中的协议总是jdbc
    * 子协议：子协议用于标识一个数据库的驱动程序
    * 子名称：一种表示数据库的方法。子名称可以依不同的子协议而改变，用子名称的目的是为了定位数据库提供足够的信息。包含主机名（对应服务端的ip地址），端口号，数据库名

* ```java
  //方式一
  //获取厂商的实现类对象
  Driver driver = new com.mysql.jdbc.Driver();
           String url = "jdbc:mysql://localhost:3306/rookie";
          Properties info = new Properties();
          info.setProperty("user","root");
          info.setProperty("password","smchen123");
          Connection connect = driver.connect(url, info);
          System.out.println(connect);
  ```

* ```java
  //方式二，使之不出现第三方的api，是的程序具有更好的移植性
          //获取Driver实现类对象，使用反射来实现
          Class clazz = Class.forName("com.mysql.jdbc.Driver");
          Driver driver = (Driver) clazz.newInstance();
  
          String url = "jdbc:mysql://localhost:3306/rookie";
          Properties info = new Properties();
          info.setProperty("user","root");
          info.setProperty("password","smchen123");
          Connection connect = driver.connect(url, info);
          System.out.println(connect);
  ```

* ```java
  //方式三，使用DriverManagert替换Driver
          //1、获取Driver实现类对象，使用反射来实现
          Class clazz = Class.forName("com.mysql.jdbc.Driver");
          Driver driver = (Driver) clazz.newInstance();
          //2、提供三个连接的基本信息
          String url = "jdbc:mysql://localhost:3306/rookie";
          String username ="root";
          String password = "smchen123";
          //注册驱动
          DriverManager.registerDriver(driver);
          //获取连接
          Connection connect = DriverManager.getConnection(url,username,password);
          System.out.println(connect);
  ```

* ```java
  //方式四，可是只是加载驱动，无需注册驱动
          //1、使用反射来加载驱动
          Class.forName("com.mysql.jdbc.Driver");
          //在加载过程中能自动注册驱动，因为在Mysql的Driver中有一个静态代码块
          /**
           * static {
          *              try {
          *             DriverManager.registerDriver(new Driver());
          *         } catch (SQLException var1) {
          *             throw new RuntimeException("Can't register driver!");
          *         }
          *     }
           */
          //2、提供三个连接的基本信息
          String url = "jdbc:mysql://localhost:3306/rookie";
          String username ="root";
          String password = "smchen123";
          //获取连接
          Connection connect = DriverManager.getConnection(url,username,password);
          System.out.println(connect);
  ```

* 方式五：使用配置文件，将需要的4个基本信息声明在配置文件中(最终版)

  * 实现了数据和代码的分离，实现了解耦
  * 能够很好的扩展和切换数据库
  * 使用配置文件能够避免工程重新打包

  ```java
  //1、读取配置文件中的四个基本信息
          InputStream in =  ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
  
          Properties properties = new Properties();
          properties.load(in);
  
          //2、获取四个基本信息
          String url = properties.getProperty("url");
          String username = properties.getProperty("username");
          String password = properties.getProperty("password");
          String driverClass = properties.getProperty("driverClass");
          //3、使用反射来加载驱动
          Class.forName(driverClass);
          //在加载过程中能自动注册驱动，因为在Mysql的Driver中有一个静态代码块
  
          //获取连接
          Connection connect = DriverManager.getConnection(url,username,password);
          System.out.println(connect);
  ```

### 3、使用PreparedStatement实现了CRUD操作

* 相对于statement能够对sql进行预编译
* 能够操作BLOB数据类型

* 操作和访问数据库
  * 数据库连接被用于向数据库服务器发送命令好SQL语句，并接收数据库服务器返回的结果。其实数据库连接就是一个Socket连接。
  * 在java.sql包中有三个接口分别定义了对数据库的调用的不同方式：
    * Statement：用于执行静态SQL语句并返回它所生成结果的对象
    * PrepatedStatement：SQL语句被预编译并存储在此对象中，可以使用此对象多次高效地执行该语句
    * CallableStatement：用于执行SQL存储过程

* Statement存在SQL注入问题
  * 使用PreparedStatement（Statement扩展而来）取代Statement

* PreparedStatement的使用

  * ORM编程思想：（object relational mapping）
    * 一个数据表对应一个java类
    * 表中的一条记录对饮gjava类的一个对象
    * 表中的一个字段对应java类的一个属性
  * 两种技术
    * JDBC结果集元数据：ResultSetMetaData
      * 获取列数：getColumnCount()
      * 获取列的别名：getColumnLabel()

    * 通过反射：创建指定类的对象，获取指定属性并赋值

  * 是mysql支持批处理：在url后添加rewrite batchedstatements = true
    * Mysql5.1.37后支持批处理

  * 设置不允许自动提交数据
    * conn.setAutoCommit(false)


### 4、数据库事务的引用

* 数据一旦提交就不能回滚

* 哪些操作会导致数据自动提交：

  * DDL操作一旦执行，都会自动提交
    * setAutoCommit=false对DDL操作失效
  * DML默认情况下，一旦执行就会自动提交
    * 我们可以通过setAutoCommit = false的方式取消DML操作的自动提交
  * 默认在关闭连接时，会自动的提交数据

* ```java
  conn.setAutoCommit=false;
    //...
  conn.commit();
  //...
  //异常回滚
  conn.rollback();
  ```

* 事务的ACID属性

  * 原子性：指一个事务时一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
  * 一致性：事务必须使数据库从一个一致性状态变换到另一个一致性状态
  * 隔离性：事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作集使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能忽现干扰。
  * 持久性：持久性是指一个事务一旦被提交，它对数据库中的数据的改变是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响

* 数据库的并发问题

  * 对于同时运行的多个事务，当这些事务访问数据库中相同的数据时，如果没有采取必要的隔离机制，就会导致各种并发问题：
    * 脏读：对于两个事务T1、T2，T1读取了已经被T2更新但没有被提交的字段，之后，若T2回滚，T1读取的内容就是临时且无效的
    * 不可重复读：对于两个事务T1、T2，T1读取了一个字段，然后T2更新了该字段。之后T1再次读取同一个字段，值就不同了。
    * **幻读**：对于两个事务T1、T2，T1从一个表中读取了一个字段，然后T2在该表中插入了一些新的行。之后，如果T1再次读取同一个表，就会多出几行 
  * **数据库事务的隔离性**：数据库系统必须具有隔离并发运行各个事务的能力，使他们不会相互影响，比秒各种并发问题。
  * 一个事务与其他事务隔离的程序成为称为隔离级别。数据库规定了多种事务隔离级别。不同隔离级别对应不同的干扰成都。隔离级别越高，数据一致性就越好，但并发性越弱。

* 四种隔离级别

  * **读未提交**（READ UNCOMMITTED）：允许事务读取其他事务提交的变更。脏读，不可重复读和幻读问题都会出现
  * **读已提交数据**（READ COMMITED）：只允许事务读取已经被其他事务提交的变更。可以避免脏读，但不可重复读和幻读问题仍然可能出现
  * **REPEATABLE READ**（可重复读）：确保事务多次从一个字段种读取相同的值。在这个事务持续期间，禁止其他事务对这个字段进行更新。可以避免脏读和不可重复读，但幻读的问题荏苒存在。
  * **串形化**（SERIALIZABLE）：确保事务可以从一个表中读取相同的行。在这个事务持续期间，禁止其他事务对该表执行插入，更新和删除操作。所有并发问题都可以避免，但性能十分低下。

### 5、DAO

### 6、数据库线程池

* 数据库连接池的必要性
  * 在使用开发基于数据库的web程序时，传统的模式基本时是按一下步骤：
    * 在主程序（如servlet、beans）中建立数据库连接
    * 进行sql操作
    * 断开数据库连接
  * 这种模式存在问题
    * 普通的JDBC数据库连接使用DriverManager来获取，每次向数据库建立连接的时候都要将Connection加载到内存中，再验证用户名和密码。需要数据库连接的时候，就向数据库要求一个，执行完成后再断开连接，这样的方式将会消耗大量的资源和时间。数据库的连接资源并没有得到很好的重复利用。若同时有几百人甚至几千人在线，频繁的进行数据库连接操作将占用高很多的系统资源，严重的甚至会造成服务器的崩溃。
    * 对于每一个数据库连接，使用完后都得断开。否则，如果程序出现异常而未能关闭，将会导致数据库系统中的内存泄露哦，最终将导致重启数据库。
    * 这种开发不能控制被创建的连接对象数，系统资源会被毫无顾忌的分配出去，如连接过多，
* 数据连接池技术
  * 为解决传统开发中的数据库连接问题，可以采用数据库连接池技术
  * 数据库连接池的基本思想：就是为数据库连接建立一个“缓冲池”。预先在缓冲池中放入一定数量的连接，当需要建立数据库连接时，只需从“缓冲池”中取出一个，使用完毕之后再放回去
  * 数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用故意恶搞现有的数据库连接，而不是重新建一个。
  * 数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中，这些数据库连接数的数据量是由最小数据库连接数来设定的。无论这些数据库连接是否被使用，连接池都将一直保持至少拥有这么多连接数据量。连接池的最大数据库连接数量限定了这个连接池能占有的最大连接数，当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。
* 多种开源的数据库连接池
  * JDBC的数据库使用javax.sql.dataSource来表示，DataSource只是一个接口，该接口通常由服务器（Weblogic,WebSohere,Tomcat）提供实现，也有一些开源组织提供实现：
    * **DBCP**是Apache提供的数据库连接池。tomcat服务器自带dbcp数据库连接池。速度相对c3p0较快，但因自身存在BUG，Hibernate3已经不再提供支持。
    * **C3P0**是一个开源组织提哦那个的一个数据库连接池，使用相对较慢，稳定性还可以。hibernate官方推荐使用
    * **proxool**是sourceforge下的一个开源项目数据库连接池，有监控连接池状态的功能，稳定性较C3P0差一点
    * **BoneCP**是一个开源组织提供的数据库连接池，速度快
    * **Druid**是阿里提供的数据库连接池，据说是集DBCP、C3P0、Proxool优点集于一身的数据库连接池，但是速度不确定是否有BoneCP快。
  * DataSource通常被成为数据源，它包含连接池和连接池管理两个部分，习惯上也经常把DataSource成为连接池
  * DataSource用来取代DriverManager来获取Connection，获取速度快，同时可以大幅度提高数据库访问速度

### 7、Apache-DBUtils实现CRUD操作

* 简介
  * commons-dbutils是Apache组织提供的一个开源JDBC工具类，它是对JDBC的简单封装，学习成本极低，并且使用dbutils能极大的简化jdbc编码的工作量，同时也不会影响程序的性能
  * API介绍
    * org.apache.commons.dbutils.QueryRunner
    * Org.apache.commons.dbutils.ResultSetHandler
    * 工具类：org.apache.commons.dbutils.DbUtils