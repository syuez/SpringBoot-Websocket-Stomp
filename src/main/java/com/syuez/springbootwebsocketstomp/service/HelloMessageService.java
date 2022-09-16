package com.syuez.springbootwebsocketstomp.service;

import com.syuez.springbootwebsocketstomp.common.SendMessage;
import com.syuez.springbootwebsocketstomp.repository.SessionRedisRepository;
import com.syuez.springbootwebsocketstomp.session.SessionSubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当用户订阅 hello 主题时，发送消息
 */
@Service("hello")
public class HelloMessageService implements SendMessage {
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> yourTaskState;
    private final SimpMessagingTemplate template;
    private final SessionRedisRepository repository;
    /**
     * 当前连接数
     */
    private static final AtomicInteger count = new AtomicInteger(0);

    @Autowired
    public HelloMessageService(TaskScheduler taskScheduler, SimpMessagingTemplate template, SessionRedisRepository repository) {
        this.taskScheduler = taskScheduler;
        this.template = template;
        this.repository = repository;
    }
    /*
    * Start Stop 一个定时任务
    * https://stackoverflow.com/questions/44644141/how-to-stop-a-scheduled-task-that-was-started-using-scheduled-annotation
    */
    class ScheduledTaskExecutor implements Runnable {

        @Override
        public void run() {
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            template.convertAndSend("/topic/hello", "Hello: "+time);
        }
    }

    @Override
    public void start() {
        /*
       仅当连接数为0时才会创建线程
        */
        if(count.get() == 0) {
            long fixedDelay = 5000L;
            yourTaskState =  taskScheduler.scheduleWithFixedDelay(new HelloMessageService.ScheduledTaskExecutor(), fixedDelay);
        }
        // 数量+1
        count.getAndIncrement();
    }

    @Override
    public void stop() {
        /*
        判断当前连接数，仅当用户数只有一个时，断开才会取消线程
         */
        if(count.get() == 1) {
            yourTaskState.cancel(true);
            Optional<SessionSubscribe> optional = Optional.ofNullable(repository.findBySimpSubscriptionId("hello"));
            optional.ifPresent(repository::delete);
        }

        if(count.get() > 0) {
            // 数量-1
            count.getAndDecrement();
        }
    }

}
