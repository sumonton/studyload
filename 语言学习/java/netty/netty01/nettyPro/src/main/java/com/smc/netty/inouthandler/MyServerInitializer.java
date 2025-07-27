package com.smc.netty.inouthandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //入站的handler进行解码MyByteToLongDecoderHandler
        pipeline.addLast(new MyLongToByteHandler());
        pipeline.addLast(new MyByteToLongDecoderHandler());
        pipeline.addLast(new MyServerHandler());
    }
}
