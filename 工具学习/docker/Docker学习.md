## 一、Docker概述

### 1、Docker为什么出现

* 开发即运营
* 发布一个项目（jar+（Redis、Mysql、jdk）），项目带上环境打包
* 开发打包部署上线，一套流程做完
* java - jar （环境） - 打包项目带上环境（镜像） -> （Docker仓库，商店）-> 下载我们发布的镜像 -> 直接运行即可
* Docker的思想就来自于集装箱
  * 隔离：Docker核心思想，打包装箱，每个箱子都是互相隔离的

### 2、Docker的历史

* 虚拟机：相对来说笨重
* Docker容器技术，也是一种虚拟化技术
  * 隔离，镜像（最核心的环境4m + jdk + mysql） 十分小巧，运行镜像就可以了

### 3、docker

* docker是基于Go语言开发的
* 官网路径：<https://docs.docker.com/>
* 文档路径：<https://docs.docker.com/get-started/>
* 仓库地址：<https://github.com/docker>

### 4、Docker作用

* 虚拟机技术缺点
  * 资源占用十分多
  * 冗余步骤多
  * 启动很慢
* 容器化技术
  * 容器化技术不是模拟的一个完整的操作系统
* Docker和虚拟机技术的不同
  * 传统虚拟机，虚拟出一条硬件，运行一个完整的操作系统，然后在这个系统上安装和运行软件
  * 容器内的应用直接运行在宿主机的内容，容器里没有自己的内核的，也没有虚拟我们的硬件，所以就轻便了
  * 每个容器间是互相隔离的，每个容器内都有一个属于自己的文件系统，互不影响
* DevOps（开发、运维）
  * 应用更快速的交付和部署
    * 传统：一堆帮助文档，安装程序
    * Docker：打包镜像发布测试
  * 更便捷的升级和扩缩容
    * 使用了Docker之后，我们部署应用就和搭积木一样
    * 项目打包为一个镜像，扩展服务器A！服务器B
  * 更简单的系统运维
    * 在容器化之后，我们的开发，测试环境都是高度一致的
  * 更高效的计算资源利用
    * Docker是内核级别的虚拟机，可以在一个物理机上可以运行很多容器实例！服务器性能被压榨到极致



## 二、Docker安装

### 1、Docker的基本组成

![](/Users/smc/Desktop/smc/工具学习/docker/resources/3de5bd6038cb7d08a28c2672b298e687.jpeg)

* 镜像（image）：
  * docker镜像就好比是一个模版，可以通过这个模版来创建容器服务，tomcat镜像====>run====>tomcat01容器，通过这个镜像可以创建多个容器（最终服务运行或者项目运行就是在容器中的）
* 容器（container）：
  * Docker利用容器技术，独立运行一个或者一个组的应用，通过镜像来创建的
  * 启动，停止，删除，基本命令
  * 目前就可以把这个容器理解为一个简易的linux系统
* 仓库（repository）：
  * 仓库就是存放镜像的地方
  * 仓库分为公有仓库和私有仓库
  * Docker Hub（默认是国外的）
  * 阿里云。。都有容器服务器（配置镜像加速）

### 2、安装Docker

* 环境准备

  * centos7

* 安装

  * 卸载旧版本

    > ```shell
    > sudo yum remove docker \
    >                   docker-client \
    >                   docker-client-latest \
    >                   docker-common \
    >                   docker-latest \
    >                   docker-latest-logrotate \
    >                   docker-logrotate \
    >                   docker-engine
    > ```

  * 安装需要的安装包

    > Yum install -y yum-utils

  * 设置镜像的仓库

    >  #添加docker阿里云国内镜像
    >
    > yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

  * 更新yum软件包索引

    > yum makecache fast

  * 安装docker相关的内容

    > yum install docker-ce docker-ce-cli containers.io

    * Docker-ce:社区版，ee为企业包

  * 启动docker

    > systemctl start docker
    >
    > #查看版本
    >
    > docker version

  * 测试hello world

    > docker run hello-world

    ![](/Users/smc/Desktop/smc/工具学习/docker/resources/截屏2022-06-03 11.11.33.png)

  * 查看一下下载的镜像

    > docker images

    ![](/Users/smc/Desktop/smc/工具学习/docker/resources/截屏2022-06-03 11.14.44.png)

  * 卸载docker

    > ```shell
    > #卸载依赖
    > sudo yum remove docker-ce docker-ce-cli containerd.io docker-compose-plugin
    > 
    > #删除资源
    > sudo rm -rf /var/lib/docker
    > sudo rm -rf /var/lib/containerd
    > 
    > # /var/lib/docker docker的默认工作路径
    > ```

### 3、阿里云镜像加速

* 登录阿里云找到容器服务![](/Users/smc/Desktop/smc/工具学习/docker/resources/截屏2022-06-03 11.26.21.png)
* 找到镜像加速器![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220603-112727@2x.png)
* 配置使用

