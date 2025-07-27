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
