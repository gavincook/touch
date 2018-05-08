/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * 第一个处理器
 *
 * @author gavincook
 * @version $ID: FirstServerInHandler.java, v0.1 2018-05-08 14:58 gavincook Exp $$
 */
@ChannelHandler.Sharable
public class FirstServerInHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(getClass().getName() + " invoked");
        ctx.write(Unpooled.copiedBuffer("Hello", Charset.forName("UTF-8")));
        ctx.fireChannelRead(msg.toString(Charset.forName("UTF-8")));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
