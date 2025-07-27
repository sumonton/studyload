package com.smc.netty.codec2;

import com.smc.netty.codec.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @Date 2022/11/16
 * @Author smc
 * @Description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    //当通道就绪时就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发哦送Student 或者Worker对象
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
        if (random==0){
            //发送一个Student对象
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("李四").build()).build();
        }else{
            //发送一个worker对象
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setName("王五").setAge(23).build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    //有通道读取事件时，会触发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
