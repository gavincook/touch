package me.gavincook.touch.spring.di;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 候选者1，依赖候选者2
 * 循环依赖参见文章：https://gavincook.me/spring-applicationcontextaware/
 * @author gavincook
 * @version $ID: CandidateOne.java, v0.1 2018-06-13 09:38 gavincook Exp $$
 */
@Component
public class CandidateOne implements ApplicationContextAware, Ordered {

    @Autowired
    private CandidateTwo candidateTwo;

    /**
     * 实例测试方法
     */
    public void testMethodForOne() {
        //此处会报空指针异常
        System.out.println(candidateTwo.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        candidateTwo.testMethodForTwo();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
