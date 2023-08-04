package com.example.demo.config;

import org.redisson.api.ExecutorOptions;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.api.executor.TaskFailureListener;
import org.redisson.api.executor.TaskFinishedListener;
import org.redisson.api.executor.TaskStartedListener;
import org.redisson.api.executor.TaskSuccessListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RedissonScheduledExecutorConfig {

    @Autowired
    private RedissonClient redissonClient;
    private static int cupNum = Runtime.getRuntime().availableProcessors();
    private static final Logger log = LoggerFactory.getLogger(RedissonScheduledExecutorConfig.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean(destroyMethod = "")
    public RScheduledExecutorService rScheduledExecutorService() {
        WorkerOptions workerOptions = WorkerOptions.defaults()

                // Defines workers amount used to execute tasks.
                // Default is 1.
                .workers(cupNum + 1)
                .beanFactory(applicationContext.getAutowireCapableBeanFactory())

                // Defines Spring BeanFactory instance to execute tasks
                // with Spring's '@Autowired', '@Value' or JSR-330's '@Inject' annotation.

                // Defines custom ExecutorService to execute tasks
                // Config.executor is used by default.

                // Defines task timeout since task execution start moment

                // add listener which is invoked on task successed event
                .addListener(new TaskSuccessListener() {
                    public <T> void onSucceeded(String taskId, T result) {
                        log.info("invoked on task successes event");
                    }

                })

                // add listener which is invoked on task failed event
                .addListener(new TaskFailureListener() {
                    public void onFailed(String taskId, Throwable exception) {
                        // 需要自己实现重试次数
                        log.info("invoked on task failed event");
                    }
                })

                // add listener which is invoked on task started event
                .addListener(new TaskStartedListener() {
                    public void onStarted(String taskId) {
                        // ...
                        log.info("task started event");
                    }
                })

                // add listener which is invoked on task finished event
                .addListener(new TaskFinishedListener() {
                    public void onFinished(String taskId) {
                        // ...
                        log.info("invoked on task finished event");
                    }
                });

        ExecutorOptions executorOptions = ExecutorOptions.defaults()
                .taskRetryInterval(10, TimeUnit.SECONDS);
        RScheduledExecutorService executorService = redissonClient
                .getExecutorService("ConversationTimeoutExecutorService", executorOptions);
        executorService.registerWorkers(workerOptions);
        return executorService;
    }
}
