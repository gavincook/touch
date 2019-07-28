package me.gavincook.touch.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.testng.annotations.Test;

/**
 * TimerTest
 *
 * @author gavincook
 * @date 2019-06-08 11:59
 * @since 1.0.0
 */
public class TimerTest {

    @Test
    public void delay2Seconds() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Timer timer = new Timer();
        timer.schedule(new Task(countDownLatch), 2000);

        countDownLatch.await();
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Timer timer = new Timer();
        timer.schedule(new Task(countDownLatch), calendar.getTime());

        countDownLatch.await();
    }

    @Test
    public void scheduleCycle() throws InterruptedException {
        Timer timer = new Timer();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        timer.schedule(new Task(countDownLatch), 5000, 2000);
        countDownLatch.await();
    }

    @Test
    public void scheduleCycleStartAtSpecialTime() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0);

        timer.schedule(new Task(countDownLatch), calendar.getTime(), 2000);
        countDownLatch.await();
    }

    @Test
    public void scheduleFixed() throws InterruptedException {
        Timer timer = new Timer();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        timer.scheduleAtFixedRate(new Task(countDownLatch), 5000, 2000);
        countDownLatch.await();
    }

    @Test
    public void scheduleFixedStartAtSpecialTime() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        //        timer.scheduleAtFixedRate(() -> sendEmailToBoss(), calendar.getTime(), 24 * 3600 * 1000);
        countDownLatch.await();
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
                cronTrigger.nextExecutionTime(simpleTriggerContext).getTime() - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);
            countDownLatchForPerSchedule.await();
            countDownLatch.countDown();
            if (countDownLatch.getCount() == 0) {
                scheduledExecutorService.shutdown();
            }
        }
    }

    public class Task extends TimerTask {

        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            System.out.println(
                "task initialized at " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        @Override
        public void run() {
            System.out.println(
                "task executed at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            countDownLatch.countDown();
        }
    }
}
