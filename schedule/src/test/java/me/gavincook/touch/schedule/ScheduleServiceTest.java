package me.gavincook.touch.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.testng.annotations.Test;

/**
 * ScheduleServiceTest
 *
 * @author gavincook
 * @date 2019-06-08 11:59
 * @since 1.0.0
 */
public class ScheduleServiceTest {

    @Test
    public void delay2Seconds() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.schedule(new Task(countDownLatch), 2,
            TimeUnit.SECONDS);

        countDownLatch.await();
        scheduledExecutorService.shutdown();
    }

    @Test
    public void scheduleTaskAt6PM() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LocalTime scheduleTime = LocalTime.of(18, 0, 0);
        LocalTime now = LocalTime.now();
        LocalDateTime nextExecuteTime = LocalDateTime.of(LocalDate.now(), scheduleTime);
        if (now.isAfter(scheduleTime)) {
            nextExecuteTime = nextExecuteTime.plusDays(1);
        }
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        System.out.println(now.until(nextExecuteTime, ChronoUnit.MILLIS));
        scheduledExecutorService.schedule(new Task(countDownLatch),
            now.until(nextExecuteTime, ChronoUnit.MILLIS),
            TimeUnit.MILLISECONDS);

        countDownLatch.await();
        scheduledExecutorService.shutdown();
    }

    @Test
    public void scheduleFixedDelay() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Task task1 = new Task(countDownLatch);
        task1.setName("first");
        scheduledExecutorService.scheduleWithFixedDelay(task1,
            1000, 500,
            TimeUnit.SECONDS);
        Thread.sleep(1000);
        scheduledExecutorService.scheduleWithFixedDelay(new Task(countDownLatch),
            2, 500,
            TimeUnit.SECONDS);

        countDownLatch.await();
        scheduledExecutorService.shutdown();
    }

    @Test
    public void scheduleCronTask() throws InterruptedException {

        CronTrigger cronTrigger = new CronTrigger("0/2 * * * * ?");
        SimpleTriggerContext simpleTriggerContext = new SimpleTriggerContext();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        while (!scheduledExecutorService.isShutdown()) {
            CountDownLatch countDownLatchForPerSchedule = new CountDownLatch(1);
            scheduledExecutorService.schedule(new Task(countDownLatchForPerSchedule),
                cronTrigger.nextExecutionTime(simpleTriggerContext).getTime() - System
                    .currentTimeMillis(),
                TimeUnit.MILLISECONDS);
            countDownLatchForPerSchedule.await();
            countDownLatch.countDown();
            if (countDownLatch.getCount() == 0) {
                scheduledExecutorService.shutdown();
            }
        }
    }

    public class Task implements Runnable {

        private CountDownLatch countDownLatch;

        private String name;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(
                name + " task executed at " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }

}