```shell
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://oluas55l.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 4、回顾HelloWorld流程

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220603-113652@2x.png)

### 5、底层原理

* Docker是怎么工作的？

  * Docker是一个Client-Server结构的系统，Docker的守护进程运行在主机上。通过Socket从客户端访问
  * DockerServer接收到Docker-Client的指令，就会执行这个命令

  ![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220603-114321@2x.png)

* Docker为什么比VM快![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220603-114633@2x.png)
  * Docker有着比虚拟机更少的抽象层
  * docker利用的宿主机的内核，vm需要是Guest OS
  * 新建容器的时候，docker不需要想虚拟机一样重载一个操作系统内核，避免引导。虚拟机是加载Guest OS，分钟级别的，而docker是利用宿主机的操作系统，忽略了复杂的过程

## 三、Docker命令

### 1、Docker常用命令

* 帮助命令

  ```shell
  docker version #显示docker的版本信息
  docker info #显示docker的系统信息，包括镜像和容器
  docker 命令 --help #帮助命令
  ```

* 镜像命令

  * docker images 查看所有本地主机上的镜像

  ```shell
  [root@localhost ~]# docker images
  REPOSITORY    TAG       IMAGE ID       CREATED        SIZE
  hello-world   latest    feb5d9fea6a5   8 months ago   13.3kB
  #解释
  #REPOSITORY 镜像的仓库源
  #TAG				镜像的标签
  #IMAGE ID		镜像的id
  #CREATED		镜像的创建时间
  #SIZE				镜像的大小
  
  #可选项
  	-a, --all 	#列出所有的镜像
  	-q, --quiet	#只显示镜像的id
  	
  ```

  * docker search 搜索镜像。Docker hub:<https://hub.docker.com/>

  ```shell
  [root@localhost ~]# docker search mysql
  NAME                           DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
  mysql                          MySQL is a widely used, open-source relation…   12681     [OK]       
  mariadb                        MariaDB Server is a high performing open sou…   4863      [OK]       
  percona                        Percona Server is a fork of the MySQL relati…   579       [OK]       
  phpmyadmin                     phpMyAdmin - A web interface for MySQL and M…   548       [OK]       
  
  # 可选项
  --filter=STARS=3000 #搜索出来的镜像就是STARS大于3000的镜像
  
  ```

  * Docker pull下载镜像

  ```shell
  # 下载镜像 docker pull 镜像名[:tag]
  [root@localhost ~]# docker pull mysql
  Using default tag: latest # 如果不写tag，默认就是latest
  latest: Pulling from library/mysql
  72a69066d2fe: Pull complete #分层下载，docker image和核心，联合文件系统
  93619dbc5b36: Pull complete 
  99da31dd6142: Pull complete 
  626033c43d70: Pull complete 
  37d5d7efb64e: Pull complete 
  ac563158d721: Pull complete 
  d2ba16033dad: Pull complete 
  688ba7d5c01a: Pull complete 
  00e060b6d11d: Pull complete 
  1c04857f594f: Pull complete 
  4d7cfa90e6ea: Pull complete 
  e0431212d27d: Pull complete 
  Digest: sha256:e9027fe4d91c0153429607251656806cc784e914937271037f7738bd5b8e7709 #签名
  Status: Downloaded newer image for mysql:latest
  docker.io/library/mysql:latest # 真是地址
  
  
  #指定版本下载
  [root@localhost ~]# docker pull mysql:5.7
  5.7: Pulling from library/mysql
  72a69066d2fe: Already exists 
  93619dbc5b36: Already exists 
  99da31dd6142: Already exists 
  626033c43d70: Already exists 
  37d5d7efb64e: Already exists 
  ac563158d721: Already exists 
  d2ba16033dad: Already exists 
  0ceb82207cd7: Pull complete 
  37f2405cae96: Pull complete 
  e2482e017e53: Pull complete 
  70deed891d42: Pull complete 
  Digest: sha256:f2ad209efe9c67104167fc609cca6973c8422939491c9345270175a300419f94
  Status: Downloaded newer image for mysql:5.7
  docker.io/library/mysql:5.7
  
  
  
  ```

  * docker remove ，删除镜像

  ```shell
  docker rmi -f d8eb10657875 #根据id删除指定的镜像
  docker rmi -f c20987f18b13 3218b38490ce # 删除多个镜像
   docker rmi -f $(docker images -aq) #删除全部镜像
  ```

* 容器命令

  * 说明：我们有了镜像才可以创建容器，linux，下载一个centos镜像来测试学习

    > docker pull centos

  * 新建容器并启动

    ```shell
    docker run [可选参数] image #创建容器，并运行
    #参数说明
    --name="name" #容器名字tomcat01 tomcat02 ,用来区分容器
    -d						#后台方式运行
    -it						#使用交互式方式运行，进入容器查看
    -p						#指定容器的端口 -p 8080:8080
    	-p ip:主机端口:容器端口
    	-p 主机端口:容器端口
    	-p 容器端口
    -p						#随机指定端口
    --restart=always #开机自启
    
    #测试，启动并进入容器
    [root@localhost ~]# docker run 配置项 centos 
    [root@caee88354a2e /]# 基础版本，很多命令都是不完善的
    #推出
    [root@caee88354a2e /]# exit
    
    
    ```

  * 列出所有运行的容器

    ```shell
    #docker ps 
    	#当前正在运行的容器
    -a #列出当前正在运行的容器+带出历史运行过的容器
    -n=? #显示最近运行的容器
    -q 		#只显示容器的编号
    [root@localhost ~]# docker ps
    CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
    [root@localhost ~]# docker ps -a
    CONTAINER ID   IMAGE         COMMAND       CREATED         STATUS                          PORTS     NAMES
    caee88354a2e   centos        "/bin/bash"   3 minutes ago   Exited (0) About a minute ago             hungry_ramanujan
    4b3da548bbc8   hello-world   "/hello"      2 hours ago     Exited (0) 2 hours ago                    modest_haibt
    
    ```

  * 退出容器

    ```shell
    exit #直接容器停止并推出
    Ctrl+P+Q#容器不停止
    ```

  * 删除容器

    ```shell
    docker rm 容器id	#删除指定容器,不能删除正在运行的容器，如果要强制删除 rm -f 
    docker rm -f $(docker ps -aq)	#删除所有容器
    docker ps -a -q|xargs docker rm #删除所有容器
    ```

  * 启动和停止容器的操作

    ```shell
    docker start 容器id	#启动容器
    docker restart 容器id#重启容器
    docker stop 容器id	#停止当前正在运行的容器
    docker kill 容器id	#强制停止当前容器
    
    docker system prune -af #删除所有无用images和容器
    
    ```

  * 修改容器配置

    ```shell
    docker update 容器id --restart=always(配置项)
    ```

    

* 常用其他命令

  * 后台启动容器

    ```shell
    # 命令 docker run -d 镜像名
    [root@localhost ~]# docker run -d centos
    756ad509a8725bd775728628eb80c95b1fdeba466cb0444d79500012cce8d995
    
    #问题docker ps，发现centos停止了
    #常见的坑：docker容器使用后台运行，就必须要有一个前台进程，docker发现没有应用，就会自动停止
    #nginx，容器启动后，发现自己没有提供服务，就是立刻停止，就是没有程序了
    ```

  * 查看日志

    ```shell
    docker logs -f -t --tail 条数 容器id
    #自己编写一段shell脚本，重复输出smc
    [root@localhost ~]# docker run -d centos /bin/sh -c "while true;do echo smc;sleep 1;done"
    #打印日志
    -tf						#显示日志
    -- tail number #要显示的日志条数
    [root@localhost ~]# docker logs -tf --tail 100 d306221dda43
    ```

  * 查看容器中的进程信息

    ```shell
    [root@localhost ~]# docker top d306221dda43
    
    ```

  * 查看镜像的元数据

    ```shell
    [root@localhost ~]# docker inspect d306221dda43 
    [
        {
            "Id": "d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b",
            "Created": "2022-06-03T03:01:41.038033346Z",
            "Path": "/bin/sh",
            "Args": [
                "-c",
                "while true;do echo smc;sleep 1;done"
            ],
            "State": {
                "Status": "running",
                "Running": true,
                "Paused": false,
                "Restarting": false,
                "OOMKilled": false,
                "Dead": false,
                "Pid": 39461,
                "ExitCode": 0,
                "Error": "",
                "StartedAt": "2022-06-03T03:01:41.448436873Z",
                "FinishedAt": "0001-01-01T00:00:00Z"
            },
            "Image": "sha256:5d0da3dc976460b72c77d94c8a1ad043720b0416bfc16c52c45d4847e53fadb6",
            "ResolvConfPath": "/var/lib/docker/containers/d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b/resolv.conf",
            "HostnamePath": "/var/lib/docker/containers/d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b/hostname",
            "HostsPath": "/var/lib/docker/containers/d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b/hosts",
            "LogPath": "/var/lib/docker/containers/d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b/d306221dda43b182c8d2e675d1ea1e9283a7d6e10ef335bd53c6a8a7ae42bb3b-json.log",
            "Name": "/determined_ramanujan",
            "RestartCount": 0,
            "Driver": "overlay2",
            "Platform": "linux",
            "MountLabel": "",
            "ProcessLabel": "",
            "AppArmorProfile": "",
            "ExecIDs": null,
            "HostConfig": {
                "Binds": null,
                "ContainerIDFile": "",
                "LogConfig": {
                    "Type": "json-file",
                    "Config": {}
                },
                "NetworkMode": "default",
                "PortBindings": {},
                "RestartPolicy": {
                    "Name": "no",
                    "MaximumRetryCount": 0
                },
                "AutoRemove": false,
                "VolumeDriver": "",
                "VolumesFrom": null,
                "CapAdd": null,
                "CapDrop": null,
                "CgroupnsMode": "host",
                "Dns": [],
                "DnsOptions": [],
                "DnsSearch": [],
                "ExtraHosts": null,
                "GroupAdd": null,
                "IpcMode": "private",
                "Cgroup": "",
                "Links": null,
                "OomScoreAdj": 0,
                "PidMode": "",
                "Privileged": false,
                "PublishAllPorts": false,
                "ReadonlyRootfs": false,
                "SecurityOpt": null,
                "UTSMode": "",
                "UsernsMode": "",
                "ShmSize": 67108864,
                "Runtime": "runc",
                "ConsoleSize": [
                    0,
                    0
                ],
                "Isolation": "",
                "CpuShares": 0,
                "Memory": 0,
                "NanoCpus": 0,
                "CgroupParent": "",
                "BlkioWeight": 0,
                "BlkioWeightDevice": [],
                "BlkioDeviceReadBps": null,
                "BlkioDeviceWriteBps": null,
                "BlkioDeviceReadIOps": null,
                "BlkioDeviceWriteIOps": null,
                "CpuPeriod": 0,
                "CpuQuota": 0,
                "CpuRealtimePeriod": 0,
                "CpuRealtimeRuntime": 0,
                "CpusetCpus": "",
                "CpusetMems": "",
                "Devices": [],
                "DeviceCgroupRules": null,
                "DeviceRequests": null,
                "KernelMemory": 0,
                "KernelMemoryTCP": 0,
                "MemoryReservation": 0,
                "MemorySwap": 0,
                "MemorySwappiness": null,
                "OomKillDisable": false,
                "PidsLimit": null,
                "Ulimits": null,
                "CpuCount": 0,
                "CpuPercent": 0,
                "IOMaximumIOps": 0,
                "IOMaximumBandwidth": 0,
                "MaskedPaths": [
                    "/proc/asound",
                    "/proc/acpi",
                    "/proc/kcore",
                    "/proc/keys",
                    "/proc/latency_stats",
                    "/proc/timer_list",
                    "/proc/timer_stats",
                    "/proc/sched_debug",
                    "/proc/scsi",
                    "/sys/firmware"
                ],
                "ReadonlyPaths": [
                    "/proc/bus",
                    "/proc/fs",
                    "/proc/irq",
                    "/proc/sys",
                    "/proc/sysrq-trigger"
                ]
            },
            "GraphDriver": {
                "Data": {
                    "LowerDir": "/var/lib/docker/overlay2/bf8c249d74925125be9238f872dc4c2d7f2c2111b7e9f84d30ce2b65b32caa68-init/diff:/var/lib/docker/overlay2/74b105ecf74e215a07f132eae9b4c6af1fd7ff075b9c3723289002f99913a1e0/diff",
                    "MergedDir": "/var/lib/docker/overlay2/bf8c249d74925125be9238f872dc4c2d7f2c2111b7e9f84d30ce2b65b32caa68/merged",
                    "UpperDir": "/var/lib/docker/overlay2/bf8c249d74925125be9238f872dc4c2d7f2c2111b7e9f84d30ce2b65b32caa68/diff",
                    "WorkDir": "/var/lib/docker/overlay2/bf8c249d74925125be9238f872dc4c2d7f2c2111b7e9f84d30ce2b65b32caa68/work"
                },
                "Name": "overlay2"
            },
            "Mounts": [],
            "Config": {
                "Hostname": "d306221dda43",
                "Domainname": "",
                "User": "",
                "AttachStdin": false,
                "AttachStdout": false,
                "AttachStderr": false,
                "Tty": false,
                "OpenStdin": false,
                "StdinOnce": false,
                "Env": [
                    "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
                ],
                "Cmd": [
                    "/bin/sh",
                    "-c",
                    "while true;do echo smc;sleep 1;done"
                ],
                "Image": "centos",
                "Volumes": null,
                "WorkingDir": "",
                "Entrypoint": null,
                "OnBuild": null,
                "Labels": {
                    "org.label-schema.build-date": "20210915",
                    "org.label-schema.license": "GPLv2",
                    "org.label-schema.name": "CentOS Base Image",
                    "org.label-schema.schema-version": "1.0",
                    "org.label-schema.vendor": "CentOS"
                }
            },
            "NetworkSettings": {
                "Bridge": "",
                "SandboxID": "b1f77962481bee92dd41db6f9f50fb84f7a40a07b8cc668c5e9b0a5c9fc9a23a",
                "HairpinMode": false,
                "LinkLocalIPv6Address": "",
                "LinkLocalIPv6PrefixLen": 0,
                "Ports": {},
                "SandboxKey": "/var/run/docker/netns/b1f77962481b",
                "SecondaryIPAddresses": null,
                "SecondaryIPv6Addresses": null,
                "EndpointID": "c58612432f6b7c355492d0f78f98de5bb219c07cabc5bbc37c8063369b4af601",
                "Gateway": "172.17.0.1",
                "GlobalIPv6Address": "",
                "GlobalIPv6PrefixLen": 0,
                "IPAddress": "172.17.0.2",
                "IPPrefixLen": 16,
                "IPv6Gateway": "",
                "MacAddress": "02:42:ac:11:00:02",
                "Networks": {
                    "bridge": {
                        "IPAMConfig": null,
                        "Links": null,
                        "Aliases": null,
                        "NetworkID": "3f6b5c32ef8e8358b457dff82cff9d40c43e754b4e856c13e2f893567cb11a59",
                        "EndpointID": "c58612432f6b7c355492d0f78f98de5bb219c07cabc5bbc37c8063369b4af601",
                        "Gateway": "172.17.0.1",
                        "IPAddress": "172.17.0.2",
                        "IPPrefixLen": 16,
                        "IPv6Gateway": "",
                        "GlobalIPv6Address": "",
                        "GlobalIPv6PrefixLen": 0,
                        "MacAddress": "02:42:ac:11:00:02",
                        "DriverOpts": null
                    }
                }
            }
        }
    ]
    
    ```

  * 进入当前正在运行的容器

    ```shell
    #我们的容器使用的时候一般都是在后台运行的，需要进入容器，修改一些配置
    #命令
    docker exec -it 容器id bashShell
    
    [root@localhost ~]# docker exec -it d306221dda43 /bin/bash
    [root@d306221dda43 /]# ps -ef
    UID         PID   PPID  C STIME TTY          TIME CMD
    root          1      0  0 03:01 ?        00:00:01 /bin/sh -c while true;do echo smc;sleep 1;done
    root       1722      0  0 03:30 pts/0    00:00:00 /bin/bash
    root       1745      1  0 03:30 ?        00:00:00 /usr/bin/coreutils --coreutils-prog-shebang=sleep /usr/bin/sleep 1
    root       1746   1722  0 03:30 pts/0    00:00:00 ps -ef
    
    #方式二
    docker attach 容器id
    
    [root@localhost ~]# docker attach d306221dda43
    正在执行当前的代码...
    
    #docker exec :进入容器开启一个新的终端，可以在里面操作（常用）
    #docker attach：进入当前正在执行的终端，不会启动的新的进程
    ```

  * 从容器内拷贝文件到主机上

    ```shell
    docker cp 容器id:容器内路径 容器目标路径
    
    [root@localhost opt]# docker cp aaa3b5fb59c5:/opt/test.java /opt/smc/
    [root@localhost opt]# cd smc/
    [root@localhost smc]# ll
    总用量 0
    -rw-r--r--. 1 root root 0 6月   3 11:50 test.java
    
    #拷贝是一个手动过程，未来我们使用 -v 卷的技术，可以实现docker容器和本地自动同步 
    ```

  * 自定义启动命令启动

    ```shell
    # redis-server /etc/redis/redis.conf 在images之后
    root@smchen:/data/redis# docker run -v /data/redis/redis.conf:/etc/redis/redis.conf -v /data/redis/data:/data -d --name myredis -p 6379:6379 redis redis-server /etc/redis/redis.conf
    ```

    

* 总结![](/Users/smc/Desktop/smc/工具学习/docker/resources/7a0e75e515cea8d116017b8abd8424ee.jpeg)

### 2、作业

#### 1、docker安装nginx

```shell
#1、搜索镜像，建议去docker hub搜索，可以看到帮助文档
[root@localhost smc]# docker search nginx
#2、下载镜像
[root@localhost smc]# docker pull nginx
#3、后台启动
[root@MiWiFi-R3L-srv ~]# docker run -d --name nagix01 -p 3344:80 nginx
# -d 后台运行
# --name 别名
# -p 宿主机端口，容器内部端口

