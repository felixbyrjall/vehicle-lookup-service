package com.github.felixbyrjall.vehiclelookup.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	@Bean
	public TopicExchange vehicleExchange() {
		return new TopicExchange("vehicle.exchange");
	}
}
