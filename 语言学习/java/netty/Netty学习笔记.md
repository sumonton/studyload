# 一、Netty的介绍和应用场景

## 1、介绍

* Netty是由JBOSS提供一个Java开源框架，现为Github上的独立项目
* Netty是一个异步的、事件驱动的网络应用框架，用以快速开发高性能、高可用靠性的网络IO程序
* Netty主要针对在TCP写一下，想象Clients短的高并发应用，或者Peer-toPeer场景下的大量数据持续输出的应用
* Netty本质是一个NIO框架，适用于服务器通讯相关的多种应用场景
* 要透彻理解Netty，需要先学习NIO，这样我们才能阅读Netty的源码

![image-20220901232507762](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220901232507762.png)

## 2、Netty的应用场景

### 2.1 互联网行业

* 在分布式的系统中，各个节点之间需要远程服务调用，高性能的RPC框架必不可少，Netty作为异步高性能的通信框架，往往作为基础通信组件被这些RPC框架使用

### 2.2 游戏行业

* 网络游戏
* Netty作为高性能的基础通信组件，提高了TCP/UDP和HTTP协议栈，方便定制和开发私有协议栈，账号登录服务器
* 地图服务器之间可以方便的通过Netty进行高性能的通信

### 2.3 大数据领域

* 经典的hadoop的高性能通信和序列化组件（AVRO实现数据文件共享）的RPC框架，默认采用Netty进行跨界点通信
* 它的Netty Service基于Netty框架二次封装实现

## 3、学习参考资料

* << Netty in ACTION >>

# 二、BIO编程

## 1、I/O模型

### 1.1 基本说明

* I/O模型简单的理解：就是用什么样的通道进行数据的发送和接收，很大程度上解决了程序通信的性能
* Java共支持3种网络编程模型/IO模型：BIO、NIO、AIO
* JAVA BIO：同步并阻塞（**传统阻塞型**），服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销

* JAVA NIO:**同步非阻塞**，服务器实现模式为一个线程处理多个请求（连接），即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求就进行处理

  ![image-20220901235448196](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220901235448196.png)

* Java AIO(NIO.2):异步非阻塞，AIO引入异步通道的概念，采用了Proator模式，简化了程序编写，有效的请求才启动线程，它的特点是先由操作系统完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间较长的应用。

### 1.2 BIO、NIO、AIO使用场景分析

* BIO方式适用于连接数目比较少且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，但程序简单易理解
* NIO方式适用于连接数目多且连接比较短（轻操作），比如聊天服务器，弹幕系统，服务器间通讯等。编程比较复杂，JDK1.4开始支持
* AIO方式适用于连接数目多且连接比较长（重操作）的架构，比如相册服务器，充分调用OS参与并发操作，编程比较复杂，JDK7开始支持

## 2、BIO介绍说明

### 2.1 基本介绍

* 可以通过线程池机制改善一个连接一个线程的问题

### 2.2 BIO编程简单流程

* 服务器启动一个ServerSocket
* 客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户建立一个线程与之通讯
* 可短发出请求后，先咨询服务器是否有线程相应，如果没有则会等待，或者被拒绝
* 如果有响应，客户端线程会等待请求结束后，在继续执行

### 2.3 实例

* 实例说明
  * 使用BIO模型编写一个服务器端，监听6666端口，当有客户端连接时，就启动一个线程与通讯
  * 要求使用线程池机制改善，可以连接多个客户端
  * 服务器端可以接收客户端发送的数据（telnet方式即可）
  
  ```java
  package com.smc.bio;
  
  import java.io.IOException;
  import java.io.InputStream;
  import java.net.ServerSocket;
  import java.net.Socket;
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  /**
   * @Date 2022/9/2
   * @Author smc
   * @Description:
   */
  public class BioServer {
      public static void main(String[] args) throws IOException {
          //线程池机制
          /**
           * 思路
           *  1、创建一个线程池
           *  2、如果有客户端连接，就创建一个线程，与之通讯（单独写一个方法）
           */
  
          ExecutorService executorService = Executors.newCachedThreadPool();
  
          //创建一个ServerSocket
          ServerSocket serverSocket = new ServerSocket(6666);
  
          System.out.println("服务器启动了");
          while (true){
              System.out.println("线程信息 ID ="+Thread.currentThread().getId()+
                      "名字="+Thread.currentThread().getName());
              //监听
              System.out.println("等待连接。。。");
              Socket accept = serverSocket.accept();
              System.out.println("连接到一个客户端");
              //就创建一个线程，与之通信
              executorService.execute(new Runnable() {
                  @Override
                  public void run() {
                      //可以和客户端通讯
                      handler(accept);
                      
                  }
              });
  //            accept.getInputStream().read();
          }
  
      }
      
      //编写一个handler方法，和客户端通讯
      public static void handler(Socket socket) {
          byte[] bytes = new byte[1024];
          //通过socket，获取输入流
          try {
  
              InputStream inputStream = socket.getInputStream();
              //循环读取客户端发送的数据
              while (true){
                  System.out.println("线程信息 ID ="+Thread.currentThread().getId()+
                          "名字="+Thread.currentThread().getName());
                  System.out.println("read..");
                  int read = inputStream.read(bytes);
                  if(read!=-1){
                      System.out.println(new String(bytes,0,read));
                  }else{
                      break;
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }finally {
              System.out.println("关闭和client的连接");
              try {
                  socket.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  }
  
  ```

### 2.4 Java BIO问题分析

* 每个请求都需要创建独立的线程，与对应的客户端进行数据Read，业务处理，数据write
* 当并发数较大时，需要创建大量线程来处理连接，系统资源占用较大
* 连接建立后，如果当前线程暂时没有数据可读，则线程就阻塞在Read操作上，造成线程资源浪费

# 三、NIO编程

## 1、基本介绍

* Java-NIO全称Java Non-blocking IO，是指JDK提供的新API。从JDK1.4开始，Java提供了一系列改进的输入/输出的新特性，被统称为NIO即（new IO），是同步非阻塞的
* NIO相关类都被放在java.nio包及子包下，并且对原java.io包重的很多泪进行改写
* NIO有三大核心部分：**Channel(通道)**，**Buffer（缓冲区）**，**Selector（选择器）**
* NIO是面向**缓冲区，或者面向块**编程的，数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动，这就增加了处理过程的灵活性，使用它可以提供非阻塞式的高伸缩性网络

![image-20220905225814089](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220905225814089.png)

* Java NIO的非阻塞模式，使一个线程从某通道发送请求或者读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取，而不是保持线程阻塞，所以直至数据变得可以读取之前，该线程可以继续做其他的事情。非阻塞写也是如此，一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情
* 通俗理解：NIO是可以做到用一个线程来处理多个操作的。假设有10000个请求过来，根据实际情况，可以分配50个或者100个线程来处理。不像之前的阻塞IO那样，非得分配10000个
* HTTP2.0 使用多路复用的技术，做到同一个连接并发处理多个请求，并且并发请求的数量比HTTP1.1大了好几个数量级。

### 1.1 NIO三大核心组件的关系

* 每个channel对应一个BUffer
* 一个selector对应一个线程，一个线程对应多个channel（连接）
* 可以多个channel注册到一个selector
* 程序切换到哪个个channel是有事件决定的，Event就是一个重要的概念
* Selector会根据不同的事件，在各个通道上切换
* Buffer就是一个内存块，底层是有一个数组
* 数据的读取写入是通过Buffer，这个和BIO，BIO中要么是输入流或者是输出流，不能双向，但是NIO的BUffer是可以读也可以写，需要flip方法切换
* channel是双向的，可以返回底层操作系统的情况，比如linux，底层的操作系统通道是双向的。

### 1.2 NIO和BIO的比较

* BIO以流的方式处理数据，而NIO以块的方式处理数据，块I/O的效率比流I/O高很多
* BIO是阻塞的，NIO则是非阻塞的
* BIO基于字节流和字符流进行操作的，而NIO基于Channel（通道）和Buffer（缓冲区）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入通道中。Selector（选择器）用于监听多个通道的时间。（比如：连接请求，数据到达等），因此使用单个线程就可以监听多个客户端通道。

## 2、NIO的Buffer

### 2.1 基本介绍

* 缓冲区本质上是一个可以读写数据的内存块，可以理解 成是一个**容器对象（含数组）**，该对象提供了一组方法，可以更轻松地使用内存块，缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况。Channel提供从文件、网络读取数据的渠道，但是读取或写入数据必须经由缓冲区。

### 2.2 Buffer类及其子类

* 在NIO中，Buffer是一个顶层父类，它是一个抽象类，类的层级关系图

<img src="/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220905234923354.png" alt="image-20220905234923354" style="zoom:50%;" />

* Buffer类定义了所有的缓冲区都具有四个属性来提供来提供关于其所包含的数据元素的信息

  ![image-20220905235542494](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220905235542494.png)

  * Capacity：容量，即可以容纳的最大数据量连载缓冲区创建时被设定，不能改变
  * limit：表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的
  * position：位置，下个要被读写的元素的索引，每次读写缓冲区数据时都会改变之，为下次读写作准备
  * mark：标记

* 相关方法

  ```java
  public final int capacity() //返回次缓冲区的容量
  public final int position() //返回此缓冲区的位置
  public final Buffer position(int newPosition) //设置缓冲区的位置
  public final int limit() //设置此缓冲区的限制
  public final Buffer mark() //在此缓冲区的位置设置标记
  public final Buffer reset() //在此缓冲区的位置重置为以前标记的位置
  public final Buffer clear() //清除此缓冲区，即将各个标记恢复到初始状态，但是数据并没有真正擦除
  public final Buffer flip() //反转此缓冲区
  public final Buffer rewind() //重绕此缓冲区
  public final int remaining() //返回当前位置与限制与阿苏之间的元素数
  public final boolean hasRemaining() //告知在当前位置和限制之间是否有元素
  public abstract boolean isReadOnly() //告知此缓冲区是否为只读缓冲区
  
  //JDK1.6时引入的api
  public abstract boolean hasArray() //告知此缓冲区是否具有可访问的底层实现数组
  public abstract Object array(); //返回此缓冲区的底层实现数组
  public abstract int arrayOffset(); //返回此缓冲区的底层实现数组中第一个缓冲区元素的偏移量
  public abstract boolean isDirect(); //告知此缓冲区是否为直接缓冲区
  ```

* ByteBuffer

  * 从前面可以看出对于Java中的基本数据类型（boolean）除外，都有个Buffer类型与之相对应，最常用的自然是ByteBuffer类（二进制数据），该类的主要方法如下：

  ```java
  public static ByteBuffer allocateDirect(int capacity) //创建直接缓冲区
  public static ByteBuffer allocate(int capacity) //创建缓冲区容量
  public static ByteBuffer wrap(byte[] array) //把一个数组放到缓冲区中使用
  public static ByteBuffer wrap(byte[] array,int offset, int length) //构造初始化位置offset和上界length的缓冲区
  
  //缓冲区存取相关API
  public abstract byte get(); //从当前位置position上get，get之后，position会自动+1
  public abstract byte get(int index); //从绝对位置get
  public abstract ByteBuffer put(byte b); //从当前位置普通，put之后，position会自动+1
  public abstract ByteBuffer put(int index, byte b); //从绝对位置上put
  
  ```

  

### 2.3 基本使用

```java
package com.smc.bio.nio;

import java.nio.IntBuffer;

/**
 * @Date 2022/9/5
 * @Author smc
 * @Description:
 */
public class BasicBuffer {
    public static void main(String[] args) {
        /**
         * buffer简单使用
         */
        //创建一个buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //如果从buffer读取数据
        //将buffer转换，读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}

```

## 3、通道（Channel）

### 3.1 基本介绍

* NIO的通道类似于流，但有些区别如下：

  * 通道可以同时进行读写，而流只能读或者只能写
  * 通道可以实现异步读写数据
  * 通道可以从缓冲读数据，也可以写数据到缓冲

* BIO的Stream的是单向的，例如FileInoutStream对象只能进行读取数据的操作，而NIO中的通道（Channel）是双向的，可以读操作，也可以写操作。

* Channel在NIO中是一个接口

  public interface Channel extends Closeable{}

* 常用的Channel类有FileChannel、DatagramChannel、ServerSocketChannel和SocketChannel
* FileChannel用于文件的数据读写，DatagramChannel用于UDP的数据读写，ServerSocketChannel和SocketChannel用于TCP的数据读写

### 3.2 实例代码：写文件

* 将hello，smc写入到一个文件

```java
package com.smc.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/9/6
 * @Author smc
 * @Description:
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str  = "hello,书蒙尘";
        //创建一个输出流
        FileOutputStream fileOutputStream =
                new FileOutputStream("/Users/smc/Desktop/smc/语言学习/java/netty/file.txt");
        //通过fileoutputStream，获取对应的FileChannel
        //这个filechannel真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入到byteBuffer
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));

        //对byteBuffer 进行flip
        byteBuffer.flip();

        //将byteBuffer数据写入到fileChannel
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}

```

### 3.3 实例代码：读文件

