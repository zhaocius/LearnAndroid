package com.zhaocius.nettytest;

import android.util.Log;

import java.net.InetSocketAddress;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;


public class UdpClient implements  Runnable{
    private static final String TAG = "UdpClient";

    private static final int PORT=29981;
    private static final String IP="255.255.255.255";
    private String context;
    public UdpClient(String context){
        this.context=context;
    }
    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());
            Channel ch = b.bind(0).sync().channel();
            Log.d(TAG,"客户端发送：context == "+context+" IP == "+IP+"  PORT == "+PORT);

            ch.writeAndFlush(
                    new DatagramPacket(
                            Unpooled.copiedBuffer(context, CharsetUtil.UTF_8),
                            new InetSocketAddress(IP, PORT)));

            if (!ch.closeFuture().await(15000)) {
                Log.d(TAG,"查询超时");
            }

        } catch (Exception e) {
// TODO: handle exception
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}