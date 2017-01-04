package com.strengthtailor;

import com.strengthtailor.receiver.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StrengthTailorServerApplication {

	final public static String queueName = "strength-tailor-action";

	@Autowired Receiver receiver;

	@Bean
	Queue queue() {
		return new Queue(queueName, false, false, true);
	}

	@Bean
    DirectExchange exchange() {
		return new DirectExchange("strength-tailor-exchange", false, true);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory("wyvern");
		factory.setUsername("guest");
		factory.setPassword("guest");

		return factory;
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(receiver);
		return container;
	}

/*
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
*/

	public static void main(String[] args) {
		SpringApplication.run(StrengthTailorServerApplication.class, args);
	}
}
