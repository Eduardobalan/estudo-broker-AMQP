package br.com.bln.estudocomsumidorapi.application.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ExchangesDirectConsumerConfiguration {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("ExchangesDirect");
    }

    @Bean
    public Queue autoDeleteQueueDirectExchange1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueueDirectExchange2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1a(DirectExchange direct,
                             Queue autoDeleteQueueDirectExchange1) {
        return BindingBuilder.bind(autoDeleteQueueDirectExchange1)
                .to(direct)
                .with("black");
    }

    @Bean
    public Binding binding1b(DirectExchange direct,
                             Queue autoDeleteQueueDirectExchange2) {
        return BindingBuilder.bind(autoDeleteQueueDirectExchange2)
                .to(direct)
                .with("orange");
    }

    @Bean
    public Binding binding2a(DirectExchange direct,
                             Queue autoDeleteQueueDirectExchange2) {
        return BindingBuilder.bind(autoDeleteQueueDirectExchange2)
                .to(direct)
                .with("green");
    }

    @Bean
    public Binding binding2b(DirectExchange direct,
                             Queue autoDeleteQueueDirectExchange2) {
        return BindingBuilder.bind(autoDeleteQueueDirectExchange2)
                .to(direct)
                .with("black");
    }
}