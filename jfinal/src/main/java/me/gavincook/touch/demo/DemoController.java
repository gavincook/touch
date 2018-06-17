/**
 * BBD Service Inc
 * All Rights Reserved @2018
 */
package me.gavincook.touch.demo;

import com.jfinal.core.Controller;

/**
 * DemoController
 *
 * @author gavincook
 * @version $ID: DemoController.java, v0.1 2018-06-12 11:09 gavincook Exp $$
 */
public class DemoController extends Controller {

    public void index() {
        renderText("Hello JFinal World.");
    }

}
