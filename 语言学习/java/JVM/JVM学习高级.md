# 一、概述

## 1、大厂面试题

![image-20231223182752560](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312231827644.png)

## 2、背景说明

### 2.1 生产环境中的问题

* 生产环境发生了内存溢出该如何处理？
* 生产环境应该给服务器分配多少内存合适？
* 如何对垃圾回收器的性能进行调优？
* 生产环境CPU负载飙高该如何处理？
* 生产环境应该给应用分配多少线程合适？
* 不加log，如何确定请求是否执行了某一行代码？
* 不加log，如何试试查看某个方法的入参与返回值？

### 2.2 为什么要调优

* 防止出现OOM
* 解决OOM
* 减少Full GC出现的频率

### 2.3 不同阶段的考虑

* 上线前
* 项目运行阶段
* 线上出现OOM

## 3、调优概述

### 3.1 监控的依据

* 运行日志
* 异常堆栈
* GC日志
* 线程快照
* 堆转储快照

### 3.2 调优的大方向

* 合理地编写代码
* 充分并合理地使用硬件资源
* 合理地进行JVM调优

## 4、性能优化的步骤

### 4.1 第一步（发现问题）：性能监控

* 一种一非强行或者入侵方式收集或查看应用运营性能数据的活动

* 监控通常是指一种在生产、质量评估或者开发环境下实施的带有预防或主动性的活动

* 当应用相关干系人提粗性能问题却没有提供足够多的线索时，首先我们需要进行性能监控，随后是性能分析

* 监控问题：

  * GC频繁

  * cpu load过高

  * OOM

  * 内存泄漏

  * 死锁

  * 程序响应时间较长

### 4.2 第二步（排查问题）：性能分析

* 一种以侵入方式收集运行性能数据的活动，它会影响应用的吞吐量干活响应性
* 性能分析是针对性能问题的答复结果，关注的范围通常比性能监控更加集中
* 性能分析很少在生产环境下进行，通常是在质量评估、系统测试或者开发环境下进行，是性能监控之后的步骤
  * 打印GC日志，通过GCviewer或者`http://gceasy.io`来分析日志信息
  * 灵活运用命令行工具，jstack，jap，jinfo等
  * dump出堆文件，使用内存分析工具分析文件
  * 使用阿里Arthas，或jconsole，JVisualVM来实时查看JVM状态
  * jstack查看堆栈信息

### 4.3 第三部（解决问题）：性能调优

* 一种为改善应用响应性或吞吐量而更改参数、源代码、属性配置的活动，性能调优是在性能监控、性能分析之后的活动
  * 适当增加内存，根据业务背景选择垃圾回收器
  * 优化代码，控制内存使用
  * 增加机器，分散节点压力
  * 合理设置线程池线程数量
  * 使用中间件提高程序效率，比如缓存，消息队列等
  * 其他......

## 5、性能评价/测试指标

### 5.1 停顿时间（或响应时间）

* 提交请求和返回该请求的相应之间使用的时间，一般比较关注平均响应时间

* 常用操作的相应时间列表

  ![image-20231223184737756](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312231847815.png)

* 在垃圾回收环节中
  * 暂停时间：执行垃圾收集时，程序的工作线程被暂停的时间
  * `-XX:MaxGCPauseMillis`

### 5.2 吞吐量

* 对单位时间内完成的工作量（请求）的量度
* 在GC中：运行用户代码的时间占总运行时间的比例（总运行时间：程序运行时间+内存回收的时间）吞吐量为$1- \frac{1}{(1+n)}$,`-XX:GCTimeRation=n`

### 5.3 并发数

* 同一时刻，对服务器诱食剂交互的请求数
* 1000个人在线，估计并发数在5% ~ 15% 之间，也就是同时并发量：50~150之间

### 5.4 内存占用

* Java堆区所占的内存大小

### 5.5 相互间的关系

* 以高速公路通行状况为例

# 二、JVM监控及诊断工具-命令行篇

## 1、概述

* Java作为最流行的编程语言之一，其应用性能诊断一直收到业界广泛关注。可能造成Java应用出现性能问题的因素非常多，例如线程控制、磁盘读写、数据库访问、网络I/O、垃圾收集等。想要定位这些问题，一款优秀的性能诊断工具必不可少
* 体会1：只用数据说明问题，使用知识分析问题，使用工具处理问题
* 体会2：无监控，不调优

## 2、jps：查看正在运行的Java进程

### 2.1 基本情况

* jps(Java process Status)
  * 显示指定系统内所有HotSpot虚拟机进程（查看虚拟机进程信息），可用于查询正在运行的虚拟机进程
  * 说明：对于本地虚拟机进程来说，进程的本地虚拟机ID与操作系统的进程ID是一致的，是唯一的

### 2.2 基本语法

#### 2.2.1 options参数

```shell
smc@linjianguodeMacBook-Pro java % jps
97843 Launcher
97844 ScannerTest
97851 Jps
96394 
smc@linjianguodeMacBook-Pro java % jps -help
usage: jps [-help]
       jps [-q] [-mlvV] [<hostid>]

Definitions:
    <hostid>:      <hostname>[:<port>]
# 查看pid
smc@linjianguodeMacBook-Pro java % jps -q
97904
97843
97844
96394
#输出应用程序主类的全类名或如果进程执行的是jar包，则输出jar完整路径
smc@linjianguodeMacBook-Pro java % jps -l
97843 org.jetbrains.jps.cmdline.Launcher
97844 com.smc.java.ScannerTest
96394 
97916 sun.tools.jps.Jps
#输出虚拟机进程启动时传递给主类main()的参数
smc@linjianguodeMacBook-Pro java % jps -m
97843 Launcher /Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/jps-builders.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/jps-builders-6.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/jps-javac-extension-1.jar:/Applications/IntelliJ IDEA.app/Contents/lib/util.jar:/Applications/IntelliJ IDEA.app/Contents/lib/annotations.jar:/Applications/IntelliJ IDEA.app/Contents/lib/3rd-party-rt.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jna.jar:/Applications/IntelliJ IDEA.app/Contents/lib/kotlin-stdlib-jdk8.jar:/Applications/IntelliJ IDEA.app/Contents/lib/protobuf.jar:/Applications/IntelliJ IDEA.app/Contents/lib/platform-api.jar:/Applications/IntelliJ IDEA.app/Contents/lib/jps-model.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/javac2.jar:/Applications/IntelliJ IDEA.app/Contents/lib/forms_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/qdox.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/java/lib/aether-dependency-resolv
97844 ScannerTest
96394 
97933 Jps -m

#列出虚拟机启动时的JVM参数。比如：-Xms20m -Xms50m 是启动程序指定的jvm参数
smc@linjianguodeMacBook-Pro java % jps -v
97843 Launcher -Xmx700m -Djava.awt.headless=true -Dpreload.project.path=/Users/smc/Desktop/smc/语言学习/java/JVM/JVMDemo -Dpreload.config.path=/Users/smbrary/Application Support/JetBrains/IntelliJIdea2021.3/options -Dcompile.parallel=false -Drebuild.on.dependency.change=true -Djdt.compiler.useSingleThread=true -Daether.connector.resumeDownloads=false -Dio.netty.initialSeedUniquifier=5927186340452101013 -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Didea.paths.selector=IntelliJIdea2021.3 -Djava.net.preferIPv4Stack=true -Didea.home.path=/Applications/IntelliJ IDEA.app/Contents -Didea.config.path=/Users/smc/Library/Application Support/JetBrains/IntelliJIdea2021.3 -Didea.plugins.path=/Users/smc/Library/Application Support/JetBrains/IntelliJIdea2021.3/plugins -Djps.log.dir=/Users/smc/Library/Logs/JetBrains/IntelliJIdea2021.3/build-log -Djps.fallback.jdk.home=/Applications/IntelliJ IDEA.app/Contents/jbr/Contents/Home -Djps.fallback.jdk.version=11.0.13 -Dio.netty.noUnsafe=true -Djava.io.tmpdir=/Users/smc/Libr
98019 Jps -Denv.class.path=/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/lib/tools.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/lib/dt.jar:. -Dapplication.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home -Xms8m
97844 ScannerTest -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:64532,suspend=y,server=n -Dvisualvm.id=479588820589426 -Dvisualgc.id=479587118974466 -javaagent:/Users/smc/Library/Caches/JetBrains/IntelliJIdea2021.3/captureAgent/debugger-agent.jar -Dfile.encoding=UTF-8
96394  -Xms128m -Xmx750m -XX:ReservedCodeCacheSize=512m -XX:+IgnoreUnrecognizedVMOptions -XX:SoftRefLRUPolicyMSPerMB=50 -XX:CICompilerCount=2 -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -ea -Dsun.io.useCanonCaches=false -Djdk.http.auth.tunneling.disabledSchemes="" -Djdk.attach.allowAttachSelf=true -Djdk.module.illegalAccess.silent=true -Dkotlinx.coroutines.debug=off -XX:ErrorFile=/Users/smc/java_error_in_idea_%p.log -XX:HeapDumpPath=/Users/smc/java_error_in_idea.hprof -Xms128m -Xmx2048m -XX:ReservedCodeCacheSize=240m -XX:+UseConcMarkSweepGC -XX:SoftRefLRUPolicyMSPerMB=50 -ea -XX:CICompilerCount=2 -Dsun.io.useCanonPrefixCache=false -Djava.net.preferIPv4Stack=true -Djdk.http.auth.tunneling.disabledSchemes="" -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Djdk.attach.allowAttachSelf=true -Dkotlinx.coroutines.debug=off -Djdk.module.illegalAccess.silent=true -javaagent:/Users/smc/JetBrains/micool.jar -Djb.vmOptionsFile=/Users/smc/Library/Application Support/JetBrains/IntelliJIdea2021.3

```

* 以上参数可综合使用
* 如果某Java进程关闭了默认开启的UsePerfData参数（即使用参数-XX:UsePerfData）,那么jps命令（以及下面介绍的jstat）将无法探知该Java进程

#### 2.2.2 hostid参数

* RMI注册表中注册的主机名
* 如果想要远程监控主机上的java程序，需要安装jstatd
* 对于具有更严格的安全事件的网络场所而言，可能使用一个自定义的策略文件来显示对特定的可信主机或网络的访问，尽管这种技术容易收到IP地址欺诈攻击
* 如果安全问题无法使用一个定制的策略文件来处理，那么最安全的操作是不运行jstatd服务器，而是本地使用jstat和jps工具

## 3、jstat：查看JVM的统计信息

### 3.1 基本情况

* jstat（JVM Statistics Monitoring Tool）：用于监视虚拟机各种运行状态信息的命令行工具。它可以显示本地或者远程虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据
* 在没有GUI图形界面，只提供了纯文本控制台环境的服务器上，它将是运行期定位虚拟机性能问题的首选工具。常用于检测垃圾回收问题以及内存泄漏问题

### 3.2 基本语法

#### 3.2.1 option参数

* 类装载相关

  * -class：显示ClassLoader的相关信息：类的装载、卸载数量、总空间、类装载所消耗的时间等

  ```shell
  smc@linjianguodeMacBook-Pro java % jps
  98432 ScannerTest
  98469 Jps
  96394 
  98431 Launcher
  smc@linjianguodeMacBook-Pro java % jstat -class 96394
  Loaded  Bytes  Unloaded  Bytes     Time   
   93860 189556.4     2015  2179.7     244.77
  smc@linjianguodeMacBook-Pro java % jstat -class 98432
  Loaded  Bytes  Unloaded  Bytes     Time   
     607  1197.9        0     0.0       0.16
  
  ```

* 垃圾回收相关的

  * -gc：显示与GC相关的堆信息。包括Eden区、两个Survivor区、老年代、永久代等容量、已用空间、GC时间合计等信息

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gc 910
     S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
    9728.0 9216.0  0.0   7072.1 2777088.0 1323792.7 5592576.0   844483.3  440960.0 410475.0 58496.0 53294.5  15342 1499.133  210   936.714 2435.848
     
    ```

    

  * -gccapacity：显示内容与-gc基本相同，但输出主要关注Java堆各个区域使用到的最大、最小空间。

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gccapacity 910
     NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC
    2796032.0 2796032.0 2796032.0 9728.0 9216.0 2777088.0  5592576.0  5592576.0  5592576.0  5592576.0      0.0 1431552.0 440960.0      0.0 1048576.0  58496.0  15343   210
     
    ```

    

  * -gcutil：显示内容-gc基本相同，但输出主要关注已使用空间占总空间的百分比

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gcutil 910 1000 5
      S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
     58.33   0.00  97.65  15.11  93.09  91.11  15345 1499.229   210  936.714 2435.943
     58.33   0.00  98.18  15.11  93.09  91.11  15345 1499.229   210  936.714 2435.943
     58.33   0.00  98.19  15.11  93.09  91.11  15345 1499.229   210  936.714 2435.943
     58.33   0.00  98.42  15.11  93.09  91.11  15345 1499.229   210  936.714 2435.943
      0.00  70.96   0.61  15.11  93.09  91.11  15346 1499.260   210  936.714 2435.975
     
    ```

    

  * -gccause：与-gcutil功能一样，但是会额外输出导致最后一次或当前正在发生的GC产生的原因

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gccause 910 1000 20
      S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT    LGCC                 GCC
     92.28   0.00  92.36  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  92.49  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  93.59  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  93.72  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  98.42  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  99.45  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
     92.28   0.00  99.59  15.11  93.09  91.11  15347 1499.292   210  936.714 2436.006 Allocation Failure   No GC
      0.00  59.72   2.21  15.11  93.09  91.11  15348 1499.322   210  936.714 2436.036 Allocation Failure   No GC
      0.00  59.72   2.35  15.11  93.09  91.11  15348 1499.322   210  936.714 2436.036 Allocation Failure   No GC
      0.00  59.72   3.48  15.11  93.09  91.11  15348 1499.322   210  936.714 2436.036 Allocation Failure   No GC
      0.00  59.72   3.84  15.11  93.09  91.11  15348 1499.322   210  936.714 2436.036 Allocation Failure   No GC
     
    ```

    

  * -gcnew：显示新生代的gc情况

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gcnew 910 1000 10
     S0C    S1C    S0U    S1U   TT MTT  DSS      EC       EU     YGC     YGCT
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1606445.2  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1625758.3  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1650168.5  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1662834.2  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1690974.1  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1796228.2  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1819398.4  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1829091.7  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1857730.4  15348 1499.322
    9216.0 9216.0    0.0 5504.0 15  15 9216.0 2777600.0 1879679.2  15348 1499.322
     
    ```

    

  * -gcnewcapacity：显示内容与-gcnew基本相同，输出主要关注使用到的最大最小空间

  * -gcold：显示老年代GC情况

    ```shell
    [root@cnndr4tfrapp01 ~]# jstat -gcold -t 910 1000 10
    Timestamp          MC       MU      CCSC     CCSU       OC          OU       YGC    FGC    FGCT     GCT
          1669055.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669056.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669057.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669058.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669059.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669060.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669061.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669062.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669063.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
          1669064.2 440960.0 410475.0  58496.0  53294.5   5592576.0    845299.3  15349   210  936.714 2436.068
     
    ```

    

  * -gcoldcapacity：显示内容与-gcold基本相同，输出主要关注使用到最大、最小空间

  * -gcpermcapacity：显示永久代使用到的最大、最小空间。

* JIT相关

  * -compiler：显示JIT编译器编译过的方法、耗时等信息
  * -printcompilation：输出已经被JIT编译的方法

  ```shell
  Compiled Failed Invalid   Time   FailedType FailedMethod
     62101      9       0   439.27          1 com/intellij/psi/util/PsiTreeUtilKt walkUpToCommonParent
  smc@linjianguodeMacBook-Pro java % jstat -printcompilation 96394
  Compiled  Size  Type Method
     62183   1218    1 com/intellij/psi/util/PsiTreeUtilKt elementsAroundOffsetUp
  
  ```

  

#### 3.2.2 interval参数

* 用于指定输出统计数据的周期，单位为毫秒。即：查询间隔

  ```shell
  smc@linjianguodeMacBook-Pro java % jstat -class 96394 1000
  Loaded  Bytes  Unloaded  Bytes     Time   
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
   93947 189684.7     2015  2179.7     244.89
  
  ```

#### 3.2.3 count参数

* 用于指定查询的总次数

  ```shell
  smc@linjianguodeMacBook-Pro java % jstat -class 96394 1000 10
  Loaded  Bytes  Unloaded  Bytes     Time   
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
   93965 189705.7     2015  2179.7     244.91
  smc@linjianguodeMacBook-Pro java % 
  
  ```

#### 3.2.4 -t参数

* 可以在输出信息前加上一个Timestamp列，显示程序的运行时间，单位为秒

  ```shell
  smc@linjianguodeMacBook-Pro java % jstat -class -t 96394 1000 10
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5357.4  93965 189705.7     2015  2179.7     244.91
           5358.4  93965 189705.7     2015  2179.7     244.91
           5359.4  93965 189705.7     2015  2179.7     244.91
           5360.4  93965 189705.7     2015  2179.7     244.91
           5361.4  93965 189705.7     2015  2179.7     244.91
           5362.4  93965 189705.7     2015  2179.7     244.91
           5363.4  93965 189705.7     2015  2179.7     244.91
           5364.4  93965 189705.7     2015  2179.7     244.91
           5365.4  93965 189705.7     2015  2179.7     244.91
           5366.4  93965 189705.7     2015  2179.7     244.91
  smc@linjianguodeMacBook-Pro java % 
  
  ```

#### 3.2.5 -h参数

* 可以在周期性数据输出是，输出多少行时输出表头

  ```shell
  smc@linjianguodeMacBook-Pro java % jstat -class -t -h2 96394 1000 10
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5445.0  93967 189708.8     2015  2179.7     244.91
           5446.0  93967 189708.8     2015  2179.7     244.91
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5447.0  93967 189708.8     2015  2179.7     244.91
           5448.0  93967 189708.8     2015  2179.7     244.91
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5449.1  93967 189708.8     2015  2179.7     244.91
           5450.1  93967 189708.8     2015  2179.7     244.91
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5451.1  93967 189708.8     2015  2179.7     244.91
           5452.1  93967 189708.8     2015  2179.7     244.91
  Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
           5453.1  93967 189708.8     2015  2179.7     244.91
           5454.1  93967 189708.8     2015  2179.7     244.91
  smc@linjianguodeMacBook-Pro java % 
  
  ```

### 3.3 经验

* 我们可以比较Java进程的启动时间以及总GC时间（GCT列），或者两次测量的间隔时间以及总GC时间的增量，来得出GC时间占运行时间的比例

  ![image-20231223222410786](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312232224168.png)

* 如果该比例超过20%，则说明目前堆的压力较大；如果该比例超过90%，则说明堆里几乎没有可用空间，随时都可能抛出OOM异常

### 3.4 补充

* jstat还可以用来判断是否出现内存泄漏
* 第一步：
  * 在长时间运行的Java程序中，我们可以运行jstat命令连续获取多行性能数据，并取这几行数据中的OU列（即已占用的老年代内存）的最小值
* 第二步：
  * 然后，我们每隔一段较长的时间重复一次上述操作，来获得多组OU最小值。如果这些值呈上涨趋势，则说明该Java程序的老年代内存已使用量在不断上涨，这意味着无法回收的对象在不断增加，因此很有可能存在内存泄漏

## 4、jinfo：实时查看和修改JVM配置参数

### 4.1 基本情况

* jinfo（configuration Info for Java）：查看虚拟机配置参数信息，也可用于调整虚拟机的配置参数
* 在很多情况下，Java应用程序不会指定所有的Jav啊虚拟机参数。而此时开发人员可能不知道某一个具体的Java虚拟机参数的默认值。在这种情况下，可能需要通过查找文档获取某个参数的默认值。这个查找过程可能就非常艰难的。但有了jinfo工具，开发人员可以很方便的查找Java虚拟机参数的当前值

```shell
smc@linjianguodeMacBook-Pro java % jinfo
Usage:
    jinfo [option] <pid>
        (to connect to running process)
    jinfo [option] <executable <core>
        (to connect to a core file)
    jinfo [option] [server_id@]<remote server IP or hostname>
        (to connect to remote debug server)

