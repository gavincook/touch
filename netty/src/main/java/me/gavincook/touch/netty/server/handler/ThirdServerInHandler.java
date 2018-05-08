/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 第三个处理器
 *
 * @author gavincook
 * @version $ID: ThirdServerInHandler.java, v0.1 2018-05-08 14:58 gavincook Exp $$
 */
@ChannelHandler.Sharable
public class ThirdServerInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(getClass().getName() + " invoked");
        super.channelRead(ctx, msg);
    }

}