```java
package com.smc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Date 2022/9/7
 * @Author smc
 * @Description:
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        //创建文件的输入流
        File file = new File("/Users/smc/Desktop/smc/语言学习/java/netty/file.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过fileInputStream获取对应的FileChannel->实际类型 FileChannelImpl
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将数据读入buffer
        channel.read(byteBuffer);
        //将byteBuffer的字节数据专程String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();


    }
}

```

### 3.4 实例代码：完成一个文件的读取和写入（使用一个buffer）

```java
package com.smc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Date 2022/9/7
 * @Author smc
 * @Description:
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){
            //需要复位文件，否则在读取一次后会会使read为0
            byteBuffer.clear();
            int read = channel.read(byteBuffer);
            if(read==-1){
                break;
            }
            //将buffer中的数据写入到channel1
            byteBuffer.flip();
            channel1.write(byteBuffer);

        }

        fileInputStream.close();;
        fileOutputStream.close();

    }
}

```

* 使用transferForm方法实现文件拷贝

  ```java
  package com.smc.nio;
  
  import java.io.FileInputStream;
  import java.io.FileOutputStream;
  import java.io.IOException;
  import java.nio.channels.FileChannel;
  
  /**
   * @Date 2022/9/14
   * @Author smc
   * @Description:
   */
  public class NIOFileChannel04 {
      public static void main(String[] args) throws IOException {
          //创建相关流
          FileInputStream fileInputStream = new FileInputStream("a.jpg");
          FileOutputStream fileOutputStream = new FileOutputStream("b.jpg");
  
          //获取各个流对应的filechannel
          FileChannel sourceCh = fileInputStream.getChannel();
          FileChannel destCh = fileOutputStream.getChannel();
  
          //使用transferForm完成拷贝
          destCh.transferFrom(sourceCh, 0, sourceCh.size());
  
          //关闭相关通道和流
  
          sourceCh.close();
          destCh.close();
          fileOutputStream.close();
          fileOutputStream.close();
  
      }
  }
  
  ```

### 3.5 关于Buffer和Channel的注意事项和细节

* ByteBuffer支持类型化的put和get，put放入的是什么数据类型，get就应该使用相应的数据类型来取出，否则可能有BufferUnderflowException异常

  ```java
  package com.smc.nio;
  
  import java.nio.ByteBuffer;
  
  /**
   * @Date 2022/9/14
   * @Author smc
   * @Description:
   */
  public class NiOByteBufferPutGet {
      public static void main(String[] args) {
          //创建一个ButeBuffer
          ByteBuffer byteBuffer = ByteBuffer.allocate(64);
  
          //类型化方式放入数据
          byteBuffer.putInt(100);
          byteBuffer.putLong(9);
          byteBuffer.putChar('书');
          byteBuffer.putShort((short) 4);
  
          //取出
          byteBuffer.flip();
  
          System.out.println(byteBuffer.getInt());
          System.out.println(byteBuffer.getLong());
          System.out.println(byteBuffer.getChar());
          //取出类型过长，抛出异常
          System.out.println(byteBuffer.getLong());
      }
  }
  
  ```

  ![image-20220914232635813](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220914232635813.png)

* 可以将一个普通buffer转成只读Buffer

  ```java
  package com.smc.nio;
  
  import java.nio.ByteBuffer;
  
  /**
   * @Date 2022/9/14
   * @Author smc
   * @Description:
   */
  public class ReadOnlyByteBuffer {
      public static void main(String[] args) {
          //创建buffer
          ByteBuffer buffer = ByteBuffer.allocate(64);
  
          for (int i = 0; i < 64; i++) {
              buffer.put((byte) i);
          }
  
          //读取
          buffer.flip();
  
          //得到一个只读的buffer
          ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
  
          System.out.println(readOnlyBuffer.getClass());
  
          //读取
          while (readOnlyBuffer.hasRemaining()){
              System.out.println(readOnlyBuffer.get());
          }
  
          //加入数据抛出异常
          readOnlyBuffer.putLong(99);
  
  
      }
  }
  
  ```

  ![image-20220914232840222](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220914232840222.png)

* NIO还提供了MapedByteBuffer，可以让文件直接在内存（堆外的内存）中进行修改，而如何同步到文件由NIO来完成

  ```java
  package com.smc.nio;
  
  import java.io.FileNotFoundException;
  import java.io.IOException;
  import java.io.RandomAccessFile;
  import java.nio.MappedByteBuffer;
  import java.nio.channels.FileChannel;
  
  /**
   * @Date 2022/9/14
   * @Author smc
   * @Description:
   * 1、MappedByteBuffer可让文件直接在内存（堆外内存）修改，操作系统不需要拷贝一次
   */
  public class MappedByteBufferTest {
      public static void main(String[] args) throws IOException {
          //rw表示读写模式
          RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
          
          //获取对应的通道
          FileChannel channel = rw.getChannel();
          /**
           * 参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
           * 参数2：：0：可以直接修改的起始位置
           * 参数3：映射到内存的大小，即将1.txt的多少个内存映射到内寸（可以直接修改的范围是0-4）
           * 实际类型是DirectByteBuffer
           */
          MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
          map.put(0, (byte) 'H');
          map.put(3, (byte) '9');
          //5是大小，不是索引位置，写了5会outofbounds
  //        map.put(5, (byte) 'Y');
  
  
          rw.close();
  
  
      }
  }
  ```

* 前面我们讲的读写操作，都是通过一个BUffer完成的，NIO还支持通过多个Buffer（即Buffer数组）完成读写操作，即Scattering和Gathering

  ```java
  package com.smc.nio;
  
  import java.io.IOException;
  import java.net.InetSocketAddress;
  import java.nio.ByteBuffer;
  import java.nio.channels.ServerSocketChannel;
  import java.nio.channels.SocketChannel;
  import java.util.Arrays;
  
  /**
   * @Date 2022/9/17
   * @Author smc
   * @Description: Scattering:将数据写入到buffer时，可以采用buffer数组，依次写入
   * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
   */
  public class ScatteringAndGatheringTest {
      public static void main(String[] args) throws IOException {
          //使用ServerSocketChannel 和SocketChannel网络
          ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
          InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
  
          //绑定端口到socket，并启动
          serverSocketChannel.socket().bind(inetSocketAddress);
  
          //创建buffer数组
          ByteBuffer[] byteBuffers = new ByteBuffer[2];
          byteBuffers[0]=ByteBuffer.allocate(5);
          byteBuffers[1]=ByteBuffer.allocate(3);
          //等待客户端链接（telnet）
          SocketChannel socketChannel = serverSocketChannel.accept();
          int messageLength = 8;//假定从客户端接收8个字节
          while (true){
              int byteRead = 0;
              while (byteRead<messageLength){
                  long read = socketChannel.read(byteBuffers);
                  byteRead +=read;
                  System.out.println("byteRead = "+byteRead);
                  Arrays.asList(byteBuffers).stream().map(buffer -> "position="+buffer.position()+
                          ",limit="+buffer.limit()).forEach(System.out::println);
              }
              //将所有buffer进行filp
              Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
              //将数据读出显示到客户端
              long byteWrite = 0;
              while (byteWrite < messageLength){
                  long write = socketChannel.write(byteBuffers);
                  byteWrite+=write;
              }
  
              //将所有的buffer进行clear,一个字母1个字节，回车两个字节
              Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
              System.out.println("byteRead="+byteRead+",byteWrite="+byteWrite+",messageLength"+messageLength);
          }
  
  
  
      }
  }
  
  ```

# 四、Selector(选择器)

## 1、基本介绍

* Java的NIO，用非阻塞的IO的方式。可以用一个线程，处理多个的客户端连接，就会使用到Selector（选择器）
* Selector能够检测多个 注册的通道上是否有时间发生（注意：多个Channel以事件的方式可以注册到同一个Selector），如果有事件发生，便获取事件然后针对每个事件进行相应的处理。这样就可以只用一个单线程去管理多个通道channel，也就是管理多个连接和请求。
* 只有在连接/通道真正有读写事件发生时，才会进行读写，就大大地减少了系统开销，并且不必为每个连接都创建一个线程，不用去维护多个线程
* 避免了多线程之间的上下文且婚导致的开销

### 1.1 特点

* Netty的IO线程NioEventLoop聚合了Selector（选择器，也叫多路复用器），可以同时并发处理成百上千个客户端连接
* 当线程从某客户端Socket通道进行读写数据时，若没有数据可用时，该线程可以进行其他任务。
* 线程通常将非阻塞IO的空闲时间用于在其他通道上执行IO操作，所以单独的线程可以管理多个输入和输出通道
* 由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁I/O堵塞导致的线程挂起
* 一个I/O线程可以并发处理N个客户端连接和读写操作，这从根本上解决了传统同步阻塞I/O一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升

## 2、Selector API介绍

### 2.1 Selector类的相关方法

* Selector类是一个抽象类

```java
public abstract class Selector implements Closeable {
  public static Selector open();//得到一个选择器对象
  public abstract int select(long timeout);//监控所有注册的通道，当其中有IO操作可以进行时，将对应的SelectionKey加入到内部集合中并返回，参数用来设置超时时间
  public abstract Set<SelectionKey> selectedKeys();//从内部集合中得到所有的SelectionKey
  public abstract Selector wakeup();//唤醒selector
  public abstract int selectNow();//不阻塞，立马返回
}
```

### 2.2 SelectionKey在NiO体系

* NIO非阻塞网络编程原理分析图

  ![image-20220917165529203](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20220917165529203.png)

  * 当客户端连接时会通过ServerSocketChannel得到SocketChannel,开始监听
  * 将socketChannel注册到Selector上`public final SelectionKey register(Selector sel, int ops)`,selector可以注册多个channel
  * 注册后返回一个SelectionKey，会和该Selector关联
  * Selector进行监听，select方法，返回有事件发生的通道的个数
  * 进一步得到各个SelectionKey
  * 在通过SelectionKey反向回去SocketChanne们方法是`public abstract SelectableChannel channel();`
  * 可以得到channel，完成业务处理

## 3、案例1

* 服务端

  ```java
  package com.smc.selector;
  
  import java.io.IOException;
  import java.net.InetSocketAddress;
  import java.nio.ByteBuffer;
  import java.nio.channels.*;
  import java.nio.charset.StandardCharsets;
  import java.util.Iterator;
  import java.util.Set;
  
  /**
   * @Date 2022/9/17
   * @Author smc
   * @Description:
   */
  public class NIOServer {
      public static void main(String[] args) throws IOException {
          //创建serversocketChannel
          ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
          //得到一个Selector对象
          Selector selector = Selector.open();
          //绑定端口
          serverSocketChannel.socket().bind(new InetSocketAddress(6660));
  
          //设置非阻塞
          serverSocketChannel.configureBlocking(false);
  
          //把serverSocketChannel注册到selector关心时间为OP_ACCEPT
          serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  
          //循环等待客户端连接
          while (true){
              //这里我们等待1秒，如果没有事情发生，返回
              if (selector.select(1000)==0){//没有事情发生
                  System.out.println("服务器等待了1秒，无连接");
                  continue;
              }
              /**
               * 如果返回>0，就获取到相关的selectionKey集合
               * 1、如果返回>0表示已经获取到关注的事件
               * 2、selector.selectedKeys()返回关注时间的集合
               *      通过selectionkey反向获取通道
               */
              Set<SelectionKey> selectionKeys = selector.selectedKeys();
  
              //遍历
              Iterator<SelectionKey> iterator = selectionKeys.iterator();
  
              while (iterator.hasNext()){
                  //获取selectionKey
                  SelectionKey next = iterator.next();
                  if (next.isAcceptable()){//如果是OP_ACCEPT,就有新的客户端连接
                      //就给该客户端生辰给一个SocketChannel
                      SocketChannel socketChannel = serverSocketChannel.accept();
  
                      //设置非阻塞
                      socketChannel.configureBlocking(false);
                      System.out.println("客户端连接成功，生成一个socketChannel"+socketChannel.hashCode());
                      //将socketChannel注册到Selector,关注时间为OP_READ，同时给socketChannel关联一个Buffer
                      socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
  
                  }
                  if(next.isReadable()){//发生op_read
                      //通过key反向获取channel
                      SocketChannel channel = (SocketChannel) next.channel();
                      //获取到该channel关联到buffer
                      ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                      channel.read(byteBuffer);
                      System.out.println("from客户端："+new String(byteBuffer.array()));
  
                  }
  
                  //手动从集合中移除当前的selectionKey，防止重复操作
                  iterator.remove();
              }
  
  
          }
      }
  }
  
  ```

* ```java
  package com.smc.selector;
  
  import java.io.IOException;
  import java.net.InetSocketAddress;
  import java.nio.ByteBuffer;
  import java.nio.channels.SocketChannel;
  
  /**
   * @Date 2022/9/17
   * @Author smc
   * @Description:
   */
  public class NIOClient {
      public static void main(String[] args) throws IOException {
          //得到一个网络通道
          SocketChannel socketChannel = SocketChannel.open();
          //设置非阻塞
          socketChannel.configureBlocking(false);
          //提供服务器的ip和端口
          InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6660);
          //连接服务器
          if (!socketChannel.connect(inetSocketAddress)){
              while (!socketChannel.finishConnect()){
                  System.out.println("因为连接需要时间，客户端不回阻塞，可以做其他工作");
              }
          }
          //如果连接成功，就发送数据
          String str = "111";
          ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
          //发送数据，将buffer数据写入channel
          socketChannel.write(buffer);
          System.in.read();
      }
  }
  
  ```

