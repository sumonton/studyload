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
