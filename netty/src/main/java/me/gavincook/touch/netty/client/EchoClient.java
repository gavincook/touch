package me.gavincook.touch.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.gavincook.touch.netty.client.handler.EchoClientHandler;
import me.gavincook.touch.netty.util.Configuration;

/**
 * Echo 客户端
 *
 * @author gavincook
 * @version $ID: EchoClient.java, v0.1 2018-05-04 16:58 gavincook Exp $$
 */
public class EchoClient {

    private Channel channel;

    /**
     * 启动客户端
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {

        //需要连接的远程服务器信息
        String remoteHost = Configuration.getClientConfig("remote.host");
        int remotePort = Integer.valueOf(Configuration.getClientConfig("remote.port"));

        final EchoClientHandler echoClientHandler = new EchoClientHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).remoteAddress(remoteHost, remotePort).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(echoClientHandler);
                }
            });

        //连接远程服务器
        ChannelFuture channelFuture = bootstrap.connect().sync();

        channel = channelFuture.channel();

        //一直阻塞到通道关闭
        channelFuture.channel().closeFuture().sync();
    }

    /**
     * 关闭客户端
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        channel.close().sync();
    }
}