## 4、SelectionKey

* SelectionKey，表示Selector和网络通道的注册关系，共四种
  * int OP_ACCEPT：有新的网络连接可以accept，值为16`public static final int OP_ACCEPT = 1 << 4;`
  * int OP_CONNECT：代表连接已经建立，值为8`public static final int OP_CONNECT = 1 << 3;`
  * int OP_READ：代表读操作，值为1`public static final int OP_READ = 1 << 0;`
  * int OP_WRITE：代表写操作，值为4`public static final int OP_WRITE = 1 << 2;`

* 相关方法

  ```java
  public abstract Selector selector();//得到与之关联的Selector对象
  public abstract SelectableChannel channel();//得到与之关联的通道
  public final Object attachment();//得到与之关联的共享数据
  public abstract SelectionKey interestOps(int ops);//设置或改变监听事件
  public final boolean isAcceptable();//是否可以accept
  public final boolean isReadable();//是否可以读
  
  ```

## 5、ServerSocketChannel

* ServerSocketChannel在服务器端监听新的客户端Socket连接

* 相关方法

  ```java
  public abstract class ServerSocketChannel
      extends AbstractSelectableChannel
      implements NetworkChannel
  {
    public static ServerSocketChannel open();//得到一个ServerSocketChannel通道
    public final ServerSocketChannel bind(SocketAddress local);//设置服务器端口号
    public final SelectableChannel configureBlocking(boolean block);//是父类AbstractSelectableChannel的一个方法，设置阻塞或非阻塞模式，取false表示采用非阻塞模式
    public abstract SocketChannel accept();//接受一个连接，返回代表这个连接的通道对象
    public final SelectionKey register(Selector sel, int ops,Object att);//注册一个选择器，并设置监听事件
  }
  ```

## 6、SocketChannel

* SocketChannel，网络IO通道，具体负责进行读写操作。NIO把缓冲区的数据写入通道，或者把通道里的数据读到缓冲区。

* 相关方法

  ```java
  public abstract class SocketChannel
      extends AbstractSelectableChannel
      implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, NetworkChannel
  {
    public static ServerSocketChannel open();//得到一个SocketChannel通道
    public final SelectableChannel configureBlocking(boolean block);//是父类AbstractSelectableChannel的一个方法，设置阻塞或非阻塞模式，取false表示采用非阻塞模式
    public abstract boolean connect(SocketAddress remote);//连接服务器
    public abstract boolean finishConnect();//如果上面的方法连接失败，接下来就要通过该方法连接操作
    public abstract int write(ByteBuffer src);//往通道里写数据
    public final long read(ByteBuffer[] dsts);//从通道里读数据
    public final SelectionKey register(Selector sel, int ops,Object att);//注册一个选择器，并设置监听事件
    public final void close();//关闭通道
  }
  ```

# 五、群聊系统

## 1、需求

* 编写一个NIO群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
* 实现多人群聊
* 服务器端：可以监听用户上线，离线，并实现消息转发功能
* 客户端：通过channel可以无阻塞发送消息给其他所有用户，同时可以接受其他用户发送的消息（有服务器转发得到）
* 目的：进一步理解NIO非阻塞网络编程

## 2、原理图

![image-20221112161429547](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221112161429547.png)

### 2.1 先编写服务端

#### 2.1.1 服务器启动并坚挺6667端口

#### 2.1.2 服务器接受客户端信息，并实现转发（处理上线和离线）

```java
package com.smc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    public static final int PORT=6667;

    //构造器初始化操作
    public GroupChatServer(){
        try {
            //得到选择器
            selector=Selector.open();
            //得到serverSocketChannel
            listenChannel= ServerSocketChannel.open();
            //绑定端口
            listenChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将channel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //监听
    public void listen(){
        try {
            //循环处理
            while (true){
                int count=selector.select(2000);
                //大于0,表示有事件要处理
                if (count>0){
                    //便利得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            //注册到socket
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        //通道发生read事件
                        if(key.isReadable()){
                            //处理读
                            readData(key);
                        }

                        //当前key删除，防止重复处理

                        iterator.remove();
                    }
                }else {
//                    System.out.println("服务端等待...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  读取客户端消息
    private void readData(SelectionKey key)  {
        //定义一个socketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //读取
            int read = channel.read(buffer);
            //判断是否读取到数据
            if(read>0){
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出消息
                System.out.println("from 客户端：" + channel.getRemoteAddress() + " 的消息：" + msg);

                //把消息转发给其他客户端(需去掉自己)
                sendInfoToClients(msg,channel);
            }
        }catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    //转发消息给其他客户（通道）
    private void sendInfoToClients(String msg,SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        //便利所有注册到selector上的Socketchannel，并排除channel
        for (SelectionKey key : selector.keys()) {
            //通过key取出对应的socketChannel
            SelectableChannel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel!=selfChannel){
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;

                //将buffer存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));

                //将buffer数据写到channel

                dest.write(buffer);

            }
        }

    }
    public static void main(String[] args) {
        //创建一份服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
```



### 2.2 编写客户端

#### 2.2.1 连接服务器

#### 2.2.2 发送消息

#### 2.2.3 接收服务器消息

```java
package com.smc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class GroupChatClient {
    //定义相关属性
    private final String HOST = "127.0.0.1";//服务器ip
    private final int PORT = 6667;//服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器，完成初始化操作
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString();
        System.out.println(username + " is ok ..");
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info = username+"说："+info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取从服务器端回复的消息
    public void readInfo(){
        try {
            int read = selector.select();
            //如果read大于有发生事件的通道
            if (read>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }else{
//                System.out.println("没有可用的通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程
        new Thread() {
            @Override
            public void run() {
                while (true){
                    chatClient.readInfo();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
```

# 六、NIO与零拷贝

* 零拷贝是网络编程的关键，很多性能优化都离不开
* 在Java程序中，常用的零拷贝有mmap（内存映射）和sendFile。那么，他们在OS里，到底是怎样的一个的设计？
* NIO中如何使用零拷贝

## 1、原理剖析

### 1.1 传统IO数据的读写（4次拷贝，三次切换）

![image-20221112185925846](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221112185925846.png)

### 1.2 mmap优化（三次拷贝，三次切换）

* mmap通过内存映射，将文件映射到内核缓冲区，同时，用户空间可以共享内核空间的数据。这样在进行网络传输的时，就可以减少内核空间到用户空间的拷贝次数

![image-20221112190400646](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221112190400646.png)

### 1.3 sendFile优化（三次拷贝，二次切换）

* Linux2.1版本提供了sendFile函数，其基本原理如下：数据根本不经过用户态，直接从内核缓冲区进入到SocketBuffer，同时由于和用户态完全无关，就减少了一次上下文切换

![image-20221112191144302](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221112191144302.png)

### 1.4 sendFile 升级优化（二次拷贝，二次切换）

* Linux在2.4版本中，做了一些修改，避免了从内核缓冲区卡贝到Socketbuffer的操作，直接拷贝到协议栈，从而再一次减少了数据拷贝。
* 这里其实有一次cpu拷贝kernel buffer->socket buffer，但是，拷贝的信息很少，比如lengt，offset消耗低，可以忽略

![image-20221112192100173](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221112192100173.png)

### 1.5 零拷贝再理解

* 我们说零拷贝，是从操作系统角度来说的。因为内核缓冲区之间，没有数据是重复的（只有kernel buffer有一份数据）
* 零拷贝不仅仅带来更少的数据赋值，还能带来其他的性能优势，例如更少的上下文切换，更少的CPU缓存伪共享以及无CPU校验和计算

* mmap适合小数据量读写，sendFile适合大文件传输

## 2、零拷贝应用实例

### 2.1 案例要求

* 使用传统的IO方法传递一个大文件
* 使用NIO零拷贝方式传递（transferTo）一个大文件
* 看看两种传递方式消耗的时间分别是多少

```java
package com.smc.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount=0;
            while (-1 !=readCount){
                try {
                    readCount = socketChannel.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.rewind();//倒带position=0，mark报废
            }
        }
    }
}
```



```java
package com.smc.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename = "202211102054.csv";

        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);

        //在linux下一个transferTo 方法就可以完成传树
        //在Windows下一次调用transferTo只能发送8m，就需要分段传输文件，而且需要注意传输位置
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数=" + transferCount + "，耗时：" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
```

# 七、BIO、NIO、AIO对比

## 1、JavaAIO基本介绍

* JDK7引入了Asynchronous I/O,即AIO。在进行I/O编程中，常用到两种模式：Reactor和Proactor。Java就是Reactor，当有事件触发时，服务端得到通知，进行相应的处理
* AIO即NIO2.0，叫做异步不阻塞的IO。AIO引入异步通道的概念，采用了Proactor模式，简化了程序编写，有效的请求才启动线程。它的特点是先由操作系统完成后才通知服务端程序启动线程区处理，一般适用于连接数较多且连接时间较长的应用。

## 2、比较

|          | BIO      | NIO                    | AIO        |
| -------- | -------- | ---------------------- | ---------- |
| IO模型   | 同步阻塞 | 同步非阻塞（多路复用） | 异步非阻塞 |
| 编程难度 | 简单     | 复杂                   | 复杂       |
| 可靠性   | 差       | 好                     | 好         |
| 吞吐量   | 低       | 高                     | 高         |

* 举例说明
  * 同步阻塞：到理发店理发，就一直等理发师，知道轮到自己理发
  * 同步非阻塞：到理发店理发，发现前面有其他人理发，给理发师说下，先干其他事，一会过来看是否轮到自己
  * 异步非阻塞：给理发师打电话，让理发师上门服务，自己干其他事情，理发师自己来家给你理发

# 八、Netty

## 1、Netty概述

### 1.1 原生NIO存在的问题

* NIO的类库和API繁杂，使用麻烦：需要熟练掌握Selector，ServerSocketChannel、SocketChannel、ByteBuffer等
* 需要具备其他的额外技能：要熟悉Java多线程编程，因为NIO编程设计到Reactor模式，你必须对多线程和网络编程非常熟悉，才能编写高质量的NIO程序。
* 开发工作量和难度都非常大：例如客户端面临断连重连，网络闪断、半包读写、失败缓存、网络拥塞和一长溜的处理等等
* JDK NIO的Bug：例如臭名昭著的EpollBug，它会导致Selector空轮询，最终导致CPU 100%，知道JDK1.7版本该问题仍旧存在，没有根本解决。

### 1.2 Netty官网说明

* 官网:<https://netty.io/>

![image-20221113180714094](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221113180714094.png)

* Netty是由JBOSS提供的一个Java开源框架。Netty提供异步的、基于事件驱动的网络应用程序框架，用以开素开发高性能、高可靠性的网络IO程序
* Netty可以帮助你快速、简单的开发出一个网络应用，相当于简化和流程化的NIO的开发过程
* Netty是目前最流行的NIO框架，Netty在互联网领域、大数据分布式计算领域、游戏行业、通信行业等获得了广泛的应用，指定的Elasticsearch、Dubbo框架内不都采用了Netty

### 1.3 Netty的有点

* Netty对JDK自带的NIO的API进行了封装，解决了上述问题
  * 设计优雅：适用于各种传输类型的统一，API阻塞和非阻塞Socket；基于灵活且可扩展的事件模型，可以清晰地分离关注点了高度可定制的线程模型-单线程，一个或多个线程池。
  * 适用方便：详细记录的Javadoc，用户指南和实力，没有其他依赖项，JDK5（Netty3.x）或6（Netty4.x）就足够了
  * 高性能、吞吐量高：延迟更低；减少资源消耗；最小化不必要的内存复制
  * 安全：完整的SSL/TLS个StartTLS支持
  * 社区活跃、不断更新：社区活鱼，版本迭代周期短，发现的Bug可以被及时修复，同时，更多的新功能会被加入。

## 2、Netty高性能架构设计

### 2.1 线程模型基本介绍

* 不同的线程模式，对程序的性能有很大影响，为了高清Netty线程模式，我们来系统的讲解下各个线程模式，最后看看Netty线程模型的优越性
* 目前存在的线程模型有：
  * 传统阻塞I/O服务模型
  * Reactor模式
* 根据Reactor的数量和处理资源池线程的数量不同，有三种典型的实现
  * 单Reactor单线程
  * 单Reactor多线程
  * 主从Reactor多线程
* Netty线程模式（Netty主要基于主从Reactor多线程模型做了一定的改进，其中主从Reactor多线程模型有多个Reactor）

### 2.2 传统阻塞I/O服务模型

![image-20221113224013063](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221113224013063.png)

* 工作原理图
  * 黄色：对象
  * 蓝色：线程
  * 白色：方法（API）