#docker修改容器别名
[root@MiWiFi-R3L-srv ~]# docker rename nagix01 nginx01

#进入容器
[root@MiWiFi-R3L-srv ~]# docker exec -it nginx01 /bin/bash
root@ba3eb4e38cdf:/# whereis nginx
nginx: /usr/sbin/nginx /usr/lib/nginx /etc/nginx /usr/share/nginx
root@ba3eb4e38cdf:/# 
```

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220604-200418@2x.png)

* 思考问题：我们每次改动nginx配置文件，都需要进入容器内部，十分的麻烦，是否可以在容器外部提供一个映射路径，实现在容器修改文件名，容器内部就可以自动修改？**数据卷技术**

#### 2、docker来装一个tomcat

```shell
#官方的使用
docker run -it --rm tomcat:9.0
#我们之前的启动都是后台，停止了容器之后，容器还是可以查到 docker run -it --rm ,一般用来测试，用完就删除容器，镜像孩子

#下载启动
[root@MiWiFi-R3L-srv ~]# docker run -d -p 4455:8080 --name tomcat01 tomcat
#进入容器
[root@MiWiFi-R3L-srv ~]# docker exec -it tomcat01 /bin/bash

#发现问题：阿里云镜像默认是最小的镜像，所有不必要的都剔除掉
#保证最小的运行的项目
```

思考问题：如果容器上部署数据库，会造成删容器数据就消失了

#### 3、部署es+kibana

```shell
#es暴露的端口很多
#es十分的耗内存
#es的数据一般需要放置到安全目录

