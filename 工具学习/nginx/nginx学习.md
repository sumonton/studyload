# 虚拟机配置

## 1、启动网络

```shell
#代开网卡配置文件
[root@localhost ~]# vim /etc/sysconfig/network-scripts/ifcfg-ens33 
#将ONBOOT设置为启动
ONBOOT=yes
```

## 2、给虚拟机配置静态ip

```shell
[root@localhost ~]# vim /etc/sysconfig/network-scripts/ifcfg-ens33 

TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
#BOOTPROTO=dhcp
BOOTPROTO=static #修改动态dhcp为static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=b6e1c8df-f6df-4a96-bbc9-ecd6b1fffd69
DEVICE=ens33
ONBOOT=yes
IPADDR=192.168.1.102 #静态ip地址
NETMASK=255.255.255.0 #子网掩码
GATEWAY=192.168.1.1 #网关
DNS1=8.8.8.8 #DNS
DNS2=8.8.4.4


#重启网卡
[root@localhost ~]# systemctl restart network

```

# Nginx安装部署

## 1、Nginx四个发行版介绍

* Nginx开源版
  * <http://nginx.org>
* Nginx plus 商业街
  * <https://www.nginx.com>
* Openresty
  * <http://openresty.org>
* Tengine
  * <http://tengine.taobao.org>

## 2、Nginx开源版安装

* 下载安装，配置环境

  ```shell
  #下载nginx的解压包
  [root@localhost opt]# wget http://nginx.org/download/nginx-1.23.1.tar.gz
  
  #解压后，进入文档运行configure文件，查看运行环境
  [root@localhost opt]# cd nginx-1.23.1
  [root@localhost nginx-1.23.1]# ll
  总用量 808
  drwxr-xr-x. 6 1001 1001   4096 7月  27 05:38 auto
  -rw-r--r--. 1 1001 1001 319222 7月  19 22:05 CHANGES
  -rw-r--r--. 1 1001 1001 487813 7月  19 22:05 CHANGES.ru
  drwxr-xr-x. 2 1001 1001    168 7月  27 05:38 conf
  -rwxr-xr-x. 1 1001 1001   2590 7月  19 22:05 configure
  drwxr-xr-x. 4 1001 1001     72 7月  27 05:38 contrib
  drwxr-xr-x. 2 1001 1001     40 7月  27 05:38 html
  -rw-r--r--. 1 1001 1001   1397 7月  19 22:05 LICENSE
  drwxr-xr-x. 2 1001 1001     21 7月  27 05:38 man
  -rw-r--r--. 1 1001 1001     49 7月  19 22:05 README
  drwxr-xr-x. 9 1001 1001     91 7月  27 05:38 src
  [root@localhost nginx-1.23.1]# ./configure 
  
  
  #checking for OS
  # + Linux 3.10.0-1160.el7.x86_64 x86_64
  #checking for C compiler ... not found
  #./configure: error: C compiler cc is not found
  #需要安装gcc的c语言编译器
  [root@localhost nginx-1.23.1]# yum install -y gcc
  
  #./configure: error: the HTTP rewrite module requires the PCRE library.
  [root@localhost nginx-1.23.1]# yum install -y pcre-devel
  
  #./configure: error: the HTTP gzip module requires the zlib library.
  [root@localhost nginx-1.23.1]# yum install -y zlib zlib-devel
  
  
  ```

  

  * 运行环境配置成功

    <img src="/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813175236061.png" alt="image-20220813175236061" style="zoom:60%;" />

* 编译并安装

  ```shell
  #编译
  [root@localhost nginx-1.23.1]# make
  
  #安装
  [root@localhost nginx-1.23.1]# make install
  
  ```

  * 安装成功后，在/usr/local下能看到nginx![image-20220813175722989](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813175722989.png)

* 启动Nginx

  ```shell
  #进入安装目录/usr/local/nginx/sbin,执行文件
  [root@localhost sbin]# ./nginx 
  #nginx成功运行
  [root@localhost sbin]# ps -ef | grep nginx
  root      22368      1  0 05:50 ?        00:00:00 nginx: master process ./nginx
  nobody    22372  22368  0 05:50 ?        00:00:00 nginx: worker process
  root      22815 100124  0 05:51 pts/2    00:00:00 grep --color=auto nginx
  
  #快速停止
  [root@localhost sbin]# ./nginx -s stop
  #优雅关闭，在退出前完成已经接受的连接请求
  [root@localhost sbin]# ./nginx -s quit
  #重新加载配置
  [root@localhost sbin]# ./nginx -s reload
  
  
  
  ```

## 3、安装成系统服务

* 创建服务脚本

  ```shell
  #创建系统服务文件
  [root@localhost sbin]# vim /usr/lib/systemd/system/nginx.service
  
  #文件内容
  [Unit]
  Description=nginx - web server
  After=network.target remote-fs.target nss-lookup.target
  
  [Service]
  Type=forking
  PIDFile=/usr/local/nginx/logs/nginx.pid
  ExecStartPre=/usr/local/nginx/sbin/nginx -t -c /usr/local/nginx/conf/nginx.conf
  ExecStart=/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf
  ExecReload=/usr/local/nginx/sbin/nginx -s reload
  ExecStop=/usr/local/nginx/sbin/nginx -s stop
  ExecQuit=/usr/local/nginx/sbin/nginx -s quit
  PrivateTmp=true
  
  [Install]
  WantedBy=multi-user.target
  
  
  #重新加载系统服务
  [root@localhost sbin]# systemctl daemon-reload
  
  #启动服务
  [root@localhost sbin]# systemctl start nginx
  
  #开机自启动
  systemctl enable nginx.service
  ```

## 4、目录结构与基本运行原理

* 基本目录结构

  * `conf/`:放置nginx配置文件
  * `html/`:放置静态文件
    * `index.html`:默认启动页
    * `50x.thml`:错误页
  * `logs/`:日志文件
    * `access.log`:用户连接请求日志
    * `error.log`:错误日志
    * `nginx.pid`:当前程序的运行id
  * `sbin`:程序的启动文件

* 基本运行原理![image-20220813205059059](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813205059059.png)

  * nginx启动有分为主进程和子进程，主进程负责协调各个子进程![image-20220813205223907](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813205223907.png)

