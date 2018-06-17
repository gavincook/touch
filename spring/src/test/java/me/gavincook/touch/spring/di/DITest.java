package me.gavincook.touch.spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 依赖测试
 *
 * @author gavincook
 * @version $ID: DITest.java, v0.1 2018-06-13 09:52 gavincook Exp $$
 */
public class DITest {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("me.gavincook.touch.spring");
        applicationContext.refresh();
        System.out.println(applicationContext.getBeanDefinitionNames().length);
        Thread.sleep(10000000);
    }

}
