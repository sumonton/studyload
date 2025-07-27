package com.smc.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyLongToByteHandler extends MessageToByteEncoder<Long> {
    //编码方法
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg=" + aLong);
        byteBuf.writeLong(aLong);
    }
}
