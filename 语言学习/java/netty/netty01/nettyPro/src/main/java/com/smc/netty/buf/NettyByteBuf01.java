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
