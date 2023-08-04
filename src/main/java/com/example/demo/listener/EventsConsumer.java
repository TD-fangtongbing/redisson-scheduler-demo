package com.example.demo.listener;

import com.example.demo.entity.EventMsg;
import com.example.demo.entity.MyScheduledTask;
import com.example.demo.service.ScheduledTaskService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.example.demo.config.RabbitMQConfig.QUEUE_NAME;
import static com.example.demo.config.RabbitMQConfig.QUEUE_NAME_1;


@Component
public class EventsConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventsConsumer.class);

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @RabbitListener(queues = QUEUE_NAME)
    public void handleMessage(String message) {
        EventMsg eventMsg = new Gson().fromJson(message, EventMsg.class);
        log.info("Received eventMsg: {}", message);

        String interactionId = eventMsg.getInteractionId();

        //generate task
        MyScheduledTask task = new MyScheduledTask();
        task.setInteractionId(interactionId);

        try {
            int delay = new Random().nextInt(61) + 20;
            log.info("interactionId: {}; task delay time： {}", task.getInteractionId(), delay);
            scheduledTaskService.addScheduledTask(Duration.ofSeconds(delay), task);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = QUEUE_NAME_1)
    public void handleMessage2(String message) {
        log.info("Received timeout eventMsg: {}", message);
    }

}
