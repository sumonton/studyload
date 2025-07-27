package com.smc.netty.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;//上下文
    private String result;//返回的结果
    private String para;//客户端调用方法时，传入的参数

    /**
     * 与服务器连接创建后就会被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context=ctx;
    }

    /**
     * 收到服务器数据后，调用该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();//唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 被代理对线给调用，发送数据给服务器->wait->等待被唤醒（channelRead唤醒）->返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        //进行wait
        wait();//等待channelRead唤醒
        return result; //服务方返回的结果
    }

    void setPara(String para){
        this.para=para;
    }
}
