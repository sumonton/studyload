package com.smc.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        //需要得到二进制字节码->MessageProtocol数据包对象
        int len = byteBuf.readInt();

        byte[] content = new byte[len];
        byteBuf.readBytes(content);
        //封装成MessageProtocol对象，放入list，传递给下一个handler

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        list.add(messageProtocol);

    }
}