#启动
#docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:tag
[root@MiWiFi-R3L-srv ~]# docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch

# --net somenetwork ? 网络配置


docker stats #查看 cpu的状态

#修改配置文件 -e 环境配置修改
[root@MiWiFi-R3L-srv ~]# docker run -d --name elasticsearch1 -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx128m" elasticsearch

```

* 思考问题：使用kibana连接es?网络如何连接过去![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220604-225034@2x.png)

### 3、可视化

#### 1、portainer

* Docker图形话界面管理工具！提供一个后台面板供我们操作

```shell
#已弃用
[root@MiWiFi-R3L-srv ~]# docker run -d -p 8088:9000 portainer/portainer
#最新使用
[root@MiWiFi-R3L-srv ~]# docker run -d -p 8088:9000 portainer/portainer-ce
```



#### 2、Rancher(CI/CD子啊用)

## 四、Docker镜像

### 1、镜像是什么

* 镜像是一种轻量级、可执行的独立软件包，用来打包软件运行环境和基于运行环境开发的软件，它包含运行某个软件所需的所有内容，包括代码、运行时、库、环境变量和配置文件
* 所有的应用，直接到爆docker镜像，就可以直接跑起来
* 如何得到镜像：
  * 从远程仓库下载
  * 从他人那拷贝
  * 自己制作一个镜像DockerFIle

### 2、Docker镜像加载原理

* UnionFS（联合文件系统）

  * UnionFS：是一种分层、轻量级冰姐高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层叠加，同时可以将不同目录挂载到同一个虚拟文件系统下（unite servral directories into a single virtual filesystem）.Union 文件系统时Docker镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像
  * 特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把隔层呢文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录

* Docker镜像加载原理

  * docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS
  * bootfs（boot file system）主要包含bootloader和kernel，bootloader主要是引导加载kernel，Linux刚启动时会加载bootfs文件系统，在Docker镜像的最底层时bootfs，这一层与我们典型的linux/Unix系统时一样的，包含boot加载器和内核。当boot加载完成之后整个内核就都在内存中来，此时内存的使用权已由bootfs转交给内核，此时系统也会卸载bootfs
  * rootfs（root file system），在bootfs之上，包含的就是典型的Linux系统中的/dev，/proc，/bin，/etc等标准目录和文件。rootfs就是各种不通的操作系统发行版，比如Ubuntu，Centos等等

  * 对于一个精简的OS，rootfs可以很小，只需要包含最基本的命令，工具和程序库就可以了，因为底层直接用Host的kernel，自己只需要提供rootfs就可以了。由此可见对于不通的linux发行版，bootfs基本是一致的，rootfs会有差别吗因此不通的发行版可以共用bootfs

### 3、分层理解

* 为什么docker惊险更要采用这种分层的机构呢？
  * 为了资源共享。
* 所有的Docker镜像都起始于一个基本镜像层，当进行修改或增加新的内容时，就会在当前镜像之上，创建新的镜像层
  * 这种情况下，上层镜像层中的文件覆盖了底层镜像层中的文件。这样就是的文件的更新版本作为一个新镜像添加到镜像当中。
  * docker通过存储引擎（新版本采用快照极致）的方式来实现镜像层堆栈，并保证多镜像层对外展示为统一的文件系统
  * 有当新的一层是版本升级时会覆盖底层低版本，当新镜像底层有一样的时，会对其进行复用
* 特点
  * Docker镜像都是只读的，当容器启动时，一个新的可写层被加载到镜像的顶部
  * 这一层就是我们通常说的容器层，容器之下的都叫镜像层![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220604-235933@2x.png)

### 4、commit镜像

```shell
#docker commit 提交容器，成为一个新的镜像
docker commit -m="提交的描述信息" -a="作者" 容器id 目标镜像名：[TAG]
```

* 测试

  ```shell
  [root@MiWiFi-R3L-srv ~]# docker ps
  CONTAINER ID   IMAGE     COMMAND             CREATED         STATUS             PORTS                                       NAMES
  d3195349d93f   tomcat    "catalina.sh run"   3 minutes ago   Up 3 minutes       0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   wizardly_hellman
  3a28488162e8   tomcat    "catalina.sh run"   2 hours ago     Up About an hour   0.0.0.0:3366->8080/tcp, :::3366->8080/tcp   tomcat01
  [root@MiWiFi-R3L-srv ~]# docker stop d3195349d93f
  d3195349d93f
  [root@MiWiFi-R3L-srv ~]# docker commit -m="smchen_tomcat" -a="smc" 3a28488162e8 tomcat02:1.0
  sha256:1c5a450b0518ca35923f90406342bce0b976ce918dc46d81528b53e5dacdfc46
  [root@MiWiFi-R3L-srv ~]# docker images
  REPOSITORY               TAG       IMAGE ID       CREATED          SIZE
  tomcat02                 1.0       1c5a450b0518   11 seconds ago   684MB
  nginx                    latest    605c77e624dd   5 months ago     141MB
  tomcat                   latest    fb5657adc892   5 months ago     680MB
  redis                    latest    7614ae9453d1   5 months ago     113MB
  portainer/portainer-ce   latest    0df02179156a   5 months ago     273MB
  hello-world              latest    feb5d9fea6a5   8 months ago     13.3kB
  centos                   latest    5d0da3dc9764   8 months ago     231MB
  portainer/portainer      latest    580c0e4e98b0   14 months ago    79.1MB
  elasticsearch            latest    5acf0e8da90b   3 years ago      486MB
  [root@MiWiFi-R3L-srv ~]# docker run -d -p 3377:8080 1c5a450b0518
  46b0428bf6a99d31cb9411ad55ce1e692cb45e80547e14dd5f87f4f24420e8c8
  [root@MiWiFi-R3L-srv ~]# docker ps
  CONTAINER ID   IMAGE          COMMAND             CREATED          STATUS             PORTS                                       NAMES
  46b0428bf6a9   1c5a450b0518   "catalina.sh run"   17 seconds ago   Up 16 seconds      0.0.0.0:3377->8080/tcp, :::3377->8080/tcp   optimistic_booth
  3a28488162e8   tomcat         "catalina.sh run"   2 hours ago      Up About an hour   0.0.0.0:3366->8080/tcp, :::3366->8080/tcp   tomcat01
  [root@MiWiFi-R3L-srv ~]# 
  ```

### 5、docker传输

```shell
#将镜像保存成一个压缩包
docker save -o abc.tar xxx:xxx(镜像name)

#让别的机器加载这个镜像
docker load -i abc.tar
```

### 6、推送到远程仓库

```shell
#推送镜像到远程仓库
docker cp 5
```



## 五、容器数据卷

### 1、什么是容器数据卷

#### 1、docker的理念回顾

* 将应用和环境打包成一个镜像
* 如果数据都在容器中，那么我们容器删除，数据就会丢失！需求：**数据可以持久化**
* 如Mysql，容器删了，删库跑路！==需求：Mysql的数据可以存储在本地
* 容器之间可以有一个数据共享的技术！Docker容器中产生的数据，同步到本地！
* 这就是卷技术！目录的挂载，将我们容器内的目录，挂载到linux上面
* ***总结：容器的持久化和同步操作！容器间也是可以数据共享的！***

### 2、使用数据卷

#### 方式一：直接使用命令来挂载 -v

```shell
docker run -it -v 主机目录:容器内目录
#查看挂载目录
[root@localhost ~]# docker inspect 容器id

#:ro 表示只读
docker run -d -p 80:80 -v /data/html:/usr/share/nginx/html:ro -v /data/conf/nginx.conf:/etc/nginx/nginx.conf --name mynginx-02 nginx
```

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-174304@2x.png)

* docker间文件同步是双向的
  * 好处：我们以后修改只需要在本地修改即可，容器内会自动同步

### 3、实战:安装MySQL

* 思考：MySQL的数据持久化问题

```shell
#获取镜像
#运行容器，挂载
#官方测试：docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
[root@localhost ~]# docker run -d -p 3310:3306 -v /home/mysql/conf:/etc/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql01 mysql
# -d	后台运行
# -p 	端口映射
# -v	卷挂载
# -e	环境配置
# --name	配置名字


