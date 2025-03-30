package br.com.bln.estudocomsumidorapi.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesDirectRPCConsumerConfiguration {

    public static final String REQUEST_QUEUE = "rpc.requests";
    public static final String EXCHANGE_NAME = "rpc.direct";
    public static final String ROUTING_KEY = "rpc_key";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue requestQueue) {
        return BindingBuilder
                .bind(requestQueue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}