* 模型的特点
  * 采用阻塞I/O获取输入的数据
  * 每个连接都需要独立的线程完成的数据的输入，业务处理，数据返回
* 问题分析
  * 当并发数很大，就会创建大量的线程，占用很大的系统资源
  * 连接创建后，如果当前线程暂时没有数据可读，该线程会阻塞在read操作，造成线程资源的浪费

### 2.3 Reactor模式

#### 2.3.1 针对传统阻塞I/O服务模型的两个缺点，解决方案：

* 针对传统I/O复用模型：多个连接共用一个阻塞对象，应用程序只需要在一个阻塞对象等待，无需阻塞等待所有连接。当某个连接有新的数据可以处理时，操作系统会通知应用程序，线程从阻塞状态返回，开始进行业务处理
* Reactor对应的叫法
  * 反应器模式
  * 分发者模式（Dispatcher）
  * 通知者模式（notify）
* 基于线程池复用线程资源：不必再为每个连接创建线程，将连接完成后的业务处理任务分配给线程进行处理，一个线程可以处理多个客户端的任务

![image-20221113234116234](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221113234116234.png)

#### 2.3.2 整体设计架构图

* Reactor模式，通过一个或多个输入同时传递给服务处理器的模式（基于事件驱动）
* 服务器程序处理传入的多个请求，并将它们同步分派相应的处理线程，因此Reactor模式也叫Dispatcher模式
* Reactor模式使用IO复用监听事件，收到事件后，分发给某个线程（进程），这点就是网络服务器高并发处理的关键

![image-20221113234758497](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221113234758497-8354479.png)

### 2.4 Reactor模式中核心组成

* Reactor：Reactor在一个单独的线程中运行，负责监听和分发事件，分发给适当的处理程序来对IO事件做出反应。它就像公司的电话接线员，它接听来自客户的电话并将线路转移给适当的联系人
* Handler：处理程序执行IO事件要完成的实际事件，类似于客户想要与之交谈的公司的实际人。Reactor通过调度适当的处理程序来响应I/O事件，处理程序执行非阻塞操作

### 2.5 模式的分类

* 根据Reactor的数量个处理资源池线程的数量不同，有三种典型的实现
  * 单Reactor单线程
  * 单Reactor多线程
  * 主从Reactor多线程（Netty主要运用模型）

## 3、单Reactor单线程

![image-20221113235648475](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221113235648475.png)

* 方案说明
  * Select是前面I/O 复用模型介绍的标准网络编程API，可以实现应用程序通过一个阻塞对象监听多路连接请求
  * Reactor对象通过Select监控客户端请求事件，收到事件后通过Dispatch进行分发
  * 如果是建立连接请求事件，则由Acceptor通过Accept处理连接请求，然后创建一个Handler对象处理连接完成后的后续业务处理
  * 如果不是建立连接事件，则Reactor会分发调用连接对应的Handler来响应
  * Handler会完成Read->业务处理->Send的完成业务流程
* 优缺点分析
  * 优点：模型简单，没有多线程、进程通信，竞争的问题，全部都在一个线程中完成
  * 缺点：性能问题。只有一个线程，无法完全发挥多核CPU的性能。Handler在处理某个连接上的业务时，整个进程无法处理其他连接事件，很容易导致性能瓶颈
  * 缺点：可靠性问题，线程意外终止，或者进入死循环，会导致整个系统通信模块不可用，不能接收和处理外部消息，造成节点故障
  * 使用场景：客户端数量有限，业务处理非常快速，比如Redis在业务处理的时间复杂度O(1)的情况

## 4、单Reactor多线程

![image-20221114001320744](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221114001320744.png)

* 方案说明
  * Reactor对象通过select监控客户端请求事件，收到事件后，通过dispatch进行分发
  * 如果是建立连接请求，则由Acceptor通过accept处理连接请求，然后创建一个Handler对象处理完成连接后的各种事件
  * 如果不是连接请求，则由Reactor会分发调用对应连接的Handler
  * Handler只负责响应事件，不做具体业务处理，通过read读取数据后会分发给后面的worker线程池的某个处理线程处理业务
  * woker线程池会分配独立线程完成真正的业务，并将结果返回给handler。
  * handler收到响应后，通过send返回给client。

* 优缺点
  * 优点：可以充分利用多核cpu的处理能力
  * 缺点：多线程数据共享和访问比较复杂，reactor处理所有事件的监听和响应，在单线程运行，在高并发场景容易出现性能瓶颈

## 5、主从Reactor多线程

![image-20221114235655427](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221114235655427.png)

* 方案说明
  * Reactor主线程MainReactor对象通过select监听连接事件，收到事件后，通过Acceptor处理连接事件
  * 当Acceptor处理连接事件后，在MainReactor将连接分配给SubReactor
  * SubReactor将连接加入到连接队列，并创建handler进行各种事件处理
  * 当有新事件发生时，subreactor就会调用对应的handler处理
  * handler通过read读取数据，会分发给worker线程处理
  * worker线程池会分配独立的workder线程进行业务处理，并返回结果
  * handler收到响应的结果后，在通过send将结果返回给client
  * Reactor主线程可以对应多个Reactor子线程，即MainReactor可以关联多个SubReactor

* 优缺点
  * 优点：父线程与子线程的数据交互简单指责明确，父线程只需接收新连接，子线程完成后续的业务处理
  * 优点：父线程与子线程的数据交互简单，Reactor主线程只需要把新连接传给子线程，子线程无需返回数据
  * 缺点：编程复杂度较高
* 结合实例：这宗模式在许多项目中广泛使用过，包括Nginx主从Reactor多进程模型，Memcached主从多线程，Netty主从多线程模型的支持

## 6、Netty模型

### 6.1 工作原理示意图-简单版

* Netty主要基于主从Reactors多线程做了一定的修改，其中主从Reactor多线程模型有多个Reactor

![image-20221115191156832](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221115191156832.png)



* BossGroup线程维护Selector，只关注Accecpt
* 当接收Accept事件，获取对应的SocketChannel，封装成NIOSocketChaneel并注册到Worker线程（事件循环），并进行维护
* 当Worker线程监听到selector中的通道发生了自己感兴趣的事件后，他就进行处理（就由handler）

### 6.2 工作原理示意图-进阶版

* 主从Reactor多线程模型有多个Reactor

![image-20221115192015578](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221115192015578.png)

### 6.3 工作原理示意图-详细版

![image-20221115192311289](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221115192311289.png)

* Netty抽象出两组线程池BossGroup专门负责接收客户端的连接，WorkerGroup专门负责网络的读写
* BossGroup和WorkerGroup类型都是NioEventLoopGroup
* NioEventLoopGroup相当于一个事件循环组，这个组中含有多个时间循环，每一个时间循环时NioEventLoop
* NioEventLoop表示一个不断循环的执行处理任务的线程，每个NioEventLoop都有一个selector，用于绑定在其上的socket的网络通讯
* NioEventLoopGroup可以有多个线程，即可以含有多个NioEventLoop
* 每个Boss NioEventLoop执行的步骤有三步
  * 轮询accept事件
  * 处理accept事件，与client建立连接，生成NioSocketChannel，并将其注册到某个worker NioEventLoop上的selector
  * 处理任务队列的任务，即runAllTasks
* 每个Woker NioEventLoop循环执行的步骤
  * 轮询read，write事件
  * 处理IO事件，即read，write事件，在对应的NioSocketChannel
  * 处理任务队列的任务，即runAllTasks
* 每个Worker NioEventLoop处理业务时，会使用pipeline（管道），pipeline中包含了channel，即通过pipeline可以获取到对应管道，管道中维护了很多的处理器

## 7、Netty快速入门实例

* Netty服务器在6668 端口监听，客户端能发送消息给服务器

* 服务器可以回复消息给客户端

* 服务器

  ```java
  package com.smc.netty.simple;
  
  import io.netty.bootstrap.ServerBootstrap;
  import io.netty.channel.Channel;
  import io.netty.channel.ChannelFuture;
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelOption;
  import io.netty.channel.nio.NioEventLoopGroup;
  import io.netty.channel.socket.SocketChannel;
  import io.netty.channel.socket.nio.NioServerSocketChannel;
  import io.netty.channel.socket.nio.NioSocketChannel;
  
  /**
   * @Date 2022/11/15
   * @Author smc
   * @Description:
   */
  public class NettyServer {
      public static void main(String[] args) throws InterruptedException {
          /**
           * 创建BossGroup 和WorkGroup
           * 说明：
           * 1、创建两个线程bossGroup和workerGroup
           * 2、bossGroup只是处理连接请求，真正和客户端业务处理会交给workGroup完成
           * 3、两个都是无限循环
           * 4、bossGroup和workerGroup含有的子线程（NioEventLoop）的个数：默认实际cpu核数*2
           */
          NioEventLoopGroup bossGroup = new NioEventLoopGroup();
          NioEventLoopGroup workGroup = new NioEventLoopGroup();
  
          //创建服务器端的启动对象，配置参数
          ServerBootstrap bootstrap = new ServerBootstrap();
  
          try {
              //使用链式编程来进行设置
              bootstrap.group(bossGroup,workGroup)//设置两个线程组
                      .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通道实现
                      .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到的连接个数
                      .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                      .childHandler(new ChannelInitializer<SocketChannel>() {
                          //创建一个通道测试对象
                          //给pipeline设置处理器
                          @Override
                          protected void initChannel(SocketChannel ch) throws Exception {
                              ch.pipeline().addLast(new NettyServerHandler());
                          }
                      });//给我们的workerGroup的EventLoop对应的管道设置处理器
              System.out.println("服务器已经准备好了");
  
              //绑定一个端口并且同步，生成一个ChannelFuture
              ChannelFuture cf = bootstrap.bind(6668).sync();
  
              //关闭通道进行监听
              cf.channel().closeFuture().sync();
          } finally {
              bossGroup.shutdownGracefully();
              workGroup.shutdownGracefully();
          }
      }
  }
  
  ```

* 服务器Handler

  ```java
  package com.smc.netty.simple;
  
  import io.netty.buffer.ByteBuf;
  import io.netty.buffer.Unpooled;
  import io.netty.channel.Channel;
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.ChannelInboundHandlerAdapter;
  import io.netty.channel.ChannelPipeline;
  import io.netty.util.CharsetUtil;
  
  
  /**
   * @Date 2022/11/16
   * @Author smc
   * @Description:我们自定义一个Handler 需要继续netty规定好的某个HandlerAdapter
   * 这时我们自定义一个Handler，才能称为Handler
   */
  public class NettyServerHandler extends ChannelInboundHandlerAdapter {
      /**
       * 读取实际数据（这里我们可以读取客户端发送的信息）
       * ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
       * objet msg：就是客户端发送的数据 默认Object
       * @param ctx
       * @param msg
       * @throws Exception
       */
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
  
          System.out.println("服务器读取线程：" + Thread.currentThread().getName());
          System.out.println("server ctx=" + ctx);
          System.out.println("看看channel和pipeline的关系");
          Channel channel = ctx.channel();
          ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表
  
          //将msg转成一个byteBuf,属于netty的Bytebuffer
  
          ByteBuf buffer= (ByteBuf) msg;
          System.out.println("客户端发送消失是：" + buffer.toString(CharsetUtil.UTF_8));
          System.out.println("客户端地址：" + channel.remoteAddress());
      }
  
      /**
       * 数据读取完毕
       * @param ctx
       * @throws Exception
       */
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          /**
           * writeAndFlush write+flush
           * 将数据写入到缓存，并刷新
           * 一般讲，我们对这个发送的数据进行编码
           */
          ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～",CharsetUtil.UTF_8));
      }
  
      /**
       * 处理异常，一般是需要关闭通道
       * @param ctx
       * @param cause
       * @throws Exception
       */
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          ctx.close();
      }
  }
  
  ```

* 客户端

  ```java
  package com.smc.netty.simple;
  
  import io.netty.bootstrap.Bootstrap;
  import io.netty.channel.ChannelFuture;
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.nio.NioEventLoopGroup;
  import io.netty.channel.socket.SocketChannel;
  import io.netty.channel.socket.nio.NioSocketChannel;
  
  /**
   * @Date 2022/11/16
   * @Author smc
   * @Description:
   */
  public class NettyClient {
      public static void main(String[] args) {
          //客户端只需要一个事件循环组
          NioEventLoopGroup clientGroup = new NioEventLoopGroup();
  
          /**
           * 创建客户端启动对象
           * 注意客户端使用的不是ServerBootstrap，而是 Bootstrap
           */
          Bootstrap bootstrap = new Bootstrap();
  
          try {
              //设置相关参数
              bootstrap.group(clientGroup) //设置线程组
                      .channel(NioSocketChannel.class) //设置客户端通道的实现类（反射）
                      .handler(new ChannelInitializer<SocketChannel>() {
                          @Override
                          protected void initChannel(SocketChannel ch) throws Exception {
                              ch.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                          }
                      });
              System.out.println("客户端 ok...");
  
              //启动客户端去连接服务器端
              ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
              //给关闭通道进行监听
              channelFuture.channel().closeFuture().sync();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }finally {
              clientGroup.shutdownGracefully();
          }
  
      }
  }
  
  ```

