package me.gavincook.touch.spring.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Application
 *
 * @author gavincook
 * @version $ID: Application.java, v0.1 2018-06-17 18:17 gavincook Exp $$
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DirectClientApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DirectClientApplication.class).web(true).run(args);
    }
}
