package com.smc.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Date 2022/11/21
 * @Author smc
 * @Description:
 * 1、SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2、HttpObject客户端和服务器端相互通许的数据被封装成HttpObject
 */
public class HttpServerHanlder extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是httprequest请求
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址 " + ctx.channel().remoteAddress());

            //获取到
            HttpRequest req = (HttpRequest) msg;
            //获取uri
            URI uri = new URI(req.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico,不做响应");
                return;
            }
            //回复消息给浏览器【http协议】
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http响应，即httpreponse
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将构建好的response回应
            ctx.writeAndFlush(res);
        }
    }
}
