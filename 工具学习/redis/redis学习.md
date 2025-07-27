## 1、nosql讲解

### 1、为什么要用Nosql

> 1、单机MySQL年代

![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-19 22.00.01.png)

* 数据量非常小，单个数据库完全足够
* 网站的瓶颈：
  * 数据量如果太大，一个机器放不下
  * 数据的索引（B+Tree），一个机器内存也放不下
  * 访问量（读写混合），一个服务器承受不了

> 2、Memcached（缓存）+MySQL+垂直拆分（读写分析）

* 网站80%的情况都是在读，每次都要去查询数据的话就十分麻烦！所以我们希望减轻数据的压力，我们可以使用缓存来保证效率
* 发展过程：优化数据结构和索引->文件混存（IO）->Memcached（最热门的技术）

![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-19 22.06.03.png)

> 3、分库分表+水平拆分+MySQL集群

* 本质：数据库（读，写）![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-19 22.15.27.png)

> 4、互联网公司架构

![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-19 22.35.01.png)

> 为什么使用NoSQL

* 用户的个人信息，社交网络，地理位置。用户自己产生的数据，用户日志等等爆发式增长
* NoSQL就能很好的解决上面的问题

### 2、什么是NoSQL

* NoSQL=Not Only SQL：泛指非关系型数据库，随着web2.0互联网诞生！传统的关系型数据库很难对付web2.0时代！尤其是超大规模的高并发的社区！暴露出来很多难以克服的问题，非关系型数据库，NoSQL在当今大数据环境下发展的十分迅速，Redis是发展最快的，而且是我们当下必须要掌握的一个技术
* 很多数据类型用户的个人信息，社交网络，地理位置。这些数据类型的存储不需要一个固定的格式！不需要多余的操作就可以横向扩展的！
  * 关系型数据库：表格，行，列

### 3、NoSQL特点

* 方便扩展（数据之间没有关系，很好扩展）

* 大数据量高性能（Redis一秒写八万次，读取11万，NoSQL的缓存记录级，是一种细粒度的缓存，性能会比较高）

* 数据类型是多样性的！（不需要事先设计数据库！随取随用！如果是数据量十分大的表，很多人就无法设计了）

* 传统的RDBMS和NoSQL

  > 传统的RDBMS
  >
  > * 结构化组织
  > * SQL
  > * 数据和关系都存在单独的表中
  > * 数据定义语言
  > * 严格的一致性
  > * 基础的事务
  > * 。。

  > NoSQL
  >
  > - 不仅仅是数据
  > - 没有固定的查询语言
  > - 健值对存储，列存储，文档存储，图形数据库（社交关系）
  > - 最终一致性
  > - CAP定力和BASE（异地多活）初级架构师
  > - 高性能，高可用，高可扩
  > - 。。。

* 了解：3V+3高

  * 大数据时代的3V：主要描述问题
    * 海量Volume
    * 多样Variety
    * 实时Velcity
  * 大数据时代的3高：主要是对程序的要求
    * 高并发
    * 高可扩
    * 高性能

* 真正在公司中的实践NoSQL+RDBMS一起使用才是最强的

## 2、阿里巴巴架构演进

> 1、商品基本信息
>
> * 名称、价格、商品信息
> * 关系型数据库可以解决了！MySQL/Oracle
>
> 2、商品的描述，评论（文字比较多）
>
> * 文档型数据库中，MongoDB
>
> 3、图片
>
> * 分布式文件系统 FastDFS
>   * 淘宝自己的 TFS
>   * Google的 GFS
>   * Hadoop HDFS
>   * 阿里云的 oss
>
> 4、商品的关键字（搜索）
>
> * 搜索引擎 solr elasticsearch
> * ISearch：多隆（所有牛逼的人都有一段苦逼的岁月！像SB一样坚持终将牛逼）
>
> 5、商品人们的波段信息
>
> * 内存数据库
> * redis、Tair、Memache
>
> 6、商品的交易，外部的支付接口
>
> * 三方应用

* 大型互联网因公问题
  * 数据类型太多
  * 数据源繁多
  * 数据要改造，大面积改造
* 解决问题：添加数据层：UDSL（统一数据服务平台）
  * 热点缓存

## 3、nosql数据模型

## 4、Nosql四大分类

### 1、KV健值对

* 新浪：Redis
*  美团：Redis+Tair
* 阿里、百度：Redis+memecache

### 2、文档型数据库（bson格式和json一样）

* MongoDB（一般需要要掌握）
  * MongoDB是一个机遇分布式文件存储的数据库，C++编写，主要用来处理大量的文档
  * MongoDB是一个介于关系型数据库和非关系型数据库中间的产品！MongoDB是非关系型数据库中功能最丰富，最像关系型数据库的
* ConthDB

### 3、列存储数据库

* HBase
* 分布式文件系统

### 4、图关系数据库

* 是存储关系的数据库，比如：朋友圈社交网络，广告推荐
* Neo4j，InfoGrid

## 5、CAP

## 6、BASE

## 7、Redis入门

### 1、概述

>  Redis(Remote Dictionary Server)，即远程字典服务

* 是一个开源的使用ANSI，C语言编写，支持网络、可基于内存亦可持久化日志型、Key-Value数据库，并提供多种语言的API。
* 区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且再次基础上实现了master-slave（主从）同步

> redis能干嘛？

* 内存存储、持久化，内存中是断电即失，、所以说持久化很重要（rdb，aof）
* 效率高，可以用于高速缓存
* 发布订阅系统
* 地图信息分析
* 计时器、计数器（浏览量）

> 特性

* 多样的数据类型
* 持久化
* 集群
* 事务

> 地址

* 官网 <https://redis.io/>
* 中文官网 <https://www.redis.net.cn/>
* github <https://github.com/redis/redis>
* windows只能通过官网下载

## 8、Redis安装（window & Linux服务器）

### 1、redis在Linux安装

* 下载安装包 **7.0.4.tar.gz**

* 解压Redis的安装包![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-21 23.43.30.png)

* 进入文件，可看到redis的配置文件![](/Users/smc/Desktop/smc/工具学习/redis/resource/WX20220721-234508@2x.png)

* 基本的环境安装

  ```shell
  #安装gcc-c++环境
  [root@192 redis-7.0.4]# yum install gcc-c++
  [root@192 redis-7.0.4]# gcc -v
  使用内建 specs。
  COLLECT_GCC=gcc
  COLLECT_LTO_WRAPPER=/usr/libexec/gcc/x86_64-redhat-linux/4.8.5/lto-wrapper
  目标：x86_64-redhat-linux
  配置为：../configure --prefix=/usr --mandir=/usr/share/man --infodir=/usr/share/info --with-bugurl=http://bugzilla.redhat.com/bugzilla --enable-bootstrap --enable-shared --enable-threads=posix --enable-checking=release --with-system-zlib --enable-__cxa_atexit --disable-libunwind-exceptions --enable-gnu-unique-object --enable-linker-build-id --with-linker-hash-style=gnu --enable-languages=c,c++,objc,obj-c++,java,fortran,ada,go,lto --enable-plugin --enable-initfini-array --disable-libgcj --with-isl=/builddir/build/BUILD/gcc-4.8.5-20150702/obj-x86_64-redhat-linux/isl-install --with-cloog=/builddir/build/BUILD/gcc-4.8.5-20150702/obj-x86_64-redhat-linux/cloog-install --enable-gnu-indirect-function --with-tune=generic --with-arch_32=x86-64 --build=x86_64-redhat-linux
  线程模型：posix
  gcc 版本 4.8.5 20150623 (Red Hat 4.8.5-44) (GCC) 
  
  #在redis目录下安装所有所需插件和环境
  [root@192 redis-7.0.4]# make
  #查看状态
  [root@192 redis-7.0.4]# make install
  cd src && make install
  which: no python3 in (/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.x86_64/bin:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.x86_64/jre/bin:/root/bin)
  make[1]: 进入目录“/opt/redis-7.0.4/src”
  
  Hint: It's a good idea to run 'make test' ;)
  
      INSTALL redis-server
      INSTALL redis-benchmark
      INSTALL redis-cli
  make[1]: 离开目录“/opt/redis-7.0.4/src”
  #redis服务安装目录
  [root@192 bin]# ll
  总用量 21492
  -rwxr-xr-x. 1 root root  5197832 7月  22 07:53 redis-benchmark
  lrwxrwxrwx. 1 root root       12 7月  22 07:53 redis-check-aof -> redis-server
  lrwxrwxrwx. 1 root root       12 7月  22 07:53 redis-check-rdb -> redis-server
  -rwxr-xr-x. 1 root root  5411024 7月  22 07:53 redis-cli
  lrwxrwxrwx. 1 root root       12 7月  22 07:53 redis-sentinel -> redis-server
  -rwxr-xr-x. 1 root root 11390184 7月  22 07:53 redis-server
  [root@192 bin]# pwd
  /usr/local/bin
  
  
  ```

* 配置文件复制到redis服务安装目录（保留原先，能够返回）

  ```shell
  [root@anonymous redis-7.0.4]# cp /opt/redis-7.0.4/redis.conf /usr/local/bin/kconfig/
  [root@anonymous redis-7.0.4]# cd  /usr/local/bin/kconfig/
  [root@anonymous kconfig]# ll
  总用量 108
  -rw-r--r--. 1 root root 106545 7月  22 08:50 redis.conf
  
  ```

* 修改配置文件daemonize为yes，使redis服务后台启动![](/Users/smc/Desktop/smc/工具学习/redis/resource/WX20220723-170305@2x.png)

* 启动redis服务，并使用客户端连接和使用![](/Users/smc/Desktop/smc/工具学习/redis/resource/WX20220723-170730@2x.png)

* 查看redis进程是否开启![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-23 17.08.48.png)

* 关闭redis服务：` shutdown` 

  ```shell
  127.0.0.1:6379> shutdown
  not connected> exit
  
  ```

## 9、redis-benchmark性能测试

* 官方自带的性能测试工具

  ```shell
  #测试：100个并发连接 100000请求
  #先进行连接
  [root@anonymous bin]# redis-server kconfig/redis.conf 
  #无需一定要连接
  [root@anonymous bin]# redis-cli -p 6379	
  127.0.0.1:6379> 
  
  
  #对使用redis-benchmark进行测试
  [root@anonymous ~]# ps -ef | grep redis
  root      99731      1  0 09:05 ?        00:00:00 redis-server 127.0.0.1:6379
  root     100550  72814  0 09:06 pts/0    00:00:00 redis-cli -p 6379
  root     100960  85530  0 09:06 pts/2    00:00:00 grep --color=auto redis
  [root@anonymous ~]# cd /usr/local/bin/
  [root@anonymous bin]# redis-benchmark -h localhost -p 6379 -c 100 -n 100000
  
  ```

  ![](/Users/smc/Desktop/smc/工具学习/redis/resource/WX20220723-172713@2x.png)

  ![](/Users/smc/Desktop/smc/工具学习/redis/resource/WX20220723-172528@2x.png)

* Redis-benchemark命令![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-23 17.29.49.png)

## 10、基础的知识

### 1、redis数据库

