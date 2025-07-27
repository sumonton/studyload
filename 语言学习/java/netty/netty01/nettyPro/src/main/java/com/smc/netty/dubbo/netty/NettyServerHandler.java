package com.smc.netty.dubbo.netty;

import com.smc.netty.dubbo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        /**
         * 客户端在调用服务器的api时，我们需要定义一个消息
         * 比如我们要求每次发消息时都必须以某个字符串开头 "HelloService#hello"
         */
        if(msg.toString().startsWith("HelloService#hello#")){
            String result = new HelloServiceImpl().
                    hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