```

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-202852@2x.png)

* 假设我们将容器删除！我们挂载到本地的数据卷依旧没有丢失，这就实现了容器数据持久化功能![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-203255@2x.png)

### 4、具名和匿名挂载

```shell
# 匿名挂载 -v 容器内路径
[root@localhost data]# docker run -d -P --name nginx01 -v /etc/nginx nginx
# -P	随机映射端口
#查看所有的volume的情况
[root@localhost data]# docker volume ls
DRIVER    VOLUME NAME
local     4a6efb2b64764066f0df6b45282bb938e74d00dfaf2a70b80cfb5ab24a581e12
local     9b6f801d2f29202f3b65379e533ec748c7de36896ae3d2fb80f32841381d5ac2
local     22232f6bb80670444232d42d7e2a216e280b8e96811198d7a75612ba68364b69
local     c601098343745ae08442202f31f08f5e110dac79617a3ef7769e82d1932d3536
local     cc2e66ae08f4734afab0349a9ac0160e1c9f2d6b666cbd74c01c1a02afc59f55
local     e62e8079e2516101d5533118d0416be62c2b4265c303894283a90280d3661386
local     e6360e3705d8f1561b3a427dcf6361ba8dba90cf8160afe0bb966e038d8c70ce
local     f917f17508af6491d58eb7702cc4e1b909494729f29fb79de6e5dc7dfce8c320
#这种就是匿名挂载，我们在-v只写了容器内的路径，没有写容器外的路径

```

```shell
#具名挂载
[root@localhost data]# docker run -d -P --name nginx02 -v juming-nginx:/etc/nginx nginx
78bd230055e0c575ba8ba511e1df9296acaeebaf6b33f1af8987181580fd9bc1
[root@localhost data]# docker volume ls
DRIVER    VOLUME NAME
local     juming-nginx

# 通过 -v 卷名：容器内的路径
#	查看一下这个卷
[root@localhost data]# docker volume inspect juming-nginx
[
    {
        "CreatedAt": "2022-06-03T22:27:08+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/juming-nginx/_data",
        "Name": "juming-nginx",
        "Options": null,
        "Scope": "local"
    }
]

```

* #所有的docker容器内的卷，没有指定目录的情况下都是在 /var/lib/docker/volumes/xxxx 目录下
* 我们通过具名挂载可以方便的找到我们的一个卷，大多数情况下使用**具名挂载**

```shell
#如何确定是具名挂载还是匿名挂载，还是指定路径挂载
-v 容器内路径	#匿名挂载
-v 卷名:容器内路径	#具名挂载啊
-v	/宿主路径:/容器内路径	#指定路径瓜子啊
```

* 扩展

  ```shell
  #通过-v 容器内路径：ro rw 改变读写权限
  #ro readonly 只读，只能通过宿主机来操作，容器内部无法操作
  #rw readwrite 可读可写
  #一旦设置了容器权限，容器对我们挂载出来的内容就有限定
  [root@localhost _data]# docker run -d -P --name nginx05 -v juming-nginx1:/etc/nginx:ro nginx
  [root@localhost _data]# docker run -d -P --name nginx05 -v juming-nginx1:/etc/nginx:rw nginx
  
  
  ```

  

### 5、DockerFile

* DockerFile就是用来构建docker镜像的构建文件！命令脚本
* 通过这个脚本可以生成镜像，镜像是一层一层的，脚本就是一个一个的命令

```shell
#创建一个一个dockerfile脚本
FROM centos

VOLUME ["volume01","volume02"]

CMD echo "----end-----"
CMD /bin/bash
#这里的每个命令，就是镜像的一层

#生成镜像
[root@localhost dockerfile]# docker build -f dockerfile1 -t smc/centos:1.0 .
Sending build context to Docker daemon  2.048kB
Step 1/4 : FROM centos
 ---> 5d0da3dc9764
Step 2/4 : VOLUME ["volume01","volume02"]
 ---> Running in f093f57531a6
Removing intermediate container f093f57531a6
 ---> 2b97479d89ac
Step 3/4 : CMD echo "----end-----"
 ---> Running in 230634d93e56
Removing intermediate container 230634d93e56
 ---> a3c97f664ded
Step 4/4 : CMD /bin/bash
 ---> Running in 041362f8bf2f
Removing intermediate container 041362f8bf2f
 ---> 29e9263990eb
Successfully built 29e9263990eb
Successfully tagged smc/centos:1.0

```

```shell
#启动自己写的容器
[root@localhost dockerfile]# docker run -it 29e9263990eb /bin/bash
[root@72c515ca74e8 /]# ls
bin  dev  etc  home  lib  lib64  lost+found  media  mnt  opt  proc  root  run  sbin  srv  sys  tmp  usr  var  volume01  volume02

```

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-211658@2x.png)

* 这个卷和外部有一个同步的目录

* 查看一下卷挂载的路径

  ```shell
  [root@localhost dockerfile]# docker inspect 497a372771f3
  [root@localhost dockerfile]# cd /var/lib/docker/volumes/a008c4ea7a2a82dc4d346843c46a862ccd3e8b943b59ccd5156b647594cdf16f/_data
  [root@localhost _data]# ll
  总用量 0
  -rw-r--r--. 1 root root 0 6月   3 23:04 container.txt
  [root@localhost _data]# 
  
  ```

  ![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-212601@2x.png)

  * 这种方式未来会频繁使用，因为我们通常会构建自己的镜像
  * 假设构建镜像时候没有挂载卷，要手动镜像挂载 -v 卷名：容器内容路径

### 6、数据卷容器![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-213552@2x.png)

```shell
#启动三个容器实现同步
[root@localhost _data]# docker run -it --name docker01 29e9263990eb /bin/bash
[root@localhost _data]# docker ps
CONTAINER ID   IMAGE          COMMAND       CREATED         STATUS         PORTS     NAMES
740fff440953   29e9263990eb   "/bin/bash"   6 seconds ago   Up 6 seconds             docker01
#创建第二个容器，使用--volumes-from实现挂载同步
[root@localhost _data]# docker run -it --name docker02 --volumes-from docker01 29e9263990eb

#若删除父容器，还有其他容器使用时，挂载文件依然存在不会被删除
```

```shell
#多个mysql之间数据共享
[root@localhost ~]# docker run -d -p 3310:3306 -v /home/mysql/conf:/etc/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql01 mysql

