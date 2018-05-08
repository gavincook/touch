/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.netty;

import me.gavincook.touch.netty.client.EchoClient;
import me.gavincook.touch.netty.server.MultiHandlerServer;
import org.testng.annotations.Test;

/**
 * 多handler处理器服务端+ echo 客户端测试
 *
 * @author gavincook
 * @version $ID: MultiHandlerTest.java, v0.1 2018-05-04 17:13 gavincook Exp $$
 */
public class MultiHandlerTest {

    @Test
    public void testMultiHandler() throws InterruptedException {
        MultiHandlerServer server = new MultiHandlerServer();
        EchoClient echoClient = new EchoClient();

        //先启动服务器
        new Thread(() -> {
            try {
                server.start();
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
        server.stop();
    }

}