* Nginx基础配置

  * 最小配置文件

    <img src="/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813210425662.png" alt="image-20220813210425662" style="zoom:70%;" />

    <img src="/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813212009159.png" alt="image-20220813212009159" style="zoom:70%;" />

    <img src="/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813212637927.png" alt="image-20220813212637927" style="zoom:80%;" />

    <img src="/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813214013002.png" alt="image-20220813214013002" style="zoom:80%;" />

    

# 基本使用

## 1、虚拟主机与域名解析

### a、域名、dns、ip地址的关系，浏览器、Nginx与http协议，虚拟主机

![image-20220813215003695](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220813215003695.png)

### b、域名解析

* 通过修改本地的hosts文件,外网无法访问

  * ip  域名名称

* 外网域名

  * server_name和listen是两个结合是唯一的

  ```vbscript
  #监听端口不同
  server {
  	listen       80;
  	server_name  localhost;
  
  	location / {
  		root   /www/game; 
  		index  index.html index.htm;
  	}
  	error_page   500 502 503 504  /50x.html;
  		location = /50x.html {
  		root   html;
  	}
  }
  server {
  	listen       88;
  	server_name  localhost;
  
  	location / {
  		root   /www/game; 
  		index  index.html index.htm;
  	}
  }
  server {
  	listen       89;
  	server_name  localhost;
  
  	location / {
  		root   /www/video;
  		index  index.html index.htm;
  	}
  }
  #服务器域名不同,在外网已经配置好域名访问ip:80,则二级域名访问可配置
  server {
  	listen       80;
  server_name  game.smchen.top;
  
  	location / {
  		root   /www/game; 
  		index  index.html index.htm;
  	}
  }
  server {
  	listen       80;
  	server_name  game.smchen.top;
  
  	location / {
  		root   /www/video;
  		index  index.html index.htm;
  	}
  }
  ```

  * 同一个server_name可以配置多个域名

  * 精确匹配
  * 通配符匹配
  * 通配符结束匹配
  * 正则匹配

### c、域名解析相关企业项目实战技术架构

* 多用户二级域名
  * 通过nginx反向代理，在另一个服务器寻找数据
* 短网址
  * 通过短网址在nginx返现代理获取真实地址，然后redirect转向到真实地址
* httpdns

## 2、Nginx反向代理

### a、反向代理流程图![image-20220814141457003](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814141457003.png)

### b、正向代理流程图![image-20220814142759607](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814142759607.png)

### c、DR模型![image-20220814143011065](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814143011065.png)

### d、基于反向代理的负载均衡器

* nginx配置集群

### e、反向代理外网与内网主机配置

* 代理外网地址，转发到指定外网，外网和本机之间转换数据通过nginx进行中间代理服务器

  ![image-20220814155835999](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814155835999.png)

* 无法代理https的地址，需要另外设置
* 当某些地址不完整是可能触发网站重定向功能

## 3、负载均衡

### a、基本配置

* 使用upstream多个代理外网，多个地址轮询算法，不加端口时默认为80端口![image-20220814172344462](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814172344462.png)

### b、负载均衡策略之权重、down、backup

* 权重：设置完后能够修改轮询概率![image-20220814172903290](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814172903290.png)



* down，使请求服务地址失效![image-20220814173023595](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814173023595.png)
* backup：备用服务，当其他服务都已经失效时才使用![image-20220814173141260](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814173141260.png)

### c、其他策略

* 轮询策略
  * 默认情况使用的轮询策略，逐一转发，这种方式使用于无状态请求（无法保存用户登录状态）

* ip_hash
  * 判断来源ip地址，转发访问同一个服务器，可以保持会话
* least_conn
  * 最少连接数访问
* url_hash
  * 根据用户访问的url定向转发请求（一般用于文件在多个服务器）
* fair
  * 根据后端服务器响应时间转发请求（有流量倾斜的分享）

## 4、动静分离

* 动静分离原理![image-20220814175840022](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814175840022.png)

* 配置：将资源放到nginx本地资源目录下，请求地址都是先在本地查找，找不到再去proxy_pass找

  * 方法一：使用多个location配置对应目录

    ![image-20220814215500488](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814215500488.png)

  * 方法二：使用该政策配置

    ![image-20220814220824791](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814220824791.png)



## 5、URLRewrite伪静态配置

* 格式![image-20220814231607502](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220814231607502.png)
  * 关键字：其中error_log不能改变
  * 正则：perl兼容正则表达式语句进行规则匹配
  * 替代内容：将正则匹配的内容替换为replacement
  * flag标记：rewrite支持的flag标记
* rewrite参数的标签段位置
  * server，location，if
* flag标记说明
  * last：本条规则匹配完成后，继续向下匹配新的location URI规则
  * break：本条规则匹配完成即终止，不再匹配后面的任何规则
  * redirect：返回302临时重定向，浏览器地址会显示跳转后的URL地址
  * permanent：返回301永久重定向，浏览器地址会显示跳转后的URL地址

## 6、网关的概念、伪静态同时负载均衡

* 网关：相当于某个局域网的大门
* 配置（网关服务器）![image-20220815000756845](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815000756845.png)

## 7、防盗链与http的referer

* 当第二次访问请求资源时会在请求中带上referer，说明是原先的网点![image-20220815002022478](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815002022478.png)

## 8、防盗链的配置与none

* 在需要防盗链的location中配置，server_names ，设置一个或多个 URL ，检测 Referer 头域的值是否是这些 URL 中的某一个![image-20220815214221534](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815214221534.png)

* none ，检测Referer头域不存在的情况![image-20220815215110585](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815215110585.png)

* blocked，检测 Referer 头域的值被防火墙或者代理服务器删除或伪装的情况。这种情况该头域的值不以

  “http://” 或 “https://” 开头

## 9、使用curl测试访问防盗链

* 根据8最后的配置，是用curl进行配置

