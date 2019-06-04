package com.zhaocius.nettytest;
import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class UdpSeverHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String TAG = "UdpSeverHandler";
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception
    {
        // 读取收到的数据
        ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, CharsetUtil.UTF_8);
        Log.d(TAG, "收到客户端的数据："+body);

        // 回复一条信息给客户端
        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer("(1)\r\n"
                        , CharsetUtil.UTF_8)
                , packet.sender())).sync();
    }
}