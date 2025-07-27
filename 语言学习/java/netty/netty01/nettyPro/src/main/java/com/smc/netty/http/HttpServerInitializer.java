package com.smc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Date 2022/11/21
 * @Author smc
 * @Description:
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 加入一个netty，提供的httpServerCodec codec=>[coder-decoder]
         * HttpServerCodec 说明
         * 1、HttpServerCodec是netty，提供的处理http的编码解码器
         * 2、增加一个自定义的处理器Handler
         */
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        pipeline.addLast("MyTestHttpServerHandler",new HttpServerHanlder());
    }
}
