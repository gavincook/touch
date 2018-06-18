package me.gavincook.touch.spring.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * DemoClientController
 *
 * @author gavincook
 * @version $ID: DemoClientController.java, v0.1 2018-06-17 20:38 gavincook Exp $$
 */
@RestController
public class LoadBalanceClientController {

    @Autowired
    private RestTemplate       restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 直接访问
     * @return
     */
    @RequestMapping("/load")
    public String load() {
        String url = "http://demo-server/test";
        return restTemplate.getForObject(url, String.class);
    }
}
