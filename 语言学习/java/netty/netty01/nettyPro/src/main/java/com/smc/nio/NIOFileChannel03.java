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
        fileInputStream.close();
        fileOutputStream.close();
    }
}