[root@localhost ~]# docker run -d -p 3311:3306 --volumes-from mysql01 --name mysql02 mysql
```



* 结论
  * 容器之间配置的信息传递，数据卷容器的声明周期一直持续到没有容器使用为止，但是一旦你持久化到本地，这个时候，本地的数据不会删除

## 六、DockerFile

### 1、Dockerfile介绍

* dockerfile是用来构建docker镜像的文件！命令参数脚本！

* 构建步骤

  * 编写一个dockerfile文件
  * dockerbuild构建成为一个镜像
  * docker run 运行镜像
  * docker push 发布镜像（dockerHub、阿里云镜像仓库）

  ![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220606-223250@2x.png)

* 很多官方镜像都是基础包，很多功能没有，我们通常会自己搭建自己的镜像

### 2、Dockerfile的构建过程

* 基础知识
  * 每个保留关键字(指令)都是必须是大写字母
  * 执行从上到下顺序执行
  * \# 表示注释
  * 每一个指令都会创建提交一个新的镜像层，并提交
* dockerfile是面向开发的，我们以后发布项目，做镜像，就需要编写dockerfile文件，这个文件十分简单
* Docker镜像逐渐成为了企业交付的标准，必须掌握
* DockerFile：构建文件，定义了一切的步骤，源代码
* DockerImages：通过DockerFile构建生成的镜像，最终发布和运行的铲平，原来是jar war
* Docker容器：容器就是镜像运行起来提供服务的

### 3、Dockerfile的指令![](/Users/smc/Desktop/smc/工具学习/docker/resources/bdfb1c79c17db9eb360d71f4f3d1bca5.jpeg)

```shell
FROM 				# 基础镜像，一切从这里开始构建
MAINTAINER	# 镜像是谁写的，姓名+邮箱
RUN					# 镜像构建的时候需要运行的命令
ADD					# 步骤：tomcat镜像，这个tomcat的压缩包！添加内容
WORKDIR			# 镜像的工作目录 
VOLUME			# 挂载的目录
EXPOSE			# 暴露端口配置
CMD					# 指定这个容器启动的时候要运行的命令 
ENTRYPOINT	# 指定这个容器启动的时候要运行的命令，可以追加命令
ONBUILD			# 当构建一个被继承 DockerFile 这个时候就会运行ONBUILD的指令。触发指令
COPY				# 类似ADD，将我们文件拷贝到镜像中
ENV					# 构建的时候设置环境变量！
```

### 4、实战测试

* Docker Hub中99%镜像都是从这个基础镜像过来的FROM scratch，然后配置需要的软件和配置来进行构建

* 创建一个自己的centos

  * 编写dockerfile文件

  ```shell
  FROM centos:centos7.9.2009
  MAINTAINER smc<kiln1031@163.com>
  ENV MYPATH /usr/local	#健值对
  WORKDIR $MYPATH
  
  RUN yum -y install vim
  RUN yum -y install net-tools
  
  EXPOSE 80
  
  CMD echo $MYPATH
  CMD echo "---end---"
  CMD /bin/bash
  
  ```

  * 通过这个文件构建镜像

  ```shell
  #命令 docker build -f dockerfile文件路径 -t 镜像名:[tag]
  [root@localhost dockerfile]# docker build -f dockerfile2 -t smccentos:1.0 .
  ```

  * 运行镜像生成容器

  > 测试运行

* CMD和ENTRYPOINT区别

  ```shell
  CMD	#指定这个容器启动的时候要运行的命令，只有最后一个会生效，可悲替代
  ENTRYPOINT	#指定这个容器启动的时候要运行的命令，可以追加命令
  ```

  * 测试cmd

  ```shell
  #编写dockerfile文件
  FROM centos:centos7.9.2009
  CMD ["ls","-a"]
  #构建镜像
  [root@localhost dockerfile]# docker build -f dockerfile3 -t smccmd .
  
  #运行
  [root@localhost dockerfile]# docker run smccmd
  
  #想追加一个命令 -l ls -al,CMD直接追加命令报错
  [root@localhost dockerfile]# docker run smccmd -l
  docker: Error response from daemon: failed to create shim task: OCI runtime create failed: runc create failed: unable to start container process: exec: "-l": executable file not found in $PATH: unknown.
  
  ```

  * 测试ENTRYPOINT

  ```shell
  #编写dockerfile文件
  FROM centos:centos7.9.2009
  ENTRYPOINT ["ls","-a"]
  #entrypoint 可追加命令，不会报错
  [root@localhost dockerfile]# docker run 6cd1ad483187 -l
  ```

### 5、实战：Tomcat镜像

* 准备镜像文件tomcat压缩包，jdk压缩包

  ```shell
  [root@localhost tomcat]# ll
  总用量 155848
  -rw-r--r--. 1 root root  11579290 6月   4 03:32 apache-tomcat-9.0.64.tar.gz
  -rw-r--r--. 1 root root        98 6月   4 03:36 dockerFile
  -rw-r--r--. 1 root root 148003421 6月   4 03:37 jdk-8u333-linux-x64.tar.gz
  -rw-r--r--. 1 root root         0 6月   4 03:35 readme.txt
  
  ```

* 编写dockerfile文件，官方命名 **Dockerfile**,build会自动识别，就不需要-f指定了

  ```shell
  [root@localhost tomcat]# vim dockerFile 
  FROM centos:centos7.9.2009
  MAINTAINER smc<kiln1031@163.com>
  COPY readme.txt /usr/local/readme.txt
  
  ADD jdk-8u333-linux-x64.tar.gz /usr/local/
  ADD apache-tomcat-9.0.64.tar.gz /usr/local/
  RUN yum -y install vim
  
  ENV MYPATH /usr/local
  WORKDIR $MYPATH
  ENV JAVA_HOME /usr/local/jdk1.8.0_333
  ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.64
  ENV CATALINA_BASH /usr/local/apache-tomcat-9.0.64
  ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
  
  EXPOSE 8080 #容器向外暴露端口
  
  CMD /usr/local/apache-tomcat-9.0.64/bin/startup.sh && tail -F /usr/local/apache-tomcat-9.0.64/logs/catalina.out
  #创建镜像
  [root@localhost tomcat]# docker build -t smctomcat:1.0 .
  #创建容器
  [root@localhost tomcat]# docker run -it -p 8088:8080 --name tomcatInit02 -v /opt/dockerfile/tomcat/apache-tomcat-9.0.64/webapps/test:/usr/local/apache-tomcat-9.0.64/webapps/test -v /opt/dockerfile/tomcat/logs:/usr/local/apache-tomcat-9.0.64/logs 78fcd5c59045 /bin/bash
  
  
  [root@localhost tomcat]# docker run -it -p 9090:8080 --name tomcatInit01 78fcd5c59045 /bin/bash
  
  ```

  ![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220611-170428@2x.png)

### 6、发布镜像到dockerhub

* 地址<https://hub.docker.com/>注册自己的账号
* 确定这个好可以登录
* 在我们的服务器上提交自己的镜像

```shell
[root@localhost ~]# docker login --help

Usage:  docker login [OPTIONS] [SERVER]

Log in to a Docker registry.
If no server is specified, the default is defined by the daemon.

Options:
  -p, --password string   Password
      --password-stdin    Take the password from stdin
  -u, --username string   Username

```

* 登录完毕后就可以提交镜像，就是一步docker push

```shell
[root@localhost ~]# docker login -u kiln1031
Password: 
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
#增加一个tag
#docker tag local-image:tagname new-repo:tagname
#docker push new-repo:tagname
[root@localhost ~]# docker tag 78fcd5c59045 smctomcat:2.0
[root@localhost ~]# docker push smctomcat:2.0

```

* 提交的时候也按照镜像层级来提交

### 7、阿里云镜像服务

* 登录阿里云
* 找到容器镜像服务
* 创建命名空间![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220611-233402@2x.png)
* 创建容器镜像

```shell
[root@localhost ~]# docker login --username=975952433@qq.com registry.cn-hangzhou.aliyuncs.com

```

### 小结

```shell
docker save
docker load
```



## 七、Docker网络原理

### 1、理解网络Docker0

* 测试![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-002653@2x.png)

  * 三个网络

  > 问题：docker是如何处理容器网络访问的

  ```shell
  [root@localhost ~]# docker run -d -P --name tomcat01 tomcat
  
  #查看容器的内部网络地址 ip addr,发布容器启动的时候会得到一个 eth0if262 ip地址,docker分配的
  ```

* 原理

  * 每启动一个docker容器，docker容器就会容器分配一个ip，我们只要安装了docker，就会有一个网卡docker0桥接模式，使用的 技术是evth-pair技术
  * 在启动一个容器后都会在后面加一对网卡，分别在虚拟机和容器中![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-010305@2x.png)

```shell
# evth-pair 就是一堆的虚拟设备接口，他们都是成对出现的，一段连着协议，一段彼此相连
# 正因为有这个特性，evth-pair 充当一个桥梁,连接各种虚拟网络设备的
# OpenStac，Docker容器之间的连接，OVS的连接，都是使用evth-pair技术
```

* 容器和容器之间是可以互相ping通的![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-011307@2x.png)
  * 结论：tomcat01和tomcat02是公用的一个路由器，docker0，所有的容器不指定网络的情况下，都是docker0路由的，docker会给我们的容器分配一个默认的可用ip

### 2、--link

* 思考一个场景，我们编写了一个微服务，database url = ip，项目不重启，数据库ip换掉了，我们希望可以处理这个问题，可以名字来进行访问容器

```shell
docker exec -it tomcat02 ping tomcat01 #无法ping通
docker exec -it tomcat03 --link tomcat02 tomcat #能够ping通

