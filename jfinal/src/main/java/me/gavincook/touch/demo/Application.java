/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.demo;

import com.jfinal.core.JFinal;

/**
 * Application
 *
 * @author gavincook
 * @version $ID: Application.java, v0.1 2018-06-12 11:13 gavincook Exp $$
 */
public class Application {

    public static void main(String[] args) {
        JFinal.start("/workspace/personal/touch/jfinal/src/main/webapp", 8089, "/", 5);
    }
}
