package br.com.bln.estudoprodutorapi.application.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bln.exchangestopic.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesTopicProductorConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("ExchangeTopic");
    }
}
