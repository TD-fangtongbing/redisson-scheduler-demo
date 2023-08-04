package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "channel_exchange";
    public static final String EXCHANGE_NAME_1 = "channel_exchange_1";
    public static final String QUEUE_NAME = "channel_queue";
    public static final String ROUTING_KEY = "chat_channel_routing_key";

    public static final String QUEUE_NAME_1 = "channel_queue_1";
    public static final String ROUTING_KEY_1 = "chat_channel_routing_key_1";

    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    @Bean
    public DirectExchange myExchange1() {
        return new DirectExchange(EXCHANGE_NAME_1);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, true);
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(myExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Queue myQueue1() {
        return new Queue(QUEUE_NAME_1, true);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(myExchange1()).with(ROUTING_KEY_1);
    }

}