where <option> is one of:
    -flag <name>         to print the value of the named VM flag
    -flag [+|-]<name>    to enable or disable the named VM flag
    -flag <name>=<value> to set the named VM flag to the given value
    -flags               to print VM flags
    -sysprops            to print Java system properties
    <no option>          to print both of the above
    -h | -help           to print this help message
smc@linjianguodeMacBook-Pro java % clear
smc@linjianguodeMacBook-Pro java % jinfo -help
Usage:
    jinfo [option] <pid>
        (to connect to running process)
    jinfo [option] <executable <core>
        (to connect to a core file)
    jinfo [option] [server_id@]<remote server IP or hostname>
        (to connect to remote debug server)

where <option> is one of:
    -flag <name>         to print the value of the named VM flag
    -flag [+|-]<name>    to enable or disable the named VM flag
    -flag <name>=<value> to set the named VM flag to the given value
    -flags               to print VM flags
    -sysprops            to print Java system properties
    <no option>          to print both of the above
    -h | -help           to print this help message

```

### 4.2 基本语法

#### 4.2.1 查看

* jinfo -sysprops PID：可以查看由System.getProperties()取得的参数

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jps
  4613 Launcher
  4614 ScannerTest2
  96394 
  4623 Jps
  smc@linjianguodeMacBook-Pro JVMDemo % jinfo -sysprops 4614
  Java System Properties:
  #Sun Dec 24 01:38:03 CST 2023
  gopherProxySet=false
  socksProxyHost=127.0.0.1
  awt.toolkit=sun.lwawt.macosx.LWCToolkit
  http.proxyHost=127.0.0.1
  java.specification.version=11
  sun.cpu.isalist=
  sun.jnu.encoding=UTF-8
  java.class.path=/Users/smc/Desktop/smc/\u8BED\u8A00\u5B66\u4E60/java/JVM/JVMDemo/out/production/jvm07\:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar
  https.proxyPort=2080
  java.vm.vendor=Oracle Corporation
  sun.arch.data.model=64
  java.vendor.url=https\://openjdk.java.net/
  user.timezone=
  visualvm.id=495560798915804
  java.vm.specification.version=11
  os.name=Mac OS X
  sun.java.launcher=SUN_STANDARD
  user.country=CN
  sun.boot.library.path=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home/lib
  sun.java.command=com.smc.java.ScannerTest2
  http.nonProxyHosts=127.0.0.1|192.168.0.0/16|*.192.168.0.0/16|10.0.0.0/8|*.10.0.0.0/8|172.16.0.0/12|*.172.16.0.0/12|100.64.0.0/10|*.100.64.0.0/10|17.0.0.0/8|*.17.0.0.0/8|localhost|*.localhost|local|*.local|169.254.0.0/16|*.169.254.0.0/16|224.0.0.0/4|*.224.0.0.0/4|240.0.0.0/4|*.240.0.0.0/4
  jdk.debug=release
  sun.cpu.endian=little
  user.home=/Users/smc
  user.language=zh
  java.specification.vendor=Oracle Corporation
  java.version.date=2022-08-18
  java.home=/Library/Java/JavaVirtualMachines/jdk-11.0.16.1.jdk/Contents/Home
  file.separator=/
  https.proxyHost=127.0.0.1
  java.vm.compressedOopsMode=Zero based
  line.separator=\n
  java.specification.name=Java Platform API Specification
  java.vm.specification.vendor=Oracle Corporation
  intellij.debug.agent=true
  java.awt.graphicsenv=sun.awt.CGraphicsEnvironment
  visualgc.id=495560798716768
  user.script=Hans
  sun.management.compiler=HotSpot 64-Bit Tiered Compilers
  ftp.nonProxyHosts=127.0.0.1|192.168.0.0/16|*.192.168.0.0/16|10.0.0.0/8|*.10.0.0.0/8|172.16.0.0/12|*.172.16.0.0/12|100.64.0.0/10|*.100.64.0.0/10|17.0.0.0/8|*.17.0.0.0/8|localhost|*.localhost|local|*.local|169.254.0.0/16|*.169.254.0.0/16|224.0.0.0/4|*.224.0.0.0/4|240.0.0.0/4|*.240.0.0.0/4
  java.runtime.version=11.0.16.1+1-LTS-1
  user.name=smc
  path.separator=\:
  os.version=12.7.1
  java.runtime.name=Java(TM) SE Runtime Environment
  file.encoding=UTF-8
  java.vm.name=Java HotSpot(TM) 64-Bit Server VM
  java.vendor.version=18.9
  java.vendor.url.bug=https\://bugreport.java.com/bugreport/
  java.io.tmpdir=/var/folders/yt/3q6c04sn40x8f7hsl67ngcqw0000gn/T/
  java.version=11.0.16.1
  jboss.modules.system.pkgs=com.intellij.rt
  user.dir=/Users/smc/Desktop/smc/\u8BED\u8A00\u5B66\u4E60/java/JVM/JVMDemo
  
  ```

  

* jinfo -flags PID：查看曾经赋过值的一些参数

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jinfo -flags 4614
  VM Flags:
  -XX:CICompilerCount=3 -XX:ConcGCThreads=1 -XX:G1ConcRefinementThreads=4 -XX:G1HeapRegionSize=1048576 -XX:GCDrainStackTargetSize=64 -XX:InitialHeapSize=268435456 -XX:MarkStackSize=4194304 -XX:MaxHeapSize=4294967296 -XX:MaxNewSize=2576351232 -XX:MinHeapDeltaBytes=1048576 -XX:NonNMethodCodeHeapSize=5830732 -XX:NonProfiledCodeHeapSize=122913754 -XX:ProfiledCodeHeapSize=122913754 -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:+UseG1GC 
  ```

  

* jinfo -flag 具体参数PID：查看某个java进程的具体参数的值

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jinfo -flag UseG1GC 4614
  -XX:+UseG1GC
  smc@linjianguodeMacBook-Pro JVMDemo % jinfo -flag UseParallelGC 4614
  -XX:-UseParallelGC
  smc@linjianguodeMacBook-Pro JVMDemo % jinfo -flag MaxHeapSize 4614  
  -XX:MaxHeapSize=4294967296
  ```



#### 4.2.2修改

* jinfo不仅可以查看运行时某一个Java虚拟机参数的实际取值，设置可以在运行时修改部分参数，并使之立即生效

* 但是，并非所有参数都支持动态修改。参数只有被标识为manageable的flag可以被实时修改。其实，这个修改能力是极其有限的

* 可以查看被标记为manageable的参数

  ![image-20231224014618165](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312240146282.png)

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % java -XX:+PrintFlagsFinal -version | grep manageable
       intx CMSAbortablePrecleanWaitMillis           = 100                                    {manageable} {default}
       intx CMSTriggerInterval                       = -1                                     {manageable} {default}
       intx CMSWaitDuration                          = 2000                                   {manageable} {default}
       bool HeapDumpAfterFullGC                      = false                                  {manageable} {default}
       bool HeapDumpBeforeFullGC                     = false                                  {manageable} {default}
       bool HeapDumpOnOutOfMemoryError               = false                                  {manageable} {default}
      ccstr HeapDumpPath                             =                                        {manageable} {default}
      uintx MaxHeapFreeRatio                         = 70                                     {manageable} {default}
      uintx MinHeapFreeRatio                         = 40                                     {manageable} {default}
       bool PrintClassHistogram                      = false                                  {manageable} {default}
       bool PrintConcurrentLocks                     = false                                  {manageable} {default}
  java version "11.0.16.1" 2022-08-18 LTS
  Java(TM) SE Runtime Environment 18.9 (build 11.0.16.1+1-LTS-1)
  Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.16.1+1-LTS-1, mixed mode)
  
  ```

* 针对boolean类型：`jinfo -flag [+|-]具体参数 PID`
* 针对非boolean类型：`jinfo -flag  具体参数=具体参数值 PID`

### 4.3 扩展

* `java -XX:+PrintFlagsInitial`：查看所有JVM参数启动的初始值
*   `Java -XX:+PrintFlagsFinal`：查看所有JVM参数的最终值
* `java -XX:PrintCommandLineFlags`：查看那些已经被用户或者JVM设置过得详细的xx参数的名称和值

## 5、jmap：导出内存映像文件&内存使用情况

### 5.1 基本情况

* jmap（JVM Memory Map）：作用一方面是获取dump文件（堆转储快照文件，二进制文件），它还可以获取目标Java进程的内存相关信息，包括Java堆各区域的使用情况、堆中对象的统计信息、类加载信息等

```shell
smc@linjianguodeMacBook-Pro JVMDemo % jmap -help
Usage:
    jmap -clstats <pid>
        to connect to running process and print class loader statistics
    jmap -finalizerinfo <pid>
        to connect to running process and print information on objects awaiting finalization
    jmap -histo[:live] <pid>
        to connect to running process and print histogram of java object heap
        if the "live" suboption is specified, only count live objects
    jmap -dump:<dump-options> <pid>
        to connect to running process and dump java heap
    jmap -? -h --help
        to print this help message

    dump-options:
      live         dump only live objects; if not specified,
                   all objects in the heap are dumped.
      format=b     binary format
      file=<file>  dump heap to <file>

    Example: jmap -dump:live,format=b,file=heap.bin <pid>
```

### 5.2 基本语法

* 它的基本语法为

  * jmap [option] \<pid\>
  * Jmap [option] \<executable <core\>
  * Jmap [option] [server_id@]\<remote server IP or hostname\>

* 其中option包括

  ![image-20231224020320404](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312240203538.png)

* -dump

  * 生成Java堆转储快照：dump文件

  * 特别的：-dump:live只保存堆中的存活对象

*  -heap
  * 输出整个堆空间的详细信息，包括GC的使用、堆配置信息，以及内存的使用信息等

* -histo

  * 输出堆中对象的统计信息，包括类、实例数量和合计容量

  * 特别的：-histo:live只统计堆中的存活对象

* -permstat

  * 以ClassLoader为统计口径输出永久代的内存状态信息

  * 仅linux/solaris平台有效

* -finalzerinfo

  * 显示在F-Queue中等待Finalizer线程执行finalize方法的对象

  * 仅linux/solaris平台有效

* -F

  * 当虚拟机进程对-dump选项没有任何响应时，可使用此选项强制生成dump文件

  * 仅linux/solaris平台有效

* -h | -help
  * jmap工具使用的帮助命令

* -J \<flag\>
  * 转移参数给jmap启动的jvm

### 5.3 使用1：导出内存映像文件

* Heap Dump又叫做对存储文件，指一个Java进程在某个时间点的内存快照。Heap Dump在触发内存快照的时候会保存此刻的信息如下
* 说明
  * 通常再写Heap Dump文件前会触发一次Full GC，所以heap dump文件里保存的都是FullGC后留下的对象信息
  * 由于生成dump文件比较耗时，因此大家需要耐心等地啊，尤其是大内存镜像生成dump文件则需要耗费更长的时间来完成

#### 5.3.1 手动的方式

```shell
smc@linjianguodeMacBook-Pro JVMDemo % jps
5850 Jps
96394 
5820 Launcher
5821 GcDumpTest
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:format=b,file=./1.hprof 5821
Heap dump file created
smc@linjianguodeMacBook-Pro JVMDemo % jps                                    
5904 Jps
5883 Launcher
96394 
5884 GcDumpTest
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:format=b,file=./2.hprof 5884
Heap dump file created
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:format=b,file=./3.hprof 5884
Heap dump file created
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:format=b,file=./4.hprof 5884
Heap dump file created
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:lave,format=b,file=./4.hprof 5884
File exists
#live 只获取统计存活的对象
smc@linjianguodeMacBook-Pro JVMDemo % jmap -dump:live,format=b,file=./5.hprof 5884
Heap dump file created

