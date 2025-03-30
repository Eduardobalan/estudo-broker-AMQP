package br.com.bln.estudoprodutorapi.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bln.exchangesDirectRPC.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesDirectRPCProductorConfiguration {

    public static final String REQUEST_QUEUE = "rpc.requests";
    public static final String EXCHANGE_NAME = "rpc.direct";
    public static final String ROUTING_KEY = "rpc_key";

    @Bean
    public DirectExchange rpcExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue rpcRequestQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }


    @Bean
    public Binding binding(DirectExchange rpcExchange, Queue rpcRequestQueue) {
        return BindingBuilder
                .bind(rpcRequestQueue)
                .to(rpcExchange)
                .with(ROUTING_KEY);
    }
}