### 一、为什么学习Maven

#### 1、Maven作为依赖管理工具

#### 2、Maven作为构建管理工具

### 二、什么是maven

#### 1、构建

* Java项目开发过程中，构建指的是使用（原材料生产厂坪的过程）
* 构建过程包含的主要的环节
  * 清理：删除上一次构建的结果，为下一次构建做好准备
  * 编译：Java远程源程序编译成#.class字节码文件
  * 测试：运行提前准备好的测试程序
  * 报告：针对刚才测试的结果生成一个全面的信息
  * 打包
    * Java工程：Jar包
    * Web工程：war包
  * 安装：把一个Maven工程经过打包操作生成的jar包或war包存入Maven仓库
  * 部署
    * 部署jar包：把一个jar包部署成nexus私服服务器上
    * 部署war包：借助相关Maven插件（例如cargo），将war包部署到Tomcat服务器上

#### 2、依赖

* 如果A工程里面用到了B工程的类、接口、配置文件等等这样的资源，我们就说A依赖B

#### 3、Maven的工程机制![](/Users/smc/Desktop/smc/工具学习/maven/resources/截屏2022-04-30 20.46.40.png)

### 三、Maven核心程序解压和配置

* 指定本地仓库：localRepository
* 配置镜像仓库
* 配置Maven工程的基础JDK版本
  * 如果按照默认配置运行，Java工程使用的默认JDK版本是1.5，而我们熟悉和常用的是JDK1.8版本，修改配置的方式是：将profile标签整个复制到settings.xml文件的profiles标签内

### 四、Maven入门、原理与实战

#### 1、根据坐标创建Maven工程

