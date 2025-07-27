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
