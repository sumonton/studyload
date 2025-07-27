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
