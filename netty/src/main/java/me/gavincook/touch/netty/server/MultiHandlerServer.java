/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.gavincook.touch.netty.server.handler.ExceptionCaughtServerHandler;
import me.gavincook.touch.netty.server.handler.FirstServerInHandler;
import me.gavincook.touch.netty.server.handler.FirstServerOutHandler;
import me.gavincook.touch.netty.server.handler.SecondServerInHandler;
import me.gavincook.touch.netty.server.handler.SecondServerOutHandler;
import me.gavincook.touch.netty.server.handler.ThirdServerInHandler;
import me.gavincook.touch.netty.server.handler.ThirdServerOutHandler;
import me.gavincook.touch.netty.util.Configuration;

/**
 * 多个处理逻辑的服务器，用于测试多个handler在不同场景下的调用顺序
 * <pre>
 *     -----------------------------------------------------------------
 *     | FirstServerOutHandler        | 读取处理器调用顺序    /\
 *     | SecondServerOutHandler       |        ||          / \
 *     | FirstServerInHandler         |        ||           ||
 *     | SecondServerInHandler        |        ||           ||
 *     | ThirdServerInHandler         |        ||           ||
 *     | ExceptionCaughtServerHandler |       \  /          ||
 *     | ThirdServerOutHandler        |        \/      写数据调用顺序
 *     -----------------------------------------------------------------
 *
 * </pre>
 *
 * 如果写数据调用channel#write或pipeline#write会从最后一个出站处理器开始一直流像第一个出站处理器，如果调用ChannelHandlerContext#write
 * 则会从该上下文关联的handler处开始流向第一个出站处理器。
 *
 * <p>
 *     对于入站异常，只需要复写exceptionCaught即可。
 *     对于出站异常，需要为写入操作（ChannelFuture）添加事件监听或者对写入对象的promise对象进行监听。
 * </p>
 * @author gavincook
 * @version $ID: MultiHandlerServer.java, v0.1 2018-05-08 14:57 gavincook Exp $$
 */
public class MultiHandlerServer {

    private Channel channel;

    /**
     * 启动服务器
     */
    public void start() throws InterruptedException {
        //获取监听端口
        int port = Integer.valueOf(Configuration.getServerConfig("listen"));

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class).localAddress(port)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {

                    //出站处理器
                    ch.pipeline().addLast(new FirstServerOutHandler());
                    ch.pipeline().addLast(new SecondServerOutHandler());

                    //添加入站处理器
                    ch.pipeline().addLast(new FirstServerInHandler());//该入站处理器会发起数据写出
                    ch.pipeline().addLast(new SecondServerInHandler());
                    ch.pipeline().addLast(new ThirdServerInHandler());
                    ch.pipeline().addLast(new ExceptionCaughtServerHandler());

                    //出站处理器
                    ch.pipeline().addLast(new ThirdServerOutHandler());

                }
            });

        ChannelFuture channelFuture = serverBootstrap.bind().sync();

        channel = channelFuture.channel();

        channelFuture.channel().closeFuture().sync();
    }

    /**
     * 关闭服务器
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        if (channel != null) {
            channel.close().sync();
        }
    }
}
