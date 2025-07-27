package com.smc.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Date 2022/9/17
 * @Author smc
 * @Description:
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建serversocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6660));

        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector关心时间为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {
            //这里我们等待1秒，如果没有事情发生，返回
            if (selector.select(1000) == 0) {//没有事情发生
//                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            /**
             * 如果返回>0，就获取到相关的selectionKey集合
             * 1、如果返回>0表示已经获取到关注的事件
             * 2、selector.selectedKeys()返回关注时间的集合
             *      通过selectionkey反向获取通道
             */
            System.out.println(selector.keys().size()+","+selector.selectedKeys().size());
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                //获取selectionKey
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {//如果是OP_ACCEPT,就有新的客户端连接
                    //就给该客户端生辰给一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成一个socketChannel" + socketChannel.hashCode());
                    //将socketChannel注册到Selector,关注时间为OP_READ，同时给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(20));

                }
                if (next.isReadable()) {//发生op_read
                    //通过key反向获取channel
                    SocketChannel channel = (SocketChannel) next.channel();
                    //获取到该channel关联到buffer
                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println("from客户端：" + new String(byteBuffer.array(), StandardCharsets.UTF_8));


                }

                //手动从集合中移除当前的selectionKey，防止重复操作
                iterator.remove();
            }


        }
    }
}
