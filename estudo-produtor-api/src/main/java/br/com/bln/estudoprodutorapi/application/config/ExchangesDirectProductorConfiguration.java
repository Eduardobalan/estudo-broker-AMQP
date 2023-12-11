package br.com.bln.estudoprodutorapi.application.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bln.exchangesdirect.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesDirectProductorConfiguration {
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("ExchangesDirect");
    }
}
