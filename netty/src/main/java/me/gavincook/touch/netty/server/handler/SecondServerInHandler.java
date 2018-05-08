/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 第二个处理器
 *
 * @author gavincook
 * @version $ID: SecondServerInHandler.java, v0.1 2018-05-08 14:58 gavincook Exp $$
 */
@ChannelHandler.Sharable
public class SecondServerInHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(getClass().getName() + " invoked");
        ctx.fireChannelRead(msg);
    }
}