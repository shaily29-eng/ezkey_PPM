package com.ezkey.projects.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String USER_ID_EXCHANGE = "user_id_exchange";
    public static final String USER_ID_RESPONSE_TOPIC = "q.response-user-id";
    public static final String USER_ID_REQUEST_TOPIC = "q.request-user-id";

    @Bean
    public Queue requestQueue() {
        return new Queue(USER_ID_REQUEST_TOPIC);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(USER_ID_RESPONSE_TOPIC);
    }

    @Bean
    public TopicExchange rpcExchange() {
        return new TopicExchange(USER_ID_EXCHANGE);
    }

    @Bean
    public Binding binding(TopicExchange rpcExchange, Queue requestQueue) {
        return BindingBuilder.bind(requestQueue).to(rpcExchange).with(USER_ID_REQUEST_TOPIC);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
