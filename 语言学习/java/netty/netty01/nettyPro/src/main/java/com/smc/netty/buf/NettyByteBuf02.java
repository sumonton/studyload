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
