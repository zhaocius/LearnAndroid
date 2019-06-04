package com.zhaocius.nettytest;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String TAG = "UdpClientHandler";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
// TODO Auto-generated method stub
        String response = packet.content().toString(CharsetUtil.UTF_8);
        Log.d(TAG, "客户端收到回复：response == "+response);
        ctx.close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }


}