* 有16种数据库![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-24 17.07.42.png)

  * 默认使用的是第0个

  * 使用select切换数据库

    ```shell
    127.0.0.1:6379> select 3
    OK
    127.0.0.1:6379[3]> DBSIZE
    (integer) 0
    127.0.0.1:6379[3]> 
    
    ```

    ![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-07-24 17.12.49.png)

  ```shell
  #查看所有key的value
  127.0.0.1:6379[3]> keys *
  1) "name"
  
  #清楚当前数据库
  127.0.0.1:6379[3]> FLUSHDB
  OK
  127.0.0.1:6379[3]> keys *
  (empty array)
  
  #清空全部数据库
  127.0.0.1:6379[3]> FLUSHALL
  OK
  
  ```

* redis是单线程的

  * 明白redis是很快的，官方表示，Redis是基于内存操作，CPU不是Redis的性能瓶颈，Redis的瓶颈时根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了！所有就使用单线程了！
  * Redis是C语言写的，官方提供的数据为100000+的QPS，完全不比同样使用key-value的Memecache差

* Redis为什么单线程还那么快
  * 误区1:高性能的服务器一定是多线程的
  * 误区2:多线程（CPU上下文切换）一定比单线程效率高
  * 核心：redis是将所有的数据全部存放在内存中的，所以说使用单线程去操作效率就是最高的，多线程（CPU上下文会切换：耗时的操作），对于欧内存而言，如果没有上下文切换效率就是最高的！多次读写都是在一个CPU上的，在内存下，这个就是最佳的方案。

## 11、五大数据类型