```

#### 5.3.2 自动的方式

* 当程序发生OOM退出系统时，一些瞬时信息都随着程序的终止而消失，而重现OOM问题往往比较苦难或者耗时。此时若能在OOM时，自动导出dump文件就显得非常迫切

* 这里介绍一种比较常用的取得堆快照文件的方法，即使用

  * `-XX:+HeapDumpOnOutMemoryError`：在程序发生OOM时，导出应用程序的当前堆快照
  * `-XX:HeapDumpPath`：可以指定堆快照的保存信息

  * 例如：`-Xmx100m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./m.hprof`

### 5.4 使用2：显示堆内存相关信息

* jmap -heap pid

* jmap -histo pid

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jmap -histo 6505
   num     #instances         #bytes  class name (module)
  -------------------------------------------------------
     1:          8762       10641768  [B (java.base@11.0.16.1)
     2:          1112        1329120  [I (java.base@11.0.16.1)
     3:          8876         284032  java.util.HashMap$Node (java.base@11.0.16.1)
     4:           873         166776  [Ljava.util.HashMap$Node; (java.base@11.0.16.1)
     5:          6870         164880  java.lang.String (java.base@11.0.16.1)
     6:          1069         130680  java.lang.Class (java.base@11.0.16.1)
     7:          2209         120064  [Ljava.lang.Object; (java.base@11.0.16.1)
     8:            42          88408  [C (java.base@11.0.16.1)
     9:          1144          45760  java.util.LinkedHashMap$Entry (java.base@11.0.16.1)
    10:          1319          42208  java.util.concurrent.ConcurrentHashMap$Node (java.base@11.0.16.1)
    11:           683          35336  [Ljava.lang.String; (java.base@11.0.16.1)
    12:           660          31680  java.util.HashMap (java.base@11.0.16.1)
    13:           544          30464  jdk.internal.org.objectweb.asm.Item (java.base@11.0.16.1)
    14:           573          22920  java.util.HashMap$KeyIterator (java.base@11.0.16.1)
    15:            21          20880  [Ljdk.internal.org.objectweb.asm.Item; (java.base@11.0.16.1)
    16:            63          19696  [Ljava.util.concurrent.ConcurrentHashMap$Node; (java.base@11.0.16.1)
    17:           314          15072  java.lang.invoke.MemberName (java.base@11.0.16.1)
    18:           452          14464  java.lang.invoke.MethodType$ConcurrentWeakInternSet$WeakEntry (java.base@11.0.16.1)
    19:           563          13512  java.util.ImmutableCollections$Set12$1 (java.base@11.0.16.1)
    20:           336          13440  java.lang.invoke.MethodType (java.base@11.0.16.1)
    21:           559          13416  java.util.ImmutableCollections$SetN$SetNIterator (java.base@11.0.16.1)
    22:           506          13096  [Ljava.lang.Class; (java.base@11.0.16.1)
    23:           511          12264  java.lang.StringBuilder (java.base@11.0.16.1)
    24:           129          11352  java.lang.reflect.Method (java.base@11.0.16.1)
    25:            44           9856  jdk.internal.org.objectweb.asm.MethodWriter (java.base@11.0.16.1)
    26:           369           8856  java.lang.module.ModuleDescriptor$Exports (java.base@11.0.16.1)
    27:           542           8672  java.util.Optional (java.base@11.0.16.1)
    28:           441           7056  java.util.HashSet (java.base@11.0.16.1)
    29:           258           6192  java.util.ImmutableCollections$Set12 (java.base@11.0.16.1)
    30:            72           5760  java.net.URI (java.base@11.0.16.1)
    31:           176           5632  java.lang.module.ModuleDescriptor$Requires (java.base@11.0.16.1)
    32:           205           4920  java.util.KeyValueHolder (java.base@11.0.16.1)
    33:           201           4824  java.util.concurrent.ConcurrentHashMap$MapEntry (java.base@11.0.16.1)
    34:            75           4800  java.net.URL (java.base@11.0.16.1)
    35:            72           4608  java.util.stream.ReferencePipeline$2 (java.base@11.0.16.1)
    36:            57           4560  java.lang.reflect.Constructor (java.base@11.0.16.1)
    37:            70           4480  java.lang.module.ModuleDescriptor (java.base@11.0.16.1)
    38:            69           4416  java.util.concurrent.ConcurrentHashMap (java.base@11.0.16.1)
    39:             3           4336  [[Ljava.lang.Object; (java.base@11.0.16.1)
    40:           262           4192  java.lang.Integer (java.base@11.0.16.1)
    41:            12           4088  [J (java.base@11.0.16.1)
    42:            71           3976  java.util.stream.ReferencePipeline$Head (java.base@11.0.16.1)
    43:            70           3920  jdk.internal.module.Builder (java.base@11.0.16.1)
    44:            70           3920  jdk.internal.module.ModuleReferenceImpl (java.base@11.0.16.1)
    45:           163           3912  java.util.ImmutableCollections$SetN (java.base@11.0.16.1)
    46:            67           3752  java.lang.Module (java.base@11.0.16.1)
    47:            54           3672  [Ljava.lang.ref.SoftReference; (java.base@11.0.16.1)
    48:            20           3520  jdk.internal.org.objectweb.asm.ClassWriter (java.base@11.0.16.1)
    49:           136           3264  java.lang.invoke.ResolvedMethodName (java.base@11.0.16.1)
    50:           193           3088  java.util.Collections$UnmodifiableSet (java.base@11.0.16.1)
    51:            96           3072  java.lang.invoke.LambdaForm$Name (java.base@11.0.16.1)
    52:             8           3008  java.lang.Thread (java.base@11.0.16.1)
    53:            45           2880  jdk.internal.org.objectweb.asm.Label (java.base@11.0.16.1)
    54:            71           2840  java.util.Spliterators$IteratorSpliterator (java.base@11.0.16.1)
    55:            70           2800  java.lang.ref.SoftReference (java.base@11.0.16.1)
    56:            70           2728  [Ljava.lang.module.ModuleDescriptor$Exports; (java.base@11.0.16.1)
    57:            16           2608  [Ljava.lang.invoke.MethodHandle; (java.base@11.0.16.1)
    58:           106           2544  jdk.internal.org.objectweb.asm.ByteVector (java.base@11.0.16.1)
    59:            52           2496  sun.util.locale.LocaleObjectCache$CacheEntry (java.base@11.0.16.1)
    60:            37           2368  java.lang.Class$ReflectionData (java.base@11.0.16.1)
    61:           139           2224  java.util.HashMap$KeySet (java.base@11.0.16.1)
    62:            66           2112  java.lang.invoke.LambdaForm$Kind (java.base@11.0.16.1)
    63:            53           2000  [Ljava.lang.invoke.LambdaForm$Name; (java.base@11.0.16.1)
    64:            70           1992  [Ljava.lang.module.ModuleDescriptor$Requires; (java.base@11.0.16.1)
    65:            19           1976  java.lang.invoke.InnerClassLambdaMetafactory (java.base@11.0.16.1)
    66:            33           1848  java.lang.invoke.MethodTypeForm (java.base@11.0.16.1)
    67:            72           1728  java.util.stream.ReferencePipeline$2$1 (java.base@11.0.16.1)
    68:           107           1712  java.lang.Object (java.base@11.0.16.1)
    69:            70           1680  java.util.stream.FindOps$FindSink$OfRef (java.base@11.0.16.1)
    70:            70           1680  jdk.internal.module.SystemModuleFinders$2 (java.base@11.0.16.1)
    71:            65           1560  java.util.Collections$UnmodifiableCollection$1 (java.base@11.0.16.1)
    72:             1           1528  [[B (java.base@11.0.16.1)
    73:            70           1464  [Ljava.lang.module.ModuleDescriptor$Provides; (java.base@11.0.16.1)
    74:            61           1464  java.lang.module.ResolvedModule (java.base@11.0.16.1)
    75:            61           1464  jdk.internal.loader.BuiltinClassLoader$LoadedModule (java.base@11.0.16.1)
    76:            61           1464  jdk.internal.module.ModuleBootstrap$2 (java.base@11.0.16.1)
    77:            61           1464  jdk.internal.module.ServicesCatalog$ServiceProvider (java.base@11.0.16.1)
    78:            60           1440  java.util.ImmutableCollections$List12 (java.base@11.0.16.1)
    79:            57           1368  java.lang.module.ModuleDescriptor$Provides (java.base@11.0.16.1)
    80:            32           1280  java.io.ObjectStreamField (java.base@11.0.16.1)
    81:            22           1232  jdk.internal.org.objectweb.asm.AnnotationWriter (java.base@11.0.16.1)
    82:             6           1200  [Ljava.util.Map$Entry; (java.base@11.0.16.1)
    83:            70           1152  [Ljava.lang.module.ModuleDescriptor$Opens; (java.base@11.0.16.1)
    84:            48           1152  java.lang.invoke.MethodHandles$Lookup (java.base@11.0.16.1)
    85:            48           1152  java.util.ArrayList (java.base@11.0.16.1)
    86:             4           1128  [Ljava.lang.Integer; (java.base@11.0.16.1)
    87:            35           1120  sun.util.locale.BaseLocale (java.base@11.0.16.1)
    88:            35           1120  sun.util.locale.BaseLocale$Key (java.base@11.0.16.1)
    89:            23           1104  java.lang.invoke.LambdaForm (java.base@11.0.16.1)
    90:            34           1088  java.lang.invoke.LambdaForm$NamedFunction (java.base@11.0.16.1)
    91:            17           1088  java.nio.DirectByteBufferR (java.base@11.0.16.1)
    92:            66           1056  jdk.internal.module.SystemModuleFinders$3 (java.base@11.0.16.1)
    93:            22           1056  jdk.internal.ref.CleanerImpl$PhantomCleanableRef (java.base@11.0.16.1)
    94:            42           1008  [Ljava.lang.reflect.Constructor; (java.base@11.0.16.1)
    95:            31            992  java.lang.invoke.VarHandle$AccessMode (java.base@11.0.16.1)
    96:            41            984  java.util.jar.Attributes$Name (java.base@11.0.16.1)
    97:            19            968  [Lsun.invoke.util.Wrapper; (java.base@11.0.16.1)
    98:            28            896  java.io.File (java.base@11.0.16.1)
    99:            16            896  java.util.concurrent.ConcurrentHashMap$ValueIterator (java.base@11.0.16.1)
   100:             6            864  [Ljava.lang.invoke.VarHandle$AccessMode; (java.base@11.0.16.1)
   101:            15            840  java.lang.invoke.LambdaFormEditor$Transform (java.base@11.0.16.1)
   102:            26            832  java.util.Locale (java.base@11.0.16.1)
   103:            16            808  [Ljava.lang.reflect.Method; (java.base@11.0.16.1)
   104:            25            800  java.util.ArrayList$Itr (java.base@11.0.16.1)
   105:            31            744  java.util.concurrent.CopyOnWriteArrayList (java.base@11.0.16.1)
   106:            23            736  java.lang.invoke.DirectMethodHandle (java.base@11.0.16.1)
   107:            23            736  java.util.ImmutableCollections$ListItr (java.base@11.0.16.1)
   108:            28            672  jdk.internal.reflect.NativeConstructorAccessorImpl (java.base@11.0.16.1)
   109:             3            624  [Ljava.lang.invoke.LambdaForm; (java.base@11.0.16.1)
   110:            26            624  java.util.regex.Pattern$Slice (java.base@11.0.16.1)
   111:            19            608  java.lang.ref.ReferenceQueue (java.base@11.0.16.1)
   112:            15            600  java.lang.invoke.MethodHandleImpl$IntrinsicMethodHandle (java.base@11.0.16.1)
   113:             4            576  [Ljava.lang.invoke.MemberName; (java.base@11.0.16.1)
   114:             2            560  [Ljava.lang.invoke.LambdaForm$Kind; (java.base@11.0.16.1)
   115:             7            560  java.util.concurrent.TimeUnit (java.base@11.0.16.1)
   116:            17            544  java.lang.invoke.SimpleMethodHandle (java.base@11.0.16.1)
   117:            11            528  java.nio.HeapByteBuffer (java.base@11.0.16.1)
   118:            13            520  java.security.AccessControlContext (java.base@11.0.16.1)
   119:             8            512  java.nio.DirectByteBuffer (java.base@11.0.16.1)
   120:            31            496  java.lang.Byte (java.base@11.0.16.1)
   121:            10            480  sun.invoke.util.Wrapper (java.base@11.0.16.1)
   122:            19            456  java.lang.invoke.ConstantCallSite (java.base@11.0.16.1)
   123:            19            456  java.lang.invoke.InfoFromMemberName (java.base@11.0.16.1)
   124:            19            456  java.lang.invoke.InnerClassLambdaMetafactory$ForwardingMethodGenerator (java.base@11.0.16.1)
   125:            19            456  java.lang.invoke.MethodHandleNatives$CallSiteContext (java.base@11.0.16.1)
   126:            14            448  java.lang.invoke.BoundMethodHandle$Species_L (java.base@11.0.16.1)
   127:            28            448  jdk.internal.reflect.DelegatingConstructorAccessorImpl (java.base@11.0.16.1)
   128:            11            440  java.lang.OutOfMemoryError (java.base@11.0.16.1)
   129:             6            416  [S (java.base@11.0.16.1)
   130:             4            416  java.util.jar.JarFile$JarFileEntry (java.base@11.0.16.1)
   131:            13            416  sun.util.resources.Bundles$CacheKey (java.base@11.0.16.1)
   132:            10            400  java.util.WeakHashMap$Entry (java.base@11.0.16.1)
   133:             1            384  com.intellij.rt.execution.application.AppMainV2$1
   134:             1            384  java.lang.ref.Finalizer$FinalizerThread (java.base@11.0.16.1)
   135:             8            384  java.nio.HeapCharBuffer (java.base@11.0.16.1)
   136:            12            384  java.util.concurrent.ConcurrentHashMap$ForwardingNode (java.base@11.0.16.1)
   137:             1            384  jdk.internal.misc.InnocuousThread (java.base@11.0.16.1)
   138:             1            376  java.lang.ref.Reference$ReferenceHandler (java.base@11.0.16.1)
   139:             9            360  java.security.CodeSource (java.base@11.0.16.1)
   140:            12            344  [Ljava.io.ObjectStreamField; (java.base@11.0.16.1)
   141:            14            336  java.lang.invoke.InnerClassLambdaMetafactory$1 (java.base@11.0.16.1)
   142:            21            336  java.lang.ref.ReferenceQueue$Lock (java.base@11.0.16.1)
   143:             6            336  java.nio.DirectLongBufferU (java.base@11.0.16.1)
   144:             6            336  sun.util.calendar.ZoneInfo (java.base@11.0.16.1)
   145:             8            328  [Ljava.lang.invoke.MethodType; (java.base@11.0.16.1)
   146:             5            328  [Ljava.util.regex.Pattern$Node; (java.base@11.0.16.1)
   147:             8            320  java.io.FileDescriptor (java.base@11.0.16.1)
   148:             5            320  jdk.internal.org.objectweb.asm.FieldWriter (java.base@11.0.16.1)
   149:            10            320  jdk.internal.org.objectweb.asm.Type (java.base@11.0.16.1)
   150:             8            320  sun.util.locale.LanguageTag (java.base@11.0.16.1)
   151:             1            296  [Ljava.lang.module.ModuleDescriptor; (java.base@11.0.16.1)
   152:             1            296  [Ljava.lang.module.ModuleReference; (java.base@11.0.16.1)
   153:             1            296  [Ljdk.internal.module.ModuleHashes; (java.base@11.0.16.1)
   154:             1            296  [Ljdk.internal.module.ModuleResolution; (java.base@11.0.16.1)
   155:             1            296  [Ljdk.internal.module.ModuleTarget; (java.base@11.0.16.1)
   156:             9            288  java.lang.ThreadLocal$ThreadLocalMap$Entry (java.base@11.0.16.1)
   157:            12            288  java.net.StandardSocketOptions$StdSocketOption (java.base@11.0.16.1)
   158:             9            288  java.util.Hashtable$Entry (java.base@11.0.16.1)
   159:             9            288  java.util.concurrent.ConcurrentHashMap$ReservationNode (java.base@11.0.16.1)
   160:             7            280  [Ljava.util.stream.StreamOpFlag$Type; (java.base@11.0.16.1)
   161:             7            280  [Ljava.util.stream.StreamOpFlag; (java.base@11.0.16.1)
   162:             3            280  [[Ljava.lang.String; (java.base@11.0.16.1)
   163:             7            280  java.security.ProtectionDomain (java.base@11.0.16.1)
   164:             7            280  java.util.HashMap$ValueIterator (java.base@11.0.16.1)
   165:             5            280  java.util.LinkedHashMap (java.base@11.0.16.1)
   166:             5            280  java.util.Properties (java.base@11.0.16.1)
   167:             5            280  java.util.concurrent.ConcurrentHashMap$EntryIterator (java.base@11.0.16.1)
   168:             7            264  [Ljava.lang.invoke.LambdaForm$NamedFunction; (java.base@11.0.16.1)
   169:             1            264  [Ljava.lang.module.ResolvedModule; (java.base@11.0.16.1)
   170:            11            264  java.io.ExpiringCache$Entry (java.base@11.0.16.1)
   171:            11            264  java.lang.invoke.MethodHandleImpl$Intrinsic (java.base@11.0.16.1)
   172:            11            264  java.util.regex.Pattern$CharProperty (java.base@11.0.16.1)
   173:            11            264  java.util.regex.Pattern$CharPropertyGreedy (java.base@11.0.16.1)
   174:            16            256  java.util.ImmutableCollections$ListN (java.base@11.0.16.1)
   175:             3            240  [Ljava.util.WeakHashMap$Entry; (java.base@11.0.16.1)
   176:             6            240  java.lang.ClassLoader$NativeLibrary (java.base@11.0.16.1)
   177:            10            240  java.nio.charset.CoderResult (java.base@11.0.16.1)
   178:            10            240  java.nio.file.StandardOpenOption (java.base@11.0.16.1)
   179:             6            240  java.util.HashMap$EntryIterator (java.base@11.0.16.1)
   180:             5            240  java.util.zip.ZipFile$ZipFileInputStream (java.base@11.0.16.1)
   181:             6            240  sun.util.locale.InternalLocaleBuilder (java.base@11.0.16.1)
   182:             5            240  sun.util.resources.Bundles$BundleReference (java.base@11.0.16.1)
   183:             4            224  java.io.FileCleanable (java.base@11.0.16.1)
   184:            14            224  java.lang.invoke.LambdaFormEditor (java.base@11.0.16.1)
   185:             5            200  [Ljava.lang.invoke.VarHandle$AccessType; (java.base@11.0.16.1)
   186:             5            200  java.lang.invoke.VarHandleObjects$FieldInstanceReadWrite (java.base@11.0.16.1)
   187:             5            200  java.util.EnumMap (java.base@11.0.16.1)
   188:             5            200  java.util.stream.StreamOpFlag (java.base@11.0.16.1)
   189:             5            200  sun.util.locale.StringTokenIterator (java.base@11.0.16.1)
   190:             4            192  [Ljava.util.Hashtable$Entry; (java.base@11.0.16.1)
   191:             6            192  java.lang.invoke.LambdaForm$BasicType (java.base@11.0.16.1)
   192:             6            192  java.util.ArrayDeque$DeqIterator (java.base@11.0.16.1)
   193:             4            192  java.util.Hashtable (java.base@11.0.16.1)
   194:             6            192  java.util.ImmutableCollections$MapN (java.base@11.0.16.1)
   195:             4            192  sun.nio.cs.StreamEncoder (java.base@11.0.16.1)
   196:             4            192  sun.nio.cs.UTF_8$Encoder (java.base@11.0.16.1)
   197:             2            192  sun.util.calendar.Gregorian$Date (java.base@11.0.16.1)
   198:             7            168  java.lang.Class$1 (java.base@11.0.16.1)
   199:             2            160  [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry; (java.base@11.0.16.1)
   200:             4            160  [Ljava.lang.invoke.LambdaForm$BasicType; (java.base@11.0.16.1)
   201:             4            160  java.io.BufferedWriter (java.base@11.0.16.1)
   202:             4            160  java.io.FileInputStream (java.base@11.0.16.1)
   203:             5            160  java.lang.invoke.VarHandle$AccessType (java.base@11.0.16.1)
   204:             5            160  java.util.RegularEnumSet (java.base@11.0.16.1)
   205:             5            160  sun.util.locale.provider.LocaleProviderAdapter$Type (java.base@11.0.16.1)
   206:             3            144  java.lang.ThreadGroup (java.base@11.0.16.1)
   207:             6            144  java.lang.WeakPairMap$Pair$Lookup (java.base@11.0.16.1)
   208:             6            144  java.security.SecureClassLoader$1 (java.base@11.0.16.1)
   209:             3            144  java.util.WeakHashMap (java.base@11.0.16.1)
   210:             6            144  jdk.internal.perf.PerfCounter (java.base@11.0.16.1)
   211:             3            144  sun.util.locale.provider.LocaleResources$ResourceReference (java.base@11.0.16.1)
   212:             2            128  [Ljava.lang.invoke.MethodHandleImpl$Intrinsic; (java.base@11.0.16.1)
   213:             2            128  java.io.ExpiringCache$1 (java.base@11.0.16.1)
   214:             8            128  java.lang.ThreadLocal (java.base@11.0.16.1)
   215:             2            128  java.util.stream.ReferencePipeline$3 (java.base@11.0.16.1)
   216:             1            128  sun.nio.fs.UnixFileAttributes (java.base@11.0.16.1)
   217:             4            128  sun.util.locale.provider.LocaleResources (java.base@11.0.16.1)
   218:             3            120  java.io.BufferedInputStream (java.base@11.0.16.1)
   219:             5            120  java.lang.module.ModuleDescriptor$Opens (java.base@11.0.16.1)
   220:             5            120  java.util.ArrayDeque (java.base@11.0.16.1)
   221:             5            120  java.util.Collections$SynchronizedSet (java.base@11.0.16.1)
   222:             5            120  java.util.stream.StreamOpFlag$Type (java.base@11.0.16.1)
   223:             1            120  jdk.internal.loader.ClassLoaders$AppClassLoader (java.base@11.0.16.1)
   224:             3            120  jdk.internal.loader.URLClassPath$JarLoader$2 (java.base@11.0.16.1)
   225:             5            120  jdk.net.ExtendedSocketOptions$ExtSocketOption (jdk.net@11.0.16.1)
   226:             5            120  sun.util.locale.ParseStatus (java.base@11.0.16.1)
   227:             7            112  [Ljava.security.Principal; (java.base@11.0.16.1)
   228:             7            112  java.lang.ClassLoader$2 (java.base@11.0.16.1)
   229:             2            112  java.nio.DirectIntBufferRU (java.base@11.0.16.1)
   230:             7            112  java.security.ProtectionDomain$Key (java.base@11.0.16.1)
   231:             2            112  java.util.ServiceLoader (java.base@11.0.16.1)
   232:             1            112  jdk.internal.loader.ClassLoaders$BootClassLoader (java.base@11.0.16.1)
   233:             1            112  jdk.internal.loader.ClassLoaders$PlatformClassLoader (java.base@11.0.16.1)
   234:             3             96  [Ljava.lang.Thread; (java.base@11.0.16.1)
   235:             4             96  java.io.OutputStreamWriter (java.base@11.0.16.1)
   236:             3             96  java.lang.Runtime$Version (java.base@11.0.16.1)
   237:             2             96  java.lang.invoke.BoundMethodHandle$SpeciesData (java.base@11.0.16.1)
   238:             4             96  java.lang.invoke.VarForm (java.base@11.0.16.1)
   239:             4             96  java.lang.module.ModuleDescriptor$Modifier (java.base@11.0.16.1)
   240:             4             96  java.lang.module.ModuleDescriptor$Requires$Modifier (java.base@11.0.16.1)
   241:             1             96  java.net.SocksSocketImpl (java.base@11.0.16.1)
   242:             3             96  java.nio.file.attribute.FileTime (java.base@11.0.16.1)
   243:             3             96  java.security.Permissions (java.base@11.0.16.1)
   244:             6             96  java.security.SecureClassLoader$CodeSourceKey (java.base@11.0.16.1)
   245:             3             96  java.util.Collections$UnmodifiableMap (java.base@11.0.16.1)
   246:             6             96  java.util.HashMap$EntrySet (java.base@11.0.16.1)
   247:             3             96  java.util.ServiceLoader$ProviderImpl (java.base@11.0.16.1)
   248:             2             96  java.util.StringTokenizer (java.base@11.0.16.1)
   249:             3             96  java.util.Vector (java.base@11.0.16.1)
   250:             4             96  java.util.stream.StreamShape (java.base@11.0.16.1)
   251:             1             96  jdk.internal.jimage.ImageReader$SharedImageReader (java.base@11.0.16.1)
   252:             2             96  jdk.internal.org.objectweb.asm.Frame (java.base@11.0.16.1)
   253:             3             96  sun.net.spi.DefaultProxySelector$NonProxyInfo (java.base@11.0.16.1)
   254:             2             96  sun.nio.cs.ISO_8859_1$Encoder (java.base@11.0.16.1)
   255:             3             96  sun.nio.fs.UnixPath (java.base@11.0.16.1)
   256:             3             96  sun.security.util.LazyCodeSourcePermissionCollection (java.base@11.0.16.1)
   257:             1             88  java.util.regex.Pattern (java.base@11.0.16.1)
   258:             2             80  [Ljava.lang.invoke.BoundMethodHandle$SpeciesData; (java.base@11.0.16.1)
   259:             2             80  [Lsun.util.locale.provider.LocaleProviderAdapter$Type; (java.base@11.0.16.1)
   260:             2             80  java.io.ExpiringCache (java.base@11.0.16.1)
   261:             2             80  java.io.FileOutputStream (java.base@11.0.16.1)
   262:             2             80  java.io.PrintStream (java.base@11.0.16.1)
   263:             2             80  java.lang.ModuleLayer (java.base@11.0.16.1)
   264:             2             80  java.lang.VirtualMachineError (java.base@11.0.16.1)
   265:             2             80  java.lang.invoke.DirectMethodHandle$Constructor (java.base@11.0.16.1)
   266:             2             80  java.lang.invoke.DirectMethodHandle$Special (java.base@11.0.16.1)
   267:             2             80  java.lang.module.Configuration (java.base@11.0.16.1)
   268:             5             80  java.util.Properties$EntrySet (java.base@11.0.16.1)
   269:             2             80  java.util.ServiceLoader$LazyClassPathLookupIterator (java.base@11.0.16.1)
   270:             5             80  java.util.jar.JarFile$1 (java.base@11.0.16.1)
   271:             5             80  java.util.stream.StreamOpFlag$MaskBuilder (java.base@11.0.16.1)
   272:             1             80  sun.util.cldr.CLDRLocaleProviderAdapter (java.base@11.0.16.1)
   273:             1             80  sun.util.locale.provider.JRELocaleProviderAdapter (java.base@11.0.16.1)
   274:             1             80  sun.util.locale.provider.LocaleProviderAdapter$NonExistentAdapter (java.base@11.0.16.1)
   275:             3             72  [Ljava.security.ProtectionDomain; (java.base@11.0.16.1)
   276:             3             72  java.io.ByteArrayOutputStream (java.base@11.0.16.1)
   277:             3             72  java.lang.PublicMethods$MethodList (java.base@11.0.16.1)
   278:             3             72  java.lang.WeakPairMap (java.base@11.0.16.1)
   279:             3             72  java.lang.invoke.Invokers (java.base@11.0.16.1)
   280:             3             72  java.net.Proxy$Type (java.base@11.0.16.1)
   281:             3             72  java.util.Collections$SetFromMap (java.base@11.0.16.1)
   282:             3             72  java.util.LinkedList$Node (java.base@11.0.16.1)
   283:             3             72  java.util.ServiceLoader$1 (java.base@11.0.16.1)
   284:             3             72  java.util.concurrent.atomic.AtomicLong (java.base@11.0.16.1)
   285:             1             72  java.util.regex.Matcher (java.base@11.0.16.1)
   286:             3             72  java.util.stream.Collector$Characteristics (java.base@11.0.16.1)
   287:             1             72  java.util.zip.ZipFile$Source (java.base@11.0.16.1)
   288:             3             72  jdk.internal.jimage.BasicImageReader$1 (java.base@11.0.16.1)
   289:             3             72  jdk.internal.misc.Signal (java.base@11.0.16.1)
   290:             3             72  jdk.internal.module.IllegalAccessLogger$Mode (java.base@11.0.16.1)
   291:             3             72  jdk.internal.reflect.NativeMethodAccessorImpl (java.base@11.0.16.1)
   292:             3             72  sun.util.resources.LocaleData$1 (java.base@11.0.16.1)
   293:             2             64  [Ljava.nio.charset.CoderResult; (java.base@11.0.16.1)
   294:             2             64  [Ljava.util.stream.Collector$Characteristics; (java.base@11.0.16.1)
   295:             2             64  java.io.BufferedOutputStream (java.base@11.0.16.1)
   296:             2             64  java.io.DataInputStream (java.base@11.0.16.1)
   297:             2             64  java.lang.Package (java.base@11.0.16.1)
   298:             1             64  java.lang.invoke.InvokerBytecodeGenerator (java.base@11.0.16.1)
   299:             2             64  java.lang.ref.ReferenceQueue$Null (java.base@11.0.16.1)
   300:             2             64  java.net.URI$Parser (java.base@11.0.16.1)
   301:             2             64  java.security.BasicPermissionCollection (java.base@11.0.16.1)
   302:             4             64  java.util.ResourceBundle$ResourceBundleProviderHelper$$Lambda$19/0x00000008000a8440 (java.base@11.0.16.1)
   303:             2             64  java.util.ServiceLoader$ModuleServicesLookupIterator (java.base@11.0.16.1)
   304:             4             64  java.util.concurrent.ConcurrentHashMap$EntrySetView (java.base@11.0.16.1)
   305:             1             64  java.util.jar.JarFile (java.base@11.0.16.1)
   306:             2             64  java.util.stream.FindOps$FindOp (java.base@11.0.16.1)
   307:             4             64  jdk.internal.reflect.ReflectionFactory$GetReflectionFactoryAction (java.base@11.0.16.1)
   308:             1             56  [Ljava.nio.file.StandardOpenOption; (java.base@11.0.16.1)
   309:             1             56  [Ljava.util.regex.Pattern$GroupHead; (java.base@11.0.16.1)
   310:             1             56  [[I (java.base@11.0.16.1)
   311:             1             56  java.lang.invoke.BoundMethodHandle$Specializer (java.base@11.0.16.1)
   312:             1             56  java.net.SocketInputStream (java.base@11.0.16.1)
   313:             1             56  java.nio.DirectIntBufferU (java.base@11.0.16.1)
   314:             1             56  jdk.internal.ref.CleanerImpl$SoftCleanableRef (java.base@11.0.16.1)
   315:             1             48  [Ljava.util.concurrent.TimeUnit; (java.base@11.0.16.1)
   316:             1             48  java.io.BufferedReader (java.base@11.0.16.1)
   317:             2             48  java.io.File$PathStatus (java.base@11.0.16.1)
   318:             3             48  java.io.FileInputStream$1 (java.base@11.0.16.1)
   319:             2             48  java.lang.Class$3 (java.base@11.0.16.1)
   320:             2             48  java.lang.NamedPackage (java.base@11.0.16.1)
   321:             2             48  java.lang.RuntimePermission (java.base@11.0.16.1)
   322:             2             48  java.lang.StringCoding$Result (java.base@11.0.16.1)
   323:             2             48  java.lang.ThreadLocal$ThreadLocalMap (java.base@11.0.16.1)
   324:             1             48  java.lang.invoke.BoundMethodHandle$Specializer$Factory (java.base@11.0.16.1)
   325:             1             48  java.lang.invoke.LambdaFormBuffer (java.base@11.0.16.1)
   326:             1             48  java.lang.module.Resolver (java.base@11.0.16.1)
   327:             1             48  java.net.SocketCleanable (java.base@11.0.16.1)
   328:             3             48  java.nio.charset.CodingErrorAction (java.base@11.0.16.1)
   329:             2             48  java.security.Permissions$1 (java.base@11.0.16.1)
   330:             2             48  java.util.Date (java.base@11.0.16.1)
   331:             3             48  java.util.HashMap$Values (java.base@11.0.16.1)
   332:             3             48  java.util.LinkedHashMap$LinkedKeySet (java.base@11.0.16.1)
   333:             3             48  java.util.ResourceBundle$NoFallbackControl (java.base@11.0.16.1)
   334:             2             48  java.util.ServiceLoader$2 (java.base@11.0.16.1)
   335:             2             48  java.util.ServiceLoader$3 (java.base@11.0.16.1)
   336:             1             48  java.util.concurrent.ConcurrentSkipListMap (java.base@11.0.16.1)
   337:             2             48  java.util.concurrent.CopyOnWriteArrayList$COWIterator (java.base@11.0.16.1)
   338:             3             48  java.util.concurrent.atomic.AtomicInteger (java.base@11.0.16.1)
   339:             2             48  java.util.stream.ReferencePipeline$3$1 (java.base@11.0.16.1)
   340:             3             48  java.util.zip.CRC32 (java.base@11.0.16.1)
   341:             1             48  jdk.internal.jimage.ImageHeader (java.base@11.0.16.1)
   342:             2             48  jdk.internal.jimage.ImageLocation (java.base@11.0.16.1)
   343:             2             48  jdk.internal.loader.URLClassPath$3 (java.base@11.0.16.1)
   344:             1             48  jdk.internal.loader.URLClassPath$JarLoader (java.base@11.0.16.1)
   345:             2             48  jdk.internal.misc.Signal$NativeHandler (java.base@11.0.16.1)
   346:             1             48  jdk.internal.ref.CleanerImpl$WeakCleanableRef (java.base@11.0.16.1)
   347:             3             48  jdk.internal.reflect.DelegatingMethodAccessorImpl (java.base@11.0.16.1)
   348:             1             48  sun.nio.cs.StreamDecoder (java.base@11.0.16.1)
   349:             2             48  sun.nio.cs.Surrogate$Parser (java.base@11.0.16.1)
   350:             3             48  sun.util.resources.LocaleData (java.base@11.0.16.1)
   351:             1             48  sun.util.resources.TimeZoneNames (java.base@11.0.16.1)
   352:             1             48  sun.util.resources.TimeZoneNames_en (java.base@11.0.16.1)
   353:             1             48  sun.util.resources.cldr.TimeZoneNames (java.base@11.0.16.1)
   354:             1             48  sun.util.resources.cldr.TimeZoneNames_en (java.base@11.0.16.1)
   355:             2             40  [Ljdk.internal.org.objectweb.asm.Type; (java.base@11.0.16.1)
   356:             1             40  [[Ljava.lang.invoke.LambdaForm$Name; (java.base@11.0.16.1)
   357:             1             40  java.lang.ArithmeticException (java.base@11.0.16.1)
   358:             1             40  java.lang.InternalError (java.base@11.0.16.1)
   359:             1             40  java.lang.NullPointerException (java.base@11.0.16.1)
   360:             1             40  java.lang.Package$VersionInfo (java.base@11.0.16.1)
   361:             1             40  java.lang.invoke.DirectMethodHandle$Accessor (java.base@11.0.16.1)
   362:             1             40  java.lang.invoke.DirectMethodHandle$Interface (java.base@11.0.16.1)
   363:             1             40  java.util.IdentityHashMap (java.base@11.0.16.1)
   364:             1             40  java.util.Properties$LineReader (java.base@11.0.16.1)
   365:             1             40  java.util.ResourceBundle$2 (java.base@11.0.16.1)
   366:             1             40  java.util.StringJoiner (java.base@11.0.16.1)
   367:             1             40  java.util.zip.ZipFile$Source$End (java.base@11.0.16.1)
   368:             1             40  jdk.internal.loader.URLClassPath (java.base@11.0.16.1)
   369:             1             40  jdk.internal.ref.CleanerImpl$CleanerCleanable (java.base@11.0.16.1)
   370:             1             40  sun.nio.cs.US_ASCII$Decoder (java.base@11.0.16.1)
   371:             1             40  sun.nio.cs.UTF_8$Decoder (java.base@11.0.16.1)
   372:             1             40  sun.util.resources.Bundles$1 (java.base@11.0.16.1)
   373:             1             32  [Ljava.lang.OutOfMemoryError; (java.base@11.0.16.1)
   374:             2             32  [Ljava.lang.StackTraceElement; (java.base@11.0.16.1)
   375:             1             32  [Ljava.lang.ThreadGroup; (java.base@11.0.16.1)
   376:             2             32  [Ljava.lang.annotation.Annotation; (java.base@11.0.16.1)
   377:             1             32  [Ljava.lang.module.ModuleDescriptor$Modifier; (java.base@11.0.16.1)
   378:             1             32  [Ljava.lang.module.ModuleDescriptor$Requires$Modifier; (java.base@11.0.16.1)
   379:             1             32  [Ljava.net.Proxy$Type; (java.base@11.0.16.1)
   380:             1             32  [Ljava.util.stream.StreamShape; (java.base@11.0.16.1)
   381:             1             32  [Ljdk.internal.module.IllegalAccessLogger$Mode; (java.base@11.0.16.1)
   382:             1             32  [Lsun.nio.fs.NativeBuffer; (java.base@11.0.16.1)
   383:             1             32  java.io.ByteArrayInputStream (java.base@11.0.16.1)
   384:             1             32  java.io.RandomAccessFile (java.base@11.0.16.1)
   385:             1             32  java.io.UnixFileSystem (java.base@11.0.16.1)
   386:             2             32  java.lang.Boolean (java.base@11.0.16.1)
   387:             1             32  java.lang.invoke.VarHandleInts$FieldInstanceReadWrite (java.base@11.0.16.1)
   388:             1             32  java.lang.module.ModuleDescriptor$Version (java.base@11.0.16.1)
   389:             1             32  java.net.InetAddress$InetAddressHolder (java.base@11.0.16.1)
   390:             1             32  java.net.Socket (java.base@11.0.16.1)
   391:             2             32  java.nio.ByteOrder (java.base@11.0.16.1)
   392:             1             32  java.util.ArrayList$SubList (java.base@11.0.16.1)
   393:             1             32  java.util.LinkedList (java.base@11.0.16.1)
   394:             2             32  java.util.ResourceBundle$SingleFormatControl (java.base@11.0.16.1)
   395:             2             32  java.util.WeakHashMap$KeySet (java.base@11.0.16.1)
   396:             1             32  java.util.jar.Manifest$FastInputStream (java.base@11.0.16.1)
   397:             1             32  java.util.regex.Pattern$Branch (java.base@11.0.16.1)
   398:             1             32  java.util.stream.Collectors$CollectorImpl (java.base@11.0.16.1)
   399:             1             32  java.util.stream.ReduceOps$3 (java.base@11.0.16.1)
   400:             1             32  java.util.stream.ReduceOps$3ReducingSink (java.base@11.0.16.1)
   401:             1             32  java.util.zip.ZipFile$CleanableResource (java.base@11.0.16.1)
   402:             2             32  jdk.internal.loader.ClassLoaderValue (java.base@11.0.16.1)
   403:             1             32  jdk.internal.loader.URLClassPath$FileLoader$1 (java.base@11.0.16.1)
   404:             1             32  jdk.internal.module.IllegalAccessLogger (java.base@11.0.16.1)
   405:             1             32  jdk.internal.module.IllegalAccessLogger$Builder (java.base@11.0.16.1)
   406:             2             32  jdk.internal.module.ServicesCatalog (java.base@11.0.16.1)
   407:             1             32  jdk.internal.ref.CleanerImpl (java.base@11.0.16.1)
   408:             2             32  jdk.internal.reflect.ConstantPool (java.base@11.0.16.1)
   409:             2             32  jdk.internal.vm.PostVMInitHook$1 (java.base@11.0.16.1)
   410:             1             32  sun.instrument.InstrumentationImpl (java.instrument@11.0.16.1)
   411:             1             32  sun.net.spi.DefaultProxySelector$4 (java.base@11.0.16.1)
   412:             1             32  sun.nio.cs.StandardCharsets (java.base@11.0.16.1)
   413:             1             32  sun.nio.fs.MacOSXFileSystem (java.base@11.0.16.1)
   414:             1             32  sun.nio.fs.NativeBuffer (java.base@11.0.16.1)
   415:             2             32  sun.util.resources.cldr.provider.CLDRLocaleDataMetaInfo (jdk.localedata@11.0.16.1)
   416:             1             24  [Ljava.io.File$PathStatus; (java.base@11.0.16.1)
   417:             1             24  [Ljava.net.InetAddress; (java.base@11.0.16.1)
   418:             1             24  java.io.InputStreamReader (java.base@11.0.16.1)
   419:             1             24  java.lang.Double (java.base@11.0.16.1)
   420:             1             24  java.lang.invoke.InvokerBytecodeGenerator$CpPatch (java.base@11.0.16.1)
   421:             1             24  java.lang.invoke.MethodType$ConcurrentWeakInternSet (java.base@11.0.16.1)
   422:             1             24  java.lang.reflect.ReflectPermission (java.base@11.0.16.1)
   423:             1             24  java.net.Inet4Address (java.base@11.0.16.1)
   424:             1             24  java.net.Inet6AddressImpl (java.base@11.0.16.1)
   425:             1             24  java.net.InetSocketAddress$InetSocketAddressHolder (java.base@11.0.16.1)
   426:             1             24  java.net.Proxy (java.base@11.0.16.1)
   427:             1             24  java.util.Collections$EmptyMap (java.base@11.0.16.1)
   428:             1             24  java.util.Collections$UnmodifiableRandomAccessList (java.base@11.0.16.1)
   429:             1             24  java.util.Locale$Cache (java.base@11.0.16.1)
   430:             1             24  java.util.ResourceBundle$Control$CandidateListCache (java.base@11.0.16.1)
   431:             1             24  java.util.jar.Manifest (java.base@11.0.16.1)
   432:             1             24  java.util.regex.Pattern$Start (java.base@11.0.16.1)
   433:             1             24  java.util.regex.Pattern$TreeInfo (java.base@11.0.16.1)
   434:             1             24  java.util.zip.ZipCoder$UTF8 (java.base@11.0.16.1)
   435:             1             24  java.util.zip.ZipFile$Source$Key (java.base@11.0.16.1)
   436:             1             24  jdk.internal.jimage.ImageReader (java.base@11.0.16.1)
   437:             1             24  jdk.internal.loader.BuiltinClassLoader$5 (java.base@11.0.16.1)
   438:             1             24  jdk.internal.loader.FileURLMapper (java.base@11.0.16.1)
   439:             1             24  jdk.internal.loader.URLClassPath$FileLoader (java.base@11.0.16.1)
   440:             1             24  jdk.internal.misc.InnocuousThread$2 (java.base@11.0.16.1)
   441:             1             24  jdk.internal.module.ModuleBootstrap$SafeModuleFinder (java.base@11.0.16.1)
   442:             1             24  jdk.internal.module.ModuleHashes (java.base@11.0.16.1)
   443:             1             24  jdk.internal.module.ModuleHashes$Builder (java.base@11.0.16.1)
   444:             1             24  jdk.internal.module.SystemModuleFinders$SystemModuleFinder (java.base@11.0.16.1)
   445:             1             24  jdk.internal.module.SystemModuleFinders$SystemModuleReader (java.base@11.0.16.1)
   446:             1             24  jdk.internal.ref.CleanerFactory$1$1 (java.base@11.0.16.1)
   447:             1             24  sun.instrument.InstrumentationImpl$1 (java.instrument@11.0.16.1)
   448:             1             24  sun.instrument.TransformerManager (java.instrument@11.0.16.1)
   449:             1             24  sun.net.sdp.SdpProvider (java.base@11.0.16.1)
   450:             1             24  sun.nio.cs.ISO_8859_1 (java.base@11.0.16.1)
   451:             1             24  sun.nio.cs.ThreadLocalCoders$1 (java.base@11.0.16.1)
   452:             1             24  sun.nio.cs.ThreadLocalCoders$2 (java.base@11.0.16.1)
   453:             1             24  sun.nio.cs.US_ASCII (java.base@11.0.16.1)
   454:             1             24  sun.nio.cs.UTF_8 (java.base@11.0.16.1)
   455:             1             24  sun.nio.fs.NativeBuffer$Deallocator (java.base@11.0.16.1)
   456:             1             24  sun.nio.fs.UnixFileAttributeViews$Basic (java.base@11.0.16.1)
   457:             1             24  sun.security.action.GetPropertyAction (java.base@11.0.16.1)
   458:             1             24  sun.util.cldr.CLDRTimeZoneNameProviderImpl (java.base@11.0.16.1)
   459:             1             24  sun.util.locale.BaseLocale$Cache (java.base@11.0.16.1)
   460:             1             24  sun.util.locale.provider.LocaleServiceProviderPool (java.base@11.0.16.1)
   461:             1             24  sun.util.locale.provider.TimeZoneNameProviderImpl (java.base@11.0.16.1)
   462:             1             16  [D (java.base@11.0.16.1)
   463:             1             16  [F (java.base@11.0.16.1)
   464:             1             16  [Ljava.lang.Throwable; (java.base@11.0.16.1)
   465:             1             16  [Ljava.nio.file.LinkOption; (java.base@11.0.16.1)
   466:             1             16  [Ljava.nio.file.Path; (java.base@11.0.16.1)
   467:             1             16  [Ljava.security.cert.Certificate; (java.base@11.0.16.1)
   468:             1             16  [Ljava.util.regex.IntHashSet; (java.base@11.0.16.1)
   469:             1             16  [Lsun.instrument.TransformerManager$TransformerInfo; (java.instrument@11.0.16.1)
   470:             1             16  [Lsun.util.calendar.ZoneInfoFile$ZoneOffsetTransitionRule; (java.base@11.0.16.1)
   471:             1             16  [Z (java.base@11.0.16.1)
   472:             1             16  java.io.FileDescriptor$1 (java.base@11.0.16.1)
   473:             1             16  java.io.RandomAccessFile$2 (java.base@11.0.16.1)
   474:             1             16  java.lang.CharacterDataLatin1 (java.base@11.0.16.1)
   475:             1             16  java.lang.Float (java.base@11.0.16.1)
   476:             1             16  java.lang.ModuleLayer$Controller (java.base@11.0.16.1)
   477:             1             16  java.lang.Runtime (java.base@11.0.16.1)
   478:             1             16  java.lang.String$CaseInsensitiveComparator (java.base@11.0.16.1)
   479:             1             16  java.lang.StringCoding$1 (java.base@11.0.16.1)
   480:             1             16  java.lang.System$2 (java.base@11.0.16.1)
   481:             1             16  java.lang.Terminator$1 (java.base@11.0.16.1)
   482:             1             16  java.lang.invoke.ClassSpecializer$1 (java.base@11.0.16.1)
   483:             1             16  java.lang.invoke.MemberName$Factory (java.base@11.0.16.1)
   484:             1             16  java.lang.invoke.MethodHandleImpl$1 (java.base@11.0.16.1)
   485:             1             16  java.lang.invoke.VarHandle$1 (java.base@11.0.16.1)
   486:             1             16  java.lang.module.ModuleDescriptor$1 (java.base@11.0.16.1)
   487:             1             16  java.lang.module.ModuleFinder$1 (java.base@11.0.16.1)
   488:             1             16  java.lang.ref.Cleaner (java.base@11.0.16.1)
   489:             1             16  java.lang.ref.Cleaner$1 (java.base@11.0.16.1)
   490:             1             16  java.lang.ref.Reference$1 (java.base@11.0.16.1)
   491:             1             16  java.lang.reflect.ReflectAccess (java.base@11.0.16.1)
   492:             1             16  java.net.AbstractPlainSocketImpl$1 (java.base@11.0.16.1)
   493:             1             16  java.net.InetAddress$1 (java.base@11.0.16.1)
   494:             1             16  java.net.InetAddress$2 (java.base@11.0.16.1)
   495:             1             16  java.net.InetAddress$PlatformNameService (java.base@11.0.16.1)
   496:             1             16  java.net.InetSocketAddress (java.base@11.0.16.1)
   497:             1             16  java.net.Socket$2 (java.base@11.0.16.1)
   498:             1             16  java.net.SocksSocketImpl$3 (java.base@11.0.16.1)
   499:             1             16  java.net.URI$1 (java.base@11.0.16.1)
   500:             1             16  java.net.URL$3 (java.base@11.0.16.1)
   501:             1             16  java.net.URL$DefaultFactory (java.base@11.0.16.1)
   502:             1             16  java.nio.Bits$1 (java.base@11.0.16.1)
   503:             1             16  java.nio.Buffer$1 (java.base@11.0.16.1)
   504:             1             16  java.nio.file.FileSystems$DefaultFileSystemHolder$1 (java.base@11.0.16.1)
   505:             1             16  java.security.ProtectionDomain$JavaSecurityAccessImpl (java.base@11.0.16.1)
   506:             1             16  java.util.Collections$EmptyEnumeration (java.base@11.0.16.1)
   507:             1             16  java.util.Collections$EmptyIterator (java.base@11.0.16.1)
   508:             1             16  java.util.Collections$EmptyList (java.base@11.0.16.1)
   509:             1             16  java.util.Collections$EmptySet (java.base@11.0.16.1)
   510:             1             16  java.util.Collections$SingletonSet (java.base@11.0.16.1)
   511:             1             16  java.util.EnumMap$1 (java.base@11.0.16.1)
   512:             1             16  java.util.IdentityHashMap$KeySet (java.base@11.0.16.1)
   513:             1             16  java.util.LinkedHashSet (java.base@11.0.16.1)
   514:             1             16  java.util.ResourceBundle$1 (java.base@11.0.16.1)
   515:             1             16  java.util.ResourceBundle$Control (java.base@11.0.16.1)
   516:             1             16  java.util.Spliterators$EmptySpliterator$OfDouble (java.base@11.0.16.1)
   517:             1             16  java.util.Spliterators$EmptySpliterator$OfInt (java.base@11.0.16.1)
   518:             1             16  java.util.Spliterators$EmptySpliterator$OfLong (java.base@11.0.16.1)
   519:             1             16  java.util.Spliterators$EmptySpliterator$OfRef (java.base@11.0.16.1)
   520:             1             16  java.util.concurrent.ConcurrentHashMap$ValuesView (java.base@11.0.16.1)
   521:             1             16  java.util.concurrent.ConcurrentSkipListSet (java.base@11.0.16.1)
   522:             1             16  java.util.concurrent.atomic.AtomicBoolean (java.base@11.0.16.1)
   523:             1             16  java.util.jar.Attributes (java.base@11.0.16.1)
   524:             1             16  java.util.jar.JavaUtilJarAccessImpl (java.base@11.0.16.1)
   525:             1             16  java.util.regex.Pattern$$Lambda$15/0x0000000800066c40 (java.base@11.0.16.1)
   526:             1             16  java.util.regex.Pattern$1 (java.base@11.0.16.1)
   527:             1             16  java.util.regex.Pattern$BranchConn (java.base@11.0.16.1)
   528:             1             16  java.util.regex.Pattern$LastNode (java.base@11.0.16.1)
   529:             1             16  java.util.regex.Pattern$Node (java.base@11.0.16.1)
   530:             1             16  java.util.stream.Collectors$$Lambda$5/0x0000000800061440 (java.base@11.0.16.1)
   531:             1             16  java.util.stream.Collectors$$Lambda$6/0x0000000800061840 (java.base@11.0.16.1)
   532:             1             16  java.util.stream.Collectors$$Lambda$7/0x0000000800061c40 (java.base@11.0.16.1)
   533:             1             16  java.util.stream.Collectors$$Lambda$8/0x0000000800062040 (java.base@11.0.16.1)
   534:             1             16  java.util.stream.FindOps$FindSink$OfRef$$Lambda$10/0x0000000800062840 (java.base@11.0.16.1)
   535:             1             16  java.util.stream.FindOps$FindSink$OfRef$$Lambda$11/0x0000000800062c40 (java.base@11.0.16.1)
   536:             1             16  java.util.stream.FindOps$FindSink$OfRef$$Lambda$12/0x0000000800063040 (java.base@11.0.16.1)
   537:             1             16  java.util.stream.FindOps$FindSink$OfRef$$Lambda$13/0x0000000800063440 (java.base@11.0.16.1)
   538:             1             16  java.util.zip.ZipFile$1 (java.base@11.0.16.1)
   539:             1             16  jdk.internal.jimage.ImageReaderFactory$1 (java.base@11.0.16.1)
   540:             1             16  jdk.internal.jimage.ImageStringsReader (java.base@11.0.16.1)
   541:             1             16  jdk.internal.jimage.NativeImageBuffer$1 (java.base@11.0.16.1)
   542:             1             16  jdk.internal.jimage.decompressor.Decompressor (java.base@11.0.16.1)
   543:             1             16  jdk.internal.loader.URLClassPath$JarLoader$1 (java.base@11.0.16.1)
   544:             1             16  jdk.internal.misc.InnocuousThread$3 (java.base@11.0.16.1)
   545:             1             16  jdk.internal.misc.TerminatingThreadLocal$1 (java.base@11.0.16.1)
   546:             1             16  jdk.internal.misc.Unsafe (java.base@11.0.16.1)
   547:             1             16  jdk.internal.module.DefaultRoots$$Lambda$1/0x0000000800060040 (java.base@11.0.16.1)
   548:             1             16  jdk.internal.module.DefaultRoots$$Lambda$2/0x0000000800060840 (java.base@11.0.16.1)
   549:             1             16  jdk.internal.module.DefaultRoots$$Lambda$3/0x0000000800060c40 (java.base@11.0.16.1)
   550:             1             16  jdk.internal.module.DefaultRoots$$Lambda$4/0x0000000800061040 (java.base@11.0.16.1)
   551:             1             16  jdk.internal.module.DefaultRoots$$Lambda$9/0x0000000800062440 (java.base@11.0.16.1)
   552:             1             16  jdk.internal.module.ModuleLoaderMap$Mapper (java.base@11.0.16.1)
   553:             1             16  jdk.internal.module.ModulePatcher (java.base@11.0.16.1)
   554:             1             16  jdk.internal.module.ModuleTarget (java.base@11.0.16.1)
   555:             1             16  jdk.internal.module.SystemModules$all (java.base@11.0.16.1)
   556:             1             16  jdk.internal.perf.Perf (java.base@11.0.16.1)
   557:             1             16  jdk.internal.perf.Perf$GetPerfAction (java.base@11.0.16.1)
   558:             1             16  jdk.internal.ref.CleanerFactory$1 (java.base@11.0.16.1)
   559:             1             16  jdk.internal.reflect.ReflectionFactory (java.base@11.0.16.1)
   560:             1             16  jdk.internal.util.Preconditions$1 (java.base@11.0.16.1)
   561:             1             16  jdk.net.ExtendedSocketOptions$1 (jdk.net@11.0.16.1)
   562:             1             16  jdk.net.ExtendedSocketOptions$PlatformSocketOptions$1 (jdk.net@11.0.16.1)
   563:             1             16  jdk.net.MacOSXSocketOptions (jdk.net@11.0.16.1)
   564:             1             16  jdk.net.MacOSXSocketOptions$$Lambda$14/0x0000000800066840 (jdk.net@11.0.16.1)
   565:             1             16  sun.net.NetProperties$1 (java.base@11.0.16.1)
   566:             1             16  sun.net.spi.DefaultProxySelector (java.base@11.0.16.1)
   567:             1             16  sun.net.spi.DefaultProxySelector$1 (java.base@11.0.16.1)
   568:             1             16  sun.net.www.protocol.file.Handler (java.base@11.0.16.1)
   569:             1             16  sun.net.www.protocol.jar.Handler (java.base@11.0.16.1)
   570:             1             16  sun.net.www.protocol.jrt.Handler (java.base@11.0.16.1)
   571:             1             16  sun.nio.fs.MacOSXFileSystemProvider (java.base@11.0.16.1)
   572:             1             16  sun.nio.fs.NativeBuffers$1 (java.base@11.0.16.1)
   573:             1             16  sun.nio.fs.UnixFileAttributes$UnixAsBasicFileAttributes (java.base@11.0.16.1)
   574:             1             16  sun.nio.fs.UnixNativeDispatcher$1 (java.base@11.0.16.1)
   575:             1             16  sun.security.action.GetBooleanAction (java.base@11.0.16.1)
   576:             1             16  sun.util.calendar.Gregorian (java.base@11.0.16.1)
   577:             1             16  sun.util.calendar.ZoneInfoFile$1 (java.base@11.0.16.1)
   578:             1             16  sun.util.calendar.ZoneInfoFile$Checksum (java.base@11.0.16.1)
   579:             1             16  sun.util.cldr.CLDRBaseLocaleDataMetaInfo (java.base@11.0.16.1)
   580:             1             16  sun.util.cldr.CLDRLocaleProviderAdapter$$Lambda$16/0x0000000800065840 (java.base@11.0.16.1)
   581:             1             16  sun.util.cldr.CLDRLocaleProviderAdapter$1 (java.base@11.0.16.1)
   582:             1             16  sun.util.locale.InternalLocaleBuilder$CaseInsensitiveChar (java.base@11.0.16.1)
   583:             1             16  sun.util.locale.provider.JRELocaleProviderAdapter$$Lambda$17/0x0000000800065c40 (java.base@11.0.16.1)
   584:             1             16  sun.util.locale.provider.JRELocaleProviderAdapter$$Lambda$18/0x00000008000a8040 (java.base@11.0.16.1)
   585:             1             16  sun.util.locale.provider.TimeZoneNameUtility$TimeZoneNameGetter (java.base@11.0.16.1)
   586:             1             16  sun.util.resources.LocaleData$LocaleDataStrategy (java.base@11.0.16.1)
   587:             1             16  sun.util.resources.provider.NonBaseLocaleDataMetaInfo (jdk.localedata@11.0.16.1)
  Total         45982       13547936
  ```

