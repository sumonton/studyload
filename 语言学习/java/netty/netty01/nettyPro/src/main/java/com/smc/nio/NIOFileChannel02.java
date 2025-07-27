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
