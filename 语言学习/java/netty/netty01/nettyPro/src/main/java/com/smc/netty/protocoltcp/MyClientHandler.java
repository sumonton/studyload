package com.smc.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/12/3
 * @Author smc
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10跳数据hello，server
        for (int i = 0; i < 10; i++) {
            String mes="今天天气冷，吃火锅";
            byte[] content = mes.getBytes(CharsetUtil.UTF_8);
            int length = content.length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("客户端接收到信息如下：");
        System.out.println("长度=" + msg.getLen());
        System.out.println("内容=" + new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("客户端接收到消息报数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
