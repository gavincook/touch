/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * SecondServerOutHandler
 *
 * @author gavincook
 * @version $ID: SecondServerOutHandler.java, v0.1 2018-05-08 15:38 gavincook Exp $$
 */
public class SecondServerOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener((ChannelFutureListener) future -> {
            System.out.println("promise.isSuccess() = " + future.isSuccess());
            if (!promise.isSuccess()) {
                System.out.println("错误发生了");
            }
        });
        System.out.println(getClass() + " invoked");
        ctx.write(msg, promise);
    }
}