* Redis是一个开源（BSD许可），内存存储的数据结构服务器，可用作**数据库**，**高速缓存**和**消息队列代理**。它支持[字符串](https://www.redis.net.cn/tutorial/3508.html)、[哈希表](https://www.redis.net.cn/tutorial/3509.html)、[列表](https://www.redis.net.cn/tutorial/3510.html)、[集合](https://www.redis.net.cn/tutorial/3511.html)、[有序集合](https://www.redis.net.cn/tutorial/3512.html)，[位图](https://www.redis.net.cn/tutorial/3508.html)，[hyperloglogs](https://www.redis.net.cn/tutorial/3513.html)等数据类型。内置复制、[Lua脚本](https://www.redis.net.cn/tutorial/3516.html)、LRU收回、[事务](https://www.redis.net.cn/tutorial/3515.html)以及不同级别磁盘持久化功能，同时通过Redis Sentinel提供高可用，通过Redis Cluster提供自动[分区](https://www.redis.net.cn/tutorial/3524.html)。

### 1、Redis Key

```shell
127.0.0.1:6379[3]> set name smc
OK
127.0.0.1:6379[3]> keys *
1) "name"
127.0.0.1:6379[3]> set age 26
OK
127.0.0.1:6379[3]> keys *
1) "age"
2) "name"
#判断name的key是否存在
127.0.0.1:6379[3]> Exists name
(integer) 1
127.0.0.1:6379[3]> 
127.0.0.1:6379[3]> EXISTS age
(integer) 1
#移动当前name到指定库
127.0.0.1:6379[3]> move name 1
(integer) 1
127.0.0.1:6379[3]> keys *
1) "age"
127.0.0.1:6379[1]> keys *
1) "name"
127.0.0.1:6379[1]> get name
"smc"

#删除指定key
127.0.0.1:6379[1]> keys *
1) "name"
127.0.0.1:6379[1]> get name
"smc"
127.0.0.1:6379[1]> DEL name
(integer) 1
127.0.0.1:6379[1]> keys *
(empty array)


127.0.0.1:6379[3]> set name smc
OK
127.0.0.1:6379[3]> get name
"smc"

#设置key的过期时间
127.0.0.1:6379[3]> EXPIRE name 10
(integer) 1
#显示key的倒计时剩余时间
127.0.0.1:6379[3]> ttl name
(integer) 7
127.0.0.1:6379[3]> ttl name
(integer) 6
127.0.0.1:6379[3]> ttl name
(integer) 4
127.0.0.1:6379[3]> ttl name
(integer) 4
127.0.0.1:6379[3]> ttl name
(integer) 3
127.0.0.1:6379[3]> ttl name
(integer) 1
127.0.0.1:6379[3]> ttl name
(integer) 0
127.0.0.1:6379[3]> ttl name
(integer) -2
127.0.0.1:6379[3]> keys *
1) "age"

#查看当前key的value类型
127.0.0.1:6379[3]> set name smc
OK
127.0.0.1:6379[3]> type name
string

```



### 2、String（字符串类型）

```shell
127.0.0.1:6379[3]> set key1 v1
OK
127.0.0.1:6379[3]> get key1
"v1"
#往key后追加字符串，若key不存在则创建
127.0.0.1:6379[3]> APPEND key1 "hello"
(integer) 7
127.0.0.1:6379[3]> get key1
"v1hello"
127.0.0.1:6379[3]> 

#查看字符串的长度
127.0.0.1:6379[3]> STRLEN key1
(integer) 7


127.0.0.1:6379[3]> set views 0
OK
127.0.0.1:6379[3]> get views
"0"
127.0.0.1:6379[3]> type views
string
#自增1，类似i++
127.0.0.1:6379[3]> incr views
(integer) 1
127.0.0.1:6379[3]> type views
string
127.0.0.1:6379[3]> get views
"1"
#自减1,类似i--
127.0.0.1:6379[3]> decr views
(integer) 0
127.0.0.1:6379[3]> get views
"0"
127.0.0.1:6379[3]> decr views
(integer) -1
127.0.0.1:6379[3]> get views
"-1"
#设置步长，设置增长的量
127.0.0.1:6379[3]> INCRBY views 10
(integer) 9
127.0.0.1:6379[3]> get views
"9"
#设置减少的量
127.0.0.1:6379[3]> DECRBY views 5
(integer) 4
127.0.0.1:6379[3]> get views
"4"



127.0.0.1:6379[3]> set key1 "hello,redis" 
OK
127.0.0.1:6379[3]> get key1
"hello,redis"
#截取字符串，类似substr
127.0.0.1:6379[3]> GETRANGE key1 0 3
"hell"
127.0.0.1:6379[3]> GETRANGE key1 1 3
"ell"
#最后的位数也可以负数表示
127.0.0.1:6379[3]> GETRANGE key1 1 -2
"ello,redi"
127.0.0.1:6379[3]> GETRANGE key1 1 -1
"ello,redis"
127.0.0.1:6379[3]> 

127.0.0.1:6379[3]> set key2 abcdefg
OK
127.0.0.1:6379[3]> get key2
"abcdefg"
#替换指定位置开始的字符串
127.0.0.1:6379[3]> SETRANGE key2 1 xxx
(integer) 7
127.0.0.1:6379[3]> get key2
"axxxefg"



#setex (set with expire) 设置过期时间
#设置key3的值为30秒后过期
127.0.0.1:6379[3]> SETEX key3 30 hello  
OK
127.0.0.1:6379[3]> ttl key3
(integer) 26
127.0.0.1:6379[3]> get key3
"hello"
#setnx (set if not exist) 不存在在设置（在分布式锁中会常常使用）
#如果key4存在则会创建失败
127.0.0.1:6379[3]> SETNX key4 redis
(integer) 1
127.0.0.1:6379[3]> keys *
1) "key4"
2) "key2"
3) "key1"
127.0.0.1:6379[3]> 

#同时设置多个值
127.0.0.1:6379[3]> mset k1 v1 k2 v2 k3 v3
OK
127.0.0.1:6379[3]> keys *
1) "k3"
2) "k2"
3) "k1"
#同时获取多个值
127.0.0.1:6379[3]> mget k1 k2 
1) "v1"
2) "v2"
#原子性操作，要么同时成功，要么同时失败
127.0.0.1:6379[3]> msetnx k1 vv1 k2 vv2 k4 v4
(integer) 0
127.0.0.1:6379[3]> keys *
1) "k3"
2) "k2"
3) "k1"


#对象的使用，设置一个user:2对象，值为json字符串来保存一个对象
127.0.0.1:6379[3]> set user:2 {name:wangting,age:30}
OK
127.0.0.1:6379[3]> get user:2
"{name:wangting,age:30}"

#也可以这样来设置一个对象，用前缀来区分
127.0.0.1:6379[3]> mset user:1:name smc user:1:age 26
OK
127.0.0.1:6379[3]> keys *
1) "user:1:age"
2) "user:1:name"

#如果不存在值则返回nil，然后设置新的值
127.0.0.1:6379[3]> GETSET db redis
(nil)
127.0.0.1:6379[3]> get db
"redis"
#如果存在则返回值，并设置新的值
127.0.0.1:6379[3]> getset db mongdb
"redis"
127.0.0.1:6379[3]> get db
"mongdb"

```

* String类似的使用场景：value除了是我们的字符串还可以是我们的数字
  * 计数器
  * 统计多单位数量
  * 粉丝数
  * 对象缓存存储

### 3、List

* 在redis里面，可以把list玩成栈、队列、阻塞队列
* 所有的list命令都是以 ***l*** 开头的，redis不区分大小写命令

```shell
#将一个值或多个值，插入到列表头部（左）
127.0.0.1:6379> LPUSH list one
(integer) 1
127.0.0.1:6379> LPUSH list two
(integer) 2
127.0.0.1:6379> LPUSH list three
(integer) 3

#获取list中的值,从左到右
127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "two"
3) "one"

#根据区间获取具体的值
127.0.0.1:6379> LRANGE list 0 1
1) "three"
2) "two"
127.0.0.1:6379> 

#向列表的右边插入值
127.0.0.1:6379> RPUSH list right
(integer) 4
127.0.0.1:6379> LRANGE list 0 0
1) "three"
127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "two"
3) "one"
4) "right"


127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "two"
3) "one"
4) "right"
#推出第一个值（左）
127.0.0.1:6379> LPOP list
"three"
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"
3) "right"
#移除最后一个值（右）
127.0.0.1:6379> RPOP list 
"right"
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"

127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"
#根据LINDEX获取指定坐标的值（从左到右）
127.0.0.1:6379> LINDEX list 1
"one"
127.0.0.1:6379> LINDEX list 0
"two"
127.0.0.1:6379> 

#获取list的长度
127.0.0.1:6379> LLEN list
(integer) 2
127.0.0.1:6379> 


127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "three"
3) "three"
4) "1"
5) "three"
6) "two"
#从左到右删除一个three
127.0.0.1:6379> LREM list 1 three
(integer) 1
127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "three"
3) "1"
4) "three"
5) "two"
#从左到右删除所有three
127.0.0.1:6379> LREM list 0 three
(integer) 3
127.0.0.1:6379> LRANGE list 0 -1
1) "1"
2) "two"
#一次性插入多个three
127.0.0.1:6379> LPUSH list three three three three
(integer) 6
127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "three"
3) "three"
4) "three"
5) "1"
6) "two"
#从左到右删除2个three
127.0.0.1:6379> LRANGE list 2 three
(error) ERR value is not an integer or out of range
127.0.0.1:6379> LREM list 2 three
(integer) 2


127.0.0.1:6379> RPUSH mylist "hello1" "hello2" "hello3" "hello4"
(integer) 4
127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello1"
2) "hello2"
3) "hello3"
4) "hello4"
#根据坐标截取指定长度的list(从左到右)
127.0.0.1:6379> LTRIM mylist 1 2
OK
127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello2"
2) "hello3"

127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello1"
2) "hell02"
3) "hello3"
4) "hello4"
#从指定的list右边推出，再从指定的list左边推入
127.0.0.1:6379> RPOPLPUSH mylist myothenlist
"hello4"
127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello1"
2) "hell02"
3) "hello3"
127.0.0.1:6379> keys *
1) "myothenlist"
2) "mylist"
127.0.0.1:6379> LRANGE myothenlist 0 -1
1) "hello4"



127.0.0.1:6379> EXISTS list
(integer) 0
#设置list指定坐标的值，由于list不存在则无法修改
127.0.0.1:6379> LSET list 0 item
(error) ERR no such key
127.0.0.1:6379> LPUSH list v1
(integer) 1
127.0.0.1:6379> LRANGE list 0 -1
1) "v1"
#如果存在则会更新list指定坐标的值
127.0.0.1:6379> lset list 0 vvvvv1
OK
127.0.0.1:6379> LRANGE list 0 -1
1) "vvvvv1"
#若修改的坐标不在list范围内，则无法更新
127.0.0.1:6379> LSET list 1 vvv2
(error) ERR index out of range


127.0.0.1:6379> RPUSH mylist "hello" "world"
(integer) 2
#将某个具体的值插入到列的某个具体值的前面
127.0.0.1:6379> LINSERT mylist BEFORE "world" "other"
(integer) 3
127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello"
2) "other"
3) "world"
#将某个具体的值插入到列的某个具体值的后面
127.0.0.1:6379> LINSERT mylist AFTER world new 
(integer) 4
127.0.0.1:6379> LRANGE mylist 0 -1
1) "hello"
2) "other"
3) "world"
4) "new"


```

* 实践上是一个双向链表
* 如果key不存在，创建新的链表
* 如果key存在，新增内容
* 如果移除所有值，空链表，也代表不存在
* 在两边插入或者改动值，效率最高！中间元素相对效率较低
* 

### 4、Set

* set中的值是不能重复的
* set的命令都是以***s***开头

```shell
#set中添加元素
127.0.0.1:6379> SADD myset "hello"
(integer) 1
127.0.0.1:6379> sadd myset "smc" "love java"
(integer) 2
#查看指定set的所有元素
127.0.0.1:6379> SMEMBERS myset
1) "love java"
2) "smc"
3) "hello"
#判断某一个值是否在set集合中
127.0.0.1:6379> SISMEMBER myset hello
(integer) 1
127.0.0.1:6379> SISMEMBER myset world
(integer) 0
#获取set集合中的元素个数
127.0.0.1:6379> SCARD  myset 
(integer) 3

#移除set中的指定元素
127.0.0.1:6379> SREM myset hello
(integer) 1
127.0.0.1:6379> SISMEMBER myset hello
(integer) 0
127.0.0.1:6379> SMEMBERS myset
1) "love java"
2) "smc"

#无序不重复集合
127.0.0.1:6379> SMEMBERS myset
1) "lovejs"
2) "love java"
3) "smc"
4) "lovepython"
#随机抽取一个元素
127.0.0.1:6379> SRANDMEMBER myset
"smc"
127.0.0.1:6379> SRANDMEMBER myset
"lovepython"
127.0.0.1:6379> SRANDMEMBER myset
"lovepython"
127.0.0.1:6379> SRANDMEMBER myset
"love java"
#随机抽取两个元素
127.0.0.1:6379> SRANDMEMBER myset 2
1) "lovejs"
2) "lovepython"
127.0.0.1:6379> SRANDMEMBER myset 2
1) "lovejs"
2) "smc"

#随机删除一些set集合中的元素
127.0.0.1:6379> SPOP myset
"lovejs"
127.0.0.1:6379> SPOP myset
"lovepython"
127.0.0.1:6379> SMEMBERS myset
1) "love java"
2) "smc"


127.0.0.1:6379> SADD myset hello world smc
(integer) 3
127.0.0.1:6379> sadd myset2 set
(integer) 1
#移动一个set的值到另一个set
127.0.0.1:6379> SMOVE myset myset2 smc
(integer) 1
127.0.0.1:6379> SMEMBERS myset
1) "world"
2) "hello"
127.0.0.1:6379> SMEMBERS myset2
1) "smc"
2) "set"


127.0.0.1:6379> sadd key a b c
(integer) 3
127.0.0.1:6379> sadd key2 b c d
(integer) 3
#查看差集
127.0.0.1:6379> SDIFF key key2
1) "a"
#查看交集
127.0.0.1:6379> SINTER key key2
1) "c"
2) "b"
#查看并集
127.0.0.1:6379> SUNION key key2
1) "b"
2) "a"
3) "c"
4) "d"


```

* 共同关注，共同爱好，二度好友等功能

### 5、Hash

* map集合，key-map，值是一个map集合

```shell
#设置hash值，可设置多个，类似hmset
127.0.0.1:6379> HSET myhash field1 smc field2 ljg
(integer) 2
127.0.0.1:6379> keys *
1) "myhash"
2) "mylist"
3) "list"
4) "myothenlist"
#根据key和field获取value
127.0.0.1:6379> hget myhash field1
"smc"
#获取多个字段值
127.0.0.1:6379> HMGET myhash field1 field2
1) "smc"
2) "ljg"
#获取所有字段值
127.0.0.1:6379> hgetall myhash
1) "field1"
2) "smc"
3) "field2"
4) "ljg"


127.0.0.1:6379> HGETALL myhash
1) "field1"
2) "smc"
3) "field2"
4) "ljg"
#删除hash指定key字段！对应的value就消失了
127.0.0.1:6379> HDEL myhash field1
(integer) 1
127.0.0.1:6379> HGETALL myhash
1) "field2"
2) "ljg"

127.0.0.1:6379> HMSET myhash field4 4444
OK
127.0.0.1:6379> HGETALL myhash
1) "field2"
2) "222"
3) "field3"
4) "333"
5) "field4"
6) "4444"
#获取hash值长度
127.0.0.1:6379> HLEN myhash
(integer) 3

#判断hash中指定字段是否存在
127.0.0.1:6379> HEXISTS myhash field1
(integer) 0
127.0.0.1:6379> HEXISTS myhash field2
(integer) 1

#获取hash中所有field
127.0.0.1:6379> HKEYS myhash
1) "field2"
2) "field3"
3) "field4"
#获取hash中所有value
127.0.0.1:6379> HVALS myhash
1) "222"
2) "333"
3) "4444"

#指定增量
127.0.0.1:6379> hset myhash field5 5
(integer) 1
127.0.0.1:6379> HINCRBY myhash field5 1
(integer) 6
127.0.0.1:6379> HINCRBY myhash field -1
(integer) -1
127.0.0.1:6379> HVALS myhash
1) "222"
2) "333"
3) "4444"
4) "6"
5) "-1"
127.0.0.1:6379> HINCRBY myhash field5 -1
(integer) 
#如果不存在则可以创建，存在则不能设置
127.0.0.1:6379> HSETNX myhash myfield6 66
(integer) 1
127.0.0.1:6379> 

```

* hash变更的数据 user  name age尤其是用户信息之类的，经常变动的信息！hash更适合于对象的存储，String更加符合字符串的存储

### 6、Zset(有序集合)

* 在set的基础上，增加一个值，set k1 v1 

```shell
#添加一个值
127.0.0.1:6379> zadd myset 1 one
(integer) 1
#添加多个值
127.0.0.1:6379> zadd myset 2 two 3 three
(integer) 2
127.0.0.1:6379> ZRANGE myset 0 -1
1) "one"
2) "two"
3) "three"

#添加三个用户
127.0.0.1:6379> zadd salary 2500 xiaohong
(integer) 1
127.0.0.1:6379> zadd salary 5000 zhangsan
(integer) 1
127.0.0.1:6379> zadd salary 500 smc
(integer) 1
#展示salary数据
127.0.0.1:6379> ZRANGE salary 0 -1
1) "smc"
2) "xiaohong"
3) "zhangsan"
#显示所有数据，从小到大，显示范围
127.0.0.1:6379> zrangebyscore salary -inf +inf
1) "smc"
2) "xiaohong"
3) "zhangsan"
#显示所有数据，并带上成绩
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf WITHSCORES
1) "smc"
2) "500"
3) "xiaohong"
4) "2500"
5) "zhangsan"
6) "5000"
#显示负无穷到2500之间的数据
127.0.0.1:6379> ZRANGEBYSCORE salary -inf 2500 WITHSCORES
1) "smc"
2) "500"
3) "xiaohong"
4) "2500"

#移除有序集合中的元素
127.0.0.1:6379> ZREM salary smc
(integer) 1
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf WITHSCORES
1) "xiaohong"
2) "2500"
3) "zhangsan"
4) "5000"

#获取有序集合中的个数
127.0.0.1:6379> ZCARD salary
(integer) 2

#根据分数范围降序排序
127.0.0.1:6379> ZREVRANGEBYSCORE salary +inf -inf
1) "zhangsan"
2) "xiaohong"

#根据范围计算数量
127.0.0.1:6379> zadd myset 1 hello 2 world 3 redis
(integer) 3
127.0.0.1:6379> zcount myset 1 3
(integer) 3
127.0.0.1:6379> zcount myset 2 3
(integer) 2

```

* 案例思路：1、重要消息2、带权重进行判断
* 排行榜应用实现，取top

## 12、三种特殊数据类型

### 1、geospatial（地理位置）

* 朋友的定位，附近的人，打车距离计算
* redis的Geo在Redis3.2版本推出，可以推算地理为孩子的信息，两地的距离，方圆几里的人
* 六个命令![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-08-03 23.14.46.png)

```shell
#geoadd 添加地理位置
127.0.0.1:6379> GEOADD china:beijing 116.408 39.904 
(error) ERR wrong number of arguments for 'geoadd' command
127.0.0.1:6379> GEOADD china:city 116.408 39.904 beijing 
(integer) 1
127.0.0.1:6379> GEOADD china:city 121.445 31.213 shanghai 
(integer) 1
127.0.0.1:6379> GEOADD china:city 118.08 24.43 xiamen 
(integer) 1
127.0.0.1:6379> GEOADD china:city 119.533 26.683 ningde 113.736 23.047 dongguan 119.022 25.451 putian
(integer) 3

#GEOPOS获取指定城市的经度和纬度
127.0.0.1:6379> GEOPOS china:city beijing dongguan
1) 1) "116.40800267457962036"
   2) "39.90399988166036138"
2) 1) "113.73600214719772339"
   2) "23.04699903363086122"

#GEODIST查看两个位置的距离
127.0.0.1:6379> GEODIST china:city beijing shanghai
"1068232.0171"
#单位km
127.0.0.1:6379> GEODIST china:city beijing shanghai km
"1068.2320"

#我附近的人：获得所有附近的人的地址，定位，通过半径来查询
#GEORADIUS从指定的经纬度为中心查找1000km内的人，但数据必须在key中
127.0.0.1:6379> GEORADIUS china:city 110 30 1000 km
1) "dongguan"
#获取经纬度
127.0.0.1:6379> GEORADIUS china:city 110 30 1000 km withcoord
1) 1) "dongguan"
   2) 1) "113.73600214719772339"
      2) "23.04699903363086122"
#获取距离
127.0.0.1:6379> GEORADIUS china:city 110 30 1000 km withcoord withdist
1) 1) "dongguan"
   2) "857.9126"
   3) 1) "113.73600214719772339"
      2) "23.04699903363086122"

#GEORADIUSBYMEMBER找出指定元素周围的元素
127.0.0.1:6379> GEORADIUSBYMEMBER china:city beijing 1500 km 
1) "ningde"
2) "shanghai"
3) "beijing"

#geohash将二维的经纬度转为一纬的字符串，如果两个字符串越相似，距离越近
127.0.0.1:6379> geohash china:city beijing shanghai
1) "wx4g0bm9xh0"
2) "wtw3ed1sct0"

```

* 底层的实现原理其实**zset**

```shell
#查看所有geo元素
127.0.0.1:6379> ZRANGE china:city 0 -1
1) "dongguan"
2) "xiamen"
3) "putian"
4) "ningde"
5) "shanghai"
6) "beijing"
127.0.0.1:6379> ZREM china:city putian
(integer) 1
127.0.0.1:6379> ZRANGE china:city 0 -1
1) "dongguan"
2) "xiamen"
3) "ningde"
4) "shanghai"
5) "beijing"

```



### 2、hyperloglog

* 什么是基数：（不重复的元素）
  * 找不重复元素的个数
* 简介
  * 基数统计的算法
  * 网页的UV（一个人访问一个网站多次，但是还是算一个人）
    * 传统的方式，set保存用户的id，然后就可以统计set中元素数量作为标准判断
    * 这个方式如果保存大量的用户id，就会比较麻烦！我们的目的是为了计数，而不是保存用户id

```shell
127.0.0.1:6379> PFADD mykey a b c d e f g h i j #创建第一组元素 mykey
(integer) 1
127.0.0.1:6379> PFCOUNT mykey #统计mykey元素的基数数量
(integer) 10
127.0.0.1:6379> PFADD mykey i j k l m n g h  f # 往第一组元素中添加元素，会去重添加
(integer) 1
127.0.0.1:6379> PFCOUNT mykey 
(integer) 14
127.0.0.1:6379> PFADD mykey2 h i j k l m n o p q # 创建第二组元素 mykey3
(integer) 1
127.0.0.1:6379> PFCOUNT mykey2
(integer) 10
127.0.0.1:6379> PFMERGE mykey3 mykey mykey2 #合并两组元素
OK
127.0.0.1:6379> PFCOUNT mykey3 #去重
(integer) 17

```

* 如果允许容错，那么一定可以使用Hyperloglog
* 如果不允许容错，就使用set或者自己的数据类型

### 3、bitmap

* 按位存储
  * 统计疫情感染人数
  * 统计用户信息，活跃、不活跃！登录，未登录！打卡，365打卡
  * 位图，数据结构！都是操作二进制位来进行记录，就只有0和1两个状态

```shell
#使用bitmap来记录周一到周日的打卡
127.0.0.1:6379> SETBIT sign 0 1
(integer) 0
127.0.0.1:6379> SETBIT sign 1 1
(integer) 0
127.0.0.1:6379> SETBIT sign 2 1
(integer) 0
127.0.0.1:6379> SETBIT sign 3 0
(integer) 0
127.0.0.1:6379> SETBIT sign 4 0
(integer) 0
127.0.0.1:6379> SETBIT sign 5 1
(integer) 0
127.0.0.1:6379> SETBIT sign 6 1
(integer) 0
#查看某一天是否有打开
127.0.0.1:6379> GETBIT sign 2
(integer) 1
#查询打卡天数，统计
127.0.0.1:6379> BITCOUNT sign
(integer) 5

```



## 13、Redis事务操作

* redis事务本质：一组命令的集合！一个事物中的所有命令都会被序列化，在事物执行过程中，会按照顺序执行
* 一次性、顺序性、排他性！执行一系列的命令
* redis事物没有隔离级别的概念
* 所有的命令在事物中，并没有直接被执行！只有发起执行命令的时候才会执行
* redis单条命令式保证原子性的，但是事物不保证原子性

###  1、redis事物

* 开始事物（multi）
* 命令入队
* 执行事物

```shell
#事物执行流程
127.0.0.1:6379> MULTI #开启事物
OK
#命令入队
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> get k2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> EXEC #执行事物
1) OK
2) OK
3) "v2"
4) OK

#放弃事物
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2 k4 v4
QUEUED
127.0.0.1:6379(TX)> DISCARD #取消事物
OK
127.0.0.1:6379> get k4 #事物中的命令都不会被执行
(nil)

```

* 编译型异常（代码有问题！命令有错！），事物中所有的命令都不会被执行

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2 
QUEUED
127.0.0.1:6379(TX)> set k4 v4
QUEUED
127.0.0.1:6379(TX)> getset k4 v4
QUEUED
127.0.0.1:6379(TX)> getset k5 #错误的命令
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379(TX)> set k6 v6
QUEUED
127.0.0.1:6379(TX)> EXEC
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379> get k6 v6 #所有的命令都无法执行
(error) ERR wrong number of arguments for 'get' command
```



* 运行时异常（如：1/0），如果事物队列中存在语法型错误，那么在执行命令时，其他命令可以正常执行的，错误命令抛出异常

```shell
127.0.0.1:6379> set k1 "v1"
OK
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> INCR k1 #会执行的时候失败
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> get k3
QUEUED
127.0.0.1:6379(TX)> EXEC
1) (error) ERR value is not an integer or out of range #其他命令正常执行
2) OK
3) OK
4) "v3"
127.0.0.1:6379> get k2
"v2"

