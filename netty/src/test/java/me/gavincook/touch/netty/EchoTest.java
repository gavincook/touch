/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty;

import me.gavincook.touch.netty.client.EchoClient;
import me.gavincook.touch.netty.server.EchoServer;
import org.testng.annotations.Test;

/**
 * echo 服务端+ echo 客户端测试
 *
 * @author gavincook
 * @version $ID: EchoTest.java, v0.1 2018-05-04 17:13 gavincook Exp $$
 */
public class EchoTest {

    @Test
    public void testEcho() throws InterruptedException {
        EchoServer echoServer = new EchoServer();
        EchoClient echoClient = new EchoClient();

        //先启动服务器
        new Thread(() -> {
            try {
                echoServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //休眠一下，等server启动
        Thread.sleep(2000);

        //再启动客户端
        new Thread(() -> {
            try {

                echoClient.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //休眠一下，等待执行
        Thread.sleep(2000);

        echoClient.stop();
        echoServer.stop();
    }

}