### 5.5 使用3：其他作用

* `jmap -permstat pid`：查看系统的ClassLoader信息
* `jmap -finalizerinfo`：查看堆积在finalizer队列中的对象

### 5.6 小结

* 由于jmap将访问堆中的所有对象，为了保证再次过程中不被应用线程干扰，jmap需要借助安全点机制，让所有线程停留在不改变堆中数据的状态。也就是说，有jmap导出的堆快照必定是安全点位置的。这可能导致基于该堆快照的分析结果存在偏差

* 假设在编译生成的机器码中，某些对象的生命周期在两个安全点之间，那么：live选项将无法探知到这些对象
* 另外，如果某个线程长时间无法跑到安全点，jmap将一直等下去
* 与前面的jstat则不同，垃圾回收器会主动将jstat所需要的摘要数据保存至固定位置之中，而jstat只需直接读取即可

## 6、jhat：JDK自带堆分析工具

### 6.1 基本情况

* jhat（JVM Heap Analysis Tool）：Sun JDK提供的jhat命令与jmap命令搭配使用，用于分析jmap生成的heap dump 文件（堆转储快照）。jhat内置了一个微型的HTTP/HTML服务器，生成dump文件的分析结果，，用户可以在浏览器中查看分析结果（分析虚拟机转储快照信息）

