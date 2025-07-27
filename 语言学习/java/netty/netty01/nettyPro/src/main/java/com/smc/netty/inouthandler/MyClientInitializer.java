package com.smc.netty.inouthandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //加入出站的handler，对数据进行编码
        pipeline.addLast(new MyLongToByteHandler());

        //加入入站的decoder
        pipeline.addLast(new MyByteToLongDecoderHandler());
        //加入自定以handler，处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
