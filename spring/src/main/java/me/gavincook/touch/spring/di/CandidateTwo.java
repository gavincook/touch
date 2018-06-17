package me.gavincook.touch.spring.di;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 候选者2，依赖后选择1
 *
 * @author gavincook
 * @version $ID: CandidateTwo.java, v0.1 2018-06-13 09:38 gavincook Exp $$
 */
@Component
public class CandidateTwo implements ApplicationContextAware, Ordered {

    @Autowired
    private CandidateOne candidateOne;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        candidateOne.testMethodForOne();
    }

    /**
     * 实例测试方法
     */
    public void testMethodForTwo() {
        System.out.println(candidateOne.toString());
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
