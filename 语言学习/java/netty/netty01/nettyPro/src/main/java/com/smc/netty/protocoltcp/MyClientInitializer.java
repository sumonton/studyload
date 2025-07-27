package com.smc.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyClientInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入编码器
        pipeline.addLast(new MyMessageEncoder());
        //加入解码器
        pipeline.addLast(new MyMessageDecoder());
        pipeline.addLast(new MyClientHandler());

    }
}
