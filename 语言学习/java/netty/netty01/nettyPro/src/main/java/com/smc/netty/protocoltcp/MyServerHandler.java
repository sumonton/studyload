package com.smc.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        //接收到数据，并处理
        Integer len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下：");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息报数量=" + (++this.count));

        String s = UUID.randomUUID().toString();

        int length = s.getBytes(CharsetUtil.UTF_8).length;

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(s.getBytes(CharsetUtil.UTF_8));

       ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