```shell
#不带referer可以访问
[root@localhost html]# curl -I http://192.168.1.102:88/image/logo.png
HTTP/1.1 200 OK
Server: nginx/1.23.1
Date: Mon, 15 Aug 2022 01:31:44 GMT
Content-Type: image/png
Content-Length: 3067
Last-Modified: Sun, 14 Aug 2022 21:18:30 GMT
Connection: keep-alive
ETag: "62f966a6-bfb"
Accept-Ranges: bytes


#referer为192.168.1.111可以访问
[root@localhost html]# curl -e "http://192.168.1.111" -I http://192.168.1.102:88/image/logo.png
HTTP/1.1 200 OK
Server: nginx/1.23.1
Date: Mon, 15 Aug 2022 01:33:20 GMT
Content-Type: image/png
Content-Length: 3067
Last-Modified: Sun, 14 Aug 2022 21:18:30 GMT
Connection: keep-alive
ETag: "62f966a6-bfb"
Accept-Ranges: bytes

#referer为192.168.1.102不可以访问
[root@localhost html]# curl -e "http://192.168.1.102" -I http://192.168.1.102:88/image/logo.png
HTTP/1.1 403 Forbidden
Server: nginx/1.23.1
Date: Mon, 15 Aug 2022 01:33:36 GMT
Content-Type: text/html
Content-Length: 153
Connection: keep-alive


```

## 10、配置错误返回页面或提示图片

* 创建错误跳转页面![image-20220815223339488](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815223339488.png)
* nginx配置文件配置错误码跳转页面![image-20220815223502050](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815223502050.png)
* 错误吗提示图片![image-20220815224602209](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815224602209.png)

## 11、高可用配置

* 高可用场景及解决方案

  * 若nginx崩溃导致无法转发服务，如何实现不额外加机器的情况下实现两台nginx动态切换。
  * 方法1:当一台nginx崩溃，将另一台ip改为那一台的ip（若崩溃的nginx重启造成ip重复），可行性不高
  * 方法2:使用**keeplived**，在两台nginx之间有一个虚拟ip，可以在两台nginx之间切换，当主nginx正常时持有，当主nginx崩溃时，备用nginx持有。

* 安装keeplived

  * 安装方式1:编译安装

    > 下载地址：https://www.keepalived.org/download.html

    * 使用`.configure`编译安装

  * 使用yum安装

    ```shell
    [root@localhost html]# yum install -y keepalived
    
    #配置文件所在位置/etc/keepalived
    [root@localhost keepalived]# vim /etc/keepalived/keepalived.conf 
    
    
    ```

  * 配置文件![image-20220815231342052](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815231342052.png)

  * 启动keepalived

    ```shell
    [root@localhost keepalived]# systemctl start keepalived
    #查看状态
    [root@localhost keepalived]# systemctl status keepalived
    ● keepalived.service - LVS and VRRP High Availability Monitor
       Loaded: loaded (/usr/lib/systemd/system/keepalived.service; disabled; vendor preset: disabled)
       Active: active (running) since 一 2022-08-15 10:34:53 CST; 46s ago
      Process: 51241 ExecStart=/usr/sbin/keepalived $KEEPALIVED_OPTIONS (code=exited, status=0/SUCCESS)
     Main PID: 51242 (keepalived)
       CGroup: /system.slice/keepalived.service
               ├─51242 /usr/sbin/keepalived -D
               ├─51243 /usr/sbin/keepalived -D
               └─51244 /usr/sbin/keepalived -D
    
    8月 15 10:35:14 localhost.localdomain Keepalived_healthcheckers[51243]: Error connecting server [192.168.201.100]:443.
    8月 15 10:35:14 localhost.localdomain Keepalived_healthcheckers[51243]: Check on service [192.168.201.100]:443 failed after 3 retry.
    8月 15 10:35:14 localhost.localdomain Keepalived_healthcheckers[51243]: Removing service [192.168.201.100]:443 from VS [192.168.200.100]:443
    8月 15 10:35:14 localhost.localdomain Keepalived_healthcheckers[51243]: Lost quorum 1-0=1 > 0 for VS [192.168.200.100]:443
    8月 15 10:35:14 localhost.localdomain Keepalived_healthcheckers[51243]: Remote SMTP server [192.168.200.1]:25 connected.
    8月 15 10:35:17 localhost.localdomain Keepalived_healthcheckers[51243]: Error reading data from remote SMTP server [192.168.200.1]:25.
    8月 15 10:35:17 localhost.localdomain Keepalived_healthcheckers[51243]: Error reading data from remote SMTP server [192.168.200.1]:25.
    8月 15 10:35:17 localhost.localdomain Keepalived_healthcheckers[51243]: Error reading data from remote SMTP server [192.168.200.1]:25.
    8月 15 10:35:17 localhost.localdomain Keepalived_healthcheckers[51243]: Error reading data from remote SMTP server [192.168.200.1]:25.
    8月 15 10:35:17 localhost.localdomain Keepalived_healthcheckers[51243]: Error reading data from remote SMTP server [192.168.200.1]:25.
    
    #查看ip地址
    [root@localhost keepalived]# ip addr
    
    
    ```

    ![image-20220815231642634](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815231642634.png)

  * 配置备用机nginx![image-20220815233007515](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220815233007515.png)

  * 在主机宕机后使用192.168.1.200也能访问到nginx备用机

  * 监控keepalived可以使用脚本监控重启keepalived

## 12、HTTPS证书配置

* 不安全的HTTP协议(对称加密)，密码在网络上传输，不安全![image-20220816215856710](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220816215856710.png)
* 非对称加密算法（不安全），公钥可在中间拦截![image-20220816220248678](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220816220248678.png)![image-20220816220756695](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220816220756695.png)
* ca机构参与保证互联网安全（第三方）![image-20220816222804454](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220816222804454.png)

## 13、给域名申请证书

* 下载指定域名证书到目录![image-20220816235916541](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220816235916541.png)

* 配置nginx配置文件![image-20220817000111435](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817000111435.png)

* 出现报错![image-20220817000256804](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817000256804.png)

* 原先没有ssl的模块参数![image-20220817000418685](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817000418685.png)

* 到原先的nginx文件下重新编译安装

  ```shell
  [root@localhost nginx-1.23.1]# ./configure --with-http_stub_status_module --with-http_ssl_module
  
  #./configure: error: SSL modules require the OpenSSL library.
  [root@localhost nginx-1.23.1]# yum -y install openssl openssl-devel
  
  #重新编译安装
  [root@localhost nginx-1.23.1]# make & make install
  
  #重新安装后，多了SSL模块
  [root@localhost sbin]# ./nginx -V
  nginx version: nginx/1.23.1
  built by gcc 4.8.5 20150623 (Red Hat 4.8.5-44) (GCC) 
  built with OpenSSL 1.0.2k-fips  26 Jan 2017
  TLS SNI support enabled
  configure arguments: --with-http_stub_status_module --with-http_ssl_module
  
  ```

