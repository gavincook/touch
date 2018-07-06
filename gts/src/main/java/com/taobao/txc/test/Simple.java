package com.taobao.txc.test;

import com.taobao.txc.client.aop.annotation.TxcTransaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Simple {

    private static JdbcTemplate jdbcTemplate1 = null;
    private static JdbcTemplate jdbcTemplate2 = null;

    @TxcTransaction(timeout = 60000 * 3, appName = "moon")
    public void transfer(int money) throws InterruptedException {
        //A账户从db1减钱
        jdbcTemplate1.update("update USER_MONEY_A set money = money - ?", money);
        //        jdbcTemplate1.update("insert into USER_MONEY_A(money) values(123)");
        if (new Random().nextInt(100) < 0) {
            throw new RuntimeException("error");
        }

        //B账户从db2加钱
        jdbcTemplate2.update("update USER_MONEY_B  set money = money + ?", money);
        //        jdbcTemplate2.update("insert into USER_MONEY_B(money) values(456)");
        if (new Random().nextInt(5) < 10) {
            System.out.println("休息10s.....");
            Thread.sleep(10000);
            throw new RuntimeException("error");
        }

        //余额不足时抛异常，GTS会回滚前两次操作
        int money_a = getMoney(jdbcTemplate1, "USER_MONEY_A");
        if (money_a < 0) {
            Thread.sleep(20);
            throw new RuntimeException("Balance is not enough.");
        }
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("txc-client-context.xml");
        Simple simple = (Simple) context.getBean("clientTest");

        jdbcTemplate1 = (JdbcTemplate) context.getBean("jdbcTemplate1");
        jdbcTemplate2 = (JdbcTemplate) context.getBean("jdbcTemplate2");
        //清空账户
        jdbcTemplate1.update("truncate USER_MONEY_A");
        jdbcTemplate2.update("truncate USER_MONEY_B");
        //每个账户初始值为10000元
        jdbcTemplate1.update("insert into USER_MONEY_A(money) values(10000)");
        jdbcTemplate2.update("insert into USER_MONEY_B(money) values(10000)");

        //for (int i = 0; i < 10; ++i) {
        //System.out.println("第" + i + "次转账");
        try {
            simple.transfer(20);
        } catch (Exception e) {
            System.out.println("Tranfer failed，transaction is rollbacked.");
            e.printStackTrace();
        }
        int money1 = getMoney(jdbcTemplate1, "USER_MONEY_A");
        int money2 = getMoney(jdbcTemplate2, "USER_MONEY_B");
        System.out.println("A account：" + money1 + "    B account：" + money2 + "    total：" + (money1 + money2));
        Thread.sleep(1500);
        //}
        System.exit(0);
    }

    private static int getMoney(JdbcTemplate jdbcTemplate, String tableName) {
        String sql = "select money from " + tableName;
        List<Integer> moneyList = jdbcTemplate.queryForList(sql, java.lang.Integer.class);
        if (moneyList.size() > 0) {
            return moneyList.get(0);
        }
        return 0;
    }

}
