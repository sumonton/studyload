package com.smc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2022/9/2
 * @Author smc
 * @Description:
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        //线程池机制
        /**
         * 思路
         *  1、创建一个线程池
         *  2、如果有客户端连接，就创建一个线程，与之通讯（单独写一个方法）
         */

        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");
        while (true){
            System.out.println("线程信息 ID ="+Thread.currentThread().getId()+
                    "名字="+Thread.currentThread().getName());
            //监听
            System.out.println("等待连接。。。");
            Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //就创建一个线程，与之通信
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    handler(accept);
                    
                }
            });
//            accept.getInputStream().read();
        }

    }
    
    //编写一个handler方法，和客户端通讯
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        //通过socket，获取输入流
        try {

            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true){
                System.out.println("线程信息 ID ="+Thread.currentThread().getId()+
                        "名字="+Thread.currentThread().getName());
                System.out.println("read..");
                int read = inputStream.read(bytes);
                if(read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