* 使用了jhat命令，就启动了一个http服务，端口是7000，即http://localhost:7000，就可以在浏览器里分析

* 说明：jhat命令在JDK9，JDK10已经删除，官方建议用VisualVM代替

  ```shell
  [root@cnndr4tfrapp01 ~]# jhat -help
  Usage:  jhat [-stack <bool>] [-refs <bool>] [-port <port>] [-baseline <file>] [-debug <int>] [-version] [-h|-help] <file>
  
          -J<flag>          Pass <flag> directly to the runtime system. For
                            example, -J-mx512m to use a maximum heap size of 512MB
          -stack false:     Turn off tracking object allocation call stack.
          -refs false:      Turn off tracking of references to objects
          -port <port>:     Set the port for the HTTP server.  Defaults to 7000
          -exclude <file>:  Specify a file that lists data members that should
                            be excluded from the reachableFrom query.
          -baseline <file>: Specify a baseline object dump.  Objects in
                            both heap dumps with the same ID and same class will
                            be marked as not being "new".
          -debug <int>:     Set debug level.
                              0:  No debug output
                              1:  Debug hprof file parsing
                              2:  Debug hprof file parsing, no server
          -version          Report version number
          -h|-help          Print this help and exit
          <file>            The file to read
  
  For a dump file that contains multiple heap dumps,
  you may specify which dump in the file
  by appending "#<number>" to the file name, i.e. "foo.hprof#3".
  
  All boolean options default to "true"
   
  ```