* 重新启动nginx，并防火墙开放443端口![image-20220817002850231](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817002850231.png)![image-20220817002933263](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817002933263.png)

## 14、nginx配置文件里配置http协议自动跳转https

```shell
return 301 https://$server_name$request_uri;
```

## 15、Discuz论坛安装

# 高级篇

## 1、扩容

* 通过扩容提升整体吞吐量
* 扩容原则
  * 无状态原则
  * 弹性原则

### 1）单机垂直扩容：硬件资源增加

* 云服务资源增加
* 整机：IBM、浪潮、DELL、HP等
* CPU/主板：更新到主流
* 网卡：10G/40G网卡
* 磁盘：SAS（SCSI）HDD（机械），HHD（混合）、SATA SSD、PCI-e SSD、MVME SSD
  * SSD
    * 多副本机制
    * 系统盘/热点数据/数据库存储
  * HDD
    * 冷数据存储

### 2）水平扩容：集群化

#### a、会话管理

* Nginx高级负载均衡

  * ip_hash![image-20220817212927695](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817212927695.png)

    * 问题：
      * 导致ip过于集中一个服务器，比如一个网吧内可能用的都是同一个ip地址
      * 后端服务器宕机，则会导致转发到另一台没有会话的服务器
      * ip_hash是通过前面三段来hash映射，所以前三段相同，最后一段不同也会转发到同一服务器
    * 配置：在upstream里面配置ip_hash,则该ip会访问相同的服务器![image-20220817213612029](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817213612029.png)

  * 其他Hash

    * hash	$cookie_jessionId

      * 第一次登录由系统下发一个cookie文件到客户端，下次客户端会携带cookie访问服务器
      * 通过cookie来hash映射
      * cookie不同发送不同的服务器，同一个cookie发送相同过的服务器

      ![image-20220817231625543](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817231625543.png)

    * hash 	$request_uri	

      * 应用场景一：一些移动端ip无法携带cookie，则会在url后带同一个jsessionId参数，则需要通过uri来识别，转发到同一个服务器
      * 应用场景二：在资源不平均分配在不同服务器时，通过uri和哈说算法来识别转发到指定的服务器
      * 配置，uri不同hash映射不同，相同uri映射相同的服务器![image-20220817230841506](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220817230841506.png)

* 使用sticky模块完成对静态服务器的会话保持

  * sticky(google)地址：<https://bitbucket.org/nginx-goodies/nginx-sticky-module-ng/src>

  * 下载完解压缩，重新编译，并安装

    ```shell
    #--prefix前缀，存放位置
    #--add-module 添加模块
    [root@localhost nginx-1.23.1]# ./configure  --prefix=/usr/local/ --add-module=/opt/nginx-goodies-sticky/
    
    ```

  * 由于sticky版本过老报错![image-20220818212048713](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220818212048713.png)

  * 在sticky配置中添加头文件

    ```shell
    [root@localhost nginx-goodies-sticky]# vim ngx_http_sticky_misc.c 
    
    ```

    ![image-20220818214638631](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220818212309948.png)

    ```c
    #include <openssl/sha.h>
    #include <openssl/md5.h>
    
    ```

  * 重新./configure编译安装,报错![](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220818212742333.png)

  * 安装openssl-level

    ```shell
    [root@localhost nginx-1.23.1]# yum install -y openssl-devel
    
    ```

  * 重新编译安装

  * 替换编译后的nginx替换安装目录后的nginx

    ```shell
    [root@localhost nginx-1.23.1]# mv objs/nginx /usr/local/nginx/sbin/
    
    ```

#### b、Keepalive

* request和response的connection里的keepalive![image-20220818230558146](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220818230558146.png)

```shell
#关闭keepalived
keepalived_timeout 0
```

![image-20220818231236987](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220818231236987.png)

* 抓包工具charles
  * 官网:<https://www.charlesproxy.com/>
  * 破解网址:<https://www.zzzmode.com/mytools/charles/>

* 对客户端配置

  ![image-20220820223342749](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220820223342749.png)

* 对服务器配置：配置keepalive是为了让后端服务器保持长连接，不必每次请求都进行三次握手

  * 首先需要配置使用http1.1协议，以便建立高效的传输，默认使用http1.0，在http1.0中需要配置header才能在Upstream中配置，上游服务器中默认都是用端链接的，即每次请求都会在完成之后断开
  * upstream中配置
    * keeplive 100:向上游服务器的保留连接数
    * keepalive_timeout：连接保留时间
    * keepalive_requests：一个tcp复用中，可以并发接受的请求个数
  * server中的配置
    * proxy_http_version 1.1:配置http版本号，默认使用http1.0协议，需要在request中增加Connection：keep-alive，header才能够支持，而HTTP1.0默认支持
    * proxy_set_header connection "":清除close信息，使支持长连接、

![image-20220820224513237](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220820224513237.png)

* 用AB对你keepalive性能压测对比

  ```shell
  #yum安装
  [root@localhost ~]# yum install httpd-tools
  #参数说明
  #-n 即request,用于指定测试总共的执行次数
  #-c 即concurrency，用于指定的并发数
  [root@localhost ~]# ab -n 100000 -c 30 http://smchen.top/ 
  #执行10万次，并发数为30
  ```

* nginx配置keepalive连接tomcat会比直连tomcat性能明显提升。



* http1.x报文组成

![image-20220821113543632](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821113543632.png)



#### c、反向代理内存与文件缓冲区核心流程

![image-20220821114936030](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821114936031.png)

* 对客户端的限制

  ![image-20220821122255009](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821122255009.png)

  * 获取程序的ip地址（通过一台nginx转发后）

    ![image-20220821123310565](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821123310565.png)

    * 在后端服务器通过读取header的X-Forwaded-For

  * 一些默认有用的header，（服务器接收的客户端传来的header）

    * host：IP地址（nginx默认隐藏覆盖）
    * upgrade-insecure-request:1,协议版本，可升级
    * User-agent:客户端发送请求的浏览器引擎
    * accept:浏览器可接收的数据类型
    * Accept-encoding:gzip,deflate客户端可接收网页压缩类型
    * accept-language：客户端希望接收的语言

