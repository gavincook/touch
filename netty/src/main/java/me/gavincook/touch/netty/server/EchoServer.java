package me.gavincook.touch.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.gavincook.touch.netty.server.handler.EchoServerHandler;
import me.gavincook.touch.netty.util.Configuration;

/**
 * Echo 服务器
 *
 * @author gavincook
 * @version $ID: EchoServer.java, v0.1 2018-05-04 15:51 gavincook Exp $$
 */
public class EchoServer {

    private Channel channel;

    /**
     * 启动服务端
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        //获取监听端口
        int port = Integer.valueOf(Configuration.getServerConfig("listen"));

        //消息处理器
        final EchoServerHandler echoServerHandler = new EchoServerHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup).localAddress(port).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(echoServerHandler);
                }
            });

        //绑定到指定端口
        ChannelFuture channelFuture = serverBootstrap.bind().sync();

        channel = channelFuture.channel();

        //阻塞直到关闭
        channelFuture.channel().closeFuture().sync();

    }

    /**
     * 关闭服务端
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        channel.close().sync();
    }
}
