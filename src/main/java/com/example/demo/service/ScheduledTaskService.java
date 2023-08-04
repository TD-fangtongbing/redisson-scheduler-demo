package com.example.demo.service;

import com.example.demo.entity.MyScheduledTask;
import org.redisson.api.RScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class ScheduledTaskService {

    @Autowired
    private RScheduledExecutorService executorService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);

    /**
     * according interaction update or add scheduler task.
     * executor will be restored lastly scheduler task
     * taskId : interactionId
     */
    public String addScheduledTask(Duration duration, MyScheduledTask task) throws ExecutionException, InterruptedException {
        String taskId = executorService.schedule(task.getInteractionId(), task, duration).getTaskId();

        log.info("success add taskId {}", taskId);

        return taskId;
    }

    /**
     * cancel a scheduler task
     *
     * @param interactionId
     * @return if a task wasn't found return null
     */
    public Boolean cancelScheduledTask(String interactionId) {
        return executorService.cancelTask(interactionId);
    }
}
