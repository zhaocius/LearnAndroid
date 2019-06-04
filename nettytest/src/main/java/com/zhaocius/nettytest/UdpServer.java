package com.zhaocius.nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer implements Runnable {
    private static final String TAG = "UdpServer";

    private static final int PORT=12345;
    @Override
    public void run() {
        try {
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new UdpSeverHandler());

            // 服务端监听在29981端口
            try {
                b.bind(PORT).sync().channel().closeFuture().await();
            }catch(Exception e){

            }

        }catch (Exception e){

        }

    }
}
