package me.gavincook.touch.spring.client.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * DemoService
 *
 * @author gavincook
 * @version $ID: DemoService.java, v0.1 2018-06-17 21:43 gavincook Exp $$
 */
@FeignClient("demo-server")
public interface DemoService {

    @GetMapping("/test")
    String test();

}
