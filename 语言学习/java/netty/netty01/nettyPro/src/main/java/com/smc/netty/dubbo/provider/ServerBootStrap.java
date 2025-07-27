package com.smc.netty.dubbo.provider;

import com.smc.netty.dubbo.netty.NettyServer;

/**
 * @Date 2022/12/4
 * @Author smc
 * @Description:会启动一个服务提供者，就是nettyServer
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }

}
