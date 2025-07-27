package com.smc.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Date 2022/11/27
 * @Author smc
 * @Description:
 */
public class MyByteToLongDecoderHandler extends ReplayingDecoder<Void> {
    /**
     *decoder 会根据接收到的数据，被调用多次，知道确定更没有新的元素被添加到list或者是ByteBuf没有更多的可读字节为止
     * 如果list不为空 ，会将list的内容传递给下一个channelinboundhandle处理，该处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文对线
     * @param byteBuf 入站的ByteBUf
     * @param list 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //在ReplayingDecoder不需要判断是否足够读取，内部会进行处理判断
//        if(byteBuf.readableBytes()>=8){
//            list.add(byteBuf.readLong());
//        }
        list.add(byteBuf.readLong());
    }
}