* 客户端handler

  ```java
  package com.smc.netty.simple;
  
  import io.netty.buffer.ByteBuf;
  import io.netty.buffer.Unpooled;
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.ChannelInboundHandlerAdapter;
  import io.netty.util.CharsetUtil;
  
  /**
   * @Date 2022/11/16
   * @Author smc
   * @Description:
   */
  public class NettyClientHandler extends ChannelInboundHandlerAdapter {
      //当通道就绪时就会触发该方法
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          System.out.println("client " + ctx);
  
          ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:", CharsetUtil.UTF_8));
  
  
      }
  
      //有通道读取事件时，会触发
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          ByteBuf buf= (ByteBuf) msg;
          System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
          System.out.println("服务器地址：" + ctx.channel().remoteAddress());
  
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          cause.printStackTrace();
          ctx.close();
      }
  }
  
  ```

## 8、taskQueue自定义任务

### 8.1 任务队列中的Task有三种典型使用场景

* 用户程序自定义的普通的任务
* 用户自定义定时任务
* 非当前Reactor线程调用Channel的各种方法
  * 例如在推送系统的业务线程里面，根据用户的标识，找到对应的Channel饮用，然后调用Write类方法像该用户推送消息，就会进入到这种场景。最终Write会提交到任务队列中后被异步消费。

```java
 /**
 * 比如这里我们又一个非常耗时长的任务->异步执行->提交该channel对应的NIOEventLoop到taskQueue中
 */
  Thread.sleep(1000*10);
  ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
  /**
           *  解决方案1,用户自定以的普通任务
           */
  ctx.channel().eventLoop().execute(new Runnable() {
    @Override
    public void run() {
      try {
        Thread.sleep(1000*10);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端3",CharsetUtil.UTF_8));


      } catch (InterruptedException e) {
        System.out.println("发生异常" + e.getMessage());
      }
    }
  });
  /**
           * 用户自定义定时任务->该任务是提交到scheduleTaskQueue中
           */
  ctx.channel().eventLoop().schedule(new Runnable() {
    @Override
    public void run() {
      try {
        Thread.sleep(1000 * 5);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端4", CharsetUtil.UTF_8));


      } catch (InterruptedException e) {
        System.out.println("发生异常" + e.getMessage());
      }
    }
  },5, TimeUnit.SECONDS);
```

### 8.2 方案再说明

* Netty抽象出两组线程池，BossGroup专门负责接收客户端连接，WorkerGroup专门负责网络读写操作
* NioEventLoop表示一个不断循环执行处理任务的线程，每个NioEventLoop都有一个selector，用于监听绑定在其上的socket网络通道
* NIOEventLoop内部采用串行化设计，从消息的读取->解码->处理->编码->发送，始终由IO线程NioEventLoop负责
  * NioEventLoopGroup下包含多个NioEventLoop
  * 每个NioEventLoop中包含一个Selector，一个taskQueue
  * 每个NioEventLoop的selector上可以注册监听多个NioChannel
  * 每个NioChannel只会绑定在唯一的NioEventLoop上
  *  每个NioChannel都绑定有一个自己的ChannelPipeline

## 9、异步模型

### 9.1 基本介绍

* Netty中的I/O操作时异步的，包括Bind、Write、Connect等操作会简单的返回一个ChannelFuture。
* 调用者并不能立刻获得结果，而是通过Future-Listener机制，用户可以方便的获取或者通过通知机制获得IO操作结果
* Netty的异步模型时建立在future和callback之上的。callback就是回调。重点说Future，它的核心思想是：假设一个方法fun，计算过程可能非常耗时，等待fun返回显然不合适。那么可以在调用fun的时候，立马返回一个Future，后续可以通过Future取监控方法fun的处理过程（即：Future-Listener机制）

### 9.2 Future说明

* 表示异步的执行结果，可以通过它提供的方法来检测执行是否完成，比如检索计算等等
* ChannelFuture是一个接口：`public interface ChannelFuture extends Future<Void>`,我们可以添加监听器，当监听的事件发生时，就会通知监听器。

### 9.3 原理示意图

![image-20221121145617170](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221121145617170.png)

* 说明：

  * 在使用Netty进行编程时，拦截操作和转换出入站数据只需要您提供callback或利用future即可。这使得链式操作简单、高效，并有利于编写可重用、通用的代码

  * Netty框架的木笔哦啊就是让你的业务逻辑从网络基础应用编码中分离出来，解脱出来。

### 9.4 Future—Listener机制

* 当Future对象刚刚创建时，处于非完成状态，调用者可以通过返回的ChannelFuture来获取操作执行的状态，注册监听函数来执行完成后的操作
* 常见有如下操作
  * 通过isDone方法来判断当前操作是否完成
  * 通过isSuccess方法来判断已完成的当前操作是否成功
  * 通过getCause方法来获取已完成的当前操作失败的原因
  * 通过isCancelled方法来判断已完成的当前操作是否被取消
  * 通过addListener方法来注册监听器，当操作已完成（isDone方法来返回完成），将会通知指定的监听器，如果Future对象已完成，则通知指定的监听器。

```java
//给cf注册监听器，监控我们关心的事件
cf.addListener(new ChannelFutureListener() {
  @Override
  public void operationComplete(ChannelFuture channelFuture) throws Exception {
    if(cf.isSuccess()){
      System.out.println("监听端口6668成功");
    }else {
      System.out.println("监听端口6668失败");
    }
  }
});
```

* 相比传统阻塞I/O，执行I/O操作后线程会被阻塞住，知道操作完成；异步处理的好处是不会造成线程阻塞，线程在I/O操作期间可以执行别的程序，在高并发情形下会更稳定和更高的吞吐量

## 10、 快速入门实例-HTTP服务

* 实例要求：使用IDEA创建Netty项目
* Netty服务器在6668端口监听，浏览器发出请求“http://localhost:6668/”
* 服务器可以回复消息客户端“Hello！我是服务器5”，并对特定请求资源进行过滤
* 目的：Netty可以做Http服务开发，并且理解Handler实例和客户端及其请求的关系

```java
package com.smc.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Date 2022/11/21
 * @Author smc
 * @Description:
 */
public class HttpServerNetty {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

```

```java
package com.smc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Date 2022/11/21
 * @Author smc
 * @Description:
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 加入一个netty，提供的httpServerCodec codec=>[coder-decoder]
         * HttpServerCodec 说明
         * 1、HttpServerCodec是netty，提供的处理http的编码解码器
         * 2、增加一个自定义的处理器Handler
         */
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        pipeline.addLast("MyTestHttpServerHandler",new HttpServerHanlder());
    }
}

```

```java
package com.smc.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Date 2022/11/21
 * @Author smc
 * @Description:
 * 1、SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2、HttpObject客户端和服务器端相互通许的数据被封装成HttpObject
 */
public class HttpServerHanlder extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是httprequest请求
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址 " + ctx.channel().remoteAddress());

            //回复消息给浏览器【http协议】

            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http响应，即httpreponse
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将构建好的response回应
            ctx.writeAndFlush(res);
        }
    }
}

```

# 九、Netty 核心模块组件

## 1、Bootstrap、ServerBootstrap

* Bootstrap意思是引导，一个Netty应用通常由一个Bootstrap开始，主要作用是配置整个Netty程序，串联各个组件，Netty中Bootstrap类是客户端的启动引导类，ServerBootstrap是服务端的启动引导类
* 常见的方法有
  * `public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)`:该方法应用于服务端，用来设置两个EventLoop
  * `public B group(EventLoopGroup group)`:该方法用于客户端，用来设置一个EventLoopGroup
  * `public B channel(Class<? extends C> channelClass)`:该方法用来设置一个服务器端的通道实现
  * `public <T> B option(ChannelOption<T> option, T value)`:用来给ServerChannel添加配置
  * `public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value)`:用来给接收到的通道添加配置
  * `public ServerBootstrap childHandler(ChannelHandler childHandler)`:该方法用来设置业务处理类（自定义的handler）
  * `public ChannelFuture bind(int inetPort)`:该方法用于服务器端，用来设置占用的端口号
  * `public ChannelFuture connect(String inetHost, int inetPort)`:该方法用于客户端，用来连接服务器端。

## 2、Future、ChannelFuture

* Netty中所有的IO操作都是异步的，不能立刻得知消息是否被正确处理。但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通过Future和ChannelFutures，他们可以注册一个监听，当操作执行成功或者失败时监听会自动触发注册的监听事件
* 常见的方法有
  * `Channel channel();`:返回当前正在尽心IO操作的通道
  * `ChannelFuture sync()`:等待异步操作执行完毕

## 3、Channel

* Netty网络通信的组件，能够用于执行网络IO操作
* 通过Channel可获得当前网络连接的通道的状态
* 通过Channel可获得，网络连接的配置参数（例如接收缓冲区的大小）
* Channel提供异步的网络I/O操作（如建立连接，读写，绑定端口），异步调用意味着任何I/O调用都将立即返回，并且不保证在调用结束时所请求的I/O操作已完成
* 调用立即返回一个ChannelFuture实例，通过注册监听器到ChannelFuture上，可以I/O操作成功、失败或取消时回调通知调用方
* 支持关联IO操作与对应的处理程序
* 不同协议、不同的阻塞类型的连接都有不同的Channel类型与之对应，常用的Channel类型：
  * NioSocketChannel，异步的客户端TCP Socket
  * NioServerSocketChannel，异步的服务器端TCP Socket连接
  * NioDatagramChannel，异步的UDP连接
  * NioSctpChannel，异步的客户端Sctp连接
  * NioSctpServerChannel，异步的Sctp服务器端连接

## 4、Selector

* Netty基于Selector对象实现I/O多路复用，通过Selector一个线程可以监听多个连接的channel时间
* 当向一个Selector中注册Channel后，Selector内部的机制就可以自动不断的查询（Selector）这些注册的Channel是否有已就绪的I/O时间（例如可读，可写，网络连接完成等），这样程序就可以很简单地使用一个线程高效地管理多个Channel

## 5、ChannelHandler及其实现类

* ChannelHandler是一个接口，处理I/O事件或拦截I/O操作，并将其转发到其ChannelPipeline（业务处理链中的下一个处理程序）

* CHannelHandler本身没有提供很多方法，因为这个接口有许多的方法需要实现，方便使用期间，可以继承它的子类

* 相关接口和类一览图

  ![image-20221122234510250](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221122234510250.png)

* 我们经常需要自定义Handler类去继承ChannelInboundHandlerAdapter，然后通过重写相应方法实现业务逻辑，我们接下来看看一般都需要重写哪些方法

  ```java
  public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelRegistered();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelUnregistered();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       * 通道就绪事件
       */
      @Skip
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelActive();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelInactive();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       * 通道读取数据事件
       */
      @Skip
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          ctx.fireChannelRead(msg);
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelReadComplete()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       * 数据读取完毕事件
       */
      @Skip
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelReadComplete();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
          ctx.fireUserEventTriggered(evt);
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireChannelWritabilityChanged()} to forward
       * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
          ctx.fireChannelWritabilityChanged();
      }
  
      /**
       * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
       * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
       *
       * Sub-classes may override this method to change behavior.
       */
      @Skip
      @Override
      @SuppressWarnings("deprecation")
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
              throws Exception {
          ctx.fireExceptionCaught(cause);
      }
  }
  ```

## 6、Pipeline和ChannelPipeline

* ChannelPipeline时一个Handler的集合，它负责处理和拦截inbound或者outbound的事件和操作，相当于一个贯穿Netty的链。（也可以这样理解：ChannelPipeline时一个保存ChannelHandler的List，用于处理和拦截Channel的入站事件和出站事件操作）

* ChannelPipeline实现了一种高级形式的拦截过滤器模式，使用户可以完全控制事件的处理方式，以及Channel中各个的ChannelHandler如何相互交互

* Pipeline和ChanelPipeline

  * 在Netty中每个Channel都有且仅有一个ChannelPipeline与之对应，他们组成关系如下

    ![image-20221126144157289](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221126144157289.png)

    * 一个Channel包含一个ChannelPipeline，而ChannelPipeline中又维护了一个由ChannelHandlerContext组成的双向链表，并且每个ChannelHandlerContext中又关联着一个ChannelHandler
    * 入站事件和出站事件在一个双向链表中，入站事件会从链表head往后传递到最后一个入站的handler，出站事件会从链表tail往前传递到最前一个出站的handler，两种类型的handler互不干扰。

* 常用方法
  * `ChannelPipeline addFirst(ChannelHandler... handlers);`:把一个业务处理类（handler）添加到链表中的第一个位置
  * `ChannelPipeline addLast(ChannelHandler... handlers);`:把一个业务处理类（handler）添加到链中的最后一个位置。

## 7、ChannelHandlerContext

* 保存了Channel相关的所有上下文信息，同时关联一个ChannelHandler对象
* 即ChannelHandlerContext中包含一个具体的事件处理器ChannelHandler，同时ChannelHandlerContext中也绑定了对应的pipeline和Channel的信息，方便对ChannelHandler进行调用
* 常用方法
  * `ChannelFuture close();`:关闭通道
  * `ChannelOutboundInvoker flush();`:刷新
  * `ChannelFuture writeAndFlush(Object msg);`:将数据写到ChannelPipeline中当前ChannelHandler的下一个ChannelHandler开始处理（出站）