#### d、GZIP压缩

* 原理：服务器通过gzip压缩数据，加快传输速度（需要两端支持）

* GZIP动态压缩即缺点

  ```shell
  gzip on; #开启gzip，默认不开启
  gzip_buffers 16 8k;#缓冲区大小 16块缓存区 每块8k
  gzip_comp_level 6;#压缩等级，等级越低，压缩比越低，解压越快
  gzip_http_version 1.1;#支持的http版本，默认1.1
  gzip_min_length 256;#大于256字节的进行压缩
  gzip_proxied any; #作为反向代理时，针对上游服务器返回的头信息进行压缩，any无条件压缩
  gzip_vary on;#在http同上多一个vary信息，可配可不配
  gzip_types text/plain application/x-javascript text/css application/xml; #对什么类型的数据进行压缩
  gzip_types 
  application/atom+xml application/javascript application/json 
  application/rss+xml application/vnd.ms-fontobject application/x-font-ttf 
  application/x-web-app-manifest+json application/xhtml+xml application/xml 
  font/opentype image/svg+xml image/x-icon text/css text/plain text/javascript 
  text/x-component; # 对什么类型的数据进行压缩，与上上类似;
  gzip_disable "msie [1-6]"; #禁止ie6以下版本
  ```

  ![image-20220821131619305](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821131619305.png)

  * 开启了gzip动态压缩后，无法使用sendfile

* Gzip静态压缩

  ```shell
  #安装nginx静态压缩模块，由之前安装过其他模块，若编译时只安装这个这个模块，覆盖时会造成缺失
  #先查看已经安装过的模块
  [root@localhost sbin]# ./nginx status
  nginx: invalid option: "status"
  [root@localhost sbin]# ./nginx -s status
  nginx: invalid option: "-s status"
  [root@localhost sbin]# ./nginx -V
  nginx version: nginx/1.23.1
  built by gcc 4.8.5 20150623 (Red Hat 4.8.5-44) (GCC) 
  configure arguments: --prefix=/usr/local/nginx/ --with-http_gzip_static_module
  
  #配置编译安装新模块
  [root@localhost nginx-1.23.1]# ./configure --with-http_stub_status_module --with-http_ssl_module --with-http_gzip_static_module
  [root@localhost nginx-1.23.1]# make
  
  #配分原先的，复制新的
  [root@localhost nginx-1.23.1]# mv /usr/local/nginx/sbin/nginx /usr/local/nginx/sbin/nginx.old
  [root@localhost nginx-1.23.1]# cp objs/nginx /usr/local/nginx/sbin/
  
  #查看最新的模块
  [root@localhost sbin]# ./nginx -V
  nginx version: nginx/1.23.1
  built by gcc 4.8.5 20150623 (Red Hat 4.8.5-44) (GCC) 
  built with OpenSSL 1.0.2k-fips  26 Jan 2017
  TLS SNI support enabled
  configure arguments: --with-http_stub_status_module --with-http_ssl_module --with-http_gzip_static_module
  
  ```

  * gzip_static配置文件配置

    ```shell
    #与动态压缩放的位置相同
    gzip_static on|off|always #on开启，off关闭，always不管客户端是否支持gzip都发送
    ```

  * 可添加gunzip静态解压缩模块来配合always使用

    ```shell
    [root@localhost nginx-1.23.1]# ./configure --with-http_stub_status_module --with-http_ssl_module --with-http_gzip_static_module --with-http_gunzip_module
    
    #剩下相同
    [root@localhost sbin]# ./nginx -V
    nginx version: nginx/1.23.1
    built by gcc 4.8.5 20150623 (Red Hat 4.8.5-44) (GCC) 
    built with OpenSSL 1.0.2k-fips  26 Jan 2017
    TLS SNI support enabled
    configure arguments: --with-http_stub_status_module --with-http_ssl_module --with-http_gzip_static_module --with-http_gunzip_module
    
    ```

  * Gunzip配置文件

    ```shell
    #与静态压缩放的位置相同
    gunzip on|off;
    ```

* 第三方zip模块Brotli与模块化加载（仅支持https）
  * Ngx：<https://github.com/google/ngx_brotli>
  * brotli：<https://github.com/google/brotli>

#### e、合并请求（减少并发请求）

* 文本
* Concat模块原理及配置
  * 下载地址：<https://github.com/alibaba/nginx-http-concat>
* 多媒体压缩
  * 图片：传回一张图片由多张，用css区指定区域的图片

#### f、资源静态化方案

* 前端：关联->动态引用，节约服务器的计算资源，消耗请求数
* 后端：SSI<http://nginx.org/en/docs/http/ngx_http_ssi_module.html>

![image-20220821153443516](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821153443516.png)

* 合并文件输出

