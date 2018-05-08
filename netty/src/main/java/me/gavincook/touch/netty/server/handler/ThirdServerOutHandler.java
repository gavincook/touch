/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * ThirdServerOutHandler
 *
 * @author gavincook
 * @version $ID: ThirdServerOutHandler.java, v0.1 2018-05-08 15:38 gavincook Exp $$
 */
public class ThirdServerOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(getClass() + " invoked");
        super.write(ctx, msg, promise);
    }
}