## 8、ChannelOption

* Netty在创建Channel实例后，一般都需要设置ChannelOption参数
* ChannelOption参数如下
  * ChannelOption.SO_BACKLOG:对应TCP/IP协议listen函数中的backlog参数，用来初始化服务器可连接队列大小。服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
  * ChannelOption.SO_KEEPALIVE:一直保持连接活动状态

## 9、EventLoopGroup和其实现类NioEventLoopGroup

* EventLoopGroup是一组EventLoop的抽象，Netty为了更好的利用多核CPU资源，一般会有多个EventLoop同时工作，每个EventLoop维护着一个Selector实例。
* EventLoopGroup提供next接口，可以从组里面按照一定规则获取其中一个EventLoop来处理任务。在Netty服务器端编程中，我们一般都需要提供两个EventLoopGroup。例如：BossEventLoopGroup和WorkEventLoopGroup。
* 通常一个服务端口即一个ServerSocketChannel对应一个Selector和一个EventLoop线程。BossEventLoop负责接收客户端的连接并将SocketChannel交给WOrkerEventLoopGroup来进行IO处理

![image-20221126160056863](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221126160056863.png)

* 常用方法
  * `Future<?> shutdownGracefully();`:关闭线程

## 10、Unpooled类

* Netty提供一个专门用来操作缓冲区（即Netty的数据容器）的工具类

* 常用方法

  * `public static ByteBuf copiedBuffer(CharSequence string, Charset charset)`:通过给定的数据和字符编码返回一个ByteBuf对象

* 举例1

  ```java
  package com.smc.netty.buf;
  
  import io.netty.buffer.ByteBuf;
  import io.netty.buffer.Unpooled;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class NettyByteBuf01 {
      public static void main(String[] args) {
          /**
           * 创建一个ByteBuf
           * 说明
           *  1、创建对象，该对象包含一个数据ar，是一个byte[10]
           *  2、在netty的buffer中，不需要使用flip进行反转，因为底层维护了readerindex和writerIndex
           *  3、通过readerindex和writerIndex和capacity，将buffer分成三个区域
           *  0-readerindex已经读取的区域
           *  readerindex-writerIndex，可读的区域
           *  writeIndex-capacity 可写的区域
           */
          ByteBuf buffer = Unpooled.buffer(10);
  
          for (int i = 0; i < 10; i++) {
              buffer.writeByte(i);
          }
  
          System.out.println("capacity= " + buffer.capacity());
  
          for (int i = 0; i < buffer.capacity(); i++) {
              System.out.println(buffer.getByte(i));
              System.out.println(buffer.readByte());
          }
      }
  }
  
  ```

* 实例2

  ```java
  package com.smc.netty.buf;
  
  import io.netty.buffer.ByteBuf;
  import io.netty.buffer.Unpooled;
  import io.netty.util.CharsetUtil;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class NettyByteBuf02 {
      public static void main(String[] args) {
          ByteBuf buf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.ISO_8859_1);
          //使用相关方法
          if(buf.hasArray()){
              byte[] content = buf.array();
              //将content转成字符串
              System.out.println(new String(content, CharsetUtil.ISO_8859_1));
  
              System.out.println(buf);
              System.out.println(buf.arrayOffset());
              System.out.println(buf.readerIndex());
              System.out.println(buf.writerIndex());
              System.out.println(buf.capacity());
              System.out.println(buf.readableBytes());//可读的字节数
              //使用for取出各字节
              for (int i = 0; i < buf.readableBytes(); i++) {
                  System.out.println((char) buf.getByte(i));
              }
              System.out.println(buf.getCharSequence(0, 4, CharsetUtil.ISO_8859_1));
              System.out.println(buf.getCharSequence(4, 6, CharsetUtil.ISO_8859_1));
  
          }
      }
  }
  
  ```

# 十、Netty应用实例：群聊系统

* 实例要求

  * 编写一个Netty群聊系统，实现服务器和客户端之间的数据简单通讯（非阻塞）
  * 实现多人群聊
  * 服务器端：可以监测用户上线，离线，并实现消息转发功能
  * 客户端：通过channel可以无阻塞发送消息给其他所有用户，同时可以接受其它用户发送的消息（由服务器转发得到）

* 服务器端

  ```java
  package com.smc.netty.groupchat;
  
  import io.netty.bootstrap.ServerBootstrap;
  import io.netty.channel.ChannelFuture;
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelOption;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.nio.NioEventLoopGroup;
  import io.netty.channel.socket.SocketChannel;
  import io.netty.channel.socket.nio.NioServerSocketChannel;
  import io.netty.handler.codec.string.StringDecoder;
  import io.netty.handler.codec.string.StringEncoder;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class GroupChatServer {
      private int port;
  
      public GroupChatServer(int port) {
          this.port = port;
      }
  
      public void run(){
          //创建两个线程组
          NioEventLoopGroup boosGroup = new NioEventLoopGroup();
          NioEventLoopGroup workerGroup = new NioEventLoopGroup();
  
          ServerBootstrap bootstrap = new ServerBootstrap();
  
          try {
              bootstrap.group(boosGroup,workerGroup)
                      .channel(NioServerSocketChannel.class)
                      .option(ChannelOption.SO_BACKLOG,128)
                      .childOption(ChannelOption.SO_KEEPALIVE,true)
                      .childHandler(new ChannelInitializer<SocketChannel>() {
                          @Override
                          protected void initChannel(SocketChannel ch) throws Exception {
                              //获取到pipeline
                              ChannelPipeline pipeline = ch.pipeline();
                              //向pipeline加入解码器的handler
                              pipeline.addLast("decoder",new StringDecoder());
                              //向pipeline加入编码器的handler
                              pipeline.addLast("encoder",new StringEncoder());
                              //加入自己的handler
                              pipeline.addLast(new GroupChatServerHandler());
                          }
                      });
              System.out.println("netty 服务器启动");
              ChannelFuture channelFuture = bootstrap.bind(port).sync();
              //监听关闭
              channelFuture.channel().closeFuture().sync();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }finally {
              boosGroup.shutdownGracefully();
              workerGroup.shutdownGracefully();
  
          }
  
      }
  
      public static void main(String[] args) {
          new GroupChatServer(7000).run();
      }
  }
  
  
  
  
  //////////////////////////////////////////////////////////////
  
  
  
  
  package com.smc.netty.groupchat;
  
  import io.netty.channel.Channel;
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.SimpleChannelInboundHandler;
  import io.netty.channel.group.ChannelGroup;
  import io.netty.channel.group.DefaultChannelGroup;
  import io.netty.util.concurrent.GlobalEventExecutor;
  
  import java.text.SimpleDateFormat;
  import java.util.Date;
  
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
      /**
       * 定义channel组，管理所有的channel
       * GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
        */
      private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  
  
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
      /**
       * handlerAdded表示连接建立，一旦连接，第一个被执行
       * 将当前channel加入channelGroup
       * @param ctx
       * @throws Exception
       */
      @Override
      public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
          Channel channel = ctx.channel();
          /**
           * 将该客户加入聊天的信息推送给其他在线的客户端
           * 该方法会讲channelGroup中所有的channel遍历，并发送消息，我们不需要遍历
           */
          channels.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天"+sdf.format(new Date())+"\n");
          channels.add(channel);
  
      }
  
      /**
       * 断开连接,将客户离开信息推送给当前在线客户端
       * @param ctx
       * @throws Exception
       */
      @Override
      public void handlerRemoved(ChannelHandlerContext ctx)  {
          System.out.println(channels.writeAndFlush("【客户端】" + ctx.channel().remoteAddress() + "离开了"+sdf.format(new Date())+"\n"));
          System.out.println(channels.size());
      }
  
      /**
       * 表示channel处于活动状态，提示xx上线
       * @param ctx
       * @throws Exception
       */
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          System.out.println(ctx.channel().remoteAddress() + "上线了");
      }
  
  
  
      /**
       * 表示channel处于不活动的状态，提示xx离线了
       * @param ctx
       * @throws Exception
       */
      @Override
      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
          System.out.println(ctx.channel().remoteAddress() + "离线了");
      }
  
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
          //获取到当前channel
          Channel channel = ctx.channel();
          //这是我们遍历channelGroup，根据不同的情况，回送不同的消息
          channels.forEach(ch->{
              if (channel!=ch){
                  ch.writeAndFlush("【客户端】"+channel.remoteAddress()+":"+sdf.format(new Date())+"发送了消息："+msg+"\n");
              }else{
                  ch.writeAndFlush("【自己】"+sdf.format(new Date())+"发送了消息:"+msg+"\n");
              }
          });
  
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          //关闭通道
          ctx.close();
      }
  }
  
  ```

* 客户端

  ```java
  package com.smc.netty.groupchat;
  
  import io.netty.bootstrap.Bootstrap;
  import io.netty.channel.Channel;
  import io.netty.channel.ChannelFuture;
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.nio.NioEventLoopGroup;
  import io.netty.channel.socket.SocketChannel;
  import io.netty.channel.socket.nio.NioSocketChannel;
  import io.netty.handler.codec.string.StringDecoder;
  import io.netty.handler.codec.string.StringEncoder;
  
  import java.util.Scanner;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class GroupChatClient {
      private final String host;
      private final int port;
  
      public GroupChatClient(String host, int port) {
          this.host = host;
          this.port = port;
      }
  
      public void run(){
          NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
  
          Bootstrap bootstrap = new Bootstrap();
  
          try {
              bootstrap.group(eventExecutors)
                      .channel(NioSocketChannel.class)
                      .handler(new ChannelInitializer<SocketChannel>() {
                          @Override
                          protected void initChannel(SocketChannel ch) throws Exception {
                              ChannelPipeline pipeline = ch.pipeline();
                              pipeline.addLast("decoder",new StringDecoder());
                              pipeline.addLast("encoder",new StringEncoder());
                              pipeline.addLast(new GroupChatClientHandler());
  
                          }
                      });
              ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
              //得到channel
              Channel channel = channelFuture.channel();
              System.out.println("-----" + channel.localAddress() + "-----");
              //客户端需要输入信息，创建一个扫描器
              Scanner scanner = new Scanner(System.in);
              while (scanner.hasNextLine()){
                  String msg = scanner.nextLine();
                  //通过channel发送到服务器端
                  channel.writeAndFlush(msg+"\r\n");
              }
  
  
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              eventExecutors.shutdownGracefully();
          }
  
  
      }
  
      public static void main(String[] args) {
          new GroupChatClient("127.0.0.1",7000).run();
      }
  }
  
  ////////////////////////////////////////////////////////////////////////////////
  package com.smc.netty.groupchat;
  
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.SimpleChannelInboundHandler;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
  
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
          System.out.println(msg.trim());
      }
  }
  
  ```

# 十一、Netty心跳检测机制案例

* 编写一个Netty心跳检测机制案例，当服务器超过3秒没有读时，就提示读空闲
* 当服务器超过5秒没有写操作时，就提示写空闲
* 实现当服务器超过7秒没有读或者写操作时，就提示读写空闲

```java
package com.smc.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Date 2022/11/26
 * @Author smc
 * @Description:
 */
public class MyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//在boosGroup增加一个日志处理器
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 提供一个netty提供的IdleStateHandler
                             * 说明
                             *  1、IdleStateHandler是Netty提供的处理空闲状态的处理器
                             *  2、long readerIdleTime ：表示多长事件没有读，就会发送一个心跳检测包，检测是否还是连接状态
                             *  3、long writerIdleTime ： 表示多长事件没有写，就会发送一个心跳检测爆检测是否连接
                             *  4、long allIdleTime ： 表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                             *  5、当 IdleStateEvent触发后，就会传递给管道的下一个handler去处理，通过触发下一个handler的userEventTiggered
                             *      在该方法中处理IdleStateEvent（读空闲，写空闲，读写空闲）
                             *
                             */

                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }

                    });
            //启动服务器
            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}



```

```java
package com.smc.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import static io.netty.handler.timeout.IdleState.*;


/**
 * @Date 2022/11/26
 * @Author smc
 * @Description:
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //将evt向下转型IdleStateEvent
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "--超时事件--" + eventType);
            //可以执行相关操作
//            ctx.channel().close();
        }
    }
}

```

# 十二、WebSocket编程实现服务器和客户端长连接

* Http协议是无状态的，浏览器和服务器间的请求响应一次，下一次会重新创建连接。
* 要求：实现基于webSocket的长连接的全双工交互
* 改变Http协议多次请求的约束，实现长连接了，服务器可以发送消息给浏览器
* 客户端浏览器和服务器端会相互感知，，比如服务器关闭了，浏览器会感知，同样浏览器关闭了，服务器会感知。