* Maven核心概念：坐标
  * 向量说明：使用三个（向量）在（Maven的仓库）中唯一的定位到一个（jar）包
    * groupId：公司货组织的id
    * artifactId：一个项目或者项目中的一个模块的id
    * version：版本号
  * 三个向量的取值方式
    * groupId：公司或组织域名的倒序，通常也会加上项目名称
      * 例如：com.atguigu.maven
    * artifactId：模块的名称，将来作为Maven工程的工程名
    * version：模块的版本号，根据自己的需要设定
      * 例如：SNAPSHOT表示快照版本，正在迭代过程中，不稳定的版本
      * 例如：RELEASE表示正式版本
  * 坐标和仓库中jar包存储路径之间的对应关系.  ` groupId\version\artifactId\`
* 实验操作
  * 创建目录作为后面操作的工作空间
    * 运行**mvn archetype:generate**
  * pom介绍
    * scope：配置当前依赖的范围
* Maven的核心概念：POM
  * 含义：POM，Project Object Model，项目对象模型，和POM类似的是：DOM（Document Object Model），文档对象模型，它们都是模型化思想的具体体现
  * 模型化思想：POM 表示将工程抽象为一个模型，在用程序中的对象来描述这个模型，这样我们就可以用程序来管理项目了，我们在开发过程中，最基本的做法就是将显示生活中的事抽象为模型，然后分装模型相关的数据作为一个对象，这样就可以在程序中计算与现实事物相关的数据
    * 对应的配置文件：POM理念集中体现在Maven工程根目录下pom.xml配置文件中，所以这个pom.xml配置文件就是Maven工程的核心配置文件。启示学习Maven就是学这个文件怎么配置，各个配置有什么用。
  * Maven核心概念：约定的目录结构
    * 各个目录的作用

#### 2、在Maven工程中编写代码

* 执行Maven的构建命令
  * 要求：运行Maven中和构建相关的命令时，必须进入到pom.xml所在的目录。如果没有在pom.xml所在的目录运行Maven的构建命令，那么会看到下面的错误信息
* 清理操作：
  * mvn clean
  * 删除target目录
* 编译操作
  * 主程序编译：mvn compile
  * 测试程序编译：mvn test-compile
  * 主题程序编译结果存放的目录：target/classes
  * 测试程序编译结果存放的目录：target/test-classes
* 测试操作
  * mvn test
  * 测试的报告存放的目录：target/surefire-reports
* 打包操作
  * mvn package
  * 打包的结果-jar包，存放的目录：target
* 安装操作
  * mvn install
  * 安装的效果就是将本地构建过程中生成的jar包存放Maven本地仓库，这个jar包在Maven仓库中的路径时根据它的坐标生成的
  * 另外，安装操作还会将pom.xml文件转换为XXX.pom文件一起存入本地仓库。所以我们在Maven的本地仓库中想看一个jar包原始的pom.xml文件时，查看对应XXX.pom文件即可，它们是名字发生了改变，本质上是同一个文件

#### 3、使用Maven生成web工程

#### 4、让web工程依赖Java工程

* 观念：明确一个意识：从来只有Web工程以来Java工程，没有反过来Java工程以来Web工程。本质上来说，Web工程以来的Java工程其实就是Web工程里导入的jar包。最终Java工程会生成jar包，放在Web工程的WEB-INF/lib目录下
* mvn dependency:tree:展示依赖结构
* mvn dependency:list：展示依赖列表

#### 5、测试依赖的范围

* 依赖范围
  * 标签的位置：dependecies/dependency/scope
  * 标签可选值：compile、test、provided、system、runtime、import
* compile和test、provided对比

|          | main目录（空间） | test目录（空间） | 开发过程（时间） | 部署到服务器（时间） |
| -------- | ---------------- | ---------------- | ---------------- | -------------------- |
| Compile  | 有效             | 有效             | 有效             | 有效                 |
| test     | 无效             | 有效             | 有效             | 无效                 |
| provided | 有效             | 有效             | 有效             | 无效                 |

* 结论
  * compile：通常使用的第三方框架的jar包这样的项目实际运行时真正要用到的jar包都是以compile范围进行依赖的。比如SSM框架所需jar包
  * test：测试过程中使用的jar包，以test范围依赖进来。比如junit。
  * provided：在开发过程中需要用到的“服务器上的jar包”通常以provided范围依赖进来。比如servlet-api、jsp-api。二这个范围的jar包之所以不参与部署、不放进war包，就是避免和服务器上已有的同类jar包产生冲突，同时减轻服务器的负担。

#### 6、测试依赖的传递性

* 概念
  * A依赖B，B依赖C，那么在A没有配置对C的依赖的情况下，A里面能不能直接使用C？
* 传递的原则
  * 在A依赖B，B依赖C的前提下，C是否能够传递到A，取决于B依赖C时使用的依赖范围
    * B依赖C时使用compile范围：可以传递
    * B依赖C时使用test或provided范围：不能传递，所以需要这样的jar包时，就必须在需要的地方明确配置依赖才可以

#### 7、测试依赖的排除

* 概念

  * 当A依赖B，B依赖C而且C可以传递到A的时候，A不想要C，需要在A里面把C排除掉。而往往这种情况都是为了避免jar包之间的冲突
  * 所以依赖的排除其实就是阻止某些jar包的传递。因为这样的jar包传递过来会和其他jar包冲突

* 配置方式

  ```xml
  <!--配置依赖的排除-->
  <exclusions>
    <!--配置具体排除信息，让某个依赖不要传递到当前工程-->
  				<exclusion>
            <!--不需要指定version-->
  					<groupId></groupId>
  					<artifactId></artifactId>
  				</exclusion>
  			</exclusions>
  ```

#### 8、继承

* 概念：

  * Maven工程之间，A工程继承B工程
    * B工程：父工程
    * A工程：子工程
  * 本质上是A工程的pom.xml中配置集成了B工程中pom.xml的配置

* 作用

  * 在父工程中同一管理项目中的依赖信息，具体来说是管理依赖信息的版本
  * 它的背景是：
    * 对一个比较大型的项目进行模块拆分。
    * 每一个module都需要配置自己的依赖信息
  * 它背后的需求
    * 在每一个module中维护各自的依赖很容易发生出入，不易统一管理
    * 使用同一框架内的不同jar包，它们应该是同一个版本，所以整个项目中使用的框架版本需要统一
    * 使用框架时所需要的jar包组合（或者说依赖信息组合）需要经过长期摸索和反复调试，最终确定一个可用组合。这个耗费很大精力总结出来的方案不应该在新的项目中重新摸索

* 操作

  * 创建父工程

    * 工程创建好之后，要修改它的打包方式

    > 当前工程为父工程，它要去管理子工程，所以打包方式必须时pom

    * 只有打包方式为pom的Maven工程能够管理其他Maven工程，打包方式为pom的maven工程不写业务代码，它是专门管理其他Maven工程的工程。

  * 创建模块工程

    * 模块工程类似于IDEA中的module，所以需要进入父工程的根目录，然后运行` mvn archetype:generate`命令来创建模块工程

  * 查看被添加新内容的父工程的pom.xml

  ```xml
  <!--聚合的配置-->
  <modules>
          <module>litemall-core</module>
          <module>litemall-db</module>
          <module>litemall-wx-api</module>
          <module>litemall-admin-api</module>
          <module>litemall-all</module>
          <module>litemall-all-war</module>
      </modules>
  ```

  * 查看子工程pom.xml配置父工程

  ```xml
  
  <parent>
          <groupId>org.linlinjava</groupId>
          <artifactId>litemall</artifactId>
          <version>0.1.0</version>
      </parent>
  <!--子工程的groupId和version如果和父工程一样，则可以省略-->
  <artifactId>litemall-admin-api</artifactId>
  ```

* 在父工程中统一管理依赖

  ```xml
  <dependencyManagement>
          <dependencies>
              <dependency>
                  <groupId></groupId>
                  <artifactId></artifactId>
                  <version></version>
              </dependency>
          </dependencies>
      </dependencyManagement>
  ```

  

  * 即使在父工程中配置了对依赖的管理，子工程需要使用具体哪一个依赖还是要明确配置
  * 对于已经在父工程进行管理的依赖，子工程中引用时可以可以补血version，若不省略，且和父工程version不一样，覆盖父工程管理的版本，绝大部分情况还是遵从父工程的依赖

* 配置自定义的属性标签

  * 创建自定义属性标签
  * 引用方式${标签名}

  ```xml
  <properties>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <java.version>1.8</java.version>
          <maven.test.skip>true</maven.test.skip>
      </properties>
  
  <dependency>
                  <groupId>org.linlinjava</groupId>
                  <artifactId>litemall-core</artifactId>
    <!--引用自定义属性标签-->
                  <version>${project.version}</version>
              </dependency>
  ```

* 实践的意义

  ![](/Users/smc/Desktop/smc/工具学习/maven/resources/截屏2022-05-01 10.49.34.png)

  * 编写一套符合要求、开发各种功能都能正常工作的依赖组合并不容易。如果公司里一斤刚有人总结了成熟的组合方案，那么再开发新项目时，如果不实用原有的积累，而是重新摸索，会浪费大量的时间。为了提高效率，我们可以使用工程继承的机制，让成熟的依赖组合方案能够保留下来
  * 如上图所示，公司级的父工程中管理的就是成熟的依赖组合方案，各个新项目、子系统各取所需。

#### 9、聚合

* 含义：部分组成整体
* 好处
  * 一键执行Maven命令：很多构建命令都可以在“总工程”中一键执行
  * 以mvn install命令为例：Maven要求有父工程时先安装父工程；有依赖的工程时，先安装被依赖的工程。我们自己考虑这些规则会很麻烦。但是还工程聚合之后，在总工程执行mvn install可以一键完成安装，而且会自动按照正确的顺序执行
  * 配置聚合之后，各个模块工程会在总工程中展示一个列表，让项目中的各个模块一目了然。
  * 入A依赖B，B依赖C，C依赖D，则顺序为` A-D->C->B`
  * 依赖循环问题
    * 如果A工程依赖B工程，B工程以来C工程，C工程又反过来以来A工程，那么在执行构建操作时会报错循环引用

### 五、IDEA环境

* 当有子工程时会自动配置package
* 可执行maven命令![](/Users/smc/Desktop/smc/工具学习/maven/resources/截屏2022-05-01 11.18.36.png)

> \# -D 表示后面要附加的命令参数，字母D和后面的参数是紧挨着的，中间没有任何其他字符
>
> \# -Dmaven.test.skip=true表示在执行命令的过程中跳过测试
>
> mvn clean install -Dmaven.test.skip=true

### 六、其他核心概念

#### 1、生命周期

* 作用：
  * 为了让构建过程自动化程度，Maven设定了三个生命周期，生命周期中的每一个环节对应构建过程中的一个操作
  * deploy将最终的包复制到远程的仓库

#### 2、插件和目标

* 插件
  * Mven的核心程序仅仅负责宏观调度，不做具体工作。具体工作都是由Maven插件完成的。例如：编译就是由maven-complier-plugin-3.1.jar来自执行
* 目标
  * 一个插件可以对应多个目标，而每一个目标都和生命周期中的某一个环节对应
  * Default生命周期中有compile和test-compile两个和编译相关的环节，这两个环节对应gcompile和test-compile两个目标，而这两个目标都是由maven-compile-plugin-.jar插件来执行的。

#### 3、仓库

* 本地仓库：在当前电脑上，为电脑上所有Maven工程服务
* 远程仓库：需要联网
  * 局域网：我们自己搭建的Maven私服，例如使用Nexus技术
  * Internet
    * 中央仓库
    * 镜像仓库内容和中央仓库保持一致，但是能够分担中央仓库的负载，同时让用户能够就近访问提高下载速度，例如：Nexus aliyun
  * 建议：不要中央仓库和阿里云镜像混用，否则jar包来源不纯，彼此冲突

### 七、案例

#### 1、创建工程引入以来

* 架构
  * 概念：就是项目的**结构**，只是因为架构是一个更大的词，通常用来形容比较大规模事物的结构
  * 单一架构：单一架构也叫**all-in-one**结构，就是所有代码、配置文件、各种资源都在同一个工程
    * 一个项目包含一个工程
    * 导出一个war包
    * 放在一个Tomcat上运行

#### 2、引入依赖

* 怎么选择依赖
  * 确定技术选型：确定我们项目中要使用哪些技术
    * 确定技术选型、组件依赖列表、项目划分模块。。。等等这些操作其实都属于架构设计的范畴
      * 项目本身所属行业的基本特点
      * 项目具体的功能需求
      * 项目预计访问压力程度
      * 项目预计将来需要扩展的功能
      * 设计项目总体的体系结构
  * 到网站搜索具体的依赖信息
  * 确定这个技术使用哪个版本的依赖
    * 考虑因素1:看是否有别的技术要求这里必须用某一个版本
    * 考虑因素2:如果没有硬性要求，使用最高版本或使用下载高版本。
  * 在实际使用中检验所有依赖信息是否都正常可用（把一套可用的保存下来）

#### 3、工程

* ThreadLocal使用

  * 具体三个主要的方法

  | 方法名       | 功能                       |
  | ------------ | -------------------------- |
  | set(T value) | 将数据绑定到当前线程       |
  | get()        | 从当前线程获取已绑定的数据 |
  | remove()     | 将数据从当前线程移除       |

  

* PortalServlet
* 登录流程图![](/Users/smc/Desktop/smc/工具学习/maven/resources/截屏2022-05-01 15.59.12.png)

* 指定打包名称

> Build/finalName

### 八、SSM整合

* 配置文件为什么要放在Web工程里面
  * Web工程将来生辰war包
  * war包直接部署到tomcat运行
  * Tomcat从war包查找配置文件
  * 如果不是把配置文件放在Web工程，而是放在Java工程，那就等于将配置文件放在了war包内的jar中
  * 配置文件在jar包中读取相对困难