* 资源静态同步

  * rsync：是一个远程数据同步工具，可通过LAN/WAN快速同步多台主机之间的文件，也可以使用rsync同步本地硬盘中的不同目录。rsync是用于替代rcp的一给工具，rsync使用所谓的rsync算法进行数据同步，这种算法只传送两个文件的不同部分，而不是每次都整份传送，因此速度快
  * rsync基于inotirfy开发

  ![image-20220821160019337](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821160019337.png)

  * rsync有三种模式

    * 本地模式（类似cp命令）
    * 远程模式（类似scp命令）
    * 守护进程（socket进程，是rsync的重要功能）

  * 配置

    ```shell
    #两台服务器都安装
    [root@localhost ~]# yum install -y rsync
    #配置文件位置/etc/rsyncd.conf，添加文件位置
    [ftp]
            path = /usr/local/nginx/html
    ```

    ![image-20220821160835164](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821160835164.png)

    ```shell
    #启动rsync
    [root@localhost etc]# rsync --daemon
    [root@localhost etc]# ps -ef | grep rsync
    root      82503      1  0 01:39 ?        00:00:00 rsync --daemon
    root      82617  76333  0 01:39 pts/0    00:00:00 grep --color=auto rsync
    
    #目标服务器展示愿服务器文件，原服务器需要开放873端口
    [root@localhost ~]# rsync --list-only 192.168.1.109::ftp/
    drwxr-xr-x             69 2022/08/15 10:05:52 .
    -rw-r--r--            551 2022/08/15 09:52:05 401.html
    -rw-r--r--            527 2022/08/15 09:38:55 50x.html
    -rw-r--r--            643 2022/08/14 03:13:28 index.html
    drwxr-xr-x          4,096 2022/08/15 10:04:53 image
    #查看目标服务器本地文件
    [root@localhost ~]# ll /usr/local/nginx/html/
    总用量 8
    -rw-r--r--. 1 root root 497 7月  22 07:42 50x.html
    -rw-r--r--. 1 root root 650 7月  22 07:44 index.html
    
    #目标服务器同步源服务器文件
    [root@localhost ~]# rsync -avz 192.168.1.109::ftp/ /usr/local/nginx/html/
    
    ```

  * 增加安全认证

    ```shell
    #添加密码
    [root@localhost etc]# echo "sgg:111" >> /etc/rsync.pwd
    #修改配置文件
    
    ```

    ![image-20220821163435584](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821163435584-1070876.png)

    ```shell
    #原服务器修改权限(权限太高可能无法拉取)
    [root@localhost etc]# chmod 600 rsync.pwd
    
    #目标服务器请求，需要输入密码
    [root@localhost ~]# rsync --list-only smc@192.168.1.109::ftp/
    Password: 
    drwxr-xr-x             69 2022/08/15 10:05:52 .
    -rw-r--r--            551 2022/08/15 09:52:05 401.html
    -rw-r--r--            527 2022/08/15 09:38:55 50x.html
    -rw-r--r--            643 2022/08/14 03:13:28 index.html
    drwxr-xr-x          4,096 2022/08/15 10:04:53 image
    
    #目标服务器添加验证
    [root@localhost ~]# echo "111" >> /etc/rsync.pwd.client
    
    [root@localhost etc]# chmod 600 rsync.pwd.client
    
    [root@localhost etc]# rsync --list-only --password-file=/etc/rsync.pwd.client smc@192.168.1.109::ftp/
    
    #拉取全部
    [root@localhost etc]# rsync -avz --delete --password-file=/etc/rsync.pwd.client smc@192.168.1.109::ftp/ /usr/local/nginx/html/
    
    ```

  * 使用脚本定时拉取

  * 实时推送

    * 配置目标服务器

      ![image-20220821165302820](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821165302820.png)

      ```shell
      #修改文件权限
      [root@localhost etc]# chmod 600 /etc/rsync.pwd
      
      
      #源服务器请求,注意密码文件权限
      [root@localhost etc]# rsync --list-only --password-file=/etc/rsync.pwd.server smc@192.168.1.110::ftp/
      drwxr-xr-x             69 2022/08/15 10:05:52 .
      -rw-r--r--            551 2022/08/15 09:52:05 401.html
      -rw-r--r--            527 2022/08/15 09:38:55 50x.html
      -rw-r--r--            643 2022/08/14 03:13:28 index.html
      drwxr-xr-x          4,096 2022/08/15 10:04:53 image
      
      ```

    * 实现推的操作

      * 在目标服务器配置,使源服务器可以写入目标服务器文件夹![image-20220821170444336](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821170444336.png)

      ```shell
      #重启目标服务器进程，源服务器进行推动作
      [root@localhost etc]# rsync -avz --delete --password-file=/etc/rsync.pwd.server /usr/local/nginx/html/ smc@192.168.1.110::ftp/ 
      
      ```

    * 实现实时推送

      * 推送端安装inotify<https://github.com/inotify-tools/inotify-tools>

        ```shell
        #下载安装包
        [root@localhost opt]# wget https://github.com/inotify-tools/inotify-tools/archive/refs/tags/3.22.6.0.tar.gz
        #安装依赖
        [root@localhost inotify-tools-3.22.6.0]# yum install autoconf
        [root@localhost inotify-tools-3.22.6.0]# yum install -y automake
        [root@localhost inotify-tools-3.22.6.0]# yum install libtool
        
        #执行autogen.sh
        [root@localhost inotify-tools-3.22.6.0]# chmod +x autogen.sh
        
        [root@localhost inotify-tools-3.22.6.0]# ./autogen.sh 
        #编译
        [root@localhost inotify-tools-3.22.6.0]# ./configure --prefix=/usr/local/notify
        
        [root@localhost inotify-tools-3.22.6.0]# make install
        
        #查看文件并执行
        [root@localhost inotify-tools-3.22.6.0]# cd /usr/local/notify/
        [root@localhost notify]# ll
        总用量 0
        drwxr-xr-x. 2 root root  45 8月  17 03:01 bin
        drwxr-xr-x. 3 root root  26 8月  17 03:01 include
        drwxr-xr-x. 2 root root 143 8月  17 03:01 lib
        drwxr-xr-x. 3 root root  17 8月  17 03:01 share
        
        [root@localhost bin]# ./inotifywait -mrq --timefmt '%Y-%m-%d %H:%M:%S' --format '%T %w%f %e' -e close_write,attrib,move,create,modify,delete /usr/local/nginx/html
        
        ```

      * 配置自动化脚本

        ```shell
        #/bin/bash
        ./inotifywait -mrq --timefmt '%Y-%m-%d %H:%M:%S' --format '%T %w%f %e' -e close_write,attrib,move,create,modify,delete /usr/local/nginx/html | while read file do
        	rsync -az --delete --password-file=/etc/rsync.pwd.server /usr/local/nginx/html/ smc@192.168.1.110::ftp/
        done
        ```

        * 监控报错纠正

          * 监听时出现权限问题是由于未赋予访问文件夹权限![image-20220821175144664](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821175144664.png)

            ```shell
            [root@localhost bin]# chmod -R 777 /usr/local/nginx/html/
            ```

          * 配置推送端用户![image-20220821180601676](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821180601676.png)

### 3）、多级缓存![image-20220823220405141](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823220405141.png)

#### a、静态资源缓存

#### b、浏览器缓存

* 强制缓存和协商缓存

  ![image-20220821210852336](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821210852336.png)

  ![image-20220821211200304](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220821211200304.png)

* 总结：

  * cache-control expires:强制缓存
    * 页面首次打开，直接读取缓存数据，刷新，会向服务器发送请求
  * etag，lastmodify:协商缓存
    * 没发生变化，返回304，不发送数据

