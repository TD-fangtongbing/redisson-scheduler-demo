package com.example.demo.controller;

import com.example.demo.entity.EventMsg;
import com.example.demo.enums.InteractionStatus;
import com.example.demo.service.ScheduledTaskService;
import com.example.demo.utils.UUidUtils;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.config.RabbitMQConfig.EXCHANGE_NAME;
import static com.example.demo.config.RabbitMQConfig.ROUTING_KEY;

@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @GetMapping("/sendMsg")
    public void send() {
        EventMsg msg = new EventMsg();
        msg.setAccountId("accountId");
        msg.setCreateTime(System.currentTimeMillis());
        msg.setInteractionId(UUidUtils.getUUid());
        msg.setInteractionStatus(InteractionStatus.ASSIGNED.getStr());

        msg.setChannelType("CHAT");

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, new Gson().toJson(msg));
    }
}
