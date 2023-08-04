package com.example.demo.entity;

import com.example.demo.enums.InteractionStatus;
import com.google.gson.Gson;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

import static com.example.demo.config.RabbitMQConfig.EXCHANGE_NAME_1;
import static com.example.demo.config.RabbitMQConfig.ROUTING_KEY_1;

public class MyScheduledTask implements Runnable, Serializable {
    private String interactionId;
    private String accountId;
    private String userId;

    @RInject
    private RedissonClient redissonClient;

    @RInject
    private String taskId;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(MyScheduledTask.class);

    @Override
    public void run() {


        // task timeout run action
        try {
            log.info("scheduler is running，current time：{}, taskId: {}", System.currentTimeMillis(), taskId);

            EventMsg msg = new EventMsg();
            msg.setAccountId("accountId");
            msg.setCreateTime(System.currentTimeMillis());
            msg.setInteractionId(interactionId);
            msg.setInteractionStatus(InteractionStatus.ASSIGNED.getStr());

            msg.setChannelType("CHAT");
            rabbitTemplate.convertAndSend(EXCHANGE_NAME_1, ROUTING_KEY_1, new Gson().toJson(msg));
            log.info("send do action msg to mq success。。。");
        } catch (Exception e) {
            // todo try times
            log.info("occurred exception", e);
        }
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

