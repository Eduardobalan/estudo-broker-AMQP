package br.com.bln.estudocomsumidorapi.application.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimplesQueuesComAckManualConsumerConfiguration {

    @Bean
    public Queue SimplesQueuesComACK() {
        return new Queue("SimplesQueuesComACK");
    }
}