```

### 2、监控

* 悲观锁：很悲观，认为无论什么时候都会出现问题，无论做什么都会加锁

* 乐观锁：很乐观，认为无论什么时候都不会出现问题，更新数据的时候去判断一下，在此期间是否有人修改过这个数据

  * 获取version
  * 更新的时候比较version

* redis的监视测试

  * 正常执行成功

  ```shell
  127.0.0.1:6379> set money 100
  OK
  127.0.0.1:6379> set out 0
  OK
  127.0.0.1:6379> watch money #监视money
  OK
  127.0.0.1:6379> multi #事物正常结束，数据期间没有发生变动，这个时候就正常执行
  OK
  127.0.0.1:6379(TX)> DECRBY money 20
  QUEUED
  127.0.0.1:6379(TX)> INCRBY out 20
  QUEUED
  127.0.0.1:6379(TX)> exec
  1) (integer) 80
  2) (integer) 20
  
  ```

  * 测试多线程修改值，使用watch可以当作redis的乐观锁操作

  ```shell
  127.0.0.1:6379> watch money #监视money
  OK
  127.0.0.1:6379> multi
  OK
  127.0.0.1:6379(TX)> DECRBY money 10
  QUEUED
  127.0.0.1:6379(TX)> INCRBY out 10
  QUEUED
  127.0.0.1:6379(TX)> EXEC #执行之前，另外一个线程修改了监视的money，则事物会执行失败
  (nil)
  
  #如果执行失败，watch最新的值就好
  127.0.0.1:6379> UNWATCH #如果发现事物执行失败，就先解锁
  OK
  127.0.0.1:6379> WATCH money #获取最新的值，再次见识，select version
  OK
  127.0.0.1:6379> multi
  OK
  127.0.0.1:6379(TX)> DECRBY money 10
  QUEUED
  127.0.0.1:6379(TX)> INCRBY money 10
  QUEUED
  127.0.0.1:6379(TX)> EXEC #比对监视的值是否发生变化，如果没有变化，那么正常执行，如果变化就执行失败
  1) (integer) 990
  2) (integer) 1000
  
  ```

## 14、Jedis

* 我们要使用Java来操作Redis

* Jedis事Redis官方推荐的java连接开发工具！使用Java操作redis中间件，如果需要使用java操作redis，那么一定要对jedis十分熟悉

  * 导入对应的依赖

    ```xml
    <dependencies>
      <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>4.2.3</version>
      </dependency>
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>2.0.11</version>
      </dependency>
    </dependencies>
    ```

  * 编码测试

    * 连接数据库

      ```java
      // 1、new Jedis对象
      Jedis jedis = new Jedis("192.168.0.100",6379);
      //2、jedis所有的命令就是我们之前学习的所有指令
      System.out.println(jedis.ping());
      
      /*
      	连接时可能会被redis拒绝
      		1、注释配置文件bind 127.0.0.1,要不然只能允许本地访问
      		2、件protect-mode yes改为no，关闭保护模式
          3、对外开放redis端口
      */
      ```

      

    * 操作命令:所有命令方法同上redis命令

      * String
      * list
      * set
      * hash
      * zset

      ```java
      
      // 1、new Jedis对象
      Jedis jedis = new Jedis("192.168.0.100",6379);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("user1","world");
      jsonObject.put("user2","smc");
      //开启事物
      Transaction multi = jedis.multi();
      String result = jsonObject.toJSONString();
      try {
        multi.set("user1", result);
        multi.set("user2",result);
        multi.exec();
      } catch (Exception e) {
        multi.discard();
        e.printStackTrace();
      }finally {
        System.out.println(jedis.get("user1"));
        System.out.println(jedis.get("user2"));
        //关闭连接
        jedis.close();
      }
      ```

      

    * 断开连接

      ```java
      jedis.close();
      ```


## 15、SpringBoot集成Redis操作

* SpringBoot操作数据-spring-data:jpa jdbc mongodb redis

* SpringData也是和SpringBoot齐名的项目

* 在springboot2.x之后，原来使用的jedis替换位了lettuce

  * lettuce：采用netty，实例可以在多个线程中进行共享，不存在线程不安全的情况，可以减少线程数量，类似NIO模式
  * jedis：采用直连，多个线程操作的话是不安全的，想要避免不安全的使用jedis pool连接池。类似BIO模式

* 源码

  ```java
  @Bean
  @ConditionalOnMissingBean(
    name = {"redisTemplate"}
  )//可以自己自定义一个redisTemplate替换
  @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //默认的RedisTemplate没有过多的设置，redis对象都是需要序列化
    //两个范型都是object，object的类型我们使用时需要强制转换
    RedisTemplate<Object, Object> template = new RedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
  
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnSingleCandidate(RedisConnectionFactory.class)//由于string时redis中最常使用的类型，所以说单独提出来一个bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    return new StringRedisTemplate(redisConnectionFactory);
  }
  ```

### 1、整合

* 导入依赖

  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
    
  <!--往下一层-->
  <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-redis</artifactId>
      <version>2.7.2</version>
      <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>io.lettuce</groupId>
    <artifactId>lettuce-core</artifactId>
    <version>6.1.9.RELEASE</version>
    <scope>compile</scope>
  </dependency>
  ```

* 配置连接

  ```yaml
  spring:
    redis:
      host: 192.168.1.103
      port: 6379
      lettuce:
        pool:
          max-active: 20
          
  ```

* 测试类

  ```java
  package com.smc.redis02springboot;
  
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.data.redis.connection.RedisConnection;
  import org.springframework.data.redis.core.HashOperations;
  import org.springframework.data.redis.core.ListOperations;
  import org.springframework.data.redis.core.RedisTemplate;
  import org.springframework.data.redis.core.ValueOperations;
  
  @SpringBootTest
  class Redis02SpringbootApplicationTests {
  
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
      //获取连接
      //        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
      //        connection.flushAll();
      //        connection.flushDb();
      //        connection.select(3); //切换数据库
      //redisTemplate 操作不同的数据类型，api和我们指令类似
      //操作字符串
      ValueOperations valueOperations = redisTemplate.opsForValue();
      valueOperations.set("hello","书蒙尘redis");
      System.out.println(valueOperations.get("hello"));
      //操作list
      ListOperations listOperations = redisTemplate.opsForList();
      //操作hash
      HashOperations hashOperations = redisTemplate.opsForHash();
    }
  
  }
  
  ```