* 浏览器缓存注意事项

  * 多级集群负载时last-modified必须保持一致
  * 还有一些场景下我们希望禁用浏览器缓存。比如轮训api上报数据
    * /v1/account/adduser?tx=hash
  * 浏览器缓存很难彻底禁用，大家的做法是加版本好，随机数等方法
  * 只缓存200响应头的数据，像3xx这类跳转的页面不需要缓存
  * 对于js，css这类可以缓存很久的数据，可以通过加版本好的方式更新内容
  * 不需要抢一致性的数据，可以缓存几秒
  * 异步加载的接口数据，可以使用ETag来校验
  * 在服务器添加Server头，有利于排查错误
  * 分为手机APP和Client以及是否遵循http协议
  * 在没有联网的状态下可以展示数据
  * 避免流量消耗过多
  * 提前下发 避免秒杀时同时下发数据造成流量段时间暴增
  * 兜底数据 在服务器崩溃和网络不可用的时候提示
  * 临时缓存 退出即清理
  * 固定缓存 展示框架这种，可能很长时间不会更新，可用随客户端下发
  * **首页**有的时候可以看作是框架，应该禁用缓存，以保证加载的资源都是最新的
  * 父子连接 页面跳转时有一部分内容不需要重新加载，可用从父菜单带过来
  * 预加载 某一些逻辑可用判断用户接下来的操作，那么可用异步加载那些资源
  * 漂亮的加载过程 异步加载 先展示框架，然后异步加载内容，避免主线程阻塞

#### c、CDN缓存

* GEOIP2:IP地理解析
  * 官方git:<https://github.com/maxmind/libmaxminddb>
  * NGINX模块:<https://github.com/leev/ngx_http_geoip2_module>
  * 下载ip资源库
    * 官网需要注册登录
    * 下载资源库:<https://www.maxmind.com/en/home>

#### d、正向代理缓存

* 配置(只能代理http请求)![image-20220822223346164](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220822223346164.png)
* 代理https请求
  * 需要第三方模块<https://github.com/chobits/ngx_http_proxy_connect_module>
  * 

#### e、反向代理缓存

* Proxy_cache：实现指定页面缓存的攻鞥

  ![image-20220822232517995](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220822232517995.png)

* 缓存清理purge工具：指定缓存清楚，配额后proxy_cache使用

  * <https://github.com/FRiCKLE/ngx_cache_purge>

* 断电续传

  * `proxy_set_header Range $http_range;`

#### f、NGINX内存缓存

* 缓存文件元数据信息跳高sendfile性能

  * Strace内核执行过程追踪神器

    ```shell
    #安装strace
    [root@localhost ~]# yum install -y strace
    #监控nginx
    [root@localhost ~]# ps -ef | grep nginx
    root      85300   8221  0 03:31 pts/0    00:00:00 grep --color=auto nginx
    root     120338      1  0 01:55 ?        00:00:00 nginx: master process /usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf
    nobody   120339 120338  0 01:55 ?        00:00:00 nginx: worker process
    nobody   120340 120338  0 01:55 ?        00:00:00 nginx: cache manager process
    [root@localhost ~]# strace -p 120339
    
    ```

  * Open_file_cache![image-20220823224227329](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823224227329.png)

* Nginx外置缓存

  * error_page

    * 错误跳转网页![image-20220823225614740](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823225614740.png)
    * * 跳转到指定页面（使404页面相对友好）![image-20220823225812260](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823225812260.png)

  * 匿名location：使页面不能访问，只能跳转访问![image-20220823230855837](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823230855837.png)

  * Memcached使用

    ```shell
    #安装Memcached
    [root@localhost ~]# yum install -y memcached
    #启动并查看memcached状态
    [root@localhost ~]# systemctl start memcached
    [root@localhost ~]# memcached-tool 127.0.0.1:11211 stats
    #使用telnet连接memcached并操作
    [root@localhost ~]# telnet 127.0.0.1 11211
    Trying 127.0.0.1...
    Connected to 127.0.0.1.
    Escape character is '^]'.
    set name 0 0 3   
    123
    STORED
    get name
    VALUE name 0 3
    123
    END
    
    ```

    * nginx配置memcache![image-20220823232511729](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220823232511729.png)

  * 使用redis2-nginx-module连接redis

    * 官网：<https://github.com/openresty/redis2-nginx-module>

    ```shell
    #安装redis
    #安装nginx-redis2模块 https://github.com/openresty/redis2-nginx-module
    [root@localhost nginx-1.23.1]# ./configure --prefix=/usr/local/nginx/ --with-http_stub_status_module --with-http_ssl_module --with-http_gzip_static_module --with-http_gunzip_module --add-module=/opt/redis2-nginx-module-0.15
    
    ```

    ![image-20220824005238108](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220824005238108.png)

#### g、外置内存缓存

#### h、上游服务器应用缓存

### 4）使用Stream模块为mysql集群透明代理

* 地址：<http://nginx.org/en/docs/stream/ngx_stream_core_module.html>

  ```shell
  #重新编译安装--with-stream
  ```

  ![image-20220824223524136](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220824223524136.png)

* 配置

  ```shell
  #和http同一级
  stream {
  	upstream mysql{
  		server 192.168.44.211:3306;
  		server 192.168.44.212:3306;
  	}
  
  	server{
  		listen 3306;
  		proxy_pass mysql;
  	}
  }
  ```

### 5）Nginx qps限制

* 漏桶算法：处理请求的速率

  * Apache jMeter
    * 

* limit_req

  * 地址：<http://nginx.org/en/docs/http/ngx_http_limit_req_module.html>

  ![image-20220824232618837](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220824232618837.png)

  * burst：五个请求缓存
  * nodealy：直接处理请求，超过五个就丢弃

* 令牌桶算法：接收请求的速率

### 6）Nginx带宽限制

* 限制宽带速度1K![image-20220825215554470](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220825215554470.png)
* 处理1M请求后限速![image-20220825215934125](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220825215934125.png)

### 7）Nginx并发数限制

* 计数器算法![image-20220825220354928](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220825220354928.png)

  

## 2、高效

### 1）日志