### 6.2 基本语法

```shell
smc@linjianguodeMacBook-Pro JVMDemo % jhat ./1.hprof 
Reading from ./1.hprof...
Dump file created Sun Dec 24 02:24:43 CST 2023
Snapshot read, resolving...
Resolving 15655 objects...
Chasing references, expect 3 dots...
Eliminating duplicate references...
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.

```

![image-20231224145803174](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241458468.png)

![image-20231224145602339](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241456593.png)

* option参数：stack false|true
  * 关闭|打开对象分配调用栈跟踪
* option参数：refs flase|true
  * 关闭|打开对象引用跟踪
* option参数：port port-number
  * 设置jhat HTTP Server的端口号，默认7000
* option参数：exclude exclude-file
  * 执行对象查询时需要排除的数据成员
* option参数：baseline exclude-file
  * 指定一个基准堆转储
* option参数：debug int
  * 设置debug级别
* option参数：-version
  * 启动后显示版本信息就退出
* option参数：-j\<flag\>
  * 传入启动参数，比如-J -Xmx512m

## 7、jstack：打印JVM中线程快照

### 7.1 基本快照

* jstack（JVM Stack Trace）:用于生成虚拟机指定进程当前时刻的线程快照（虚拟机堆栈跟踪）。线程快照就是当前虚拟机内指定进程的每一条线程正在执行的方法堆栈的集合
* 生成线程快照的作用：可用与定位线程长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等问题。这些都是导致线程长时间停顿的常见原因。当线程出现停顿时，就可以用jstack显示各个县城调用的堆栈情况
* 在thread dump中，要留意下面几种状态
  * 死锁，Deadlock（重点关注）
  * 等待资源，Waiting on condition（重点关注）
  * 等待获取监视器，Waiting on monitor entry（重点关注）
  * 阻塞，Blocked（重点关注）
  * 执行中，Runnable
  * 暂停，Suspended

```shell
smc@linjianguodeMacBook-Pro JVMDemo % jstack -help
Usage:
    jstack [-l][-e] <pid>
        (to connect to running process)

Options:
    -l  long listing. Prints additional information about locks
    -e  extended listing. Prints additional information about threads
    -? -h --help -help to print this help message
smc@linjianguodeMacBook-Pro JVMDemo % 

```

### 7.2 基本语法

```shell
smc@linjianguodeMacBook-Pro JVMDemo % jps
10854 Jps
10827 Launcher
96394 
10828 ThreadDeadLock
smc@linjianguodeMacBook-Pro JVMDemo % jstack 10828
2023-12-24 16:22:55
Full thread dump Java HotSpot(TM) 64-Bit Server VM (11.0.16.1+1-LTS-1 mixed mode):

Threads class SMR info:
_java_thread_list=0x000060000341faa0, length=15, elements={
0x00007fe68802d000, 0x00007fe68780f000, 0x00007fe68684c000, 0x00007fe68684f000,
0x00007fe687821000, 0x00007fe6888da800, 0x00007fe686850000, 0x00007fe686887000,
0x00007fe6868f9000, 0x00007fe68897c800, 0x00007fe68897d800, 0x00007fe687826800,
0x00007fe68898a800, 0x00007fe68898d000, 0x00007fe6868fb800
}

"Reference Handler" #2 daemon prio=10 os_prio=31 cpu=0.21ms elapsed=48.06s tid=0x00007fe68802d000 nid=0x4b03 waiting on condition  [0x0000700004aa1000]
   java.lang.Thread.State: RUNNABLE
        at java.lang.ref.Reference.waitForReferencePendingList(java.base@11.0.16.1/Native Method)
        at java.lang.ref.Reference.processPendingReferences(java.base@11.0.16.1/Reference.java:241)
        at java.lang.ref.Reference$ReferenceHandler.run(java.base@11.0.16.1/Reference.java:213)

"Finalizer" #3 daemon prio=8 os_prio=31 cpu=0.46ms elapsed=48.06s tid=0x00007fe68780f000 nid=0x4903 in Object.wait()  [0x0000700004ba4000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(java.base@11.0.16.1/Native Method)
        - waiting on <0x000000070ff09018> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@11.0.16.1/ReferenceQueue.java:155)
        - waiting to re-lock in wait() <0x000000070ff09018> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@11.0.16.1/ReferenceQueue.java:176)
        at java.lang.ref.Finalizer$FinalizerThread.run(java.base@11.0.16.1/Finalizer.java:170)

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 cpu=0.51ms elapsed=48.02s tid=0x00007fe68684c000 nid=0x3f03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #5 daemon prio=9 os_prio=31 cpu=0.08ms elapsed=48.02s tid=0x00007fe68684f000 nid=0x5603 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #6 daemon prio=9 os_prio=31 cpu=87.45ms elapsed=48.02s tid=0x00007fe687821000 nid=0xa803 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"C1 CompilerThread0" #8 daemon prio=9 os_prio=31 cpu=79.64ms elapsed=48.01s tid=0x00007fe6888da800 nid=0x5903 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Sweeper thread" #9 daemon prio=9 os_prio=31 cpu=0.11ms elapsed=48.00s tid=0x00007fe686850000 nid=0x5c03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Common-Cleaner" #10 daemon prio=8 os_prio=31 cpu=0.19ms elapsed=47.83s tid=0x00007fe686887000 nid=0x5e03 in Object.wait()  [0x00007000052bc000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(java.base@11.0.16.1/Native Method)
        - waiting on <0x000000070ffc00b0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@11.0.16.1/ReferenceQueue.java:155)
        - waiting to re-lock in wait() <0x000000070ffc00b0> (a java.lang.ref.ReferenceQueue$Lock)
        at jdk.internal.ref.CleanerImpl.run(java.base@11.0.16.1/CleanerImpl.java:148)
        at java.lang.Thread.run(java.base@11.0.16.1/Thread.java:834)
        at jdk.internal.misc.InnocuousThread.run(java.base@11.0.16.1/InnocuousThread.java:134)

"JDWP Transport Listener: dt_socket" #11 daemon prio=10 os_prio=31 cpu=4.13ms elapsed=47.52s tid=0x00007fe6868f9000 nid=0x6003 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Event Helper Thread" #12 daemon prio=10 os_prio=31 cpu=7.96ms elapsed=47.52s tid=0x00007fe68897c800 nid=0x6103 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Command Reader" #13 daemon prio=10 os_prio=31 cpu=1.08ms elapsed=47.52s tid=0x00007fe68897d800 nid=0x6303 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-0" #14 prio=5 os_prio=31 cpu=2.96ms elapsed=47.31s tid=0x00007fe687826800 nid=0xa003 waiting for monitor entry  [0x00007000057cb000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.smc.java.ThreadDeadLock$1.run(ThreadDeadLock.java:25)
        - waiting to lock <0x000000070fd23b80> (a java.lang.StringBuilder)
        - locked <0x000000070fd23b48> (a java.lang.StringBuilder)

"Thread-1" #15 prio=5 os_prio=31 cpu=2.11ms elapsed=47.31s tid=0x00007fe68898a800 nid=0x9e03 waiting for monitor entry  [0x00007000058ce000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.smc.java.ThreadDeadLock$2.run(ThreadDeadLock.java:46)
        - waiting to lock <0x000000070fd23b48> (a java.lang.StringBuilder)
        - locked <0x000000070fd23b80> (a java.lang.StringBuilder)
        at java.lang.Thread.run(java.base@11.0.16.1/Thread.java:834)

"DestroyJavaVM" #16 prio=5 os_prio=31 cpu=320.37ms elapsed=47.31s tid=0x00007fe68898d000 nid=0x1703 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" #17 daemon prio=9 os_prio=31 cpu=1.65ms elapsed=0.11s tid=0x00007fe6868fb800 nid=0x9d03 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"VM Thread" os_prio=31 cpu=6.40ms elapsed=48.09s tid=0x00007fe6888d7000 nid=0x4d03 runnable  

"GC Thread#0" os_prio=31 cpu=2.91ms elapsed=48.13s tid=0x00007fe688018800 nid=0x2e03 runnable  

"G1 Main Marker" os_prio=31 cpu=1.87ms elapsed=48.13s tid=0x00007fe688837000 nid=0x5303 runnable  

"G1 Conc#0" os_prio=31 cpu=0.04ms elapsed=48.13s tid=0x00007fe688838000 nid=0x5103 runnable  

"G1 Refine#0" os_prio=31 cpu=2.38ms elapsed=48.12s tid=0x00007fe6888c8800 nid=0x3303 runnable  

"G1 Young RemSet Sampling" os_prio=31 cpu=10.73ms elapsed=48.12s tid=0x00007fe68781d800 nid=0x3403 runnable  
"VM Periodic Task Thread" os_prio=31 cpu=42.95ms elapsed=47.36s tid=0x00007fe68803c000 nid=0x6503 waiting on condition  

JNI global refs: 32, weak refs: 672


Found one Java-level deadlock:
=============================
"Thread-0":
  waiting to lock monitor 0x00007fe687313e00 (object 0x000000070fd23b80, a java.lang.StringBuilder),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x00007fe687313f00 (object 0x000000070fd23b48, a java.lang.StringBuilder),
  which is held by "Thread-0"

Java stack information for the threads listed above:
===================================================
"Thread-0":
        at com.smc.java.ThreadDeadLock$1.run(ThreadDeadLock.java:25)
        - waiting to lock <0x000000070fd23b80> (a java.lang.StringBuilder)
        - locked <0x000000070fd23b48> (a java.lang.StringBuilder)
"Thread-1":
        at com.smc.java.ThreadDeadLock$2.run(ThreadDeadLock.java:46)
        - waiting to lock <0x000000070fd23b48> (a java.lang.StringBuilder)
        - locked <0x000000070fd23b80> (a java.lang.StringBuilder)
        at java.lang.Thread.run(java.base@11.0.16.1/Thread.java:834)

Found 1 deadlock.

```