### 2、常见问题

* redis不能存储未序列化对象，会报错

  ```java
   @Test
  void test() throws JsonProcessingException {
    User user = new User("书蒙尘", 26);
    ValueOperations valueOperations = redisTemplate.opsForValue();
    //        String jsonUser = new ObjectMapper().writeValueAsString(user); //将对象字符串化
    valueOperations.set("user",user);
    System.out.println(valueOperations.get("user"));
  }
  ```

  ![](/Users/smc/Desktop/smc/工具学习/redis/resource/截屏2022-08-11 16.32.38.png)

* 在企业开发中对象都需要序列化

  ```java
  @Component
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public class User implements Serializable {
      private String name;
  
      private Integer age;
  }
  ```

* 编写自己的template序列化配置类,企业开发中一般不使用原生的

  ```java
  package com.smc.redis02springboot.config;
  
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
  import org.springframework.data.redis.core.RedisTemplate;
  import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
  import org.springframework.data.redis.serializer.StringRedisSerializer;
  
  /**
   * @Date 2022/8/11
   * @Author smc
   * @Description:
   */
  @Configuration
  public class RedisConfig {
      //编写自己的redisTemplate
      @Bean
      public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory)
      {
          RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
  
          redisTemplate.setConnectionFactory(lettuceConnectionFactory);
          //设置key序列化方式string
          redisTemplate.setKeySerializer(new StringRedisSerializer());
          //设置value的序列化方式json
          redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
          redisTemplate.setHashKeySerializer(new StringRedisSerializer());
          redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
          redisTemplate.afterPropertiesSet();
  
          return redisTemplate;
      }
  
  }
  
  ```

* redisUtil封装redis

  ```java
  package com.smc.redis02springboot.utils;
  
  import lombok.Builder;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.dao.DataAccessException;
  import org.springframework.data.redis.connection.RedisConnection;
  import org.springframework.data.redis.connection.RedisStringCommands;
  import org.springframework.data.redis.core.*;
  import org.springframework.data.redis.core.types.Expiration;
  import org.springframework.data.redis.serializer.RedisSerializer;
  import org.springframework.stereotype.Component;
  import org.springframework.util.CollectionUtils;
  
  import java.util.Collection;
  import java.util.List;
  import java.util.Map;
  import java.util.Set;
  import java.util.concurrent.TimeUnit;
  
  /**
   * @Date 2022/8/11
   * @Author smc
   * @Description:
   */
  //在真实开发中，一般公司都会有自己的redis工具类
  @Component
  @Slf4j
  @Builder
  public final class RedisUtil {
  
      @Autowired
      private RedisTemplate redisTemplate;
  
      //    RedisProperties.Jedis jedis = new CtoeduJedisPool().getJedis();
  
      public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
          this.redisTemplate = redisTemplate;
      }
      //=============================common============================
      /**
       * 指定缓存失效时间
       * @param key 键
       * @param time 时间(秒)
       * @return
       */
      public boolean expire(String key,long time){
          try {
              if(time>0){
                  redisTemplate.expire(key, time, TimeUnit.SECONDS);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 根据key 获取过期时间
       * @param key 键 不能为null
       * @return 时间(秒) 返回0代表为永久有效
       */
      public long getExpire(String key){
          return redisTemplate.getExpire(key,TimeUnit.SECONDS);
      }
  
      /**
       * 判断key是否存在
       * @param key 键
       * @return true 存在 false不存在
       */
      public boolean hasKey(String key){
          try {
              return redisTemplate.hasKey(key);
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 删除缓存
       * @param key 可以传一个值 或多个
       */
      @SuppressWarnings("unchecked")
      public void del(String ... key){
          if(key!=null&&key.length>0){
              if(key.length==1){
                  redisTemplate.delete(key[0]);
              }else{
                  redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
              }
          }
      }
  
      //============================String=============================
      /**
       * 普通缓存获取
       * @param key 键
       * @return 值
       */
      public Object get(String key){
          return key==null?null:redisTemplate.opsForValue().get(key);
      }
  
      /**
       * 普通缓存放入
       * @param key 键
       * @param value 值
       * @return true成功 false失败
       */
      public boolean set(String key,Object value) {
          try {
              redisTemplate.opsForValue().set(key, value);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
  
      }
  
      /**
       * 普通缓存放入并设置时间
       * @param key 键
       * @param value 值
       * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
       * @return true成功 false 失败
       */
      public boolean set(String key,Object value,long time){
          try {
              if(time>0){
                  redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
              }else{
                  set(key, value);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 递增
       * @param key 键
       * @param delta 要增加几(大于0)
       * @return
       */
      public long incr(String key, long delta){
          if(delta<0){
              throw new RuntimeException("递增因子必须大于0");
          }
          return redisTemplate.opsForValue().increment(key, delta);
      }
  
      /**
       * 递减
       * @param key 键
       * @param delta 要减少几(小于0)
       * @return
       */
      public long decr(String key, long delta){
          if(delta<0){
              throw new RuntimeException("递减因子必须大于0");
          }
          return redisTemplate.opsForValue().increment(key, -delta);
      }
  
      //================================Map=================================
      /**
       * HashGet
       * @param key 键 不能为null
       * @param item 项 不能为null
       * @return 值
       */
      public Object hget(String key,String item){
          return redisTemplate.opsForHash().get(key, item);
      }
  
      /**
       * 获取hashKey对应的所有键值
       * @param key 键
       * @return 对应的多个键值
       */
      public Map<Object,Object> hmget(String key){
          return redisTemplate.opsForHash().entries(key);
      }
  
      /**
       * HashSet
       * @param key 键
       * @param map 对应多个键值
       * @return true 成功 false 失败
       */
      public boolean hmset(String key, Map<String, List<String>> map){
          try {
              redisTemplate.opsForHash().putAll(key, map);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
      public boolean hmset(Map<String,String> map,String key){
          try {
              redisTemplate.opsForHash().putAll(key, map);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * HashSet 并设置时间
       * @param key 键
       * @param map 对应多个键值
       * @param time 时间(秒)
       * @return true成功 false失败
       */
      public boolean hmset(String key, Map<String,Object> map, long time){
          try {
              redisTemplate.opsForHash().putAll(key, map);
              if(time>0){
                  expire(key, time);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
  
  
      /**
       * 向一张hash表中放入数据,如果不存在将创建
       * @param key 键
       * @param item 项
       * @param value 值
       * @return true 成功 false失败
       */
      public boolean hset(String key,String item,Object value) {
          try {
              redisTemplate.opsForHash().put(key, item, value);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 向一张hash表中放入数据,如果不存在将创建
       * @param key 键
       * @param item 项
       * @param value 值
       * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
       * @return true 成功 false失败
       */
      public boolean hset(String key,String item,Object value,long time) {
          try {
              redisTemplate.opsForHash().put(key, item, value);
              if(time>0){
                  expire(key, time);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 删除hash表中的值
       * @param key 键 不能为null
       * @param item 项 可以使多个 不能为null
       */
      public void hdel(String key, Object... item){
          redisTemplate.opsForHash().delete(key,item);
      }
  
      /**
       * 判断hash表中是否有该项的值
       * @param key 键 不能为null
       * @param item 项 不能为null
       * @return true 存在 false不存在
       */
      public boolean hHasKey(String key, String item){
          return redisTemplate.opsForHash().hasKey(key, item);
      }
  
      /**
       * hash递增 如果不存在,就会创建一个 并把新增后的值返回
       * @param key 键
       * @param item 项
       * @param by 要增加几(大于0)
       * @return
       */
      public double hincr(String key, String item,double by){
          return redisTemplate.opsForHash().increment(key, item, by);
      }
  
      /**
       * hash递减
       * @param key 键
       * @param item 项
       * @param by 要减少记(小于0)
       * @return
       */
      public double hdecr(String key, String item,double by){
          return redisTemplate.opsForHash().increment(key, item,-by);
      }
  
      //============================set=============================
      /**
       * 根据key获取Set中的所有值
       * @param key 键
       * @return
       */
      public Set<Object> sGet(String key){
          try {
              return redisTemplate.opsForSet().members(key);
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
      }
  
      /**
       * 根据value从一个set中查询,是否存在
       * @param key 键
       * @param value 值
       * @return true 存在 false不存在
       */
      public boolean sHasKey(String key,Object value){
          try {
              return redisTemplate.opsForSet().isMember(key, value);
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 将数据放入set缓存
       * @param key 键
       * @param values 值 可以是多个
       * @return 成功个数
       */
      public long sSet(String key, Object...values) {
          try {
              return redisTemplate.opsForSet().add(key, values);
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
  
      /**
       * 将set数据放入缓存
       * @param key 键
       * @param time 时间(秒)
       * @param values 值 可以是多个
       * @return 成功个数
       */
      public long sSetAndTime(String key,long time,Object...values) {
          try {
              Long count = redisTemplate.opsForSet().add(key, values);
              if(time>0){
                  expire(key, time);
              }
              return count;
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
  
      /**
       * 获取set缓存的长度
       * @param key 键
       * @return
       */
      public long sGetSetSize(String key){
          try {
              return redisTemplate.opsForSet().size(key);
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
  
      /**
       * 移除值为value的
       * @param key 键
       * @param values 值 可以是多个
       * @return 移除的个数
       */
      public long setRemove(String key, Object ...values) {
          try {
              Long count = redisTemplate.opsForSet().remove(key, values);
              return count;
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
      //===============================list=================================
  
      /**
       * 获取list缓存的内容
       * @param key 键
       * @param start 开始
       * @param end 结束  0 到 -1代表所有值
       * @return
       */
      public List<Object> lGet(String key,long start, long end){
          try {
              return redisTemplate.opsForList().range(key, start, end);
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
      }
  
      /**
       * 获取list缓存的长度
       * @param key 键
       * @return
       */
      public long lGetListSize(String key){
          try {
              return redisTemplate.opsForList().size(key);
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
  
      /**
       * 通过索引 获取list中的值
       * @param key 键
       * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
       * @return
       */
      public Object lGetIndex(String key,long index){
          try {
              return redisTemplate.opsForList().index(key, index);
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
      }
  
      /**
       * 将list放入缓存
       * @param key 键
       * @param value 值
       * @return
       */
      public boolean lSet(String key, Object value) {
          try {
              redisTemplate.opsForList().rightPush(key, value);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 将list放入缓存
       * @param key 键
       * @param value 值
       * @param time 时间(秒)
       * @return
       */
      public boolean lSet(String key, Object value, long time) {
          try {
              redisTemplate.opsForList().rightPush(key, value);
              if (time > 0) {
                  expire(key, time);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 将list放入缓存
       * @param key 键
       * @param value 值
       * @return
       */
      public boolean lSet(String key, List<Object> value) {
          try {
  
              redisTemplate.opsForList().rightPushAll(key, value);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 将list放入缓存
       * @param key 键
       * @param value 值
       * @param time 时间(秒)
       * @return
       */
      public boolean lSet(String key, List<Object> value, long time) {
          try {
              redisTemplate.opsForList().rightPushAll(key, value);
              if (time > 0){
                  expire(key, time);
              }
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 根据索引修改list中的某条数据
       * @param key 键
       * @param index 索引
       * @param value 值
       * @return
       */
      public boolean lUpdateIndex(String key, long index,Object value) {
          try {
              redisTemplate.opsForList().set(key, index, value);
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
      }
  
      /**
       * 移除N个值为value
       * @param key 键
       * @param count 移除多少个
       * @param value 值
       * @return 移除的个数
       */
      public long lRemove(String key,long count,Object value) {
          try {
              Long remove = redisTemplate.opsForList().remove(key, count, value);
              return remove;
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
  
      //===============================Pipelined=================================
  
      /**
       * 功能描述: 使用pipelined批量存储
       *
       * @param: [map, seconds]
       * @return: void
       * @auther: liyiyu
       * @date: 2020/4/19 14:34
       */
      public void executePipelined(Map<String, String> map, long seconds) {
          RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
          redisTemplate.executePipelined(new RedisCallback<String>() {
              @Override
              public String doInRedis(RedisConnection connection) throws DataAccessException {
                  map.forEach((key, value) -> {
                      connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.seconds(seconds), RedisStringCommands.SetOption.UPSERT);
                  });
                  return null;
              }
          },serializer);
      }
  
  
      /**
       * 功能描述: 使用pipelined批量存储资源文件
       *
       * @param: [map, seconds]
       * @return: void
       * @auther: lixk6
       * @date: 2020/4/19 14:34
       */
  //    public void executeResourcePipelined(List<MonitorreprotinfoResponeDTO> list, long seconds) {
  //        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
  //        redisTemplate.executePipelined(new RedisCallback<String>() {
  //            @Override
  //            public String doInRedis(RedisConnection connection) throws DataAccessException {
  //                list.forEach((value) -> {
  //                    connection.set(serializer.serialize(RedisConfig.nmsResourcePrefix+"sss"), serializer.serialize(JSON.toJSONString(list.toString())),Expiration.seconds(seconds), RedisStringCommands.SetOption.UPSERT);
  //                });
  //                return null;
  //            }
  //        },serializer);
  //    }
  
      /**
       * 使用pipelined批量存储资源文件
       */
      public void test1() {
          List<Object> pipelinedResultList = redisTemplate.executePipelined(new SessionCallback<Object>() {
              @Override
              public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                  ValueOperations<String, Object> valueOperations = (ValueOperations<String, Object>) operations.opsForValue();
  
                  valueOperations.set("yzh1", "hello world");
                  valueOperations.set("yzh2", "redis");
  
                  valueOperations.get("yzh1");
                  valueOperations.get("yzh2");
  
                  // 返回null即可，因为返回值会被管道的返回值覆盖，外层取不到这里的返回值
                  return null;
              }
          });
          System.out.println("pipelinedResultList=" + pipelinedResultList);
      }
  
  }
  
  ```

  

