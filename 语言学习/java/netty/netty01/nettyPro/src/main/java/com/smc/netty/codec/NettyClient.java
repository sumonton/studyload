package com.smc.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端只需要一个事件循环组
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        /**
         * 创建客户端启动对象
         * 注意客户端使用的不是ServerBootstrap，而是 Bootstrap
         */
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置相关参数
            bootstrap.group(clientGroup) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //在pipeline中加入protoBufEncoder
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok...");

            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            clientGroup.shutdownGracefully();
        }

    }
}