* option参数：-F：当正常输出的请求不被响应时，强制输出线程堆栈
* option参数：-l：除堆栈外，显示关于锁的附加信息
* option参数：-m：如果调用到本地方法的话，可以显示C/C++堆栈
* option参数：-h：帮助操作

## 8、jcmd：多功能命令行

### 8.1 基本情况

* 在JDK 1.7之后，新增了一个命令行工具jcmd

* 它是一个多功能的工具，可以用来实现前面出了jstat之外所有命令的功能。比如：用它来导出堆、内存使用、查看Java进程、导出线程信息、执行GC、JVM运行时间等

* jcmd拥有jmap的大部分功能，并且在Oracle的官方网站上也推荐使用jcmd命令代jmap命令

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jcmd -help
  Usage: jcmd <pid | main class> <command ...|PerfCounter.print|-f file>
     or: jcmd -l                                                    
     or: jcmd -h                                                    
                                                                    
    command must be a valid jcmd command for the selected jvm.      
    Use the command "help" to see which commands are available.   
    If the pid is 0, commands will be sent to all Java processes.   
    The main class argument will be used to match (either partially 
    or fully) the class used to start Java.                         
    If no options are given, lists Java processes (same as -l).     
                                                                    
    PerfCounter.print display the counters exposed by this process  
    -f  read and execute commands from the file                     
    -l  list JVM processes on the local machine                     
    -? -h --help print this help message
  ```

* jcmd -l：列出所有的JVM进程

* jcmd pid help：针对指定的进程，列出支持的所有命令

  ```shell
  smc@linjianguodeMacBook-Pro JVMDemo % jcmd 10828 help
  10828:
  The following commands are available:
  Compiler.CodeHeap_Analytics
  Compiler.codecache
  Compiler.codelist
  Compiler.directives_add
  Compiler.directives_clear
  Compiler.directives_print
  Compiler.directives_remove
  Compiler.queue
  GC.class_histogram
  GC.class_stats
  GC.finalizer_info
  GC.heap_dump
  GC.heap_info
  GC.run
  GC.run_finalization
  JFR.check
  JFR.configure
  JFR.dump
  JFR.start
  JFR.stop
  JVMTI.agent_load
  JVMTI.data_dump
  ManagementAgent.start
  ManagementAgent.start_local
  ManagementAgent.status
  ManagementAgent.stop
  Thread.print
  VM.check_commercial_features
  VM.class_hierarchy
  VM.classloader_stats
  VM.classloaders
  VM.command_line
  VM.dynlibs
  VM.flags
  VM.info
  VM.log
  VM.metaspace
  VM.native_memory
  VM.print_touched_methods
  VM.set_flag
  VM.stringtable
  VM.symboltable
  VM.system_properties
  VM.systemdictionary
  VM.unlock_commercial_features
  VM.uptime
  VM.version
  help
  ```

* jcmd pid 具体命令：显示指定进程的指令命令的数据

## 9、jstatd：远程主机信息收集

* 之前的指令只涉及到监控本机的Java应用程序，而这些工具中，一些监控工具也支持对远程计算机的监控（如jps、jstat）。为了启用远程监控，则需要配合使用jstatd工具
* 命令jstatd是一个RMI服务端程序，它的作用相当与代理服务器，建立本地计算机与远程监控工具的通信。jstatd服务器本地的Java应用程序信息传递到远程计算机

![image-20231224170202634](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241702124.png)

# 三、JVM监控及诊断工具-GUI篇

## 1、工具概述

* 使用上一章命令行工具或组合能帮您获取目标Java应用性能相关的基础信息，但他们存在下列局限：
  * 无法获取方法级别的分析数据，如方法间的调用关系、各方法的调用次数和调用时间等（这对定位应用性能的瓶颈至关重要）
  * 要求用户登录到目标Java应用所在的宿主机上，使用起来不是很方便
  * 分析数据通过终端输出，结果展示不够直观
* 为此，JDK提供了一些内存泄漏的分析工具，如jconsole，jvisualvm等，用于辅助开发人员定位问题，但是这些工具很多时候并不足以满足快速定位的需求。所以这里我们介绍的工具相对多一些、丰富一些

### 1.1 图形化综合诊断工具

#### 1.1.1 JDK自带的工具

* jconsole：JDK自带的可视化监控工具。查看Java应用程序的运行概况、监控堆信息、永久区（或元空间）使用情况、类加载情况等
  * 位置：jdk/bin/jconsole.exe
* Visual VM:Visual VM是一个工具，它提供了一个可视化界面，用于查看Java虚拟机上运行的基于Java技术的应用程序的详细信息
  * 位置：jdk/bin/jvisualvm.exe
* JMC:Java Mission Control，内置Java Flight Recorder。能够以极低的性能开销收集Java虚拟机的性能数据

#### 1.1.2 第三方工具

* MAT:(Memory Analyzer Tool)是基于Eclipse的内存分析工具，是一个快速、功能丰富的Java heap分析工具，它可以帮助我们查找内存泄漏和减少内存消耗
  * Eclipse的插件和单独下载安装
* Jprofiler：商业软件，需要付费。功能强大
  * 与VisualVM类似
* Arthas：Alibaba开源的Java诊断工具，深受开发者喜爱
* Btrace：Java运行时追踪工具。可以在不停机的情况下，跟踪指定的方法调用、构造函数调用和系统内存等信息

## 2、JConsole

### 2.1 基本概述

* 从Java5开始，在JDK中自带的java监控和管理控制台
* 用于对JVM中内存、线程和类等的监控，是一个基于JMX（java management extensions）的GUI性能监控工具

### 2.2 启动

* jdk/bin目录下，启动jconsole.exe
* 不需要jps命令来查询

![image-20231224172551511](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241725923.png)

### 2.3 三种连接方式

![image-20231224173135793](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241731080.png)

## 3、Visual VM

### 3.1 基本概述

* Visual VM是一个功能强大的多合一故障诊断和性能监控的可视化工具
* 它集成了多个JDK命令行工具，使用Visual VM可用于显示虚拟机进程及进程的配置和环境信息（jps，jinfo），监视应用程序的CPu、GC、堆、方法区及线程的信息（jstat、jstack）等，设置代替JConsole
* 在JDK 6 update7以后，Visual VM便作为JDK的一部分发布（在JDK/bin目录下）。
* 此外，Visual VM也可以作为独立的软件安装。`https://visualvm.github.io/index.html`

### 3.2 插件的安装

* 支持客户端直接安装
* 支持网页下载安装`https://visualvm.github.io/pluginscenters.html`

### 3.3 连接方式

![image-20231224180201921](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241802528.png)

### 3.4 主要功能

* 生成/读取堆内存快照
* 查看JVM参数和系统属性
* 查看运行中的虚拟机进程
* 生成/读取线程快照
* 程序资源的实时监控
* 其他功能
  * JMX代理连接
  * 远程环境监控
  * CPU分析和内存分析

## 4、eclipse MAT

### 4.1 基本概述

* MAT（Memory Analyzer Tool）工具是一款功能强大的Java堆内存分析器。可以用于查找内存泄漏以及查看内存消耗情况
* MAT是基于Eclipse开发的，不仅可以单独使用，还可以嵌入在Eclipse中使用`https://eclipse.dev/mat/`

### 4.2 获取堆dump文件

#### 4.2.1 dump文件内容

* MAT可以分析heap dump文件。在进行内存分析时，只要获得了反映当前设备内存映像的hprof文件，通过MAT打开就可以直观给的看到当前的内存信息
* 一般来说，这些内存信息包含
  * 所有的对象信息，包括对象实例，成员变量、存储与栈中的基本类型值和存储与堆中的其他对象的引用值
  * 所有的类信息，包括classloader、类名称、父类、静态变量等
  * GCRoot到所有的这些对象的引用路径
  * 线程信息，包括线程的调用栈以及此线程的线程局部变量（TLS）

#### 4.2.2 两点说明

* 缺点：
  * MAT不是一个万能工具，它并不能处理所有类型的存储文件。但是比较主流的厂家和格式，例如Sun，HP，SAP所采用的HPROF二进制堆存储文件，以及IBM的PHD堆存储文件等都能被很好的解析
* 说明2：
  * 最吸引人的还是能够快速为开发人员生成内存泄漏报表，方便定位问题和分析问题。虽然MAT有如此强大的功能，但是内存分析也没有简单到一键完成的程度，很多内存问题还是需要我们从MAT展现给我们的信息当中通过经验和直接来判断才能发现

### 4.3 分析堆dump文件

* histogram：展示了各个类的实例数目以及这些实例的shallow heap或Retainedheap的总和

* thread overview

  * 查看系统中的Java线程
  * 查看局部变量的信息

* 获得对象相互引用的关系

  * with outgoing references
  * with incoming references

* 深堆与浅堆

  * shallow heap

    *  是指一个对象所消耗的内存。在32为系统中，一个对象引用会占据4个字节，一个int类型会占据4个字节，long型变量会占据8个字节，每个对象头需要占用8个字节。根据堆快照格式不同，对象的大小可能会向8个字节进行对齐
    * 以String为例：2个int值共占8字节，对线高引用占用4字节，对象头8字节，合计20字节，向8字节对齐，故占24字节

    ![image-20231224191440489](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241914826.png)

    * 这24字节为String对象的浅堆大小。它与String的value实际取值无关，无论字符串疮毒如何，浅堆大小始终是24字节

  * retained heap

    * 保留级（Retained Set）：对象A的保留级指当对象A被垃圾回收后，可以被释放的所有的对象集合（包括对象A本身），即对象A的保留级可以被认为是只能通过对象A被直接或间接访问到的所有对象的集合。通俗地说，就是指仅被对象A所持有的对象的集合
    * 深堆（Retained heap）：深堆是指对象的保留集中所有的对象的浅堆大小之和
    * 注意：浅堆指对象本身占用的内存，不包括其内部引用对象的大小。一个对象的深堆指只能通过该对象访问到的（直接 或间接）所有对象的浅堆之和，即对象被回收后，可以释放的真实空间

  * 补充：对象的实际大小

    ![image-20231224192325041](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241923373.png)

* 支配树（Dominator Tree）

  * 支配树的概念源自图论
  * MAT提供了一个成为支配树（Dominator Tree）的对象图。支配树提现了对象实例件的支配关系。在对象引用图中，所有指向对象B的路径都经过对象A，则认为对象A支配对象B。如果对象A是里对象B最近的支配对象，则认为对象A为对象B的直接支配者。支配树是基于对象间的引用图所建立的，它有以下基本性质：
    * 对象A的子树（所有被对象A支配的对象集合）表示对象A的保留级，即深堆
    * 如果对象A支配对象B，那么对象A的直接支配者也支配对象B
    * 支配树的边与对象引用图的边不直接对应
  * 如下图所示：左图表示对象引用图，右图表示左图所对应的支配树。对象A和B由跟对象直接支配，由于在对象C的路径中，可以经过A，也可以经过B美因茨对象C的直接支配者也是跟对象。对象F与对象D相互引用，因为到对象F的所有路径必然经过对象D，因此，对象D是对象F的直接支配者。而到对象D的所有路径中，必然经过对象C，即时是从对象F到对象D的引用，从根节点出发，也是经过对象C的，所以对象D的直接支配者也是对象对象C

  ![image-20231224194808178](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241948121.png)

### 4.4 tomcat堆溢出分析

## 5、补充1：再谈内存泄露

### 5.1 内存泄漏的理解与分类

#### 5.1.1 何为内存泄漏（memory leak）

![image-20231224195631008](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312241956689.png)

* 可达性分析算法来判断对象是否不再使用的对象，本质都是判断一个对象是否还被引用。那么对于这种情况下，由于代码的实现不同就会出现很多内存泄漏问题（让JVM误以为此对象还在引用中，无法回收，造成内存泄漏）
* 是否还被使用？是
* 是否还被需要？否

#### 5.1.2 内存泄漏的理解

* 严格来说，只有对象不会再被程序用到了，但是GC又不能回收他们的情况，才叫内存泄漏
* 但实际情况很多时候一些不太好的实践（或疏忽）会导致对象的生命周期变得很长甚至导致OOM，也可以叫做宽泛意义上的“内存泄漏”

![image-20231224200057997](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242000568.png)

#### 5.1.3 内存邪路与内存溢出的关系

* 内存泄漏的增多，导致内存溢出

#### 5.1.4 泄漏的分类

* 经常发生：发生内存泄漏的代码会被多次执行，每次执行，泄漏一块内存
* 偶然发生：在某些特定情况下才会发生
* 一次性：发生内存泄漏的方法只会执行一次
* 隐式泄露：一直占着内存不释放，知道执行结束；严格的说这个不算内存泄漏，因为最终释放掉了，但是如果执行时间特别长，也可能导致内存耗尽

### 5.2 内存泄漏的8中情况

#### 5.2.1 静态集合类

* 静态集合类，如HashMap、LinkedList等等。如果这些容器为静态的，那么他们的生命周期与JVM程序一直，则容器中的对象在程序结束之前将不能释放，从而造成内存泄漏。简单而言，长生命周期的兑现该持有短生命周期对象的引用，尽管短生命周期的对象不再使用，但是因为长生命周期的对象持有它的引用而导致不能被回收

![image-20231224223448025](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242234196.png)

#### 5.2.2 单例模式

*单例模式，和静态集合导致内存泄漏的愿意类似，因为单例的静态特性，它的生命周期和JVM的生命周期一样长，所以如果单例对象如果持有外部对象的引用，那么这个外部对象也不会被回收，那么就会造成内存泄漏

#### 5.2.3 内部类持有外部类

* 内部类持有外部类，如果一个外部类的实例对象的方法返回一个内部类的实例对象。
* 这个内部类对象被长期引用了，即使那个外部类实例对象不再被使用，但由于内部类持有外部类的实例对象，这个外部类对象将不会被垃圾回收，这也会造成内存泄漏

#### 5.2.4 各种连接，如数据库连接、网络连接和IO连接等

* 各种连接，如数据库连接、网络连接和IO连接等
* 在对数据库进行操作的过程中，首先需要建立与数据库的连接，当不再使用时，需要调用close方法来释放与数据库的连接。只有连接被关闭后，垃圾回收器才会回收对应的对象
* 否则，如果在访问数据库的过程中，对Connection、Statement或ResultSet不显性地关闭，将会造成大量的对象无法被回收，从而引起内存泄漏

![image-20231224224112902](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242241194.png)

#### 5.2.5 变量不合理的作用域

* 变量不合理的作用域。一般而言，一个变量的定义的作用范围大于其使用范围，很有可能会造成内存泄漏。另一方面，如果没有及时地把对象设置为null，很有可能导致内存泄漏的发生

![image-20231224224721480](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242247573.png)

* 如上面的这个伪代码，通过readFromNet方法把接收的消息保存在变量msg中，然后调用saveDB方法把msg的内容保存到数据库中，此时msg已经就没用了，由于msg的生命周期与对象的生命周期相同，此时msg还不能回收，因此造成了内存泄漏
* 实际上这个msg变量可以放在receiveMsg方法内部，当方法使用完，那么msg的生命周期也就结束，此时就可以回收了。还有一种方法，在使用完msg后，把msg设置为null，这样垃圾回收器也会回收msg的内存空间