## 16、Redis .conf详解

* 启动redis服务的配置文件

### 1、单位![image-20220811173716087](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811173716087.png)

* 配置文件对大小写不敏感

### 2、包含![image-20220811173849759](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811173849759.png)

* 可以有包含多个配置文件，类似java的import

### 3、网络

* IP绑定![image-20220811174222386](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811174222386.png)
* 保护模式![image-20220811175303110](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175303110.png)
  * 开启时只能让本地访问，或者其他地址通过密码访问
* 端口配置![image-20220811175353694](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175353694.png)

### 4、通用

* 以守护进程方式运行，默认时no，改为yes，即能够后台运行![image-20220811175642793](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175642793.png)
* 如果以后台方式运行，我们需要指定一个pid文件![image-20220811175739547](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175739547.png)
* 日志级别![image-20220811175801637](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175801637.png)
* 生成的文件位置和名称![image-20220811175904772](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175904772.png)
* 设置数据库的数量![image-20220811175923348](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811175923348.png)
* 启动时是否显示logo![image-20220811180003066](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811180003066.png)

### 5、快照

* 持久话，在规定的时间，执行了多少次操作，则会持久化到文件.rdb,.aof。redis时内存数据库，如果没有持久话，那么数据断电数据就会消失。![image-20220811180143026](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811180143026.png)

  ```shell
  save 900 1 #如果900秒内至少有一个key进行了修改，我们就进行持久化操作
  save 300 10 # 如果300s内，有至少10个key尽心隔离修改，就会进行持久化操作
  save 60 10000 # 60s内，10000个可以修改，持久化
  ```

* 持久化出错是否继续继续运行![image-20220811180532805](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811180532805.png)

* 是否压缩rdb文件，需要消耗cpu资源![image-20220811180558801](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811180558801.png)

* 保存rdb文件时进行错误的检查校验![image-20220811180636109](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811180636109.png)

* rdb文件的文件名和目录位置![image-20220811181017100](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811181017100.png)

### 6、主从复制![image-20220811181114436](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811181114436.png)

### 7、Security安全

* 设置密码（两种方式）,默认时没有密码的

  * 添加配置文件，重启服务![image-20220811181621007](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811181621007.png)

    ```shell
    127.0.0.1:6379> config get requirepass #无法操作请求，没有通过密码验证
    (error) NOAUTH Authentication required.
    127.0.0.1:6379> auth 123456 #通过密码验证通过
    OK
    127.0.0.1:6379> config get requirepass
    1) "requirepass"
    2) "123456"
    
    ```

  * 直接通过cli客户端设置密码,服务器重启后失效

    ```shell
    127.0.0.1:6379> CONFIG set requirepass 654321
    OK
    ```

### 8、客户端配置

* 限制客户端数量![image-20220811183225793](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811183225793.png)

### 9、内存配置

* 最大内存![image-20220811183411440](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811183411440.png)
* 内存达到上限的处理策略![image-20220811183508221](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811183508221.png)

### 10、append only模式 aof配置

* 默认情况下不开启aof模式，默认时使用rdb方式持久化的，大部分情况下，rdb完全够用![image-20220811184044782](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811184044782.png)
* 持久化aof文件名及路径![image-20220811184209784](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811184209784.png)
* 持久化同步![image-20220811184431247](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811184431247.png)
* 

## 17、Redis持久化

* redis时内存数据库，如果将内存中的数据库状态保存到数据库，那么一旦服务器进程退出，服务器中的数据库状态会消失，所以redis提供了持久化的功能

### 1、RDB（redis database）

* 将制定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话中的Snapshot快照，它恢复时将快照文件直接读到内存里。

* Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程不进行任何IO操作的。这就确保了极高的性能。如果需要进行大规模的数据恢复，且对数据恢复的完整性不是非常敏感，那RDB方式要比AOF更加的高效。RDB的缺点时最后一次持久化后的数据可能丢失。我们默认就是RDB，一般情况下不需要修改这个配置。![image-20220811211441001](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811211441001.png)

* **rdb保存的文件是dump.rdb文件**![image-20220811212328006](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811212328006.png)

  ```shell
  127.0.0.1:6379> SET k1 v1
  OK
  127.0.0.1:6379> set k2 v2
  OK
  127.0.0.1:6379> set k3 v3
  OK
  127.0.0.1:6379> set k4 v4 
  OK
  127.0.0.1:6379> set k5 v5
  OK
  127.0.0.1:6379> SHUTDOWN # 关闭服务
  not connected> exit
  [root@localhost bin]# ps -ef | grep redis
  root      97460  59826  0 23:31 pts/2    00:00:00 grep --color=auto redis
  not connected> exit
  #重新启动服务
  [root@localhost bin]# redis-server kconfig/redis.conf 
  [root@localhost bin]# redis-cli -p 6379
  127.0.0.1:6379> keys *
  (error) NOAUTH Authentication required.
  127.0.0.1:6379> auth 123456
  OK
  #生成rdb文件之前的文件
  127.0.0.1:6379> keys *
  1) "hello"
  2) "k1"
  3) "k3"
  4) "k2"
  5) "k5"
  6) "user"
  7) "k4"
  
  ```

  * save规则满足的情况下，会自动触发rdb规则
  * 执行flushall命令，也会触发rdb规则
  * 退出redis(` shutdown`)，也会产生rdb文件
  * 可直接在客户端执行` save`命令生成dump文件

* 如何恢复rdb文件

  * 需要多备份文案，否则会被覆盖

  * 只需要将rdb文件放在我们的redis启动目录就可以，redis启动的时候会自动检查dump.rdb

  * 查看rdb可以存放的位置

    ```shell
    127.0.0.1:6379> config get dir
    1) "dir"
    2) "/usr/local/bin"
    
    ```

* 优点：
  * 适合大规模的数据恢复
  * 对数据的完整性要求不高
* 缺点：
  * 需要一定的时间间隔操作！如果redis意外宕机了，这个最后一次修改数据就没有了
  * fork进程的时候，会占用一定的内存空间
  * 需要经常进行备份

### 2、AOF（Append Only File）

* 将我们的所有命令都记录下来，形成历史记录，恢复的时候把这个文件都执行一遍![image-20220811220511088](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811220511088.png)
* 以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话会根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作
* **AOF保存的是appendonly.aof文件**

* append![image-20220811221709407](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811221709407.png)

  * 默认时不开启的，改成yes开启,重启服务器生效
  * 如果这个aof文件有错位，则redis无法重启，我们需要修复这个aof文件
  * redis为我们提供了一个工具`redis-check-aof`,可对aof文件进行修复

  ```shell
  [root@localhost bin]# redis-check-aof --fix appendonlydir/appendonly.aof.1.incr.aof 
  Start checking Old-Style AOF
  AOF appendonlydir/appendonly.aof.1.incr.aof format error
  AOF analyzed: filename=appendonlydir/appendonly.aof.1.incr.aof, size=64, ok_up_to=52, ok_up_to_line=13, diff=12
  This will shrink the AOF appendonlydir/appendonly.aof.1.incr.aof from 64 bytes, with 12 bytes, to 52 bytes
  Continue? [y/N]: y
  Successfully truncated AOF appendonlydir/appendonly.aof.1.incr.aof
  
  ```

* 优点：

  * 每一次修改都同步，文件的完整会更加好
  * 默认每秒同步一次，可能会丢失一秒的数据
  * 从不同步，效率最高

* 缺点

  * 相对于数据文件，aof远远大于rdb，修复速度比rdb慢
  * aof运行效率比rdb慢，所以redis默认的配置就是rdb持久化

## 18、Redis实现订阅发布

* Redis发布订阅（pub/sub）是一种消息通信模式：发送这（pub）发送消息，订阅者（sub）接受信息

* redis客户端可以订阅任意数量的频道

* 订阅/发布消息图:![image-20220811225957791](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811225957791.png)

  * 消息发送者
  * 频道
  * 消息订阅者

* 命令

  * 这些命令被广泛用于构建即使通信应用，比如网络聊天室(chatroom)和实时广播，实时提醒![image-20220811231159410](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811231159410.png)

* 测试

  ```shell
  #一个客户端订阅一个频道，监听读取的信息
  127.0.0.1:6379> SUBSCRIBE shumengchen
  Reading messages... (press Ctrl-C to quit)
  1) "subscribe"
  2) "shumengchen"
  3) (integer) 1
  #另外一个客户端发送信息到指定频道
  127.0.0.1:6379> PUBLISH shumengchen hello,redis
  (integer) 1
  #订阅者接收信息
  127.0.0.1:6379> SUBSCRIBE shumengchen
  Reading messages... (press Ctrl-C to quit)
  1) "subscribe"
  2) "shumengchen"
  3) (integer) 1
  1) "message"
  2) "shumengchen"
  3) "hello,redis"
  
  ```