* 服务器

  ```java
  package com.smc.netty.webSocket;
  
  import com.smc.netty.heartbeat.MyServerHandler;
  import io.netty.bootstrap.ServerBootstrap;
  import io.netty.channel.ChannelFuture;
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelOption;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.nio.NioEventLoopGroup;
  import io.netty.channel.socket.nio.NioServerSocketChannel;
  import io.netty.channel.socket.nio.NioSocketChannel;
  import io.netty.handler.codec.http.HttpObjectAggregator;
  import io.netty.handler.codec.http.HttpServerCodec;
  import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
  import io.netty.handler.logging.LogLevel;
  import io.netty.handler.logging.LoggingHandler;
  import io.netty.handler.stream.ChunkedWriteHandler;
  import io.netty.handler.timeout.IdleStateHandler;
  
  import java.util.concurrent.TimeUnit;
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class MyServer {
      public static void main(String[] args) {
          NioEventLoopGroup bossGroup = new NioEventLoopGroup();
          NioEventLoopGroup workerGroup = new NioEventLoopGroup();
          ServerBootstrap bootstrap = new ServerBootstrap();
          try {
              bootstrap.group(bossGroup, workerGroup)
                      .channel(NioServerSocketChannel.class)
                      .handler(new LoggingHandler(LogLevel.INFO))//在boosGroup增加一个日志处理器
                      .option(ChannelOption.SO_BACKLOG, 128)
                      .childOption(ChannelOption.SO_KEEPALIVE, true)
                      .childHandler(new ChannelInitializer<NioSocketChannel>() {
                          @Override
                          protected void initChannel(NioSocketChannel ch) throws Exception {
                              ChannelPipeline pipeline = ch.pipeline();
                              /**
                               * 因为居于http协议，使用http的编码和解码器
                               */
                              pipeline.addLast(new HttpServerCodec());
                              //是以块方式写，添加ChunkedWriteHandler处理器
                              pipeline.addLast(new ChunkedWriteHandler());
                              /**
                               * 说明
                               *  1、http数据在传输过程中是分段的，HttpObjectAggregator，就是可以将多个短聚合
                               *  2、这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                               */
                              pipeline.addLast(new HttpObjectAggregator(8192));
                              /**
                               * 说明
                               *  1、对应websocket，它的数据以帧（frame）形式传递
                               *  2、可以看到WebSocketFrame，下面有六个子类
                               *  3、浏览器请求时：ws://localhost：7000/xxx表示请求的uri
                               *  4、WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                               *  5、长连接是通过状态 101 来判断连接
                               */
                              pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
  
                              //自定义handler,处理业务逻辑
                              pipeline.addLast(new MyTextWebSocketframeHandler());
                          }
  
                      });
              //启动服务器
              ChannelFuture channelFuture = bootstrap.bind(7000).sync();
              channelFuture.channel().closeFuture().sync();
  
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              bossGroup.shutdownGracefully();
              workerGroup.shutdownGracefully();
          }
      }
  }
  
  ```

  ```java
  package com.smc.netty.heartbeat;
  
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.ChannelInboundHandlerAdapter;
  import io.netty.handler.timeout.IdleStateEvent;
  
  import static io.netty.handler.timeout.IdleState.*;
  
  
  /**
   * @Date 2022/11/26
   * @Author smc
   * @Description:
   */
  public class MyServerHandler extends ChannelInboundHandlerAdapter {
      /**
       *
       * @param ctx 上下文
       * @param evt 事件
       * @throws Exception
       */
      @Override
      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
          if (evt instanceof IdleStateEvent){
              //将evt向下转型IdleStateEvent
              IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
              String eventType = null;
              switch (idleStateEvent.state()){
                  case READER_IDLE:
                      eventType="读空闲";
                      break;
                  case WRITER_IDLE:
                      eventType="写空闲";
                      break;
                  case ALL_IDLE:
                      eventType="读写空闲";
                      break;
              }
              System.out.println(ctx.channel().remoteAddress() + "--超时事件--" + eventType);
              //可以执行相关操作
  //            ctx.channel().close();
          }
      }
  }
  
  ```

* 前端

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <script>
      var socket;
      //判断当前浏览器是否支持websocket
      if(window.WebSocket){
          //go on
          socket=new WebSocket("ws://localhost:7000/hello");
          //相当于channelReado，ev收到服务器端回送的消息
          socket.onmessage=function (ev){
              var rt = document.getElementById("responseText");
              rt.value=rt.value+"\n"+ev.data;
          }
          //相当于连接开启
          socket.onopen=function (ev){
              var rt = document.getElementById("responseText");
              rt.value="连接开启了";
          }
  
          socket.onclose=function (ev){
              var rt = document.getElementById("responseText");
              rt.value=rt.value+"\n"+"连接关闭了..";
          }
  
      }else{
          alert("当前浏览器不支持websocket")
      }
      //发送消息到服务器
      function send(message){
          if(!window.socket)
              return;
          if (socket.readyState==WebSocket.OPEN){
              //通过socket发送消息
              socket.send(message);
          }else {
              alert("连接未开启")
          }
      }
  </script>
  <body>
      <form onsubmit="return false">
          <textarea name="message" style="height: 300px;width: 300px"></textarea>
          <input type="button" value="发送消息" onclick="send(this.form.message.value)">
          <textarea id="responseText" style="height: 300px;width: 300px"></textarea>
          <input type="button" value="清空内容" onclick="document.getElementById('responseText').value=''">
      </form>
  </body>
  </html>
  ```

# 十三、编码和解码的基本介绍

* 编写网络应用程序时，因为数据在网络中传输的都是二进制字节码数据，在发送数据时需要编码，接收数据时需要解码![image-20221127140344791](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221127140344791.png)
* codec（编解码器）的组成部分有两个：decoder（解码器）和encoder（编码器）。encoder负责把业务数据转换成字节码数据，decoder负责把字节码数据转换成业务数据。

## 1、Netty本身的编码解码的机制和问题分析

* Netty自身提供了一些codec（编解码器）
* Netty提供的编码器
  * StringEncoder，对字符串数据进行编码
  * ObjectEncoder，对Java对象进行编码
* Netty提供的解码器
  * StringDecoder，对字符串数据进行解码
  * ObjectDecoder，对Java对象进行解码
* Netty本身自带的ObjectDecoder和ObjectEncoder可以用来实现POJO对象或各种业务对象的编码和解码，底层使用的仍是Java序列化技术，而Java序列化技术本身效率就不高，存在以下问题
  * 无法跨语言
  * 序列化后体积太大，是二进制编码的5倍多
  * 序列化性能太低
* 引出新的解决方案【Google和Protobuf】

## 2、Protobuf

### 2.1 Protobuf基本介绍和使用示意图

* Protobuf是Google发布的开源项目，全称Google Protocol Buffer，是一种轻便高效的结构化数据存储格式，可以用于结构化数据串行化，或者说序列化。它很适合做数据存储或RPC【远程过程调用remote procedure call】数据交换格式

* 参考文档：<https://developers.google.com/protocol-buffers/docs/proto>

* Protobuf是以message的方式来管理数据的

* 支持跨平台、跨语言，即【客户端和服务器端可以是不同的语言编写的】（支持目前绝大多数语言）

* 高性能，高可靠性

* 使用protobuf编译器能自动生成代码，Protobuf是将类的定义使用.proto文件进行描述。说明，在idea中编写.proto文件时，会自动提示是否下载.protot编写插件，可以让语法高亮

* 然后通过protoc.exe编译器根据.proto自动生成.java文件

* protobuf使用示意图

  ![image-20221127145538816](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221127145538816.png)

### 2.2 Protobuf快速入门实例1

* 客户端可以发送一个Student Pojo对象到服务器（通过Protobuf编码）
* 服务端能接收Student PoJo对象，并显示信息（通过Protobuf解码）

#### 2.2.1 protobuf安装

* 在mac执行安装protobuf

  ```shell
  brew install protobuf
  
  #查看版本
  protobuf --version
  ```

* idea安装插件protobuf

* 编写proto文件

  ```protobuf
  syntax = "proto3";//  版本
  option java_outer_classname="StudentPOJO";//生成的外部类名，同时也是文件名
  
  //protobuf 使用message管理数据
  message Student{ //会在StudentPOJO外部类生成一个内部类Student，他是真正发送的POJO对象
    int32 id=1;//Student类中有一个属性名字为id 类型为 int32(protobuf)类型，1表示属性序号，不是值
     string name=2;
  }
  ```

* 执行命令转换为java文件pojo类型

  ```shell
  #proto -I=当前文件的路径 --java_out=编译输出的路径 .proto文件的名称
  smc@linjianguodeMBP codec % protoc -I=./ --java_out=./ Student.proto
  
  ```

#### 2.2.2 服务器

```java
package com.smc.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @Date 2022/11/15
 * @Author smc
 * @Description:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建BossGroup 和WorkGroup
         * 说明：
         * 1、创建两个线程bossGroup和workerGroup
         * 2、bossGroup只是处理连接请求，真正和客户端业务处理会交给workGroup完成
         * 3、两个都是无限循环
         * 4、bossGroup和workerGroup含有的子线程（NioEventLoop）的个数：默认实际cpu核数*2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             *  在pipeline中加入ProtoBufDecoder
                             *  指定对哪种对象进行解码
                             */
                            pipeline.addLast("decoder",
                                    new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));

                            pipeline.addLast(new NettyServerHandler());
                        }
                    });//给我们的workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器已经准备好了");

            //绑定一个端口并且同步，生成一个ChannelFuture
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });
            //关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

```

```java
package com.smc.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:我们自定义一个Handler 需要继续netty规定好的某个HandlerAdapter
 * 这时我们自定义一个Handler，才能称为Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    /**
     * 读取实际数据（这里我们可以读取客户端发送的信息）
     * ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
     * objet msg：就是客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        //读取从客户端发送的StudentPOJO.student
        System.out.println("客户端发送的数据id=" + msg.getId() + " 名字=" + msg.getName());

    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * writeAndFlush write+flush
         * 将数据写入到缓存，并刷新
         * 一般讲，我们对这个发送的数据进行编码
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

```

#### 2.2.3 客户端 

```java
package com.smc.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端只需要一个事件循环组
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        /**
         * 创建客户端启动对象
         * 注意客户端使用的不是ServerBootstrap，而是 Bootstrap
         */
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置相关参数
            bootstrap.group(clientGroup) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //在pipeline中加入protoBufEncoder
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok...");

            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            clientGroup.shutdownGracefully();
        }

    }
}

```

```java
package com.smc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**#
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    //当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发生一个Student对象到服务器
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("张三").build();
        ctx.writeAndFlush(student);
    }

    //有通道读取事件时，会触发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

```

### 2.3 Protobuf快速入门实例2

* 客户端可以随机发送Student PoJo/WorkerPoJo对向到服务器
* 服务器端能接收Student PoJo/WorkerPoJo对象（需要判断哪种类型），并显示信息

#### 2.3.1 写proto类，并转换成javaPOJO

```proto
syntax = "proto3";
option optimize_for = SPEED;//加快解析
//option java_package = "com.smc.netty.codec2";//指定生成到哪个包下
option java_outer_classname="MyDataInfo";//外部类名

//protobuf 可以使用gmessage 管理其他的message

message MyMessage{
  //定义个枚举类型
  enum DataType{
    StudentType=0; //在proto3 要求enum编号从0开始
    WorkerType=1;
  }
  //用data_tyoe来标识传的是哪个枚举类型
  DataType data_type=1;
  //表示每次枚举类型最多只能出现其中的一个，节省控件
  oneof dataBody{
    Student student=2;
    Worker worker=3;
  }
}

message Student{
  int32 id=1;//Student类的属性
  string name = 2;
}

message Worker{
  string name = 1;
  int32 age=2;
}
```

```shell
#执行转换
smc@linjianguodeMBP codec2 % protoc -I=./ --java_out=./ MyMessage.proto

```

#### 2.3.2 服务器

```java
package com.smc.netty.codec2;

import com.smc.netty.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @Date 2022/11/15
 * @Author smc
 * @Description:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建BossGroup 和WorkGroup
         * 说明：
         * 1、创建两个线程bossGroup和workerGroup
         * 2、bossGroup只是处理连接请求，真正和客户端业务处理会交给workGroup完成
         * 3、两个都是无限循环
         * 4、bossGroup和workerGroup含有的子线程（NioEventLoop）的个数：默认实际cpu核数*2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             *  在pipeline中加入ProtoBufDecoder
                             *  指定对哪种对象进行解码
                             */
                            pipeline.addLast("decoder",
                                    new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));

                            pipeline.addLast(new NettyServerHandler());
                        }
                    });//给我们的workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器已经准备好了");

            //绑定一个端口并且同步，生成一个ChannelFuture
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });
            //关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

```

```java
package com.smc.netty.codec2;

