package com.smc.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String string = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("服务器接收到数据" + string);
        System.out.println("服务器接收到消息量" + ++(this.count));
        //服务器回送数据给客户端，回送给一个随机id
        ByteBuf buf = Unpooled.copiedBuffer(UUID.randomUUID().toString(),CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
