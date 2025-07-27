package com.smc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    public static final int PORT=6667;

    //构造器初始化操作
    public GroupChatServer(){
        try {
            //得到选择器
            selector=Selector.open();
            //得到serverSocketChannel
            listenChannel= ServerSocketChannel.open();
            //绑定端口
            listenChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将channel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //监听
    public void listen(){
        try {
            //循环处理
            while (true){
                int count=selector.select(2000);
                //大于0,表示有事件要处理
                if (count>0){
                    //便利得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            //注册到socket
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        //通道发生read事件
                        if(key.isReadable()){
                            //处理读
                            readData(key);
                        }

                        //当前key删除，防止重复处理

                        iterator.remove();
                    }
                }else {
//                    System.out.println("服务端等待...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  读取客户端消息
    private void readData(SelectionKey key)  {
        //定义一个socketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //读取
            int read = channel.read(buffer);
            //判断是否读取到数据
            if(read>0){
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出消息
                System.out.println("from 客户端：" + channel.getRemoteAddress() + " 的消息：" + msg);

                //把消息转发给其他客户端(需去掉自己)
                sendInfoToClients(msg,channel);
            }
        }catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    //转发消息给其他客户（通道）
    private void sendInfoToClients(String msg,SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        //便利所有注册到selector上的Socketchannel，并排除channel
        for (SelectionKey key : selector.keys()) {
            //通过key取出对应的socketChannel
            SelectableChannel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel!=selfChannel){
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;

                //将buffer存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));

                //将buffer数据写到channel

                dest.write(buffer);

            }
        }

    }
    public static void main(String[] args) {
        //创建一份服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
