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
