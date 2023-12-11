package br.com.bln.estudoprodutorapi.application.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bln.exchangesfanout.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesFanoutProductorConfiguration {
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("ExchangesFanout", true, false);
    }
}
