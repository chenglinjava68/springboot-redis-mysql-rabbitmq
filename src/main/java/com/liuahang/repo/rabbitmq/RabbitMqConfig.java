package com.liuahang.repo.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class RabbitMqConfig {
	
	private static final Logger logger = Logger.getLogger(RabbitMqConfig.class);
	
	@Autowired
    private Environment env;
	
	private String queue = "init-springboot-queue";
	private String exchange = "init-springboot-exchange";
	    @Bean
	    public Queue queue() {
	        return new Queue(queue, false);
	       
	    }

	    @Bean
	    public TopicExchange exchange() {
	        return new TopicExchange(exchange);
	    	
	    }

	    @Bean
	    public Binding binding(Queue queue, TopicExchange exchange) {
	        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("spring.rabbitmq.routing.key"));
	    }

	    @Bean
	    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	        container.setConnectionFactory(connectionFactory);
	        container.setQueueNames(env.getProperty("spring.rabbitmq.queue"));
	        container.setAutoDeclare(true);
	        container.setMessageListener(listenerAdapter);
	        return container;
	    }

	    @Bean
	    public Receiver receiver() {
	        return new Receiver();
	    }

	    @Bean
	    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
	        return new MessageListenerAdapter(receiver, "onMessage");
	    }

	
}
