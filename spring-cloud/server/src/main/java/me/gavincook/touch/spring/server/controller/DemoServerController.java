package me.gavincook.touch.spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author gavincook
 * @version $ID: DemoController.java, v0.1 2018-06-17 18:18 gavincook Exp $$
 */
@RestController
public class DemoServerController {
    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/test")
    public String test() {
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

}