* 原理

  * redis是使用C实现的，通过分析Redis源码中的pubsub.c文件，了解发布和订阅机制的底层实现，借此加深对redis的理解

  * redis通过publish,subscribe和psubscribe等命令实现发布和订阅功能

  * 微信：

    * 通过subscribe命令订阅了一个频道之后，redis-server里维护了一个字典，字典的健就是一个个频道！而字典的值则是一个链表，链表中保存了所有订阅这个channel的客户端，subscribe命令的关键，就是将客户端添加到给定channel的订阅链表中![image-20220811233025708](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811233025708.png)
    * 通过PUBLISH命令向订阅者发布消息，redis-server会使用给定的频道作为键，在它所维护的channel字典中查找记录里订阅这个频道的所有客户端端链表，遍历这个链表，将消息发送给所有订阅者

    * pub/sub从字面上理解就是发布（publish）与订阅（subscribe），在redis中，你可以设定对某一个key值进行消息发布及消息订阅，当一个key值上进行消息发布后，所有订阅它的客户端都会收到相应消息。这个功能最明显的用法就是做实时消息系统，比如即时聊天，群聊等功能。

## 19、Redis主从复制

### 1、概念

* 主从复制是将一台redis服务器的数据，复制到其他的redis服务器。前者称为主节点(master/leader)，后者称为从节点（slave/follower）；数据的复制是单向的，只能从主节点到从节点，Master以写为主，slave以读为主
* 默认情况下，每台redis服务器都是主节点；且一个主节点可以有多个从节点（活没有从节点），但一个从节点只能有一个主节点
* 主从复制作用主要包括
  * 数据冗余：主从复制实现了数据的热备份，是持久化之外的一个数据冗余方式
  * 故障恢复：当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复；实际上是一种服务的冗余
  * 负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务（即写Redis数据时应用连接主节点，读redis数据时应用连接从节点），分担服务器负载；尤其是在写少读多的场景下，通过多个从节点分担读负载，可以大大提高redis服务器的并发量
  * 高可用（集群）基石：除上述作用外，主从复制还是哨兵和集群能够实施的基础，因此说主从复制时Redis高可用的基础
* 一般来说，要将redis运用于工程项目中，只使用一台redis时万万不能的（宕机，一主二从），原因如下：
  * 从结构上，单个redis服务器会发生单点故障，并且一台服务器需要处理所有的请求负载，压力较大
  * 从容量上，单个redis服务器内存容量有限，就算一台redis服务器内存容量为256G，也不能建给所有内存用户redis存储内存，一般来说，单台reids最大使用内存不应该超过20G
  * 电商网站上的商品，一般都是一次上传，无数次浏览，读多写少
  * 对于这种场景，可以使用如下这种结构：![image-20220811235114211](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220811235114211.png)
  * 主从复制，读写分离！80%的情况下都是在进行读操作！减缓服务器的压力！架构中经常使用！一主二从！

### 2、环境配置

* 只配置从库,不用配置主库

  ```shell
  127.0.0.1:6379> info replication #查看当前库的信息
  # Replication
  role:master #角色 master
  connected_slaves:0 #没有从机
  master_failover_state:no-failover
  master_replid:feeee6898d694ea6a02a88bf4251e96f2f613c09
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:0
  second_repl_offset:-1
  repl_backlog_active:0
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:0
  repl_backlog_histlen:0
  
  ```

* 复制三个配置，修改信息

  * 端口
  * pid
  * log
  * dump

* 修改完毕之后，启动3个redis服务器，可以通过进程查看

### 3、一主二从

* 默认情况下，每台Redis服务器都是主节点，我们一般情况下只用过配置从机就好

* 一主（79）二从（80，81）

  * 80配置主机![image-20220812003535021](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812003535021.png)

  ```shell
  127.0.0.1:6380> info replication
  # Replication
  role:slave
  master_host:127.0.0.1 #主机状态
  master_port:6379
  master_link_status:up
  master_last_io_seconds_ago:3
  master_sync_in_progress:0
  slave_read_repl_offset:42
  slave_repl_offset:42
  slave_priority:100
  slave_read_only:1
  replica_announced:1
  connected_slaves:0
  master_failover_state:no-failover
  master_replid:5525977e007a1e2bed33a941212686f27f8a6500
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:42
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:15
  repl_backlog_histlen:28
  
  
  #主机状态
  127.0.0.1:6379> info replication
  # Replication
  role:master
  connected_slaves:1 #多了从机的配置
  slave0:ip=127.0.0.1,port=6380,state=online,offset=182,lag=0
  master_failover_state:no-failover
  master_replid:5525977e007a1e2bed33a941212686f27f8a6500
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:182
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:1
  repl_backlog_histlen:182
  
  ```

* 81设置主机![image-20220812003811320](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812003811320.png)

  ```shell
  #从机状态
  127.0.0.1:6381> info replication
  # Replication
  role:slave
  master_host:127.0.0.1
  master_port:6379
  master_link_status:up
  master_last_io_seconds_ago:5
  master_sync_in_progress:0
  slave_read_repl_offset:462
  slave_repl_offset:462
  slave_priority:100
  slave_read_only:1
  replica_announced:1
  connected_slaves:0
  master_failover_state:no-failover
  master_replid:5525977e007a1e2bed33a941212686f27f8a6500
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:462
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:435
  repl_backlog_histlen:28
  
  
  #主机状态
  127.0.0.1:6379> info replication
  # Replication
  role:master
  connected_slaves:2
  slave0:ip=127.0.0.1,port=6380,state=online,offset=490,lag=1
  slave1:ip=127.0.0.1,port=6381,state=online,offset=490,lag=1
  master_failover_state:no-failover
  master_replid:5525977e007a1e2bed33a941212686f27f8a6500
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:504
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:1
  repl_backlog_histlen:504
  
  ```

* 在配置文件中配置时一直生效的，若通过客户端配置则重启服务后失效，且无法设置主机密码

### 4、细节

* 主机可以写，从机只能读!主机中的所有信息和数据都会自动被从机保存

  ```shell
  #主机写
  127.0.0.1:6379> set k1 v1
  OK
  127.0.0.1:6379> keys *
  1) "k1"
  #从机读
  127.0.0.1:6380> keys *
  1) "k1"
  
  #从机无法写
  127.0.0.1:6380> set k2 v2
  (error) READONLY You can't write against a read only replica.
  
  ```

* 主机宕机,从机依然还是从机状态，且保留数据，主机从新连上后依然能够备份数据

  ```shell
  #主机宕机
  127.0.0.1:6379> SHUTDOWN
  not connected> 
  
  
  127.0.0.1:6380> keys *
  1) "k1" #从机数据依然存在
  127.0.0.1:6380> info replication
  # Replication
  role:slave #依然为从机状态
  master_host:127.0.0.1
  master_port:6379
  master_link_status:down # 从机中主机状态为down
  master_last_io_seconds_ago:-1
  master_sync_in_progress:0
  slave_read_repl_offset:906
  slave_repl_offset:906
  master_link_down_since_seconds:12
  slave_priority:100
  slave_read_only:1
  replica_announced:1
  connected_slaves:0
  master_failover_state:no-failover
  master_replid:5525977e007a1e2bed33a941212686f27f8a6500
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:906
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:15
  repl_backlog_histlen:892
  
  127.0.0.1:6380> set k2 v2 #依然无法写数据
  (error) READONLY You can't write against a read only replica.
  
  #主机重连后，写数据依然能够备份数据
  127.0.0.1:6379> set k2 v2 #主机写数据
  OK
  #从机状态
  127.0.0.1:6380> keys *
  1) "k2"
  2) "k1"
  
  ```

* 从机宕机后，从机重连后依然能够获取主机在从机宕机后设置的值

  ```shell
  #从机宕机
  127.0.0.1:6380> shutdown
  not connected> 
  #主机状态
  127.0.0.1:6379> info replication
  # Replication
  role:master
  connected_slaves:1 #只有一台从机了
  slave0:ip=127.0.0.1,port=6381,state=online,offset=1280,lag=1
  master_failover_state:no-failover
  master_replid:068fdf8dc3351b6467d9d2b851f17b930d031f10
  master_replid2:5525977e007a1e2bed33a941212686f27f8a6500
  master_repl_offset:1280
  second_repl_offset:907
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:907
  repl_backlog_histlen:374
  #主机设置值
  127.0.0.1:6379> set k4 v4
  OK
  
  #从机重连后
  127.0.0.1:6380> info replication
  # Replication
  role:slave
  master_host:127.0.0.1
  master_port:6379
  master_link_status:up
  master_last_io_seconds_ago:9
  master_sync_in_progress:0
  slave_read_repl_offset:1435
  slave_repl_offset:1435
  slave_priority:100
  slave_read_only:1
  replica_announced:1
  connected_slaves:0
  master_failover_state:no-failover
  master_replid:068fdf8dc3351b6467d9d2b851f17b930d031f10
  master_replid2:0000000000000000000000000000000000000000
  master_repl_offset:1435
  second_repl_offset:-1
  repl_backlog_active:1
  repl_backlog_size:1048576
  repl_backlog_first_byte_offset:1239
  repl_backlog_histlen:197
  
  #依然能够获取主机设置的值
  127.0.0.1:6380> keys *
  1) "k2"
  2) "k4"
  3) "k1"
  
  
  ```

### 5、复制原理

* slave启动成功连接到master后会发送一个sync同步命令
* master接到命令，启动后台的存盘进程，同时收集所有接受到的用于修改数据集的命令，在后台进程执行完毕之后，master将传送整个数据文件到slave，并完成一次完全同步
* 全量复制：slave服务在连接到主服务器之后，会将其数据存盘并加载到内存中
* 增量复制：Master将新的所有收到的修改命令依次传给slave，完成同步
* 只要重新连接slave，一次全量复制就会被执行

### 6、层层链路

![image-20220812010524609](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812010524609.png)

* 这时候也可设置主从复制
* 若主机断开了连接，我们可以使用`slaveof no one`让自己成为主机。这时候老大重启了，依然无法当主机了

## 20、Redis哨兵模式（现在公司中所有的集群都用哨兵模式）

### 1、概述

* 主从切换技术的方法是：当主服务器宕机后，需要手动把一台服务器切换为主服务器，这就需要人工干预，费时费力，还会造成一段时间内服务不可用。这不是一种推荐的方式，更多时候，我们优先考虑哨兵模式。Redis从2.8开始正式提供了Sentinel（哨兵结构来解决这个问题）
* 能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库
* 哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独立运行。其原理是哨兵通过发送命令，等待Redis服务器响应，从而监控运行的多个redis实例。![image-20220812013318684](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812013318684.png)
* 哨兵模式的两个作用
  * 通过发送命令，让redis服务器返回监控其运行状态，把包括主服务器和从服务器
  * 当哨兵检测到master宕机，会自动将slave切换到master，然后通过**发布订阅模式**通知其他的从服务器，修改配置文件，让它们切换主机
* 然后一个哨兵进程对Redis服务器进行监控，可能会出现问题，为此，我们可以使用多个哨兵进行监控，这样就形成了多哨兵模式![image-20220812013711441](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812013711441.png)
* 假设主服务器宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅是哨兵1主观的认为主服务器不可用，这个现象称为**主观下线**。当后面的哨兵也检测到主服务器不可用，并且数量达到一定值时，那么哨兵之间就会进行一次投票，投票的结果由一个哨兵发起，进行failover（故障转移）操作。切换成功后，就会通过发布订阅模式，让各个哨兵把自己监控的从服务器实现切换主机，这个过程称为**客观下线**。

