package br.com.bln.estudoprodutorapi.application.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bln.simplesqueuescomack.enabled", havingValue = "true", matchIfMissing = false)
public class SimplesQueuesComAckManualProductorConfiguration {

    @Bean
    public Queue simplesQueuesComACK() {
        return new Queue("SimplesQueuesComACK");
    }
}