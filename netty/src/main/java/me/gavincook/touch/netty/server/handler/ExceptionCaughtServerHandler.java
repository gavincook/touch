/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 异常处理器
 *
 * @author gavincook
 * @version $ID: FirstServerHandler.java, v0.1 2018-05-08 14:58 gavincook Exp $$
 */
@ChannelHandler.Sharable
public class ExceptionCaughtServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(getClass().getName() + " invoked");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("异常发生，关闭通道");
        ctx.channel().close();
    }
}