### 2、测试

* 我们目前的状态时一主二从

* 配置哨兵配置文件sentienl.conf

  ```shell
  #sentinel monitor 被监控的名称 host port 1
  sentinel monitor masterredis 127.0.0.1 6379 1
  # 后面的1代白逐渐挂了，slave投票看让谁接替主机，票数最多的就会成为主机
  
  #监视带有密码的redis的服务器
  #sentinel autho-pass 监控名称 密码
  sentinel auth-pass masterredis 123456
  ```

* 启动哨兵

  ```shell
  [root@localhost bin]# redis-sentinel kconfig/sentinel.conf 
  75813:X 27 Jul 2022 03:36:01.960 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
  75813:X 27 Jul 2022 03:36:01.960 # Redis version=7.0.4, bits=64, commit=00000000, modified=0, pid=75813, just started
  75813:X 27 Jul 2022 03:36:01.960 # Configuration loaded
  75813:X 27 Jul 2022 03:36:01.962 * Increased maximum number of open files to 10032 (it was originally set to 1024).
  75813:X 27 Jul 2022 03:36:01.962 * monotonic clock: POSIX clock_gettime
                  _._                                                  
             _.-``__ ''-._                                             
        _.-``    `.  `_.  ''-._           Redis 7.0.4 (00000000/0) 64 bit
    .-`` .-```.  ```\/    _.,_ ''-._                                  
   (    '      ,       .-`  | `,    )     Running in sentinel mode
   |`-._`-...-` __...-.``-._|'` _.-'|     Port: 26379
   |    `-._   `._    /     _.-'    |     PID: 75813
    `-._    `-._  `-./  _.-'    _.-'                                   
   |`-._`-._    `-.__.-'    _.-'_.-'|                                  
   |    `-._`-._        _.-'_.-'    |           https://redis.io       
    `-._    `-._`-.__.-'_.-'    _.-'                                   
   |`-._`-._    `-.__.-'    _.-'_.-'|                                  
   |    `-._`-._        _.-'_.-'    |                                  
    `-._    `-._`-.__.-'_.-'    _.-'                                   
        `-._    `-.__.-'    _.-'                                       
            `-._        _.-'                                           
                `-.__.-'                                               
  
  75813:X 27 Jul 2022 03:36:01.963 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
  75813:X 27 Jul 2022 03:36:01.976 * Sentinel new configuration saved on disk
  75813:X 27 Jul 2022 03:36:01.976 # Sentinel ID is a50fd505f723ead04e699b4ca65c0ecc26ac1248
  75813:X 27 Jul 2022 03:36:01.976 # +monitor master masterredis 127.0.0.1 6379 quorum 1
  75813:X 27 Jul 2022 03:36:32.024 # +sdown master masterredis 127.0.0.1 6379
  75813:X 27 Jul 2022 03:36:32.024 # +odown master masterredis 127.0.0.1 6379 #quorum 1/1
  75813:X 27 Jul 2022 03:36:32.024 # +new-epoch 1
  75813:X 27 Jul 2022 03:36:32.024 # +try-failover master masterredis 127.0.0.1 6379
  75813:X 27 Jul 2022 03:36:32.027 * Sentinel new configuration saved on disk
  75813:X 27 Jul 2022 03:36:32.027 # +vote-for-leader a50fd505f723ead04e699b4ca65c0ecc26ac1248 1
  75813:X 27 Jul 2022 03:36:32.027 # +elected-leader master masterredis 127.0.0.1 6379
  75813:X 27 Jul 2022 03:36:32.027 # +failover-state-select-slave master masterredis 127.0.0.1 6379
  75813:X 27 Jul 2022 03:36:32.094 # -failover-abort-no-good-slave master masterredis 127.0.0.1 6379
  75813:X 27 Jul 2022 03:36:32.180 # Next failover delay: I will not start a failover before Wed Jul 27 03:42:32 2022
  ^C75813:signal-handler (1658864248) Received SIGINT scheduling shutdown...
  75813:X 27 Jul 2022 03:37:28.690 # User requested shutdown...
  75813:X 27 Jul 2022 03:37:28.690 # Sentinel is now ready to exit, bye bye...
  [root@localhost bin]# vim kconfig/sentinel.conf 
  [root@localhost bin]# redis-sentinel kconfig/sentinel.conf 
  90225:X 27 Jul 2022 03:39:18.516 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
  90225:X 27 Jul 2022 03:39:18.517 # Redis version=7.0.4, bits=64, commit=00000000, modified=0, pid=90225, just started
  90225:X 27 Jul 2022 03:39:18.517 # Configuration loaded
  90225:X 27 Jul 2022 03:39:18.520 * Increased maximum number of open files to 10032 (it was originally set to 1024).
  90225:X 27 Jul 2022 03:39:18.520 * monotonic clock: POSIX clock_gettime
                  _._                                                  
             _.-``__ ''-._                                             
        _.-``    `.  `_.  ''-._           Redis 7.0.4 (00000000/0) 64 bit
    .-`` .-```.  ```\/    _.,_ ''-._                                  
   (    '      ,       .-`  | `,    )     Running in sentinel mode
   |`-._`-...-` __...-.``-._|'` _.-'|     Port: 26379
   |    `-._   `._    /     _.-'    |     PID: 90225
    `-._    `-._  `-./  _.-'    _.-'                                   
   |`-._`-._    `-.__.-'    _.-'_.-'|                                  
   |    `-._`-._        _.-'_.-'    |           https://redis.io       
    `-._    `-._`-.__.-'_.-'    _.-'                                   
   |`-._`-._    `-.__.-'    _.-'_.-'|                                  
   |    `-._`-._        _.-'_.-'    |                                  
    `-._    `-._`-.__.-'_.-'    _.-'                                   
        `-._    `-.__.-'    _.-'                                       
            `-._        _.-'                                           
                `-.__.-'                                               
  
  90225:X 27 Jul 2022 03:39:18.540 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
  90225:X 27 Jul 2022 03:39:18.540 # Sentinel ID is a50fd505f723ead04e699b4ca65c0ecc26ac1248
  90225:X 27 Jul 2022 03:39:18.540 # +monitor master masterredis 127.0.0.1 6379 quorum 1
  90225:X 27 Jul 2022 03:39:18.544 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ masterredis 127.0.0.1 6379
  90225:X 27 Jul 2022 03:39:18.550 * Sentinel new configuration saved on disk
  90225:X 27 Jul 2022 03:39:18.550 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ masterredis 127.0.0.1 6379
  90225:X 27 Jul 2022 03:39:18.566 * Sentinel new configuration saved on disk
  
  ```

* 如果后面主机回来，只能归并到新的主机下，当作从机，这就是哨兵模式的规则

* 优点：
  * 哨兵集群，基于主从复制模式，所有的主从配置优点，它全有
  * 主从可以切换，故障可以转移，系统的可用性就会更好
  * 哨兵模式就是主从模式的升级，手动到自动，更加健壮
* 缺点
  * redis不好在线扩容，集群容量一旦达到上限，在线扩容就十分麻烦
  * 实现哨兵模式的配置很麻烦，里面有很多选择

## 21、缓存穿透和雪崩（面试高频，工作常用）

* 服务器的高可用问题

* Redis缓存的使用，极大的提升了应用程序的性能和效率，特别是数据查询方面。但同时，它也带来了一些问题。其中，最要害的问题，就是数据的一致性问题，从严格意义上讲，这个问题无解。如果对数据的一致性要求很高，那么就不能使用缓存。
* 另外一些典型的问题就是，缓存穿透、缓存雪崩和缓存击穿。目前业界也都有比较流行的解决方案

### 1、缓存穿透（查不到）

* 概念

  *  缓存穿透的概念是用户想要查询一个数据，发现redis内存数据库中没有，也就是缓存没有命中，于是向持久层数据库查询。发现也没有，于是本次查询失败。当用户很多的时候，缓存都没有命中（例如：秒杀），于是都去请求了持久层数据库。这会给持久层数据库造成很大的压力，这时候就相当于出现了缓存穿透

* 解决方案

  * 布隆过滤器

    * 布隆过滤器是一种数据结构，对所有可能查询的参数以hash形式存储，在控制层先进性校验，不符合则丢弃，从而避免了对底层储存系统的查询压力![image-20220812022517837](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812022517837.png)

  * 缓存空对象

    * 当存储层不命中后，即时返回的空对象也将其缓存起来，同时设置一个过期时间，之后再访问这个数据就会从缓存中获取，保护后端数据源![image-20220812022702522](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812022702522.png)

    * 但是这种方法存在两个问题
      * 如果空值能够被缓存起来，这就意味着缓存需要更多的空间存储更多的键，因为这当种可能会有很多空值的键
      * 即时对空值设置了过期时间，还是会存在缓存层和存储层的数据会有一段时间窗口的不一致，这对于需要保持一致性的业务会有影响

### 2、缓存击穿（查太多）

* 概念
  * 缓存击穿，是指一个key非常热点，在不停的扛着大病发，大并发集中对这个点进行访问，当这个key失效的瞬间，持续的大并发就穿透缓存，直接请求数据库。
  * 当某个key在过期的瞬间，有大量的请求访问，这类数据一般是热点数据，由于缓存过期，会同同时访问数据库来查询最新数据，并且回写缓存，会导致数据库瞬间压力过大
* 解决方案
  * 设置热点数据永不过期
    * 从缓存层面来看，没有设置过期时间，所以不会出现热点key过期后产生的问题
  * 加互斥锁
    * 使用分布式锁，保证对于每个key同时只有一个线程去查询后端服务，其他线程没有获得分布式锁的权限，因此只需要等待即可。这种方式将高并发的压力转移到了分布式锁，因此对分布式锁的考验很大

### 3、缓存雪崩

* 概念
  * 缓存雪崩，是指在某一个时间段，缓存集中过期失效。redis宕机
  * 产生雪崩的原因之一，比如在写文本的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商品时间比较集中的进入混存，假设混存一小时。那么到凌晨一点的时候，这批赏评的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言就会产生压力波峰。于是所有的请求都到了存储层，存储层的调用量会暴增，造成存储层挂掉![image-20220812024018340](/Users/smc/Desktop/smc/工具学习/redis/resource/image-20220812024018340.png)
  * 其实集中过期，倒不是最致命，比较致命的缓存雪崩，是缓存服务器的某个节点宕机或断网。因为自然形成的缓存雪崩，一定是在某一个时间段集中创建缓存，这个时候数据库也是可以顶住压力的。无非就是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力不可预知的，很有可能瞬间就把数据库压垮
* 解决方案
  * redis高可用
    * 这个思想的含义是，既然redis有可能挂掉，那我多增设几台redis，这样一台挂掉之后还有其他还可以继续工作，其实就是搭建的集群
  * 限流降级
    * 在缓存失效后，通过加锁或者队列来控制读数据写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待
  * 数据预热
    * 数据加热的含义就是在正式部署之前，我先把可能的数据先预先访问一遍，这样大量可能访问的数据就会加载到缓存中。在即将发生大并发访问前手动触发加载缓存不同的key。设置不同的过期时间没让缓存失效的时间尽量均匀。









