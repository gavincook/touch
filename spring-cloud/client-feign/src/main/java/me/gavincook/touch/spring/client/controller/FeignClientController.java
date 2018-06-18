package me.gavincook.touch.spring.client.controller;

import me.gavincook.touch.spring.client.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoClientController
 *
 * @author gavincook
 * @version $ID: DemoClientController.java, v0.1 2018-06-17 20:38 gavincook Exp $$
 */
@RestController
public class FeignClientController {
    @Autowired
    DemoService demoService;

    /**
     * @return
     */
    @RequestMapping("/feign")
    public String test() {
        return demoService.test();
    }
}
