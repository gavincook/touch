package me.gavincook.touch.netty.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置
 *
 * @author gavincook
 * @version $ID: Configuration.java, v0.1 2018-05-04 15:59 gavincook Exp $$
 */
public class Configuration {

    private static final Configuration configuration    = new Configuration();

    /** 服务端配置 */
    private Properties                 serverProperties = new Properties();

    /** 客户端配置 */
    private Properties                 clientProperties = new Properties();
    {
        try {
            serverProperties.load(Configuration.class.getClassLoader().getResourceAsStream("server.properties"));
            clientProperties.load(Configuration.class.getClassLoader().getResourceAsStream("client.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务端配置
     * @param key
     * @return
     */
    public static String getServerConfig(String key) {
        return configuration.serverProperties.getProperty(key);
    }

    /**
     * 获取客户端配置
     * @param key
     * @return
     */
    public static String getClientConfig(String key) {
        return configuration.clientProperties.getProperty(key);
    }
}
