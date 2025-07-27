package com.smc.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Date 2022/11/12
 * @Author smc
 * @Description:
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount=0;
            while (-1 !=readCount){
                try {
                    readCount = socketChannel.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.rewind();//倒带position=0，mark报废
            }
        }
    }
}
