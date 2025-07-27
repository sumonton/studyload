package com.smc.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:我们自定义一个Handler 需要继续netty规定好的某个HandlerAdapter
 * 这时我们自定义一个Handler，才能称为Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    /**
     * 读取实际数据（这里我们可以读取客户端发送的信息）
     * ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
     * objet msg：就是客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        //读取从客户端发送的StudentPOJO.student
        System.out.println("客户端发送的数据id=" + msg.getId() + " 名字=" + msg.getName());

    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * writeAndFlush write+flush
         * 将数据写入到缓存，并刷新
         * 一般讲，我们对这个发送的数据进行编码
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
