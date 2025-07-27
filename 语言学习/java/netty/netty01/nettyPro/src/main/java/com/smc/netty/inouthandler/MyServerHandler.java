package com.smc.netty.inouthandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(1234555555L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("从客户端" + ctx.channel().remoteAddress() + "读取到long" + msg);
        ctx.channel().writeAndFlush(888888888L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