#### 5.2.6 改变哈希值

* 改变哈希值，当一个对象被存储进HashSet集合中以后，就不能修改这个对象中的那些参与计算哈希值的字段了。
* 否则，对象修改后的哈希值与最初存储进HashSet集合中时的哈希值就不同了，在这种情况下，即使contains方法使用该对象的当前引用作为的参数HashSet集合中检索对象，也将返回找不到对象的结果，这也会导致无法从HashSet集合中单独删除当前对象，造成内存泄漏
* 这也是String为什么被设置成不可变累心个，我们可以放心地把String存入HashSet，或者把String当做HashMap的key值
* 当我们想把自己定义的类保存到散列表的时候，需要保证对象的hashCode不可变

#### 5.2.7 缓存泄露

* 内存泄漏的另一个常见来源是缓存，一旦你把对象引用放入到缓存中，他就很容易遗忘。比如：之前项目在一次上线的时候，应用启动奇慢知道夯死，这就因为代码中会加载一个表中的数据到缓存（内存）中，测试环境只有几百条数据，但是生产环境有几百万的数据
* 对于这个问题，可以使用WeakHashMap代表缓存，此种map的特点是，当除了自身有对key的引用外，此key没有其他引用那么此map会自动丢弃此值

#### 5.2.8 监听器和回调

* 内存泄漏第三个常见来源是监听器和其他回调，入股客户端在你实现的APi中注册回调，却没有显示取消，那么就会积聚
* 需要确保回调立即被当作垃圾回收的最佳方法是指保存它的弱引用，例如将它们保存成为WeakHashMap中的键

## 6、支持使用OQL语言查询对象信息

* MAT支持一种类似于SQL的查询语言OQL（Object Query Language）。OQL使用类似SQL语法，可以在堆中进行对象的查找和筛选

### 6.1 SELECT子句

![image-20231224232204678](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242322542.png)

### 6.2 FROM子句

![image-20231224232433035](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242324428.png)

### 6.3 WHERE 子句

![image-20231224232614573](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242326990.png)

### 6.4 内置对象与方法

![image-20231224232654411](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312242326078.png)

## 7、JProfiler

### 7.1 基本概述

#### 7.1.1 介绍

* 在运行Java的时候有时候想测试运行时占用内存情况，这时候就需要使用测试工具查看了。在eclipse里面有MAT可以测试，而在IDEA中也有这么一个插件，就是JProfiler
* JProfiler是由ej-technologies公司开发的一款Java应用性能诊断工具，功能强大，但是收费
* `https://www.ej-technologies.com/products/jprofiler/overview.html`

#### 7.1.2 特点

* 使用方便、界面操作友好
* 对被分析的应用影响小
* CPU，Thread，Memory分析功能尤其强大
* 支持对jdbc，nosql，jsp，servlet，socket等进行分析
* 支持多种模式（离线、在线）的分析
* 支持监控本地、远程的JVM
* 跨平台，拥有多种操作系统的安装版本

#### 7.1.3 主要功能

* 方法调用：对方法调用的分析可以帮助了解应用程序正在做什么，并找到提高其性能的方法
* 内存分配：通过分析堆上对象、引用链和垃圾收集能帮您修复内存泄漏问题，优化内存使用
* 线程和锁：Jprofiler提供多种针对线程和锁的分析视图助你发现多线程问题
* 高级子系统：许多性能问题都发生在更高的语义级别上。例如，对于JDBC调用，您可能希望找出执行最慢的SQL语句。JProfiler支持对这些子系统进行集成分析

### 7.2 具体使用

#### 7.2.1 数据采集方式

* JProfiler数据采集方式分为两种：Sampling（样本采集）和Instrumentation（重构模式）
  * Instrumentation：这是Jprofiler全功能模式。在class加载之前，JProfiler把相关功能代码写入到需要分析的class的bytecode中，对正在运行的jvm有一定影响
    * 优点：功能强大。在此设置中，调用堆栈信息是准确的
    * 缺点：若要分析的class较多，则对应用的性能影响较大，CPU开销可能很高（取决于Filter的控制）。因此使用此模式一般配合Filter使用，只对特定的类或包进行分析
  * Sampling：类似于样本统计，每隔一段时间（5ms）将每个线程中方法栈中的信息统计出来。
    * 优点：对CPU的开销非常低，对应用影响较小（即使你不配置任何Filter）
    * 缺点：一些数据/特性不能提供（例如：方法的调用次数、执行时间）
* 注：JProfiler本身没有支出数据的采集类型，这里的采集类型是针对方法调用的采集类型。因为JProfiler的绝大多数核心功能都依赖方法调用采集的数据，所以可以直接认为是JProfiler的数据采集类型。

#### 7.2.2 遥感检测Telemetries

#### 7.2.3 内存视图Live Memory

* Live memory 内存剖析：class/class instance的相关信息。例如对象的个数，大小，对象创建方法执行栈，对象创建热点

  * 所有对象All Objects

    * 显示所有加载的类的列表和对上分配的实例数。只有Java 1.5（JVMTI）才会显示次视图

    ![image-20231225000447906](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312250018397.png)

  * 记录对象 Record Objects
    * 查看特定时间段对象的分配，并记录分配的调用堆栈

* 分析：内存中对象的情况

  * 频繁创建的Java对象：死循环、循环次数过多
  * 存在大的对象：读取文件时，byte[]应该边读边写。-->如果长时间不写出的话，导致byte[]过大
  * 存在内存泄漏

#### 7.2.4 堆遍历 heap walker

#### 7.2.5 cpu视图 cpu views

* Jprofiler提供不同的方法来记录访问树以优化性能和细节。现成或者线程组以及线程状况可以被所有的视图选择。所有的视图都可以聚集到方法、类、包或J2EE组件等不同层上
* 访问树 Call Tree：显示一个鸡肋的自顶向下的树，树中包含所有在JVM中已记录的访问队列。JDBC，JMS和JNDI服务请求都被注释在请求中。请求树可以根据Servlet和JSP对URL的不同需要进行拆分

* 热点 Hot Spots：显示消耗时间最多的方法的列表，对每个热点都能够显示回溯树。该热点可以按照方法请求，JDBC、JMS和JNDI服务请求以及按照URL请求来进行计算
* 访问图：Call Graph：显示一个从已选方法、类、包或J2EE组件开始的访问队列的图
* 方法统计 Method Statistis：显示一段时间内记录的方法的调用时间细节

#### 7.2.6 线程视图 threads

* Jprofiler通过对线程历史的监控判断器运行状态，并监控是否有现成阻塞产生，还能将一个线程所管理的方法以树状形式呈现。对线程剖析
* 线程历史 Thread History：显示一个与线程活动和线程状态在一起的活动时间表
* 线程监控 Thread Monitor：显示一个列表，包括所有的活动线程以及他们目前的活动状况
* 现成转储 Thread Dumps：显示所有现成的堆栈跟踪
* 线程分析主要关系三个方面：
  * web容器的线程最大数。比如：Tomcat的线程容量应该略大于最大并发数
  * 线程阻塞
  * 线程死锁

#### 7.2.7 监视器&锁 Monitors&locks

## 8、Arthas（阿尔萨斯）

### 8.1 基本概述

#### 8.1.1 概述

* 在线排查问题，无需重启；动态跟踪代码；实时监控JVM状态
* 支持JDK 6+，支持Linux/Mac/Windows，采用命令行交互模式，同事提供丰富的Tab自动补全功能，进一步方便进行问题的定位和诊断

![image-20231225005223052](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312250052808.png)

#### 8.1.2 基于哪些工具开发而来

![image-20231225005335758](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312250053129.png)

* 官网：`https://arthas.aliyun.com/`
* 网页访问：`http://localhost:8563/`
* 日志文件位置：`~/logs/arthas/arthas.log`
* 帮助：`java -jar arthas-boot.jar -h`

### 8.2 相关诊断指令

#### 8.2.1 基础指令

#### 8.2.2 jvm相关

#### 8.2.3 火焰图profiler

## 9、Java Mission Controller

* 路径：`JDK/bin/`

## 10 其他工具

### 10.1 火焰图

* 在追求极致性能的场景下，了解你的程序运行过程中的CPU在干什么非常重要，火焰图是一种非常直观的展示cpu在程序整个生命周期过程中时间分配的工具
* 火焰图对于现代的程序员不应该陌生，这个工具可以飞铲更直观的显示出调用栈中的CPU消耗瓶颈
* 网上关于java火焰图讲解大部分来自于Brendan Gregg的博客：`https://www.brendangregg.com/flamegraphs.html`

## 10.2 Tprofile

![image-20231225235215209](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312252352896.png)

* 下载地址：`https://github.com/alibaba/Tprofiler`

### 10.3 Btrace

### 10.4 Yourkit

# 四、JVM运行时参数

## 1、JVM参数选项类型

### 1.1 类型一：标准参数选项

* 特点：

  * 比较稳定，后续版本基本不会变化
  * 以-开头

* 各种选项

* 补充内容：-server 与client

  ![image-20231226000018561](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260000833.png)

### 1.2 -X参数选项

* 特点：

  * 非标准化参数
  * 功能还是比较稳定的。但官方说后续版本可能会变更
  * 以-X开头

* 各种选项：运行java -X命令可以看到所有的X选项

* JVM的JIT编译模式相关的选项

   ![image-20231226000304071](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260003169.png)

* 特别地:-Xmx -Xms -Xss属于XX参数

  * -Xms\<size\>：设置初始Java堆大小，等价于-XX:InitialHeapSize
  * -Xmx\<size\>：设置最大Java堆大小，等价于-XX:MaxHeapSize
  * -Xss\<size\>：设置Java线程堆栈大小，等价于-XX:ThreadStackSize

### 1.3 -XX参数选项

* 特点
  * 非准化参数
  * 使用的最多的参数类型
  * 这类选项属于实验性，不稳定
  * 以-XX开头
* 作用：用户开发和调试JVM
* 分类
  * Boolean类型
    * -XX:+\<option\>表示启用option属性
    * -XX:-\<option\>表示关闭option属性
  * 非boolean类型格式（key-value类型）
    * 子类型1：数值型格式-XX:\<option\>=\<number\>
    * 子类型2：数值型格式-XX:\<option\>=\<String\>
* 特别地：-XX:PrintFlagsFinal
  * 输出所有参数的名称和默认值
  * 默认不包括Diagnostic和Experimental的参数
  * 可以配合-XX:+UnlockDiagnosticVMOptions和-XX:UnlockExperimentalVMOptions使用

## 2、添加JVM参数选项

## 3、常用的JVM参数选项

### 3.1 打印设置的XX选项及值

* `-XX:+PrintCommandLineFlags`：可以让在程序运行前打印出用户手动设置或者JVM自动设置的XX选项
* `-XX:+PrintFlagsInitial`：表示打印出所有XX选项的默认值
* `-XX:+PrintFlagsFinal`：表示打印出XX选项在运行程序时生效的值
* `-XX:+PrintVMOptions`：打印JVM参数

### 3.2 堆、栈、方法区等内存大小设置

#### 3.2.1 栈

* -Xss128k：设置每个线程的栈大小为128k

#### 3.2.2 堆内存

* -Xms3550m：等价于-XX:InitialHeapSize，设置JVM初始堆内存为3550M
* -Xmx3550m：等价于-XX:MaxHeapSize，设置JVM最大堆内存为3550M
* -Xmn2g
  * 设置年轻代大小为2G
  * 官方推荐为整个堆大小的3/8
* -XX:NewSize=1024m：设置年轻代初始值为1024M
* -XX:MaxNewSize=1024m：设置年轻代最大值为1024M
* -XX:SurvivorRatio=8：设置年轻代中Eden区与一个Survivor区的比值，默认为8
* -XX+UseAdaptiveSizePolicy：自动选择各区大小比例
* -XX:NewRatio=4：设置老年代与年轻代（包括1个Eden和2个Survivor区）的比值
* -XX:PretenureSizeThreadshold=1024
  * 设置让大于次阈值的对象直接分配在老年代，单位为字节
  * 支队Serial、ParNew收集器有效
* -XX:MaxTenuringThreshold=15
  * 默认值为15
  * 新生代每次MinorGC后，还存活的对象年龄+1，当对象的年龄大于设置的这个值时就进入老年代
* -XX:PrintTenuringDistribution：让JVM在每次MinorGC后打印出当前使用的Survivor中对象的年龄分布
* -XX:TargetSurvivorRatio：表示MinorGC结束后Survivor区域中占用空间的期望比例

#### 3.2.3 方法区

* 永久代
  * -XX:PermSize=156m：设置永久代初始值为256M
  * -XX:MaxPermSize=256m：设置永久代最大值为256M

* 元空间
  * -XX:MetaspaceSize：初始空间大小
  * -XX:MaxMetaspaceSize：最大空间，默认没有限制
  * -XX:+UseCompressedOops：压缩对象指针
  * -XX:+UseCompressedClassPointers：压缩类指针
  * -XX:CompressedClassSpaceSize：设置Klass Metaspace的大小，默认1G

#### 3.2.4 直接内存：

* -XX:MaxDirecMemorySize：指定DirectMemory容量，若未指定，则默认与Java堆最大值一样

### 3.3 OutofMemory相关的选项

* -XX:+HeapDumpOnOutOfMemoryError：表示在内存出现OOM的时候，把Heap转存的文件以便后续分析

* -XX:+HeapDumpBeforeFullGC：表示在出现FullGC之前，生成Heap转储文件

* -XX:HeapDumpPath=\<path\>：指定转存文件的存储路径

* -XX:OnOutOfMemoryError：指定一个可行性程序或者脚本的路径，当发生OOM的时候，去执行这个脚本

  ![image-20231226010114576](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260101822.png)

### 3.4 垃圾收集器相关选项

![image-20231226010414513](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260104662.png)

![image-20231226010440107](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260104382.png)

#### 3.4.1 查看默认的垃圾收集器

* -XX:+PrintCommandLineFlags：查看命令行相关参数（包含使用的垃圾回收器）
* 使用命令行指令：jinfo -flag 相关垃圾回收器参数 进程ID

#### 3.4.2 Serial回收器

* Serial收集器作为HotSpot中Client模式下的默认新生代垃圾收集器。Serial Old是运行在Client模式下默认的老年代的垃圾回收器
* -XX:+UseSerialGC
  * 指定年轻代和老年代都使用串行收集器，等价于新生代用Serial GC，且老年代用Serial Old GC。可以获得最高的单线程收集效率

#### 3.4.3 ParNew回收器

![image-20231226011209762](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260112035.png)



#### 3.4.4 Parallel回收器

![image-20231226011253662](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260112929.png)

#### 3.4.5 CMS回收器

![image-20231226011510487](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260115837.png)

![image-20231226011722423](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260117680.png)

![image-20231226011740562](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260117818.png)

#### 3.4.6 G1回收器

![image-20231226011818501](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260118835.png)

![image-20231226011936763](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260119053.png)

#### 3.4.7 怎么选择垃圾回收器

![image-20231226012029091](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260120653.png)

### 3.5、GC日志相关参数

#### 3.5.1 常用参数

* -verbose:gc：输出gc日志信息，默认输出到标准输出
* -XX:+PrintGC：等同于-verbose:gc，表示打开简化的GC日志
* -XX:+PrintGCDetails：在发生垃圾回收时打印内存回收详细的日志，并在进程退出时输出当前内存各区域分配情况
* -XX:+PrintGCTimeStamps：输出GC发生时的时间戳，不能单独使用，一般于PrintGCDetails配合使用
* -XX:+PrintGCDateStamps：输出GC发生时的时间戳（以日期的形式，如2013-0504T21:59.234+0800）
* -XX:+PrintHeapAtGC：每一次GC前和GC后，都打印堆信息
* -Xloggc:路径：将日志输出到路径文件下

#### 3.5.2 其他参数

![image-20231226013244545](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260132881.png)

### 3.6 其他参数

![image-20231226013452411](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260134525.png)

## 4、通过Java代码获取JVM参数

![image-20231226013635334](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260136421.png)

![image-20231226013604506](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260136625.png)

# 五、分析GC日志

## 1、GC日志参数

## 2、GC日志格式

### 2.1 复习：GC分类

![image-20231226014011641](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260140926.png)

![image-20231226014424538](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260144276.png)

### 2.2 日志分类

#### 2.2.1 MinorGC

![image-20231226014612635](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260146022.png)

#### 2.2.2 FullGC

![image-20231226014542569](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260145959.png)

### 2.3 GC日志结构剖析

* GC时间

  ![image-20231226014912979](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/202312260149266.png)

## 3、GC日志分析工具

### 3.1 GCEasy

`https://gceasy.io/`

### 3.2 GCViewer