import com.smc.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:我们自定义一个Handler 需要继续netty规定好的某个HandlerAdapter
 * 这时我们自定义一个Handler，才能称为Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    /**
     * 读取实际数据（这里我们可以读取客户端发送的信息）
     * ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
     * objet msg：就是客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        //读取从客户端发送的StudentPOJO.student
        if(msg.getDataType() == MyDataInfo.MyMessage.DataType.StudentType){
            System.out.println("客户端发送的数据 id=" + msg.getStudent().getId() + " 名字=" + msg.getStudent().getName());
        }else{
            System.out.println("客户端发送的数据 name=" + msg.getWorker().getName() + " 年龄=" + msg.getWorker().getAge());
        }


    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * writeAndFlush write+flush
         * 将数据写入到缓存，并刷新
         * 一般讲，我们对这个发送的数据进行编码
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

```

#### 2.3.3 客户端

```java
package com.smc.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端只需要一个事件循环组
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        /**
         * 创建客户端启动对象
         * 注意客户端使用的不是ServerBootstrap，而是 Bootstrap
         */
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置相关参数
            bootstrap.group(clientGroup) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //在pipeline中加入protoBufEncoder
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok...");

            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            clientGroup.shutdownGracefully();
        }

    }
}

```

```java
package com.smc.netty.codec2;

import com.smc.netty.codec.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    //当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发哦送Student 或者Worker对象
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
        if (random==0){
            //发送一个Student对象
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("李四").build()).build();
        }else{
            //发送一个worker对象
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setName("王五").setAge(23).build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    //有通道读取事件时，会触发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

```

# 十四、Netty入站与出站机制

## 1、基本说明

* netty的组件设计：Netty的主要组件有Channel、EventLoop、ChannelFuture、ChannelHandler、ChannelPipe等
* ChannelHandler充当了处理入站和出站数据的应用程序逻辑的容器。例如：实现ChannelInboundHandler接口（或ChannelInboundHandlerAdapter），你就可以接收入站时间和数据，这些数据会被业务逻辑处理。当要给客户端发送响应时，也可以从ChannelInboundHandler冲刷数据。业务逻辑通常写在一个或者多个ChannelInboundHandler中。ChannelOutboundHandler原理一样，只不过它是用来处理出站数据的。
* ChannelPipeline提供了ChannelHandler链的容器。以客户端应用程序为力，如过时间的运动方向是从客户端到服务端，那么我们称这些时间为出站的，即客户端发送给给服务端的数据会通过pipeline中的一系列ChannellOutboundHandler，并被这些Handler处理，反之则称之为入站的。（以服务端为例，则服务端到客户端为出站，客户端到服务端为入）

## 2、编码解码器

* 当Netty发送或者接收一个消息的时候，就将会发生一次数据转换。入站消息会被解码：从字节转换为另一种格式（比如java对象）；如果是出站消息，他会被编码成字节。
* Netty提供一列实用的编解码器，他们都实现了ChannelInboundHandler或者ChannelOutboundHandler接口。在这些类中，channelRead方法已经被重写了。以入站为例，对于每个从入站Channel读取的消息，这个方法会被调用。随后，它将调用由解码器所提供的decode()方法进行解码，并将已经解码的字节转发给ChannelPipeline中的下一个ChannelInboundHandler。

## 3、解码器-ByteToMessageDecoder

* 关系继承图

  ![image-20221127181202280](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221127181202280.png)

* 由于不可能知道远程节点是否会一次性发送给一个完整的信息，tcp有可能出现粘包拆包的问题，这个类会对入站数据进行缓冲，知道它准备好被处理。

## 4、Netty的handler的调用机制

* 实例要求
  * 使用自定义的编码器和解码器来说明Netty的handler调用机制
  * 客户端发送long->服务器
  * 服务器发送long->客户端

### 4.1 服务器端

```java
package com.smc.netty.inouthandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workderGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(bossGroup,workderGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            ChannelFuture ch = serverBootstrap.bind(7000).sync();

            ch.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workderGroup.shutdownGracefully();
        }

    }
}

```

* EventGroup处理

  ```java
  package com.smc.netty.inouthandler;
  
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.socket.SocketChannel;
  
  /**
   * @Date 2022/11/27
   * @Author smc
   * @Description:
   */
  public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
          ChannelPipeline pipeline = ch.pipeline();
  
          //入站的handler进行解码MyByteToLongDecoderHandler
          pipeline.addLast(new MyLongToByteHandler());
          pipeline.addLast(new MyByteToLongDecoderHandler());
          pipeline.addLast(new MyServerHandler());
      }
  }
  
  ```

  

* 服务器端自定义handler

  ```java
  package com.smc.netty.inouthandler;
  
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.SimpleChannelInboundHandler;
  
  /**
   * @Date 2022/11/27
   * @Author smc
   * @Description:
   */
  public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
  
      @Override
      public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
          ctx.channel().writeAndFlush(1234555555L);
      }
  
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
  
          System.out.println("从客户端" + ctx.channel().remoteAddress() + "读取到long" + msg);
          ctx.channel().writeAndFlush(888888888L);
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
          cause.printStackTrace();
          ctx.close();
      }
  }
  
  ```

### 4.2 客户端

```java
package com.smc.netty.inouthandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyClient {
    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new MyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 7000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}

```

* EventGroup处理，即pipeline

  ```java
  package com.smc.netty.inouthandler;
  
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.socket.SocketChannel;
  
  /**
   * @Date 2022/11/27
   * @Author smc
   * @Description:
   */
  public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
          ChannelPipeline pipeline = ch.pipeline();
  
          //加入出站的handler，对数据进行编码
          pipeline.addLast(new MyLongToByteHandler());
  
          //加入入站的decoder
          pipeline.addLast(new MyByteToLongDecoderHandler());
          //加入自定以handler，处理业务
          pipeline.addLast(new MyClientHandler());
      }
  }
  
  ```

* 客户端自定义handler

  ```java
  package com.smc.netty.inouthandler;
  
  import io.netty.channel.ChannelHandlerContext;
  import io.netty.channel.SimpleChannelInboundHandler;
  
  /**
   * @Date 2022/11/27
   * @Author smc
   * @Description:
   */
  public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
          System.out.println("从服务器读取到+" + msg);
      }
  
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
          System.out.println("MyClinetHandler 发送数据");
          ctx.writeAndFlush(1234567L);
      }
  }
  
  ```

### 4.3 编码器

```java
package com.smc.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyLongToByteHandler extends MessageToByteEncoder<Long> {
    //编码方法
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg=" + aLong);
        byteBuf.writeLong(aLong);
    }
}

```

### 4.4 解码器

```java
package com.smc.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyByteToLongDecoderHandler extends ByteToMessageDecoder {
    /**
     *decoder 会根据接收到的数据，被调用多次，知道确定更没有新的元素被添加到list或者是ByteBuf没有更多的可读字节为止
     * 如果list不为空 ，会将list的内容传递给下一个channelinboundhandle处理，该处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文对线
     * @param byteBuf 入站的ByteBUf
     * @param list 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //因为long8个字节，需要判断有8个字节，才能读取一个long
        if(byteBuf.readableBytes()>=8){
            list.add(byteBuf.readLong());
        }
    }
}

```

### 4.5 结论

* 不论解码器handler还是编码器handler即接收到的消息类型必须与待处理的消息类型一致，否则handler不会被执行
* 在解码器进行数据解码时，需要判断缓存去（byteBuf）的数据是否哦足够，否则接收到的结果会和期望结果可能不一致。

## 5、其他解码器编码器

### 5.1 ReplayingDecoder

* `public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder`
* ReplayingDecoder扩展了ByteToMessageDecoder类，使用这个类不必调用readableBytes()方法。参数S指定类用户状态管理的类型，其中Void代表不需要状态管理。

```java
package com.smc.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyByteToLongDecoderHandler extends ReplayingDecoder<Void> {
    /**
     *decoder 会根据接收到的数据，被调用多次，知道确定更没有新的元素被添加到list或者是ByteBuf没有更多的可读字节为止
     * 如果list不为空 ，会将list的内容传递给下一个channelinboundhandle处理，该处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文对线
     * @param byteBuf 入站的ByteBUf
     * @param list 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //在ReplayingDecoder不需要判断是否足够读取，内部会进行处理判断
//        if(byteBuf.readableBytes()>=8){
//            list.add(byteBuf.readLong());
//        }
        list.add(byteBuf.readLong());
    }
}

```

### 5.2 其他解码器

* LineBasedFrameDecoder：这个类在Netty内部也有使用，它使用行为控制字符（\n或者\r\n）座位分隔符来解析数据
* DelimiterBaseFrameDecoder：使用自定义的特殊字符作为消息的分隔符
* HttpObjectDecoder：一个Http数据的解码器
* LenthFieldBaseFrameDecoder：通过指定长度来表示整包休闲，遮掩个就可以自动给的处理粘包和半包消息。

### 5.3 其他编码器

 # 十五、Log4整合到Netty

* 在Maven中添加Log4j的依赖

  ```xml
  <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
  </dependency>
  <!--log4j2和slf4j的连接包 需要绑定到log4j2 core核心包上-->
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.12.1</version>
    <scope>test</scope>
  </dependency>
  <!--log4j-core中有log4j-api的依赖包 ,所以可以不添加og4j-api依赖-->
  <!--<dependency>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-api</artifactId>
              <version>2.11.1</version>
          </dependency>-->
  <!--slf4j 日志门面-->
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
  </dependency>
  <!--slf4j与log4j2实现所需要的日志依赖   start-->
  ```

  

* 配置Log4j，在resources/log4j2.xml

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
          <root level="DEBUG">
              <appender-ref ref="Console"/>
          </root>
      </loggers>
  </configuration>
  ```

# 十六、Tcp粘包和拆包

## 1、基本介绍

* TCP是面向连接、面向流的，提供高可靠性服务。收发两端（客户端和服务器）都要有一一成对的socket，因此，发送端为了将多个发送给接收端的爆，更有效的发给对方，使用了优化方法（Nagle算法），将多次间隔较小且数据量小的数据，合并成一个大的数据块，然后进行封包。这样做虽然提高了效率，但是接收端就难于分辨出完整的数据包了，因为面向流的通信是无消息保护边界的。

* 由于TCP无消息保护边界，需要在接收端处理消息边界问题，也就是我们所说的粘包、拆包问题

* TCP粘包拆包图解

  ![image-20221203232749848](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221203232749848.png)

## 2、TCP粘包和拆包解决方案

* 使用自定义协议+编解码器来解决
* ***关键就是要解决服务器端每次读取数据长度的问题***，这个问题解决，就不会出现服务器多读或少读数据的问题，从而避免TCP粘包、拆包

```java
package com.smc.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10跳数据hello，server
        for (int i = 0; i < 10; i++) {
            String mes="今天天气冷，吃火锅";
            byte[] content = mes.getBytes(CharsetUtil.UTF_8);
            int length = content.length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("客户端接收到信息如下：");
        System.out.println("长度=" + msg.getLen());
        System.out.println("内容=" + new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("客户端接收到消息报数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

```

```java
package com.smc.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        //接收到数据，并处理
        Integer len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下：");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息报数量=" + (++this.count));

        String s = UUID.randomUUID().toString();

        int length = s.getBytes(CharsetUtil.UTF_8).length;

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(s.getBytes(CharsetUtil.UTF_8));

       ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

```

# 十七、RPC调用流程分析

## 1、RPC基本介绍

* RPC（Remote Procedure Call）-远程过程调用，是一个计算机通信协议。该协议允许运行与一台计算机的程序调用另一台计算机的子程序，而程序员无需额外地为这个交互作用编程
* 两个或多个应用程序都分不在不同的服务器上，他们之间的调用都像是本地方法调用一样

![image-20221204180423536](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221204180423536.png)

* 常用的RPC框架有：比较知名的如阿里的Dubbo、google的gRPC、Go语言的rpcx、Apache的thrift，Spring的Spring Cloud

## 2、RPC调用流程说明

![image-20221204181150175](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221204181150175.png)

* 1）服务消费房（client）以本地调用方式调用服务
* 2）client stub接收到调用后负责将该方法、参数等封装成能够尽心个网络传输的消息题
* 3）client stub将消息进行编码并发送到服务端
* 4）server stub收到消息后进行解码
* 5）server stub根据解码结果调用本地的服务
* 6）本地服务执行并将结果进行编码并发送至消费方
* 7）server stub将返回导入结果进行编码并发送至消费方
* 8）client stub 接收到消息并进行解码
* 9）服务消费方（client）得到结果

小结：RPC的目标就是将2-8这些步骤都封装起来，用户无需关系这些细节，可以像调用本地方法一样即可完成远程服务调用

## 3、自己实现dubbo RPC（基于Netty）

### 3.1需求说明

* dubbo底层使用了Netty作为网络通讯框架，要求Netty实现一个简单的RPC框架
* 模仿dubbo，消费者和提供者约定接口和协议，消费者远程调用提供者的服务，提供者返回一个字符串，消费者打印提供者返回的数据。底层网络通信使用Netty 4.1.20

### 3.2 设计说明

* 创建一个接口，定义抽象方法。用户消费者和提供者之间的约定
* 创建一个提供者，该类需要监听消费者的请求，并按照约定返回数据
* 创建一个消费者，该类需要透明的调用自己不存在的方法，内部需要使用Netty请求提供者返回数据

![image-20221204182730065](/Users/smc/Desktop/smc/语言学习/java/netty/resource/image-20221204182730065.png)

