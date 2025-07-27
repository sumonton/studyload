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
