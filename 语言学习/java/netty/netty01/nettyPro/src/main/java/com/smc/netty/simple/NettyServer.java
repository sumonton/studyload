package com.smc.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Date 2022/11/15
 * @Author smc
 * @Description:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建BossGroup 和WorkGroup
         * 说明：
         * 1、创建两个线程bossGroup和workerGroup
         * 2、bossGroup只是处理连接请求，真正和客户端业务处理会交给workGroup完成
         * 3、两个都是无限循环
         * 4、bossGroup和workerGroup含有的子线程（NioEventLoop）的个数：默认实际cpu核数*2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 可以使用一个集合管理SOcketChannel，再推送消息时，可以将业务加入到各个channel对应的NIOEventLoop的
                             * taskQueue或者scheduleTaskQueue
                             */
                            System.out.println("客户socketchannel hashcode=" + ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器已经准备好了");

            //绑定一个端口并且同步，生成一个ChannelFuture
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });
            //关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