* 应用场景
* nginx日志模块地址:<http://nginx.org/en/docs/http/ngx_http_log_module.html>
* 像素点日志收集模块：<http://nginx.org/en/docs/http/ngx_http_empty_gif_module.html>
* 日志压缩与json格式输出
  * 使用gzip压缩日志文件

### 2）上游服务健康状态检查

* 重试机制:在上游服务器不可用时，自动切换另一台(被动)

  * 模块地址:<http://nginx.org/en/docs/http/ngx_http_proxy_module.html>

  ![image-20220825231116625](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220825231116625.png)

* 状态检查（主动健康检查）

  * tengine健康检查模块:<https://github.com/yaoweibin/nginx_upstream_check_module>
  * patch需要nginx版本一致

### 3）使用Lua对Nginx二次开发

* Lua是用c语言编写的脚本语言

  * 官网:<http://www.lua.org/>

* EmmyLua插件（IDE）

  * <https://github.com/EmmyLua/Intellij-EmmyLua>
  * <https://emmylua.github.io/>

* mac安装lua

  * mac安装lua环境

    ```shell
    brew install lua
    brew install luarocks
    luarocks install luasocket
    ```

  * Idea安装lua插件

* lua基础语法

* OpenResty

  * 安装：<http://openresty.org/cn/download.html>

  ```shell
  #下载安装包
  [root@localhost opt]# wget https://openresty.org/download/openresty-1.21.4.1.tar.gz
  #解压缩
  [root@localhost opt]# tar -zxvf openresty-1.21.4.1.tar.gz 
  #编译
  [root@localhost openresty-1.21.4.1]# ./configure --prefix=/usr/local/openresty
  #安装
  [root@localhost openresty-1.21.4.1]# gmake
  [root@localhost openresty-1.21.4.1]# gmake install
  #加载配置文件启动nginx
  [root@localhost sbin]# ./nginx -c /usr/local/openresty/nginx/conf/nginx.conf
  
  ```

  * 启动成功页面![image-20220827115022699](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220827115022699.png)

  * 测试lua代码

    ```shell
    #直接输出
    location /lua{
      default_type text/html;
      content_by_lua '
      ngx.say("<p>hello ! lua</p>")
    
      ';
    }
    #引用外部文件
    location /luains {
      default_type text/html;
      content_by_lua_file lua/hello.lua;
    }
    
    ```

  * 获取nginx系统变量

    ![image-20220827120334342](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220827120334342.png)

    ![image-20220827120532341](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220827120532341.png)

    * 获取nginx头部信息

      ```shell
      local headers = ngx.req.get_headers()
      
      ngx.say("Host:",headers["Host"],"<br/>")
      
      ngx.say("user-agent:",headers["user-agent"],"<br/>")
      
      ngx.say("user-agent:",headers.user_agent,"<br/>")
      
      for k, v in pairs(headers) do
          if type(v) == "table" then
              ngx.say(k," : ",table.concat(v,",") ,"<br/>")
          else
              ngx.say(k," : ",v,"<br/>")
          end
      end
      
      ```

    * 获取post请求参数

      ```shell
      ngx.req.read_body()
      
      ngx.say("post args begin","<br/>")
      
      local post_args = ngx.req.get_post_args()
      
      for k, v in pairs(post_args) do
          if type(v) == "table" then
              ngx.say(k, " : ",table.concat(v,", "),"<br/>")
          else
              ngx.say(k, " : ",v,"<br/>")
          end
      end
      ```

    * http协议版本

      ```shell
      ngx.say("ngx.req.http_version:",ngx.req.http_version(),"<br/>")
      ```

    * 请求方法

      ```shell
      ngx.say("ngx.req.get_method:",ngx.req.get_method(),"<br/>")
      ```

    * 原始请求头内容

      ```shell
      ngx.say("ngx.req.raw_header:",ngx.req.raw_header(),"<br/>")
      ```

  * 常用模块

    * Nginx进程缓存

      * Lua_shared_dict

        ![image-20220827125801603](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220827125801603.png)

        ```shell
        local shared_data = ngx.shared.shared_data
        
        local i = shared_data:get("i")
        
        if not i then
            i=1
            shared_data:set("i",1)
            ngx.say("lazy set i ",i,"<br/>")
        end
        
        i= shared_data:incr("i",i)
        ngx.say("i=",i,"<br/>")
        ```

      * Lua-resty-lrucache

        * Lua实现的一个简单的LRU缓存，适合Lua空间里直接缓存较为复杂的Lua数据结构：它相比ngx_lua共享内存字典可以省去昂贵的序列还操作，相比memcached这样的外部服务又能省去觉昂贵的socket操作

          <https://github.com/openresty/lua-resty-lrucache>

        ![image-20220827131406043](/Users/smc/Desktop/smc/工具学习/nginx/resource/image-20220827131406043.png)

        ```shell
        local _M = {}
        
        local lrucache = require "resty.lrucache"
        
        local c, err = lrucache.new(200)  -- allow up to 200 items in the cache
        ngx.say("count=init")
        
        if not c then
            error("failed to create the cache: " .. (err or "unknown"))
        end
        
        function _M.go()
            count = c:get("count")
            c:set("count", 100)
            ngx.say("count=",count, "<br/>")
            ngx.say("cat: ", c:get("cat"))
            if not count then
                c:set("count",1)
                ngx.say("lazy set count",c:get("count"),"<br/>")
        
            else
                c:set("count",count+1)
                ngx.say("count=",count,"<br/>")
            end
        end
        
        return _M
        
        ```

        

        * 需要关掉`lua_code_cache off;`,要不然每次执行lua脚本都会重新new对象

  * 连接redis

    * <https://github.com/openresty/lua-resty-redis>
    * Resty-redis-cluster:redis集群操作:<https://github.com/openresty/resty-redis-cluster>
    * 一般用来读取redis的数据

  * 连接mysql

    * <https://github.com/openresty/lua-resty-mysql>

  * 模版引擎

    * <https://github.com/bungle/lua-resty-template>

  * 基于Openresty的开源项目

    * kong:基于Openresty的流量网关
      * <https://github.com/kong/kong>
      * <https://konghq.com/>
    * APISIX
    * ABTestingGateWay
      * <https://github.com/CNSRE/ABTestingGateway>

  * Lua开源项目

    * WAF
      * <https://github.com/unixhot/waf>
      * https://github.com/loveshell/ngx_lua_waf

    

