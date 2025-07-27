package com.smc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:我们自定义一个Handler 需要继续netty规定好的某个HandlerAdapter
 * 这时我们自定义一个Handler，才能称为Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取实际数据（这里我们可以读取客户端发送的信息）
     * ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
     * objet msg：就是客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        /**
         * 比如这里我们又一个非常耗时长的任务->异步执行->提交该channel对应的NIOEventLoop到taskQueue中
         */
        Thread.sleep(1000*10);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
        /**
         *  解决方案1,用户自定以的普通任务
         */
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*10);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端3",CharsetUtil.UTF_8));


                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        });
        /**
         * 用户自定义定时任务->该任务是提交到scheduleTaskQueue中
         */
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端4", CharsetUtil.UTF_8));


                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        },5, TimeUnit.SECONDS);
        System.out.println("go on ..");
//        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
//        System.out.println("server ctx=" + ctx);
//        System.out.println("看看channel和pipeline的关系");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表
//
//        //将msg转成一个byteBuf,属于netty的Bytebuffer
//
//        ByteBuf buffer= (ByteBuf) msg;
//        System.out.println("客户端发送消失是：" + buffer.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + channel.remoteAddress());
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