#反向无法ping通
```

* 其实这个tomcat03就是在本地配置了tomcat02的配置

```shell
#查看hosts配置，--link就是在hosts中增加了tomcat02的ip映射
docker exec -it tomcat03 cat /etc/hosts
```

* 本质探究：--link就是我们在hosts配置中增加了一个172.18.0.3 tomcat02 
  * 我们现在玩Docker已经不建议使用--link了
  * 自定义网络！不实用docker0
  * docker0问题：他不支持容器名连接访问

### 3、自定义网络

* 查看所有的docker网络

  ```shell
  [root@localhost ~]# docker network ls
  NETWORK ID     NAME      DRIVER    SCOPE
  e992a3709ce3   bridge    bridge    local
  1848b56aacbc   host      host      local
  06973c9d4152   none      null      local
  
  ```

* 网络模式

  * bridge：桥接docker大桥（默认）
  * none：不配置网络
  * host：和宿主机共享网络
  * container：容器内网络联通（用的少）

* 测试

  ```shell
  #我们直接启动的命令 --net bridge，而这个就是我们的docker0
  [root@localhost ~]# docker run -d -P --name tomcat01 tomcat
  [root@localhost ~]# docker run -d -P --name tomcat01 --net bridge tomcat # 与上同
  
  #docker0特点，默认，域名不能访问， --link可以打通连接
  
  #我们可以自定义一个网络
  # --driver bridge 默认模式
  # --subnet 192.168.0.0/16 子网
  # --gateway 192.168.0.1 网关
  [root@localhost ~]# docker network create --driver bridge --subnet 192.168.0.0/16 --gateway 192.168.0.1 mynet
  f65adcb6e64dc58d657ad44ae539b6dca051b359dd5de8229292676f61b4dacf
  [root@localhost ~]# docker network ls
  NETWORK ID     NAME      DRIVER    SCOPE
  e992a3709ce3   bridge    bridge    local
  1848b56aacbc   host      host      local
  f65adcb6e64d   mynet     bridge    local
  06973c9d4152   none      null      local
  [root@localhost ~]# docker network inspect mynet
  [
      {
          "Name": "mynet",
          "Id": "f65adcb6e64dc58d657ad44ae539b6dca051b359dd5de8229292676f61b4dacf",
          "Created": "2022-06-04T08:52:07.680095545+08:00",
          "Scope": "local",
          "Driver": "bridge",
          "EnableIPv6": false,
          "IPAM": {
              "Driver": "default",
              "Options": {},
              "Config": [
                  {
                      "Subnet": "192.168.0.0/16",
                      "Gateway": "192.168.0.1"
                  }
              ]
          },
          "Internal": false,
          "Attachable": false,
          "Ingress": false,
          "ConfigFrom": {
              "Network": ""
          },
          "ConfigOnly": false,
          "Containers": {},
          "Options": {},
          "Labels": {}
      }
  ]
  
  [root@localhost ~]# docker run -d -P --name tomcat-net-01 --net mynet tomcat
  [root@localhost ~]# docker run -d -P --name tomcat-net-02 --net mynet tomcat
  [root@localhost ~]# docker network inspect mynet
  [
      {
          "Name": "mynet",
          "Id": "f65adcb6e64dc58d657ad44ae539b6dca051b359dd5de8229292676f61b4dacf",
          "Created": "2022-06-04T08:52:07.680095545+08:00",
          "Scope": "local",
          "Driver": "bridge",
          "EnableIPv6": false,
          "IPAM": {
              "Driver": "default",
              "Options": {},
              "Config": [
                  {
                      "Subnet": "192.168.0.0/16",
                      "Gateway": "192.168.0.1"
                  }
              ]
          },
          "Internal": false,
          "Attachable": false,
          "Ingress": false,
          "ConfigFrom": {
              "Network": ""
          },
          "ConfigOnly": false,
          "Containers": {
              "46c01808ad838020f695310364fcf20bdaf8cb27a71dbeafd05961cd80f184c4": {
                  "Name": "tomcat-net-01",
                  "EndpointID": "dca0f17950028937121a0aed911b373835ba1dbe6067dbbb06bac57e115899bb",
                  "MacAddress": "02:42:c0:a8:00:02",
                  "IPv4Address": "192.168.0.2/16",
                  "IPv6Address": ""
              },
              "ea3adda2adac187f2651f3d4d90a4e072e0a26e26ff99f66e4131067c4013630": {
                  "Name": "tomcat-net-02",
                  "EndpointID": "a8a2a26770b1c48fe6a57b211659345ccffabfc97486afb9ad3d0ec76048f18a",
                  "MacAddress": "02:42:c0:a8:00:03",
                  "IPv4Address": "192.168.0.3/16",
                  "IPv6Address": ""
              }
          },
          "Options": {},
          "Labels": {}
      }
  ]
  
  #可通过容器名直接ping通
  [root@localhost ~]# docker exec -it tomcat-net-01 ping tomcat-net-02 
  PING tomcat-net-02 (192.168.0.3) 56(84) bytes of data.
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=1 ttl=64 time=0.100 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=2 ttl=64 time=0.591 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=3 ttl=64 time=0.120 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=4 ttl=64 time=0.103 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=5 ttl=64 time=0.122 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=6 ttl=64 time=0.125 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=7 ttl=64 time=0.116 ms
  64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=8 ttl=64 time=0.089 ms
  
  ```

  * 我们自定义的网络docker都已经帮我们维护好了对应的关系，推荐我们平时这样使用网络
  * 好处
    * redis-不同的集群使用不同的网络，保证集群是安全和健康的
    * mysql-不同的集群使用不同的网络，保证集群是安全健康的

### 4、网络连通

![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-112110@2x.png)![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-111815@2x.png)

* 测试

  ```shell
  [root@localhost ~]# docker network connect --help
  
  Usage:  docker network connect [OPTIONS] NETWORK CONTAINER
  
  Connect a container to a network
  
  Options:
        --alias strings           Add network-scoped alias for the container
        --driver-opt strings      driver options for the network
        --ip string               IPv4 address (e.g., 172.30.100.104)
        --ip6 string              IPv6 address (e.g., 2001:db8::33)
        --link list               Add link to another container
        --link-local-ip strings   Add a link-local address for the container
        
  [root@localhost ~]# docker network connect mynet tomcat01 
  
  ```

  ![](/Users/smc/Desktop/smc/工具学习/docker/resources/WX20220612-112735@2x.png)

  * 测试打通之后将tomcat01放到mynet下

    ```shell
    [root@localhost ~]# docker exec -it tomcat-net-01 ping tomcat01
    PING tomcat01 (192.168.0.4) 56(84) bytes of data.
    64 bytes from tomcat01.mynet (192.168.0.4): icmp_seq=1 ttl=64 time=0.122 ms
    64 bytes from tomcat01.mynet (192.168.0.4): icmp_seq=2 ttl=64 time=0.084 ms
    64 bytes from tomcat01.mynet (192.168.0.4): icmp_seq=3 ttl=64 time=0.120 ms
    64 bytes from tomcat01.mynet (192.168.0.4): icmp_seq=4 ttl=64 time=0.129 ms
    
    ```

#### 结论

​	假设要跨网络操作别人，就需要使用docker network connect 连通！

### 5、 实战：Redis集群部署

* shell命令

  ```shell
  [root@localhost ~]# docker stop $(docker ps -aq) #先暂停所有容器
  
  # 创建redis网络
  [root@localhost ~]# docker network create redis --subnet 172.38.0.0/16 
  f44a92b47af59718a5dde6363d9429ed6bd4df078ff2b48f048364a39c124ba8
  [root@localhost ~]# docker network ls
  NETWORK ID     NAME      DRIVER    SCOPE
  e992a3709ce3   bridge    bridge    local
  1848b56aacbc   host      host      local
  f65adcb6e64d   mynet     bridge    local
  06973c9d4152   none      null      local
  f44a92b47af5   redis     bridge    local
  #通过脚本创建六个redis配置
  for port in $(seq 1 6);
  do
  mkdir -p /mydata/redis/node-${port}/conf
  touch /mydata/redis/node-${port}/conf/redis.conf
  cat << EOF >>/mydata/redis/node-${port}/conf/redis.conf
  port 6379
  bind 0.0.0.0
  cluster-enabled yes
  cluster-config-file nodes.conf
  cluster-node-timeout 5000
  cluster-announce-ip 172.38.0.1${port}
  cluster-announce-port 6379
  cluster-announce-bus-port 16379
  appendonly yes
  EOF
  done
  #开启一个redis服务，类似开启多个
  [root@localhost redis]# docker run -p 6371:6379 -p 16371:16379 --name redis-1 -v /mydata/redis/node-1/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-1/data:/data -d --net redis --ip 172.38.0.11 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  
  [root@localhost mydata]# docker run -p 6372:6379 -p 16372:16379 --name redis-2 -v /mydata/redis/node-2/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-2/data:/data -d --net redis --ip 172.38.0.12 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  
  [root@localhost mydata]# docker run -p 6373:6379 -p 16373:16379 --name redis-3 -v /mydata/redis/node-3/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-3/data:/data -d --net redis --ip 172.38.0.13 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  [root@localhost mydata]# docker run -p 6374:6379 -p 16374:16379 --name redis-4 -v /mydata/redis/node-4/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-4/data:/data -d --net redis --ip 172.38.0.14 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  [root@localhost mydata]# docker run -p 6375:6379 -p 16375:16379 --name redis-5 -v /mydata/redis/node-5/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-5/data:/data -d --net redis --ip 172.38.0.15 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  
  [root@localhost mydata]# docker run -p 6376:6379 -p 16376:16379 --name redis-6 -v /mydata/redis/node-6/conf/redis.conf:/etc/redis/redis.conf -v /mydata/redis/node-6/data:/data -d --net redis --ip 172.38.0.16 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
  
  #创建集群
  [root@localhost conf]# docker exec -it a01da2587f09 /bin/sh
  /data # redis-cli --cluster create 172.38.0.11:6379 172.38.0.12:6379 172.38.0.13:6379 172.38.0.14:6379 172.38.0.15:6379 172.38.0.16:6379 --cluster-replicas 1
  >>> Performing hash slots allocation on 6 nodes...
  Master[0] -> Slots 0 - 5460
  Master[1] -> Slots 5461 - 10922
  Master[2] -> Slots 10923 - 16383
  Adding replica 172.38.0.15:6379 to 172.38.0.11:6379
  Adding replica 172.38.0.16:6379 to 172.38.0.12:6379
  Adding replica 172.38.0.14:6379 to 172.38.0.13:6379
  M: 67cb33063ddd403f2491ece49bec33c46865c9a4 172.38.0.11:6379
     slots:[0-5460] (5461 slots) master
  M: 9efeb95acd22e982a0e52d6527e1edca22394c7a 172.38.0.12:6379
     slots:[5461-10922] (5462 slots) master
  M: 874b950f1d69373de55c57c8eb40e48e80d831fb 172.38.0.13:6379
     slots:[10923-16383] (5461 slots) master
  S: f9c5c496b6ac73c234179c5078ac9a9e45e76d2c 172.38.0.14:6379
     replicates 874b950f1d69373de55c57c8eb40e48e80d831fb
  S: c75b25ae6752e9f4143f8f33030f0269677a7e91 172.38.0.15:6379
     replicates 67cb33063ddd403f2491ece49bec33c46865c9a4
  S: 30fc6f4bc25d59f2a3f5980d934af00a4f097dc3 172.38.0.16:6379
     replicates 9efeb95acd22e982a0e52d6527e1edca22394c7a
  Can I set the above configuration? (type 'yes' to accept): yes
  >>> Nodes configuration updated
  >>> Assign a different config epoch to each node
  >>> Sending CLUSTER MEET messages to join the cluster
  Waiting for the cluster to join
  ...
  >>> Performing Cluster Check (using node 172.38.0.11:6379)
  M: 67cb33063ddd403f2491ece49bec33c46865c9a4 172.38.0.11:6379
     slots:[0-5460] (5461 slots) master
     1 additional replica(s)
  S: c75b25ae6752e9f4143f8f33030f0269677a7e91 172.38.0.15:6379
     slots: (0 slots) slave
     replicates 67cb33063ddd403f2491ece49bec33c46865c9a4
  M: 9efeb95acd22e982a0e52d6527e1edca22394c7a 172.38.0.12:6379
     slots:[5461-10922] (5462 slots) master
     1 additional replica(s)
  M: 874b950f1d69373de55c57c8eb40e48e80d831fb 172.38.0.13:6379
     slots:[10923-16383] (5461 slots) master
     1 additional replica(s)
  S: f9c5c496b6ac73c234179c5078ac9a9e45e76d2c 172.38.0.14:6379
     slots: (0 slots) slave
     replicates 874b950f1d69373de55c57c8eb40e48e80d831fb
  S: 30fc6f4bc25d59f2a3f5980d934af00a4f097dc3 172.38.0.16:6379
     slots: (0 slots) slave
     replicates 9efeb95acd22e982a0e52d6527e1edca22394c7a
  [OK] All nodes agree about slots configuration.
  >>> Check for open slots...
  >>> Check slots coverage...
  [OK] All 16384 slots covered.
  
  
  
  #集群测试
  /data # redis-cli -c
  127.0.0.1:6379> cluster info
  cluster_state:ok
  cluster_slots_assigned:16384
  cluster_slots_ok:16384
  cluster_slots_pfail:0
  cluster_slots_fail:0
  cluster_known_nodes:6
  cluster_size:3
  cluster_current_epoch:6
  cluster_my_epoch:1
  cluster_stats_messages_ping_sent:103
  cluster_stats_messages_pong_sent:104
  cluster_stats_messages_sent:207
  cluster_stats_messages_ping_received:99
  cluster_stats_messages_pong_received:103
  cluster_stats_messages_meet_received:5
  cluster_stats_messages_received:207
  
  127.0.0.1:6379> cluster nodes
  c75b25ae6752e9f4143f8f33030f0269677a7e91 172.38.0.15:6379@16379 slave 67cb33063ddd403f2491ece49bec33c46865c9a4 0 1654310739637 5 connected
  9efeb95acd22e982a0e52d6527e1edca22394c7a 172.38.0.12:6379@16379 master - 0 1654310739537 2 connected 5461-10922
  874b950f1d69373de55c57c8eb40e48e80d831fb 172.38.0.13:6379@16379 master - 0 1654310739638 3 connected 10923-16383
  67cb33063ddd403f2491ece49bec33c46865c9a4 172.38.0.11:6379@16379 myself,master - 0 1654310738000 1 connected 0-5460
  f9c5c496b6ac73c234179c5078ac9a9e45e76d2c 172.38.0.14:6379@16379 slave 874b950f1d69373de55c57c8eb40e48e80d831fb 0 1654310739000 4 connected
  30fc6f4bc25d59f2a3f5980d934af00a4f097dc3 172.38.0.16:6379@16379 slave 9efeb95acd22e982a0e52d6527e1edca22394c7a 0 1654310739000 6 connected
  
  127.0.0.1:6379> set a b
  -> Redirected to slot [15495] located at 172.38.0.13:6379
  OK
  
  172.38.0.13:6379> get a
  "b"
  
  [root@localhost ~]# docker stop redis-3
  redis-3
  
  172.38.0.13:6379> set a c
  OK
  
  127.0.0.1:6379> get a
  -> Redirected to slot [15495] located at 172.38.0.14:6379
  "c"
  172.38.0.14:6379> cluster nodes
  30fc6f4bc25d59f2a3f5980d934af00a4f097dc3 172.38.0.16:6379@16379 slave 9efeb95acd22e982a0e52d6527e1edca22394c7a 0 1654311165000 6 connected
  874b950f1d69373de55c57c8eb40e48e80d831fb 172.38.0.13:6379@16379 master,fail - 1654310976046 1654310974929 3 connected
  f9c5c496b6ac73c234179c5078ac9a9e45e76d2c 172.38.0.14:6379@16379 myself,master - 0 1654311166000 7 connected 10923-16383
  c75b25ae6752e9f4143f8f33030f0269677a7e91 172.38.0.15:6379@16379 slave 67cb33063ddd403f2491ece49bec33c46865c9a4 0 1654311165910 5 connected
  9efeb95acd22e982a0e52d6527e1edca22394c7a 172.38.0.12:6379@16379 master - 0 1654311166000 2 connected 5461-10922
  67cb33063ddd403f2491ece49bec33c46865c9a4 172.38.0.11:6379@16379 master - 0 1654311166926 1 connected 0-5460
  
  ```

  

## 八、IDEA整合Docker

* 构架springboot项目
* 打包应用
* 编写dockerfile
* 构建镜像
* 发布运行
* 以后我们使用docker之后，给逼人交付的就是一个镜像即可

## 九、Docker Compose

## 十、Docker Swarm

## 十一、CI\CD Jenkins

